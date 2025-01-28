<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (TIPOS DE RELACIONES)

# 1. Relación Uno a Uno (1:1)

**Descripción**: En una relación uno a uno, un registro de una tabla está relacionado con un solo registro en otra tabla.  

**Ejemplo**: Un empleado puede tener solo un pasaporte y cada pasaporte está asociado con un solo empleado.

## 2. Relación Uno a Muchos (1:N)

**Descripción**: En una relación uno a muchos, un registro de una tabla está relacionado con múltiples registros en otra tabla, pero un registro en la segunda tabla solo puede estar asociado con un solo registro en la primera tabla.  

**Ejemplo**: Un autor puede escribir muchos libros, pero cada libro tiene un solo autor.

## 3. Relación Muchos a Muchos (N:M)

**Descripción**: En una relación muchos a muchos, múltiples registros en una tabla pueden estar relacionados con múltiples registros en otra tabla.  

**Ejemplo**: Un estudiante puede estar inscrito en varios cursos, y un curso puede tener varios estudiantes inscritos.

## 4. Relación Uno a Muchos con Atributos en la Relación

**Descripción**: Una relación uno a muchos en la que la tabla intermedia (la que une las dos entidades) contiene atributos adicionales, es decir, la relación tiene más información asociada.  

**Ejemplo**: Un cliente puede realizar múltiples compras, y cada compra tiene información adicional como la fecha y el total gastado.

## 5. Relación Muchos a Muchos con Atributos en la Relación

**Descripción**: Similar a la relación muchos a muchos, pero en este caso, la tabla intermedia contiene atributos adicionales.  

**Ejemplo**: Un estudiante puede inscribirse en varios cursos, y un curso puede tener varios estudiantes. La relación también incluye la fecha de inscripción y el grado obtenido en cada curso.

## 6. Relación Recursiva (Relación de Auto-relación)

**Descripción**: Una relación recursiva ocurre cuando una entidad está relacionada consigo misma.  

**Ejemplo**: Un empleado puede tener a otros empleados a su cargo. En este caso, la entidad "Empleado" tiene una relación recursiva.

## 7. Herencia (Especialización o Generalización)

**Descripción**: La herencia es un tipo de relación que refleja una jerarquía entre entidades, donde una entidad más general se divide en entidades más específicas (especialización) o donde varias entidades específicas se unifican en una entidad más general (generalización).  

**Ejemplo**: Los vehículos pueden tener una entidad "Vehículo" general, y luego pueden especializarse en "Coche", "Moto" y "Camión", que tienen atributos adicionales específicos.

## 8. Relación de Clave Compuesta

**Descripción**: Cuando una relación entre dos tablas involucra más de una columna de clave primaria para identificar de manera única la relación entre los registros.  

**Ejemplo**: Una tabla que relacione estudiantes y cursos, donde la combinación del ID de estudiante y el ID de curso forma una clave compuesta.

</div>