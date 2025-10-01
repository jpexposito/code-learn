<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Trabajo con elementos y ordenación)

Supongamos que tenemos 5 números, puedes utilizar un enfoque simple de comparación e intercambio. Aquí tienes un ejemplo en Java:

```java
public class OrdenarNumeros {

    public static void main(String[] args) {
        int num1 = 30;
        int num2 = 10;
        int num3 = 50;
        int num4 = 20;
        int num5 = 40;

        if (num1 > num2) { 
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }
        if (num2 > num3) { 
            int temp = num2;
            num2 = num3;
            num3 = temp;
        }
        if (num3 > num4) {
            int temp = num3;
            num3 = num4;
            num4 = temp; 
        }
        if (num4 > num5) {
            int temp = num4;
            num4 = num5;
            num5 = temp;
        }
        if (num1 > num2) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
            }
        if (num2 > num3) {
            int temp = num2;
            num2 = num3;
            num3 = temp;
        }
        if (num3 > num4) {
            int temp = num3;
            num3 = num4;
            num4 = temp;
        }
        if (num1 > num2) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }
        if (num2 > num3) {
            int temp = num2;
            num2 = num3;
            num3 = temp;
        }
        if (num1 > num2) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }

        System.out.println("Números ordenados: " + num1 + ", " + num2 + ", " + num3 + ", " + num4 + ", " + num5);
    }
}
```

## Arrays Unidimensionales

Un array es una estructura para guardar un conjunto de objetos de la misma clase. Se accede a cada elemento individual del array mediante un número entero denominado __índice (index en inglés)__. __0__ es el índice del primer elemento y __n-1__ es el índice del último elemento, siendo __n__, la dimensión del array.

Para declarar un array se usa la siguiente sintaxis:

```java
tipo_de_dato[] nombre_del_array;
```

Para declarar y inicializar un array en Java, puedes hacerlo de la siguiente manera:

| Tipo de Dato | Nombre del Array | Tamaño del Array | Elementos |
|--------------|-------------------|-------------------|-----------|
| int          | numeros           | 4                 | [2, -4, 15, -25] |

Por, ejemplo, para declarar un array de enteros escribimos lo siguiente:

```java
int[] numeros;
```

Para crear un array de 4 números enteros escribimos lo siguiente:

```java
numeros = new int[4];
```

La declaración y la creación del array se puede hacer en una misma línea:

```java
int[] numeros = new int[4];
```

Para inicializar el array de 4 enteros escribimos lo siguiente:

```java
int[] numeros = new int[4];
numeros[0] = 2;
numeros[1] = -4;
numeros[2] = 15;
numeros[3] = -25;
```

| Índice | Valor |
|-------|-------|
| 0     | 2 |
| 1     | -4 |
| 2     | 15 |
| 3     | -25 |

Los arrays se pueden declarar, crear e inicializar en una misma línea, de la siguiente manera:

```java
int[] numeros = {2, -4, 15, -25};
String[] nombres = {"Juan", "José", "Miguel", "Antonio"};
```

Para imprimir a los elementos de array nombres se escribe

```java
for(int i=0; i < nombres.length; i++){
    System.out.println(nombres[i]);
}
```

Un array tiene la propiedad __length__, que retorna su número de elementos.

Veamos algunos ejemplos:

### Inicializar y Acceder a Elementos en un Array

```java
public class EjemploArrays {

    public static void main(String[] args) {
        int[] numeros = {10, 20, 30, 40, 50};

        System.out.println("El primer elemento es: " + numeros[0]);
        System.out.println("El segundo elemento es: " + numeros[1]);
    }
}
```

> __Importante__: Definicimos un array de tamaño __5__ (__lenght__).
La posición __numeros[0]__ contiene el valor __10__.
La posición __numeros[1]__ contiene el valor __20__.

El código:

```java
System.out.println("El primer elemento es: " + numeros[0]);
System.out.println("El segundo elemento es: " + numeros[1]);
```

```code
El primer elemento es: 10
El segundo elemento es: 20
```

### Recorrer un Array con un Bucle

```java
public class EjemploArrays {

    public static void main(String[] args) {
        String[] nombres = {"Juan", "María", "Carlos", "Elena"};

        for (String nombre : nombres) {
            System.out.println("Nombre: " + nombre);
        }
    }
}
```

> __Importante__: Definicimos un array de tamaño __4__ (__lenght__).
La posición __nombres[0]__ contiene el valor __Juan__.
La posición __nombres[1]__ contiene el valor __María__, y así sucesivamente.


El bucle:

```java
for (String nombre : nombres) {
    System.out.println("Nombre: " + nombre);
}
```

Muestra la siguiente salida:

```code
Nombre: Juan
Nombre: María
Nombre: Carlos
Nombre: Elena
```

### Encontrar el Valor Máximo en un Array:

```java
public class EjemploArrays {

    public static void main(String[] args) {
        int[] numeros = {30, 10, 50, 20, 40};

        int maximo = numeros[0];
        for (int i = 1; i < numeros.length; i++) {
            if (numeros[i] > maximo) {
                maximo = numeros[i];
            }
        }

        System.out.println("El valor máximo es: " + maximo);
    }
}
```

> __Importante__: Definicimos un array de tamaño __4__ (__lenght__).
Donde los valores son: __30,10__,... .

El algoritmo realiza:

- Fija un valor máximo al inicio, cuyo valor es primer elemento del array (__30__).
- Comenzamos recorriendo el array en la posición __1__, y no en la __0__, dado que es el valor máquino y no tiene sentido __->__ (__30 = 30__).
- Si el elmento que se hace refrencia a través del __índice__ es mayor que el __máximo__ en ese momento, se actualiza.
- El __máximo__ se encuentra en cunado el __índice__ vale __2__ y se hace referencia al valor _50_ -> __(numeros[2] = 50)__.

### Copiar un Array a Otro

```java
public class EjemploArrays {

    public static void main(String[] args) {
        int[] original = {1, 2, 3, 4, 5};
        int[] copia = new int[original.length];

        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i];
        }

        System.out.println("Copia del array original:");
        for (int elemento : copia) {
            System.out.print(elemento + " ");
        }
    }
}
```

> __Importante__: Definicimos un array de tamaño __4__ (__lenght__).

En esta parte del código se define el tamaño del __array copia__, que tiene el valor del tamaño del __array original__.

```java
int[] copia = new int[original.length];
```

El bucle:

```java
for (int i = 0; i < original.length; i++) {
    copia[i] = original[i];
}
```

copia el contenido de un array en el otro.

### Copiar un Array en orden inverso:

```java
int[] arrayOriginal = {1, 2, 3, 4, 5};

int[] arrayInverso = new int[arrayOriginal.length];

for (int i = 0; i < arrayOriginal.length; i++) {
    arrayInverso[i] = arrayOriginal[arrayOriginal.length - 1 - i];
}
```

| Array Original | Array Inverso |
|----------------|---------------|
| `[1, 2, 3, 4, 5]` | `[0, 0, 0, 0, 0]` |

_Iterando sobre el array original en orden inverso_:

- Para __i = 0__:
arrayInverso[0] = arrayOriginal[4] => [__5__, 0, 0, 0, 0]

- Para __i = 1__:
arrayInverso[1] = arrayOriginal[3] => [__5, 4__, 0, 0, 0]

- Para __i = 2__:
arrayInverso[2] = arrayOriginal[2] => [__5, 4, 3__, 0, 0]

- Para __i = 3__:
arrayInverso[3] = arrayOriginal[1] => [__5, 4, 3, 2__, 0]

- Para __i = 4__:
arrayInverso[4] = arrayOriginal[0] => [__5, 4, 3, 2, 1__]

### Ordenar un Array:

```java
import java.util.Arrays;

public class EjemploArrays {

    public static void main(String[] args) {
        int[] numeros = {30, 10, 50, 20, 40};

        Arrays.sort(numeros);

        System.out.println("array ordenado:");
        for (int numero : numeros) {
            System.out.print(numero + " ");
        }
    }
}
```

> __Importante__: Definicimos un array de tamaño __4__ (__lenght__).

En este ejemplo se hace uso de la sentencia __Array.sort__ que recibe una __array__ como parámetro de entrada, y realiza la ordenación.

```java
Arrays.sort(numeros);
```

__Sin Array.sort___:

```java
public class EjemploBubbleSort {

    public static void main(String[] args) {
        int[] numeros = {30, 10, 50, 20, 40};

        bubbleSort(numeros);

        System.out.println("array ordenado:");
        for (int numero : numeros) {
            System.out.print(numero + " ");
        }
    }

    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }
}
```

En este ejemplo, la función __bubbleSort__ implementa el algoritmo de ordenación _Bubble Sort_. Funciona _comparando elementos adyacentes y los intercambia si están en el orden incorrecto. Este proceso se repite hasta que el array esté completamente ordenado_.

Por favor, ten en cuenta que __Bubble Sort__ __NO__ es la opción más eficiente para grandes conjuntos de datos, ya que tiene una complejidad de tiempo de __O(n^2)__. Para conjuntos de datos más grandes, se recomiendan algoritmos más eficientes como __QuickSort o MergeSort__.

# Arrays Bidimensionales

Una matriz bidimensional en __es simplemente un array de arrays__, lo que significa que contiene otros arrays como sus elementos. __Cada uno de estos "arrays internos" representa una fila de la matriz__.

## Matrices Bidireccionales

En Java, una matriz bidimensional se declara y se inicializa de la siguiente manera:

```java
tipoDato[][] nombreMatriz = new tipoDato[numFilas][numColumnas];
```

Donde __tipoDato__ es el tipo de datos que contendrá la matriz, __nombreMatriz__ es el nombre de la matriz, __numFilas__ es el número de filas y __numColumnas__ es el número de columnas. Su definicial es similar a los __Array´s Unidimensionales__. 

### Ejemplo

Supongamos que queremos crear una matriz bidimensional de enteros de 2x3 e inicializarla con algunos valores. Aquí está el código Java para hacerlo:

```java
int[][] matriz = {{1, 2, 3}, {4, 5, 6}};
```

Esta matriz se vería como sigue:

```code
|---|---|---|
| 1 | 2 | 3 |
|---|---|---|
| 4 | 5 | 6 |
|---|---|---|
```

La matriz matriz es de tipo __int__, tiene __2__ filas y __3__ columnas, y está inicializada con los valores del __1 al 6__ _distribuidos en las filas y columnas_.

### Acceso a Elementos de una Matriz Bidimensional

Para acceder a los elementos de una matriz bidimensional, utilizamos dos índices: uno para la fila y otro para la columna. Por ejemplo, para acceder al elemento en la segunda fila y tercera columna de la matriz anterior, usaríamos:

```java
// Esto asignaría el valor 6 a la variable "elemento"
int elemento = matriz[1][2]; 
```

### Modificación de Elementos de una Matriz Bidimensional

Para modificar un elemento de una matriz bidimensional, también utilizamos dos índices. Por ejemplo, para cambiar el valor en la primera fila y segunda columna a 10, usaríamos:

```java
matriz[0][1] = 10;
```

### Recorriendo una Matriz Bidimensional

Para recorrer una matriz bidimensional, utilizamos dos bucles for. El primer bucle itera sobre las filas y el segundo bucle itera sobre las columnas. Por ejemplo, para imprimir todos los elementos de la matriz, podríamos usar:

```java
// Tenemos el iterados i para las filas
for (int i = 0; i < matriz.length; i++) {
    //Tenemos el iterador j para las columnas
    for (int j = 0; j < matriz[i].length; j++){
        System.out.print(matriz[i][j] + " ");
    }
    //Salta de fila
    System.out.println();
}
```

Este código imprimiría:

```code
1 2 3 
4 5 6 
```

## Matrices tridireccionales

Una matriz tridimensional en es un array de arrays de arrays, lo que significa que contiene otros __arrays bidimensionales__ como sus elementos. __Cada uno de estos "arrays internos" representa una matriz bidimensional__, que a su vez representa una capa de la matriz tridimensional.

Representación

En Java, una matriz tridimensional se declara y se inicializa de la siguiente manera:

```code
tipoDato[][][] nombreMatriz = new tipoDato[numCapas][numFilas][numColumnas];
```

Donde __tipoDato__ es el tipo de datos que contendrá la matriz, __nombreMatriz__ es el nombre de la matriz, __numCapas__ es el número de capas, __numFilas__ es el número de filas y __numColumnas__ es el número de columnas.

### Ejemplo de Matriz Tridimensional

Supongamos que queremos crear una matriz tridimensional de enteros de __2x3x2__ e _inicializarla con algunos valores_.

```java
int[][][] matriz = {{{1, 2}, {3, 4}, {5, 6}}, {{7, 8}, {9, 10}, {11, 12}}};
```

Esta matriz se vería como sigue:

```yaml
Capa 1:
| 1  2 |
| 3  4 |
| 5  6 |

Capa 2:
| 7  8 |
| 9 10 |
| 11 12 |

```

La matriz matriz es de tipo int, tiene _2 capas, 3 filas y 2 columnas__, y está inicializada con los valores del __1 al 12__ distribuidos en las __capas, filas y columnas__.

### Acceso a Elementos de una Matriz Tridimensional

```java
int elemento = matriz[1][2][0]; // Esto asignaría el valor 11 a la variable "elemento"
```

#### Modificación de Elementos de una Matriz

```java
matriz[0][1][1] = 20;
```

#### Recorriendo una Matriz Tridimensional

```java

// Tenemos el iterados i para las capas
for (int i = 0; i < matriz.length; i++) {
    System.out.println("Capa " + (i+1) + ":");
    // Tenemos el iterados j para las filas
    for (int j = 0; j < matriz[i].length; j++) {
        // Tenemos el iterados k para las columnas
        for (int k = 0; k < matriz[i][j].length; k++) {
            System.out.print(matriz[i][j][k] + " ");
        }
        //Salta de fila
        System.out.println();
    }
    //Salta de capa
    System.out.println();
}
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles

</div>