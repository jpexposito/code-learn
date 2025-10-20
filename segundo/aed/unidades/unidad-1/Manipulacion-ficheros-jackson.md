<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Manipulación de ficeros con Jackson (xml -Json))

Vamos a detallar como construir una API con **Spring Boot** que gestione **ficheros y directorios** usando `java.nio.file`, persista datos en **JSON** o **XML** con **Jackson**, implemente **CRUD** sobre ficheros, realice **conversión entre formatos**, y maneje **excepciones**, además de pautas para la **documentación de aplicaciones**.

## Objetivo didáctico de esta unidad será

- Utilización de **clases para la gestión de ficheros y directorios**.  
- **Ventajas e inconvenientes** de distintos **formatos de acceso** a ficheros.  
- **Recuperación y manipulación** de información almacenada en ficheros (**CRUD**).  
- **Conversión** entre formatos de ficheros (**JSON ⇄ XML**).  
- **Gestión de excepciones**.  
- **Documentación** de aplicaciones (Javadoc).

## 🧰 Stack y requisitos

- **Java 17+**
- **Spring Boot 3.x** (`spring-boot-starter-web`, `spring-boot-starter-validation`)
- **Jackson** (`jackson-databind`, `jackson-dataformat-xml`)
- **Maven**

**Maven (pom.xml — dependencias clave):**

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>
  <dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
  </dependency>
  <dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

## 🗂️ Estructura propuesta del proyecto

```code
src/
 ├─ main/java/com/docencia/files/
 │   ├─ FilesConfig.java
 │   ├─ model/Note.java
 │   ├─ repo/FileNoteRepository.java
 │   ├─ service/NoteService.java
 │   ├─ web/NoteController.java
 │   ├─ web/GlobalExceptionHandler.java
 │   └─ util/FormatConverter.java
 ├─ main/resources/
 │   
 └─ test/java/... (tests)
```


## 📁 Clases para gestión de ficheros y directorios (java.nio.file)

Clases clave y ejemplos:

- `Path`, `Paths`: representación inmutable de rutas.
- `Files`: operaciones de alto nivel (crear, leer, escribir, copiar, mover, borrar, stream).
- `DirectoryStream`: recorrer directorios eficientemente.
- `FileVisitor`/`SimpleFileVisitor`: recorrer árbol de directorios.
- `FileTime`: metadatos (última modificación).
- `StandardOpenOption`/`StandardCopyOption`: opciones de apertura/copia.
- `FileChannel`/`MappedByteBuffer`: acceso avanzado (memoria mapeada).

**Ejemplos rápidos:**

```java
Path root = Paths.get(storageRoot);
Files.createDirectories(root);

Path notePath = root.resolve(id + "." + ext);
boolean exists = Files.exists(notePath);

byte[] content = Files.readAllBytes(notePath);
Files.writeString(notePath, json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

try (DirectoryStream<Path> stream = Files.newDirectoryStream(root, "*.{json,xml}")) {
  for (Path p : stream) { /* ... */ }
}
```

## ⚙️ Formatos de acceso a ficheros: ventajas e inconvenientes

| Formato de acceso                | Ventajas                                                                 | Inconvenientes / Cuándo evitar                                       |
|----------------------------------|--------------------------------------------------------------------------|-----------------------------------------------------------------------|
| **Secuencial (streams)**         | Simple, bajo consumo de memoria, ideal para ficheros grandes.           | Acceso aleatorio costoso; relecturas completas para pequeñas ediciones.|
| **Buffered (BufferedInput/OutputStream, Reader/Writer)** | Menos I/O físico, mejora rendimiento.                    | Buffer tuning necesario en cargas inusuales.                          |
| **Aleatorio (RandomAccessFile)** | Lectura/escritura en posiciones específicas.                            | API más compleja; sincronización en concurrencia.                     |
| **Canales/NIO (`FileChannel`)**  | Transferencias rápidas (zero-copy), soporte `lock`, memory-mapping.     | Curva de aprendizaje; cuidado con `MappedByteBuffer` y GC.            |
| **Memoria mapeada**              | Máximo rendimiento en lectura aleatoria; útil para grandes ficheros.    | Gestión de recursos delicada; no portable a FS remotos.               |

## 📦 Modelo de datos y serialización (Jackson JSON & XML)

**`Note.java` (Bean (clase) con validación):**

```java
package com.docencia.files.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Note {

  @NotBlank
  private String id;

  @NotBlank @Size(max = 200)
  private String title;

  @NotBlank
  private String content;

  // getters/setters/constructores
}
```

**Configuración de Jackson (opcional):**

```java
@Bean
ObjectMapper jsonMapper() {
  return new ObjectMapper()
      .findAndRegisterModules(); // fechas, etc.
}

@Bean
XmlMapper xmlMapper() {
  XmlMapper xm = new XmlMapper();
  xm.findAndRegisterModules();
  return xm;
}
```

## 🧮 Conversión entre formatos (JSON ⇄ XML)

**`FormatConverter.java`:**

```java
package com.docencia.files.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class FormatConverter {
  private final ObjectMapper json;
  private final XmlMapper xml;

  public FormatConverter(ObjectMapper json, XmlMapper xml) {
    this.json = json; this.xml = xml;
  }

  public <T> String jsonToXml(String jsonString, Class<T> type) throws Exception {
    T obj = json.readValue(jsonString, type);
    return xml.writeValueAsString(obj);
  }

  public <T> String xmlToJson(String xmlString, Class<T> type) throws Exception {
    T obj = xml.readValue(xmlString, type);
    return json.writeValueAsString(obj);
  }
}
```

## 🗃️ Repositorio de ficheros y CRUD

**`FileNoteRepository.java`:**

```java
package com.docencia.files.repo;

import com.docencia.files.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class FileNoteRepository {
  private final Path root;
  private final ObjectMapper json;
  private final XmlMapper xml;
  private final String defaultExt; // "json" o "xml"

  public FileNoteRepository(Path root, ObjectMapper json, XmlMapper xml, String defaultExt) throws IOException {
    this.root = root; this.json = json; this.xml = xml; this.defaultExt = defaultExt;
    Files.createDirectories(root);
  }

  private Path pathOf(String id, String ext) { return root.resolve(id + "." + ext); }

  public boolean exists(String id) throws IOException {
    try (Stream<Path> s = Files.list(root)) {
      return s.anyMatch(p -> p.getFileName().toString().matches(id + "\\\\.(json|xml)$"));
    }
  }

  public Note findById(String id) throws IOException {
    Path pJson = pathOf(id, "json");
    Path pXml  = pathOf(id, "xml");
    if (Files.exists(pJson)) return json.readValue(Files.readString(pJson), Note.class);
    if (Files.exists(pXml))  return xml.readValue(Files.readString(pXml), Note.class);
    throw new NoSuchFileException("Note " + id + " not found");
  }

  public List<Note> findAll() throws IOException {
    List<Note> out = new ArrayList<>();
    try (DirectoryStream<Path> ds = Files.newDirectoryStream(root, "*.{json,xml}")) {
      for (Path p : ds) {
        String name = p.getFileName().toString();
        if (name.endsWith(".json")) out.add(json.readValue(Files.readString(p), Note.class));
        else out.add(xml.readValue(Files.readString(p), Note.class));
      }
    }
    return out;
  }

  public Note save(Note n, Optional<String> extOpt) throws IOException {
    String ext = extOpt.orElse(defaultExt);
    Path p = pathOf(n.getId(), ext);
    String payload = ext.equals("json") ? json.writeValueAsString(n) : xml.writeValueAsString(n);
    Files.writeString(p, payload, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    return n;
  }

  public void delete(String id) throws IOException {
    Files.deleteIfExists(pathOf(id, "json"));
    Files.deleteIfExists(pathOf(id, "xml"));
  }
}
```

## 🧠 Servicio y controlador (Spring)

**`NoteService.java`:**

```java
package com.docencia.files.service;

import com.docencia.files.model.Note;
import com.docencia.files.repo.FileNoteRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class NoteService {
  private final FileNoteRepository repo;

  public NoteService(FileNoteRepository repo) { this.repo = repo; }

  public Note create(Note n, String format) throws IOException {
    if (repo.exists(n.getId())) throw new IllegalStateException("ID repetido");
    return repo.save(n, Optional.ofNullable(format));
  }

  public Note update(Note n, String format) throws IOException {
    return repo.save(n, Optional.ofNullable(format));
  }

  public Note get(String id) throws IOException { return repo.findById(id); }

  public List<Note> all() throws IOException { return repo.findAll(); }

  public void delete(String id) throws IOException { repo.delete(id); }
}
```

**`NoteController.java`:**

```java
package com.docencia.files.web;

import com.docencia.files.model.Note;
import com.docencia.files.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
public class NoteController {
  //implementar
}
```

## 🛡️ Gestión de excepciones

**`GlobalExceptionHandler.java`:**

```java
package com.docencia.files.exceptios;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.NoSuchFileException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NoSuchFileException.class)
  public ProblemDetail notFound(NoSuchFileException ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    pd.setTitle("Recurso no encontrado");
    pd.setDetail(ex.getMessage());
    return pd;
  }

  @ExceptionHandler(IllegalStateException.class)
  public ProblemDetail conflict(IllegalStateException ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
    pd.setTitle("Conflicto de estado");
    pd.setDetail(ex.getMessage());
    return pd;
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail generic(Exception ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    pd.setTitle("Error interno");
    pd.setDetail(ex.getMessage());
    return pd;
  }
}
```

**Buenas prácticas:**

- Validar entrada (`@Valid` + anotaciones en el modelo).
- Usar `ProblemDetail` (Spring 6) para respuestas de error consistentes.
- Registrar logs con `Slf4j` (no mostrado por brevedad).
- En concurrencia, considerar bloqueos a nivel de fichero (`FileChannel#lock`) o estrategias de nombrado temporal + `move` atómico.

</div>