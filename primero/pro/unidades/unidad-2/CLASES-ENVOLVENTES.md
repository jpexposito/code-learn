<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Clases envolventes)

# Clases Envolventes en Java

En Java, las **clases envolventes** (*wrapper classes*) proporcionan una representación orientada a objetos de los tipos de datos primitivos. Cada tipo primitivo tiene una clase envolvente correspondiente, lo que permite tratarlos como objetos cuando es necesario (por ejemplo, en colecciones).

| Tipo Primitivo | Clase Envolvente |
| -------------- | ---------------- |
| `byte`         | `Byte`           |
| `short`        | `Short`          |
| `int`          | `Integer`        |
| `long`         | `Long`           |
| `float`        | `Float`          |
| `double`       | `Double`         |
| `char`         | `Character`      |
| `boolean`      | `Boolean`        |

## Principales Características

1. **Autoboxing y Unboxing**: Conversión automática entre tipos primitivos y clases envolventes.
   - *Autoboxing*: Convertir un tipo primitivo a su correspondiente clase envolvente.
   - *Unboxing*: Convertir una clase envolvente a su tipo primitivo.
2. **Métodos útiles**: Las clases envolventes proporcionan ___métodos útiles para convertir, comparar y manipular valores___.

## Métodos Relevantes

A continuación se describen algunos de los métodos más relevantes de cada clase envolvente:

### `Byte`

- **`byteValue()`**: Devuelve el valor primitivo `byte` del objeto `Byte`.
- **`compareTo(Byte anotherByte)`**: Compara dos objetos `Byte`.
- **`parseByte(String s)`**: Convierte una cadena en un valor primitivo `byte`.

### `Short`

- **`shortValue()`**: Devuelve el valor primitivo `short` del objeto `Short`.
- **`compareTo(Short anotherShort)`**: Compara dos objetos `Short`.
- **`parseShort(String s)`**: Convierte una cadena en un valor primitivo `short`.

### `Integer`

- **`intValue()`**: Devuelve el valor primitivo `int` del objeto `Integer`.
- **`compareTo(Integer anotherInteger)`**: Compara dos objetos `Integer`.
- **`parseInt(String s)`**: Convierte una cadena en un valor primitivo `int`.
- **`valueOf(String s)`**: Convierte una cadena en un objeto `Integer`.

### `Long`

- **`longValue()`**: Devuelve el valor primitivo `long` del objeto `Long`.
- **`compareTo(Long anotherLong)`**: Compara dos objetos `Long`.
- **`parseLong(String s)`**: Convierte una cadena en un valor primitivo `long`.
- **`valueOf(String s)`**: Convierte una cadena en un objeto `Long`.

### `Float`

- **`floatValue()`**: Devuelve el valor primitivo `float` del objeto `Float`.
- **`compareTo(Float anotherFloat)`**: Compara dos objetos `Float`.
- **`parseFloat(String s)`**: Convierte una cadena en un valor primitivo `float`.

### `Double`

- **`doubleValue()`**: Devuelve el valor primitivo `double` del objeto `Double`.
- **`compareTo(Double anotherDouble)`**: Compara dos objetos `Double`.
- **`parseDouble(String s)`**: Convierte una cadena en un valor primitivo `double`.
- **`valueOf(String s)`**: Convierte una cadena en un objeto `Double`.

### `Character`

- **`charValue()`**: Devuelve el valor primitivo `char` del objeto `Character`.
- **`compareTo(Character anotherCharacter)`**: Compara dos objetos `Character`.
- **`isLetter(char ch)`**: Comprueba si el carácter es una letra.
- **`isDigit(char ch)`**: Comprueba si el carácter es un dígito.

### `Boolean`

- **`booleanValue()`**: Devuelve el valor primitivo `boolean` del objeto `Boolean`.
- **`compareTo(Boolean anotherBoolean)`**: Compara dos objetos `Boolean`.
- **`parseBoolean(String s)`**: Convierte una cadena en un valor primitivo `boolean`.

## Ejemplo de Uso

```java
// Ejemplo con Integer
Integer numObj = 10; // Autoboxing
int numPrim = numObj; // Unboxing

// Uso de métodos
String str = "123";
int parsedInt = Integer.parseInt(str); // Convierte cadena en int
System.out.println("Parsed int: " + parsedInt);

// Comparar objetos Integer
Integer num1 = 100;
Integer num2 = 200;
int comparison = num1.compareTo(num2); // Resultado será negativo, ya que 100 < 200
System.out.println("Comparison result: " + comparison);
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles

</div>