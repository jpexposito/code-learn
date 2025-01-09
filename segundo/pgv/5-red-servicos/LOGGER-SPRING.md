<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios (Logger en Servicios y clases))

<div align="center">
   <img src=images/spring-logo.png width="400">
</div>


## Introducción

Un **logger** es una herramienta utilizada en desarrollo de software para registrar eventos, errores, información de ejecución y otros mensajes importantes que ocurren dentro de una aplicación. En aplicaciones Java, se utiliza principalmente para depurar, monitorear y auditar el comportamiento de la aplicación en tiempo de ejecución.

En Java, existen varias bibliotecas de logging que proporcionan APIs estándar para registrar estos eventos. Entre las más populares se encuentran **Log4j**, **SLF4J**, y **java.util.logging**.

## Propósito del Logger

El principal propósito de un logger es proporcionar un mecanismo eficiente y flexible para manejar los registros en una aplicación. Algunos de los usos más comunes son:

- **Depuración**: Permite seguir el flujo de ejecución de la aplicación y ver los datos relevantes en tiempo de ejecución.
- **Monitoreo**: Permite identificar problemas de rendimiento, cuellos de botella o cualquier anomalía.
- **Auditoría**: Registra eventos significativos para auditar el comportamiento del sistema o las acciones de los usuarios.
- **Manejo de errores**: Almacena errores y excepciones para poder analizar y corregir los problemas.

## Bibliotecas de Logging en Java

### 1. **Log4j**

Log4j es una de las bibliotecas de logging más conocidas en Java. Fue creada por la Apache Software Foundation y proporciona una solución robusta para registrar eventos en aplicaciones.

#### Características

- Permite configuraciones flexibles para la salida de logs (consola, archivo, base de datos, etc.).
- Niveles de log detallados (TRACE, DEBUG, INFO, WARN, ERROR, FATAL).
- Soporta múltiples appenders (salidas) y loggers jerárquicos.
- Rendimiento eficiente incluso en aplicaciones de alto tráfico.

#### Configuración básica de Log4j

```xml
<!-- log4j2.xml -->
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} %-5p [%t] %c{1}:%L - %m%n"/>
        </Console>
        <File name="File" fileName="app.log">
            <PatternLayout>
                <Pattern>%d{ISO8601} %-5p %c{1}:%L - %m%n</Pattern>
            </PatternLayout>
        </File>
    </Appenders>
    <Loggers>
        <Root level="debug">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="File"/>
        </Root>
    </Loggers>
</Configuration>
```

### 2. **SLF4J**

SLF4J (Simple Logging Facade for Java) no es una implementación de logging en sí misma, sino una interfaz que se puede usar con diferentes bibliotecas de logging, como Log4j, Logback, y java.util.logging. Su objetivo es proporcionar una fachada de logging común que se pueda utilizar en cualquier tipo de proyecto Java.

#### Características

- Proporciona una API sencilla y flexible.
- Permite cambiar de implementación de logging sin modificar el código fuente.
- Combinado con Logback, es una de las soluciones más utilizadas en aplicaciones Java modernas.

Un ejemplo de uso es el siguiente:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiServicio {

   private static final Logger logger = LoggerFactory.getLogger(MiServicio.class);

   public void realizarAccion() {
      logger.debug("Este es un mensaje de depuracion");
```

## Niveles de Logging

La mayoría de las bibliotecas de logging en Java utilizan un esquema común de niveles, que ayudan a clasificar la severidad y el propósito de los mensajes registrados. Los niveles de logging más comunes son los siguientes:

- **TRACE**: 
  - El nivel más detallado. Se utiliza para registrar información muy granular, como pasos individuales en el flujo de la aplicación. 
  - Usualmente se emplea durante el desarrollo o la depuración.

- **DEBUG**:
  - Usado para mensajes de depuración. Ayuda a los desarrolladores a rastrear el flujo de ejecución de la aplicación y verificar valores intermedios durante el proceso.

- **INFO**:
  - Registra información general sobre el estado o progreso de la aplicación. No se trata de un evento crítico, pero sí de algo relevante para el funcionamiento normal de la aplicación.

- **WARN**:
  - Indica una posible anomalía o situación que podría convertirse en un problema en el futuro, pero que no afecta el funcionamiento actual de la aplicación. Las advertencias deben ser monitorizadas.

- **ERROR**:
  - Se usa para registrar errores que ocurren durante la ejecución de la aplicación, que pueden afectar el flujo normal de trabajo. Aunque no siempre detienen la ejecución, requieren atención.

- **FATAL**:
  - El nivel más grave. Se utiliza para registrar errores críticos que generalmente resultan en la detención de la aplicación o servicio.

---

## Buenas Prácticas para el Uso de Loggers

1. **No loguear información sensible**:
   - Evita registrar datos sensibles como contraseñas, claves API, números de tarjeta de crédito, o cualquier tipo de información personal identificable (PII).

2. **Uso adecuado de los niveles de log**:
   - Utiliza **DEBUG** y **TRACE** para la depuración y desarrollo.
   - Usa **INFO** para eventos que son parte del flujo normal de la aplicación.
   - Registra **WARN** para situaciones potencialmente problemáticas que merecen atención.
   - Emplea **ERROR** para errores importantes que afectan el comportamiento de la aplicación.
   - Usa **FATAL** solo para los errores más críticos que resultan en la detención de la aplicación.

3. **Configuración externa**:
   - Configura los logs a través de archivos de configuración externos (como `log4j2.xml`, `logback.xml`, o `logging.properties`), lo que permite cambiar el comportamiento de logging sin modificar el código fuente.

4. **Evitar el uso excesivo de logs en producción**:
   - En ambientes de producción, enfócate en registrar solo los niveles **ERROR** y **WARN** para evitar una sobrecarga de I/O y la creación de archivos de log demasiado grandes.

5. **Formato y claridad**:
   - Utiliza un formato claro y consistente para los logs. Es recomendable incluir siempre el timestamp, el nivel del log, el nombre del logger (clase o componente) y un mensaje claro. Ejemplo:
     ```
     2025-01-09 14:25:00 INFO [MiServicio] - Acción realizada correctamente.
     ```

---

## Conclusión

El uso adecuado de un sistema de logging es esencial para el monitoreo, depuración y mantenimiento de aplicaciones Java. Escoger una biblioteca de logging adecuada (como Log4j, SLF4J con Logback o java.util.logging) y configurar los logs de manera eficiente ayuda a mantener la visibilidad de los eventos clave dentro de la aplicación, facilita la detección de errores y problemas de rendimiento, y permite auditar las acciones del sistema.

Al seguir las buenas prácticas y elegir los niveles adecuados para cada tipo de mensaje, se garantiza que los logs sean útiles tanto para los desarrolladores como para los administradores del sistema. Un sistema de logging bien diseñado y bien mantenido contribuye a la fiabilidad y facilidad de mantenimiento de la aplicación a largo plazo.

> [Ejemplo Logger en Spring]([SPRING-REST.md](https://github.com/jpexposito/spring-boot-persistence-h2/blob/logger/src/main/java/es/system/jpexposito/springboot/service/UserService.java)).

</div>

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>