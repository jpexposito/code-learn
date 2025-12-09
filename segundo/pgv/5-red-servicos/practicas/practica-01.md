<div align="justify">

# <img src=.../../../../../../images/coding-book.png width="40"> Code & Learn (Práctica 1: Proyecto Tareas)

## 1. Objetivo del ejercicio

Construir un **servicio de gestión de tareas** que expone la misma lógica de negocio a través de:

- Una **API REST** (JSON) bajo `/api/tareas`.
- Un **servicio SOAP** (XML, WSDL) usando **Apache CXF 4.1.4** bajo `/services/tareas`.

Todo debe estar organizado en el paquete:

- `com.docencia.tareas`

Y el `targetNamespace` del servicio SOAP será:

- `http://tareas.ies.puerto.es/`

El modelo de datos básico es:

```json
{
  "tareas": [
    { "id": 1, "titulo": "Estudiar TypeScript", "descripcion": "Repasar tipos y funciones", "completada": false },
    { "id": 2, "titulo": "Hacer la práctica 1", "descripcion": "Proyecto tareas en memoria", "completada": true }
  ]
}
```

Descarga el proyecto base [spring-tareas-rest-soap.zip](files/spring-tareas-rest-soap.zip), como proyecto base.

---

## 2. Preparación del proyecto (pom.xml y configuración básica)

### 2.1. Crear proyecto Spring Boot

1. Crear un proyecto Maven Spring Boot (por ejemplo, con Spring Initializr o manualmente).
2. Usar:
   - **GroupId**: `com.docencia`
   - **ArtifactId**: `tareas-rest-soap-mapstruct`
   - Java 17
   - Dependencia inicial: `spring-boot-starter-web`

### 2.2. Añadir dependencias para REST, SOAP, Swagger y MapStruct

En el `pom.xml`, añadir las propiedades y dependencias:

```xml
<properties>
    <java.version>17</java.version>
    <springdoc.version>2.6.0</springdoc.version>
    <cxf.version>4.1.4</cxf.version>
    <mapstruct.version>1.5.5.Final</mapstruct.version>
</properties>

<dependencies>
    <!-- Web REST -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- OpenAPI / Swagger UI -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>${springdoc.version}</version>
    </dependency>

    <!-- Apache CXF: SOAP / JAX-WS con Spring Boot -->
    <dependency>
        <groupId>org.apache.cxf</groupId>
        <artifactId>cxf-spring-boot-starter-jaxws</artifactId>
        <version>${cxf.version}</version>
    </dependency>

    <!-- Opcional: logging de mensajes SOAP -->
    <dependency>
        <groupId>org.apache.cxf</groupId>
        <artifactId>cxf-rt-features-logging</artifactId>
        <version>${cxf.version}</version>
    </dependency>

    <!-- MapStruct -->
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>${mapstruct.version}</version>
    </dependency>

    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct-processor</artifactId>
        <version>${mapstruct.version}</version>
        <scope>provided</scope>
    </dependency>

    <!-- Tests -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

Configurar los plugins (especialmente para MapStruct):

```xml
<build>
    <plugins>
        <!-- Compilador Java con annotation processors (MapStruct) -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.13.0</version>
            <configuration>
                <source>${java.version}</source>
                <target>${java.version}</target>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>${mapstruct.version}</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>

        <!-- Spring Boot plugin -->
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

### 2.3. Configuración en `application.properties`

En `src/main/resources/application.properties`:

```properties
server.port=8080
cxf.path=/services
```

Con esto, CXF publicará los servicios SOAP bajo `/services/...`.

---

## 3. Construcción de las capas (dominio, servicio, REST, SOAP)

### 3.1. Capa de dominio (model)

Paquete: `com.docencia.tareas.model`

1. **Clase `Tarea`**

   ```java
   package com.docencia.tareas.model;

   import jakarta.xml.bind.annotation.XmlAccessType;
   import jakarta.xml.bind.annotation.XmlAccessorType;
   import jakarta.xml.bind.annotation.XmlRootElement;

   @XmlRootElement(name = "tarea")
   @XmlAccessorType(XmlAccessType.FIELD)
   public class Tarea {

       private Long id;
       private String titulo;
       private String descripcion;
       private boolean completada;

       public Tarea() {
       }

       public Tarea(Long id, String titulo, String descripcion, boolean completada) {
           this.id = id;
           this.titulo = titulo;
           this.descripcion = descripcion;
           this.completada = completada;
       }

       // getters y setters...
   }
   ```

2. **Wrapper `TareasResponse`** (para REST):

   ```java
   package com.docencia.tareas.model;

   import java.util.List;

   public class TareasResponse {

       private List<Tarea> tareas;

       public TareasResponse() {
       }

       public TareasResponse(List<Tarea> tareas) {
           this.tareas = tareas;
       }

       public List<Tarea> getTareas() {
           return tareas;
       }

       public void setTareas(List<Tarea> tareas) {
           this.tareas = tareas;
       }
   }
   ```

---

### 3.2. Capa de servicio (lógica de negocio)

Paquete: `com.docencia.tareas.service`

1. **Interfaz `TareaService`**

   ```java
   package com.docencia.tareas.service;

   import com.docencia.tareas.model.Tarea;
   import java.util.List;

   public interface TareaService {

       List<Tarea> listarTodas();

       Tarea buscarPorId(Long id);

       Tarea crearTarea(String titulo, String descripcion);

       Tarea actualizarTarea(Long id, String titulo, String descripcion, Boolean completada);

       boolean eliminarTarea(Long id);
   }
   ```

2. **Implementación en memoria `InMemoryTareaService`**

   Pasos:

   - Declarar un `Map<Long, Tarea>` para guardar las tareas.
   - Usar un `AtomicLong` como secuencia de IDs.
   - Inicializar con las 2 tareas del enunciado.
   - Implementar los métodos de CRUD.

   Esquema:

   ```java
   @Service
   public class InMemoryTareaService implements TareaService {

       private final Map<Long, Tarea> datos = new LinkedHashMap<>();
       private final AtomicLong secuencia = new AtomicLong(0);

       public InMemoryTareaService() {
           Tarea t1 = new Tarea(
               secuencia.incrementAndGet(),
               "Estudiar TypeScript",
               "Repasar tipos y funciones",
               false
           );
           Tarea t2 = new Tarea(
               secuencia.incrementAndGet(),
               "Hacer la práctica 1",
               "Proyecto tareas en memoria",
               true
           );
           datos.put(t1.getId(), t1);
           datos.put(t2.getId(), t2);
       }

       @Override
       public List<Tarea> listarTodas() {
           return new ArrayList<>(datos.values());
       }

       @Override
       public Tarea buscarPorId(Long id) {
           if (id == null) return null;
           return datos.get(id);
       }

       @Override
       public Tarea crearTarea(String titulo, String descripcion) {
           long id = secuencia.incrementAndGet();
           Tarea t = new Tarea(id, titulo, descripcion, false);
           datos.put(id, t);
           return t;
       }

       @Override
       public Tarea actualizarTarea(Long id, String titulo, String descripcion, Boolean completada) {
           Tarea existente = datos.get(id);
           if (existente == null) return null;
           if (titulo != null) existente.setTitulo(titulo);
           if (descripcion != null) existente.setDescripcion(descripcion);
           if (completada != null) existente.setCompletada(completada);
           return existente;
       }

       @Override
       public boolean eliminarTarea(Long id) {
           if (id == null) return false;
           return datos.remove(id) != null;
       }
   }
   ```

---

### 3.3. Capa REST (controlador)

Paquete: `com.docencia.tareas.rest`

1. **Controlador `TareaRestController`**

   ```java
   @RestController
   @RequestMapping("/api/tareas")
   public class TareaRestController {

       private final TareaService tareaService;

       public TareaRestController(TareaService tareaService) {
           this.tareaService = tareaService;
       }

       @GetMapping
       public TareasResponse listarTodas() {
           return new TareasResponse(tareaService.listarTodas());
       }

       @GetMapping("/{id}")
       public ResponseEntity<Tarea> buscarPorId(@PathVariable Long id) {
           Tarea tarea = tareaService.buscarPorId(id);
           if (tarea == null) {
               return ResponseEntity.notFound().build();
           }
           return ResponseEntity.ok(tarea);
       }

       public record CrearTareaRequest(String titulo, String descripcion) {}
       public record ActualizarTareaRequest(String titulo, String descripcion, Boolean completada) {}

       @PostMapping
       public ResponseEntity<Tarea> crear(@RequestBody CrearTareaRequest request) {
           if (request == null || request.titulo() == null || request.titulo().isBlank()) {
               return ResponseEntity.badRequest().build();
           }
           Tarea creada = tareaService.crearTarea(request.titulo(), request.descripcion());
           return ResponseEntity.status(HttpStatus.CREATED).body(creada);
       }

       @PutMapping("/{id}")
       public ResponseEntity<Tarea> actualizar(@PathVariable Long id, @RequestBody ActualizarTareaRequest request) {
           Tarea actualizada = tareaService.actualizarTarea(
               id,
               request.titulo(),
               request.descripcion(),
               request.completada()
           );
           if (actualizada == null) {
               return ResponseEntity.notFound().build();
           }
           return ResponseEntity.ok(actualizada);
       }

       @DeleteMapping("/{id}")
       public ResponseEntity<Void> eliminar(@PathVariable Long id) {
           boolean ok = tareaService.eliminarTarea(id);
           if (!ok) {
               return ResponseEntity.notFound().build();
           }
           return ResponseEntity.noContent().build();
       }
   }
   ```

---

### 3.4. Capa SOAP (CXF + JAX‑WS)

Paquete: `com.docencia.tareas.soap`

1. **Interfaz JAX‑WS `TareaSoapEndpoint`**

   ```java
   @WebService(targetNamespace = "http://tareas.ies.puerto.es/")
   public interface TareaSoapEndpoint {

       @WebMethod
       List<Tarea> listarTodas();

       @WebMethod
       Tarea buscarPorId(@WebParam(name = "id") long id);

       @WebMethod
       Tarea crearTarea(
           @WebParam(name = "titulo") String titulo,
           @WebParam(name = "descripcion") String descripcion
       );

       @WebMethod
       Tarea actualizarTarea(
           @WebParam(name = "id") long id,
           @WebParam(name = "titulo") String titulo,
           @WebParam(name = "descripcion") String descripcion,
           @WebParam(name = "completada") Boolean completada
       );

       @WebMethod
       boolean eliminarTarea(@WebParam(name = "id") long id);
   }
   ```

2. **Implementación `TareaSoapEndpointImpl`**

   ```java
   @WebService(
       serviceName = "TareaService",
       portName = "TareaPort",
       targetNamespace = "http://tareas.ies.puerto.es/",
       endpointInterface = "com.docencia.tareas.soap.TareaSoapEndpoint"
   )
   @Service
   public class TareaSoapEndpointImpl implements TareaSoapEndpoint {

       private final TareaService tareaService;

       public TareaSoapEndpointImpl(TareaService tareaService) {
           this.tareaService = tareaService;
       }

       @Override
       public List<Tarea> listarTodas() {
           return tareaService.listarTodas();
       }

       @Override
       public Tarea buscarPorId(long id) {
           return tareaService.buscarPorId(id);
       }

       @Override
       public Tarea crearTarea(String titulo, String descripcion) {
           return tareaService.crearTarea(titulo, descripcion);
       }

       @Override
       public Tarea actualizarTarea(long id, String titulo, String descripcion, Boolean completada) {
           return tareaService.actualizarTarea(id, titulo, descripcion, completada);
       }

       @Override
       public boolean eliminarTarea(long id) {
           return tareaService.eliminarTarea(id);
       }
   }
   ```

3. **Configuración CXF `CxfConfig`**

   Paquete: `com.docencia.tareas.config`

   ```java
   @Configuration
   public class CxfConfig {

       private final Bus bus;
       private final TareaSoapEndpointImpl tareaSoapEndpoint;

       public CxfConfig(Bus bus, TareaSoapEndpointImpl tareaSoapEndpoint) {
           this.bus = bus;
           this.tareaSoapEndpoint = tareaSoapEndpoint;
       }

       @Bean
       public Endpoint tareaEndpoint() {
           EndpointImpl endpoint = new EndpointImpl(bus, tareaSoapEndpoint);
           endpoint.publish("/tareas");
           return endpoint;
       }
   }
   ```

Con `cxf.path=/services`, el endpoint SOAP quedará en:

- `http://localhost:8080/services/tareas`
- WSDL: `http://localhost:8080/services/tareas?wsdl`

---

## 4. Documentación con Swagger (springdoc-openapi)

Gracias a la dependencia:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>${springdoc.version}</version>
</dependency>
```

no hace falta una configuración complicada. Por defecto, al arrancar la aplicación:

1. Ejecuta:

   ```bash
   mvn spring-boot:run
   ```

2. Abre en el navegador:

   - `http://localhost:8080/swagger-ui.html`  
     o
   - `http://localhost:8080/swagger-ui/index.html`

En esa interfaz verás todos los endpoints de `TareaRestController` documentados automáticamente:

- `GET /api/tareas`
- `GET /api/tareas/{id}`
- `POST /api/tareas`
- `PUT /api/tareas/{id}`
- `DELETE /api/tareas/{id}`

Puedes añadir anotaciones como `@Operation` si quieres enriquecer aún más la documentación, pero no es obligatorio.

---

## 5. Uso opcional de MapStruct

Paquete: `com.docencia.tareas.mapper`

Si quieres separar **DTOs REST** o **DTOs SOAP** de tu modelo interno `Tarea`, puedes:

1. Crear un DTO, por ejemplo:

   ```java
   public class TareaRestDto {
       private Long id;
       private String titulo;
       private String descripcion;
       private boolean completada;
       // getters/setters...
   }
   ```

2. Crear un mapper:

   ```java
   @Mapper(componentModel = "spring")
   public interface TareaMapper {

       TareaRestDto toRestDto(Tarea tarea);

       Tarea toEntity(TareaRestDto dto);
   }
   ```

3. Inyectar `TareaMapper` en tu controlador REST y usar `toRestDto` antes de devolver la respuesta.

MapStruct generará las clases de implementación en `target/generated-sources/annotations` cuando ejecutes:

```bash
mvn clean compile
```

---

## 6. Resumen de pasos para construir el servicio

1. Crear el proyecto Spring Boot (`com.docencia`, Java 17).
2. Configurar `pom.xml` con:
   - `spring-boot-starter-web`
   - `springdoc-openapi-starter-webmvc-ui`
   - `cxf-spring-boot-starter-jaxws`
   - `mapstruct` + `mapstruct-processor`
   - Plugins (maven-compiler-plugin + spring-boot-maven-plugin).
3. Configurar `application.properties` con `server.port` y `cxf.path`.
4. Crear capa **model**:
   - `Tarea`
   - `TareasResponse`
5. Crear capa **service**:
   - `TareaService`
   - `InMemoryTareaService` (datos en memoria).
6. Crear capa **REST**:
   - `TareaRestController`, rutas `/api/tareas`.
7. Verificar REST con:
   - `curl` o Swagger UI (`/swagger-ui.html`).
8. Crear capa **SOAP**:
   - `TareaSoapEndpoint` (@WebService con targetNamespace `http://tareas.ies.puerto.es/`).
   - `TareaSoapEndpointImpl` delegando en `TareaService`.
   - `CxfConfig` publicando `/tareas`.
9. Verificar SOAP con:
   - `http://localhost:8080/services/tareas?wsdl` (WSDL).
   - Cliente SOAP (SoapUI, Postman, etc.).
10. (Opcional) Crear DTOs y `TareaMapper` con MapStruct para separar modelos internos y externos.


 🚀 A por el siguiente paso.

</div>