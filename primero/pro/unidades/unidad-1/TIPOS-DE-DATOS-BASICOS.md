<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Tipos de datos básicos)

En secciones anteriores hemos hablado sobre variables. Hemos aprendido que podemos usarlas para almacenar valores, y que estos valores pueden ser de diferentes tipos. En esta sección vamos a aprender sobre los tipos de datos básicos que proporciona Java y cómo podemos usarlos para almacenar valores en nuestros programas.

## Tipos numéricos

Hay dos tipos numéricos principales en Java:

- __Valores enteros__, que pueden representarse mediante bytelos tipos de datos short, intolong
- __Valor real__, que puede representarse mediante tipos de datos floato double.

### Tipos de datos enteros

Como hemos dicho antes, podemos elegir entre 4 tipos de datos diferentes para representar valores enteros. La elección puede venir determinada por el rango de valores con los que necesitamos trabajar. En esta tabla se puede ver el rango de valores permitidos por cada tipo de dato:

| __Tipo de datos__ | __Memoria (bytes)__ | __Rango permitido__                        |
|---------------|-----------------|----------------------------------------|
| byte          | 1               | -128 a 127                             |
| short         | 2               | -65.536 a 65.535                       |
| int           | 4               | -2.147.483.648 a 2.147.483.647          |
| long          | 8               | hasta números de 18-19 dígitos         |

Por ejemplo, si queremos gestionar la _edad de una persona_, podríamos utilizar una variable __int__, pero desperdiciaríamos memoria, ya que esta edad suele ser _inferior a 100, y sólo necesitaríamos un único byte para almacenarla. byte En su lugar, podríamos utilizar una variable:

```java
byte age = 34;
```

Sin embargo, si queremos almacenar el precio de un objeto, debemos utilizar una variable __short__ o incluso una variable __int__, dado que permite valores mayores:

```java
short price = 4200;
int higherPrice = 2223424;
```

### Tipos de datos reales

Si queremos trabajar con números reales, Java proporciona dos tipos de datos diferentes, cada uno con su propio espacio de memoria y rango:
- __float__ El tipo de datos necesita 4 bytes de memoria y nos permite gestionar números con hasta 6 o 7 números significativos. Por ejemplo, si queremos almacenar el valor PI en una variable flotante con solo 4 o 5 dígitos fraccionarios (es decir, 3.14159 ), podemos usar una variable float.

```java
float pi = 3.14159;
```

- __double__. Este tipo de datos necesita 8 bytes de memoria y nos permite manejar números con hasta 15 números significativos. De esta manera podemos almacenar más dígitos fraccionarios, si queremos:

```java
double pi = 3.14159265359;
```

En cuanto a float, las variables, si queremos asignarles un valor directo, debemos especificar un __f__ símbolo al final de dicho valor. Por lo tanto, el ejemplo dado anteriormente debe escribirse así (de lo contrario, obtendremos un error de compilación):

```java
float pi = 3.14159f;
```

### El problema del desbordamiento

Cuando trabajamos con números, es posible que necesitemos realizar algunas operaciones que excedan el rango máximo permitido por un tipo de dato. Por ejemplo, si trabajamos con dos bytevalores y los sumamos, es posible que excedamos el rango máximo permitido por byte el tipo de dato, que es __127__. Esta situación se denomina desbordamiento .

Por lo tanto, debemos tener cuidado con los tipos de datos que elegimos para cada situación, teniendo en cuenta las diferentes operaciones que esperamos realizar con estas variables.

## Tipos de texto

Para tratar con textos, Java proporciona dos tipos de datos:

- __char__ Tipo de datos si queremos utilizar ___caracteres individuales o símbolos___.
- __String__ Tipo de dato si queremos gestionar __textos complejos__ _(con más de un carácter o símbolo)_.
En cuanto al tipo __char__, tiene una longitud de 2 bytes, por lo que podemos representar cualquier carácter o símbolo posible. Simplemente declaramos la variable correspondiente y le asignamos el carácter representado entre comillas simples:

```java
char symbol = 'a';
```

Si queremos trabajar con textos más largos, entonces utilizamos variables __String__, especificando el texto entre comillas dobles:

```java
String text = "Hello world";
```

## Secuencias de escape

Existen algunos caracteres especiales que no se pueden representar fácilmente con el teclado en un archivo fuente. Por ejemplo, el carácter de nueva línea o incluso las comillas dentro de un texto citado. Para ello, podemos utilizar secuencias de escape , es decir, símbolos especiales que representan estos elementos no editables. A continuación, se muestra una lista de los caracteres o secuencias de escape más populares:

| Secuencia | Significado         |
|-----------|---------------------|
| \n        | Nueva línea         |
| \t        | Tabulación          |
| \"        | Comillas dobles      |
| \'        | Comillas simples     |
| \\        | Barra invertida      |

Estas secuencias de escape se pueden colocar dentro de un valor de carácter o cadena:

```java
char newLine = '\n';
String message = "Hello world.\n\"Quoted text\"";
```

### Operaciones con caracteres

Podemos realizar algunas operaciones básicas con caracteres. Hay que tener en cuenta que Java trata internamente los caracteres como valores numéricos, asignando a cada carácter un código numérico. Por ejemplo, los caracteres del alfabeto se representan mediante valores numéricos consecutivos, de aa z. De esta forma, si sumamos 3 a avalor, obtendremos dvalor:

```java
char symbol = 'a';
symbol += 3;
```

También podemos utilizar +el operador en textos ( cadenas ), pero en este caso no estamos haciendo ninguna suma, solo estamos concatenando textos o expresiones. Esta expresión produce el texto __“Hola3”__:

```java
String text = "Hello" + 3;
```

Tenga en cuenta que no se pueden combinar operaciones aritméticas y de texto directamente en una sola línea. La siguiente expresión produce un resultado de “Hello32”:

String text = "Hello" + 3 + 2;
Si desea calcular la suma y luego concatenar el resultado, debe priorizar la suma utilizando paréntesis. Esta expresión produce un resultado de “Hola5”:

```java
String text = "Hello" + (3 + 2);
```

## Conversión entre tipos de datos

A veces necesitamos convertir un valor de un tipo en otro diferente. La forma en que realizamos este paso depende de los tipos involucrados.

### Algunas conversiones básicas

#### Conversión de tipos

Las conversiones entre valores numéricos son bastante sencillas. Solo tenemos que hacer un typecast , es decir, especificar entre paréntesis el tipo de dato al que queremos convertir la expresión. En este ejemplo, estamos convirtiendo piun valor real a un entero (por lo que obtenemos 3 como resultado final):

```java
float pi = 3.1416f;
int piInteger = (int)pi;
```

También se puede realizar el paso inverso. En este caso, convertiremos un valor entero en uno doble (el valor final será 5,0):

```java
int number = 5;
double realNumber = (double)number;
```

Sin embargo, este paso NO es necesario si el tipo de origen es más pequeño que el tipo de destino. Por ejemplo, byteno es necesario convertir a en int:

```java
byte value = 3;
int number = value;
```

La conversión de tipos puede ser útil, por ejemplo, para convertir divisiones de números enteros en números reales. Este ejemplo divide dos valores enteros, pero, como estamos convirtiendo uno de ellos en __float__, el resultado final será un número real, con los dígitos fraccionarios correspondientes, y se puede almacenar en una variable __float__:

```java
float result = (float) 3 / 2;
```

En general, cada operación aritmética intenta producir un resultado del mismo tipo de sus operandos (si dividimos dos enteros, obtenemos un resultado entero, por ejemplo). Sin embargo, en ciertas operaciones, como las sumas o multiplicaciones, Java intenta convertir el resultado a un tipo superior, y necesitamos convertirlo en un tipo. En este ejemplo, intentamos sumar dos valores de bytes, pero Java intenta convertir el resultado a int, por lo que debemos especificar que queremos seguir usando a bytecomo resultado (aunque podríamos provocar un desbordamiento):

```java
byte a = 3, b = 2;
byte result = (byte)(a + b);
```

Si mezclamos dos tipos diferentes en una operación aritmética, entonces Java convierte el resultado al mayor de ellos. Esta multiplicación obtiene un floatnúmero porque uno de los operandos es float:

```java
float a = 3.5f;
int b = 4;
float result = a * b;
```

### Conversión de / a cadena

En algunas situaciones, podemos leer valores numéricos de fuentes textuales, como un archivo de texto o una entrada del usuario. En este caso, necesitamos convertir el texto en el valor numérico correspondiente . Para ello, Java proporciona algunas instrucciones útiles. Aquí puedes ver las más útiles:

- __Integer.parseInt__ convierte un valor de texto en int:

```java
int value = Integer.parseInt("23");
```

- __Float.parseFloat, Double.parseDouble, Byte.parseByte, Short.parseShort y Long.parseLong__ hace lo mismo con sus tipos de datos correspondientes:

```java
float value = Float.parseFloat("3.1416");
```

Si queremos convertir un valor numérico en una cadena , podemos elegir una de estas soluciones:

Concatenar el valor numérico con una cadena vacía "":

```java
int number = 23;
String text = "" + number;
```

Utilice ___String.valueOf__ la instrucción para convertir el valor especificado en una cadena:

```java
int number = 23;
String text = String.valueOf(number);
```

> __Ejercicio 1__: Crea un programa llamado Ages.java que:
>
> - Define dos variables byte para almacenar tu edad y la edad de un amigo.
> Define otra variable byte para almacenar la suma de ambas edades (es posible que tengas que convertir el resultado a un tipo de variable)
> - Define una variable __float__ para almacenar el promedio de estas edades, incluidos los dígitos fraccionarios.
> - Imprime el mensaje __“El promedio de edad es” seguido del promedio calculado en el paso anterior__

### Tipos Booleanos

Java proporciona el tipo `boolean` para manejar valores lógicos, que solo pueden ser `true` o `false`. Estos valores booleanos suelen ser utilizados en condiciones y ciclos de control de flujo.

#### Declaración y Asignación de `boolean`

```java
boolean esJavaDivertido = true;
boolean esMayor = 10 > 5;  // true
boolean esIgual = (5 == 5);  // true
```

#### Ejemplos de Operaciones con `boolean`

##### Comparaciones

El resultado de las comparaciones siempre será un valor booleano (`true` o `false`).

```java
boolean esMayor = 20 > 15;      // true
boolean esMenor = 10 < 5;       // false
boolean esIgual = 10 == 10;     // true
boolean esDistinto = 5 != 10;   // true
```

#### Operadores Lógicos

Los operadores lógicos permiten combinar múltiples condiciones.

- __AND (&&)__: Verdadero si ambas condiciones son verdaderas.
- __OR (||)__: Verdadero si al menos una de las condiciones es verdadera.
- __NOT (!)__: Invierte el valor lógico.

```java
boolean resultadoAnd = (5 > 3) && (8 > 6);   // true
boolean resultadoOr = (5 > 10) || (8 > 6);   // true
boolean resultadoNot = !(5 > 3);             // false
```

</div>