<div align="justify">

# <img src=../../images/coding-book.png width="40"> Code & Learn (Php en 5 días)

## 🗓️ Introducción y Sintaxis Básica

### Conceptos

- PHP es un lenguaje interpretado que se ejecuta en el **servidor**.  
- Se utiliza para generar páginas web dinámicas.  
- Archivos terminan en `.php`.  
- Las variables comienzan siempre con `$`.  
- Soporta varios tipos de datos: `string`, `int`, `float`, `bool`, `array`, `object`, `null`.  
- Las **constantes** se definen con `define()`.  
- Se pueden escribir **comentarios** de una línea (`//`, `#`) o varias líneas (`/* ... */`).

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

### Conceptos

- **Operadores**: aritméticos, comparación, lógicos y asignación.  
- **Condicionales**: permiten ejecutar bloques de código dependiendo de condiciones (`if`, `else`, `elseif`, `switch`).  
- **Bucles**: permiten repetir código (`while`, `do-while`, `for`, `foreach`).  

```php
``php
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

## 🗓️ Funciones y Arrays

### Conceptos

- Una **función** es un bloque de código reutilizable.  
- Se pueden definir funciones con parámetros opcionales y valores por defecto.  
- También se pueden usar funciones con **parámetros variables** (`...$args`).  
- **Arrays**:  
  - Indexados: usan índices numéricos.  
  - Asociativos: usan claves con nombres.  
  - Multidimensionales: contienen arrays dentro de otros arrays.  
- Funciones útiles para arrays: `count()`, `array_push()`, `array_merge()`, `in_array()`, `array_map()`.

```php
``php
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

## 🗓️ Manejo de Formularios y Archivos

### Conceptos

- Los **formularios HTML** permiten enviar datos a PHP mediante `GET` o `POST`.  
- PHP puede acceder a esos datos con `$_GET` o `$_POST`.  
- Manejo de archivos:  
  - `file_put_contents()` y `file_get_contents()` para escribir/leer de forma rápida.  
  - `fopen()`, `fwrite()`, `fread()`, `fclose()` para mayor control.  
  - Modos de apertura: `"r"`, `"w"`, `"a"`, `"r+"`, etc.  

```php
//creacion del formulario
<form method="post" action="procesar.php">
  Nombre: <input type="text" name="nombre">
  <input type="submit">
</form>
```

```php
// proceso el formulario (procesar.php)
<?php
$nombre = $_POST["nombre"];
echo "Hola, $nombre";
?>
```

```php
//ficheros
<?php
file_put_contents("archivo.txt", "Hola mundo\n");

echo file_get_contents("archivo.txt");

$file = fopen("archivo.txt", "r");
while (!feof($file)) {
    echo fgets($file);
}
fclose($file);

$file = fopen("archivo.txt", "a");
fwrite($file, "Nueva línea\n");
fclose($file);
?>
```

---

## 🗓️ Introducción a Bases de Datos (MySQL con PDO)

### Conceptos

- **PDO (PHP Data Objects)** permite conectarse a distintas bases de datos con una misma interfaz.  
- Operaciones CRUD:  
  - **Create** → insertar registros.  
  - **Read** → consultar registros.  
  - **Update** → modificar registros.  
  - **Delete** → eliminar registros.  
- Uso de **sentencias preparadas** (`prepare`, `execute`) evita inyecciones SQL.  

```php

<?php
try {
    $pdo = new PDO("mysql:host=localhost;dbname=escuela", "root", "");
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo "Conexión exitosa";
} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}

$sql = "CREATE TABLE IF NOT EXISTS alumnos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    edad INT
)";
$pdo->exec($sql);

$sql = "INSERT INTO alumnos (nombre, edad) VALUES (:nombre, :edad)";
$stmt = $pdo->prepare($sql);
$stmt->execute(["nombre" => "Ana", "edad" => 20]);

$sql = "SELECT * FROM alumnos";
foreach ($pdo->query($sql) as $fila) {
    echo $fila["nombre"] . " - " . $fila["edad"] . "<br>";
}

$sql = "UPDATE alumnos SET edad = :edad WHERE nombre = :nombre";
$stmt = $pdo->prepare($sql);
$stmt->execute(["edad" => 21, "nombre" => "Ana"]);

$sql = "DELETE FROM alumnos WHERE nombre = :nombre";
$stmt = $pdo->prepare($sql);
$stmt->execute(["nombre" => "Ana"]);
?>
```

</div>