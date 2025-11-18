<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Persistencia básica en Java y bases de datos y a través de un ORM)

<div align="center">
    <img src=images/spring-data-jpa.png width="300">
</div>

## 1. Introducción

Cuando programamos en Java, trabajamos con **objetos** (`Note`, `Persona`, `User`, `Pedido`, ...).  
Cuando guardamos datos de forma permanente, normalmente lo hacemos en una **base de datos relacional**, que almacena **tablas y filas**.

El problema clásico:

- Tengo un objeto Java en memoria.
- Quiero guardarlo como una fila en la base de datos.
- Quiero poder leerlo después y volver a tener un objeto Java.

A eso lo llamamos **persistencia**.

En este ejemplo se realiza la evolución típica de la persistencia en Java:

1. **JDBC puro** (SQL manual).
2. Organización mediante **DAO / Repository**.
3. Limitaciones de hacerlo todo a mano.
4. **ORM + JPA/Hibernate** (mapeo objeto-relacional automático).
5. **Spring Data JPA** (repositorios declarativos).
6. Tests con base de datos de pruebas.

---

## 2. JDBC: el nivel más básico

**JDBC (Java Database Connectivity)** es la API estándar de Java para conectarse a una base de datos relacional.

Con JDBC tú haces todo:

- Abres la conexión.
- Escribes el SQL.
- Pasas los parámetros.
- Ejecutas la consulta.
- Lees el resultado y lo conviertes a objetos Java.
- Cierras los recursos.

En los ejemplos de esta unidad usaremos una entidad sencilla `Note`, pero podríamos usar igual `Persona`, `Pedido`, etc.

### 2.1 Ejemplo de entidad de dominio en Java

```java
public class Note {
    private String id;
    private String title;
    private String content;
    // Recuerda constructores / getters, setters, equals y toString()
}
```

### 2.2 Tabla en la base de datos (SQLite / H2 / cualquier relacional)

```sql
CREATE TABLE notes (
    id TEXT PRIMARY KEY,
    title TEXT NOT NULL,
    content TEXT
);
```

> **Extensión típica para SQLite**: `.sqlite` o `.db`.  
> En H2 se suele usar un fichero `.mv.db` o una BBDD en memoria (`jdbc:h2:mem:testdb`).

### 2.3 Guardar una nota con JDBC

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class NoteJdbcBasico {

    public void save(Note note) throws Exception {

        Connection conn = DriverManager.getConnection(
            "jdbc:sqlite:notes.db"
        );

        String sql = "INSERT INTO notes (id, title, content) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, note.getId());
        ps.setString(2, note.getTitle());
        ps.setString(3, note.getContent());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }
}
```

> **Mejorable**: no deberíamos propagar `Exception` sin más, ni olvidarnos de `try/catch` o `try-with-resources` para asegurar que cerramos la conexión incluso si hay error.

### 2.4 Leer una nota por id con JDBC

```java
import java.sql.*;

public class NoteJdbcExample {

    public Note findById(String id) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:notes.db");

        String sql = "SELECT id, title, content FROM notes WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        Note result = null;
        if (rs.next()) {
            result = new Note();
            result.setId(rs.getString("id"));
            result.setTitle(rs.getString("title"));
            result.setContent(rs.getString("content"));
        }

        rs.close();
        ps.close();
        conn.close();

        return result;
    }
}
```

### 2.5 Qué aprendemos aquí

- Tú controlas completamente el SQL.
- Tú haces el mapeo manual columna → campo.
- Tú gestionas la conexión y el cierre.

Funciona. Es estándar. Pero empieza a cansar en cuanto tienes 5 o más tablas y relaciones.

---

## 3. DAO / Repository: poner orden

Si mezclas SQL por todas partes, el código se vuelve inmantenible.

Solución clásica: crear una **capa de acceso a datos**, normalmente llamada `DAO` (Data Access Object) o `Repository`.

La idea:

- Definir una interfaz clara con las operaciones que necesitas.
- Escribir una implementación que sabe hablar con la base de datos (con JDBC, por ejemplo).

### 3.1 Interfaz de repositorio

```java
public interface NoteRepository {

    boolean exists(String id);

    Note findById(String id);

    java.util.List<Note> findAll();

    Note save(Note note);

    boolean delete(String id);
}
```

> Esta interfaz dice: “esto es lo que el resto de la aplicación puede hacer con las notas”.  
> No dice *cómo* se hace (SQL, Hibernate, memoria, etc.), sólo *qué* se puede hacer.

### 3.2 Implementación JDBC del repositorio

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteRepositoryJdbc implements NoteRepository {

    private final String url = "jdbc:sqlite:notes.db";

    @Override
    public boolean exists(String id) {
        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM notes WHERE id = ?"
            );
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            boolean found = rs.next();

            rs.close();
            ps.close();
            return found;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Note findById(String id) {
        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT id, title, content FROM notes WHERE id = ?"
            );
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            Note note = null;
            if (rs.next()) {
                note = new Note();
                note.setId(rs.getString("id"));
                note.setTitle(rs.getString("title"));
                note.setContent(rs.getString("content"));
            }

            rs.close();
            ps.close();
            return note;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Note> findAll() {
        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT id, title, content FROM notes"
            );

            ResultSet rs = ps.executeQuery();
            List<Note> notes = new ArrayList<>();

            while (rs.next()) {
                Note note = new Note();
                note.setId(rs.getString("id"));
                note.setTitle(rs.getString("title"));
                note.setContent(rs.getString("content"));
                notes.add(note);
            }

            rs.close();
            ps.close();
            return notes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Note save(Note note) {
        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO notes (id, title, content) VALUES (?, ?, ?)"
            );
            ps.setString(1, note.getId());
            ps.setString(2, note.getTitle());
            ps.setString(3, note.getContent());

            ps.executeUpdate();
            ps.close();
            return note;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM notes WHERE id = ?"
            );
            ps.setString(1, id);

            int deleted = ps.executeUpdate();
            ps.close();
            return deleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
```

Esto ya está mucho mejor organizado:

- Todo el SQL vive en una clase.
- El resto del proyecto sólo habla con `NoteRepository`, sin saber si por debajo hay SQLite, H2, Postgres, etc.

---

## 4. Problemas de hacerlo todo a mano

Con JDBC + repositorios manuales, hay varios dolores:

1. Mucho código repetido:
   - Abrir conexión, crear `PreparedStatement`, mapear columnas ↔ objetos...
2. Mantenimiento costoso:
   - Si cambias una columna en BBDD, tienes que revisar a mano cada SQL.
3. Relaciones complejas:
   - Cuando `Note` está relacionada con `User`, `Tag`, `Attachment`, etc.  
     acabas haciendo `JOIN` constantemente.
4. Transacciones:
   - Si necesitas guardar varias cosas juntas de forma atómica:
     `conn.setAutoCommit(false)` / `commit()` / `rollback()` lo gestionas tú.

> **Solución**: Para aliviar este dolor aparece el ORM.

---

## 5. ORM y JPA / Hibernate

### 5.1 Qué es un ORM

Un **ORM (Object-Relational Mapper)** automatiza el mapeo **objeto ↔ fila**:

- Tú defines tus entidades con anotaciones.
- El framework genera el SQL y lo ejecuta.

En Java el estándar es **JPA / Jakarta Persistence** (`jakarta.persistence.*`),  
y la implementación más usada es **Hibernate**.

### 5.2 Recordatorio rápido: bases de datos relacionales

- Datos en **tablas, filas y columnas**.
- Esquema **fijo** definido con `CREATE TABLE`.
- Soportan **joins** entre tablas.
- Ejemplo:
  - Tabla `personas` con columnas `id`, `nombre`, `edad`, `email`.
  - Tabla `direcciones` con columnas `id`, `calle`, `ciudad`, `codigo_postal`, `pais`, `persona_id`.

### 5.3 Entidad JPA básica

```java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "notes")
public class Note {

    @Id // Identifica la clave primaria
    private String id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 4000)
    private String content;

    // getters y setters
}
```

Con esto:

- `@Entity` dice “esta clase se guarda en la base de datos”.
- `@Table` define el nombre de la tabla.
- `@Id` marca la clave primaria.
- `@Column` describe detalles de la columna.

El ORM (Hibernate) podrá hacer `INSERT`, `SELECT`, `UPDATE`, `DELETE` sin que tú escribas SQL manual.

### 5.4 Objetos embebidos: `@Embedded` y `@Embeddable`

Ejemplo con `Persona` y `Direccion`:

```java
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Embeddable
public class Direccion {
    private String calle;
    private String ciudad;
    private String codigoPostal;
    private String pais;
}

@Entity
@Table(name = "personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    private Integer edad;

    @Column(unique = true)
    private String email;

    @Embedded
    private Direccion direccion;
}
```

En la tabla `personas` tendremos columnas `calle`, `ciudad`, `codigo_postal`, `pais` además de las de `Persona`.

### 5.5 Relaciones entre entidades

Ejemplo típico: una persona tiene muchos pedidos → `OneToMany` / `ManyToOne`.

```java
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;
}

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "persona_id") // FK en pedidos.persona_id
    private Persona persona;
}
```

> El ORM entiende esta relación y puede cargar `pedido.getPersona()` o `persona.getPedidos()` sin que tengas que escribir los `JOIN` a mano.

---

## 6. Spring Data JPA: repositorios declarativos

Spring Data JPA da todavía un paso más: ya no escribes la clase repositorio “a mano”.

Tú solo declaras una **interfaz** que extiende `JpaRepository`, y Spring genera la implementación al arrancar.

### 6.1 Dependencia básica (Maven)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

Ejemplo de configuración (H2 en memoria):

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 6.2 Repositorio de `Note` con Spring Data JPA

```java
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface NoteRepositorySpringData extends JpaRepository<Note, String> {

    // Spring generará la query automáticamente a partir del nombre del método
    Optional<Note> findByTitle(String title);
}
```

> **¿Qué obtienes gratis?**
>
> - `save(note)`
> - `findById(id)`
> - `findAll()`
> - `deleteById(id)`
> - `existsById(id)`
> - y cualquier método derivado del nombre como `findByTitle(...)`

### 6.3 Repositorio de `Persona` con búsquedas personalizadas

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    // Método derivado del nombre
    List<Persona> findByNombreContainingIgnoreCase(String nombre);

    List<Persona> findByEdadBetween(Integer min, Integer max);

    // JPQL usando el campo embebido "direccion"
    @Query("SELECT p FROM Persona p WHERE p.direccion.ciudad = :ciudad")
    List<Persona> buscarPorCiudad(@Param("ciudad") String ciudad);

    // Consulta SQL nativa
    @Query(value = "SELECT * FROM personas WHERE email LIKE %:dominio%", nativeQuery = true)
    List<Persona> buscarPorDominioEmail(@Param("dominio") String dominio);
}
```

- Métodos derivados: `findBy...`, `countBy...`, `existsBy...`.
- `@Query` puede usar **JPQL** o **SQL nativo** (`nativeQuery = true`).

### 6.4 Auditoría con Spring Data

Se pueden usar anotaciones para registrar automáticamente fechas de creación/modificación:

```java
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditable {

    @CreatedDate
    private Instant creado;

    @LastModifiedDate
    private Instant modificado;
}
```

Y activarlo en la aplicación:

```java
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableJpaAuditing
@SpringBootApplication
public class MiAplicacion {
    // ...
}
```

---

## 7. Testing con base de datos de prueba

Para estar seguros de que la persistencia funciona, se escriben **tests de integración** que usan una base de datos de prueba (normalmente H2 en memoria).

Ejemplo con Spring Boot:

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // usa application-test.yml/properties
class NoteRepositoryIT {

    @Autowired
    private NoteRepositorySpringData noteRepository;

    private Note baseNote;

    @BeforeEach
    @Transactional
    void setUp() {
        Note n = new Note();
        n.setId("123");
        n.setTitle("Nota base");
        n.setContent("contenido base");
        baseNote = noteRepository.save(n);
    }

    @Test
    @Transactional
    void update_changes_content() {
        baseNote.setContent("actualizado");
        Note updated = noteRepository.save(baseNote);

        assertThat(updated.getContent()).isEqualTo("actualizado");
    }
}
```

Detalles clave:

- `@SpringBootTest` levanta el contexto de Spring completo.
- `@ActiveProfiles("test")` hace que usemos una BBDD de pruebas, no la real.
- `@Transactional` en los tests: cada test se ejecuta dentro de una transacción, y al terminar se hace **rollback** automático.
  - Eso significa que los datos creados en un test no se quedan “sucios” para el siguiente test.
  - Cada test empieza en una base de datos limpia.

---

## 8. En resumen

La **persistencia básica en Java** suele seguir este camino de madurez:

1. **JDBC puro**
   - Tú escribes SQL.
   - Tú haces el mapeo `ResultSet → objeto`.
   - Máximo control, máximo trabajo.

2. **DAO / Repository manual**
   - Centralizas el acceso a datos en clases repositorio.
   - Separas lógica de negocio vs. lógica de persistencia.
   - Todavía escribes SQL tú mismo.

3. **ORM con JPA / Hibernate**
   - Declaras entidades con anotaciones (`@Entity`, `@Id`, `@ManyToOne`, `@Embedded`, ...`).
   - El ORM genera el SQL y gestiona las relaciones.
   - Menos duplicación y menos riesgo de errores al mapear.

4. **Spring Data JPA**
   - Declaras interfaces que extienden `JpaRepository`.
   - Spring crea las implementaciones CRUD y las consultas derivadas.
   - Se integra con transacciones, validación, auditoría, etc.

5. **Tests con base de datos de prueba**
   - `@SpringBootTest` levanta el contexto real.
   - `@Transactional` en los tests asegura rollback al final.
   - Cada test es independiente y repetible.

El objetivo de este Code & Learn es que veas este viaje completo:  
desde escribir `INSERT` y `SELECT` a mano, hasta trabajar casi solo con **clases Java, anotaciones y repositorios declarativos**.

</div>
