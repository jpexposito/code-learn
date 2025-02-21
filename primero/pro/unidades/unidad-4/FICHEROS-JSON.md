<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Ficheros JSON)

## Trabajando con Archivos XML

### Introducción

JSON (JavaScript Object Notation) es un formato ligero de intercambio de datos, basado en texto y fácil de leer y escribir tanto para humanos como para máquinas. Se utiliza comúnmente para la transmisión de datos entre un servidor y una aplicación web.

## 🏗 **Elementos Principales de un Archivo JSON**

Para identificar correctamente la estructura de un fichero JSON, es importante reconocer los siguientes caracteres:

- `{}`: Define un objeto.
- `[]`: Define un array.
- `:`: Separa claves y valores dentro de un objeto.
- `,`: Separa elementos dentro de un objeto o array.
- `""`: Define una cadena de texto.

## Tipos de Datos en JSON

JSON admite los siguientes tipos de datos:

- **Objetos**: Conjunto de pares clave-valor encerrados entre `{}`.
- **Arrays**: Lista ordenada de valores encerrados entre `[]`.
- **Cadenas de texto (Strings)**: Se representan con comillas dobles `""`.
- **Números**: Pueden ser enteros o decimales.
- **Booleanos**: `true` o `false`.
- **Nulo**: `null` para representar valores nulos o vacíos.

## Ejemplos de JSON

### Ejemplo de Objeto JSON

```json
{
  "nombre": "Juan",
  "edad": 30,
  "casado": false,
  "direccion": {
    "calle": "Av. Siempre Viva",
    "ciudad": "Springfield"
  }
}
```

### Ejemplo de Array JSON

```json
[
  {
    "id": 1,
    "nombre": "Manzana",
    "precio": 0.5
  },
  {
    "id": 2,
    "nombre": "Banana",
    "precio": 0.3
  }
]
```

### Ejemplo de JSON con Diferentes Tipos de Datos

```json
{
  "titulo": "Ejemplo JSON",
  "version": 1.1,
  "disponible": true,
  "categorias": ["programación", "JSON", "Dam"],
  "autor": null
}
```

## Cosas a tener muy en cuenta

- JSON es **sensible** a las comillas dobles (`""`) para definir claves y valores tipo string.
- **No permite comentarios dentro de los ficheros**.
- La última propiedad de un objeto **NO** debe terminar en coma **`,`**.
- JSON **es independiente** del **lenguaje de programación**, pero ampliamente soportado en la mayoría de ellos.

## Trabajando con Ficheros Json desde Java

### Librerías para trabajar con Json y Java

Existen múltiples librerias para trabajar con json en java. Utilizaremos, la que probablemente es la más potente a la hora de crear/leer/modificar y transformar Objetos Json. Para ello debemos de incorporar la siguiente librería dentro de nuestro proyecto`pom.xm`:

```xml
<!-- Dentro de la etiqueta <dependencies> si existe y si no crearla hay que incluir-->

<dependencies>
    <!-- Jackson Core para procesamiento JSON -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.15.0</version>
    </dependency>
</dependencies>
```

### Clase Car

```java
public class Car {
    private int id;
    private String marca;
    private String modelo;
    private double precio;
    <!-- getters and setters ... -->
```

### 📌 Clase ObjectMapper  

`ObjectMapper` es la clase principal de la biblioteca **Jackson**, utilizada para la conversión entre objetos **Java** y **JSON**. Esta clase permite serializar objetos Java a JSON y deserializar JSON a objetos Java.  

Se encuentra en el paquete:  

`com.fasterxml.jackson.databind`

---

## 🔹 Características Principales  

- **Serialización**: Convierte objetos Java a JSON.  
- **Deserialización**: Convierte JSON a objetos Java.  
- **Soporte para Colecciones**: Permite convertir JSON en listas, mapas y otros tipos de colecciones.  
- **Manejo de Fechas**: Compatible con `LocalDate`, `LocalDateTime` y otros tipos de fecha/hora.  
- **Personalización**: Soporta configuraciones avanzadas mediante anotaciones y opciones de mapeo.  

---

### 🔹 Principales Métodos de `ObjectMapper`  

| Método | Descripción |
|--------|------------|
| `writeValueAsString(obj)` | Convierte un objeto Java en un **JSON String**. |
| `writeValue(File, obj)` | Guarda un objeto Java en un archivo JSON. |
| `readValue(String, Clase.class)` | Convierte un **JSON String** en un objeto Java. |
| `readValue(File, Clase.class)` | Lee un archivo JSON y lo convierte en un objeto Java. |
| `readValue(String, new TypeReference<List<T>>(){})` | Convierte un JSON en una **Lista de objetos**. |
| `configure(Feature, boolean)` | Configura opciones avanzadas de `ObjectMapper`. |

### 📌 Principales Anotaciones en Jackson

Jackson proporciona varias anotaciones para personalizar la serialización y deserialización de objetos Java a JSON y viceversa.

---

#### 🔹 `@JsonProperty`

Permite definir un nombre personalizado para la propiedad en el JSON.

---

#### 🔹 `@JsonIgnore`

Indica que un atributo no debe ser incluido en la serialización o deserialización.

---

#### 🔹 `@JsonIgnoreProperties`

Se usa a nivel de clase para ignorar múltiples propiedades en la deserialización.

---

#### 🔹 `@JsonInclude`

Controla la inclusión de valores `null` u otros valores predeterminados en la serialización.

---

#### 🔹 `@JsonFormat`

Define el formato de fechas y otros valores durante la serialización y deserialización.

---

#### 🔹 `@JsonCreator`

Se usa para indicar un constructor o método de fábrica que debe usarse para crear instancias de la clase durante la deserialización.

---

#### 🔹 `@JsonAnySetter`

Permite capturar propiedades desconocidas en un `Map<String, Object>`.

---

#### 🔹 `@JsonAnyGetter`

Indica que un método proporciona propiedades dinámicas adicionales en la serialización.

---

#### 🔹 `@JsonSetter`

Define un método específico para asignar valores durante la deserialización.

---

#### 🔹 `@JsonAlias`

Permite definir múltiples nombres alternativos para una propiedad en la deserialización.

---

### Cosas a tener en cuenta cuando trabajamos con la librería

#### Ignorar null en la serialización

```java
objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

Car car = new Car(1, "Toyota", "Corolla", null);
String json = objectMapper.writeValueAsString(car);
System.out.println(json);

<!-- salida -->
{"id":1,"marca":"Toyota","modelo":"Corolla"}
```

#### Ignorar desconocidos en la serialización

También se pueden ignorar las propiedades desconocidas.

```java
objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
<!-- o anotando la clase -->
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Car { ... }
```

## Referencias

- [www.baeldung.com](https://www.baeldung.com/jackson-object-mapper-tutorial)

</div>