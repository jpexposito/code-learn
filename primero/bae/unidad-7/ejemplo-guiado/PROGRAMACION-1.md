<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Programando en BBDD

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

### 🔧 ¿Qué son los delimitadores en MySQL?

En MySQL, un **delimitador** es el símbolo que marca el final de una instrucción SQL.

#### 🧱 Delimitador por defecto

El delimitador por defecto es el punto y coma (`;`), que MySQL usa para saber cuándo termina una instrucción.

```sql
SELECT * FROM clientes;
```

---

### 🧠 ¿Por qué cambiar el delimitador?

Cuando se crean **procedimientos almacenados**, **funciones**, **triggers** o **vistas complejas**, se necesita usar `;` dentro del bloque de código.  
Por eso, se cambia temporalmente el delimitador para evitar que MySQL interprete el `;` interno como el final de la instrucción.

---

####  Ejemplo práctico con un procedimiento en la BBDD `ventas`

```sql
DELIMITER $$

CREATE PROCEDURE total_gastado_por_cliente(IN id_cliente INT)
BEGIN
    SELECT c.nombre, SUM(p.precio * v.cantidad) AS total
    FROM ventas v
    JOIN clientes c ON v.cliente_id = c.id
    JOIN productos p ON v.producto_id = p.id
    WHERE c.id = id_cliente
    GROUP BY c.nombre;
END $$

DELIMITER ;
```

#### 📌 Explicación

- `DELIMITER $$` indica que la instrucción terminará con `$$` en lugar de `;`
- El bloque del procedimiento puede usar `;` internamente sin confundir a MySQL
- Al final se restablece el delimitador original con `DELIMITER ;`

---

#### 📝 ¿Dónde es útil en la BBDD `ventas`?

- Al definir **procedimientos** para obtener ventas por producto o cliente
- Al crear **triggers** para actualizar estadísticas automáticamente
- En cualquier lógica de negocio compleja que necesite bloques múltiples de SQL

### 🛠️ Trabajando con Procedimientos y delimitadores

#### 📌 Nombre del procedimiento: `total_ventas_por_cliente`

Este procedimiento recibe como parámetro el ID de un cliente y devuelve el total de productos comprados y el importe total gastado por ese cliente.

---

#### 📄 Código del procedimiento

```sql
DELIMITER $$

CREATE PROCEDURE total_ventas_por_cliente(IN id_cliente INT)
BEGIN
    SELECT 
        c.nombre,
        COUNT(v.id) AS numero_ventas,
        SUM(v.cantidad) AS total_unidades,
        SUM(v.cantidad * p.precio) AS total_gastado
    FROM ventas v
    JOIN clientes c ON v.cliente_id = c.id
    JOIN productos p ON v.producto_id = p.id
    WHERE c.id = id_cliente
    GROUP BY c.nombre;
END $$

DELIMITER ;
```

> **NOTA IMPORTANTE**: como podemos ver establecemos el delimitar **$$** para hacer uso del **;** si fuera necesario. Una vez creado el procedimiento, restablecemos el delimitador por defecto **;**.
---

#### 🧠 ¿Qué hace este procedimiento?

- Busca todas las ventas asociadas al cliente indicado.
- Calcula:
  - Número total de ventas realizadas.
  - Unidades totales compradas.
  - Monto total gastado (precio * cantidad).

---

#### 🧪 ¿Cómo se utiliza?

```sql
CALL total_ventas_por_cliente(1);
```

Este ejemplo muestra el resumen de ventas para el cliente con ID = 1.

---

#### 🎯 ¿Para qué sirve?

- Para obtener fácilmente informes individuales de clientes.
- Para alimentar dashboards o reportes financieros.
- Para integrarse con aplicaciones que necesiten mostrar métricas por cliente.

---

####  Resultado esperado

| nombre       | numero_ventas | total_unidades | total_gastado |
|--------------|----------------|----------------|----------------|
| Ana Pérez    | 2              | 3              | 1300.00        |

_(Los valores dependen de los datos en la tabla `ventas`)_, aunque si tienes los datos mínimos obtendrás ese resultado.

### ❓ Preguntas sobre Procedimientos en MySQL en un posible examen (reflexiona)

- Obtener todas las ventas realizadas entre dos fechas específicas, incluyendo el nombre del cliente, el producto, la cantidad y el total gastado por línea de venta.
- La llamada al procedimiento será:

    ```sql
    CALL ventas_por_rango_fechas('2024-05-01', '2024-05-15');
    ```
    - En primer lugar, genera la consulta con dos parámetros de ejemplo, que simule los parámetros de entrada, para después encapsular en un procedimiento.
    - La salida esperada es la siguiente:

| fecha       | cliente        | producto     | cantidad | total   |
|-------------|----------------|--------------|----------|---------|
| 2024-05-01  | Ana Pérez      | Laptop       | 1        | 1200.00 |
| 2024-05-03  | Luis Gómez     | Teléfono     | 2        | 1600.00 |
| 2024-05-05  | Carla Ruiz     | Tablet       | 1        | 500.00  |
| 2024-05-07  | Pedro Sánchez  | Auriculares  | 3        | 450.00  |
| 2024-05-10  | Lucía Martínez | Monitor      | 1        | 300.00  |
| 2024-05-11  | Jorge Torres   | Laptop       | 1        | 1200.00 |
| 2024-05-12  | Ana Pérez      | Teclado      | 2        | 100.00  |
| 2024-05-13  | Luis Gómez     | Tablet       | 1        | 500.00  |
| 2024-05-14  | Carla Ruiz     | Teléfono     | 1        | 800.00  |
| 2024-05-15  | Pedro Sánchez  | Monitor      | 2        | 600.00  |

### 🧮 Trabajado con funciones y delimitadores

#### 📘 ¿Qué es una función almacenada?

Una **función almacenada** en MySQL es un bloque de código SQL que realiza una operación y devuelve un único valor.  
A diferencia de un procedimiento, **una función puede usarse dentro de consultas SQL como si fuera una función nativa** (`NOW()`, `ROUND()`, etc.). *Es dedir, (`NOW()`, `ROUND()`, etc.), son funciones propias de MYSQL*.

---

#### 🧱 Sintaxis básica

```sql
DELIMITER $$

CREATE FUNCTION nombre_funcion(parámetros)
RETURNS tipo_de_dato
DETERMINISTIC
BEGIN
    -- lógica SQL
    RETURN valor;
END $$

DELIMITER ;
```

---

### 🧪 Ejemplo: Función `calcular_total_venta`

Esta función toma un `producto_id` y una `cantidad`, y devuelve el total (precio * cantidad) desde la tabla `productos`.

#### 📄 Código

```sql
DELIMITER $$

CREATE FUNCTION calcular_total_venta(pid INT, cant INT)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE precio_unitario DECIMAL(10,2);

    SELECT precio INTO precio_unitario
    FROM productos
    WHERE id = pid;

    RETURN precio_unitario * cant;
END $$

DELIMITER ;
```

---

#### 🧠 ¿Qué hace?

- Consulta el precio del producto con el ID dado.
- Multiplica ese precio por la cantidad indicada.
- Devuelve el resultado como total de la venta.
- Recuerda que al final se restablece el delimitador original con `DELIMITER ;`
---

####  ¿Cómo se usa?

```sql
SELECT calcular_total_venta(1, 3);
```

Esto devolverá el total para 3 unidades del producto con ID 1.

| calcular_total_venta(1, 3) |
|----------------------------|
| 3600.00                    |

---

#### ⚠️ Consideraciones al usar funciones

- Una función **debe devolver un solo valor** (`RETURN`).
- No puede realizar operaciones como `INSERT`, `UPDATE`, ni `COMMIT`.
- Solo se puede usar en **consultas SELECT, SET o WHERE**, no como sentencia autónoma.

---

#### 🎯 ¿Para qué sirven?

- Para reutilizar cálculos o transformaciones frecuentes.
- Para simplificar lógica de negocio en informes o consultas.
- Para integrarse dentro de vistas, triggers o procedimientos.

#### 🔄 Funciones Deterministas vs No Deterministas 

En MySQL, una función puede ser **determinista** o **no determinista**, lo cual afecta cómo el optimizador trata su resultado en cachés y consultas.

---

#####  Función Determinista

Una función **determinista** siempre devuelve el mismo resultado cuando se le pasan los mismos argumentos.

**Ejemplo:**

```sql
DELIMITER $$

CREATE FUNCTION calcular_iva(precio DECIMAL(10,2))
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    RETURN precio * 0.21;
END $$

DELIMITER ;
```

📌 Esta función es determinista porque:
- Siempre que se le pase el mismo `precio`, devuelve el mismo `iva`.
- No depende de variables externas ni del tiempo actual.

---

##### ❌ Función No Determinista

Una función **no determinista** puede devolver resultados distintos con los mismos argumentos, porque depende de factores externos.

**Ejemplo:**

```sql
DELIMITER $$

CREATE FUNCTION fecha_actual_mas_dias(dias INT)
RETURNS DATE
NOT DETERMINISTIC
BEGIN
    RETURN CURDATE() + INTERVAL dias DAY;
END $$

DELIMITER ;
```

📌 Esta función es no determinista porque:
- Depende de `CURDATE()`, que cambia cada día.
- Llamarla con el mismo número de días dará distintos resultados dependiendo de cuándo se ejecute.

---

##### 🧠 ¿Por qué es importante declarar esto?

- El motor de MySQL puede **optimizar mejor funciones deterministas**.
- Funciones no deterministas no pueden usarse en **índices** o **columnas generadas almacenadas** (`STORED`).
- Declarar incorrectamente una función puede causar errores de ejecución o resultados inesperados.

### ⚡ Triggers y delimitadores

#### 📘 ¿Qué es un trigger?

Un **trigger** (disparador) es un bloque de código SQL que se ejecuta automáticamente cuando ocurre un evento específico (`INSERT`, `UPDATE` o `DELETE`) sobre una tabla.

Se asocia directamente a una tabla y se activa antes o después del evento definido.

---

#### 📌 ¿Para qué se usan los triggers?

- Auditar cambios en los datos (guardar logs de inserciones, modificaciones o eliminaciones)
- Validar o transformar datos antes de insertarlos o modificarlos
- Mantener integridad o consistencia entre tablas
- Generar actualizaciones automáticas (como ajustar inventarios o estadísticas)

---

#### 🧱 Sintaxis básica

```sql
CREATE TRIGGER nombre_trigger
{BEFORE | AFTER} {INSERT | UPDATE | DELETE}
ON nombre_tabla
FOR EACH ROW
BEGIN
    -- Código SQL
END;
```

---

#### 🧪 Ejemplo: Trigger de auditoría para la tabla `ventas`

```sql
DELIMITER $$

CREATE TRIGGER after_insert_venta
AFTER INSERT ON ventas
FOR EACH ROW
BEGIN
    INSERT INTO auditoria_ventas (venta_id, cliente_id, fecha, accion)
    VALUES (NEW.id, NEW.cliente_id, NOW(), 'INSERT');
END $$

DELIMITER ;
```

##### 🔧 Tabla `auditoria_ventas` requerida:

```sql
CREATE TABLE auditoria_ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT,
    cliente_id INT,
    fecha DATETIME,
    accion VARCHAR(20)
);
```

#### 🎯 Disparo del Trigger `after_insert_venta`

##### 🧾 Entrada de ejemplo que activa el trigger

```sql
INSERT INTO ventas (cliente_id, producto_id, fecha, cantidad)
VALUES (1, 2, '2024-05-16', 1);
```

---

##### ⚙️ ¿Qué ocurre cuando se ejecuta esta instrucción?

1. Se inserta una nueva fila en la tabla `ventas`.
2. El trigger `after_insert_venta` se activa automáticamente porque es un trigger de tipo `AFTER INSERT`.
3. El trigger ejecuta una acción adicional:
   - Inserta un registro en la tabla `auditoria_ventas`
   - Registra el `venta_id`, `cliente_id`, la fecha actual (`NOW()`), y la acción realizada (`'INSERT'`)

---

##### 📋 Resultado esperado en la tabla `auditoria_ventas`

| id | venta_id | cliente_id | fecha              | accion |
|----|----------|------------|--------------------|--------|
| 1  | 10       | 1          | 2024-05-16 12:30:00| INSERT |

> Nota: El `venta_id` dependerá del valor autogenerado al insertar en la tabla `ventas`.

---

#####  ¿Por qué es útil?

Este mecanismo es útil para:

- Llevar un control o bitácora automática de cambios
- Generar registros para auditoría sin intervención manual
- Mejorar la trazabilidad del sistema sin afectar la lógica de la aplicación principal

---

#### 🧠 ¿Qué hace este trigger?

- Cada vez que se inserta una fila en `ventas`, automáticamente se registra en `auditoria_ventas`:
  - El ID de la venta
  - El cliente relacionado
  - La fecha y hora del evento
  - La acción realizada (`INSERT`)

---

#### ⚠️ Consideraciones importantes

- Un trigger no puede modificar la misma tabla en la que se activa.
- Solo puede haber un trigger `BEFORE` y uno `AFTER` por cada tipo de evento (`INSERT`, `UPDATE`, `DELETE`).
- Ten cuidado con la recursividad y el rendimiento en operaciones masivas.

---

####  Uso típico

- Control de cambios y auditoría
- Reglas de negocio automáticas
- Mantenimiento de relaciones dependientes o estadísticas

</div>