<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Manipulación de ficeros con Jackson (xml -Json))

Esta guía resume cómo **anotar modelos (POJOs)** para controlar **serialización** y **deserialización** en **JSON** y **XML** con Jackson, y cómo integrarlo en **Spring Boot**. Incluye ejemplos, tablas rápidas y buenas prácticas.

> Spring Boot autoconfigura `ObjectMapper` (JSON) y, si detecta `jackson-dataformat-xml`, registra también un `XmlMapper`. El **`Content-Type`** / **`Accept`** decide si se devuelve JSON o XML.

---

## 🧩 Bean anotado para **JSON y XML**

```java
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.*;
import java.time.OffsetDateTime;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)                     // ignora props desconocidas
@JsonInclude(JsonInclude.Include.NON_NULL)                      // no incluir nulls
@JsonPropertyOrder({ "id", "full_name", "email", "createdAt", "phones", "extras" })
@JsonRootName("persona")                                        // raíz JSON (si WRAP_ROOT_VALUE está activo)
@JacksonXmlRootElement(localName = "persona")                   // raíz XML
public class Person {

  @JacksonXmlProperty(isAttribute = true)                       // atributo XML
  private String id;

  @JsonProperty("full_name")                                    // nombre en JSON
  @JacksonXmlProperty(localName = "full-name")                  // nombre en XML
  private String name;

  @JsonAlias({ "correo", "mail" })                              // alias de entrada
  private String email;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmXXX")                // formato JSON
  @JacksonXmlProperty(localName = "created-at")                 // nombre en XML
  private OffsetDateTime createdAt;

  @JacksonXmlElementWrapper(localName = "phones", useWrapping = true) // <phones><phone>..</phone></phones>
  @JacksonXmlProperty(localName = "phone")
  private List<String> phones = new ArrayList<>();

  @JsonIgnore                                                    // no exponer
  private String internalToken;

  private Map<String, Object> extras = new HashMap<>();

  @JsonAnyGetter                                                 // props dinámicas -> JSON/XML
  public Map<String, Object> getExtras() { return extras; }

  @JsonAnySetter                                                 // capturar props desconocidas
  public void setExtra(String key, Object value) { extras.put(key, value); }

  // getters/setters/constructores...
}
```

**Salida JSON (ejemplo)**

```json
{
  "id": "p-1",
  "full_name": "Ada Lovelace",
  "email": "ada@example.com",
  "createdAt": "2024-12-01T10:30+01:00",
  "phones": ["+34-600-000-000"],
  "nick": "ada"
}
```

**Salida XML (ejemplo)**

```xml
<persona id="p-1">
  <full-name>Ada Lovelace</full-name>
  <email>ada@example.com</email>
  <created-at>2024-12-01T10:30+01:00</created-at>
  <phones>
    <phone>+34-600-000-000</phone>
  </phones>
  <nick>ada</nick>
</persona>
```

---

## 🧭 Tabla rápida de anotaciones **JSON** (Jackson Core)

| Anotación | Propósito |
|---|---|
| `@JsonProperty("nombre")` | Renombrar propiedad (lectura/escritura). |
| `@JsonAlias({"a","b"})` | Aceptar **nombres alternativos** al deserializar. |
| `@JsonIgnore` / `@JsonIgnoreProperties` | Excluir campos / ignorar props desconocidas. |
| `@JsonInclude(Include.NON_NULL/EMPTY)` | Excluir null/vacíos en la salida. |
| `@JsonPropertyOrder({...})` | Orden de propiedades (estético). |
| `@JsonFormat(pattern = "...")` | Formateo de fechas/números. |
| `@JsonCreator` + `@JsonProperty` | Deserialización vía constructor/factory. |
| `@JsonValue` | Serializar objeto/enum como valor simple. |
| `@JsonAnyGetter` / `@JsonAnySetter` | Propiedades dinámicas (mapa). |
| `@JsonView(Views.X.class)` | Vistas (controlar qué campos se exponen por contexto). |
| `@JsonSerialize` / `@JsonDeserialize` | Serializadores/deserializadores personalizados. |
| `@JsonNaming(SnakeCaseStrategy.class)` | Convención de nombres a nivel de clase. |
| `@JsonRootName("root")` | Nombre de raíz JSON (si está activo WRAP_ROOT_VALUE). |

---

## 🧭 Tabla rápida de anotaciones **XML** (jackson-dataformat-xml)

| Anotación | Propósito |
|---|---|
| `@JacksonXmlRootElement(localName = "raiz")` | Cambiar el elemento raíz. |
| `@JacksonXmlProperty(localName="x", isAttribute=true)` | Definir nombre y si es **atributo**. |
| `@JacksonXmlElementWrapper(localName="items", useWrapping=true)` | Envoltorio para **listas**. |
| `@JacksonXmlText` | Marcar el **contenido de texto** del elemento. |
| `@JacksonXmlCData` | Envolver el contenido en **CDATA**. |
| `@JacksonXmlProperty(namespace="...")` | Namespace por elemento/atributo. |

---

## 🧪 Polimorfismo (interfaces / clases base)

```java
import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = Rectangle.class, name = "rect"),
  @JsonSubTypes.Type(value = Circle.class,    name = "circ")
})
abstract class Shape { public String color; }
class Rectangle extends Shape { public int w, h; }
class Circle extends Shape { public int r; }
```

**JSON**

```json
{ "type":"rect", "color":"red", "w":10, "h":20 }
```

> También funciona con XML: la propiedad `type` se serializa como elemento (o atributo si lo anotas así).

---

## ⚙️ Configuración útil en **Spring Boot**

```java
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JacksonConfig {

  @Bean
  ObjectMapper objectMapper() {
    return JsonMapper.builder()
        .findAndRegisterModules()
        // .enable(SerializationFeature.WRAP_ROOT_VALUE) // para @JsonRootName
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .build();
  }

  @Bean
  XmlMapper xmlMapper() {
    return XmlMapper.builder()
        .findAndRegisterModules()
        .build();
  }
}
```

**Content negotiation en un controlador:**

```java
@RestController
@RequestMapping("/api/persons")
class PersonController {
  @PostMapping(consumes = {"application/json","application/xml"},
               produces = {"application/json","application/xml"})
  public Person upsert(@RequestBody Person p) {
    return p;
  }
}
```

---

## 🔤 Enums y valores simples

```java
enum Status {
  @JsonProperty("ok") OK,
  @JsonProperty("err") ERROR
}

class Wrapper {
  @JsonValue // serializa el objeto directamente como string/plano
  public String asString() { return "value"; }
}
```

---

## 🧩 Serializadores y deserializadores personalizados

```java
class MoneySerializer extends com.fasterxml.jackson.databind.JsonSerializer<java.math.BigDecimal> {
  @Override
  public void serialize(java.math.BigDecimal value, com.fasterxml.jackson.core.JsonGenerator gen,
                        com.fasterxml.jackson.databind.SerializerProvider serializers) throws IOException {
    gen.writeString(value.setScale(2, java.math.RoundingMode.HALF_UP).toString());
  }
}

class Product {
  @JsonSerialize(using = MoneySerializer.class)
  private java.math.BigDecimal price;
}
```

---

## 🔁 Conversión **JSON ⇄ XML** con el mismo modelo

```java
ObjectMapper json = new ObjectMapper().findAndRegisterModules();
XmlMapper xml = new XmlMapper().findAndRegisterModules();

Person p = json.readValue(jsonString, Person.class);
String xmlOut = xml.writeValueAsString(p);

Person p2 = xml.readValue(xmlOut, Person.class);
String jsonOut = json.writeValueAsString(p2);
```

---

## ✅ Buenas prácticas

- Anota **getters/setters** cuando haya lógica derivada; anota **campos** si buscas control directo.
- Usa `@JsonInclude(Include.NON_EMPTY)` para reducir ruido en salidas.
- Controla **wrappers** y **nombres** en XML con `@JacksonXmlElementWrapper`/`@JacksonXmlProperty`.
- Para **fechas**, usa `@JsonFormat` y desactiva `WRITE_DATES_AS_TIMESTAMPS`.
- En **polimorfismo**, fija names estables en `@JsonSubTypes` para no romper clientes.
- Si necesitas **root JSON**, activa `WRAP_ROOT_VALUE` además de `@JsonRootName`.
- Valida entrada con `jakarta.validation` y maneja errores con `@ControllerAdvice` + `ProblemDetail`.

</div>