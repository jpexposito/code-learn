<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn. Dando formato a las fechas(Datetimeformatter)

## Descripción

`DateTimeFormatter` es una clase utilizada para formatear y analizar objetos de fecha y hora en las clases del paquete `java.time` (como `LocalDate`, `LocalTime`, `LocalDateTime`, etc.). Es segura para hilos y ofrece una API moderna y flexible en comparación con clases como `SimpleDateFormat`.

```java
 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(formatter);
        System.out.println("Fecha formateada: " + formattedDate);
```

```java
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.of(2025, 1, 17);
        String formattedDate = date.format(formatter);
        System.out.println("Fecha formateada: " + formattedDate);
```

---

## Métodos principales

### 1. `ofPattern(String pattern)`

Crea un `DateTimeFormatter` con un patrón de formato personalizado.

- **Parámetros:**  
  `pattern` (`String`): Cadena que define el formato deseado.

- **Retorno:**  
  Un objeto `DateTimeFormatter` configurado con el patrón especificado.

---

### 2. `format(TemporalAccessor temporal)`

Convierte un objeto de fecha y hora en una cadena formateada.

- **Parámetros:**  
  `temporal` (`TemporalAccessor`): Objeto que representa la fecha/hora a formatear.

- **Retorno:**  
  Una cadena (`String`) con la fecha/hora formateada.

---

### 3. `parse(CharSequence text)`

Convierte una cadena en un objeto de fecha y hora.

- **Parámetros:**  
  `text` (`CharSequence`): Cadena que representa la fecha/hora.

- **Retorno:**  
  Un objeto `TemporalAccessor` (como `LocalDate`, `LocalDateTime`, etc.).

- **Excepciones:**  
  Lanza `DateTimeParseException` si la cadena no coincide con el patrón definido.

```java
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString = "17-01-2025";
        LocalDate date = LocalDate.parse(dateString, formatter);
        System.out.println("Fecha analizada: " + date);
```

---

## Formatos predefinidos

`DateTimeFormatter` incluye constantes con formatos estándar:

| Constante                     | Descripción                            | Ejemplo               |
|-------------------------------|----------------------------------------|-----------------------|
| `ISO_LOCAL_DATE`              | Fecha en formato ISO-8601              | `2025-01-17`          |
| `ISO_LOCAL_TIME`              | Hora en formato ISO-8601               | `14:30:45`            |
| `ISO_LOCAL_DATE_TIME`         | Fecha y hora en formato ISO-8601       | `2025-01-17T14:30:45` |
| `BASIC_ISO_DATE`              | Fecha básica ISO-8601                  | `20250117`            |
| `ISO_ZONED_DATE_TIME`         | Fecha y hora con zona horaria          | `2025-01-17T14:30:45+01:00[Europe/Madrid]` |
| `RFC_1123_DATE_TIME`          | Fecha y hora según el estándar RFC 1123| `Fri, 17 Jan 2025 14:30:45 GMT` |

---

## Patrones de formato personalizados

`DateTimeFormatter` soporta una variedad de patrones para personalizar la salida y entrada de fechas y horas:

| Símbolo   | Significado                   | Ejemplo               |
|-----------|-------------------------------|-----------------------|
| `y`       | Año                          | `2025`                |
| `M`       | Mes (número o texto)         | `01`, `January`       |
| `d`       | Día del mes                  | `1`, `31`             |
| `E`       | Día de la semana             | `Fri`, `Friday`       |
| `H`       | Hora (24 horas)              | `0`, `23`             |
| `h`       | Hora (12 horas)              | `1`, `12`             |
| `m`       | Minutos                      | `0`, `59`             |
| `s`       | Segundos                     | `0`, `59`             |
| `a`       | AM/PM                        | `AM`, `PM`            |
| `z`       | Zona horaria                 | `UTC`, `GMT+1`        |
| `'texto'` | Texto literal entre comillas | `'at'` → `at`         |

---

## Consideraciones

1. **Seguridad para hilos:**  
   `DateTimeFormatter` es seguro para hilos, lo que lo hace adecuado para aplicaciones concurrentes.

2. **Formato estricto:**  
   El patrón debe coincidir exactamente con el formato de entrada o salida deseado.

3. **Configuración regional:**  
   Soporta diferentes configuraciones regionales para la representación de fechas y horas.

---

## Formatos comunes recomendados

- **Fecha (YYYY-MM-DD):** `yyyy-MM-dd`  
- **Fecha y hora (ISO-8601):** `yyyy-MM-dd'T'HH:mm:ss`  
- **Hora (HH:MM:SS):** `HH:mm:ss`  
- **Fecha en texto (día, mes, año):** `EEEE, MMMM d, yyyy`

---

## Alternativas

En aplicaciones modernas, `DateTimeFormatter` es la opción recomendada frente a `SimpleDateFormat`, ya que es más robusto, seguro y compatible con las clases de la API `java.time`.

---
</div>