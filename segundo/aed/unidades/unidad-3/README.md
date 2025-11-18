<div align="justify">


# <img src=../../../../images/coding-book.png width="40"> Code & Learn (ORDBMS, OODBMS y NoSQL documental con Spring Data)

<div align="center">
    <img src=images/spring-java-relacional-objetos.png width="300">
</div>

## Introducción

El **objetivo** es que entiendas las diferencias entre:

- **Gestores objeto–relacionales (ORDBMS)**  
- **Gestores orientados a objetos (OODBMS)**  
- **Bases de datos NoSQL documentales** (ej. MongoDB) y cómo trabajarlas con **Spring Data MongoDB**

Con ejemplos sencillos:  
características, ventajas, **persistencia de objetos** (simples y estructurados), **consultas** (SQL / OQL / JSON), **transacciones**, **acceso desde el lenguaje** y **cuándo elegir cada uno**.

---

## 0) En 1 minuto

| Tema                  | ORDBMS                                                                 | OODBMS                                                                 | NoSQL documental (MongoDB)                                                            |
|-----------------------|------------------------------------------------------------------------|-------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| Modelo                | Relacional con extras OO (UDT, colecciones, métodos)                  | Objetos “tal cual” con OID, referencias y métodos                      | Colecciones y documentos JSON/BSON (agregados, subdocumentos embebidos)             |
| Lenguaje de consulta  | **SQL** (con extensiones para objetos)                                | **OQL** + navegación por referencias                                   | JSON queries, operadores MongoDB, framework de agregación                            |
| Persistencia          | Tablas/columnas, tipos compuestos y arrays                            | Gráficos de objetos (persistencia por alcance)                         | Documentos con campos simples y subdocumentos; arrays de objetos y valores           |
| Acceso desde lenguaje | JDBC/ODBC, JPA/Hibernate, procedimientos                              | APIs nativas: `begin/store/query/commit`                               | Drivers nativos + **Spring Data MongoDB** (`MongoRepository` + anotaciones)         |
| Transacciones         | ACID (bloqueo por **fila**)                                           | ACID (bloqueo por **objeto**) + versiones / transacciones largas      | ACID a nivel de colección; transacciones multi-documento según motor                |
| Úsalo si…             | Necesitas SQL, BI/reporting, integridad relacional, ecosistema maduro | Dominio muy OO con grafos internos complejos y mucha navegación       | Necesitas esquema flexible, escalabilidad horizontal, agregados + integración Java  |

---

## 🧩 1) ORDBMS (Object–Relational DBMS)

### 1.1 ¿Qué es?

Una base relacional **vitaminada** con rasgos OO:

- **UDT (User-Defined Types)** y **tipos compuestos**.
- **Colecciones** (arrays, varrays, nested tables).
- En algunos motores: **métodos** en tipos y **herencia**.
- Todo se consulta con **SQL**.

> Ejemplos con buen soporte objeto–relacional: **PostgreSQL** (tipos/arrays), **Oracle** (OBJECT TYPE, VARRAY/NESTED TABLE), **DB2**, etc.

### 1.2 Persistencia de objetos

**a) Objetos simples** (campos primitivos)

```sql
CREATE TABLE Customer (
  id    SERIAL PRIMARY KEY,
  name  VARCHAR(60) NOT NULL
);

INSERT INTO Customer (name) VALUES ('Ada Lovelace');
```

**b) Objetos estructurados** (tipo compuesto + colección)

```sql
-- Tipo compuesto Address
CREATE TYPE Address AS (
  street  VARCHAR(80),
  city    VARCHAR(40),
  zip     VARCHAR(10)
);

-- Tabla con un UDT y un array de teléfonos
CREATE TABLE CustomerEx (
  id       SERIAL PRIMARY KEY,
  name     VARCHAR(60) NOT NULL,
  address  Address,      -- tipo compuesto
  phones   TEXT[]        -- colección (p.ej., PostgreSQL)
);

INSERT INTO CustomerEx (name, address, phones)
VALUES (
  'Linus T.',
  ROW('Main St 1','Helsinki','00100')::Address,
  ARRAY['600111222','600333444']
);
```

### 1.3 Consultas (SQL)

- **Acceso a campos del UDT**

```sql
SELECT name, address.city
FROM CustomerEx
WHERE address.city = 'Helsinki';
```

- **Búsqueda dentro de una colección**

```sql
SELECT id
FROM CustomerEx
WHERE '600111222' = ANY(phones);
```

- **Actualizar parte del objeto**

```sql
UPDATE CustomerEx
SET address = ROW('Main St 1 Apt 2','Helsinki','00100')::Address
WHERE id = 1;
```

### 1.4 Transacciones (SQL / pseudocódigo)

```sql
BEGIN;
UPDATE CustomerEx SET name = 'Linus Torvalds' WHERE id = 1;
-- Si todo OK
COMMIT;
-- Si algo falla:
ROLLBACK;
```

### 1.5 Acceso desde el lenguaje (Java, vistazo rápido)

```java
try (Connection cx = DriverManager.getConnection(url, user, pass)) {
  cx.setAutoCommit(false);
  try (PreparedStatement st = cx.prepareStatement(
        "SELECT name, (address).city FROM CustomerEx WHERE id = ?")) {
    st.setInt(1, 1);
    try (ResultSet rs = st.executeQuery()) {
      while (rs.next()) {
        String name = rs.getString(1);
        String city = rs.getString(2);
        // ...
      }
    }
    cx.commit();
  } catch (Exception e) {
    cx.rollback();
  }
}
```

### 1.6 Ventajas y límites

**Ventajas:** SQL y su ecosistema, integridad relacional, tipos/arrays útiles, optimizador potente.  
**Límites:** el modelo sigue siendo tabular; mapear objetos **muy** complejos puede requerir trabajo (ORMs, UDTs, etc.).

---

## 🧠 2) OODBMS (Object–Oriented DBMS)

### 2.1 ¿Qué es?

La base almacena **objetos directamente**:

- Cada objeto tiene **OID** (identidad estable).
- Objetos con **composición** y **referencias** (grafos).
- Acceso **navegacional** (sigues referencias) o consultas **OQL**.

### 2.2 Persistencia de objetos

**a) Objeto simple** (pseudocódigo)

```text
class Person { String name; int age; }

tx.begin();
store(new Person("Ada", 36));
tx.commit();
```

**b) Objeto estructurado (composición y colección)**

```text
class Address { String street; String city; String zip; }
class Customer { String name; Address address; List<String> phones; }

tx.begin();
addr = new Address("Main St 1", "Helsinki", "00100");
cust = new Customer("Linus", addr, ["600111222","600333444"]);
store(cust);                 // persistencia por alcance: guarda lo alcanzable
tx.commit();
```

> **Persistencia por alcance**: si guardas `cust`, también se persiste `addr` y la lista, porque están “colgados” de `cust`.

### 2.3 Consultas (OQL, pseudocódigo)

```text
-- Clientes que viven en Helsinki
SELECT c
FROM Customer c
WHERE c.address.city = "Helsinki";

-- Clientes con algún teléfono que empieza por '600'
SELECT c.name
FROM Customer c
WHERE EXISTS p IN c.phones : startsWith(p, "600");
```

### 2.4 Transacciones (pseudocódigo)

```text
tx.begin();
c = queryOne("SELECT c FROM Customer c WHERE c.name = 'Linus'");
c.address.zip = "00101";     // editar objeto
tx.commit();                 // ACID a nivel de objeto
```

- **Aislamiento**: **pesimista** (bloqueos) u **optimista** (verificación al commit).
- **Transacciones largas** y **versionado**: habituales en dominios con edición prolongada (CAD, ingeniería…).

### 2.5 Acceso desde el lenguaje

API típica: `openDatabase`, `tx.begin()`, `store(obj)`, `query(OQL)`, `delete(obj)`, `tx.commit()/rollback()`.  
Trabajas con tus **clases de dominio** sin mapeo tabular.

### 2.6 Ventajas y límites

**Ventajas:** naturalidad OO (guardas tus objetos), navegación directa, útil para grafos ricos.  
**Límites:** menor estandarización que SQL, tooling más limitado, OQL menos extendido.

---

## 📦 3) NoSQL documental (MongoDB) y Spring Data MongoDB

### 3.1 Conceptos básicos de NoSQL documental

- **No relacional**: no hay tablas/filas, sino **colecciones** y **documentos**.
- Cada documento suele estar en formato **JSON/BSON**.
- Esquema **flexible**: documentos de una misma colección pueden tener campos distintos.
- Modelo orientado a **agregados**: documentos que contienen subdocumentos embebidos.
- Escalabilidad horizontal sencilla (**sharding**, replicación).

Ejemplo mental:

- Base de datos: `personas_db`
- Colección: `personas`
- Documento: representación JSON de una `Persona` (con su `direccion` embebida).

```json
{
  "_id": "abc123",
  "nombre": "Ada",
  "edad": 36,
  "email": "ada@example.com",
  "direccion": {
    "calle": "Main St 1",
    "ciudad": "Helsinki",
    "codigo_postal": "00100",
    "pais": "Finlandia"
  }
}
```

---

### 3.2 Dependencia básica de Spring Data MongoDB (Maven)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

Y en `application.properties` (Mongo local):

```properties
spring.data.mongodb.uri=mongodb://usuario:password@localhost:27017/personas_db?authSource=admin
```

---

### 3.3 Anotaciones comunes de Spring Data (válidas también para Mongo)

Estas vienen de `org.springframework.data.annotation.*`:

- `@Id`  
  Marca el campo identificador del documento (`_id` en Mongo).

- `@Version`  
  Campo de **control de versiones** para bloqueo optimista (optimistic locking).

- `@CreatedDate`, `@LastModifiedDate`, `@CreatedBy`, `@LastModifiedBy`  
  Para **auditoría** (requiere `@EnableMongoAuditing` en la configuración).

- `@Transient`  
  Indica que un campo **no se debe persistir** (aunque exista en la clase Java).

- `@PersistenceConstructor`  
  Indica qué constructor usar al reconstruir el objeto desde la base de datos
  (útil en clases inmutables o con campos `final`).

---

### 3.4 Anotaciones específicas de Spring Data MongoDB

Paquete principal: `org.springframework.data.mongodb.core.mapping.*` y `org.springframework.data.mongodb.core.index.*`.

#### 3.4.1 `@Document` – clase ↔ colección

Marca la clase como documento de una colección:

```java
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "personas")
public class Persona {

    @Id
    private String id;

    private String nombre;
    private Integer edad;
}
```

Si no se indica `collection`, Spring usará el nombre de la clase por convención.

#### 3.4.2 `@Field` – atributo ↔ campo del documento

Permite cambiar el nombre con el que se guarda un campo en MongoDB:

```java
import org.springframework.data.mongodb.core.mapping.Field;

public class Direccion {

    @Field("calle")
    private String calle;

    @Field("ciudad")
    private String ciudad;

    @Field("codigo_postal")
    private String codigoPostal;

    @Field("pais")
    private String pais;
}
```

Si no usamos `@Field`, el nombre del atributo se usa tal cual.

#### 3.4.3 Relaciones: `@DBRef` y `@DocumentReference`

Dos formas de representar **referencias** entre documentos:

- `@DBRef` (forma clásica MongoDB)  
  Guarda un DBRef; implica más lecturas internas y tiene limitaciones.
- `@DocumentReference` (más moderno en Spring Data)  
  Permite un modelado más flexible; Spring resuelve la relación.

```java
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
// import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.annotation.Id;

@Document("pedidos")
public class Pedido {

    @Id
    private String id;

    @DBRef // o @DocumentReference
    private Persona persona;
}
```

En Mongo, una alternativa muy común es **embebido** (meter la subestructura dentro del documento)
en lugar de hacer “joins” tradicionales.

#### 3.4.4 Índices: `@Indexed`, `@TextIndexed`, `@CompoundIndex`…

Spring Data MongoDB permite definir **índices** en las entidades:

```java
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document("usuarios")
@CompoundIndexes({
    @CompoundIndex(name = "nombre_apellidos_idx", def = "{'nombre': 1, 'apellidos': 1}")
})
public class Usuario {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @TextIndexed
    private String nombre;

    @TextIndexed
    private String apellidos;
}
```

Otras anotaciones de índice útiles:

- `@GeoSpatialIndexed` – Índice geoespacial (coordenadas).
- `@Sharded` – Indica claves de partición en colecciones shardadas.

---

### 3.5 Repositorios en Spring Data MongoDB

Para acceder a la base de datos se usa un **repositorio** que extiende `MongoRepository`:

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends MongoRepository<Persona, String> {

    Optional<Persona> findByEmail(String email);

    List<Persona> findByNombreContainingIgnoreCase(String nombre);

    List<Persona> findByEdadBetween(Integer min, Integer max);

    @Query("{ 'direccion.ciudad': ?0 }")
    List<Persona> findByCiudad(String ciudad);

    @Query("{ 'email': { $regex: ?0, $options: 'i' } }")
    List<Persona> findByEmailDomain(String domainPart);
}
```

- Métodos como `findByNombreContainingIgnoreCase` se construyen a partir del **nombre del método**.
- La anotación `@Query` permite usar **consultas MongoDB** directamente en JSON.

---

### 3.6 Ejemplo completo: Persona con Direccion embebida

```java
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;

@Document(collection = "personas")
public class Persona {

    @Id
    private String id;

    @Field("nombre")
    private String nombre;

    @Field("edad")
    private Integer edad;

    @Field("email")
    private String email;

    @Field("direccion")
    private Direccion direccion;

    // getters, setters, constructores...
}
```

```java
import org.springframework.data.mongodb.core.mapping.Field;

public class Direccion {

    @Field("calle")
    private String calle;

    @Field("ciudad")
    private String ciudad;

    @Field("codigo_postal")
    private String codigoPostal;

    @Field("pais")
    private String pais;

    // getters, setters, constructores...
}
```

---

### 3.7 Ideas clave para recordar (NoSQL documental)

- Mongo guarda documentos JSON/BSON, no filas.
- No hay joins “clásicos”; se prefiere **embebido** o consultas específicas.
- El modelo es más flexible; el esquema no está tan rígidamente definido.
- Spring Data MongoDB simplifica muchísimo el acceso y el mapeo con anotaciones y repositorios.

---

## 4) ¿Cuándo usar ORDBMS, OODBMS o NoSQL documental?

**Elige ORDBMS si…**

- Necesitas **SQL**, BI, reporting, integraciones estándar.
- Tus datos encajan bien en **tablas** con algún extra (UDT/arrays).
- Quieres aprovechar el **ecosistema** relacional (drivers, ORMs, herramientas).

**Elige OODBMS si…**

- Tu dominio es **muy OO** (objetos complejos con mucha **navegación** entre ellos).
- Quieres **persistencia transparente** de objetos y evitar mapeo relacional.
- Necesitas **transacciones largas** y **versionado** de objetos (CAD, PLM, ingeniería...).

**Elige NoSQL documental (MongoDB) si…**

- La estructura de los datos cambia a menudo o no está cerrada.
- Trabajas con **agregados** (documentos con subdocumentos y listas) y quieres leerlos de una vez.
- Necesitas **escalabilidad horizontal** (sharding) y alta disponibilidad.
- Estás en ecosistema Java/Spring y quieres una integración directa con Spring Data.

> En la práctica, muchas apps usan ORDBMS + ORM (JPA/Hibernate) para acercarse a la naturalidad OO manteniendo SQL detrás, y otras combinan relacional + Mongo para diferentes partes del sistema.

---

## 5) Recuerda

- **UDT:** tipo definido por usuario (ORDBMS).  
- **OID:** identidad de objeto (OODBMS).  
- **OQL:** lenguaje de consultas para objetos (ODMG).  
- **JSON/BSON:** formatos de documento habituales en NoSQL documental.  
- **Agregado:** conjunto de datos que se leen/escriben juntos (documento con subdocumentos).  
- **Persistencia por alcance:** se guardan los objetos accesibles desde la raíz guardada (OODBMS).  
- **ACID:** propiedades de transacciones (Atomicidad, Consistencia, Aislamiento, Durabilidad).  
- **Spring Data:** conjunto de proyectos de Spring que unifican el acceso a distintos tipos de BBDD (JPA, Mongo, etc.) mediante repositorios y anotaciones.

<div align="center">
    <img src=images/relacional-objetos.png width="300">
</div>

> [Resumen](JPA-NO-SQLP.md).

</div>
