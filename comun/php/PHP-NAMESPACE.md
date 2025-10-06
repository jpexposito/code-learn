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

---

## 📝 Resumen

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