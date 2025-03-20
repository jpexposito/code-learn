<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Clases Estáticas)

## ¿Qué es una Clase Estática?

En Java, una **clase estática** es una clase que se define dentro de otra clase y está marcada con la palabra clave `static`. Las clases estáticas tienen las siguientes características:

1. **No necesita una instancia de la clase externa**: Puedes crear una instancia de la clase estática sin necesidad de crear una instancia de la clase externa.
2. **Acceso limitado a miembros no estáticos**: Solo puede acceder a los miembros estáticos de la clase externa.
3. **Uso común**: Se utilizan para agrupar clases relacionadas o para crear clases de utilidad.

## Ejemplo con `Properties`

El siguiente ejemplo muestra cómo utilizar una clase estática para manejar la configuración de una aplicación utilizando la clase `Properties` de Java.

```properties
Database URL: jdbc:mysql://localhost:3306/mydb
Database User: root
```

### Código

```java
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    /** 
     * Clase estatica interna para manejar la configuracion
     **/

    public static class Config {
        private static final Properties properties = new Properties();

        static {
            try {
                properties.load(new FileInputStream("config.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         *  Metodo estatico para obtener una propiedad
         **/
        public static String getProperty(String key) {
            return properties.getProperty(key);
        }
    }
}
```

## Compartir Clases Estáticas y Recursos entre Clases

Cuando defines una clase estática en Java, puedes acceder a sus métodos y propiedades desde cualquier otra clase sin necesidad de crear una instancia de la clase contenedora. Esto es especialmente útil para compartir recursos comunes, como configuraciones, utilidades o constantes.

```java
public class MainClass {
    public static void main(String[] args) {
        String dbUrl = ConfigManager.Config.getProperty("database.url");
        String dbUser = ConfigManager.Config.getProperty("database.user");

        System.out.println("Database URL: " + dbUrl);
        System.out.println("Database User: " + dbUser);
    }
}
```

```java
public class OtraClase {
    public void mostrarConfiguracion() {
        String dbPassword = ConfigManager.Config.getProperty("database.password");
        System.out.println("Database Password: " + dbPassword);
    }
}
```

</div>