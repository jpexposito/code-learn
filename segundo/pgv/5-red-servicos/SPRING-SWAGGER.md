<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios (Documentación de la API con Swagger)

<div align="center">
   <img src=images/swagger.png width="400">
</div>

Vamos a ver cómo **configurar la documentación de controladores con Swagger (OpenAPI 3)** en una aplicación **Spring Boot** usando la librería **springdoc-openapi**.

---

## 1. Requisitos

- Java 17+ (recomendado para Spring Boot 3.x)
- Maven o Gradle
- Proyecto Spring Boot (con `spring-boot-starter-web` o `spring-boot-starter-webflux`)

> En los ejemplos se asume **Spring Boot 3.x** con Spring MVC (`spring-boot-starter-web`).

---

## 2. Añadir dependencias

### 2.1. Maven

En tu `pom.xml` añade:

```xml
<dependencies>
    <!-- Otras dependencias -->

    <!-- OpenAPI / Swagger UI para Spring MVC -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.8.14</version> <!-- o la última versión estable -->
    </dependency>
</dependencies>
```

### 2.2. Gradle

En tu `build.gradle`:

```gradle
dependencies {
    // Otras dependencias

    // OpenAPI / Swagger UI para Spring MVC
    implementation "org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.14"
}
```

Si utilizas **WebFlux** en lugar de MVC, usa el artefacto:

```xml
<artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
```

---

## 3. Endpoints que expone springdoc-openapi

Con la dependencia añadida, se habilitan automáticamente:

- Documentación OpenAPI en JSON: `http://localhost:8080/v3/api-docs`
- Documentación OpenAPI en YAML: `http://localhost:8080/v3/api-docs.yaml`
- Swagger UI:  
  - `http://localhost:8080/swagger-ui.html`  
  - o `http://localhost:8080/swagger-ui/index.html`

*(puerto y contexto dependen de tu configuración de Spring Boot)*

---

## 4. Configuración opcional en `application.yml`

Puedes personalizar las rutas de la documentación y de Swagger UI en `application.yml`:

```yaml
springdoc:
  api-docs:
    path: /api-docs         # en lugar de /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html  # URL de acceso a la UI
```

Otras propiedades útiles:

```yaml
springdoc:
  swagger-ui:
    operationsSorter: alpha   # ordena operaciones alfabéticamente
    tagsSorter: alpha         # ordena tags alfabéticamente
    doc-expansion: none       # colapsa los bloques por defecto
```

---

## 5. Anotaciones en los controladores

Para que la documentación sea clara y legible, anota tus controladores con las anotaciones de OpenAPI.

### 5.1. Ejemplo de controlador

```java
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones sobre productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(
        summary = "Obtiene un producto por id",
        description = "Devuelve el producto completo (parte SQL + detalle NoSQL)"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Producto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no existe",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(
            @Parameter(description = "Id del producto", example = "42")
            @PathVariable Long id) {

        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crea un nuevo producto",
        description = "Crea un producto con sus datos duros y detalle NoSQL asociado"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Producto creado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Producto.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<Producto> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del producto a crear",
                required = true,
                content = @Content(schema = @Schema(implementation = Producto.class))
            )
            @RequestBody Producto producto) {

        Producto creado = productoService.save(producto);
        return ResponseEntity.status(201).body(creado);
    }
}
```

Puntos clave:

- `@Tag` agrupa los endpoints del controlador en Swagger UI.
- `@Operation` describe cada endpoint (resumen, descripción).
- `@ApiResponses` / `@ApiResponse` documentan códigos de respuesta posibles.
- `@Parameter` documenta parámetros (`@PathVariable`, `@RequestParam`, etc.).
- `@RequestBody` (específico de OpenAPI) documenta el cuerpo de la petición.

---

## 6. Configuración global de la API

Para definir el título, descripción y versión de la API, crea una clase de configuración con `@OpenAPIDefinition`:

```java
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Productos",
        version = "v1",
        description = "API para gestionar un catálogo de productos con parte relacional y parte NoSQL",
        contact = @Contact(
            name = "Equipo de Desarrollo",
            email = "equipo@example.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Servidor local")
    }
)
public class OpenApiConfig {
}
```

Esta configuración aparecerá en la cabecera y metadatos de Swagger UI.

---

## 7. Probar la documentación

1. Levanta la aplicación Spring Boot:

   ```bash
   mvn spring-boot:run
   # o
   ./mvnw spring-boot:run
   ```

2. Abre en tu navegador:

   - `http://localhost:8080/swagger-ui.html`  
   (o la ruta que hayas configurado en `springdoc.swagger-ui.path`).

3. Deberías ver el listado de endpoints agrupados por **tags**, con la posibilidad de:
   - Ver la documentación de cada operación.
   - Probar las peticiones (`Try it out`).
   - Ver el JSON/YAML de la especificación OpenAPI.

---

## 8. Resumen

- Añade la dependencia `springdoc-openapi-starter-webmvc-ui` al proyecto.
- (Opcional) Configura rutas personalizadas en `application.yml`.
- Anota tus controladores con `@Tag`, `@Operation`, `@ApiResponses`, `@Parameter`, etc.
- Crea una clase `OpenApiConfig` con `@OpenAPIDefinition` para definir metadatos de la API.
- Accede a Swagger UI para ver y probar tu API de forma interactiva.

Con esto tendrás la documentación de tus controladores generada automáticamente y visible en Swagger UI.

</div>

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>