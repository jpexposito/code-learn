<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios (Configurando Spring-boot)

<div align="center">
   <img src=images/spring-aplication.png width="400">
</div>

Este documento resume cómo configurar un proyecto **Spring Boot** usando el fichero `application.properties`, 
centrado en un escenario con:

- Una **base de datos relacional** (JPA / Hibernate).
- Una **base de datos MongoDB** (Spring Data MongoDB).
- Otros bloques habituales: logging, seguridad, Actuator, correo, etc.

Incluye además un ejemplo completo de `application.properties` en la carpeta del proyecto.

---

## 1. Ubicación y sintaxis básica

- Ruta típica: `src/main/resources/application.properties`  
- Formato: `clave=valor`, una propiedad por línea.  
- Comentarios con `#`:

```properties
# Esto es un comentario
spring.application.name=mi-aplicacion
```

También puedes usar perfiles:

- `application-dev.properties`
- `application-prod.properties`
- etc.

Y activar el perfil con:

```properties
spring.profiles.active=dev
```

---

## 2. Datos generales de la aplicación

Propiedades típicas de identidad y núcleo de Spring Boot:

```properties
spring.application.name=demo-jpa-mongo
debug=false
trace=false
spring.main.banner-mode=console  # console | log | off
# spring.main.web-application-type=servlet  # servlet | reactive | none
```

### Definición rápida

- `spring.application.name`: nombre lógico de la aplicación. Aparece en logs y algunos endpoints.
- `debug`: activa logs de depuración de Spring (no confundir con logging.level).
- `trace`: activa logs aún más verbosos.
- `spring.main.banner-mode`: muestra el banner en consola (`console`), en logs (`log`) o lo desactiva (`off`).
- `spring.main.web-application-type`: tipo de app (`servlet` por defecto).

---

## 3. Servidor embebido (Tomcat / Jetty / Undertow)

Configuración básica del servidor HTTP embebido:

```properties
server.port=8080
server.address=0.0.0.0
server.servlet.context-path=/api

server.servlet.encoding.charset=UTF-8
server.servlet.encoding.enabled=true
server.servlet.encoding.force=true

# Ejemplos de configuración avanzada de Tomcat
# server.tomcat.threads.max=200
# server.max-http-header-size=8KB
```

### Definición

- `server.port`: puerto HTTP donde escucha la aplicación (por defecto 8080).
- `server.address`: interfaz de red donde escucha (0.0.0.0 = todas).
- `server.servlet.context-path`: prefijo común de todos los endpoints (`/api`, `/app`, etc.).
- `server.servlet.encoding.*`: configuración de codificación de peticiones/respuestas.

---

## 4. Bases de datos: JPA (relacional) + MongoDB

En este ejemplo se usan **dos BDs**:

1. Una BD relacional (PostgreSQL, MySQL, etc.) gestionada con **JPA / Hibernate**.
2. Una BD MongoDB gestionada con **Spring Data MongoDB**.

Spring Boot auto-configura cada una usando propiedades distintas:

- Relacional: `spring.datasource.*` + `spring.jpa.*`
- MongoDB: `spring.data.mongodb.*`

### 4.1. Base de datos relacional (JPA / Hibernate)

Ejemplo con PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/demo_db
spring.datasource.username=demo
spring.datasource.password=demo
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

#### Definición

- `spring.datasource.url`: URL JDBC de conexión a la BD.
- `spring.datasource.username`: usuario de la BD.
- `spring.datasource.password`: contraseña de la BD.
- `spring.datasource.driver-class-name`: clase del driver JDBC (opcional si Spring la infiere).

- `spring.jpa.hibernate.ddl-auto`: qué hace Hibernate con el esquema de la BD al arrancar.
  - `none`: no cambia el esquema.
  - `validate`: valida contra las entidades.
  - `update`: intenta actualizar esquema sin borrar datos (útil en desarrollo).
  - `create`: crea el esquema de cero (borra antes).
  - `create-drop`: crea al iniciar y borra al parar.

- `spring.jpa.show-sql`: muestra el SQL generado por Hibernate en la consola (logs).
- `spring.jpa.properties.hibernate.format_sql`: formatea el SQL con saltos de línea.
- `spring.jpa.properties.hibernate.dialect`: dialecto concreto de la BD (Postgres, MySQL, etc.).

### 4.2. Base de datos MongoDB (Spring Data MongoDB)

Puedes configurarla con una **URI** única:

```properties
spring.data.mongodb.uri=mongodb://mongo_user:mongo_pass@localhost:27017/demo_mongo
spring.data.mongodb.auto-index-creation=true
```

O con propiedades separadas (host/port/database/usuario/password):

```properties
# Alternativa si no usas URI
# spring.data.mongodb.host=localhost
# spring.data.mongodb.port=27017
# spring.data.mongodb.database=demo_mongo
# spring.data.mongodb.username=mongo_user
# spring.data.mongodb.password=mongo_pass
```

#### Definición

- `spring.data.mongodb.uri`: URI de conexión a MongoDB (incluye host, puerto, BD y credenciales).
- `spring.data.mongodb.auto-index-creation`: crea automáticamente índices basados en anotaciones como `@Indexed`.

Si usas la URI normalmente **no necesitas** definir host/port/database/username/password por separado.

---

## 5. Logging (niveles, ficheros, patrones)

Logback es el sistema de logging por defecto en Spring Boot.  
Propiedades habituales:

```properties
logging.level.root=INFO
logging.level.org.springframework.web=DEBUG
logging.level.com.example.demo=DEBUG

logging.file.name=logs/app.log
# logging.file.path=/var/log/miapp

logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n
```

### Definición

- `logging.level.root`: nivel de log global (TRACE, DEBUG, INFO, WARN, ERROR, OFF).
- `logging.level.<paquete>`: nivel para un paquete o clase concretos.
- `logging.file.name`: ruta/nombre del fichero de log.
- `logging.file.path`: directorio base para logs (Spring genera un nombre por defecto).
- `logging.pattern.console` / `logging.pattern.file`: patrón de formato para consola y fichero.

---

## 6. Actuator (salud, métricas, info)

Con `spring-boot-starter-actuator` puedes exponer endpoints de monitorización:

```properties
management.endpoints.web.exposure.include=health,info
management.endpoints.web.base-path=/actuator
# management.server.port=9090  # Opcional: puerto distinto solo para actuator

management.endpoint.health.show-details=when_authorized
```

### Definición

- `management.endpoints.web.exposure.include`: qué endpoints se exponen por HTTP (`health`, `info`, `metrics`, `env`, `*`, etc.).
- `management.endpoints.web.base-path`: prefijo de los endpoints actuator.
- `management.server.port`: si se define, actuator escuchará en otro puerto distinto.
- `management.endpoint.health.show-details`: nivel de detalle de `/actuator/health` (`never`, `when_authorized`, `always`).

---

## 7. Seguridad (Spring Security básico)

Si tienes `spring-boot-starter-security`, puedes definir un usuario “de prueba” para entornos de desarrollo:

```properties
spring.security.user.name=admin
spring.security.user.password=admin123
spring.security.user.roles=ADMIN,USER
```

### Definición

- `spring.security.user.name`: nombre de usuario por defecto.
- `spring.security.user.password`: contraseña de ese usuario.
- `spring.security.user.roles`: roles asignados (separados por comas).

> ⚠️ **Importante**: esto es solo para desarrollo/pruebas.  
> En producción deberías usar otra estrategia (BD, LDAP, OAuth2, etc.).

---

## 8. Subida de ficheros (multipart)

Controla el manejo de peticiones `multipart/form-data`:

```properties
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=20MB
```

### Definición

- `spring.servlet.multipart.enabled`: habilita/deshabilita el soporte multipart.
- `spring.servlet.multipart.max-file-size`: límite de tamaño de un único fichero.
- `spring.servlet.multipart.max-request-size`: límite de tamaño de toda la petición.

---

## 9. Internacionalización y mensajes

Para mensajes de validación, textos de negocio, etc.:

```properties
spring.messages.basename=messages
spring.messages.encoding=UTF-8
# spring.messages.cache-duration=10s
```

### Definición

- `spring.messages.basename`: nombre base de los ficheros de mensajes (`messages.properties`, `messages_es.properties`, etc.).
- `spring.messages.encoding`: codificación (normalmente `UTF-8`).
- `spring.messages.cache-duration`: duración de la caché de los bundles de mensajes.

---

## 10. Envío de correo (Spring Mail)

Con `spring-boot-starter-mail`:

```properties
spring.mail.host=smtp.miempresa.com
spring.mail.port=587
spring.mail.username=usuario@mail.com
spring.mail.password=secreto

spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Definición

- `spring.mail.host`: servidor SMTP.
- `spring.mail.port`: puerto del servidor SMTP (25, 465, 587…).
- `spring.mail.username` / `spring.mail.password`: credenciales.
- `spring.mail.properties.mail.smtp.auth`: indica si requiere autenticación.
- `spring.mail.properties.mail.smtp.starttls.enable`: activa STARTTLS (capa segura).

---

## 11. Caché y Redis

### Caché genérica

```properties
spring.cache.type=simple  # none | simple | redis | caffeine | ehcache | etc.
```

- `spring.cache.type`: tipo de proveedor de caché.

### Redis

```properties
# Ejemplo (solo si usas Redis)
# spring.redis.host=localhost
# spring.redis.port=6379
# spring.redis.password=
```

- `spring.redis.host`: host del servidor Redis.
- `spring.redis.port`: puerto de Redis.
- `spring.redis.password`: contraseña, si la hay.

---

## 12. Propiedades propias de tu aplicación

Puedes crear tus propios grupos de propiedades, por ejemplo:

```properties
app.nombre=Mi super API
app.version=1.0.0
app.feature.ventas.enabled=true
```

Y consumirlas en código con `@Value` o `@ConfigurationProperties`:

```java
@Value("${app.nombre}")
private String nombreApp;
```

o

```java
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String nombre;
    private String version;
    private Feature feature;
    // getters/setters...

    public static class Feature {
        private boolean ventasEnabled;
    }
}
```

---

## 13. Perfiles y ficheros por entorno

Estrategia habitual:

- `application.properties` → configuración común a todos los entornos.
- `application-dev.properties` → configuración específica de desarrollo.
- `application-prod.properties` → configuración específica de producción.

Activar perfil:

```properties
spring.profiles.active=dev
```

Así puedes tener, por ejemplo:

- BD local y Mongo local en `application-dev.properties`.
- BD en servidor y Mongo en cluster en `application-prod.properties`.

---

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>