<div align="justify">

# Construyendo una aplicación modular en arquitectura hexagonal sobre wildfly.

## Arquitectura Hexagonal (Rest/Soap + Core + Adapters) — codeLearn

```markdown

                         ┌─────────────────────────────────────┐
                         │          codeLearn-war              │
                         │                                     │
                         │  - web.xml / CXFServlet             │
                         │  - configuración de la solución.    |
                         |  - WEB-INF\lib\librerias de la sol. |
                         │                                     │
                         │                                     │
                         └──────────────────┬──────────────────┘
                                            │ 
                                            │ 
                                            ▼
        ┌─────────────────────────────────────────────────────────────────────────┐
        │                         codeLearn-service (CORE)                        │
        │                    (Dominio + Aplicación + Puertos)                     │
        │                                                                         │
        │   ┌──────────────────────────────┐      ┌─────────────────────────────┐ │
        │   │          DOMINIO             │      │         APLICACIÓN          │ │
        │   │  Clases de negocio:          │      │  Casos de uso (Use Cases)   │ │
        │   │  - Alumno                    │      │  - MatricularAlumnoEnCurso  │ │
        │   │  - Curso                     │      │  - CrearTarea               │ │
        │   │  - Tarea                     │      │  - AsignarTareaAAlumno      │ │
        │   │  Reglas:                     │      │  - ListarCursosDeAlumno     │ │
        │   │  - Un alumno no dup. curso   │      │  - CompletarTarea           │ │
        │   │  - Tarea debe pertenecer     │      └──────────────┬──────────────┘ │
        │   │    a un curso                │                     │                │
        │   └──────────────────────────────┘                     │ usa            │
        │                                                        │                |
        │ Puertos (interfaces)                                   ▼                │
        │ - IN: AlumnoRestApi/SoapApi, CursoRestApi/SoapApi, TareaRestApi/SoapApi │
        │ - OUT: AlumnoRepositoryPort, CursoRepositoryPort, TareaRepositoryPort   │
        │          AuditPort, NotifierPort (opcional)                             │
        └─────────────────────────────────────┬───────────────────────────────────┘
                                              │ Utiliza otros elementos 
                                              │ como
                                              ▼
                            ┌─────────────────────────────────────┐
                            │          codeLearn-adapter          │
                            │Tendremos dos varios tipos de apater:│
                            │                                     │
                            │  Persistencia:                      │
                            │  - JPA/Hibernate repos              │
                            │    (implements *RepositoryPort)     │
                            │                                     │
                            │  Integraciones (opcional):          │
                            │  - Cliente externo (correo, etc.)   │
                            |                                     |
                            |  Otros soliciones:                  |  
                            |  - Clientes Soap/Clientes Rest      |  
                            │                                     │
                            │  Auditoría (opcional):              │
                            │  - implements AuditPort             │
                            └─────────────────────────────────────┘


        ┌─────────────────────────────┐
        │        codeLearn-parent     │
        │ (Parent Maven: versiones,   │
        │  plugins, dependency mgmt)  │
        └─────────────────────────────┘
```

A continuación se muestra la estructura de módulos Maven para implementar la solución descrita (WildFly + CXF + Spring) siguiendo Arquitectura Hexagonal.

```
- codeLearn-parent (pom)
─ codeLearn-service (jar)          <- CORE (dominio + aplicación + in (rest/soap))
─ codeLearn-adapter (pom)          <- Agrupador de adaptadores
  ├─ codeLearn-adapter-persistence (jar) <- JPA/Hibernate (Repository Relacional/No Relacional)
  ├─ codeLearn-adapter-audit (jar)   <- Auditoría (AuditPort) (opcional)
  ├─ codeLearn-adapter-notifier-jms (jar) <- Notificaciones (NotifierPort) (integración con un servicio de notificación jms)
  ├─ codeLearn-adapter-client-rest-service-1 (jar)   <- Clientes REST externos
  └─ codeLearn-adapter-client-soap-service-2 (jar)   <- Clientes SOAP externos
─ codeLearn-rest/                     (jar)  <-- IN: expone REST y se comunica con codeLearn-service
─ codeLearn-war/                      (war)  <-- ensamblado de todos los jar en un war + despliegue WildFly
─ codeLearn-war (war)              <- Inbound + bootstrap (web.xml, CXF, wiring)
```

<div align="center">
  <img src=images/arquitectura-code-learn.png width=400/>
</div>
---

## ¿Qué hace cada módulo?

### `codeLearn-parent` (pom)
- Padre de herencia (centraliza versiones, plugins, propiedades)
- Contiene `dependencyManagement` y `pluginManagement`

### `codeLearn-service` (jar) — CORE
- Dominio + aplicación (casos de uso)
- Define **interfaz la interfaz para comunicarse con él**. Esta interfaz la utiliza el módulo `codeLearn-rest`
- Se comunica con los **`adapters-*`** (interfaces que implementan adapters de infraestructura).
- **NO depende de WildFly, JPA, CXF, etc**.

### `codeLearn-adapter` (pom)
- Agregador de adapters OUT
- No produce artefacto (no jar), solo organiza submódulos

### `codeLearn-adapter-persistence` (jar) — OUT
- Implementa puertos OUT del CORE (p.ej. `AlumnoRepositoryPort`)
- Suele usar JPA/Hibernate u otra tecnología (dependencias infra)
- Es utilizado por `codeLearn-service`

### `codeLearn-adapter-audit` (jar) — OUT
- Implementa un puerto OUT de auditoría/observabilidad (p.ej. `AuditPort`)
- Es utilizado por `codeLearn-service`

### `codeLearn-rest` (jar) — IN
- Expone endpoints REST (JAX-RS, CXF…)
- Traduce HTTP/JSON → Command/Clases de Dominio → **Puerto IN del CORE**
- Se comunica y relaciona con las interfaces creadas en `codeLearn-service`, y no sabe de la existencia de los `adaptares` (NO conoce persistencia (eso es OUT).

### `codeLearn-war` (war)
- Ensambla todos los `jar` para desplegar en WildFly, que es el que publica el servicio `rest`.
- Suele contener:
  - wiring/configuración (CDI/Spring/manual)
  - ficheros de despliegue (`jboss-deployment-structure.xml`, `web.xml` si aplica)
- Depende de `codeLearn-service`, `codeLearn-rest` y adapters OUT

## ¿Qué contiene cada módulo?

### `codeLearn-parent/pom.xml` 

Define el conjunto de dependencias que utilizan el resto del módulos que forman parte de la solución.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelVersion>4.0.0</modelVersion>

  <groupId>com.jpexposito.codelearn</groupId>
  <artifactId>codeLearn-parent</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>pom</packaging>

<!-- No vamos a definir los modulos en este modulo 
  <modules>
    <module>codeLearn-service</module>
    <module>codeLearn-adapter</module>
    <module>codeLearn-rest</module>
    <module>codeLearn-war</module>
  </modules>
-->

  <properties>
    <maven.compiler.release>17</maven.compiler.release>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

    <!-- Versiones compartidas -->
    <slf4j.version>2.0.13</slf4j.version>
    <junit.jupiter.version>5.10.3</junit.jupiter.version>
    <cxf.version>4.0.5</cxf.version>

    <!-- Plugins -->
    <maven.compiler.plugin.version>3.13.0</maven.compiler.plugin.version>
    <maven.surefire.plugin.version>3.2.5</maven.surefire.plugin.version>
    <maven.war.plugin.version>3.4.0</maven.war.plugin.version>
  </properties>

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>${slf4j.version}</version>
      </dependency>

      <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>${junit.jupiter.version}</version>
        <scope>test</scope>
      </dependency>

      <!-- CXF -->
      <dependency>
        <groupId>org.apache.cxf</groupId>
        <artifactId>cxf-rt-frontend-jaxrs</artifactId>
        <version>${cxf.version}</version>
      </dependency>
      <dependency>
        <groupId>org.apache.cxf</groupId>
        <artifactId>cxf-rt-transports-http</artifactId>
        <version>${cxf.version}</version>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <build>
    <pluginManagement>
      <plugins>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>${maven.compiler.plugin.version}</version>
          <configuration>
            <release>${maven.compiler.release}</release>
          </configuration>
        </plugin>

        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>${maven.surefire.plugin.version}</version>
          <configuration>
            <useModulePath>false</useModulePath>
          </configuration>
        </plugin>

        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-war-plugin</artifactId>
          <version>${maven.war.plugin.version}</version>
          <configuration>
            <failOnMissingWebXml>false</failOnMissingWebXml>
          </configuration>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>

</project>
```

### `codeLearn-service/pom.xml`

El módulo `codeLearn-service` define interfaces para que se comunique con el `codeLearn-rest` y utiliza las interfaces definida en `codeLearn-adapters-*`. Define las propias clases de `dominio` que no tienen que coincidir en `nombre` o `propiedades` a las que existen en los `adapters`. En este modulo se utilizarán `mappers` a través de `mapStruct` para transformar  las clases de domino a las clases de los apapters y viceversa. A través del `pom.xml` realiza la relación con el `pom.xml` del `padre`.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelVersion>4.0.0</modelVersion>

  <parent>
    <groupId>com.jpexposito.codelearn</groupId>
    <artifactId>codeLearn-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <relativePath>../pom.xml</relativePath>
  </parent>

  <artifactId>codeLearn-service</artifactId>
  <packaging>jar</packaging>

  <dependencies>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
    </dependency>

    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
      </plugin>
    </plugins>
  </build>

</project>
```

### `codeLearn-adapter-persistence/pom.xml` (OUT persistencia)

El módulo `codeLearn-adapter-persistence` define la comunicación de la aplicación con la persistencia, generalmente una bbdd, pero puede suer también `sistemas de ficheros`, por ejemplo.


```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelVersion>4.0.0</modelVersion>

  <parent>
    <groupId>com.jpexposito.codelearn</groupId>
    <artifactId>codeLearn-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <relativePath>../pom.xml</relativePath>
  </parent>

  <artifactId>codeLearn-adapter-persistence</artifactId>
  <packaging>jar</packaging>

  <dependencies>
    <dependency>
      <groupId>com.jpexposito.codelearn</groupId>
      <artifactId>codeLearn-service</artifactId>
      <version>${project.version}</version>
    </dependency>

    <!-- API normalmente proporcionada por WildFly -->
    <dependency>
      <groupId>jakarta.persistence</groupId>
      <artifactId>jakarta.persistence-api</artifactId>
      <version>3.1.0</version>
      <scope>provided</scope>
    </dependency>

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
    </dependency>

    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>

</project>
```

> Como puedes observar existe un error, y es que la versión de la librería dentro de este módulo (`3.1.0`). La versión de la librería se debe de hacer como el resto de librerias. Así que se debe de corregir.

---

### `codeLearn-adapter-audit/pom.xml` (OUT auditoría)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelVersion>4.0.0</modelVersion>

  <parent>
    <groupId>com.jpexposito.codelearn</groupId>
    <artifactId>codeLearn-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <relativePath>../pom.xml</relativePath>
  </parent>

  <artifactId>codeLearn-adapter-audit</artifactId>
  <packaging>jar</packaging>

  <dependencies>

    <!-- Las dependencias que fueran necesarias. Por ejemplo la de logs-->

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
    </dependency>
  </dependencies>

</project>
```

---

### `codeLearn-rest/pom.xml` (IN REST)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelVersion>4.0.0</modelVersion>

  <parent>
    <groupId>com.jpexposito.codelearn</groupId>
    <artifactId>codeLearn-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <relativePath>../pom.xml</relativePath>
  </parent>

  <artifactId>codeLearn-rest</artifactId>
  <packaging>jar</packaging>

  <dependencies>
    <!-- IN -> CORE -->
    <dependency>
      <groupId>com.jpexposito.codelearn</groupId>
      <artifactId>codeLearn-service</artifactId>
      <version>${project.version}</version>
    </dependency>

    <!-- JAX-RS API: WildFly -->
    <dependency>
      <groupId>jakarta.ws.rs</groupId>
      <artifactId>jakarta.ws.rs-api</artifactId>
      <version>3.1.0</version>
      <scope>provided</scope>
    </dependency>

    <!-- CXF (si aplica) -->
    <dependency>
      <groupId>org.apache.cxf</groupId>
      <artifactId>cxf-rt-frontend-jaxrs</artifactId>
    </dependency>
    <dependency>
      <groupId>org.apache.cxf</groupId>
      <artifactId>cxf-rt-transports-http</artifactId>
    </dependency>

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
    </dependency>
  </dependencies>

</project>
```

> Se repite el `error`, y es que existe una librería dentro de este módulo con versión (`3.1.0`, `jakarta.ws.rs-api`). Se debe de hacer lo mismo que en el resto, suber las versiones al `pom.xml` del `parent`. Así que se debe de corregir.

---

## 3.7 `codeLearn-war/pom.xml` (WAR ensamblador)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelVersion>4.0.0</modelVersion>

  <parent>
    <groupId>com.jpexposito.codelearn</groupId>
    <artifactId>codeLearn-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <relativePath>../pom.xml</relativePath>
  </parent>

  <artifactId>codeLearn-war</artifactId>
  <packaging>war</packaging>

  <dependencies>
    <!-- CORE -->
    <dependency>
      <groupId>com.jpexposito.codelearn</groupId>
      <artifactId>codeLearn-service</artifactId>
      <version>${project.version}</version>
    </dependency>

    <!-- IN REST -->
    <dependency>
      <groupId>com.jpexposito.codelearn</groupId>
      <artifactId>codeLearn-rest</artifactId>
      <version>${project.version}</version>
    </dependency>

    <!-- OUT -->
    <dependency>
      <groupId>com.jpexposito.codelearn</groupId>
      <artifactId>codeLearn-adapter-persistence</artifactId>
      <version>${project.version}</version>
    </dependency>
    <dependency>
      <groupId>com.jpexposito.codelearn</groupId>
      <artifactId>codeLearn-adapter-audit</artifactId>
      <version>${project.version}</version>
    </dependency>

    <!-- APIs provided -->
    <dependency>
      <groupId>jakarta.ws.rs</groupId>
      <artifactId>jakarta.ws.rs-api</artifactId>
      <version>3.1.0</version>
      <scope>provided</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
      </plugin>
    </plugins>
  </build>

</project>
```

> Mismo error que en el anterior y se debe de correggir de la misma forma.


## Paso a paso: cómo construir la solución 

La idea aquí es contruir el `build `y `ver` la herencia de Maven.

### Paso 0 — Prerrequisitos

- Java 17 (o el `release` que pongas en el parent)
- Maven 3.9+
- (Opcional) WildFly instalado si vas a desplegar

### Paso 1 — Construir todo desde el parent

Desde la carpeta `codeLearn-parent/`:

```bash
mvn -DskipTests clean install
```

Qué ocurre:

- Maven lee `codeLearn-parent/pom.xml`
- detecta los `<modules>`, si estuviera definido
- construye en orden: `service` → `adapter` (y submódulos) → `rest` → `war`
- instala los artefactos en el repositorio local (`~/.m2`)

### Paso 2 — Ver el *effective pom* (demostrar herencia)

Para ver qué valores “finales” hereda un módulo (versiones, plugins, properties):

```bash
mvn -pl codeLearn-war help:effective-pom
```

Busca en la salida:
- versiones que no escribiste en el WAR pero aparecen (heredadas)
- plugins con versión fija (desde `pluginManagement`)

### Paso 3 — Ver el árbol de dependencias del WAR (demostrar ensamblado)

```bash
mvn -pl codeLearn-war dependency:tree
```

Comprueba que cuelgan:

- `codeLearn-rest`
- `codeLearn-adapter-persistence`
- `codeLearn-adapter-audit`
- `codeLearn-service`

### Paso 4 — Construir solo el WAR (cuando iteras rápido)

```bash
mvn -pl codeLearn-war -am clean package
```

`-am` (“also make”) hace que Maven construya también lo necesario (sus dependencias del reactor).

El WAR quedará en:

```bash
codeLearn-war/target/codeLearn-war-1.0.0-SNAPSHOT.war
```

### Paso 5 — Desplegar en WildFly

- Copiar el WAR al directorio de deployments de WildFly:
  
  ```bash
  cp codeLearn-war/target/*.war $WILDFLY_HOME/standalone/deployments/
  ```

---

### 6) Problemas típicos y cómo diagnosticarlos

## “Me falta una versión en una dependencia”
- Solución: añade la dependencia a `dependencyManagement` del parent o especifica `<version>` en el módulo.

## “En WildFly tengo conflictos de librerías”
- Revisa `scope=provided` para APIs Jakarta
- Usa `jboss-deployment-structure.xml` si necesitas excluir módulos del server

## “El WAR no encuentra mis resources REST”
- Asegúrate de que `codeLearn-rest` está como dependencia del WAR
- Asegúrate de que el bootstrap JAX-RS/CXF en el WAR registra el paquete de resources

## Resumen

<img src=images/resumen-arquitectura.png width=400/>


</div>