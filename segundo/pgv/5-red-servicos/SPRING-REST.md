<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios (Construcción de Servicios REST))

<div align="center">
   <img src=images/spring-logo.png width="400">
</div>

Lo primero abordaremos los fundamentos teóricos necesarios para construir un servicio REST con **Spring Boot** y luego proporcionaremos un ejemplo práctico.

---

## **Fundamentos para Construir un Servicio REST**

**REST (Representational State Transfer)** es un estilo arquitectónico para diseñar servicios web. Los servicios RESTful permiten la comunicación entre sistemas usando el protocolo HTTP y recursos identificados mediante URLs. Sus principales características son:

- **Recursos**: Representan entidades del sistema (por ejemplo, usuarios, productos).
- **Métodos HTTP**:
  - **GET**: Recuperar recursos.
  - **POST**: Crear nuevos recursos.
  - **PUT/PATCH**: Actualizar recursos existentes.
  - **DELETE**: Eliminar recursos.
- **Formato de Datos**: JSON es el más común, pero también puede usarse XML, YAML, etc.

### ¿Qué es Spring Boot?

**Spring Boot** es una extensión de Spring que facilita la creación de aplicaciones al reducir la configuración manual. Entre sus características más destacadas están:

- **Servidor embebido**: No necesitas configurar Tomcat o Jetty por separado.
- **Configuración automática**: Spring Boot configura automáticamente componentes comunes.
- **Inicio rápido**: A través de herramientas como Spring Initializr.

---

### Arquitectura de un Servicio REST en Spring Boot

1. **Modelo (Model)**: Define las entidades que representan los datos del sistema.
2. **Repositorio (Repository)**: Se encarga de interactuar con la base de datos.
3. **Servicio (Service)**: Contiene la lógica de negocio.
4. **Controlador (Controller)**: Expone los recursos a través de los endpoints HTTP.

---

### Ventajas de Usar Spring Boot para REST

1. **Configuración Reducida**: Configura automáticamente muchas características necesarias.
2. **Modularidad**: Separa la lógica de negocio, la gestión de datos y la interfaz REST.
3. **Escalabilidad**: Facilita la construcción de APIs escalables y mantenibles.
4. **Soporte a JSON**: Integración nativa con bibliotecas como Jackson para manejar JSON.
5. **Integración con Bases de Datos**: A través de Spring Data JPA, facilita el acceso y la manipulación de datos.

---

### Flujo Básico para Crear un Servicio REST

1. **Configuración del Proyecto**: Configurar dependencias y estructura del proyecto.
2. **Definición del Modelo**: Crear clases que representen las entidades del sistema.
3. **Implementación del Repositorio**: Crear interfaces que interactúen con la base de datos.
4. **Lógica del Servicio**: Implementar la lógica de negocio en una capa de servicio.
5. **Creación del Controlador**: Exponer endpoints para acceder a los recursos.
6. **Pruebas y Validaciones**: Validar datos y manejar errores.

---

## **Ejemplo Práctico: Construcción de un Servicio REST en Spring Boot**

### Paso 1: Configuración del Proyecto

#### Usar Spring Initializr

1. Visita [Spring Initializr](https://start.spring.io/).
2. Configura el proyecto:
   - **Tipo**: Maven.
   - **Lenguaje**: Java.
   - **Versión de Spring Boot**: Última estable.
   - **Dependencias**:
     - **Spring Web**: Para APIs REST.
     - **Spring Data JPA**: Para acceso a datos.
     - **H2 Database**: Base de datos en memoria (opcional).
   - Descarga el proyecto generado y ábrelo en tu IDE.

---

### Paso 2: Definir el Modelo

Crea una clase que represente un recurso (por ejemplo, Usuario):

```java
    import jakarta.persistence.*;

    @Entity
    @Table(name = "users")
    public class User {

        private int id;
        private String name;
        
        public User() {
            
        }
        
        public User(String name) {
            this.name = name;
        }
        
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        
        @Column(name = "name", nullable = false)
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User [id=" + id + ", name=" + name + "]";
        }
    }
```

### Paso 3: Crear el Repositorio

Define una interfaz que extienda `JpaRepository` para manejar las operaciones CRUD de forma automática:

```java
import es.system.jpexposito.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
```

> **@Repository** infica que componente de **Spring** de tipo repositorio, es decir, provee de **datos**.

### Paso 4: Implementar el Servicio

Crea una clase de servicio que encapsule la lógica de negocio. Este servicio interactúa con el repositorio para realizar operaciones sobre los datos:

```java
@Component
public class UserService implements UserServiceInterface{

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(@PathVariable(value = "id") int userId) throws ResourceNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
    }

    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    public User updateUser(@PathVariable(value = "id") int userId,
                                           @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        user.setName(userDetails.getName());
        return userRepository.save(user);
    }

    public void deleteUser(@PathVariable(value = "id") int userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        userRepository.delete(user);
    }
}
```

### Paso 5: Crear el Controlador REST

Define un controlador REST para exponer los endpoints HTTP y permitir a los clientes interactuar con el servicio:

```java
@RestController
@RequestMapping("/api/v1")
public class UsersController {

    private UserServiceInterface userService;

    @Autowired
    public void setUserRepository(UserServiceInterface userService) {
        this.userService = userService;
    }


    @Operation(summary = "Get all users")
    @GetMapping("/users/")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") int userId) throws ResourceNotFoundException {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Insert user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/add/user/")
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/update/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") int userId,
                                           @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        final User updatedUser = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/delete/user/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") int userId) throws ResourceNotFoundException {
        userService.deleteUser(userId); 
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
```

### Paso 6: Configurar la Base de Datos

Configura la base de datos en el archivo application.properties. Para este ejemplo, usaremos una base de datos en memoria H2:

```properties
spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto = update
```

> La implementación completa del servicio se encuentra en las siguientes url:
> - [Servicio](https://github.com/jpexposito/spring-boot-persistence-h2).
> - [Clente Servicio](https://github.com/jpexposito/spring-boot-persistence-h2-client).

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>