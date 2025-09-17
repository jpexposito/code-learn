# <img src="../../images/coding-book.png" width="40"> Code & Learn (Php en 5 días)

## 🗓️ Introducción y Sintaxis Básica

### Ejemplo

```php
<?php
echo "¡Hola, mundo!";

$nombre = "Ana";
$edad = 25;
$altura = 1.70;
$activo = true;

echo "Soy $nombre y tengo $edad años.";

define("PI", 3.1416);
echo PI;
?>
```

---

## 🗓️ Operadores y Control de Flujo

### Ejemplo

```php
<?php
$numero = 10;

if ($numero > 0) {
    echo "El número es positivo";
} elseif ($numero == 0) {
    echo "El número es cero";
} else {
    echo "El número es negativo";
}

for ($i = 1; $i <= 5; $i++) {
    echo "Iteración $i\n";
}
?>
```

---


## 🧩 Funciones con Parámetros Opcionales

En PHP, las funciones pueden tener **parámetros opcionales** con valores por defecto.  
Si no se pasa el argumento al llamar a la función, se usará ese valor por defecto.

### Ejemplo
```php
<?php
// Función con parámetro opcional
function saludar($nombre = "Invitado") {
    return "Hola, $nombre";
}

echo saludar("Ana");      // Hola, Ana
echo saludar();           // Hola, Invitado
?>
```

---

## 🧩 Arrays en PHP

Los **arrays** permiten almacenar varios valores en una sola variable.  
Pueden ser **indexados** (con índices numéricos) o **asociativos** (con claves personalizadas).

### Ejemplo de arrays indexados
```php
<?php
$numeros = [1, 2, 3];
array_push($numeros, 4); // añade un 4 al final

foreach ($numeros as $n) {
    echo $n . " ";   // 1 2 3 4
}
?>
```

### Ejemplo de arrays asociativos
```php
<?php
$persona = ["nombre" => "Ana", "edad" => 25];
echo $persona["nombre"]; // Ana
echo $persona["edad"];   // 25
?>
```

---

## 🧩 Bucle `while`

El bucle `while` ejecuta un bloque de código **mientras** la condición sea verdadera.

### Ejemplo
```php
<?php
$contador = 1;

while ($contador <= 5) {
    echo "Número: $contador\n";
    $contador++;
}
?>
```
📌 Este ejemplo imprime los números del 1 al 5.

---

## 🧩 Bucle `do...while` (equivalente a *repeat-until*)

El bucle `do...while` ejecuta el bloque **al menos una vez**, y después evalúa la condición.

### Ejemplo
```php
<?php
$contador = 1;

do {
    echo "Número: $contador\n";
    $contador++;
} while ($contador <= 5);
?>
```
📌 Este ejemplo también imprime los números del 1 al 5, pero garantiza **al menos una ejecución** aunque la condición no se cumpla al inicio.

---

## 🗓️ Manejo de Formularios y Archivos

### Ejemplo

```html
<!-- formulario HTML -->
<form method="post" action="procesar.php">
  Nombre: <input type="text" name="nombre">
  <input type="submit">
</form>
```

```php
// procesar.php
<?php
$nombre = $_POST["nombre"] ?? "Invitado";
echo "Hola, $nombre";
?>
```

```php
// manejo de ficheros
<?php
file_put_contents("archivo.txt", "Hola mundo\n");

echo file_get_contents("archivo.txt");

$file = fopen("archivo.txt", "a");
fwrite($file, "Nueva línea\n");
fclose($file);
?>
```

---

## 🗓️ Bases de Datos con PDO (MySQL)

### Ejemplo

```php
<?php
try {
    $pdo = new PDO("mysql:host=localhost;dbname=escuela", "root", "");
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo "Conexión exitosa";
} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}

// Insertar
$sql = "INSERT INTO alumnos (nombre, edad) VALUES (:nombre, :edad)";
$stmt = $pdo->prepare($sql);
$stmt->execute(["nombre" => "Ana", "edad" => 20]);

// Leer
$sql = "SELECT * FROM alumnos";
foreach ($pdo->query($sql) as $fila) {
    echo $fila["nombre"] . " - " . $fila["edad"] . "<br>";
}

// Actualizar
$sql = "UPDATE alumnos SET edad = :edad WHERE nombre = :nombre";
$stmt = $pdo->prepare($sql);
$stmt->execute(["edad" => 21, "nombre" => "Ana"]);

// Eliminar
$sql = "DELETE FROM alumnos WHERE nombre = :nombre";
$stmt = $pdo->prepare($sql);
$stmt->execute(["nombre" => "Ana"]);
?>
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