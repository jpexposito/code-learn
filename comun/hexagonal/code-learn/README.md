
<div align="justify">

# CodeLearn – Creación y despliegue de una solución multimódulo en un servidor de aplicaciones

## 1. Descripción general

Este proyecto implementa una **aplicación Java basada en Spring Boot desplegada como WAR en WildFly**.  
La solución utiliza una **arquitectura multimódulo Maven**, separando responsabilidades entre persistencia, servicios REST y despliegue.

- [Descarga Wildfly 26](https://github.com/wildfly/wildfly/releases/download/26.1.3.Final/wildfly-26.1.3.Final.zip)

Además, se describe **la configuración de la aplicación (`application.properties`) externalizada en un módulo de WildFly**, lo que permite modificar parámetros sin recompilar el WAR.

---

# 2. Arquitectura del proyecto

La solución sigue una arquitectura **multimódulo Maven**:

```
codelearn-parent
│
├── codelearn-persistence
│   ├── Entidades JPA
│   ├── Repositorios Spring Data
│
├── codelearn-rest
│   ├── Controladores REST
│   ├── Servicios de negocio
│
├── codelearn-war
│   ├── Configuración Spring Boot
│   ├── Seguridad
│   ├── Bootstrap de datos
│   └── Empaquetado WAR para WildFly
```

<div align="center">
  <img src=images/code-lear-modulos.png width="400">
</div>


### Responsabilidades

| Módulo | Responsabilidad |
|------|------|
| persistence | Acceso a base de datos |
| rest | API REST y lógica de negocio |
| war | Configuración de despliegue y seguridad |

Esta separación facilita:

- mantenimiento
- reutilización
- testing
- escalabilidad del proyecto

---

# 3. Versiones principales utilizadas

| Tecnología | Versión |
|------------|--------|
| Java | 17 |
| Spring Boot | 2.7.x |
| Hibernate | 5.3.x |
| WildFly | 26.x |
| Spring Security | 5.7.x |
| Spring Data JPA | 2.7.x |
| OpenAPI / Swagger | springdoc 1.6.x |
| Base de datos | H2 (configurada en WildFly) |


<div align="center">
  <img src=images/code-lear-librerias.png width="400">
</div>

---

# 4. Configuración de base de datos en WildFly

La base de datos se configura directamente dentro de WildFly mediante un **datasource**. La configuración se realiza en el fichero **standalone.xml** en la carpeta `WILDFLY_HOME/standalone/configuguracion`.

Ejemplo de datasource utilizado:

```xml
<datasource jndi-name="java:jboss/CodeLearnDS" pool-name="CodeLearnDS" enabled="true">
    <connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
    <driver>h2</driver>
    <security>
        <user-name>sa</user-name>
        <password>sa</password>
    </security>
</datasource>
```

<div align="center">
  <img src=images/code-lear-arquitectura.png width="400">
</div>

El datasource se referencia en Spring mediante:

```
spring.datasource.jndi-name=java:jboss/CodeLearnDS
```

Esto evita almacenar credenciales dentro de la aplicación.

---

# 5. Externalización de configuración mediante módulo WildFly

Para evitar que la configuración esté dentro del WAR se utiliza un **WildFly module**.

### Módulo

```
com.docencia.codelearn.config
```

Ubicación:

```
WILDFLY_HOME/modules/com/docencia/codelearn/config/main/
```

Contenido:

```
module.xml
application.properties
```

### module.xml

```xml
<module xmlns="urn:jboss:module:1.9" name="com.docencia.codelearn.config">
    <resources>
        <resource-root path="."/>
    </resources>
</module>
```

### application.properties

Contiene la configuración principal:

```
spring.datasource.jndi-name=java:jboss/CodeLearnDS

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

auth.max-failed-attempts=3

# Swagger / OpenAPI
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true

# Forzar URLs correctas bajo el context-root /codelearn
springdoc.swagger-ui.url=/codelearn/v3/api-docs
springdoc.swagger-ui.configUrl=/codelearn/v3/api-docs/swagger-config
springdoc.swagger-ui.disable-swagger-default-url=true
```

### Dependencia desde el WAR

El WAR declara el módulo mediante:

```
WEB-INF/jboss-deployment-structure.xml
```

```xml
<jboss-deployment-structure>
  <deployment>
    <dependencies>
      <module name="com.docencia.codelearn.config"/>
    </dependencies>
  </deployment>
</jboss-deployment-structure>
```

Ventajas:

- configuración editable sin recompilar
- separación entre código y configuración
- mayor seguridad

<div align="center">
  <img src=images/code-lear-arquitectura.png width="400">
</div>

---

# 6. Seguridad

La seguridad se implementa con **Spring Security**.

## Autenticación

Se utiliza **HTTP Basic** para simplificar pruebas.

Usuarios de ejemplo:

| Usuario | Password | Rol |
|-------|--------|------|
| admin | admin | ADMIN |
| user | user | USER |

## Reglas de seguridad

| Endpoint | Permiso |
|--------|--------|
| GET /api/tasks | USER, ADMIN |
| POST /api/tasks | ADMIN |
| PUT /api/tasks | ADMIN |
| DELETE /api/tasks | ADMIN |
| /api/auth/** | Público |

Configuración simplificada:

```java
.antMatchers(HttpMethod.GET, "/api/tasks/**").hasAnyRole("USER","ADMIN")
.antMatchers(HttpMethod.POST, "/api/tasks/**").hasRole("ADMIN")
.antMatchers(HttpMethod.PUT, "/api/tasks/**").hasRole("ADMIN")
.antMatchers(HttpMethod.DELETE, "/api/tasks/**").hasRole("ADMIN")
```

<div align="center">
  <img src=images/code-learn-securizacion.png width="400">
</div>

---

# 7. Swagger / OpenAPI

La documentación de la API se genera automáticamente.

URL:

```
/swagger-ui.html
```

Puede deshabilitarse desde configuración:

```
springdoc.api-docs.enabled=false
springdoc.swagger-ui.enabled=false
```

---

# 8. Códigos HTTP implementados

La API REST devuelve códigos correctos según la operación.

| Operación | Código |
|--------|--------|
GET OK | 200 |
Creación | 201 |
Borrado | 204 |
No encontrado | 404 |
No autorizado | 401 |
Prohibido | 403 |

---

# 9. Inicialización de datos

Al arrancar la aplicación se crean usuarios de prueba mediante un **ApplicationRunner**.

Esto facilita pruebas rápidas del sistema.

---

# 10. Posibles mejoras

### Seguridad

- JWT en lugar de Basic Auth
- OAuth2 / Keycloak
- Password hashing (BCrypt)

### Arquitectura

- Separar capa de servicios
- Introducir DTO mappers (MapStruct)
- Añadir capa de testing

### Observabilidad

- Prometheus metrics
- Spring Actuator
- Logs estructurados

### Persistencia

- Migraciones con Flyway o Liquibase

### Infraestructura

- Docker
- CI/CD pipeline
- Configuración externa con Vault

---

# 11. Ventajas de la solución

- Configuración externalizada
- Arquitectura modular
- Seguridad integrada
- Compatible con WildFly
- Documentación automática API
- Fácil de mantener y extender

---

# 12. Flujo de despliegue

1. Construir proyecto

```
mvn clean package
```

2. Copiar WAR

```
standalone/deployments/
```

3. Instalar módulo WildFly de configuración

```
modules/com/docencia/codelearn/config/
```

4. Reiniciar WildFly

---

# 13. Conclusión

La solución proporciona una base sólida para aplicaciones empresariales desplegadas en WildFly, combinando:

- Spring Boot
- configuración externalizada
- arquitectura modular
- seguridad básica

El diseño permite evolucionar fácilmente hacia arquitecturas más complejas manteniendo una base limpia y mantenible.

</div>
