<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Trabajando con Indices/Vistas

## Requisitos

- [Base datos en docker](../../../../comun/docker/utilidades/mysql/)

## Introducción

En primer lugar vamos a crear una base de datos llamada **ventas** para trabajar con ella. Los pasos que vamos a realizar es la eliminación si existe y luego crear e insertar datos.

```sql
DROP DATABASE IF EXISTS ventas;
CREATE DATABASE ventas;
USE ventas;
```

## Creación e inserción de datos

En siguiente paso sera la creación e inserción de datos dentro de la esta.

```sql
CREATE TABLE clientes ( ...
```

Para lograrlo accedemos al navegador (con mysql arrancado en docker) http://localhost:8099 y entramos como **admin**. *Si accedieramos como bae no tendríamos permisos*.

Ejecutamos el **script** que se encuentra en el siguiente [enlace](script/bbdd.sql).

y pulsamos *go/continuar* en función del idioma seleccionado. Esto creará la base de datos y las tablas con los respectidos datos insertados.

## Trabajando con índices

### Creando índicas

```sql
CREATE INDEX idx_ciudad ON clientes(ciudad);
CREATE INDEX idx_fecha ON ventas(fecha);
```

*obtendremos un resultado similar al siguiente*.

```console
 MySQL ha devuelto un conjunto de valores vacío (es decir: cero columnas). (La consulta tardó 0.0469 segundos.)
 ```

### ¿Qué se hace?

Se crean dos índices:

- `idx_ciudad` sobre la columna `ciudad` de la tabla `clientes`  
- `idx_fecha` sobre la columna `fecha` de la tabla `ventas`

### ¿Cuál es su objetivo?

**Optimizar el rendimiento de las consultas que filtran o ordenan por estas columnas**.  
*Los índices permiten a MySQL encontrar los registros más rápidamente, sin escanear toda la tabla*.

### ¿Qué se obtiene?

- Consultas más rápidas al buscar clientes por ciudad o ventas por fecha  
- Mejora significativa en el tiempo de respuesta en bases de datos con gran volumen de datos

### 🔍 Cómo ver los índices y analizar su rendimiento

#### Ver los índices existentes en una tabla

#### Usar `SHOW INDEX`

```sql
SHOW INDEX FROM nombre_tabla;
```

**Ejemplo:**

```sql
SHOW INDEX FROM clientes;
```

| Table    | Non_unique | Key_name   | Seq_in_index | Column_name | Collation | Cardinality | Sub_part | Packed | Null | Index_type | Comment | Index_comment | Visible | Expression |
|----------|------------|------------|---------------|--------------|-----------|--------------|----------|--------|------|-------------|---------|----------------|---------|-------------|
| clientes | 0          | PRIMARY    | 1             | id           | A         | 6            | NULL     | NULL   |      | BTREE       |         |                | YES     |             |
| clientes | 1          | idx_ciudad | 1             | ciudad       | A         | 5            | NULL     | NULL   | YES  | BTREE       |         |                | YES     |             |


**¿Qué muestra?**

- `Key_name`: nombre del índice
- `Column_name`: columna indexada
- `Index_type`: tipo de índice (BTREE, FULLTEXT, etc.)
- `Non_unique`: 0 si es único, 1 si no lo es
- `Cardinality`: número estimado de valores únicos (útil para evaluar eficiencia)

---

#### Analizar el rendimiento de un índice

##### Usar `EXPLAIN`

```sql
EXPLAIN SELECT * FROM clientes WHERE ciudad = 'Madrid';
```

**¿Qué muestra?**

- `key`: el índice utilizado
- `rows`: número estimado de filas leídas (menos = mejor)
- `type`: tipo de acceso (ej. `ref`, `range`, `ALL`)

🔸 Si `type = ALL`, significa que no se usa ningún índice (escaneo completo de la tabla).

| id | select_type | table    | partitions | type | possible_keys | key        | key_len | ref   | rows | filtered | Extra |
|----|-------------|----------|------------|------|----------------|------------|---------|-------|------|----------|-------|
| 1  | SIMPLE      | clientes | NULL       | ref  | idx_ciudad     | idx_ciudad | 203     | const | 2    | 100.00   | NULL  |

---

##### En MySQL 8.0+: Usar `EXPLAIN ANALYZE`

```sql
EXPLAIN ANALYZE SELECT * FROM clientes WHERE ciudad = 'Madrid';
```

Proporciona tiempos estimados de ejecución y confirmación del uso de índices.

*obtendremos una salida similar a la siguiente:*

```code
Index lookup on clientes using idx_ciudad (ciudad='Madrid')  (cost=0.7 rows=2) (actual time=0.507..0.509 rows=2 loops=1)
```

---

#### Recomendaciones

- Verifica la **cardinalidad** del índice: una mayor cardinalidad implica mayor eficiencia.
- Evita usar funciones sobre columnas indexadas (por ejemplo, `YEAR(fecha)`) ya que desactivan el índice.
- Usa `EXPLAIN` para comparar consultas y validar el uso de índices correctamente.

### Eliminación de índices

```sql
DROP INDEX idx_ciudad ON clientes;
DROP INDEX idx_fecha ON ventas;
```

### ¿Qué se hace?

Se eliminan los índices creados anteriormente.

#### ¿Cuál es su objetivo?

Limpiar la base de datos o ajustar el diseño si los índices ya no son necesarios.

#### ¿Qué se obtiene?

- Liberación de recursos del sistema  
- Reducción de sobrecarga en inserciones o actualizaciones si los índices eran innecesarios

### ❓ Preguntas teóricas sobre Índices en MySQL en un posible examen (reflexiona)

1. **¿Qué es un índice en MySQL y para qué se utiliza principalmente?**

2. **¿Qué diferencia hay entre un índice único (`UNIQUE`) y un índice normal? ¿Qué efecto tiene sobre los datos?**

3. **¿Qué tipo de columnas son buenas candidatas para tener un índice y por qué?**

4. **¿Qué ocurre si aplicas una función como `UPPER()` o `YEAR()` sobre una columna indexada en una consulta? ¿Se sigue utilizando el índice?**

5. **¿Cómo puedes verificar si una consulta está usando un índice y cuál está usando específicamente?**

## Trabajando con vistas

Una **vista** (en inglés, *view*) es una tabla virtual basada en el resultado de una consulta `SELECT`.  
No almacena datos físicamente, sino que actúa como una capa de abstracción sobre las tablas reales.

Se define una sola vez y puede reutilizarse como si fuera una tabla común.

---

### 🧭 ¿Para qué se usan las vistas?

Las vistas tienen múltiples usos en MySQL:

- ✅ **Simplificar consultas complejas**: encapsulan `JOIN`, filtros y cálculos para que no tengas que escribirlos cada vez.
- 🔒 **Restringir acceso a datos sensibles**: se pueden mostrar solo algunas columnas a ciertos usuarios.
- 🔁 **Reutilizar lógica de negocio**: se centraliza la definición de datos derivados como totales, promedios, relaciones, etc.
- 📐 **Organizar consultas y estructuras**: especialmente útil en aplicaciones grandes o con muchos informes.

### Creando vistas

#### 🧱 Ejemplo básico

```sql
CREATE VIEW vista_clientes_madrid AS
SELECT nombre, correo
FROM clientes
WHERE ciudad = 'Madrid';
```

Esto crea una vista que actúa como una tabla virtual con todos los clientes de Madrid.

Puedes usarla así:

```sql
SELECT * FROM vista_clientes_madrid;
```

---

#### 📝 Importante

- Una vista **no almacena datos propios**, solo muestra datos en tiempo real de las tablas subyacentes.
- No siempre se puede modificar datos a través de una vista, especialmente si incluye agregaciones o `JOIN`.

---

#### 📌 Vista 1: `vista_resumen_clientes`

```sql
CREATE VIEW vista_resumen_clientes AS
SELECT 
    c.id AS cliente_id,
    c.nombre,
    c.ciudad,
    COUNT(v.id) AS total_ventas,
    SUM(p.precio * v.cantidad) AS total_gastado
FROM clientes c
LEFT JOIN ventas v ON c.id = v.cliente_id
LEFT JOIN productos p ON v.producto_id = p.id
GROUP BY c.id, c.nombre, c.ciudad;
```

#### ¿Qué hace esta vista?

Agrupa los datos por cliente, mostrando cuántas ventas ha realizado cada uno y cuánto ha gastado en total.

#### ¿Para qué sirve?

- Obtener un resumen por cliente.
- Identificar los clientes más activos o más rentables.
- Útil para informes gerenciales y estrategias de fidelización.

#### ¿Qué se obtiene?

Una tabla con los siguientes campos:
- `cliente_id`
- `nombre`
- `ciudad`
- `total_ventas`
- `total_gastado`

---

#### 📌 Vista 2: `vista_productos_mas_vendidos`

```sql
CREATE VIEW vista_productos_mas_vendidos AS
SELECT 
    p.id AS producto_id,
    p.nombre,
    p.categoria,
    SUM(v.cantidad) AS unidades_vendidas,
    SUM(v.cantidad * p.precio) AS ingresos_totales
FROM productos p
JOIN ventas v ON p.id = v.producto_id
GROUP BY p.id, p.nombre, p.categoria;
```

#### ¿Qué hace esta vista?

Agrupa las ventas por producto, mostrando la cantidad total de unidades vendidas y los ingresos generados por cada producto.

#### ¿Para qué sirve?

- Identificar los productos más vendidos.
- Comparar ingresos por categoría.
- Tomar decisiones sobre stock y promociones.

#### ¿Qué se obtiene?

Una tabla con los siguientes campos:
- `producto_id`
- `nombre`
- `categoria`
- `unidades_vendidas`
- `ingresos_totales`

---

#### 🧹 Cómo eliminar vistas en MySQL

##### 🔧 Sintaxis básica

```sql
DROP VIEW nombre_de_la_vista;
```

---

#### 🧠 Conclusión

Estas dos vistas permiten:

- Resumir información relevante para la toma de decisiones.
- Facilitar análisis sin tener que escribir consultas complejas repetidamente.
- Servir como base para dashboards, reportes o restricciones de acceso a ciertos datos.

### ❓ Preguntas teóricas sobre Vistas en MySQL en un posible examen (reflexiona)

1. **¿Qué es una vista en MySQL y en qué se diferencia de una tabla?**

2. **¿Qué ventajas ofrece el uso de vistas en consultas complejas y en la gestión de permisos de usuarios?**

3. **¿Qué limitaciones tiene una vista cuando se trata de insertar, actualizar o eliminar datos a través de ella?**

4. **Escribe la instrucción SQL para crear una vista que muestre el nombre del cliente y el total gastado, usando las tablas `clientes`, `ventas` y `productos`.**

5. **¿Qué sucede si eliminas una vista que está siendo utilizada por otra vista o procedimiento almacenado? Explica brevemente.**

</div>