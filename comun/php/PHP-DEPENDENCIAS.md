<div align="justify">

# <img src="../../images/coding-book.png" width="40"> Code & Learn (PHP y dependencias)

# ¿Qué es Composer?

**Composer** es el **gestor de dependencias de PHP**. Te permite:

- **Declarar** qué librerías necesita tu proyecto (en `composer.json`).
- **Resolver e instalar** versiones compatibles (descarga de *Packagist*).
- **Generar *autoload*** para usar clases sin `require` manuales (`vendor/autoload.php`).
- **Bloquear versiones** en `composer.lock` para reproducibilidad entre entornos.
- **Ejecutar scripts** de atajo (por ejemplo `composer test`).
- **Auditar** vulnerabilidades (`composer audit`).

---

## Archivos clave

- **`composer.json`**: lo editas tú. Define dependencias, *autoload*, *scripts*, etc.
- **`composer.lock`**: lo genera Composer con las **versiones exactas** que resolvió. ✨ **Súbelo** al repo para builds reproducibles.

**Ejemplo mínimo de `composer.json`:**

```json
{
  "type": "project",
  "require": {
    "php": "^8.2"
  },
  "autoload": {
    "psr-4": {
      "App\\": "src/"
    }
  },
  "scripts": {
    "test": "phpunit"
  }
}
```

### Valida el JSON y crea el autoload:

```bash
composer validate
composer dump-autoload
```

---

## ¿Qué es la carpeta `vendor/`?

Es donde Composer **instala** todas las dependencias y coloca el **autoload**:

- `vendor/autoload.php` → punto de entrada del *autoload* (inclúyelo en tu app).
- `vendor/<vendor>/<paquete>` → código de cada librería instalada.
- `vendor/composer/` → metadatos y mapas de clases generados.
- `vendor/bin/` → ejecutables de paquetes (ej.: `vendor/bin/phpunit`).

> **Buenas prácticas**: no subas `vendor/` al repositorio; sí **`composer.lock`**.  
> `vendor/` se regenera con `composer install`.

**Estructura típica:**

```bash
mi-proyecto/
├─ src/
├─ vendor/
│  ├─ autoload.php
│  ├─ composer/
│  └─ vendorname/
├─ composer.json
└─ composer.lock
```

---

## ▶️ Uso del autoload

Incluye `vendor/autoload.php` **una sola vez** y usa tus clases (PSR‑4) y las de terceros:

```php
<?php
require __DIR__ . '/vendor/autoload.php';

use App\Service\Foo;       
use Monolog\Logger;        

$log = new Logger('app');
$foo = new Foo();
```

---

## Comandos más usados

```bash
composer init -n                                
composer require monolog/monolog                
composer require --dev phpunit/phpunit:^11      

composer install                                
composer update                                 
composer remove vendor/paquete                  

composer dump-autoload                          
composer audit                                  
```

### Producción (instalación optimizada)

```bash
composer install --no-dev --prefer-dist --no-progress --optimize-autoloader
```

---

## Versionado (semver) exprés

| Constraint | Significa                         |
|-----------|------------------------------------|
| `^1.2`    | `>=1.2.0 <2.0.0`                   |
| `~1.2`    | `>=1.2.0 <1.3.0`                   |
| `1.2.*`   | parches dentro de la 1.2           |
| `1.2.3`   | versión exacta (fijada)            |

> Consejo: en `composer update`, usa `--with-all-dependencies` si hay bloqueos por sub-dependencias.

---

## Scripts (atajos)

Define en `composer.json`:

```json
{
  "scripts": {
    "test": "phpunit",
    "lint": "php -l src",
    "post-install-cmd": ["@test"]
  }
}
```

### Ejecuta:

```bash
composer test
composer run lint
```

---

## Errores comunes y cómo arreglarlos

### “does not contain valid JSON”

Tu `composer.json` no es JSON válido (faltan comillas, sobran comas, etc.).  

**Arreglo rápido:**

```bash
mv composer.json composer.json.bak 2>/dev/null || true
```

```bash
cat > composer.json <<'JSON'
{
  "name": "tuusuario/mi-proyecto",
  "type": "project",
  "require": { "php": "^8.2" },
  "autoload": { "psr-4": { "App\\": "src/" } }
}
```

```bash
composer validate
composer dump-autoload
```

### “Class X not found”

- Asegúrate de que el *namespace* coincide con la ruta PSR‑4 en `composer.json`.
- Ejecuta `composer dump-autoload` tras crear/mover clases.
- Incluye `require 'vendor/autoload.php';` al inicio.

### No encuentro `composer`

Instala Composer global o por proyecto:  
<https://getcomposer.org/download/>

---

## FAQ rápida

**¿Qué es Packagist?**  

El repositorio público de paquetes para Composer: <https://packagist.org/>

**¿Puedo usar PHP sin Composer?**  

Sí, pero perderás gestión de dependencias y *autoload* moderno. Tendrás que hacer `require` manuales y mantener librerías a mano.

**¿Qué pasa si edito código dentro de `vendor/`?**  

Se perderá al reinstalar/actualizar. Si necesitas cambios, usa *fork* o *patches* (Composer 2 admite `patches` con plugins).

**¿Debo subir `vendor/` al repo?**  

Normalmente **no**. Sube `composer.json` y `composer.lock`. En despliegue corre `composer install`.

---

## Haciendo un ejemplo *Quickstart* (plantilla)

```bash
mkdir -p mi-proyecto/src
cd mi-proyecto
```

```bash
cat > composer.json <<'JSON'
{
  "type": "project",
  "require": { "php": "^8.2" },
  "autoload": { "psr-4": { "App\\": "src/" } }
}
```

```bash
composer install
composer dump-autoload
```

## Clase de ejemplo

mkdir -p src/Service
cat > src/Service/Foo.php

```php
<?php
declare(strict_types=1);
namespace App\Service;
final class Foo { public function ping(): string { return 'pong'; } }
```

## Resumen

- **Composer**: gestiona dependencias, versiones, y genera el *autoload*.
- **`vendor/`**: carpeta donde se instalan dependencias y vive `vendor/autoload.php`.
- **`composer.json`**: tu declaración de necesidades y *autoload*.
- **`composer.lock`**: fija versiones exactas para entornos consistentes.

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