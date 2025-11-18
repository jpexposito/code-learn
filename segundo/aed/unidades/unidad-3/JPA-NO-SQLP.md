<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (JPA - NO SQL)

<div align="center">
    <img src=images/spring-java-relacional-objetos.png width="300">
</div>

## Introducción

Este documento compara el uso de **Spring Data MongoDB** (NoSQL documental)
y **Spring Data JPA** (SQL relacional) para que puedas ver rápidamente semejanzas y diferencias.

---

## 1. Modelo de datos

| Concepto                     | MongoDB (NoSQL documental)                | JPA / SQL (relacional)                     |
|-----------------------------|-------------------------------------------|-------------------------------------------|
| Unidad básica de datos      | Documento (JSON/BSON)                     | Fila (row)                                |
| Agrupación                  | Colección                                 | Tabla                                     |
| Esquema                     | Flexible (schema-less)                    | Fijo (schema-based)                       |
| Relaciones                  | Referencias / documentos embebidos        | Claves foráneas y joins                   |
| Normalización               | Menos estricta (se repiten datos)         | Alta (3FN, etc.)                          |
| Consultas complejas         | Aggregation framework, pipelines          | SQL / JPQL con joins                      |

---

## 2. Anotaciones principales: equivalencias aproximadas

| Intención                              | MongoDB (Spring Data Mongo)     | JPA / SQL (Spring Data JPA)        |
|----------------------------------------|---------------------------------|------------------------------------|
| Marcar clase mapeada a BBDD           | `@Document`                     | `@Entity`                          |
| Nombre de colección / tabla           | `@Document(collection = ...)`   | `@Table(name = ...)`              |
| Identificador                          | `@Id` (Spring Data)             | `@Id` (JPA) + `@GeneratedValue`    |
| Campo / columna                        | `@Field("nombre_campo")`        | `@Column(name = "nombre_columna")` |
| Objeto embebido                        | subdocumento / composición      | `@Embeddable` + `@Embedded`        |
| Relaciones entre entidades             | `@DBRef` / `@DocumentReference` | `@OneToMany`, `@ManyToOne`, etc.   |
| Índices                                | `@Indexed`, `@TextIndexed`, etc.| `@Index` (en `@Table`) o DDL       |
| Auditoría (fechas, usuario, etc.)      | `@CreatedDate`, `@LastModifiedDate` (con `@EnableMongoAuditing`) | mismas anotaciones + `@EnableJpaAuditing` |
| Campo no persistente                   | `@Transient` (Spring Data)      | `@Transient` (JPA) / `transient` Java |

> Nota: algunas anotaciones se llaman igual (`@Id`, `@Transient`, `@Version`), pero
> pertenecen a paquetes distintos y papel concreto en cada tecnología.

---

## 3. Modelo de ejemplo: Persona y Direccion

### 3.1. MongoDB

```java
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
}

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

### 3.2. JPA / Relacional

```java
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

@Embeddable
public class Direccion {

    private String calle;
    private String ciudad;
    private String codigoPostal;
    private String pais;
}
```

En Mongo se guarda como documento con subdocumento; en JPA, todo se reparte en columnas
de la misma tabla `personas`.

---

## 4. Repositorios: patrón común

En los dos casos se usan repositorios Spring Data:

- MongoDB: `MongoRepository<Persona, String>`
- JPA: `JpaRepository<Persona, Long>`

Ambos permiten métodos derivados del nombre:

```java
List<Persona> findByNombreContainingIgnoreCase(String nombre);
List<Persona> findByEdadBetween(Integer min, Integer max);
```

Y ambos permiten consultas personalizadas con `@Query`:

- En Mongo, `@Query` usa sintaxis JSON de Mongo.
- En JPA, `@Query` usa **JPQL** o SQL nativo.

Esto hace que el **estilo de programación** en Spring Data sea muy parecido,
aunque la base de datos subyacente sea muy distinta.

---

## 5. Escalabilidad, rendimiento y casos de uso

| Aspecto                     | MongoDB                                 | JPA / Relacional                             |
|----------------------------|-----------------------------------------|---------------------------------------------|
| Escalabilidad horizontal    | Muy buena (sharding nativo)            | Posible pero más compleja                   |
| Transacciones complejas    | Soporte moderno, pero más limitado     | Muy fuerte (ACID, joins complejos)          |
| Esquema                     | Flexible, fácil de cambiar             | Fijo; cambios requieren migraciones         |
| Lecturas densas (agregados)| Muy eficiente con documentos embebidos | Requiere joins (más costoso)                |
| Integridad referencial     | Gestión en la aplicación               | Constraints de BBDD (FKs)                   |

---

## 6. ¿Cuándo usar uno u otro? (resumen didáctico)

**MongoDB / NoSQL documental** es útil cuando:

- La estructura de los datos cambia a menudo.
- Trabajas con documentos grandes y anidados.
- Necesitas escalar en horizontal fácilmente.

**JPA / BBDD relacional** es ideal cuando:

- El modelo de datos está muy bien definido y estable.
- Necesitas integridad referencial fuerte y transacciones complejas.
- Quieres aprovechar todo el poder del SQL (consultas, joins, vistas, etc.).

**Con Spring Data**, el cambio de una tecnología a otra es menos doloroso
porque el patrón de acceso (repositorios, métodos, anotaciones de auditoría)
se mantiene bastante similar.

</div>
