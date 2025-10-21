<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Anotaciones en Spring Framework)

<div align="center">
   <img src=images/spring-logo.png width="400">
</div>

Guía rápida (con significado y ejemplo) de las anotaciones más usadas en Spring y Spring Boot. Incluye configuración, DI, web/REST, persistencia, AOP, caché/cron/async, seguridad, validación, condicionales y testing.

---

## Índice

- [Arranque y configuración](#arranque-y-configuración)
- [Componentes e Inyección de Dependencias (DI)](#componentes-e-inyección-de-dependencias-di)
- [Web MVC / REST](#web-mvc--rest)
- [Persistencia (JPA / Spring Data)](#persistencia-jpa--spring-data)
- [AOP (Aspect Oriented Programming)](#aop-aspect-oriented-programming)
- [Caché, tareas y asincronía](#caché-tareas-y-asincronía)
- [Seguridad (Spring Security)](#seguridad-spring-security)
- [Validación](#validación)
- [Condicionales (Auto-config Boot)](#condicionales-auto-config-boot)
- [Testing con Spring](#testing-con-spring)
- [Buenas prácticas rápidas](#buenas-prácticas-rápidas)

---

## Arranque y configuración

### `@SpringBootApplication`

Une `@Configuration` + `@EnableAutoConfiguration` + `@ComponentScan`. Punto de entrada de una app Spring Boot.

```java
@SpringBootApplication
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
```

### `@Configuration`

Declara una clase de configuración con métodos `@Bean`.

```java
@Configuration
class AppConfig {
  @Bean DataSource ds() { return new HikariDataSource(); }
}
```

### `@Bean`

Registra un bean gestionado por el contenedor.

```java
@Bean PasswordEncoder encoder() { return new BCryptPasswordEncoder(); }
```

### `@ComponentScan`

Indica paquetes a escanear para registrar componentes.

```java
@SpringBootApplication
@ComponentScan({"com.ejemplo.api","com.ejemplo.core"})
class App {}
```

### `@PropertySource`

Carga propiedades adicionales desde un archivo.

```java
@Configuration
@PropertySource("classpath:app-extra.properties")
class PropsCfg {}
```

### `@ConfigurationProperties` y `@EnableConfigurationProperties`

Mapea propiedades a un POJO y habilita su carga.

```java
@ConfigurationProperties(prefix = "storage")
public record StorageProps(String path, int maxFiles) {}

@SpringBootApplication
@EnableConfigurationProperties(StorageProps.class)
class App {}
```

### `@Profile`

Crea beans solo para ciertos perfiles (ej.: `dev`, `prod`).

```java
@Bean @Profile("dev")
DataSource h2() { return new HikariDataSource(/* H2 config */); }
```

### `@Import`

Importa otras configuraciones.

```java
@Configuration
@Import(SecurityConfig.class)
class RootConfig {}
```

---

## Componentes e Inyección de Dependencias (DI)

### `@Component`, `@Service`, `@Repository`, `@Controller`, `@RestController`

Estereotipos para registrar beans con semántica (servicio, repositorio, controlador).

```java
@Service class EmailService { /* ... */ }

@RestController
class HelloCtrl {
  @GetMapping("/hi")
  String hi() { return "hi"; }
}
```

### `@Autowired`

Inyección de dependencias (recomendado por **constructor**; en Spring Boot no es obligatorio poner `@Autowired` en el constructor único).

```java
@Service
class OrderService {
  private final PaymentGateway gw;
  public OrderService(PaymentGateway gw) { this.gw = gw; }
}
```

### `@Qualifier`

Desambigua cuando hay varios beans del mismo tipo.

```java
public OrderService(@Qualifier("stripe") PaymentGateway gw) { /* ... */ }
```

### `@Primary`

Marca el bean preferido para inyección.

```java
@Bean @Primary PaymentGateway stripe() { return new StripeGateway(); }
```

### `@Scope`

Alcance del bean (`singleton`, `prototype`, `request`, `session`).

```java
@Component @Scope("prototype")
class NonSingleton { /* ... */ }
```

### `@Lazy`

Inicialización diferida.

```java
@Bean @Lazy
ExpensiveClient client() { return new ExpensiveClient(); }
```

### `@Value`

Inyección de valores/propiedades.

```java
@Value("${app.title:MiApp}")
String appTitle;
```

### `@PostConstruct` / `@PreDestroy`

Hooks de ciclo de vida del bean.

```java
@Component
class InitBean {
  @PostConstruct void init() { /* listo para usarse */ }
  @PreDestroy void close() { /* liberar recursos */ }
}
```

---

## Web MVC / REST

### `@RequestMapping` y métodos abreviados: `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@PatchMapping`

Mapeo de rutas y verbos HTTP.

```java
@GetMapping("/users/{id}")
User get(@PathVariable Long id) { /* ... */ return u; }
```

### `@PathVariable` / `@RequestParam` / `@RequestBody`

Obtención de datos de la URL, querystring o body.

```java
@GetMapping("/search")
List<User> search(@RequestParam String q) { /* ... */ }

@PostMapping("/users")
User create(@RequestBody UserDto dto) { /* ... */ }
```

### `@ResponseStatus`

Fija un código HTTP concreto.

```java
@PostMapping("/ok")
@ResponseStatus(HttpStatus.CREATED)
void ok() {}
```

### `@ExceptionHandler` y `@ControllerAdvice`

Manejo (global) de errores.

```java
@ControllerAdvice
class ApiErrors {
  @ExceptionHandler(NotFoundException.class)
  ResponseEntity<?> nf(NotFoundException e) {
    return ResponseEntity.status(404).body(e.getMessage());
  }
}
```

### `@CrossOrigin`

Habilita CORS para endpoints.

```java
@CrossOrigin(origins = "*")
@GetMapping("/public")
String open() { return "ok"; }
```

---

## Persistencia (JPA / Spring Data)

> Estas anotaciones son mayormente de **Jakarta Persistence** pero se usan con Spring Data.

### `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`

Definen la entidad y sus mapeos de columnas.

```java
@Entity @Table(name = "users")
class User {
  @Id @GeneratedValue Long id;
  @Column(nullable = false, unique = true) String email;
}
```

### `@Transactional`

Control de transacciones (útil en capa de servicio).

```java
@Service
class AccountService {
  @Transactional
  void transfer(Long from, Long to, BigDecimal amount) { /* ... */ }
}
```

---

## AOP (Aspect Oriented Programming)

### `@Aspect`, `@Before`, `@AfterReturning`, `@Around`, `@Pointcut`

Intercepción para logging, métricas, seguridad transversal, etc.

```java
@Aspect @Component
class LogAspect {
  @Pointcut("within(com.ejemplo.service..*)") void svc() {}
  @Before("svc()") void log() { /* pre */ }
}
```

---

## Caché, tareas y asincronía

### `@EnableCaching`, `@Cacheable`, `@CachePut`, `@CacheEvict`

Activa y usa caché con nombres lógicos.

```java
@SpringBootApplication @EnableCaching
class App {}

@Service
class CityService {
  @Cacheable("cities")
  City byId(Long id) { /* consulta lenta */ return /* ... */; }
}
```

### `@EnableScheduling` / `@Scheduled`

Tareas programadas (cron o fixed rate/delay).

```java
@SpringBootApplication @EnableScheduling
class App {}

@Component
class Jobs {
  @Scheduled(cron = "0 0 * * * *") // cada hora
  void hourly() { /* ... */ }
}
```

### `@EnableAsync` / `@Async`

Ejecución asíncrona con `Executor`.

```java
@SpringBootApplication @EnableAsync
class App {}

@Service
class Mailer {
  @Async void sendAsync(Mail m) { /* ... */ }
}
```

---

## Seguridad (Spring Security)

### `@EnableWebSecurity`

Activa/configura seguridad web (vía DSL de `HttpSecurity`).

```java
@EnableWebSecurity
class SecurityConfig { /* http.authorizeHttpRequests(...) etc. */ }
```

### `@PreAuthorize`, `@Secured`, `@RolesAllowed`

Autorización a nivel de método.

```java
@PreAuthorize("hasRole('ADMIN')")
public void deleteUser(Long id) { /* ... */ }
```

---

## Validación

### `@Valid` / `@Validated`

Dispara la validación Bean Validation (en controladores o servicios).

```java
@PostMapping("/users")
User create(@Valid @RequestBody UserDto dto) { /* ... */ return /* ... */; }
```

### Anotaciones de Jakarta Validation (comunes)

`@NotNull`, `@Size`, `@Email`, `@Min`, `@Max`, etc.

```java
public record UserDto(
  @NotNull @Email String email,
  @Size(min = 8) String password
) {}
```

---

## Condicionales (Auto-config Boot)

### `@ConditionalOnProperty`, `@ConditionalOnClass`, `@ConditionalOnMissingBean`, etc.

Crean beans/autoconfiguraciones si se cumplen condiciones.

```java
@Bean
@ConditionalOnProperty(name = "feature.x.enabled", havingValue = "true")
FeatureX x() { return new FeatureX(); }
```

---

## Testing con Spring

### `@SpringBootTest`

Levanta el contexto completo para pruebas integradas.

```java
@SpringBootTest
class AppIT { /* tests */ }
```

### `@WebMvcTest`, `@DataJpaTest`

“Slices” para probar solo web o JPA.

```java
@WebMvcTest(controllers = HelloCtrl.class)
class HelloCtrlTest { /* ... */ }
```

### `@MockBean`

Inyecta mocks en el contexto de pruebas.

```java
@MockBean PaymentGateway gateway;
```

---

## Buenas prácticas rápidas

- Inyección por **constructor**; evita `@Autowired` en campos.
- DTOs de entrada **validados** (`@Valid`) y **mapa** a entidades en la capa de servicio.
- **Controladores delgados**, **servicios transaccionales**, **repositorios** limpios.
- Usa **perfiles** (`@Profile`) para diferenciar configs (`dev`, `test`, `prod`).
- Evita lógica en `@PostConstruct` que pueda fallar en arranque si depende de servicios externos; usa **health checks** o `SmartLifecycle`.

</div>

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>