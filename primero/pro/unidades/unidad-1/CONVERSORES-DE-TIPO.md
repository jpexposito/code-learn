<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Tipos de Datos en Java)

__Java__ es un __lenguaje de programación fuertemente tipado__, lo que significa que __cada variable y expresión tiene un tipo de datos asociado__. Los tipos de datos en Java se pueden clasificar en dos categorías principales: primitivos y de referencia.

## Tipos de Datos Primitivos

Los tipos de datos primitivos representan valores simples. Java tiene ocho tipos primitivos:

- **Enteros:**
  - `byte`: 8 bits, rango de -128 a 127.
  - `short`: 16 bits, rango de -32,768 a 32,767.
  - `int`: 32 bits, rango de -2^31 a 2^31 - 1.
  - `long`: 64 bits, rango de -2^63 a 2^63 - 1.

- **Punto Flotante:**
  - `float`: 32 bits, precisión simple.
  - `double`: 64 bits, precisión doble.

- **Caracteres:**
  - `char`: 16 bits, representa __un solo carácter__ Unicode.

- **Booleano:**
  - `boolean`: Representa valores de verdad, `true` o `false`.

## Tipos de Datos de Referencia

Los tipos de datos de referencia se refieren a objetos. Algunos de los tipos de datos de referencia comunes incluyen:

- **Cadenas (String):**
  - `String`: Secuencia de caracteres.

- **Array´s:**
  - Ejemplo: `int[] numeros;`

- **Clases Personalizadas:**
  - Definidas por el usuario, por ejemplo, `MiClase`.

## Casting (Transformaciones de Tipo)

En Java, es posible convertir un tipo de dato a otro mediante casting. Existen dos tipos principales:

- **Casting Implícito:**
  - Ocurre automáticamente cuando __no hay pérdida de datos__, por ejemplo, convertir un `int` a `double`.
  
    ```java
    // ## Ejemplo de Casting Implícito
        int entero = 5;
        float flotante = entero; // Casting implícito
    ```
    >
    > __Nota__:En este ejemplo, se realiza un casting implícito al convertir un __int__ en un __float__. ___No hay pérdida de datos en este caso___.

- **Casting Explícito:**
  - Requiere una intervención manual y __puede implicar pérdida de datos__, por ejemplo, convertir un `double` a `int`.

    ```java
    double numeroDoble = 123.456;
    int numeroEntero = (int) numeroDoble; // Casting explícito
    ```
    >
    > __Nota__: En este caso, se realiza un casting explícito para convertir un __double__ en un __int__. ___Existe la posibilidad de pérdida de datos, ya que la parte decimal se trunca___.

- **Casting entre Clases**
  - Requiere el __control de herencia__ entre clases. Además se podrán emplear librerías externas para automatizar el proceso. Se verá con detalle en el futuro. Un ejemplo sería el siguiente:

    ```java
    class Animal { }
    class Perro extends Animal { }

    Animal animal = new Perro(); // Casting implícito
    Perro perro = (Perro) animal; // Casting explícito
    ```

    >__Nota__: En este ejemplo, se muestra cómo realizar __casting entre clases relacionadas por herencia__. El casting explícito se utiliza para indicar al compilador que estás consciente de la relación entre las clases.

Es crucial entender los tipos de datos en Java y cómo se manejan las conversiones para evitar errores y garantizar un código robusto.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles

</div>