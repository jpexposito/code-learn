<div align="justify">

# CodeLearn – Servicio Multimódulo desplegado en WildFly

## 1. Introducción

Este proyecto es una **aplicación Java basada en Spring Boot desplegada como WAR en WildFly**.
Está organizada como **proyecto Maven multimódulo** para separar responsabilidades entre:

- persistencia de datos
- lógica de negocio y API REST
- configuración de despliegue

Además, la configuración de la aplicación **no está dentro del WAR**, sino que se **externaliza en un módulo de WildFly**, lo que permite modificar parámetros sin recompilar la aplicación.

- [Descarga Wildfly 26](https://github.com/wildfly/wildfly/releases/download/26.1.3.Final/wildfly-26.1.3.Final.zip)

El proyecto también incluye:

- seguridad básica con **Spring Security**
- documentación automática de la API con **OpenAPI / Swagger**
- base de datos **H2 configurada dentro de WildFly**

---

## 2. Requisitos previos

Antes de ejecutar el proyecto necesitas tener instalado:

| Software | Versión recomendada |
|--------|--------|
| Java | 17 |
| Maven | 3.8+ |
| WildFly | 26.x |
| Git | opcional |

Comprobar instalación:

```
java -version
mvn -version
```

---

## 3. Tecnologías utilizadas

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

## 4. Arquitectura del proyecto

El proyecto está dividido en módulos Maven:

```
codelearn-parent
│
├── codeLearn-persistence
│   ├── Entidades JPA
│   └── Repositorios Spring Data
│
├── codeLearn-rest
│   ├── Controladores REST
│   └── Servicios de negocio
│
├── codeLearn-war
│   ├── Configuración Spring Boot
│   ├── Seguridad
│   ├── Inicialización de datos
│   └── Empaquetado WAR para WildFly
```

<div align="center">
  <img src=images/code-lear-modulos.png width="400">
</div>

### Responsabilidades

| Módulo | Responsabilidad |
|------|------|
| persistence | Acceso a base de datos |
| rest | API REST |
| war | Configuración, seguridad y despliegue |

---

## 5. Base de datos configurada en WildFly

La base de datos **no está dentro de la aplicación**, sino que se configura en WildFly mediante un **datasource**.

Ejemplo:

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

La aplicación accede mediante:

```
spring.datasource.jndi-name=java:jboss/CodeLearnDS
```

> Tienes disponible el fichero ya preparado para la [descarga](resources/standalone.xml).

<div align="center">
  <img src=images/code-lear-arquitectura.png width="400">
</div>


---

## 6. Configuración externalizada (módulo WildFly)

La configuración de Spring **no se incluye dentro del WAR**.

Se utiliza el módulo:

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

En la carpeta **resources** tienes:

- ZIP del [módulo](resources/) WildFly ya preparado
- [standalone.xml]((resources/standalone.xml)) de ejemplo

---

## 7. Swagger / OpenAPI

Swagger permite probar la API desde el navegador.

URL:

```
http://localhost:8080/codelearn/swagger-ui/index.html
```

---

## 8. Seguridad

Se utiliza **Spring Security con HTTP Basic**.

Usuarios de prueba:

| Usuario | Password | Rol |
|-------|--------|------|
| admin | admin | ADMIN |
| user | user | USER |

<div align="center">
  <img src=images/code-learn-securizacion.png width="400">
</div>

---

## 9. Códigos HTTP

| Operación | Código |
|--------|--------|
| OK | 200 |
| Creación | 201 |
| Eliminación | 204 |
| No encontrado | 404 |
| No autenticado | 401 |
| Sin permisos | 403 |

---

## 10. Despliegue

1. Compilar:

```
mvn clean package
```

2. Copiar WAR a:

```
WILDFLY_HOME/standalone/deployments/
```

3. Instalar módulo de configuración desde `resources`

4. Reiniciar WildFly

---

## 11. Verificación

<div align="center">
  <img src=images/code-learn-wildfly-start.png width="400">
</div>

Abrir:

```
http://localhost:8080/codelearn/swagger-ui/index.html
```

<div align="center">
  <img src=images/code-learn-swagger.png width="400">
</div>


---

## 12. Posibles mejoras

- JWT
- Keycloak
- Flyway
- Observabilidad

## 13. Crear un usuario administrador en WildFly

WildFly incluye un script para crear usuarios de administración necesarios para acceder a la consola web.

### Linux / Mac

Desde el directorio de instalación de WildFly ejecutar:

```bash
WILDFLY_HOME/bin/add-user.sh
```

### Windows

Ejecutar:

```bat
WILDFLY_HOME\bin\add-user.bat
```

### Proceso de creación

El asistente preguntará:

```
What type of user do you wish to add?
a) Management User
b) Application User
```

Seleccionar:

```
a
```

Después introducir:

```
Username: admin
Password: ********
```

y al resto de opciones `yes`.

Al finalizar aparecerá:

```
Added user 'admin' to file 'mgmt-users.properties'
```

---

### Arrancar WildFly

Para arranca el servidor en remoto debemos de añadir `-b 0.0.0.0 -bmanagement 0.0.0.0` al scrip `standalone.sh\bat`. 

#### Linux / Mac

```bash
WILDFLY_HOME/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0
```

#### Windows

```bat
WILDFLY_HOME\bin\standalone.bat -b 0.0.0.0 -bmanagement 0.0.0.0
```

### Windows

```bat
standalone.bat -b 0.0.0.0 -bmanagement 0.0.0.0
```

---

### Acceder a la consola de administración

Abrir en el navegador:

```
http://localhost:9990
```

Introducir el usuario creado anteriormente.


<div align="center">
  <img src=images/wildfly-ds.png width="400">
</div>

---

### Verificar que el servidor ha arrancado

Cuando WildFly arranca correctamente aparecerá un mensaje similar a:

```
WFLYSRV0025: WildFly Full 26.x started
```

---

### Acceso a la aplicación

Aplicación:

```
http://localhost:8080/codelearn
```

Swagger:

```
http://localhost:8080/codelearn/swagger-ui/index.html
```

</div>