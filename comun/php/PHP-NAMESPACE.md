<div align="justify">

# <img src="../../images/coding-book.png" width="40"> Code & Learn (Clases y namespace en PHP )

## Estructura y tipado

Un **namespace** (espacio de nombres) en PHP sirve para **organizar y agrupar clases, funciones y constantes**, evitando colisiones de nombres entre diferentes partes de un proyecto o entre librerías externas.

Piensa en los *namespaces* como "carpetas virtuales" en tu código: puedes tener varias clases con el mismo nombre, siempre que estén en *namespaces* distintos.

---

## Ejemplo sin namespace (problema de colisión)

```php
<?php
// archivo1.php
class Usuario {
    public function saludar() {
        echo "Hola desde archivo1";
    }
}

// archivo2.php
class Usuario {
    public function saludar() {
        echo "Hola desde archivo2";
    }
}

include 'archivo1.php';
include 'archivo2.php';

// Error: Cannot redeclare class Usuario
$u = new Usuario();
```
---

## Ejemplo con namespace (solución)

```php
<?php
// archivo1.php
namespace App\Modelo;

class Usuario {
    public function saludar() {
        echo "Hola desde App\Modelo\Usuario";
    }
}

// archivo2.php
namespace App\Controller;

class Usuario {
    public function saludar() {
        echo "Hola desde App\Controller\Usuario";
    }
}

// index.php
include 'archivo1.php';
include 'archivo2.php';

// Usando el namespace completo:
$u1 = new App\Modelo\Usuario();
$u1->saludar();  // Hola desde App\Modelo\Usuario

$u2 = new App\Controller\Usuario();
$u2->saludar();  // Hola desde App\Controller\Usuario
```
---

## Alias con `use`

```php
<?php
use App\Modelo\Usuario as UsuarioModelo;
use App\Controller\Usuario as UsuarioControlador;

$u1 = new UsuarioModelo();
$u2 = new UsuarioControlador();

$u1->saludar();
$u2->saludar();
```

---

## 📂 ¿El namespace debe coincidir con la estructura de carpetas?

- **PHP puro:**  
  No es obligatorio. Puedes declarar un namespace cualquiera, sin importar en qué carpeta esté el archivo.  
  ___Pero esto puede volver tu proyecto difícil de mantener__.

- **Buenas prácticas / Composer (PSR-4):**  
  Lo recomendado (y prácticamente necesario si usas **Composer**) es que el namespace coincida con la estructura de carpetas.  
  Esto se hace con la regla **PSR-4** en el `composer.json`:

```json
{
  "autoload": {
    "psr-4": {
      "App\\": "src/"
    }
  }
}
```

Ejemplo de estructura:

```code
src/
 └── Modelo/
     └── Usuario.php
```

```php
<?php
// src/Modelo/Usuario.php
namespace App\Modelo;

class Usuario {
    public function saludar() {
        echo "Hola desde App\Modelo\Usuario";
    }
}
```

Composer cargará esta clase automáticamente porque el namespace `App\Modelo` apunta a la carpeta `src/Modelo`.

## Ejemplo de uso

Este ejemplo explica **cómo evoluciona** una clase monolítica que mezcla lógica, I/O y persistencia hacia un **diseño en capas con namespaces**.

Tenemos el siguiente clase `partida.php`

```php
final class Partida
{
    private string $id;
    private string $palabra;
    private array $aciertos = [];
    private array $fallos = [];
    private int $maxIntentos = 7;
    private string $estado = 'EN_CURSO';
    private string $rutaDatos;

    public function __construct(string $palabra, string $rutaDatos)
    {
        $this->id = bin2hex(random_bytes(4));
        $this->palabra = mb_strtolower($palabra);
        $this->rutaDatos = $rutaDatos;
        $this->guardar(); 
    }

    /**
     * Logica de dominio + validaciones + efecto de E/S todo aqui
     */ 
    public function probarLetra(string $letra): bool
    {
        if ($this->estado !== 'EN_CURSO') {
            echo "[AVISO] La partida ya terminó.\n"; 
            return false;
        }
        $letra = mb_strtolower(trim($letra));
        if ($letra === '' || mb_strlen($letra) !== 1 || !ctype_alpha($letra)) {
            echo "[ERROR] Entrada inválida.\n";
            return false;
        }
        if (in_array($letra, $this->aciertos, true) || in_array($letra, $this->fallos, true)) {
            echo "[INFO] Letra repetida.\n"; 
            return false;
        }

        if (mb_strpos($this->palabra, $letra) !== false) {
            $this->aciertos[] = $letra;
            echo "✅ ¡Acertaste!\n"; 
        } else {
            $this->fallos[] = $letra;
            echo "❌ Fallo.\n";
        }
        $this->refrescarEstado();
        $this->guardar();
        return mb_strpos($this->palabra, $letra) !== false;
    }

    public function getMascara(): string
    {
        $out = '';
        foreach (mb_str_split($this->palabra) as $ch) {
            $out .= in_array($ch, $this->aciertos, true) ? $ch : '_';
        }
        return $out;
    }

    public function getIntentosRestantes(): int
    {
        return $this->maxIntentos - count($this->fallos);
    }

    public function getEstado(): string
    {
        return $this->estado;
    }

    public function getId(): string
    {
        return $this->id;
    }

    /**
     * Funcion que guarda en un fichero la información
     */
    private function guardar(): void
    {
        $base = dirname($this->rutaDatos);
        if (!is_dir($base)) {
            @mkdir($base, 0777, true);
        }
        $data = is_file($this->rutaDatos)
            ? json_decode((string)file_get_contents($this->rutaDatos), true)
            : ['games' => []];

        $data['games'][$this->id] = [
            'id'       => $this->id,
            'palabra'  => $this->palabra,
            'aciertos' => $this->aciertos,
            'fallos'   => $this->fallos,
            'max'      => $this->maxIntentos,
            'estado'   => $this->estado,
        ];

        file_put_contents($this->rutaDatos, json_encode($data, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE));

    }

    /**
     * Funcion que refresca el estado de la partida
     */ 
    private function refrescarEstado(): void
    {
        if ($this->getMascara() === $this->palabra) {
            $this->estado = 'GANADA';
        } elseif ($this->getIntentosRestantes() <= 0) {
            $this->estado = 'PERDIDA';
        }
    }
}
```

Como podemos observar, las funciones y responsabilidades se encuentran mezcladas, almacenando en única clase los datos e funciones de la clase.

Partimos de una clase `Partida` _(Monolitica)_ que **simula una partida** y **mezcla**:

- Lógica de dominio (reglas de adivinar letras, máscara, estado).  
- Persistencia (lectura/escritura JSON a disco).  
- I/O de presentación (mensajes por consola con `echo`).

La refactorización separa estas responsabilidades en **capas** y **namespaces**:

- **Domain**: Reglas del negocio y estado.  
- **Infrastructure**: Implementaciones técnicas (p. ej., JSON en disco).  
- **Application**: Casos de uso (orquestación de dominio + repositorios).  
- **Presentation**: Entrada/salida (controladores, vistas, CLI/HTTP).

---

### Clase Partida en su versión monolítica

**`PartidaMonolitica`** (sin namespace)

- **Responsabilidades mezcladas**:
  - *Dominio*: `probarLetra`, `getMascara`, cálculo de intentos y estado.
  - *Persistencia*: método `guardar()` abre/escribe un JSON (ruta fija pasada al constructor).
  - *Presentación*: imprime mensajes (errores, aciertos) con `echo`.
- **Problemas**:
  - Dificulta pruebas unitarias (acoplamiento a disco y a stdout).
  - Cambiar el medio de persistencia obliga a tocar la clase de dominio.
  - No es reutilizable desde otros frontends (web/CLI/API) sin modificar la clase.

---

### Capas y namespaces _(Objetivo de la conversión)_

#### **Domain** — Reglas del negocio

**Namespace**: `App\Domain`

- `EstadoPartida` *(enum)*: `EN_CURSO`, `GANADA`, `PERDIDA`.
- `PalabraSecreta` *(Value Object)*: normaliza y conoce la palabra; revela máscara con letras acertadas.
- `Partida` *(Entidad)*: estado (aciertos, fallos, intentos, estado), reglas `probarLetra`, serialización `toArray()/fromArray()`.

> **No hay I/O** (no escribe archivos ni hace `echo`). Es puro dominio.

#### **Domain\Repository** — Contratos

**Namespace**: `App\Domain\Repository`

- `RepositorioPartida` *(interfaz)*: `guardar(Partida)`, `buscar(id)`.

> Permite **sustituir** la persistencia sin cambiar el dominio.

#### **Infrastructure** — Implementaciones técnicas

**Namespace**: `App\Infrastructure`

- `RepositorioPartidaArchivo` *(implementa `RepositorioPartida`)*: lectura/escritura JSON con **escritura atómica** (tmp + rename).

> Aquí vive el **cómo** se guardan los datos (disco, sesión, BBDD, etc.).

#### **Application** — Casos de uso

**Namespace**: `App\Application`

- `ServicioPartida`: orquesta el flujo de casos de uso:
  - `nueva(string $palabra): string`
  - `probar(string $id, string $letra): array`
  - `estado(string $id): array`

> **No renderiza**; devuelve DTOs/arrays que la capa de presentación transforma en UI.

#### **Presentation** — Orquestación I/O

**Namespace**: `App\Presentation`

- `ControladorPartida` (demo): muestra cómo invocar el servicio desde un controlador (CLI o HTTP).

> **Único lugar** con I/O de usuario (p. ej., `echo` en esta demo).

---

### Diagrama conceptual (simplificado)

```code
Presentation  →  Application  →  Domain  →  (interfaces)  ←  Infrastructure
(Controller)     (Services)      (Entities, VOs)          (Implementations)

           Entrada/Salida      Reglas puras      Contratos       Persistencia
```

**Regla de dependencia**: cada capa solo depende de la **inmediata inferior**; el dominio **no depende** de infra.

---

### Flujo de una operación (refactor)

1. `ControladorPartida` recibe la entrada (ej. letra).  
2. Llama a `ServicioPartida->probar(id, letra)`.  
3. El servicio carga la entidad con `RepositorioPartida->buscar(id)`.  
4. Aplica `Partida->probarLetra(letra)` (dominio puro).  
5. Persiste con `RepositorioPartida->guardar(partida)`.  
6. Devuelve un DTO/array con `máscara`, `intentosRestantes`, `fallos`, `estado`.  
7. La presentación muestra el resultado (CLI/HTML/API).

---

### Comparativa rápida

| Aspecto | Monolítico | Capas (refactor) |
|---|---|---|
| Testeabilidad | Baja (I/O mezclado) | Alta (dominio puro sin I/O) |
| Cambio de persistencia | Rompe la clase | Cambias solo la implementación de `RepositorioPartida` |
| Reuso en varios frontends | Difícil | Sencillo (Presentation reemplazable) |
| Legibilidad | Baja (responsabilidades mezcladas) | Alta (cada clase hace una cosa) |
| Mantenibilidad | Baja | Alta |

### Guía de transformación

| Responsabilidad original | Método/propiedad | Capa refactor | Clase / Namespace | Equivalente en refactor |
|---|---|---|---|---|
| Generar id | `__construct` (genera), `getId()` | Aplicación (genera), Dominio (almacena) | App\Application\ServicioPartida · App\Domain\Partida | `ServicioPartida::nueva()` crea id → `Partida::__construct($id, PalabraSecreta, $max)`; `Partida::id()` |
| Palabra normalizada | `$palabra` en `__construct` | Dominio | App\Domain\PalabraSecreta (+ Partida la usa) | `new PalabraSecreta($palabra)`; `PalabraSecreta::valor()` |
| Aciertos/Fallos | `$aciertos`, `$fallos` | Dominio | App\Domain\Partida | Estado interno; `Partida::fallos()` |
| Máximo de intentos | `$maxIntentos` | Dominio (guarda) / Aplicación (decide) | App\Domain\Partida · App\Application\ServicioPartida | Se pasa desde el servicio al constructor |
| Estado | `$estado`, `getEstado()` | Dominio | App\Domain\EstadoPartida (enum) · App\Domain\Partida | `Partida::estado(): EstadoPartida` |
| Ruta de datos | `$rutaDatos` | Infraestructura | App\Infrastructure\RepositorioPartidaArchivo | Se elimina del dominio; vive en el repo |
| Guardar al crear | `__construct` → `guardar()` | Aplicación + Infra | App\Application\ServicioPartida · App\Infrastructure\RepositorioPartidaArchivo | `ServicioPartida::nueva()` + `$repo->guardar($p)` |
| Probar letra (reglas) | `probarLetra()` | Dominio | App\Domain\Partida | `Partida::probarLetra()` sin `echo` ni I/O |
| Probar letra (mensajes) | `probarLetra()` hace `echo` | Presentación | App\Presentation\ControladorPartida | La UI decide qué mostrar |
| Probar letra (persistir) | `probarLetra()` → `guardar()` | Aplicación + Infra | App\Application\ServicioPartida · App\Infrastructure\RepositorioPartidaArchivo | `ServicioPartida::probar()` + `$repo->guardar($p)` |
| Máscara | `getMascara()` | Dominio | App\Domain\PalabraSecreta · App\Domain\Partida | `Partida::mascara()` delega en `PalabraSecreta::revelar()` |
| Intentos restantes | `getIntentosRestantes()` | Dominio | App\Domain\Partida | `Partida::intentosRestantes()` |
| Id | `getId()` | Dominio | App\Domain\Partida | `Partida::id()` |
| Guardar a disco | `guardar()` | Infraestructura | App\Infrastructure\RepositorioPartidaArchivo | `guardar(Partida)` + helpers de I/O |
| Refrescar estado | `refrescarEstado()` | Dominio | App\Domain\Partida | Privado, usa `PalabraSecreta::completaCon()` |
| Serialización | — | Dominio (forma), Infra (uso) | App\Domain\Partida · App\Infrastructure\RepositorioPartidaArchivo | `toArray()` / `fromArray()` |
| Búsqueda/carga | — | Infraestructura | App\Infrastructure\RepositorioPartidaArchivo | `buscar(string $id): ?Partida` |
| Orquestación | — | Aplicación | App\Application\ServicioPartida | `nueva()`, `probar()`, `estado()` |
| Control de flujo/UI | `echo` | Presentación | App\Presentation\ControladorPartida | `demo()`/`handle()` |

---

<details>
    <summary>Transformación</summary>

```php
namespace App\Domain {
    enum EstadoPartida: string { case EN_CURSO='EN_CURSO'; case GANADA='GANADA'; case PERDIDA='PERDIDA'; }

    final class PalabraSecreta
    {
        public function __construct(private string $valor) {
            $this->valor = mb_strtolower($valor);
        }
        public function contiene(string $letra): bool {
            $letra = mb_strtolower(trim($letra));
            return $letra !== '' && mb_strlen($letra) === 1 && ctype_alpha($letra)
                && mb_strpos($this->valor, $letra) !== false;
        }
        public function revelar(array $letras): string {
            $out = '';
            foreach (mb_str_split($this->valor) as $ch) {
                $out .= in_array($ch, $letras, true) ? $ch : '_';
            }
            return $out;
        }
        public function completaCon(array $letras): bool {
            return $this->revelar($letras) === $this->valor;
        }
        public function valor(): string { return $this->valor; }
    }

    final class Partida
    {
        /** @var string[] */
        private array $aciertos = [];
        /** @var string[] */
        private array $fallos = [];
        private EstadoPartida $estado;

        public function __construct(
            private string $id,
            private PalabraSecreta $palabra,
            private int $maxIntentos = 7
        ) { $this->estado = EstadoPartida::EN_CURSO; }

        public function id(): string { return $this->id; }
        public function estado(): EstadoPartida { return $this->estado; }
        public function mascara(): string { return $this->palabra->revelar($this->aciertos); }
        public function intentosRestantes(): int { return $this->maxIntentos - count($this->fallos); }
        /** @return string[] */
        public function fallos(): array { return $this->fallos; }

        public function probarLetra(string $letra): bool
        {
            if ($this->estado !== EstadoPartida::EN_CURSO) return false;
            $letra = mb_strtolower(trim($letra));
            if ($letra === '' || mb_strlen($letra) !== 1 || !ctype_alpha($letra)) return false;
            if (in_array($letra, $this->aciertos, true) || in_array($letra, $this->fallos, true)) return false;

            if ($this->palabra->contiene($letra)) {
                $this->aciertos[] = $letra;
            } else {
                $this->fallos[] = $letra;
            }
            $this->refrescarEstado();
            return $this->palabra->contiene($letra);
        }

        private function refrescarEstado(): void
        {
            if ($this->palabra->completaCon($this->aciertos)) {
                $this->estado = EstadoPartida::GANADA;
            } elseif ($this->intentosRestantes() <= 0) {
                $this->estado = EstadoPartida::PERDIDA;
            }
        }

        public function toArray(): array
        {
            return [
                'id'       => $this->id,
                'palabra'  => $this->palabra->valor(),
                'aciertos' => $this->aciertos,
                'fallos'   => $this->fallos,
                'max'      => $this->maxIntentos,
                'estado'   => $this->estado->value,
            ];
        }

        public static function fromArray(array $a): self
        {
            $p = new self($a['id'], new PalabraSecreta($a['palabra']), (int)($a['max'] ?? 7));
            $p->aciertos = $a['aciertos'] ?? [];
            $p->fallos   = $a['fallos']   ?? [];
            $p->estado   = EstadoPartida::from($a['estado'] ?? 'EN_CURSO');
            return $p;
        }
    }
}

namespace App\Domain\Repository {
    use App\Domain\Partida;

    interface RepositorioPartida {
        public function guardar(Partida $partida): void;
        public function buscar(string $id): ?Partida;
    }
}

namespace App\Infrastructure {
    use App\Domain\Partida;
    use App\Domain\Repository\RepositorioPartida;

    final class RepositorioPartidaArchivo implements RepositorioPartida
    {
        public function __construct(private string $ruta) {
            if (!is_file($this->ruta)) {
                @mkdir(dirname($this->ruta), 0777, true);
                file_put_contents($this->ruta, json_encode(['games'=>[]], JSON_PRETTY_PRINT|JSON_UNESCAPED_UNICODE));
            }
        }
        public function guardar(Partida $partida): void {
            $data = $this->leer();
            $data['games'][$partida->id()] = $partida->toArray();
            $this->escribir($data);
        }
        public function buscar(string $id): ?Partida {
            $data = $this->leer();
            return isset($data['games'][$id]) ? Partida::fromArray($data['games'][$id]) : null;
        }
        private function leer(): array {
            $c = @file_get_contents($this->ruta);
            $j = $c ? json_decode($c, true) : null;
            return is_array($j) ? $j : ['games'=>[]];
        }
        private function escribir(array $d): void {
            $tmp = $this->ruta . '.tmp';
            file_put_contents($tmp, json_encode($d, JSON_PRETTY_PRINT|JSON_UNESCAPED_UNICODE));
            rename($tmp, $this->ruta);
        }
    }
}

namespace App\Application {
    use App\Domain\Partida;
    use App\Domain\PalabraSecreta;
    use App\Domain\Repository\RepositorioPartida;

    final class ServicioPartida
    {
        public function __construct(private RepositorioPartida $repo, private int $max = 7) {}

        public function nueva(string $palabra): string
        {
            $id = bin2hex(random_bytes(4));
            $p = new Partida($id, new PalabraSecreta($palabra), $this->max);
            $this->repo->guardar($p);
            return $id;
        }

        public function probar(string $id, string $letra): array
        {
            $p = $this->cargar($id);
            $p->probarLetra($letra);
            $this->repo->guardar($p);
            return $this->dto($p);
        }

        public function estado(string $id): array
        {
            $p = $this->cargar($id);
            return $this->dto($p);
        }

        private function cargar(string $id): Partida
        {
            $p = $this->repo->buscar($id);
            if (!$p) {
                throw new \RuntimeException('Partida no encontrada');
            }
            return $p;
        }

        private function dto(Partida $p): array
        {
            return [
                'id' => $p->id(),
                'mascara' => $p->mascara(),
                'intentosRestantes' => $p->intentosRestantes(),
                'fallos' => $p->fallos(),
                'estado' => $p->estado()->value,
            ];
        }
    }
}

namespace App\Presentation {
    use App\Application\ServicioPartida;

    /**
     * Controlador mínimo de ejemplo (sin HTTP real).
     * Solo muestra cómo orquestar llamadas desde "presentación".
     */
    final class ControladorPartida
    {
        public function __construct(private ServicioPartida $servicio) {}

        public function demo(): void
        {
            echo "=== DEMO REFACTORIZADO ===\n";
            $id = $this->servicio->nueva('patron');
            echo "ID: $id\n";

            foreach (['a','x','t','o','r','n','p'] as $letra) {
                $estado = $this->servicio->probar($id, $letra);
                echo "Probar '$letra' => máscara: {$estado['mascara']} | fallos: [" . implode(',', $estado['fallos']) . "] | intentos: {$estado['intentosRestantes']} | estado: {$estado['estado']}\n";
                if ($estado['estado'] !== 'EN_CURSO') break;
            }
            echo "=== FIN DEMO ===\n\n";
        }
    }
}

```

</details>

## Recuerda

- Los *namespaces* permiten **organizar código y evitar conflictos de nombres**.  
- **No es obligatorio** que coincidan con la estructura de archivos, pero **es altamente recomendable**.  
- En proyectos modernos con Composer y PSR-4, **sí deben coincidir** para que el autoload funcione correctamente.  

## 📚 Referencias

A continuación tienes enlaces a la documentación oficial de PHP, con ejemplos prácticos de cada uno de los temas vistos en **Code & Learn (PHP en 5 días)**:

### 🔹 Introducción y Sintaxis Básica

- [PHP Manual – Sintaxis básica](https://www.php.net/manual/es/language.basic-syntax.php)
- [PHP Manual – echo / print](https://www.php.net/manual/es/function.echo.php)

### 🔹 Operadores y Control de Flujo

- [PHP Manual – Operadores](https://www.php.net/manual/es/language.operators.php)  
- [PHP Manual – Estructuras de control](https://www.php.net/manual/es/language.control-structures.php)  
- Ejemplos: `if`, `else`, `elseif`, `switch`, `for`, `foreach`, `while`, `do...while`.

### 🔹 Funciones

- [PHP Manual – Funciones](https://www.php.net/manual/es/language.functions.php)  
- [PHP Manual – Argumentos de funciones](https://www.php.net/manual/es/functions.arguments.php)  
- Incluye ejemplos de parámetros opcionales y paso por referencia.

### 🔹 Arrays

- [PHP Manual – Arrays](https://www.php.net/manual/es/language.types.array.php)  
- [PHP Manual – Funciones de Arrays](https://www.php.net/manual/es/ref.array.php)  
- Ejemplos de arrays indexados, asociativos y multidimensionales.

### 🔹 Bucles

- [PHP Manual – while](https://www.php.net/manual/es/control-structures.while.php)  
- [PHP Manual – do...while](https://www.php.net/manual/es/control-structures.do.while.php)  
- [PHP Manual – for](https://www.php.net/manual/es/control-structures.for.php)  
- [PHP Manual – foreach](https://www.php.net/manual/es/control-structures.foreach.php)  

### 🔹 Manejo de Formularios

- [PHP Manual – Superglobals](https://www.php.net/manual/es/language.variables.superglobals.php)  
- [PHP Manual – $_GET](https://www.php.net/manual/es/reserved.variables.get.php)  
- [PHP Manual – $_POST](https://www.php.net/manual/es/reserved.variables.post.php)  

### 🔹 Manejo de Archivos

- [PHP Manual – Manejo de archivos](https://www.php.net/manual/es/book.filesystem.php)  
- Funciones clave: [`fopen`](https://www.php.net/manual/es/function.fopen.php), [`fwrite`](https://www.php.net/manual/es/function.fwrite.php), [`fread`](https://www.php.net/manual/es/function.fread.php), [`fclose`](https://www.php.net/manual/es/function.fclose.php).  

### 🔹 Bases de Datos con PDO

- [PHP Manual – PDO](https://www.php.net/manual/es/book.pdo.php)  
- [PHP Manual – PDO::prepare](https://www.php.net/manual/es/pdo.prepare.php)  
- [PHP Manual – PDOStatement::execute](https://www.php.net/manual/es/pdostatement.execute.php)  

</div>