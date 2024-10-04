# <img src=../../../../images/computer.png width="40"> Code & Learn (Programación: Estructuras Repetitivas en Java (for/while/...)

Los bucles, iteraciones o sentencias repetitivas modifican el flujo secuencial de un programa permitiendo la ejecución reiterada de una sentencia o sentencias. En Java hay tres tipos diferentes de bucles: for, while y do-while.

### Sentencia for

Un for permite la ejecución de un bloque de código delimitado entre llaves un número determinado de veces. La sintaxis de un bucle for es la siguiente:

```java
for (inicio; termino; iteracion)
    sentencia;
```

o si se desean repetir un conjunto sentencias:

```java
for (inicio; termino; iteracion) {
    sentencia_1;
    sentencia_2;
    sentencia_n;
}
```

Es un bucle o sentencia repetitiva que:

1. Ejecuta la sentencia de inicio.
2. Verifica la expresión booleana de término.
    * Si es cierta, ejecuta la sentencia entre llaves y la sentencia de iteración para volver a verificar la expresión booleana de término.
    * Si es falsa, sale del bucle.
    

Flujo de la sentencia for:

![img_02][img_02]

Las llaves sólo son necesarias si se quieren repetir varias sentencias, aunque se recomienda su uso porque facilita la lectura del código fuente y ayuda a evitar errores al modificarlo.

Habitualmente, en la expresión lógica de término se verifica que la variable de control alcance un determinado valor. Por ejemplo:

```bash
for (i = valor_inicial; i <= valor_final; i++) {
    sentencia;
}
```

Es completamente legal en Java declarar una variable dentro de la cabecera de un bucle for. De esta forma la variable (local) sólo tiene ámbito dentro del bucle. 

Ejemplo sencillo:

```java
System.out.println("Tabla de multiplicar del 5");
for (int i =0 ; i <= 10; i++) {
    System.out.println(5 + " * " + i + " = " + 5*i );
}
```

Salida por pantalla al ejecutar el código anterior:

```bash
5 * 0 = 0
5 * 1 = 5
5 * 2 = 10
5 * 3 = 15
5 * 4 = 20
5 * 5 = 25
5 * 6 = 30
5 * 7 = 35
5 * 8 = 40
5 * 9 = 45
5 * 10 = 50
``` 

A continuación se muestra un ejemplo completo de un programa que visualiza la tabla de multiplicar del valor numérico entero introducido como parámetro de la línea de ejecución:

### Sentencia while

Es un bucle o sentencia repetitiva con una condicion al principio. Se ejecuta una sentencia mientras sea cierta una condición. La sentencia puede que no se ejecute ni una sola vez.

Sintaxis:

```java
[inicializacion;]
while (expresionLogica) {
    sentencias;
    [iteracion;]
}
```

Flujo de la sentencia while:

![img_03][img_03]

Ejemplo de programa:

/**
* Ejemplo de sentencia while

```java
public class LeerNumero {
    public static void main (String [] args) {

        Scanner sc = new Scanner(System.in);
        int numero = -1;

        while (numero <= 0) {
            System.out.println("Introduce un numero positivo: ");
            numero = sc.nextInt();
        }

        System.out.println("El numero positivo es " + numero);

        sc.close();
   
   }

}   
```

Ejemplo de ejecución y salida correspondiente por pantalla:

```bash
Introduce un numero positivo:
-5
Introduce un numero positivo:
5
El numer positivo es 5
```

### Sentencia do-while

Es un bucle o sentencia repetitiva con una condicion al final. Se ejecuta una sentencia mientras sea cierta una condición. La diferencia con respecto al bucle while es que __la sentencia se ejecuta al menos una vez__. La sintaxis es la siguiente:

```java
do {
    sentencias;
    [iteracion;]
} while (expresionLogica);
```

Flujo de la sentencia do-while:

![img_04][img_04]


Ejemplo de programa:

```java
/**
* Ejemplo de sentencia while
*/
public class LeerNumero {
    public static void main (String [] args) {

        Scanner sc = new Scanner(System.in);
        int numero = -1;

        do {
            System.out.println("Introduce un numero positivo: ");
            numero = sc.nextInt();
        } while (numero <= 0);

        System.out.println("El numero positivo es " + numero);

        sc.close();
   
   }

}   
```

## Arrays Unidimensionales

Un array es una estructura para guardar un conjunto de objetos de la misma clase. Se accede a cada elemento individual del array mediante un número entero denominado índice. 0 es el índice del primer elemento y n-1 es el índice del último elemento, siendo n, la dimensión del array.

![img_05][img_05]

Para declarar un array se usa la siguiente sintaxis:

```java
    tipo_de_dato[] nombre_del_array;
```

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

Los arrays se pueden declarar, crear e inicializar en una misma línea, de la siguiente manera:

```java
int[] numeros = {2, -4, 15, -25};

String[] nombres = {"Juan", "José", "Miguel", "Antonio"};
```

Los arrays se pueden declarar, crear e inicializar en una misma línea, del siguiente modo

```java
int[] numeros = {2, -4, 15, -25};

String[] nombres = {"Juan", "José", "Miguel", "Antonio"};
```

Para imprimir a los elementos de array nombres  se escribe

```java
for(int i=0; i < nombres.length; i++){
    System.out.println(nombres[i]);
}
```

Un _array_ tiene la propiedad __length__, que retorna su número de elementos. 

### Ejemplo: Invertir un array

Para invertir un array en Java, necesitamos intercambiar los elementos de tal forma que el primero pase a ocupar la posición del último, el segundo ocupe el lugar del penúltimo, y así sucesivamente. Este proceso continúa hasta que llegamos a la mitad del array.

#### Paso 1: Array original

El array comienza con los elementos en sus posiciones originales. La estructura del array es la siguiente:

```plaintext
Índice:  0   1   2   3   4
        [1] [2] [3] [4] [5]
```

#### Paso 2: Creamos un array del mismo tamaño

La forma más sencilla es crear un nuevo array:

__arrayFinal__:

```plaintext
Índice:  0   1   2   3   4
        [0] [0] [0] [0] [0]
```

#### Paso 3: Recorrer el array orignal y copiar la el valor de la posición en el lugar opuesto

El siguiente paso es recorrer el primer array, y colocar su valor en la posición opuesta.

```plaintext
Índice:  0   1   2   3   4
        [4] [0] [0] [0] [0]
```

> __¿Cómo lo conseguimos?__

```java
for (int i= 0; i < arrayOriginal.lenght; i++) {
   int valor = valor arrayOriginal[i];
   arrayFinal[arrayOriginal.length-1-i] 
}
```

Veamos como cambia:

```bash
i = 0;
arrayOriginal.length = 5;

valor arrayOriginal[0] = 0;

arrayFinal[arrayOriginal.length-1-0] => arrayFinal[5-1-0] => arrayFinal[4]
arrayFinal[4] = 0;
```

```bash
i = 1;
arrayOriginal.length = 5;

valor arrayOriginal[1] = 1;

arrayFinal[arrayOriginal.length-1-1] => arrayFinal[5-1-1] => arrayFinal[3]
arrayFinal[4] = 1;
```

```bash
i = 2;
arrayOriginal.length = 5;

valor arrayOriginal[2] = 2;

arrayFinal[arrayOriginal.length-1-2] => arrayFinal[5-1-2] => arrayFinal[2]
arrayFinal[4] = 2;
```

___... y asi consecutivamente___.

#### Otra forma de hacerlo más optima

__Original__

```plaintext
Índice:  0   1   2   3   4
        [1] [2] [3] [4] [5]
```

Se trata de intercambiar los valores de las posiciones.

##### Primer intercambio cuando i = 0

En la primera iteración, el primer elemento (índice 0) se intercambia con el último elemento (índice 4). El array ahora se verá así:

```plaintext
Índice:  0   1   2   3   4
        [5] [2] [3] [4] [1]

```

##### Intercambio cuando i = 1

En la segunda iteración, el segundo elemento (índice 1) se intercambia con el penúltimo (índice 3). El array ahora se verá de la siguiente manera:

```plaintext
Índice:  0   1   2   3   4
        [5] [4] [3] [2] [1]
```

##### Finalización cuando i = 2

Cuando i llega al valor 2, hemos llegado a la mitad del array. En este punto, todos los elementos han sido intercambiados correctamente y no es necesario realizar más operaciones.

```java
public class Main {
    public static void main(String[] args) {
        // Declaración e inicialización de un array de enteros
        int[] numeros = {1, 2, 3, 4, 5};

        int tamanioArray = array.length;
        for (int i = 0; i < tamanioArray / 2; i++) {
            int temp = array[i]; // Guardar temporalmente el valor del primer elemento
            array[i] = array[tamanioArray - i - 1]; // Intercambiar con el último
            array[tamanioArray - i - 1] = temp; // Asignar el valor temporal al último
        }
        
        for (int numero : array) {
            System.out.print(numero + " ");
        }
        System.out.println();
    }
}
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../LICENSE.md) para detalles.