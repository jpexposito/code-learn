<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (ORDBMS y BBDD orientadas a objetos)

<div align="center">
    <img src=images/spring-java-relacional-objetos.png width="300">
</div>

## Introducción

El **objetivo** es que entiendas las diferencias entre **gestores objeto–relacionales (ORDBMS)** y **gestores orientados a objetos (OODBMS)**, con ejemplos sencillos:  
características, ventajas, **persistencia de objetos** (simples y estructurados), **consultas** (SQL/OQL), **transacciones**, **acceso desde el lenguaje** y **cuándo elegir cada uno**.

---

## 0) En en 1 minuto

| Tema | ORDBMS | OODBMS |
|---|---|---|
| Modelo | Relacional con “extras OO” (UDT, colecciones, a veces métodos) | Objetos “tal cual” con identidad de objeto (OID), referencias y métodos |
| Lenguaje | **SQL** (con extensiones para objetos) | **OQL** (Object Query Language) + navegación por referencias |
| Persistencia | Tablas/columnas, tipos compuestos y arrays | Gráficos de objetos (persistencia por alcance) |
| Acceso desde el lenguaje | JDBC/ODBC, JPA/Hibernate, procedimientos | APIs nativas: `begin/store/query/commit` |
| Transacciones | ACID (bloqueo por **fila**) | ACID (bloqueo por **objeto**) + versiones/largas |
| Úsalo si… | Necesitas SQL, BI/reporting, integridad relacional y ecosistema maduro | Tu dominio es muy OO y navegas objetos complejos de forma natural |

---

## 🧩 1) ORDBMS (Object–Relational DBMS)

### 1.1 ¿Qué es?

Una base relacional **vitaminada** con rasgos OO:

- **UDT (User-Defined Types)** y **tipos compuestos**.
- **Colecciones** (arrays, varrays, nested tables).
- En algunos motores: **métodos** en tipos y **herencia**.
- Todo se consulta con **SQL**.

> Ejemplos con buen soporte objeto–relacional: **PostgreSQL** (tipos/arrays), **Oracle** (OBJECT TYPE, VARRAY/NESTED TABLE), **DB2**.

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
-- Si algo falla: ROLLBACK;
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

## 🧠 OODBMS (Object–Oriented DBMS)

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

## 3) ¿Cuándo usar ORDBMS y cuándo OODBMS?

**Elige ORDBMS si…**

- Necesitas **SQL**, BI, reporting, integraciones estándar.
- Tus datos encajan bien en **tablas** con algún extra (UDT/arrays).
- Quieres aprovechar **ecosistema** (drivers, ORMs, herramientas).

**Elige OODBMS si…**

- Tu dominio es **muy OO** (objetos complejos con mucha **navegación**).
- Quieres **persistencia transparente** de objetos y evitar mapeo relacional.
- Necesitas **transacciones largas** y **versionado** de objetos.

> En la práctica, muchas apps usan ORDBMS + ORM (JPA/Hibernate) para acercarse a la naturalidad OO manteniendo SQL detrás.


<div align="center">
    <img src=images/relacional-objetos.png width="300">
</div>

## 5) Recuerda

- **UDT:** tipo definido por usuario (ORDBMS).  
- **OID:** identidad de objeto (OODBMS).  
- **OQL:** lenguaje de consultas para objetos (ODMG).  
- **Persistencia por alcance:** se guardan los objetos accesibles desde la raíz guardada.  
- **ACID:** propiedades de transacciones (Atomicidad, Consistencia, Aislamiento, Durabilidad).

</div>