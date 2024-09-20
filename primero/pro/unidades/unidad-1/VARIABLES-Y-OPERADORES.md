<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Variables y operadores)

## Uso de variables

Las __variables son elementos esenciales en todo programa__, ya que nos permiten almacenar valores en ellas, de modo que podamos operar con ellas, o modificarlas a lo largo de la ejecución del programa. Cada variable tiene un tipo que nos permite saber qué tipo de información podemos almacenar en ella. Por ejemplo, existen las variables enteras , que nos permiten almacenar números enteros, o las variables de cadena para manejar textos. Aprenderemos sobre los tipos de datos en otras secciones.

Siempre que queramos utilizar una variable debemos declararla . Este paso consiste en:

- Especificar el_ ___tipo de dato___ _de la variable_ (entero, texto…).
- Especificar el ___nombre___ de la variable, que también se conoce como ___identificador de variable___ .
Por ejemplo, de esta manera declaramos una variable para almacenar valores enteros, usando la palabra intpara especificar el tipo de datos:

```java
int myVariable;
```

- Además, también podemos asignar un valor a la variable. Este paso se puede realizar cuando la declaramos o más adelante en el código:

```java
int myVariable = 3;
int myOtherVariable;
myOtherVariable = 5;
...
```

Además, las variables pueden cambiar sus valores en oraciones posteriores.

```java
int myVariable = 3;
...
myVariable = 5;
```

También podemos declarar varias variables del mismo tipo en la misma línea, separadas por comas, y podemos decidir para cada una si queremos asignar un valor inicial o no:

```java
//PERO ESTE CÓDIGO NO ES CLARO
int number1 = 0, number2, result = 1;
```

En cuanto al identificador , puede contener letras (en mayúsculas o minúsculas), dígitos y el símbolo de subrayado _, pero no puede comenzar con un dígito. Estos son ejemplos de identificadores válidos (suelen comenzar con una letra minúscula en Java, pero no es obligatorio)

```java
int aNumber;
int another_number;
int number1;
int _one_more_number;
```

Considerando que estos son ejemplos de identificadores no válidos:

```java
int 1number;
int another number;
```

Podemos utilizar la ___System.out.println___ instrucción para mostrar el valor de una variable en la pantalla:

```java
public class MyClass
{
    public static void main(String[] args)    
    {
        int myVariable = 3;
        System.out.println(myVariable);
    }
}
```

## Algunos operadores básicos de Java

Los operadores nos permiten evaluar expresiones y producir un resultado determinado. Por ejemplo, si utilizamos el operador de suma, +podemos sumar un par de números y obtener el resultado final. Este resultado final puede asignarse a una variable o mostrarse en la pantalla.

```java
int result = 3 + 4;
System.out.println(32 + 52);
```

### Operadores aritméticos

Los operadores aritméticos nos permiten realizar algunas operaciones matemáticas básicas con números. Esta es la lista de operadores aritméticos básicos en Java:

| __Operador__ | __Significado__         |
|----------|----------------------|
| +        | Suma                 |
| -        | Resta                |
| *        | Multiplicación        |
| /        | División             |
| %        | Módulo de división   |

Respecto al operador de división , ___debemos tener en cuenta que produce un resultado del mismo tipo que los números involucrados. En otras palabras, si dividimos dos números enteros, como por ejemplo 5/2, entonces el resultado será entero (2), no real. El operador módulo obtiene el módulo de una división entera. En el ejemplo anterior, 5 % 2 obtiene el módulo de dividir 5 entre 2, que es 1___.

#### Precedencia de operadores

El orden en el que se evalúan los operadores en una expresión aritmética es importante. Por ejemplo, si establecemos una expresión como esta:

```java
int result = 4 + 2 / 2;
```

___Entonces resultla variable obtiene un valor final de 5___, porque la división 2 / 2se evalúa ANTES de la suma. Este es el orden de precedencia de los operadores aritméticos:
- Multiplicaciones, divisiones y módulos
- Sumas y restas

> Si en una operación hay más de un operador del mismo rango, se evalúan de izquierda a derecha. Por ejemplo, en este caso, primero aplicamos la multiplicación y luego la división, y el resultado final es 2:

```java
int result = 4 * 3 / 6;
```

_Sin embargo, podemos alterar el orden en el que se evalúan las operaciones en una expresión, poniendo entre paréntesis las operaciones que queremos evaluar en primer lugar. Esta expresión tiene un resultado 0, porque estamos forzando a evaluar la división 3/6 = 0 en primer lugar_.

```java
int result = 4 * (3 / 6);
```

_Esta otra expresión tiene un resultado de 3, porque estamos forzando a evaluar la suma antes que la división:

```java
int result = (4 + 2) / 2;
```

> __Ejercicio 1__:Intenta determinar el valor final almacenado en resultla variable en cada una de estas expresiones. Puedes escribir un pequeño programa en Java más tarde para comprobar tus respuestas:
- int result = 4 + 8 * 2 / 4
- int result = (4 + 8) * 2 / 4
- int result = (4 + 8) * 3 % 5

### Operadores de asignación

Ya hemos utilizado el __=__,operador para asignar un valor a una variable. Pero hay otros operadores de asignación que podemos utilizar si queremos incluir alguna operación aritmética en el proceso. Por ejemplo, __+=__ el operador , que también se conoce como operador de __suma automática__ , _suma automáticamente el valor especificado al valor actual de la variable_. En este ejemplo, el valor final de la resultvariable es 5:

```java
int result = 3;
result += 2;
```

Esta es la lista de los operadores de asignación disponibles:

| __Operador__ | __Significado__              |
|----------|--------------------------|
| =        | Tarea sencilla           |
| +=       | Adición automática        |
| -=       | Auto-sustracción          |
| *=       | Multiplicación automática |
| /=       | División automática       |
| %=       | Módulo automático         |

#### Operadores de incremento y decremento automático

Java también proporciona dos operadores adicionales, que son una combinación de operadores aritméticos y de asignación. Estos operadores son los operadores de incremento automático __++__ y decremento automático __--__. Se aplican a una sola variable y aumentan o disminuyen automáticamente su valor en 1 unidad, respectivamente.

Por ejemplo, el valor final de resultla variable en el siguiente código es 4:

```java
int result = 3;
result++;
```

_Estos operadores pueden colocarse tanto antes como después de la variable afectada. Hay una diferencia importante que debes tener en cuenta en cuanto a la colocación de los operadores_:

- Si colocamos el operador __ANTES__ de la variable en una expresión compleja, ___primero aumentamos o disminuimos la variable afectada y luego completamos la expresión___. Por ejemplo, en este código, el valor final de la bvariable es 6, porque primero aumentamos ael valor (a 4) y luego sumamos automáticamente este valor para obtener b.

    ```java
    int a = 3, b = 2;
    b += ++a;
    ```

- Si colocamos el operador __DESPUÉS__ de la variable en una expresión compleja, primero evaluamos toda la expresión, asignamos el valor y luego, incrementamos/disminuimos la variable afectada. El mismo código del ejemplo anterior obtiene un resultado de 5 para bvariable si lo escribimos así, aunque avariable terminará con el mismo resultado final, que es 4.

    ```java
    int a = 3, b = 2;
    b += a++;
    ```

Tenga en cuenta que estas reglas no se aplican si utilizamos el operador de incremento o decremento automático en una sola línea. Estas dos líneas tienen el mismo efecto sobre la variable a:

```java
a++;
++a;
````

> Ejercicio 2: Determine el valor final de resultla variable después de ejecutar todas estas instrucciones:

```java
int result = 4;
```

- result += 3;
- result *= 2;
- result--;
- result %= 4;

## Declaración de constantes

___Las constantes son valores que nunca cambian___. En Java, declaramos las constantes declarando los datos como finaly static(más adelante aprenderemos el significado de estos términos). Normalmente, estas constantes se colocan al principio de la clase.

```java
class MyClass
{
    static final int MAX_USERS = 10;
    ...
}
```

## Comentarios

Los comentarios nos ayudan a aclarar algunas partes de nuestro código, _ya que agregan texto “humano”_. __El compilador ignora este texto__, pero ayuda al desarrollador a comprender o encontrar algunas partes del código.

En Java, hay dos tipos de comentarios:

- Comentarios de una sola línea, que están precedidos por una barra doble __//__:

```java
// Yo soy un comentario de tu profesor
int variable = 3;
```

Comentarios de varias líneas, que comienzan con __/*__ y terminan con __\*/__. Todo lo que se encuentra entre ellos constituye el comentario, que el compilador ignora:

```java
/* SOY OTRO COMENTADIO DE TU PROFESOR */

int variable = 3;
```

</div>