<div align="justify">

<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios (Construcción de Servicios SOAP con Apache CXF))

<div align="center">
   <img src=images/spring-logo.png width="400">
</div>

Lo primero abordaremos los fundamentos teóricos necesarios para construir un servicio **SOAP** con **Apache CXF** y **Spring Boot** y luego proporcionaremos un ejemplo práctico paso a paso.

---

## **Fundamentos para Construir un Servicio SOAP**

**SOAP (Simple Object Access Protocol)** es un protocolo para intercambio de mensajes estructurados entre sistemas, normalmente sobre **HTTP** o **HTTPS**, usando **XML** como formato de representación.

Algunos conceptos clave:

- **Envelope**: Elemento raíz del mensaje SOAP, contiene `Header` (opcional) y `Body`.
- **Body**: Contiene la invocación a la operación (request) o la respuesta.
- **WSDL (Web Services Description Language)**: Documento XML que describe el **contrato** del servicio: operaciones, mensajes, tipos, protocolo, URL…
- **Binding**: Define cómo se mapean las operaciones a un protocolo concreto (por ejemplo HTTP + SOAP 1.1/1.2).

### ¿Qué es Apache CXF?

**Apache CXF** es un framework para construir servicios web **SOAP** y **REST** en Java. Proporciona soporte para JAX-WS (SOAP) y JAX-RS (REST), integración con Spring, manejo de WSDL, generación de código, interceptores, seguridad, etc.

En este documento lo usaremos para exponer un **servicio SOAP (JAX‑WS)** dentro de una aplicación Spring Boot.

### ¿Qué es Spring Boot en este contexto?

**Spring Boot** nos permite levantar rápidamente una aplicación Java con un servidor embebido (Tomcat/Jetty/Undertow) y auto‑configuración de muchos componentes, entre ellos el **starter de Apache CXF para JAX‑WS**: `cxf-spring-boot-starter-jaxws`.

```xml
<properties>
		<cxf-spring-boot-starter-jaxws.version>4.1.4</cxf-spring-boot-starter-jaxws.version>
</properties>

<dependency>
   <groupId>org.apache.cxf</groupId>
   <artifactId>cxf-spring-boot-starter-jaxws</artifactId>
   <version>${cxf-spring-boot-starter-jaxws.version}</version> 
</dependency>
```

> **Nota**: Alinea la versión de cxf con la versión de **Tomcat** _(9/10)_, ya que es el servidor de aplicaciones que levanta ___Spring Boot___ en el entorno de desarrollo.

---

## **Arquitectura de un Servicio SOAP con CXF + Spring Boot**

1. **Contrato (WSDL/XSD)**  
   Define las operaciones, mensajes, tipos y endpoint del servicio.

2. **Interfaz de Servicio (Service Endpoint Interface)**  
   Una interfaz Java anotada con `@WebService` que representa el contrato.

3. **Implementación del Servicio**  
   Clase Java que implementa la interfaz y contiene la lógica de negocio.

4. **Configuración del Endpoint CXF**  
   Clase `@Configuration` de Spring que registra el endpoint en una URL concreta.

5. **Modelo de Datos**  
   Clases Java (POJOs) que representan las peticiones/respuestas; pueden generarse a partir de WSDL/XSD o escribirse a mano (code‑first).

6. **Cliente SOAP** (externo)  
   Otro sistema, Postman/SOAP UI, etc., que consume el WSDL y envía/recibe mensajes SOAP.

---

## **Ventajas de Usar Apache CXF + Spring Boot para SOAP**

1. **Integración sencilla con Spring Boot** gracias al starter `cxf-spring-boot-starter-jaxws`.  
2. **Auto‑configuración del servlet de CXF** bajo un path configurable (por defecto `/services/*`).  
3. **Soporte completo de JAX‑WS** (SOAP 1.1/1.2, WSDL 1.1, handlers, interceptors…).  
4. **Generación de código desde WSDL/XSD** mediante plugins de Maven (enfoque *contract‑first*).  
5. **Facilidad de despliegue**: aplicación Spring Boot empaquetada en un único `jar` ejecutable.

---

## **Flujo Básico para Crear un Servicio SOAP con CXF + Spring Boot**

1. **Configuración del proyecto**: crear proyecto Spring Boot y añadir dependencias de CXF.  
2. **Elegir enfoque**:  
   - **Code‑first**: primero defines las clases Java, CXF genera el WSDL.  
   - **Contract‑first**: primero defines WSDL/XSD y generas las clases con un plugin Maven.  
3. **Definir la interfaz del servicio (`@WebService`)**.  
4. **Implementar la lógica del servicio**.  
5. **Registrar el endpoint CXF en Spring (`EndpointImpl`)**.  
6. **Configurar propiedades (`application.properties`)** y puerto.  
7. **Probar el servicio con SOAP UI / Postman (modo SOAP)**.

En el ejemplo práctico usaremos el enfoque **code‑first**, que es más rápido para aprender.

---

## **Ejemplo Práctico: Construcción de un Servicio SOAP con Apache CXF y Spring Boot**

Vamos a construir un servicio SOAP muy sencillo de ejemplo: **`HelloService`**, con una única operación `sayHello(name)` que devuelve un saludo personalizado.

### Paso 1: Configuración del Proyecto

#### Usar Spring Initializr

1. Visita [Spring Initializr](https://start.spring.io/).
2. Configura el proyecto:
   - **Tipo**: Maven.
   - **Lenguaje**: Java.
   - **Versión de Spring Boot**: última estable (por ejemplo 3.x).
   - **Dependencias mínimas**:
     - **Spring Web** (para tener servlet container y soporte web).
   - Descarga el proyecto generado y ábrelo en tu IDE.

#### Añadir dependencia de Apache CXF (JAX‑WS)

Edita el `pom.xml` y añade la dependencia del **starter oficial de CXF para JAX‑WS**:

```xml
<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-spring-boot-starter-jaxws</artifactId>
    <version>4.1.4</version>
</dependency>
```

> ⚠️ Revisa la **última versión estable** en Maven Central: `org.apache.cxf:cxf-spring-boot-starter-jaxws`.

Opcionalmente, puedes añadir:

- `spring-boot-starter-actuator` (monitorización)
- `spring-boot-starter-validation` (validación de datos)
- Dependencias de logging adicionales si lo necesitas.

---

### Paso 2: Definir la Interfaz del Servicio (code‑first)

Creamos una interfaz Java anotada con **JAX‑WS** (`@WebService` y `@WebMethod`).

```java
package es.system.jpexposito.cxf.hello.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService(
    targetNamespace = "http://jpexposito.es/ws/hello",
    name = "HelloPortType"
)
public interface HelloService {

    @WebMethod(operationName = "sayHello")
    String sayHello(String name);
}
```

- `@WebService` indica que esta interfaz es un servicio SOAP.  
- `targetNamespace` y `name` se reflejarán en el WSDL.  
- `@WebMethod` marca los métodos que se expondrán como operaciones SOAP.

---

### Paso 3: Implementar el Servicio

Creamos la clase que implementa la interfaz. Puede estar anotada con `@WebService` y/o gestionarse como un bean de Spring (`@Service` o `@Component`).

```java
package es.system.jpexposito.cxf.hello.service.impl;

import es.system.jpexposito.cxf.hello.service.HelloService;
import jakarta.jws.WebService;
import org.springframework.stereotype.Service;

@Service
@WebService(
    serviceName = "HelloService",
    portName = "HelloPort",
    targetNamespace = "http://jpexposito.es/ws/hello",
    endpointInterface = "es.system.jpexposito.cxf.hello.service.HelloService"
)
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        if (name == null || name.isBlank()) {
            return "Hola desconocido";
        }
        return "Hola " + name + ", bienvenido a Apache CXF + Spring Boot";
    }
}
```

Puntos clave:

- `endpointInterface` enlaza la implementación con la interfaz `HelloService`.
- Al estar anotada con `@Service`, Spring la detectará automáticamente como bean.

---

### Paso 4 y 5: Configurar CXF y Publicar el Endpoint

En `src/main/resources/application.properties` podemos configurar el puerto y el path de CXF:

```properties
# Puerto del servidor
server.port=8080

# Path base del servlet de CXF (por defecto suele ser /services)
cxf.path=/services

# Nivel de log para ver las peticiones/respuestas SOAP (opcional)
logging.level.org.apache.cxf=INFO
```

Si quieres ver el XML de las peticiones y respuestas, puedes después añadir interceptores de logging de CXF, pero para un primer ejemplo suele bastar con logs básicos.

```text
http://localhost:8080/services/hello?wsdl
```

---

### Paso 6: Clase Principal de Spring Boot

La típica clase de arranque de Spring Boot apenas cambia:

```java
package es.system.jpexposito.cxf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CxfSoapApplication {

    public static void main(String[] args) {
        SpringApplication.run(CxfSoapApplication.class, args);
    }
}
```

Al ejecutar esta clase:

- Se inicia la aplicación Spring Boot.  
- CXF registra el servlet en `/services/*`.  
- Nuestro endpoint se publica en `/services/hello`.  
- El WSDL está disponible en `http://localhost:8080/services/hello?wsdl`.

---

### Paso 7: Probar el Servicio SOAP

Con la aplicación en ejecución:

1. Abre en el navegador:  
   `http://localhost:8080/services/hello?wsdl`  
   Deberías ver el WSDL generado por CXF.
2. Importa esa URL en **SOAP UI** (o similar) para generar una petición de prueba.
3. Ejemplo de mensaje SOAP de petición:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:hel="http://jpexposito.es/ws/hello">
    <soapenv:Header/>
    <soapenv:Body>
        <hel:sayHello>
            <arg0>Juan</arg0>
        </hel:sayHello>
    </soapenv:Body>
</soapenv:Envelope>
```

La respuesta esperada será algo como:

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:sayHelloResponse xmlns:ns2="http://jpexposito.es/ws/hello">
         <return>Hola Juan, bienvenido a Apache CXF + Spring Boot</return>
      </ns2:sayHelloResponse>
   </soap:Body>
</soap:Envelope>
```

---

## **(Opcional) Enfoque Contract‑First con WSDL/XSD + Plugin Maven**

En escenarios de integración con sistemas legados es habitual partir de un **WSDL** existente.

En ese caso:

1. Colocas el WSDL y XSDs en `src/main/resources/wsdl` (por ejemplo).  
2. Configuras un plugin Maven de CXF o `cxf-codegen-plugin` para generar las clases Java (stubs) a partir del WSDL.  
3. Implementas las interfaces generadas y las registras como endpoints igual que en el ejemplo anterior.

Este enfoque garantiza que el contrato WSDL sea la “fuente de verdad” del servicio.

---

## **Resumen de los Pasos Clave**

1. Crear proyecto Spring Boot y añadir `cxf-spring-boot-starter-jaxws`.  
2. Definir interfaz JAX‑WS (`@WebService`) y su implementación.  
3. Registrar endpoint en `CxfConfig` usando `EndpointImpl`.  
4. Configurar `cxf.path` y el puerto en `application.properties`.  
5. Levantar la aplicación y probar la URL del WSDL.  
6. Consumir el servicio desde SOAP UI u otro cliente SOAP.

> La implementación completa del servicio se encuentra en las siguientes url:
> - [Servicio](https://github.com/jpexposito/spring-boot-persistence-h2).
> - [Clente Servicio](https://github.com/jpexposito/spring-boot-persistence-h2-client).

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>