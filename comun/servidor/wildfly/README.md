<div align="justify">

# Construyendo una aplicación modular en arquitectura hexagonal sobre wildfly.

<div align="center">
  <img src=images/wildfly.png width="200">
</div>

## ¿Qué es WildFly?

WildFly es un **servidor de aplicaciones Java empresarial (Application
Server)** open source desarrollado por Red Hat. Está diseñado para
ejecutar aplicaciones basadas en **Jakarta EE** en entornos de
producción.

No es solo un servidor web: es una plataforma completa que proporciona
servicios empresariales integrados como transacciones, seguridad,
persistencia, mensajería y más.

En pocas palabras:

> WildFly es una plataforma completa para ejecutar aplicaciones Java
> empresariales.

------------------------------------------------------------------------

## 📁 Estructura principal

```
wildfly/
├── appclient/
├── bin/
├── docs/
├── domain/
├── modules/
├── standalone/
├── welcome-content/
└── README.txt
```

---

### 📂 bin/ — Scripts ejecutables

Contiene los scripts para arrancar, detener y administrar el servidor. Vamos a tratar sólo lo más importante.

- `standalone.sh / .bat` → Arranque en modo standalone
- `jboss-cli.sh / .bat` → Consola de administración por línea de comandos
- `add-user.sh / .bat` → Crear usuarios de administración

> Es la carpeta principal para operaciones y administración.

---

### 📂 standalone/ — Servidor independiente

Se usa cuando WildFly funciona como una sola instancia.

```
standalone/
├── configuration/
├── data/
├── deployments/
├── log/
└── tmp/
```

### configuration/
Archivos de configuración del servidor standalone.

Ejemplos:

- `standalone.xml` → Configuración básica
El resto de configuraciones no suele utilizarse, dado que son extensiones de `standalone`:
    - `standalone-full.xml` → Incluye mensajería
    - `standalone-ha.xml` → Alta disponibilidad
    - `standalone-full-ha.xml` → HA + mensajería

> Aquí se definen subsistemas, puertos, seguridad, datasources, etc.

### data/
Datos internos persistentes del servidor:

- Estado de subsistemas
- Mensajería JMS
- Timers EJB
- Cachés

> No se deben modificar manualmente.

### tmp/

Archivos temporales generados durante la ejecución.

### deployments/
Carpeta para desplegar aplicaciones:
- `.war`. Vamos a desplegar aplicaciones que contienen `librerías de terceros (spring, hibernate, etc)` y nuestros propios `micro servicios en diferentes jar`.  

WildFly detecta automáticamente nuevos despliegues.

Archivos marcadores:

- `.dodeploy`
- `.deployed`
- `.failed`
- `.undeployed`

### log/
Archivos de logs del servidor.

Principal:

```
server.log
```

### configuration/

- `domain.xml` → Configuración global
- `host.xml` → Configuración del host físico

> Generalmente estos ficheros los debe de configurar los administradores de sistemas.

---

## 📂 modules/ — Sistema de módulos

Contiene todas las librerías del servidor organizadas en módulos (JBoss Modules).

Ruta típica:

```
modules/system/layers/base/
```

Cada módulo incluye:

- Librerías JAR
- Archivo `module.xml` con dependencias

WildFly utiliza classloading modular en lugar de un classpath plano.

> Esta carpeta es importante porque vamos a utilizarla para configurar nuestro fichero de configuración, etc.


## ¿Qué proporciona WildFly de serie?

WildFly incluye implementaciones listas para usar de múltiples
especificaciones Jakarta EE:

-   Servidor HTTP/HTTPS
-   Persistencia JPA (Hibernate)
-   Transacciones distribuidas (JTA)
-   Servicios REST (JAX-RS)
-   Servicios SOAP (JAX-WS)
-   Mensajería (JMS)
-   Gestión de conexiones a bases de datos (DataSources)
-   Consola de administración web
-   Monitorización y gestión centralizada
-   ...

Todo esto funciona sin necesidad de agregar frameworks externos.

------------------------------------------------------------------------

## Frameworks externos

En WildFly, `para habilitar/deshabilitar frameworks` hemos de saber que:

1. Qué **subsistemas del servidor** están activos (Jakarta EE: JAX-RS, messaging, transactions, etc.).
2. Qué **módulos (JBoss Modules)** puede ver tu aplicación o cuáles se le excluyen.
3. Qué librerías empaquetas dentro de tu WAR/EAR frente a las que proporciona el servidor.

---

### Deshabilitar frameworks a nivel servidor (subsistemas)

WildFly se configura mediante perfiles en el fichero:

- `standalone/configuration/standalone.xml`

Ejemplos de subsistemas:

- JAX-RS
- Undertow (web/servlets)
- Messaging (ActiveMQ)
- Datasources
- Security
- Transactions

> Se configura poco o muy poco.

#### Habilitar/deshabilitar módulos para UNA aplicación

Es la forma más común cuando:

- Usas tus propias librerías (por ejemplo CXF o Spring)
- Quieres evitar conflictos con librerías del servidor

#### Archivo clave: jboss-deployment-structure.xml

Ubicación:

- WAR → `WEB-INF/jboss-deployment-structure.xml` de nuestro fichero `.war`

##### Excluir módulos del servidor

```xml
<jboss-deployment-structure>
  <deployment>
    <exclusions>
      <module name="org.jboss.resteasy.resteasy-jaxrs"/>
      <module name="org.jboss.resteasy.resteasy-jackson2-provider"/>
      <module name="org.glassfish.jaxb"/>
      <module name="org.jboss.ws.cxf.jbossws-cxf-client"/>
      <module name="org.jboss.ws.cxf.jbossws-cxf-server"/>
    </exclusions>
  </deployment>
</jboss-deployment-structure>
```

Esto `exluye las librerias propieas del servidor de aplicaciones` y **obliga** a usar las `librerías empaquetadas en tu aplicación`.

##### Añadir dependencias a módulos del servidor

```xml
<jboss-deployment-structure>
  <deployment>
    <dependencies>
      <module name="org.postgresql" services="import"/>
      <module name="org.slf4j"/>
    </dependencies>
  </deployment>
</jboss-deployment-structure>
```

---

### Módulos globales (para todas las aplicaciones)

Los módulos que se encuentran en la servidor `wildfly` se encuentra en:

```
WILDFLY_HOME/modules/system/layers/base/
```

Cada módulo contiene un archivo `module.xml`.

> **Nosotros podemos definir nuestros propios módulos**.

> Mas adeleante se describirá un ejemplo práctico.

---

## Para comenzar comienza descargando el servidor de apliaciones 

- [Wildfly-31.0.0](https://github.com/wildfly/wildfly/releases/download/31.0.0.Final/wildfly-31.0.0.Final.zip)


