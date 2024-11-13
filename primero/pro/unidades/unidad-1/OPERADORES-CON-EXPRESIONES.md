<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Operadores y Expresiones en Java)

En Java, los operadores son símbolos especiales que realizan operaciones en uno o más operandos. Las expresiones son combinaciones de variables, valores y operadores que producen un resultado. Aquí tienes una explicación de los operadores más comunes en Java y cómo se utilizan en expresiones.

## Operadores Aritméticos:

- __Suma (+)__: Suma dos operandos.
- __Resta (-)__: Resta el segundo operando del primero.
- __Multiplicación (*)__: Multiplica dos operandos.
- __División (/)__: Divide el primer operando por el segundo.
- __Módulo (%)__: Devuelve el residuo de la división del primer operando por el segundo.

```java

int a = 10, b = 3;
int suma = a + b;        // 13
int resta = a - b;       // 7
int multiplicacion = a * b;  // 30
int division = a / b;    // 3
int modulo = a % b;      // 1
```

## Operadores de Asignación:

- __Asignación (=)__: Asigna el valor de la derecha al operando de la izquierda.
- __Operadores compuestos (+=, -=, *=, /=)__: Realizan la operación y asignan el resultado.

```java

int x = 5;
x += 3;  // x = x + 3;   // 8
```

## Operadores de Comparación:

Igual (==), No Igual (!=), Mayor que (>), Menor que (<), Mayor o Igual que (>=), Menor o Igual que (<=): Comparan dos valores y devuelven un valor booleano.

```java

int num1 = 10, num2 = 5;
boolean igual = (num1 == num2);     // false
boolean mayorQue = (num1 > num2);   // true
```

## Operadores Lógicos:

__AND__ lógico __&&__, OR lógico __||__, NOT lógico __!__: Realizan operaciones lógicas y devuelven un valor booleano.

```java
boolean a = true, b = false;
boolean and = (a && b);    // false
boolean or = (a || b);     // true
boolean not = !a;          // false
```

## Operadores de Incremento y Decremento:

Incremento (++), Decremento (--): Aumentan o disminuyen el valor de la variable en uno.

```java
int contador = 0;
contador++;    // Incrementa en 1
```

## Operador Ternario:

? :: Evalúa una condición y devuelve un valor basado en si la condición es verdadera o falsa.

```java
int edad = 20;
String mensaje = (edad >= 18) ? "Eres mayor de edad" : "Eres menor de edad";
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles

</div>