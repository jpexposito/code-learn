<div align="justify">

# <img src="../../images/coding-book.png" width="40"> Code & Learn (Php en 5 días)

## 🗓️ Introducción y Sintaxis Básica

PHP,  acrónimo  recursivo  en  inglés  de  PHP  Hypertext  Preprocessor  (procesador  de
hipertexto), es un lenguaje de programación de propósito general de código del lado del servidor
originalmente diseñado para el desarrollo web de contenido dinámico. Fue uno de los primeros
lenguajes de programación del lado del servidor que se podían incorporar directamente en un
documento HTML en lugar, de llamar a un archivo externo que procese los datos. El código es
interpretado por un servidor web con un módulo de procesador de PHP que genera el HTML
resultante.
PHP ha evolucionado por lo que ahora incluye también una interfaz de línea de comandos
que puede ser usada en aplicaciones gráficas independientes. Puede ser usado en la mayoría de los
servidores web al igual que en muchos sistemas operativos y plataformas sin ningún costo.
Una página PHP generalmente consiste de una página HTML con comandos PHP incrustados en
ella. El servidor web procesa los comandos PHP y envía la salida al visualizador (browser). Un
ejemplo de una página PHP sencilla sería la siguiente:

```html
<html>
  <head> <title>Hello, world</title> </head>
  <body>
    <?php echo "Hello, world!"; ?>
  </body>
</html>
```

El comando echo de PHP produce la salida que se inserta en la página HTML. Note que el código
PHP se escribe dentro de los delimitadores <?php y ?>.
Las instrucciones se separan con “;”, en el caso de ser la última instrucción no es necesario el punto
y coma.
Los comentarios en PHP pueden ser:

>> Como en C o C++, /*…*/ ó //
    Otro tipo de comentario de una línea es #, que comentará la línea en la que aparezca pero sólo hasta el tag ?> que cierra el código php.

Vamos a realizar nuestro primer guión php y haremos uso de la función phpinfo()
Nota: en php.net tenemos la descripción de las diferentes funciones php La siguiente
información es tomada de allí
Vamos a crear una carpeta para alojar proyectos php en nuestro usuario
y la enlazaremos desde la raíz de nuestro servidor apache. Por ejemplo dentro de la carpeta de la instancia de docker ("php-docker-proyect")  __/src/public__:
Crearemos un fichero: info.php
y pondremos:

```php
<?php
echo "<p style='background: pink'> Este es mi primer script php";
echo "</p>";
phpinfo();
?>
```

Ahora accederemos con el navegador a:

 ```code
 http://localhost:8080/info.php  
```

> __Importante:__ Analiza que ha sucedido.

## 🗂️ Tipos de datos y variables

En PHP todas las variables comienzan con el símbolo del dólar __\$__ y _no es necesario definir una
variable antes de usarla. Una variable como cualquier etiqueta en php empieza por una letra o guion
bajo, seguido por cualquier número de letras, números o guiones bajos. Usando una expresión
regular, se representaría de la siguiente manera_: 

- [a-zA-Z_\x7f-\xff][a-zA-Z0-9_\x7f-\xff]*

Observar __0x7f-0xff__  hace referencia al __ASCII__ extendido ( de 127 a 255 ) y en el texto anterior se
entiende como cualquier carácter también a esos códigos
De facto significa que podemos poner cualquier carácter español sin problemas.
Ejemplos de nombres de variables:

```php
//las siguientes son todas válidas:
$pañito = 5;
$exponenteª = 4;
$celdaÇ = 2;
$admira¡a = 8;
$interroga¿e = 9;
$4site = 'aun no';      // inválido; comienza con un número
$_4site = 'aun no';     // válido; comienza con un carácter de subrayado
$täyte = 'mansikka';    // válido; 'ä' es ASCII (Extendido) 228
```

Una misma variable puede contener un número y luego puede contener caracteres. El intérprete
establecerá el tipo de dato internamente al hacer la inicialización de la variable ( tipado dinámico )

```php
$variable = “texto”; //estamos con una variable de texto
$otravariable = 8; // estamos con una variable numérica
```

A lo anterior le podemos poner la salvedad de los parámetros de función recibidos si estamos en el
modo estricto.

### Tipos de datos primitivos

- __escalares__: boolean, integer, float, string
- __compuestos__: array, object, callable, iterable
- __especiales__: resource, NULL

Fuente php.net:

> __Nota__: Para comprobar el tipo y el valor de una expresión, utilice la función __var_dump()__. 
Para obtener una representación legible por humanos de un tipo con propósitos de depuración, use
la función __gettype()__. Para comprobar un cierto tipo, no emplee __gettype()__, si no las funciones is_tipo.

Algunos ejemplos: 

```php
<?php
$un_bool = TRUE;   // un valor booleano
$un_str  = "foo";  // una cadena de caracteres
$un_str2 = 'foo';  // una cadena de caracteres
$un_int  = 12;     // un número entero
echo gettype($un_bool); // imprime: boolean
echo gettype($un_str);  // imprime: string
// Si este valor es un entero, incrementarlo en cuatro
if (is_int($un_int)) {
    $un_int += 4;
}
// Si $un_bool es una cadena, imprimirla
// (no imprime nada)
if (is_string($un_bool)) {
    echo "Cadena: $un_bool";
}
?>
```

Para saber si es de un tipo de datos en concreto usaremos pues:
is_array, is_bool, is_float, is_int, is_string, is_null, is_object, is_resource.

Hemos dicho que php es de tipado dinámico. Pero podemos en las funciones forzar a que los
parámetros sean como esperamos que deben ser:

```php
<?php
        declare( strict_types=1);
        function sum( int $a, int $b): int{
                return $a + $b;
        }
        sum(1,2);
?>
```

Podemos  observar  que  tenemos  que  establecer  que  sea  estricto  el  chequeo  de  los  tipos:

```code
strict_types=1 
```

Es fundamental tener en cuenta que ___DEBE SER LO PRIMERO QUE ESTABLECEMOS EN EL
FICHERO__.
Con __strict_types__ _una variable  no puede pasar un valor distinto a una función o retornar un
valor distinto del tipo que se haya definido_.
Por otro lado también  podemos  observar que  establecemos  el  tipo de  datos  devuelto
mediante el símbolo __“:”__   y el tipo de datos antes del cuerpo de la función
Probar el código y ver el resultado en el navegador:

```html
<?php
        declare( strict_types=1);
?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
    <?php
        function sum( int $a, int $b): int {
                return $a + $b;
        }
        echo "<p> la suma de uno más dos es: ";
        $resultado = sum(1,2);
        print sum(1,2);
        echo "</p>"
    ?>
    </body>
</html>
```

> __Referencias__ en __php.net__: [trict_types](http://php.net/manual/es/control-structures.declare.php)

En la documentación nos dice que strict_types observa el valor que se le pasa a una función y el
valor que esta devuelve, lanzando un error si no se corresponde con lo esperado
Veamos este código:

```html
<?php
        declare( strict_types=1);
?>
<!DOCTYPE html>
<html>


    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
    <?php
        function fun( int $a, int $b): int {
                $a = "o";
                //return $a;
                return $b ;
        }
        print fun(1,2);
        //print fun("e",3);
        echo "</p>"
    ?>
    </body>
</html>
```

### Variables asignadas por referencia

En PHP también podemos asignar variables por referencia. En ese caso no se les asigna un
valor, sino otra variable, de tal modo que las dos variables comparten espacio en memoria para el
mismo dato.
La notación para asignar por referencia es colocar un "&" antes del nombre de la variable.

```php
<?php 
$foo = 'Bob'; // Asigna el valor 'Bob' a $foo 
$bar = &$foo; // Referencia $foo vía $bar. 
$bar = "Mi nombre es $bar"; // Modifica $bar... 
echo $foo; // $foo también se modifica. 
echo $bar; 
?>
```

Esto dará como resultado la visualización dos veces del string __"Mi nombre es Bob"__.
 Algo como: _Mi nombre es Bob Mi nombre es Bob_. 

### Variables predefinidas

PHP proporciona una gran cantidad de __variables predefinidas__ para todos los scripts. Las
variables representan de todo, desde variables externas hasta variables de entorno incorporadas,
desde los últimos mensajes de error hasta los últimos encabezados recuperados.

Algunos ejemplos:

- ___\$_REQUEST___. matriz asociativa que contiene los datos enviados por los formularios y las
cookies
- ___\$_SERVER___. matriz  asociativa con información sobre cabeceras, rutas, etc suministrada
por el servidores.
- ___\$GLOBALS___. matriz asociativa con todas las variables disponibles en el ámbito global

Hemos hablado de variables ___globales, vamos a entender pues el ámbito de una variable___.

### Ámbito de una variable

En PHP, todas las variables creadas en la página, fuera de funciones, son variables globales a
la página. Por su parte, las variables creadas dentro de una función __son variables locales a esa
función__.

__Las variables globales__ _se pueden acceder en cualquier lugar de la página_, mientras que las
__variables locales__ sólo tienen validez _dentro de la función donde han sido creadas_. De modo que una
variable global la podemos acceder dentro de cualquier parte del código, mientras que si intentamos
acceder a una variable local fuera de la función donde fue creada, nos encontraremos con que esa
variable no tiene contenido alguno.

___Ahora bien, si intentamos acceder a una variable global dentro de una función, en principio
también nos encontraremos con que no se tiene acceso a su valor. Esto es así en PHP por motivos de
claridad del código, para evitar que se pueda prestar a confusión___ el hecho de usar dentro de una
función una variable que no ha sido declarada por ningún sitio cercano. 
Para acceder a una variable global dentro de una función, especificaremos que variables
globales queremos usar mediante la palabra reservada: __global__.

```php
function mifuncion(){ 
  global $mivariable, $otravariable;
  //con esa línea dentro de la función, declaramos el uso de variables globales 
  echo $mivariable; 
  echo $otravariable; 
}
```

Otra alternativa es hacer uso de ___\$GLOBALS___

```php
function mifuncion(){ 
  //estoy dentro de la función, para acceder a las variables utilizo $GLOBALS 
  echo $GLOBALS["mivariable"]; 
  echo $GLOBALS["otravariable"]; 
}
```

Podemos observar que podemos acceder a __\$GLOBALS__ directamente dentro de una función
¿ y cómo puede ser eso si es a su vez, una variable global ? Para las otras variables globales hemos
visto que tenemos que establecer que son globales. Aquí es donde entra el concepto de: ___variables
superglobales___. Estas variables _mantienen información del sistema_, se definen automáticamente en
un ámbito global y a las que se puede acceder desde cualquier punto del código PHP.

### Eliminar variables

La función: ___unset()___  nos permite destruir variables

> ___php.net___: 
___unset()___ destruye las variables especificadas.

El comportamiento de unset() dentro de una función puede ___variar dependiendo de qué tipo
de variable___ que se está tratando de destruir.
Si una variable global es ___unset() dentro de una función, solo la variable local es destruida___.
_La variable en el entorno de la llamada mantendrá el mismo valor anterior a la llamada a unset()_.

```php
<?php
    function destruir_foo() 
    {
        global $foo;
        unset($foo);
    }
    $foo = 'bar';
    destruir_foo();
    echo $foo;
?> 
```

El resultado del ejemplo sería:
bar
Si desea aplicar unset() a una variable global dentro de una función, puede usar la matriz
__\$GLOBALS__ para hacerlo:

```php

<?php
function foo() 
{
unset($GLOBALS['bar']);
}
$bar = "algo";
foo();
?>
```

Hemos de entender que ejecutar __unset()__ es un paso más que igualar a null una variable:

```php
$variable = null;
unset($variable);
```

Cuando establecemos unset() borramos de la tabla de símbolos de php la variables. Así si en las
sentencias de antes preguntamos por ___var_dump()__:

```php
$variable = null;
var_dump($variable);
unset($variable);
var_dump($variable);
```

> __Nota__:obtendremos en el segundo caso un mensaje diciendo que la variable no está definida.

Aprovechamos para observar el uso de var_dump  Veamos lo que dice __php.net__:

```php
void var_dump ( mixed $expression [, mixed $... ] )
```

>Esta función muestra información estructurada sobre una o más expresiones incluyendo su tipo y
valor. Las matrices y los objetos son explorados recursivamente con valores sangrados para mostrar
su estructura.
A partir PHP 5 todas las propiedades públicas, privadas y protegidas de los objetos serán devueltas en la
salida.

### Constances

Para crear una constante haremos uso de la función:  __define()__
Estas etiquetas que
generaremos no harán uso de: __\$__ para obtener el dato que tienen dentro:

```php
define('PI', 3.14159);
echo 'El valor de PI es: ' . PI ;
```

Las reglas para el nombre de las constantes sigue la misma expresión regular que para las
variables:  _[a-zA-Z_\x7f-\xff][a-zA-Z0-9_\x7f-\xff]*_

A partir de php 5  ya se puede hacer uso de la palabra reservada const:

```php
const PI = 3.14159;
```
 
### Concatenación

En php podemos concatenar textos mediante el punto: ___“.”___

```php
    <?php
        $apellidos="Arcadia Manices";
        $nombre="Amilcar";
     echo "Hola mi nombre es:" . $nombre . " y mis apellidos son:" . $apellidos;
    ?>
```

### Concatenación y asignación

En php podemos concatenar textos mediante el punto: __“.”__  ahora
bien, entonces existe el equivalente a +=, -=, etc  para:  __.=__   significando:

```php
$cadena = “Hola “;
$cadena .= “mundo”;
echo $cadena  // devolverá: hola mundo
```

Al igual que en Javascript hay diferencia entre la comparación: __==__  y la comparación __===__
Siendo el segundo que además de ser iguales, también sean exactamente del mismo tipo

```php
$a = 2;
$b = “2”;
$a == $b  // devuelve true
$a === $b // devuelve false
```

### <=> Comparador de orden. (PHP 7, Spaceship operator)

Este operador sirve para comparar dos variables:
- __($a y $b) y devolverá -1 si $a es < $b, 0 cuando sean iguales y 1 cuando $a sea mayor que $b.
- __??__ uno o el otro (PHP 7, operador coalescente)

> El operador de fusión de null __(??)__ se ha añadido como aliciente sintáctico para el caso común de la necesidad de utilizar un operador ternario junto con  isset().  Devuelve su primer operando si existe y no es NULL; de lo contrario devuelve su segundo operando.

## Comillas dobles

Si usamos comillas dobles para delimitar cadenas de PHP haremos que el lenguaje se
comporte de una manera más "inteligente". Lo más destacado es que las variables que coloquemos
dentro de las cadenas se sustituirán por los valores. Ej:

```php
<?php
    $nombre = "Riquelme";
    echo "hola amigo $nombre";
?>
```

Observando la salida vemos que con __comillas dobles__ nos ha interpretado el contenido de la variable en el __mensaje en pantalla__.

## Uso de llaves

Tratar de interpretar dentro de comillas dobles un array asociativo _( índice alfanumérico al estilo de mapa )  puede generar problemas que se resuelven mediante llaves __{__ __}__.

```php
<?php
    $array = array('uno' => 1, 'dos' => 2, 'tres' => 40, 'cuatro' => 55);
    $cadena = "La posición 'tres' contiene el dato {$array['tres']}";
    echo $cadena;
?>
```

## Comillas simples

Al usar comillas simples ya no se trata de obtener el contenido. Ahora volcará el texto literal que hemos escrito.

```php
<?php
    $nombre = "Ronaldo";
    echo 'hola amigo $nombre';
?>
```

Observar  que  si  estamos  introduciendo  un  texto  que  no  queremos  que  interprete  los
contenidos de variables nos interesa más hacer uso de las comillas simples ya que el intérprete no tendrá gasto computacional tratando de sustituir variables por su valor.

## Caracteres de escape 

Para incluir el símbolo __\$__, la contrabarra y otros caracteres utilizados por el lenguaje dentro de las cadenas y no confundirlos se usan los caracteres de escape.
Para insertar un caracter de escape tenemos que indicarlo comenzando con el símbolo de la contrabarra (barra invertida) y luego el del caracter de escape que deseemos usar.
Los caracteres de escape disponibles dependen del tipo de literal de cadena que estemos
usando. En el  caso de las cadenas con comillas dobles se permiten muchos más caracteres de escape. Los encuentras en la siguiente tabla: 

>> INTRODUCIR TABLA

Estos cambios de línea y tabulaciones tienen únicamente efecto en el código y no en el texto ejecutado por el navegador. En otras palabras, si queremos que nuestro texto ejecutado cambie de
línea hemos de introducir un echo "__\<br>__" y no “__\n__”

## Variables de variables

Observar lo siguiente:  

```php
$variable='dato';
$dato = 5;
```

Ahora mismo ya no debiéramos tener dudas que lo anterior hace referencia al nombre de dos variables. Una variable llamada: __\$variable__ que almacena la palabra: dato y otra variable llamada: __\$dato__   que almacena el número __5__. Ahora bien, _¿ cómo interpretar la siguiente expresión en el contexto anterior?_

```php
${$variable}
```

Pues bien, primero se transforma la parte interior:  __\$variable__ y se obtiene la palabra dato. Así que nos encontramos con:   __\$dato__ y esa variable sabemos que tiene el valor __5__.

>__Nota__: no es necesario poner las llaves en el ejemplo anterior.
Prueba el siguiente código:

```php
<?php
    $variable = 'dato';
    $dato = 5; 
    echo $$variable.'<br>';
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