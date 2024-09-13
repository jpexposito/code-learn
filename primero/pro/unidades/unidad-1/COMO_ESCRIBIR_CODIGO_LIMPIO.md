<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Cómo escribir código limpio)

## Introducción al código limpio

Cuando escribimos un programa, no solo debemos pensar en lo que el programa debe hacer, sino que también debemos plantearnos otras preguntas, como por ejemplo:

- __¿Qué pasa si tengo que retomar este proyecto dentro de dos años?__ - __¿Entenderé el código?__
- __¿Qué sucederá si alguien más tiene que encargarse de este proyecto en el futuro?__
- __¿Entenderá el código?__
  
Después de estas preguntas, debes pensar en una forma de escribir tu código de manera que sea fácil de leer y comprender. Aquí es donde entran en juego las reglas de código limpio.

## ¿Qué es el código limpio?

Puedes encontrar muchos ejemplos y buenas explicaciones a esta pregunta en el libro __Clean Code , de Robert C. Martin__. Aquí sólo resumo algunas de estas ideas:

- __El código debe ser elegante y fácil de leer, simple y directo. Un código limpio se lee como prosa bien escrita (Grady Booch)__.
- __La lógica debe ser sencilla para que a los errores les resulte difícil ocultarse__.
- __El rendimiento debe ser cercano al óptimo para no tentar a las personas a realizar más cambios__.

- __Tenga en cuenta la regla de los Boy Scouts: dejar el campamento más limpio de lo que lo encontró.__

## La importancia de la práctica

Escribir código limpio no consiste únicamente en leer documentos como este para tener en cuenta algunas reglas, sino también en ponerlas en práctica continuamente. Por ejemplo, puedes leer cómo montar en bicicleta, pero no aprenderás a hacerlo hasta que practiques.

Además, si no empezamos a escribir código limpio desde el principio de un proyecto, puede haber algunas consecuencias terribles más adelante: los proyectos pueden crecer demasiado y entonces puede ser difícil aplicar las reglas del código limpio a todo el código: el tiempo que pasemos arreglando el código en el futuro puede afectar a los plazos, al mantenimiento, a las versiones futuras...

## ¿Por qué existe código malo?

Aunque todos deberían aplicar reglas de código limpio en sus programas, y podemos ver fácilmente los beneficios de trabajar de esta manera, hay muchas razones por las que existe código malo:

- Horarios demasiado ajustados
- Gerentes de proyectos sin experiencia
- Docilidad del programador (no quiere que lo despidan)
- Aburrimiento (siempre hacer el mismo tipo de proyectos)
- …
## ¿Qué viene a continuación?

En esta sección nos centraremos en algunos aspectos básicos de las reglas de código limpio, como cómo asignar nombres de variables y cómo colocar comentarios en nuestro código.

### Cómo manejar nombres de variables

Los __nombres son esenciales__ en programación, ya que asignaremos un nombre a (casi) todo lo que incluyamos en nuestro programa. A esta altura ya deberías saber qué es una variable y su propósito principal (almacenar valores que se pueden modificar a lo largo de la ejecución del programa). Pero no deberías asignar un nombre de variable sin cuidado. Deberías usar nombres significativos para tus variables.

_Al leer el nombre de una variable (o cualquier otro elemento del código), se deben responder algunas preguntas básicas, como por qué existe, qué hace y cómo se utiliza_. ___Si un nombre requiere un comentario para explicar su significado, entonces no es un nombre adecuado__. Por ejemplo, si queremos almacenar en una variable la edad promedio de una lista de personas, NO debemos hacer esto:

```java
int a;			// Age average
```

Podríamos hacer esto en su lugar:

```java
int ageAverage;
```

Algunos otros aspectos que debemos tener en cuenta al tratar con nombres de variables:

- Intenta no usar nombres demasiado similares. Las variables como ___totalRegisteredUsers__ y__ ___totalUnregisteredUsers___ solo difieren en dos letras, y podrías usar la incorrecta en un fragmento de código determinado. Es mejor llamarlas __registered__ y __anonymous__.
- Agrega un contexto significativo cuando sea necesario. Por ejemplo, si una variable se llama __account__, ¿qué significa? ¿Una cuenta de usuario? ¿Una cuenta bancaria? Es mejor ser más específico y llamarla __bankAccount__, por ejemplo.
- Elija una palabra por concepto : si declara muchas variables en muchas partes de su código para referirse al inicio de sesión de un usuario, siempre debe llamarlas de la misma manera: user, o login, por ejemplo, pero no cambie el nombre en cada situación.
- No utilice nombres cortos, como _n_, o _e_, porque será difícil encontrar su variable entre otras palabras similares en el texto.
- Intenta utilizar __nombres legibles__. Es mejor utilizar un nombre como __birthDate, ddmmyyyyya__ que podrás pronunciarlo en una conversación.

### ¿Mayúsculas o minúsculas?

El uso de letras mayúsculas y minúsculas en los nombres depende del lenguaje de programación en sí. Existen principalmente cuatro estándares de nombres:

- __Camel Case__ : se utiliza en lenguajes como __Java o Javascript__. Todas las palabras del nombre de la variable comienzan con mayúscula, excepto la primera palabra. Por ejemplo:

```java
String personName;
```

Existe un subconjunto del estándar __CamelCase, llamado PascalCase__, en el que la primera palabra del nombre también comienza con mayúscula. __C# utiliza este subconjunto para definir elementos públicos__ (los elementos privados se nombran con CamelCase). Por ejemplo:

```c
string personName;
public int PersonAge;
```

- __Snake Case__: se utiliza en lenguajes como PHP. Las palabras variables se separan con guiones bajos:

```php
$person_name = "Nacho";
```

- __Caso Kebab__ : _las palabras de las variables se separan con guiones_. No es muy popular entre los lenguajes de programación, ya que muchos de ellos no permiten el guión como parte del nombre de la variable (para no mezclarlo con el operador de resta). Hay algunos ejemplos, como Lisp o Clojure.
(def person-name "Nacho")
- __Mayúsculas__: __se utilizan en muchos lenguajes para definir constantes__. Las palabras del nombre suelen estar separadas por guiones bajos, como en el caso estándar de Snake Case:
  
```java
static final int MAXIMUM_SIZE = 100;
```

## Colocación de comentarios

Los comentarios bien ubicados nos ayudan a entender el código que los rodea, mientras que los comentarios mal ubicados pueden perjudicar la comprensión del código. Algunos programadores piensan que los comentarios son un fracaso y que se deben evitar tanto como sea posible. Una de las razones que se argumenta es que son difíciles de mantener. Si cambiamos el código después de escribir un comentario, podemos olvidarnos de actualizar el comentario y, por lo tanto, hablaría de algo que ya no está presente en el código.

Otra razón para evitar los comentarios es que están estrechamente vinculados con el código incorrecto. Cuando escribimos código incorrecto, a menudo pensamos que podemos escribir algunos comentarios para que sea más comprensible, en lugar de limpiar el código en sí.

En esta sección aprenderemos dónde colocar los comentarios. Primero veremos qué tipo de comentarios son necesarios (lo que llamamos buenos comentarios ) y luego veremos qué comentarios son evitables ( malos comentarios ).

### Buenos comentarios

Se consideran necesarios los siguientes comentarios:

Comentarios legales , como derechos de autor o autoría, según los estándares de la empresa. Este tipo de comentarios se colocan normalmente al principio de cada archivo fuente que pertenece al autor o a la empresa.
Comentarios de introducción : un comentario breve al comienzo de cada archivo fuente (normalmente, clases) que explica el propósito principal de este archivo fuente o clase. Este comentario suele colocarse junto con un comentario legal al comienzo de un archivo fuente:

```java
/*
 This class stores information about a user account
 
 Created by jpexposito
 */

public class User
{
    ...
}
```

Explicación de la intención . Estos comentarios se utilizan cuando:
___Intentamos conseguir una mejor solución al problema pero no pudimos, y luego explicamos que una parte del código podría ser mejorable___.
Hay una parte del código que no sigue el mismo patrón que el código que lo rodea (por ejemplo, una variable entera entre un grupo de flotantes) y queremos explicar por qué hemos utilizado esta instrucción o tipo de datos.
Comentarios __TODO__ , que se colocan en partes incompletas. Nos ayudan a recordar todas las tareas pendientes. Este tipo de comentarios se han vuelto tan populares que muchos IDE los detectan y resaltan automáticamente.
Documentación de la API . Algunos lenguajes de programación, como __Java o C#__, nos permiten añadir algunos comentarios en algunas partes del código para que estos comentarios se exporten a formato __HTML o XML__ y formen parte de la documentación.

### Malos comentarios

Los siguientes son ejemplos de malos comentarios que podemos evitar…

- Algunos tipos de comentarios informativos se pueden evitar cambiando el nombre del elemento que explican. Por ejemplo, si tenemos este comentario con esta variable:

```java
// Total number of customers
int total;
```

Podemos evitar el comentario renombrando la variable de esta manera:

```java
int totalCustomers;
```

Comentarios redundantes , es decir, comentarios que son más largos de leer que el código que intentan explicar, o que simplemente son innecesarios, porque el código se explica por sí solo. Por ejemplo, el siguiente comentario es redundante, ya que el código que explica es bastante comprensible:

```java
/* We ask the user two numbers and add them */
Scanner sc = new Scanner(System.in);
System.out.println("Enter two numbers");
int number1 = sc.nextInt();
int number2 = sc.nextInt();
System.out.println(number1 + number2);
```

Comentarios sin contexto , es decir, comentarios que no van seguidos del código correspondiente. Por ejemplo, el siguiente comentario no se completa con el código apropiado. Decimos que estamos escribiendo datos en un archivo, pero no se ejecuta nada después de eso. Quizás había algún fragmento de código, pero se eliminó.

```java
/* We ask the user two numbers and add them */
Scanner sc = new Scanner(System.in);
System.out.println("Enter two numbers");
int number1 = sc.nextInt();
int number2 = sc.nextInt();
System.out.println(number1 + number2);
// We print the result in a text file
```

No debería haber comentarios obligatorios . Algunas personas piensan que cada variable, por ejemplo, debe tener un comentario que explique su propósito. Pero esa no es una buena decisión, ya que podemos evitar la mayoría de estos comentarios utilizando nombres de variable adecuados.

Además, no debería haber comentarios en el diario : a veces, se coloca un registro de edición al principio de un archivo fuente. Contiene todos los cambios realizados en el código, incluida la fecha y el motivo del cambio. Pero hoy en día, podemos usar aplicaciones de control de versiones, como __GitHub__, para mantener este registro fuera del código en sí.

Hace algún tiempo, algunos programadores solían colocar algunos marcadores de posición y/o separadores de código , para encontrar rápidamente un lugar en el código, o para separar algunos bloques de código que son bastante largos. Ambos tipos de comentarios no son recomendables si el código está formateado correctamente.

```java
// =================== VARIABLES ====================
int age;
String name;
...
// =================== MAIN =========================
public static void main(String[] args)
{
    ...
    ////// FINAL RESULT
}
```

Tampoco se recomiendan los comentarios de llaves de cierre . Se colocan en cada llave de cierre para explicar qué elemento cierra esta llave. Estos comentarios se pueden evitar, ya que la mayoría de los IDE actuales resaltan cada par de llaves cuando hacemos clic en ellas, de modo que podemos hacer coincidir cada par automáticamente.

```java
public static void main(String[] args)
{
    ...
} // end main
```

Las advertencias se utilizan cuando tenemos algún código que puede causar problemas en determinadas situaciones, porque es necesario revisarlo. Es muy habitual encontrar algunos bloques de código completamente comentados, y un mensaje de advertencia explicando el problema que presenta. Estos comentarios deberían convertirse en comentarios __“TODO”__, para avisar al programador de que ese código necesita ser revisado en el futuro, en lugar de simplemente eliminar los comentarios.

> __Ejercicio 1__: Este programa pide al usuario introducir tres números y obtiene el promedio de los mismos. Discuta en clase qué partes del código no están limpias o podrían mejorarse, en cuanto a nombres de variables y comentarios.

```java
import java.util.Scanner;

public class AverageNumbers
{
    public static void main(String[] args)
    {
        // Variables to store the three numbers and the average
        int n1, n2, n3;
        int Result;
        Scanner sc = new Scanner(System.in);

        // We ask the user to enter three numbers
        System.out.println("Introduce three numbers:");
        n1 = sc.nextInt();
        n2 = sc.nextInt();
        n3 = sc.nextInt();
        // The result is the average of these numbers
        /* We could have used a float number instead, 
            but we decided to keep this program as 
            simple as we could */
        Result = (n1+n2+n3)/3;
        System.out.println("The average is " + Result);
    }
}
```

</div>