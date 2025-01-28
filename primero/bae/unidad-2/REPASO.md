<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (RESUMEN E/R)

## 1. Entidad

Una **entidad** representa un objeto o concepto del mundo real sobre el que se almacena información. Cada entidad tiene atributos que la describen.

### Ejemplo:
En un sistema de gestión de biblioteca, algunas entidades podrían ser:
- `Libro`
- `Autor`
- `Miembro`
- `Prestamo`

### Documentación:

#### Entidad: **Libro**
- **Descripción**: Representa un libro en la biblioteca.
- **Atributos**:
  - `ISBN` (clave primaria)
  - `Título`
  - `Autor`
  - `Fecha_de_publicación`
  - `Número_de_páginas`
  - `Género`

## 2. Atributos

Los **atributos** son las características que describen una entidad. Cada entidad puede tener uno o más atributos, que pueden clasificarse en los siguientes tipos:

- **Atributos simples**: No se pueden dividir. Ejemplo: `Nombre`, `Fecha de nacimiento`.
- **Atributos compuestos**: Se pueden descomponer. Ejemplo: `Dirección` (compuesta de `Calle`, `Ciudad`, `Código postal`).
- **Atributos multivaluados**: Pueden tener más de un valor. Ejemplo: `Teléfonos` (una persona puede tener varios números).
- **Atributos derivados**: Son calculados a partir de otros. Ejemplo: `Edad` (calculado a partir de la fecha de nacimiento).

## 3. Relaciones

Las **relaciones** entre entidades representan cómo las entidades se conectan o interactúan entre sí. Las relaciones pueden ser de varios tipos:

### Tipos de Relaciones:

- **Relación uno a uno (1:1)**: Un registro en una entidad está asociado a un solo registro en otra entidad.
  - Ejemplo: Cada `Empleado` tiene un `Oficina` asignada.
  
- **Relación uno a muchos (1:N)**: Un registro en una entidad puede estar asociado a muchos registros en otra entidad.
  - Ejemplo: Un `Autor` puede escribir muchos `Libros`.
  
- **Relación muchos a muchos (N:M)**: Muchos registros de una entidad pueden estar asociados a muchos registros de otra entidad.
  - Ejemplo: Un `Estudiante` puede tomar varios `Cursos`, y un `Curso` puede ser tomado por varios `Estudiantes`.

### Documentación de Relaciones:

#### Relación: **Autor-Escribe-Libro**

- **Entidades involucradas**: `Autor` y `Libro`
- **Cardinalidad**: Un `Autor` puede escribir varios `Libros`, pero cada `Libro` es escrito por un único `Autor` (1:N).

## 4. Clave Primaria (PK)

La **clave primaria** es un atributo o conjunto de atributos que identifican de manera única a cada instancia de una entidad. Es fundamental para garantizar la integridad de los datos.

### Ejemplo:

En la entidad `Libro`, el atributo `ISBN` puede ser la clave primaria.

## 5. Clave Foránea (FK)

La **clave foránea** es un atributo que establece una relación entre dos entidades. Es un atributo en una entidad que hace referencia a la clave primaria de otra entidad.

### Ejemplo:

En la entidad `Prestamo`, el atributo `ID_Miembro` es una clave foránea que hace referencia a la entidad `Miembro`.

## 6. Diagrama ER

El **Diagrama Entidad-Relación (ER)** es una representación gráfica de las entidades, atributos y relaciones en un sistema. Es útil para visualizar las conexiones y asegurarse de que el modelo sea coherente y completo.

### Símbolos Comunes:

- Rectángulos: Representan **entidades**.
- Elipses: Representan **atributos**.
- Rombo: Representa una **relación**.
- Líneas: Representan las **conexiones** entre entidades, atributos y relaciones.

## 7. Reglas de Negocio

Las **reglas de negocio** son restricciones o condiciones que deben cumplirse en el sistema de base de datos, relacionadas con las entidades y sus interacciones.

### Ejemplo:

Un `Miembro` no puede realizar un `Prestamo` si ya tiene un libro prestado que no ha devuelto.

## Ejemplo de Modelo ER para una Biblioteca

### 1. Entidades:

- **Libro**:
  - Atributos: `ISBN`, `Título`, `Autor`, `Fecha_publicación`
  
- **Miembro**:
  - Atributos: `ID_Miembro`, `Nombre`, `Correo`, `Teléfono`
  
- **Prestamo**:
  - Atributos: `ID_Prestamo`, `Fecha_Prestamo`, `Fecha_Devolucion`

### 2. Relaciones:

- Un `Miembro` puede realizar varios `Prestamos` (1:N).
- Un `Libro` puede estar asociado a muchos `Prestamos` (1:N).

### 3. Cardinalidades:

- **Miembro** (1) → **Prestamo** (N)
- **Libro** (1) → **Prestamo** (N)

</div>