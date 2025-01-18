<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn. Dando formato a las fechas(SimpleDateFormat)

## Descripción

`SimpleDateFormat` es una clase utilizada para formatear y analizar fechas y horas. Permite convertir fechas en cadenas de texto con un formato específico y viceversa.

```java
```java
import java.text.SimpleDateFormat;
```

---

## Constructores

### 1. Constructor sin parámetros

Crea un objeto con un formato de fecha y hora predeterminado según la configuración regional del sistema.

```java
SimpleDateFormat sdf = new SimpleDateFormat();
Date date = new Date();
System.out.println(sdf.format(date)); // Salida: 17/01/25 14:30

```

### 2. Constructor con formato

Crea un objeto con un formato específico definido por una cadena que describe el patrón deseado.

```java
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
Date date = new Date();
System.out.println(sdf.format(date)); // Salida: 2025-01-17
```

### 3. Constructor con formato y configuración regional

Crea un objeto con un formato específico y ajustado a una configuración regional específica.

```java
import java.util.Locale;

SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.FRENCH);
Date date = new Date();
System.out.println(sdf.format(date)); // Salida: vendredi, janvier 17, 2025
```

---

## Métodos principales

### Formateo

Convierte una fecha en una cadena de texto siguiendo el formato especificado.

```java
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Obtener la fecha actual
        Date now = new Date();

        // Formatear la fecha actual
        String formattedDate = sdf.format(now);

        // Imprimir la fecha formateada
        System.out.println("Fecha formateada: " + formattedDate);
```

### Análisis

Convierte una cadena de texto en una fecha, verificando que cumpla el formato definido.

```java
// Crear un objeto SimpleDateFormat con el formato esperado
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Cadena de texto que representa una fecha
        String dateString = "2025-01-17";

        try {
            // Analizar la cadena y convertirla en un objeto Date
            Date parsedDate = sdf.parse(dateString);

            // Imprimir el objeto Date resultante
            System.out.println("Fecha analizada: " + parsedDate);
        } catch (Exception e) {
            // Manejar excepciones de análisis
            System.out.println("Error al analizar la fecha: " + e.getMessage());
        }
```

---

## Patrones de Formato

| Símbolo | Significado                   |
|---------|-------------------------------|
| `y`     | Año                          |
| `M`     | Mes (número o texto)         |
| `d`     | Día del mes                  |
| `H`     | Hora (24 horas)              |
| `h`     | Hora (12 horas)              |
| `m`     | Minutos                      |
| `s`     | Segundos                     |
| `a`     | AM/PM                        |
| `E`     | Día de la semana             |
| `z`     | Zona horaria                 |

---

## Consideraciones

- **No es seguro para hilos:** No puede utilizarse en entornos concurrentes sin protección adicional.
- **Compatibilidad regional:** La representación de texto depende de la configuración regional seleccionada.

---

## Otras Alternativas

Se recomienda usar `DateTimeFormatter` en lugar de `SimpleDateFormat` en aplicaciones modernas debido a su diseño seguro para hilos y mayor flexibilidad.

</div>