<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Sistema de Ficheros)

## Manejo de Archivos en Java

Trabajar con archivos es algo que muchos programas necesitan hacer. En Java, usamos principalmente las clases del paquete `java.io` para manejar archivos, como abrir, leer, escribir o eliminar archivos.

## Clase File en Java

La clase `File` en Java nos ayuda a trabajar con archivos y carpetas en nuestra computadora. Nos permite hacer cosas como crear, buscar y eliminar archivos o carpetas.

Un objeto `File` es como una dirección que nos dice dónde está un archivo o carpeta, pero no guarda el contenido del archivo en sí, solo nos indica dónde encontrarlo.

## Constructores de la Clase File en Java

Con los constructores de la clase `File`, puedes crear objetos que indican la ubicación de archivos y carpetas, pero esto no significa que los archivos o carpetas se creen en tu computadora.

Además, la clase `File` puede usar rutas relativas (basadas en la ubicación actual) o rutas absolutas (que indican la ubicación exacta).

A continuación, veremos los constructores más importantes.

### 1. Constructor File(String pathname)

Este constructor recibe una cadena que representa la ruta del archivo o directorio. La ruta puede ser relativa o absoluta, dependiendo de cómo esté definida.

```java
File(String pathname)
```

### Ejemplo de Uso

```java
import java.io.File;

public class EjemploFilePathname {
    public static void main(String[] args) {
        // Crea un objeto File que representa un archivo.
        File archivo = new File("C:/ejemplos/archivo.txt");

        // Verifica si el archivo existe
        if (archivo.exists()) {
            System.out.println("El archivo existe.");
        } else {
            System.out.println("El archivo no existe.");
        }
    }
}
```

### 2. Constructor File(String parent, String child)

Este constructor permite crear un objeto `File` combinando una ruta de directorio (`parent`) y un nombre de archivo o subdirectorio (`child`).

```java
File(String parent, String child)
```

### Ejemplo de uso

```java
import java.io.File;

public class EjemploFileParentChild {
    public static void main(String[] args) {
        // Directorio padre
        String directorio = "C:/ejemplos";

        // Nombre del archivo dentro del directorio
        String archivo = "archivo.txt";

        // Crea un objeto File combinando el directorio y el archivo.
        File file = new File(directorio, archivo);

        // Imprime la ruta absoluta del archivo
        System.out.println("Ruta absoluta: " + file.getAbsolutePath());
    }
}
```

### 3. Constructor File(File parent, String child)

Este constructor es similar al anterior, pero en lugar de recibir una cadena para el directorio padre, recibe un objeto `File`.

```java
File(File parent, String child)
```

#### Ejemplo de uso

```java
import java.io.File;

public class EjemploFileParentObject {
    public static void main(String[] args) {
        // Crea un objeto File para el directorio padre
        File directorio = new File("C:/ejemplos");

        // Crea un archivo dentro del directorio
        File archivo = new File(directorio, "archivo.txt");

        // Verifica si es un archivo o directorio
        if (archivo.isFile()) {
            System.out.println("Es un archivo.");
        } else {
            System.out.println("No es un archivo.");
        }
    }
}
```

### 4. Constructor File(URI uri)

Este constructor permite crear un objeto `File` a partir de un objeto URI. Este es útil cuando se trabaja con rutas que incluyen identificadores de recursos universales (URI).

```java
File(URI uri)
```

#### Ejemplo de Uso

```java
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class EjemploFileURI {
    public static void main(String[] args) {
        try {
            // Crear un URI a partir de una cadena
            URI uri = new URI("file:///C:/ejemplos/archivo.txt");

            // Crea un objeto File usando el URI
            File archivo = new File(uri);

            // Verifica si el archivo existe
            if (archivo.exists()) {
                System.out.println("El archivo existe.");
            } else {
                System.out.println("El archivo no existe.");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
```

## Consideraciones Importantes

- **Creación del archivo o directorio:** Aunque los objetos `File` permiten manejar rutas de archivos y directorios, no crean los archivos físicamente en el sistema. Para crear el archivo, se debe utilizar el método `createNewFile()`, y para crear un directorio se utiliza `mkdir()` o `mkdirs()`.
  
- **Rutas absolutas y relativas:** Dependiendo de cómo se inicialice el objeto `File`, puede representar una ruta absoluta o relativa. Las rutas relativas se basan en el directorio de trabajo actual.

## Métodos de la Clase File

La clase `File` ofrece un amplio conjunto de métodos que permiten gestionar archivos y directorios de manera eficiente. Al dominar estas funcionalidades, los desarrolladores pueden controlar de manera precisa cómo interactúa su aplicación con el sistema de archivos.

## Tabla de Métodos de la Clase File

| N.º | Modificador y Tipo | Método                                | Descripción Mejorada                                               |
|-----|--------------------|---------------------------------------|--------------------------------------------------------------------|
| 1   | `boolean`          | `canExecute()`                        | Verifica si el archivo puede ser ejecutado por la aplicación.      |
| 2   | `boolean`          | `canRead()`                           | Verifica si la aplicación tiene permisos para leer el archivo.    |
| 3   | `boolean`          | `canWrite()`                          | Determina si la aplicación puede modificar el archivo.            |
| 4   | `int`              | `compareTo(File pathname)`            | Compara dos nombres de ruta abstractos de forma lexicográfica.     |
| 5   | `boolean`          | `createNewFile()`                     | Crea un nuevo archivo vacío de manera atómica.                     |
| 6   | `static File`      | `createTempFile(String prefix, String suffix)` | Crea un archivo temporal vacío.                                  |
| 7   | `boolean`          | `delete()`                            | Elimina el archivo o directorio señalado.                          |
| 8   | `boolean`          | `exists()`                            | Verifica si el archivo o directorio existe.                        |
| 9   | `String`           | `getAbsolutePath()`                   | Devuelve la ruta absoluta del archivo.                             |
| 10  | `boolean`          | `isDirectory()`                        | Comprueba si el archivo es un directorio.                          |
| 11  | `long`             | `length()`                            | Retorna el tamaño del archivo en bytes.                            |

(La tabla continúa con más métodos de la clase `File`.)

## Ejemplo de uso de la clase File

Un ejemplo de uso de la clase `File` es la siguiente:

```java
import java.io.File;

public class FilePropertiesDemo {

    public static void main(String[] args) {
        // Definimos el nombre del archivo
        String nombreArchivo = "ejemplo.txt";

        // Creamos un objeto File para representar el archivo
        File archivo = new File(nombreArchivo);

        try {
            // Verificamos si el archivo existe
            if (archivo.exists()) {
                System.out.println("El archivo '" + nombreArchivo + "' existe.");

                // Verificamos si el archivo es legible
                boolean esLegible = archivo.canRead();
                System.out.println("Es legible: " + esLegible);

                // Verificamos si el archivo es escribible
                boolean esEscribible = archivo.canWrite();
                System.out.println("Es escribible: " + esEscribible);

                // Verificamos si el archivo es un directorio
                boolean esDirectorio = archivo.isDirectory();
                System.out.println("Es un directorio: " + esDirectorio);

                // Obtenemos el tamaño del archivo en bytes
                long tamanoArchivo = archivo.length();
                System.out.println("Tamaño del archivo: " + tamanoArchivo + " bytes");

            } else {
                // Si el archivo no existe, mostramos un mensaje
                System.out.println("El archivo '" + nombreArchivo + "' no existe.");

                // Intentamos crear un nuevo archivo
                boolean creado = archivo.createNewFile();

                if (creado) {
                    System.out.println("El archivo ha sido creado exitosamente.");
                } else {
                    System.out.println("No se pudo crear el archivo.");
                }
            }
        } catch (Exception e) {
            // Captura de cualquier excepción de entrada/salida
            e.printStackTrace();
        }
    }
}
```

## Conclusión

Este texto explica cómo interactuar con archivos en Java utilizando la clase `File`. Se detallan los constructores y métodos más comunes de esta clase, que permiten verificar propiedades de los archivos, como su existencia, permisos de lectura y escritura, y si es un directorio o archivo, entre otros. Además, se describe cómo crear un archivo si no existe.

La clase `File` es una herramienta indispensable para gestionar archivos en Java, y comprender sus métodos permite desarrollar aplicaciones que manejen eficientemente el sistema de archivos.

</div>