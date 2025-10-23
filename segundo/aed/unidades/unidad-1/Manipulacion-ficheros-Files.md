<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Manipulación de ficeros con Files)

Vamos a crear una clase con un nombre similar (por ejemplo, FileOperations) para evitar confusiones, y dentro de ella usaremos los métodos de la clase java.nio.file.Files.

Vamos a definir métodos estáticos para cada operación, de modo que se puedan usar sin instanciar la clase.

Operaciones a implementar:

- Crear un archivo
- Leer un archivo
- Escribir en un archivo
- Copiar un archivo
- Mover un archivo
- Borrar un archivo
- Obtener un stream de un archivo (para lectura o escritura)

Consideraciones:

- Manejo de excepciones: En este ejemplo, lanzaremos las excepciones para que el llamador las maneje. En una aplicación real, podrías querer manejarlas dentro de los métodos o lanzar excepciones personalizadas.

- Vamos a usar Paths.get para obtener un Path a partir de una ruta en string.

```java
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

public final class FileOperations {
    
    public static void createFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.createFile(path);
    }

    public static String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return new String(Files.readAllBytes(path));
    }

    public static List<String> readLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path);
    }

    public static void writeFile(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path, content.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void appendToFile(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path, content.getBytes(), StandardOpenOption.APPEND);
    }

    public static void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);
    }

    public static void copyFile(String source, String destination) throws IOException {
        Path src = Paths.get(source);
        Path dest = Paths.get(destination);
        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void moveFile(String source, String destination) throws IOException {
        Path src = Paths.get(source);
        Path dest = Paths.get(destination);
        Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
    }

    public static Stream<String> streamLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.lines(path);
    }

    private FileOperations() {
        throw new UnsupportedOperationException("Clase utilitaria");
    }
}
```

</div>