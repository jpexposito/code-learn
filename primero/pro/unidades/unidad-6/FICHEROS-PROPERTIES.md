<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Trabajo con Ficheros `.properties` en Java)

Los archivos **`.properties`** en Java se utilizan para almacenar configuraciones en un formato clave-valor. Son ampliamente usados para **configuración de aplicaciones**, **internacionalización (i18n)** y **almacenamiento de datos ligeros**.

---

## Conceptos Básicos

Un archivo `.properties` tiene la estructura de **clave=valor**, donde cada línea representa una configuración. Por ejemplo:

```properties
servidor=localhost
puerto=8080
usuario=admin
```

Java proporciona la clase `Properties` en el paquete `java.util` para manejar estos archivos.

## Operaciones con `.properties`

### Crear y Guardar un Archivo `.properties`

Para almacenar configuraciones en un archivo `.properties`, usamos `store()`.

### Leer un Archivo `.properties`

Para leer un archivo `.properties`, usamos `load()`.

### Eliminar una Propiedad

Podemos eliminar una clave usando `remove()` y luego guardar los cambios.

### Internacionalización (i18n)

Los archivos `.properties` permiten gestionar distintos idiomas en una aplicación mediante `ResourceBundle`.

###Cargar un Archivo `.properties` desde el Classpath
Si el archivo `.properties` está dentro del `classpath`, lo cargamos con `ClassLoader`.

## Beneficios del Uso de `.properties`

✔ Separación de configuración y código  
✔ Facilidad para modificar y eliminar propiedades  
✔ Soporte para internacionalización (i18n)  
✔ Posibilidad de cargarlos desde el `classpath`  

## Ejemplos en Java

### **Crear y Guardar un Archivo `.properties`**

```java
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class CrearProperties {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("servidor", "localhost");
        properties.setProperty("puerto", "8080");
        properties.setProperty("usuario", "admin");

        try (FileOutputStream output = new FileOutputStream("config.properties")) {
            properties.store(output, "Configuración del Servidor");
            System.out.println("Archivo properties guardado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Leer un Archivo .properties

```java
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LeerProperties {
    public static void main(String[] args) {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            System.out.println("Servidor: " + properties.getProperty("servidor"));
            System.out.println("Puerto: " + properties.getProperty("puerto"));
            System.out.println("Usuario: " + properties.getProperty("usuario"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Eliminar una Propiedad

```java
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class EliminarPropiedad {
    public static void main(String[] args) {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        properties.remove("usuario");

        try (FileOutputStream output = new FileOutputStream("config.properties")) {
            properties.store(output, "Configuración actualizada");
            System.out.println("Propiedad eliminada y archivo actualizado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## Internacionalización

```java
import java.util.Locale;
import java.util.ResourceBundle;

public class Internacionalizacion {
    public static void main(String[] args) {
        Locale localeES = new Locale("es");
        Locale localeEN = new Locale("en");

        ResourceBundle mensajesES = ResourceBundle.getBundle("mensajes", localeES);
        ResourceBundle mensajesEN = ResourceBundle.getBundle("mensajes", localeEN);

        System.out.println("Español: " + mensajesES.getString("saludo"));
        System.out.println("Inglés: " + mensajesEN.getString("saludo"));
    }
}
```

### Ficheros

#### .properties: mensajes_es.properties

```properties
saludo=Hola, bienvenido
despedida=Adiós, hasta luego
```

#### .properties: mensajes_en.properties

```properties
saludo=Hello, welcome
despedida=Goodbye, see you later
```

## Referencias

- 📖 [Documentación de Properties en Java](https://docs.oracle.com/javase/8/docs/api/java/util/Properties.html)
- 📖 [Internacionalización con ResourceBundle](https://docs.oracle.com/javase/tutorial/i18n/resbundle/index.html)


</div>