# <img src="../../images/coding-book.png" width="40"> Code & Learn (Php y las buenas prácticas)

## 📝 Estilo y legibilidad

- **PSR (PHP Standards Recommendations):** Sigue estándares como **PSR-1**, **PSR-2** (ahora reemplazado por **PSR-12**) para mantener un código consistente.  

> **Nota:** Adoptar PSR garantiza que cualquier persona que lea tu código entienda su estructura y formato sin sorpresas.

```php
function calcularImpuesto(float $precio, float $tasa): float {
    return $precio * $tasa;
}
```

- **Indentación y espacios:** Usa sangría consistente (4 espacios) y evita tabuladores mezclados.  

> **Nota:** La consistencia visual reduce errores y facilita las revisiones de código (diffs más limpios).

```php
// ❌ Malo
if($x==1){
echo "Hola";
}

//  Bueno
if ($x === 1) {
    echo "Hola";
}
```

- **Nombres claros:** Variables, funciones y clases deben tener nombres descriptivos (`$precioTotal`, `calcularImpuesto()`).  

> **Nota:** Nombres autoexplicativos reducen la necesidad de comentarios y aceleran el mantenimiento.

```php
$precioTotal = 100;
$impuesto = calcularImpuesto($precioTotal, 0.21);
```

- **Convenciones de mayúsculas:**  

> **Nota:** Usar `PascalCase`, `camelCase` y `UPPER_CASE` según el tipo hace el código predecible.

```php
class UsuarioRegistro {}     // PascalCase
$precioTotal = 100;          // camelCase
const IVA = 0.21;            // UPPER_CASE
```

---

## 🛠️ Organización del código

- **Modularidad:** Divide el código en funciones y clases reutilizables.  

> **Nota:** La modularidad permite testear, reutilizar y reemplazar piezas sin romper el sistema.

```php
function calcularDescuento($precio, $descuento) {
    return $precio - ($precio * $descuento);
}
```

- **Autoloading:** Usa `composer` y **PSR-4** para cargar clases automáticamente en lugar de `require` o `include`.  

> **Nota:** Evita `require` manuales, mantiene el árbol de dependencias claro y acelera la carga.

```json
{
  "autoload": {
    "psr-4": {
      "App\\": "src/"
    }
  }
}
```

- **Namespaces:** Organiza las clases con *namespaces* para evitar colisiones de nombres.  

> **Nota:** Los *namespaces* agrupan lógicamente y evitan choques de clases/funciones con terceros.

```php
namespace App\Model;

class Usuario {}
```

- **Evita código duplicado (DRY):**  

> **Nota:** Extrae lógica repetida a funciones/métodos; menos líneas, menos bugs y cambios centralizados.

```php
// ❌ Malo
$precioConIva = $precio * 1.21;
$precioConIva2 = $precio2 * 1.21;

//  Bueno
function aplicarIVA($precio) {
    return $precio * 1.21;
}
```

---

## 🔒 Seguridad

- **Escapar salidas:**  

> **Nota:** Escapa todo lo que venga del usuario antes de imprimirlo para prevenir XSS.

```php
echo htmlspecialchars($nombre, ENT_QUOTES, 'UTF-8');
```

- **Consultas preparadas (PDO):**  

> **Nota:** Los *prepared statements* separan datos de la consulta y bloquean inyecciones SQL.

```php
$stmt = $pdo->prepare("SELECT * FROM usuarios WHERE email = :email");
$stmt->execute(['email' => $email]);
```

- **Nunca confíes en el input del usuario:**  

> **Nota:** Valida y filtra; asume que todo input puede ser malicioso o inválido.

```php
$email = filter_var($_POST['email'] ?? '', FILTER_VALIDATE_EMAIL);
```

- **Gestión de errores:**  

> **Nota:** En producción oculta errores al usuario y registra en logs para diagnóstico seguro.

```php
ini_set('display_errors', 0);
ini_set('log_errors', 1);
ini_set('error_log', '/var/log/php_errors.log');
```

- **Contraseñas seguras:**  

> **Nota:** Nunca guardes contraseñas en texto plano ni con MD5/SHA1; usa *hashing* con *salt* y *cost*.

```php
$hash = password_hash("mi_password_seguro", PASSWORD_DEFAULT);
password_verify("mi_password_seguro", $hash);
```

- **Evita eval() y funciones peligrosas:**  

> **Nota:** `eval`, `exec`, `system` abren la puerta a RCE; evita su uso o aísla con controles estrictos.

```php
// ❌ Malo
eval($_GET['code']);

//  Bueno
// Evitarlo siempre, usar alternativas seguras.
```

---

## ⚡ Rendimiento

- **Usa caché:**  

> **Nota:** Cachear resultados costosos (config, consultas, vistas) reduce tiempos y carga de servidores.

```php
static $config;
if (!$config) {
    $config = obtenerConfiguracionDesdeBD();
}
```

- **Minimiza consultas a la BD:**  

> **Nota:** N+1 queries matan el rendimiento; agrupa, pagina y usa *joins* o *eager loading*.

```php
// ❌ Malo
foreach ($usuarios as $u) {
    $db->query("SELECT * FROM pedidos WHERE user_id = {$u['id']}");
}

//  Bueno (una sola consulta)
SELECT * FROM pedidos WHERE user_id IN (1,2,3,...);
```

- **Lazy loading:**  

> **Nota:** Carga bajo demanda para evitar traer datos que quizá no se usen.

```php
class Usuario {
    private $pedidos;

    public function getPedidos() {
        if ($this->pedidos === null) {
            $this->pedidos = cargarPedidosDesdeBD($this->id);
        }
        return $this->pedidos;
    }
}
```

- **Evita uso excesivo de include/require:**  

> **Nota:** Autoload mantiene dependencias organizadas y mejora el *bootstrapping*.

```php
// ❌ Malo
require 'Usuario.php';
require 'Pedido.php';

//  Bueno con Composer Autoload
require 'vendor/autoload.php';
```

---

##  Buenas prácticas de desarrollo

- **SOLID principles:**  

> **Nota:** Facilitan extensibilidad y pruebas; evita clases *Dios* y acoplamientos rígidos.

```php
class UsuarioRepository {
    public function guardar(Usuario $usuario) {}
}

class EmailService {
    public function enviarBienvenida(Usuario $usuario) {}
}
```

- **Tests (PHPUnit):**  

> **Nota:** Los tests previenen regresiones y documentan el comportamiento esperado.

```php
public function testSuma() {
    $this->assertEquals(4, 2 + 2);
}
```

- **Control de versiones (Git):**  

> **Nota:** Commits pequeños y descriptivos facilitan revertir y entender cambios.

```bash
git commit -m "fix: corregido bug en cálculo de impuestos"
```

- **Documentación con PHPDoc:**  

> **Nota:** PHPDoc ayuda a IDEs, *static analysis* y a los equipos a comprender las APIs internas.

```php
/**
 * Calcula el impuesto.
 * @param float $precio
 * @param float $tasa
 * @return float
 */
function calcularImpuesto(float $precio, float $tasa): float {
    return $precio * $tasa;
}
```

- **Entornos separados:**  

> **Nota:** Configuraciones por entorno previenen errores (p. ej., no enviar emails reales en testing).

```text
.env.development
.env.testing
.env.production
```

- **Linting y análisis estático:**  

> **Nota:** Detecta errores y *code smells* antes de ejecución; integra en CI.

```bash
vendor/bin/phpstan analyse src --level=max
```

---

## 🚀 Frameworks y herramientas recomendadas

- **Frameworks:**  

> **Nota:** Laravel y Symfony incorporan patrones, seguridad y utilidades listas para producción.

```text
Laravel, Symfony
```

- **Gestión de dependencias:**  

> **Nota:** Composer resuelve versiones, autoload y *scripts* de proyecto.

```bash
composer require guzzlehttp/guzzle
```

- **Linters y formateadores:**  

> **Nota:** Mantienen estilo consistente y detectan violaciones PSR automáticamente.

```bash
php-cs-fixer fix src
vendor/bin/phpcs src
```

- **CI/CD:**  

> **Nota:** Automatiza tests y despliegues para entregar cambios con confianza.

```yaml
name: PHP CI

on: [push]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Setup PHP
        uses: shivammathur/setup-php@v2
        with:
          php-version: '8.2'
      - name: Install dependencies
        run: composer install --no-interaction --prefer-dist
      - name: Static analysis
        run: vendor/bin/phpstan analyse src --level=max
      - name: Run PHPUnit
        run: vendor/bin/phpunit
```

</div>
