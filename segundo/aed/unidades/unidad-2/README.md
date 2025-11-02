<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Persistencia básica en Java y bases de datos y a través de un ORM)

<div align="center">
    <img src=images/spring-data-jpa.png width="300">
</div>

## 1. Introducción

Cuando programamos en Java, trabajamos con **objetos** (`Note`, `User`, `Pedido`, ...).  
Cuando guardamos datos de forma permanente, lo hacemos en una **base de datos relacional**, que almacena **tablas y filas**.

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

### 2.1 Ejemplo de entidad de dominio en Java

```java
public class Note {
    private String id;
    private String title;
    private String content;
    // Recuerda contructores/ getters, setters, equals y toString()
}
```

### 2.2 Tabla en la base de datos

```sql
CREATE TABLE notes (
    id TEXT PRIMARY KEY,
    title TEXT NOT NULL,
    content TEXT
);
```

> **Extensión de las bbdd en sqlite**: La extensión en la bbdd es *sqlite* o *db*.

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

> **Note**: Este código se mejora no subiendo la Exception, añadiendo un try .. catch y un finalize para acegurarnos que cerramos la conexión a la bbdd

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
- Tú haces el mapeo manual columna->campo.
- Tú gestionas la conexión y el cierre.

Funciona. Es estándar. Pero empieza a cansar en cuanto tienes 5 o más tablas.

---

## 3. DAO / Repository: poner orden

Si mezclas SQL por todas partes, el código se vuelve inmantenible.

Solución clásica: crear una **capa de acceso a datos**, normalmente llamada `DAO` (Data Access Object) o `Repository`.

La idea:

- Definir una interfaz clara con las operaciones que necesitas.
- Escribir una implementación que sabe hablar con la base de datos.

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

> **Esta interfaz dice**: "esto es lo que el resto de la aplicación puede hacer con las notas".  
No dice *cómo* se hace (SQL, Hibernate, memoria, etc.), sólo *qué* se puede hacer.

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
- El resto del proyecto sólo habla con `NoteRepository`, sin saber si por debajo hay SQLite, Postgres, etc.


---

## 4. Problemas de hacerlo todo a mano

Con JDBC + repositorios manuales, hay varios dolores:

1. Mucho código repetido:
   - Abrir conexión, crear `PreparedStatement`, mapear columnas → objetos...
2. Mantenimiento costoso:
   - Si cambias una columna en BBDD, tienes que revisar a mano cada SQL.
3. Relaciones complejas:
   - ¿Qué pasa cuando `Note` está relacionada con `User`, `Tag`, `Attachment`, etc.?  
     Acabas haciendo `JOIN` cada dos por tres.
4. Transacciones:
   - ¿Qué si necesitas guardar varias cosas juntas de forma atómica?
   - Tienes que manejar `conn.setAutoCommit(false)` / `commit()` / `rollback()` tú mismo.

> **Solución:** Para aliviar este dolor aparece el ORM.

---

## 5. ORM y JPA / Hibernate

### 5.1 Qué es un ORM

Un **ORM (Object-Relational Mapper)** automatiza el mapeo "objeto ↔ fila":

- Tú defines tus entidades con anotaciones.
- El framework genera el SQL y lo ejecuta.

En Java el estándar para describir esta información es **JPA / Jakarta Persistence**.  
La implementación más común es **Hibernate**.

### 5.2 Entidad JPA

```java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "notes")
public class Note {

    @Id //Recuerda que identifica la clave primaria
    private String id; 

    @Column(nullable = false)
    private String title;

    @Column(length = 4000)
    private String content;

    // getters y setters
}
```

Con esto:

- `@Entity` dice "esta clase se guarda en la base de datos".
- `@Table` define el nombre de la tabla.
- `@Id` marca la clave primaria.
- `@Column` describe detalles de la columna.

El ORM (Hibernate) podrá hacer `INSERT`, `SELECT`, `UPDATE`, `DELETE` sin que tú escribas SQL manual.


### 5.3 Relaciones entre entidades

Ejemplo típico: muchas notas pertenecen al mismo usuario → `ManyToOne`.

```java
import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    private String id;

    private String username;
    private String displayName;
}

@Entity
@Table(name = "notes")
public class Note {

    @Id
    private String id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id") // FK en notes.user_id
    private User author;
}
```

> **Explicación**: El ORM entiende esa relación, sabe que `notes.user_id` apunta a `app_user.id`, y puede cargar el `author` de una `Note` automáticamente.

---

## 6. Spring Data JPA: repositorios declarativos

Spring Data JPA da todavía un paso más: ya no escribes ni la clase repositorio.

Tú solo declaras una interfaz que **extiende** de `JpaRepository`, y Spring genera la implementación al arrancar.

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

Ventajas:

- Casi nada de SQL manual.
- Menos código repetitivo.
- Integración directa con Spring (inyección de dependencias, transacciones, etc.).

---

## 7. Testing con base de datos de prueba

Para estar seguros de que la persistencia funciona, se escriben **tests de integración** que usan una base de datos de prueba.

En un proyecto Spring Boot esto suele verse así:

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // usa la config application-test.yml/properties
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
  - Eso significa que los datos creados en un test no se quedan "sucios" para el siguiente test.
  - Cada test empieza en una base de datos limpia.

---

## 8. En resumen

**Persistencia básica en Java** sigue normalmente este camino de madurez:

1. **JDBC puro**
   - Tú escribes SQL.
   - Tú haces el mapeo ResultSet → objeto.
   - Máximo control, máximo trabajo.

2. **DAO / Repository manual**
   - Centralizas el acceso a datos en clases repositorio.
   - Separas lógica de negocio vs. lógica de persistencia.
   - Todavía escribes SQL tú mismo.

3. **ORM con JPA / Hibernate**
   - Declaras entidades con anotaciones (`@Entity`, `@Id`, `@ManyToOne`, ...).
   - El ORM genera el SQL y gestiona las relaciones.
   - Menos duplicación y menos riesgo de errores al mapear.

4. **Spring Data JPA**
   - Declaras interfaces que extienden `JpaRepository`.
   - Spring crea las implementaciones CRUD y las consultas derivadas.
   - Se integra con las transacciones y el ciclo de vida de Spring.

5. **Tests con base de datos de prueba**
   - `@SpringBootTest` levanta el contexto real.
   - `@Transactional` en los tests asegura rollback al final.
   - Así cada test es independiente y repetible.

Este es el camino típico en proyectos modernos: se empieza entendiendo JDBC, se organiza en repositorios, y se termina usando JPA/Hibernate + Spring Data para ser mucho más productivos y seguros.

</div>