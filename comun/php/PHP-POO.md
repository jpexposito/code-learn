<div align="justify">

# <img src="../../images/coding-book.png" width="40"> Code & Learn (Clases y POO en PHP )

## Estructura y tipado

Un `namespace` en PHP es un “espacio de nombres”: una forma de agrupar clases, interfaces, funciones y constantes bajo un nombre común para evitar colisiones y organizar el código (como carpetas lógicas dentro del lenguaje).

Por qué existen:

- Evitan choques de nombres: puedes tener `App\User` y `Vendor\User` sin conflicto.
- Hacen el código más legible y facilitan el autoload (PSR-4).

### Comprobación estricta de tipos

`declare(strict_types=1);` activa comprobación estricta de tipos en archivos PHP.

```php
<?php

declare(strict_types=1);

namespace App\Domain;

final class User {

  public function __construct(
    public readonly int $id,
    private string $name
  ) {}

  public function name(): string { return $this->name; }
}
```

### Ejercicio rápido

Crea `src/Domain/Email.php` con una propiedad privada tipada `string $value`, un validador en el constructor y un método `__toString()`.

<details>
  <summary>Ayuda en el ejercicio</summary>


</details>

## Visibilidad y miembros

`public` / `protected` / `private` en propiedades, métodos y constantes.

**Miembros estáticos** y **promoción en el constructor** (8.0+) para definir propiedades en la firma.

```php
<?php
declare(strict_types=1);

namespace App\Domain;

final class Counter {
  public const MAX = 10;           
  private static int $global = 0;  

  public function __construct(private int $value = 0) {}

  public static function global(): int { return self::$global; }
  public static function bump(): void { self::$global++; }

  public function inc(): void {
    if ($this->value >= self::MAX) throw new \RuntimeException('MAX reached');
    $this->value++;
  }

  public function value(): int { return $this->value; }
}
```

### Mutabilidad

- `const`: **inmutable**. No se puede cambiar en tiempo de ejecución.
- `static $global`: **mutable**. Puedes leer/escribir su valor.

### Acceso

- `const`: `Clase::MAX`, `self::MAX`, `static::MAX`.
- `static`: `Clase::$global`, `self::$global`, `static::$global`.

### Ámbito / vida

- **Ambas** pertenecen a la **clase** (no a instancias).  
- La **estática** es **una única copia por clase** y se reinicia en cada petición/ejecución (PHP no es *long-running* por defecto en web).

### Visibilidad

- **Constantes** (desde PHP 7.1): `public` / `protected` / `private`.
- **Propiedades estáticas**: `public` / `protected` / `private`.

### Herencia

- **Constantes**: se heredan; desde PHP 8.1 puedes marcarlas `final` para evitar “sobrescritura”.
- **Estáticas**: se heredan; si una subclase las **redeclara**, es **otro almacenamiento distinto**.

### Uso típico

- `const`: valores **fijos** definidos en código (límites, *flags*, nombres de tabla, etc.).
- `static`: **estado compartido** entre métodos/instancias de la clase (contadores, *caches* internas).

---

### Ejercicio rápido

Añade método `from(int $seed): self` estático que inicialice `value` desde `$seed` sin permitir valores `< 0`.

<details>
  <summary>Ayuda en el ejercicio</summary>
</details>

## Ciclo de vida y “magia” de objetos

`__construct` / `__destruct`.

**Clonado**: `clone $obj` + `__clone()` para copia profunda.

**Métodos mágicos**: `__get/__set/__isset/__unset`, `__call/__callStatic`, `__toString`, `__invoke`, `__serialize/__unserialize`.

```php
<?php
declare(strict_types=1);

namespace App\Domain;

final class Profile implements \Stringable {
  public function __construct(
    private array $data = ['name' => 'Anon', 'tags' => []]
  ) {}

  /**
   *  acceso controlado a propiedades "virtuales"
   */ 
  public function __get(string $key): mixed {
    return $this->data[$key] ?? null;
  }

  public function __set(string $key, mixed $val): void {
    if ($key === 'name' && $val === '') throw new \InvalidArgumentException('Valor vacio');
    $this->data[$key] = $val;
  }

  /**
  *copia profunda de arrays/objetos embebidos
  */
  public function __clone(): void {
    $this->data = unserialize(serialize($this->data)); 
  }

  public function __toString(): string {
    return sprintf('%s [%d tags]', $this->data['name'] ?? 'Anon', count($this->data['tags'] ?? []));
  }
}
```

Ejemplo de uso

```php
$profile = new Profile();
$p->name = 'Ana';
$profile1->tags = ['php','poo'];
$profile2 = clone $profile1;
$profile2->tags[] = 'tests';

echo $profile1; 
echo $profile2; 
```

_¿Qué obtengo?_

## Herencia y polimorfismo

`extends` y `final` (sellar clases/métodos).

**Late Static Binding**: `static::` vs `self::` (clases heredadas).

**Covarianza/Contravarianza** (7.4+): retornos más específicos, parámetros más generales.

```php
<?php
declare(strict_types=1);

namespace App\Domain\Shapes;

abstract class Shape {
  abstract public function area(): float;
}

final class Circle extends Shape {
  public function __construct(private float $r) {}
  public function area(): float { return M_PI * $this->r * $this->r; }
}

final class Rect extends Shape {
  public function __construct(private float $w, private float $h) {}
  public function area(): float { return $this->w * $this->h; }
}

function totalArea(Shape ...$shapes): float {
  return array_reduce($shapes, fn($a, $s) => $a + $s->area(), 0.0);
}
```

__Pregunta:__ _¿Dónde esta definido r?_.

### Late Static Binding (LSB)

```php
abstract class BaseFactory {
  public static function make(): static { return new static(); } 
}
final class SpecialFactory extends BaseFactory {}
$one = BaseFactory::make();         // instancia BaseFactory
$two = SpecialFactory::make();      // instancia SpecialFactory (¡LSB!)
```

`Late Static Binding` (LSB) en PHP es el mecanismo que hace que las referencias estáticas se resuelvan según la clase que invoca (en tiempo de ejecución) y no según la clase donde el método fue declarado.
Se usa con static:: (y con el tipo de retorno static) y contrasta con self::, que `siempre apunta a la clase donde está escrito el método`.

_¿Por qué importa?_

Para que métodos estáticos heredados (p. ej., factories, builders, fluent APIs) creen/usen la subclase correcta cuando se llaman desde ella.

### Ejercicio rápido

Crea una jerarquía `Transport` → `Car`, `Bike` con método `maxSpeed(): int`. Implementa `Fleet::fastest(array $ts): Transport`.

---

## Contratos y reutilización

### Interfaces

Definen contratos formales.

```php
<?php
declare(strict_types=1);

namespace App\Domain\Time;

interface Clock { public function now(): \DateTimeImmutable; }

final class SystemClock implements Clock {
  public function now(): \DateTimeImmutable { return new \DateTimeImmutable(); }
}
```

### Clases abstractas

Comparten base + fuerzan implementación.

```php
abstract class Notifier {
  final public function send(string $to, string $msg): void {
    $this->beforeSend($to, $msg);
    $this->doSend($to, $msg);
  }
  protected function beforeSend(string $to, string $msg): void {}
  abstract protected function doSend(string $to, string $msg): void;
}
```

### Traits

Reutiliza lógica sin herencia múltiple.

```php
trait HasTimestamps {
  private \DateTimeImmutable $createdAt;
  private \DateTimeImmutable $updatedAt;

  protected function bootTimestamps(): void {
    $this->createdAt = new \DateTimeImmutable();
    $this->updatedAt = $this->createdAt;
  }
  protected function touch(): void { $this->updatedAt = new \DateTimeImmutable(); }
  public function createdAt(): \DateTimeImmutable { return $this->createdAt; }
  public function updatedAt(): \DateTimeImmutable { return $this->updatedAt; }
}
```

### Enums (8.1+) para estados seguros

```php
enum Status: string { case Draft='draft'; case Published='pub'; }
```

## Atributos y reflexión

**Attributes** (8.0+) son metadatos nativos.

```php
#[\Attribute]
class Auditable { public function __construct(public string $scope) {} }

#[Auditable('domain')]
final class Order {}

// Leer con Reflection:
$rc = new \ReflectionClass(Order::class);
$attrs = $rc->getAttributes(Auditable::class);
$meta  = $attrs[0]->newInstance();          // Auditable(scope: 'domain')
```

_Los atributos (PHP 8+) son metadatos nativos que puedes “pegar” a clases, métodos, propiedades, funciones, etc. Piensa en ellos como etiquetas tipadas que el código puede leer en tiempo de ejecución con Reflection para cambiar el comportamiento o configurar cosas (rutas, validaciones, permisos, etc.)._

_No son comentarios: son objetos reales (clases) con constructor, tipos y validación._

_Se usan con la sintaxis #[...] justo antes del elemento al que decoran_.

## Excepciones y errores

Usa jerarquía SPL (`\RuntimeException`, `\InvalidArgumentException`, …) + excepciones propias.

Maneja con `try/catch/finally`; evita códigos de error mágicos.

```php
class DomainError extends \RuntimeException {}
final class Money {
  public function __construct(private int $cents) {
    if ($cents < 0) throw new DomainError('Negative amount');
  }
}
try { new Money(-10); } catch (DomainError $e) { /* log + respuesta */ }
```

## Buenas prácticas de diseño (resumen accionable)

- Encapsulación y Value Objects inmutables para valores (Email, Money).
- Composición sobre herencia; evita herencias profundas.
- SOLID + inyección de dependencias (depende de interfaces).
- Repositorios para persistencia; evita Singletons.
- DTOs/ViewModels para transportar datos; Factories para creación controlada.

**Ejemplo breve (Repo + Servicio)**

```php
interface UserRepository { public function save(User $u): void; public function byEmail(string $e): ?User; }

final class InMemoryUserRepository implements UserRepository {
  /** @var array<string,User> */ private array $byEmail = [];
  public function save(User $u): void { $this->byEmail[$u->jsonSerialize()['email']] = $u; }
  public function byEmail(string $e): ?User { return $this->byEmail[$e] ?? null; }
}

final class UserService {
  public function __construct(private UserRepository $repo) {}
  public function register(int $id, string $name, string $email): User {
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) throw new \InvalidArgumentException('Email inválido');
    $u = new \App\Domain\User($id, $name, $email);
    $this->repo->save($u);
    return $u;
  }
}
```

## Herramientas del lenguaje que potencian POO

`match`, null-safe `?->`, `??/??=`, *named arguments*.

**Tipos de retorno** `self` / `static` (fluidez preservando herencia).

```php
final class Builder {
  private array $data = [];
  public function with(string $k, mixed $v): static { $this->data[$k]=$v; return $this; }
  public function build(): array { return $this->data; }
}

$b = (new Builder())->with('name','Ana')->with('age',30)->build();
```

## Mini-patrones (1 línea + ejemplo conciso)

- **Factory**: `User::fromArray($data)`.
- **Strategy**: `PasswordHasher` con `BcryptHasher` / `ArgonHasher`.
- **Repository**: `UserRepository->byEmail($email)`.
- **Specification**: `IsAdultSpec` con método `isSatisfiedBy(User $u): bool`.
- **Decorator**: `CachingUserRepository` envuelve a otro repo y cachea.

```php
/**
 * 
 */ 
final class CachingUserRepository implements UserRepository {
  private array $cache = [];
  public function __construct(private UserRepository $inner) {}
  public function save(User $u): void { $this->inner->save($u); $this->cache[$u->jsonSerialize()['email']] = $u; }
  public function byEmail(string $e): ?User {
    return $this->cache[$e] ??= $this->inner->byEmail($e);
  }
}
```

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