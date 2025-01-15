<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Excepciones y/o errores controlados)

<img src=images/excetions.png width="400">

En Java, las excepciones son eventos que interrumpen el flujo normal de un programa. Las excepciones pueden ocurrir por diversas razones, como errores de entrada/salida, intentos de dividir entre cero, o el acceso a un índice fuera de los límites de un arreglo. Java proporciona un mecanismo robusto para manejar estas excepciones mediante la clase `Exception` y sus subclases.

## Introducción a las Excepciones

En un programa Java, las excepciones son eventos anormales que pueden ocurrir durante la ejecución del programa y que interrumpen su flujo normal. Las excepciones pueden ser causadas por diversos problemas, tales como un intento de acceder a una variable no inicializada, problemas con operaciones de entrada/salida, o problemas con operaciones matemáticas como dividir entre cero.

Java proporciona un mecanismo para manejar estas excepciones, lo que permite que el programa no termine abruptamente, sino que pueda gestionarlas de manera controlada.

## Jerarquía de Excepciones

<img src=images/type-exceptions.png width="400">


En Java, todas las excepciones son objetos que heredan de la clase base `Throwable`. La jerarquía de excepciones se puede dividir en dos categorías principales: `Exception` y `Error`.

### Clase Throwable

La clase `Throwable` es la raíz de la jerarquía de excepciones en Java. Todas las excepciones y errores en Java heredan de esta clase. Existen dos subclases principales de `Throwable`:

- **Exception**: Para condiciones excepcionales que un programa puede manejar.
- **Error**: Para errores que no deberían ser manejados por el programa (problemas graves como errores del sistema).

```java
// Throwable no se utiliza directamente, pero todas las excepciones y errores la heredan.
Throwable throwable = new Exception("Este es un error");
```

### Clase Exception

La clase `Exception` es una subclase de `Throwable` y es la que comúnmente se usa para representar las excepciones que un programa puede capturar y manejar. `Exception` tiene varias subclases que representan diferentes tipos de excepciones, como `IOException`, `NullPointerException`, `ArithmeticException`, entre otras.

```java
try {
    throw new Exception("Esto es una excepción comprobada");
} catch (Exception e) {
    System.out.println(e.getMessage());
}
```

### Clase Error

La clase `Error` también es una subclase de `Throwable`, pero generalmente se usa para representar errores graves del sistema que no se deben manejar directamente en el código. Ejemplos de errores son `OutOfMemoryError` o `StackOverflowError`. Estos errores normalmente indican que el sistema está en un estado irrecuperable.

```java
// Esto representaría un error grave que no debe ser manejado normalmente.
try {
    throw new Error("Este es un error grave del sistema");
} catch (Error e) {
    System.out.println("Error no manejado: " + e.getMessage());
}
```

## Tipos de Excepciones

En Java, las excepciones se dividen en dos tipos principales:

### Excepciones Comprobadas (Checked Exceptions)

Las excepciones comprobadas son aquellas que deben ser manejadas explícitamente en el código. Estas excepciones son verificadas por el compilador, lo que significa que si un método puede generar una excepción comprobada, debe declarar dicha excepción con la palabra clave `throws` o capturarla con un bloque `try-catch`. Ejemplos de excepciones comprobadas son `IOException` o `SQLException`.

```java
try {
    // Intentamos leer un archivo que podría no existir
    FileReader fr = new FileReader("archivo_inexistente.txt");
} catch (IOException e) {
    System.out.println("Se ha producido un error de entrada/salida: " + e.getMessage());
}
```

### Excepciones No Comprobadas (Unchecked Exceptions)

Las excepciones no comprobadas son aquellas que no requieren ser declaradas ni capturadas por el programa. Estas excepciones son subclases de `RuntimeException` y pueden ocurrir en cualquier momento durante la ejecución del programa. El compilador no verifica estas excepciones. Ejemplos de excepciones no comprobadas son `NullPointerException`, `ArrayIndexOutOfBoundsException`, y `ArithmeticException`.

```java
try {
    int[] arr = new int[5];
    arr[10] = 100;  // Esto lanzará una ArrayIndexOutOfBoundsException
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Índice fuera de los límites: " + e.getMessage());
}
```

## Manejo de Excepciones

Java proporciona mecanismos para manejar excepciones de forma eficiente, lo que permite que el programa recupere el control y continúe ejecutándose incluso si se produce un error.

### Bloques try-catch

Los bloques `try-catch` se utilizan para manejar excepciones en Java. El código que podría generar una excepción se coloca dentro del bloque `try`, y el bloque `catch` captura y maneja esa excepción si ocurre.

La estructura básica de un bloque `try-catch` es la siguiente:

1. **`try`**: Contiene el código que puede generar una excepción.
2. **`catch`**: Contiene el código que maneja la excepción si se produce.

```java
try {
    int result = 10 / 0;  // Esto lanza una ArithmeticException
} catch (ArithmeticException e) {
    System.out.println("No se puede dividir entre cero");
}
```

### Palabra clave throws

La palabra clave `throws` se utiliza en la declaración de un método para indicar que el método puede generar una o más excepciones. Si un método puede generar una excepción comprobada, debe declararla utilizando `throws`.

```java
public void miMetodo() throws IOException {
    throw new IOException("Archivo no encontrado");
}
```

### Palabra clave throw

La palabra clave `throw` se utiliza para lanzar explícitamente una excepción en el programa. Un `throw` se utiliza dentro de un método para generar una excepción cuando se encuentra una condición específica.

```java
public void verificarEdad(int edad) {
    if (edad < 18) {
        throw new IllegalArgumentException("Edad no válida, debes ser mayor de 18 años");
    }
}
```

## Creación de Excepciones Personalizadas

Java permite a los desarrolladores crear excepciones personalizadas para situaciones específicas de su programa. Para crear una excepción personalizada, se debe extender la clase `Exception` o una de sus subclases.

Al crear una excepción personalizada, se puede proporcionar un mensaje detallado o información adicional para ayudar a entender la causa de la excepción.

```java
// Definir una excepción personalizada
public class EdadInvalidaException extends Exception {
    public EdadInvalidaException(String message) {
        super(message);
    }
}

// Usar la excepción personalizada
public void verificarEdad(int edad) throws EdadInvalidaException {
    if (edad < 18) {
        throw new EdadInvalidaException("Edad no válida, debes ser mayor de 18 años");
    }
}
```

## Conclusión

Las excepciones son una parte fundamental del manejo de errores en Java. El mecanismo de manejo de excepciones permite a los desarrolladores escribir programas más robustos y confiables, ya que pueden manejar condiciones inesperadas de manera controlada. La jerarquía de excepciones en Java, que incluye clases como `Throwable`, `Exception`, y `Error`, ofrece una estructura clara para manejar diferentes tipos de errores.

El uso adecuado de las excepciones comprobadas y no comprobadas, así como las palabras clave `try-catch`, `throws` y `throw`, ayuda a garantizar que el flujo de ejecución de un programa no se vea interrumpido de manera inesperada y que los errores se manejen de manera eficiente.

</div>