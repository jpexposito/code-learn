<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Enumerados)

## ¿Qué es un Enumerado en Java?

En Java, un **enumerado** (también conocido como `enum`) es un tipo de dato especial que permite definir un conjunto de constantes predefinidas. Los enumerados en Java proporcionan una forma más legible y estructurada de representar un grupo de valores constantes, en comparación con el uso de valores literales como enteros o cadenas de texto.

### Características de los Enumerados en Java

- **Valores constantes**: Los `enum` en Java son constantes que pertenecen a un tipo específico.
- **Seguridad de tipo**: Al usar `enum`, se asegura que solo se asignen valores válidos dentro del conjunto de constantes definidas.
- **Métodos y atributos**: A los enumerados se les pueden asociar métodos y atributos, lo que los hace más poderosos que solo valores constantes.
- **Iteración**: Java permite iterar sobre los valores de un `enum` utilizando el método `values()`.

### Ejemplo de un Enumerado en Java

```java
public enum Dia {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
}

public class TestEnum {
    public static void main(String[] args) {
        Dia dia = Dia.LUNES;
        System.out.println("Hoy es: " + dia);
    }
}
```

## Similitudes con las Bases de Datos

Los enumerados en Java tienen algunas similitudes con las estructuras que se encuentran en las bases de datos, especialmente con el concepto de **tipos de datos enumerados** (por ejemplo, el tipo `ENUM` en bases de datos SQL). A continuación, se detallan las similitudes clave:

## 1. Restricción de valores

- **En Java**: Un `enum` restringe los valores a los definidos en la declaración del enumerado. Por ejemplo, si declaras un `enum` para los días de la semana, solo puedes usar los días especificados (lunes, martes, etc.).

- **En bases de datos**: Un campo con tipo `ENUM` en una base de datos también restringe los valores a un conjunto específico. Por ejemplo, puedes definir un campo `estado` en una tabla con los valores posibles `ACTIVO`, `INACTIVO`, y `PENDIENTE`.

## 2. Representación de conjuntos de valores fijos

- **En Java**: Un `enum` es útil cuando se tiene un conjunto fijo de constantes que no cambian, como los días de la semana, estados de un proceso, etc.

- **En bases de datos**: El tipo `ENUM` es utilizado para almacenar valores que pertenecen a un conjunto finito de opciones. Esto asegura que los datos en la base de datos sean válidos y consistentes, evitando valores erróneos.

## 3. Mejora en la legibilidad y mantenimiento

- **En Java**: Los `enum` mejoran la legibilidad del código, al darle nombres significativos a los valores constantes en lugar de usar números mágicos o cadenas.
  
- **En bases de datos**: Utilizar un tipo `ENUM` en una base de datos también mejora la legibilidad y mantenimiento, ya que los valores están claramente definidos y documentados.

## 4. Representación numérica

- **En Java**: Aunque los valores de un `enum` son representados como identificadores simbólicos, internamente, Java puede asignarles valores numéricos.

- **En bases de datos**: Los valores en un campo `ENUM` son internamente representados como índices numéricos, lo que optimiza el almacenamiento y la comparación.

```sql
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    estado ENUM('ACTIVO', 'INACTIVO', 'PENDIENTE') NOT NULL
);
```

</div>