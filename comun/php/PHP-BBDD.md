<div align="justify">

# <img src="../../images/coding-book.png" width="40"> Code & Learn (Clases y BBDD )

Esta guía describe **cómo usar SQLite3 con PHP** de forma segura y eficiente, cubriendo **PDO** y la extensión **SQLite3**, migraciones, transacciones, concurrencia, rendimiento y despliegue. 

---

## 1) ¿Cuándo elegir SQLite?

- **Aplicaciones monolíticas, CLI, APIs pequeñas/MVPs, herramientas internas**.
- Cuando se desea **cero dependencias externas** (sin servidor de BBDD).
- **Lecturas dominantes** y concurrencia moderada (hasta cientos de RPS con WAL).
- No es ideal para **altas escrituras concurrentes** o **shards** complejos.

> SQLite es un motor **embebido**: la base es un **archivo** en disco (o en memoria).

---

## 2) Requisitos e instalación

### PHP y extensiones

Asegúrate de tener al menos **PHP 8.x** y una de estas extensiones:

- `pdo_sqlite` (recomendado por su portabilidad y API común PDO).
- `sqlite3` (API orientada a SQLite, también válida).

```bash
sudo apt-get install -y php-sqlite3
php -m | grep -E "pdo_sqlite|sqlite3"
```

## 3) Elegir API: PDO vs SQLite3

| Aspecto        | PDO (pdo_sqlite)                                   | Extensión SQLite3                    |
|----------------|-----------------------------------------------------|--------------------------------------|
| Portabilidad   | Alta (mismo código con MySQL/Postgres cambiando DSN)| Media (API específica de SQLite)     |
| Prepared Stmts | Sí (nativas)                                       | Sí                                   |
| Excepciones    | `PDO::ERRMODE_EXCEPTION`                            | Manejo manual con `lastErrorMsg()`   |
| Recomendación  | **Usar por defecto**                                | Úsala si ya trabajas con su API      |

---

## 4) Conexión y creación de la base

### 4.1 Con PDO (recomendado)

```php
<?php
$path = __DIR__ . '/data/app.db';           
$dsn  = 'sqlite:' . $path;

$pdo = new PDO($dsn, null, null, [
  PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
  PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
  PDO::ATTR_EMULATE_PREPARES   => false,
]);


$pdo->exec('PRAGMA foreign_keys = ON');
$pdo->exec('PRAGMA busy_timeout = 5000');   
```

> Si el archivo no existe, SQLite lo **crea automáticamente** (si hay permisos).

### 4.2 Con la extensión SQLite3

```php
<?php
$db = new SQLite3(__DIR__ . '/data/app.db', SQLITE3_OPEN_READWRITE | SQLITE3_OPEN_CREATE);
$db->exec('PRAGMA foreign_keys = ON');
$db->busyTimeout(5000);
```

### 4.3 En memoria (tests)

- **PDO**: `sqlite::memory:`
- **SQLite3**: `:memory:`

> En memoria es volátil: se pierde al finalizar el proceso.

---

## 5) Esquema y migraciones

### 5.1 Crear tablas (SQL básico)

```php
$pdo->exec(<<<SQL
CREATE TABLE IF NOT EXISTS users (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  email TEXT NOT NULL UNIQUE,
  name  TEXT NOT NULL,
  created_at TEXT NOT NULL DEFAULT (datetime('now'))
);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
SQL);
```

### 5.2 Migraciones con archivos `.sql`

Estructura simple:

```
/database/migrations
  001_init.sql
  002_add_orders.sql
```

Runner de migraciones (idea):

```php
function runMigrations(PDO $pdo, string $dir): void {
  $pdo->exec("CREATE TABLE IF NOT EXISTS _migrations (id TEXT PRIMARY KEY)");
  foreach (glob($dir . '/*.sql') as $file) {
    $id = basename($file);
    $applied = $pdo->query("SELECT 1 FROM _migrations WHERE id = '$id'")->fetchColumn();
    if ($applied) continue;
    $sql = file_get_contents($file);
    $pdo->beginTransaction();
    try {
      $pdo->exec($sql);
      $stmt = $pdo->prepare("INSERT INTO _migrations(id) VALUES(:id)");
      $stmt->execute([':id' => $id]);
      $pdo->commit();
    } catch (Throwable $e) {
      $pdo->rollBack();
      throw $e;
    }
  }
}

```

> Alternativas: **Phinx**, **Laravel Migrations**, **Doctrine Migrations** (todas soportan SQLite).

---

## 6) CRUD básico con prepared statements (PDO)

### Insert

```php
$stmt = $pdo->prepare('INSERT INTO users(email, name) VALUES(:email, :name)');
$stmt->execute([':email' => 'alice@example.com', ':name' => 'Alice']);
$userId = (int)$pdo->lastInsertId();
```

### Select

```php
$stmt = $pdo->prepare('SELECT id, email, name, created_at FROM users WHERE email = :email');
$stmt->execute([':email' => 'alice@example.com']);
$user = $stmt->fetch(); 
```

### Update

```php
$stmt = $pdo->prepare('UPDATE users SET name = :name WHERE id = :id');
$stmt->execute([':name' => 'Alice Liddell', ':id' => $userId]);
```

### Delete

```php
$stmt = $pdo->prepare('DELETE FROM users WHERE id = :id');
$stmt->execute([':id' => $userId]);
```

---

## 7) Transacciones y concurrencia

### 7.1 Transacción típica

```php
$pdo->beginTransaction();
try {
  // ... múltiples operaciones
  $pdo->commit();
} catch (Throwable $e) {
  $pdo->rollBack();
  throw $e;
}
```

### 7.2 Modos de journal y locking

- **WAL (Write-Ahead Logging)** mejora concurrencia (lecturas no bloquean escrituras).
```php
$pdo->exec('PRAGMA journal_mode = WAL');
$pdo->exec('PRAGMA synchronous = NORMAL'); 
$pdo->exec('PRAGMA busy_timeout = 5000');
```

- Para escrituras intensivas: `BEGIN IMMEDIATE;` o `BEGIN EXCLUSIVE;` según el caso.

### 7.3 Reintentos ante bloqueos

Implementa **retry** con backoff si recibes `database is locked`.

---

## 8) Rendimiento (PRAGMA y prácticas)

- `PRAGMA foreign_keys = ON` (siempre, garantiza integridad referencial).
- `PRAGMA journal_mode = WAL` (mejor concurrencia).
- `PRAGMA synchronous = NORMAL` (equilibrio rendimiento/durabilidad).
- `PRAGMA cache_size = -20000` (≈ 20MB; valor negativo = KB).
- `PRAGMA temp_store = MEMORY` para operaciones temporales.
- Usa **indices** para columnas filtradas/ordenadas frecuentes.
- Verifica planes: `EXPLAIN QUERY PLAN SELECT ...`.
- Evita N+1 con consultas adecuadas o joins.
- Agrupa escrituras dentro de **una transacción** (gran impacto).

---

## 9) Manejo de errores

### Con PDO (excepciones)

```php
try {
  $pdo->query('SELECT * FROM no_existe');
} catch (PDOException $e) {
  error_log($e->getMessage());
}
```

### Validaciones habituales

- `UNIQUE` -> capturar violaciones para devolver 409/422.
- `NOT NULL`/FK -> 400/422.
- Usa **tipos y afinidades** correctas (INTEGER, TEXT, REAL, BLOB).

---

## 10) Seguridad

- **Parámetros ligados** (prepared statements) SIEMPRE; nunca concatenes input.
- **Permisos de archivo** mínimos: el proceso PHP debe leer/escribir el `.db` y su journal.
- **Ruta segura** (fuera del docroot web).
- Sanitiza salida (XSS) y valida entrada (filtros/DTOs).
- Si necesitas cifrado en reposo: **SQLCipher** (requiere build/driver específico).

---

## 11) Backups, mantenimiento y salud

- Backup en frío: copiar `.db` con la app parada, o usa `VACUUM INTO 'backup.db'` en SQLite 3.27+.
- `PRAGMA integrity_check;` en tareas de mantenimiento.
- `VACUUM;` para defragmentar (puede bloquear; hazlo en ventana de mantenimiento).
- Monitorea tamaño de archivo y errores `locked`/`busy`.

---

## 12) Estructura de proyecto (sugerida)

```console
project/
  src/
    Db/
      Connection.php
      MigrationsRunner.php
    Repository/
      UserRepository.php
    Domain/
      User.php
  database/
    app.db         
    migrations/
      001_init.sql
  public/          
  scripts/
    migrate.php
  .env
```

**Connection.php (ejemplo):**

```php
<?php
final class Connection {
  public static function pdo(string $path): PDO {
    $pdo = new PDO('sqlite:' . $path, null, null, [
      PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
      PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
      PDO::ATTR_EMULATE_PREPARES => false,
    ]);
    $pdo->exec('PRAGMA foreign_keys = ON');
    $pdo->exec('PRAGMA journal_mode = WAL');
    $pdo->exec('PRAGMA synchronous = NORMAL');
    $pdo->exec('PRAGMA busy_timeout = 5000');
    return $pdo;
  }
}
```

---

## 13) Mini-API de repositorio (ejemplo)

```php
final class UserRepository {
  public function __construct(private PDO $pdo) {}

  public function create(string $email, string $name): int {
    $stmt = $this->pdo->prepare('INSERT INTO users(email, name) VALUES(:email, :name)');
    $stmt->execute([':email' => $email, ':name' => $name]);
    return (int)$this->pdo->lastInsertId();
  }

  public function findByEmail(string $email): ?array {
    $stmt = $this->pdo->prepare('SELECT * FROM users WHERE email = :email');
    $stmt->execute([':email' => $email]);
    $row = $stmt->fetch();
    return $row ?: null;
  }
}
```

## 📚 Referencias

### 🔹 Bases de Datos con PDO

- [PHP Manual – PDO](https://www.php.net/manual/es/book.pdo.php)  
- [PHP Manual – PDO::prepare](https://www.php.net/manual/es/pdo.prepare.php)  
- [PHP Manual – PDOStatement::execute](https://www.php.net/manual/es/pdostatement.execute.php)  

</div>