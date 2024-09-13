<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Entrada y salida básicas)

En este apatado vamos a aprender a interactuar con el usuario final. En primer lugar, veremos cómo imprimir valores en la pantalla mediante diferentes instrucciones y, a continuación, veremos cómo recopilar información del teclado y convertirla al tipo de dato adecuado.

## Salida del programa

Puede utilizar la instrucción ___System.out.print___ o ___System.out.println___ _(según desee o no una nueva línea al final)_ para imprimir mensajes en la pantalla. 
>__Puede unir varios valores utilizando el operador de enlace ( +):

```java
int result = 12;
System.out.println("The result is " + result);
System.out.print("Have a nice day!");
```

### Salida formateada

Además de __System.out.println__ la instrucción tradicional para imprimir datos, podemos utilizar otras opciones si queremos que estos datos tengan un formato de salida determinado. Para ello, podemos utilizar __System.out.printf__ la instrucción en lugar de la anterior. Esta instrucción se comporta de forma similar a la __printf__, función original del _lenguaje C_. ___Tiene un número variable de parámetros, y el primero de todos es la cadena que se va a imprimir. Luego, esta cadena puede tener algunos caracteres especiales dentro de ella, que determinan los tipos de datos que deben reemplazar a estos caracteres___. Por ejemplo, si utilizamos esta instrucción:

```java
System.out.printf("The number is %d", number);
```
entonces el símbolo __%d__ será reemplazado por la __variable number__, y esta variable debe ser _un entero_ (esto es lo que __%d__ significa).

Existen otros símbolos que representan distintos tipos de datos. A continuación se muestran algunos de ellos:

| Secuencia | Significado       |
|-----------|------------------------------------------------------------------------------|
| %d        | Para tipos enteros (long, int)          |
| %f        | Para tipos reales (float y double)               |
| %s        | Para cuerdas (strings)                                                       |
| %c        | Para caracteres (char)                                                       |
| %n        | Para representar una nueva línea (similar a \n, pero independiente de la plataforma) |

Podemos colocar tantos símbolos como queramos __dentro de la cadena de salida y luego tendremos que agregar la cantidad correspondiente de parámetros al final de la printfinstrucción__. Por ejemplo:

```java
System.out.printf("The average of %d and %d is %f", 
    number1, number2, average);
```

Además de los símbolos primarios __%d__ y __%f__, podemos agregar otra información entre el __'%'__ y la letra, que especifica información de formato.

Especificación de dígitos enteros

Por ejemplo, si queremos generar un número entero con un número determinado de dígitos, podemos hacerlo de esta manera:

```java
System.out.printf("The number is %05d", number);
```

donde __05__ significa que el entero tendrá, al menos, 5 dígitos, y si no hay suficientes dígitos en el número, entonces se llenará con ceros. La salida de esta instrucción si el número es __33__,sería __The number is 00033__. Si no ponemos __0__, entonces el número se llenará con espacios en blanco. Entonces esta instrucción:

```java
System.out.printf("The number is %10d", number);
```

Si el número es 33, producirá el siguiente resultado: 
__The number is         33.
__

#### Especificación de dígitos fraccionarios

De la misma forma que formateamos números enteros, podemos formatear números reales. Podemos utilizar el mismo patrón visto anteriormente para especificar la cantidad total de dígitos enteros:

```java
System.out.printf("The number is %3f", number);
```

Pero, además, podemos especificar el número total de dígitos de la fracción añadiendo un punto y el número total deseado, de esta manera:

```java
System.out.printf("The number is %3.3f", number);
```

Entonces, si el número es 3.14159, la salida sería __The number is   3.142__.

## Obtener la entrada del usuario

Para obtener la entrada del usuario, la forma más sencilla puede ser a través del objeto __Scanner__. Necesitamos importarlo __java.util.Scanner__ para poder usarlo y luego creamos un elemento __Scanner__ y llamamos a algunos de sus métodos para leer los datos del usuario. Algunos de ellos son __nextLine(para leer una línea completa de texto hasta que el usuario presione Enter)__ y __nextInt(para leer un número entero explícitamente):__

```java
import java.util.Scanner;
...
public class ClassName
{ 
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        String text = sc.nextLine();	
        sc.close();
    }
```

Existen otros métodos, como __nextFloat, nextBoolean…__ pero son muy similares a __nextInt__, y nos ayudan a leer tipos de datos específicos de la entrada, en lugar de leer textos y luego convertirlos al tipo correspondiente (como __Console.ReadLine__ se hace en C#). Puedes introducir estos datos separados por espacios en blanco o nuevas líneas ( Intro ).

```java
int number1, number2;
number1 = sc.nextInt();
number2 = sc.nextInt();
```

Tenga cuidado al combinar tipos de datos

Supongamos que tienes que leer esta información desde la entrada:
```java
23 43
Hello world
```

Puede pensar que necesita usar el método __nextInt__  dos veces y luego el método __nextLine__ para leer la última cadena, pero este enfoque __NO__ es correcto: cuando usa el método __nextInt__ para leer los valores enteros, no lee el final de la línea que existe más allá del número 43, por lo que, cuando usa nextLineel método una vez, solo lee esta nueva línea, pero no la segunda línea. La secuencia correcta sería esta:

```java
int number1 = sc.nextInt();
int number2 = sc.nextInt();
sc.nextLine(); 
String text = sc.nextLine();
```

La tercera línea lee y descarta la nueva línea después del número 43.

### Uso de System.console().readLine()

Existe una forma adicional de leer datos de la entrada del usuario. 
Consiste en usar __System.console().readLine()__ el método, que es similar al emétodo newLin de Scanner: _lee la línea completa hasta que el usuario presiona Intro_ , por lo que __SIEMPRE__ leemos una cadena con esta instrucción y luego debemos convertirla a su tipo de datos correspondiente:

```java
System.out.println("Write a number:");
String text = System.console().readLine();
int number = Integer.parseInt(text);
```

El principal inconveniente de esta instrucción es que no funciona bien en la terminal de algunos __IDE__, ya que la terminal de este IDE no es una terminal de sistema , por lo que no se puede confiar en ella en determinadas situaciones.

> Ejercicio 1: Cree un programa llamado __FormattedDate__ con una clase con el mismo nombre dentro. El programa le pedirá al usuario que ingrese el día, mes y año de nacimiento (todos los valores son números enteros). Luego, imprimirá su fecha de nacimiento con el formato d/m/y . Por ejemplo, si el usuario ingresa day = 7, month = 11, year = 1990, el programa imprimirá 7/11/1990 .

> __Ejercicio 2__:Crea un programa llamado __GramOunceConverter__ que convierta de gramos a onzas. El programa solicitará al usuario que ingrese un peso en gramos (un número entero) y luego mostrará el peso correspondiente en onzas (un número real), teniendo en cuenta que 1 onza = 28,3495 gramos.

>__Ejercicio 3__: Cree un programa llamado __NumbersStrings__ . Este programa debe pedirle al usuario que ingrese 4 números, que se almacenarán en 4 variables Strings. Luego, el programa unirá el primer par de números en un solo valor entero y el segundo par de números en otro valor entero y luego sumará estos valores. Por ejemplo, si el usuario ingresa los números 23, 11, 45 y 112, entonces el programa creará un primer valor entero de 2311 y un segundo valor entero de 45112. Luego, sumará estos dos valores y obtendrá un resultado final de 47423.

> __Ejercicio 4__:Cree un programa llamado __CircleArea__ que defina una constante flotante llamada __PI__ con el valor _3.14159_. Luego, el programa le pedirá al usuario que ingrese el radio de un círculo y mostrará el área del círculo _( PI* radio * radio)_. Esta área se imprimirá con dos dígitos decimales.
</div>