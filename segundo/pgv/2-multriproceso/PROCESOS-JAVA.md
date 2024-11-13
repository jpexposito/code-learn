<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios)

## Creación rápida de procesos con Java con Runtime

<div align="center">

<img src=images/procesos.png width="400">
</div>

### Creación rápida de procesos

La clase __java.lang.Runtime__ se usa principalmente para interactuar con el _JRE de Java_. Esta clase proporciona métodos para lanzar procesos, llamar al recolector de basura (__Garbage Collector__), saber la _cantidad de memoria disponible y libre, etc_.

[Especificación java.lang.Runtime](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Runtime.html)

Cada aplicación en Java tiene acceso a una única instancia de _java.lang.Runtime_ a través del método __Runtime.getRuntime()__ que devuelve la instancia singleton de la clase __Runtime__.

> Patrones de diseño: [Singleton](PATRON-SINGLETON.md)
> 
> __¿Qué son los patrones de diseño? ¿Qué es y para qué se usa el patrón de diseño singleton?__
>
> Investiga cómo realizar una clase que siga el patrón de diseño singleton.
> [Diseño basado en patrones](https://refactoring.guru/es/design-patterns/java).

El método que nos interesa a nosotros para la creación de procesos es

```java
public Process exec(String command) throws IOException
```

Veamos un ejemplo sencillo de uso de este método

```java
public static void main(String[] args) throws IOException {
    // Launch notepad app
    Runtime.getRuntime().exec("notepad.exe");
}
```

Se puede observar que en el parámetro que pasamos al método exec __indicamos el programa que queremos ejecutar__. En este caso, como el __notepad__ se encuentra en el __PATH__ del sistema, no es necesario indicar la ruta donde se encuentra el programa. _En otro caso, sí tendríamos que hacerlo_.

### Propiedades del sistema y comandos del sistema

Si tenemos pensado desarrollar aplicaciones que funcionen en diferentes SO tendremos que enfrentarnos a la problemática del funcionamiento diferente de los distintos SO.

Vamos a ver algunos ejemplos que pueden servir como guía para otros problemas similares a los expuestos.

>💡File separator
>
>Para indicar las rutas en un sistema los sistemas UNIX emplean el caracter / como separador mientras que los sistemas Windows usan el caracter \ . En resumen, / en *X y \ en Windows.
>
>¿Cómo podemos hacer entonces que nuestras aplicaciones sean independientes del SO en el que se ejecutan?
>
>Para este tipo de cuestiones vamos a utilizar de forma recurrente las propiedades del sistema mediante System.getProperty(String propName). Estas propiedades se configuran con el propio sistema operativo, aunque las podemos modificar usando los parámetros de ejecución de la máquina virtual
>
>String separator = System.getProperty("file.separator");
>
>o
>
>-Dfile.separator
>
>Aunque siempre es una buena práctica usar el caracter / en las rutas ya que Java es capaz de convertirlas al sistema en el que se ejecuta.

Si lo que queremos es ejecutar un comando del SO, tenemos que hacerlo, al igual que si lo hacemos manualmente, a través del shell del sistema, donde volvemos a encontrar la dicotomía entre sistemas UNIX y sistemas Windows.

Vamos a ver el código que, a través de las propiedades del sistema, nos permite obtener un listado de los archivos existentes en la carpeta personal del usuario.

```java
// Primero obtenemos la carpeta del usuario
String homeDirectory = System.getProperty("user.home");
boolean isWindows = System.getProperty("os.name")
  .toLowerCase().startsWith("windows");

if (isWindows) {
    Runtime.getRuntime()
      .exec(String.format("cmd.exe /c dir %s", homeDirectory));
} else {
    Runtime.getRuntime()
      .exec(String.format("sh -c ls %s", homeDirectory));
}
```

## ProcessBuilder y Process

Java proporciona las clases ___ProcessBuilder y Process__ para gestionar procesos del sistema operativo desde aplicaciones Java. Estas clases permiten ejecutar _programas externos, capturar su salida, gestionar su entrada y modificar su comportamiento_.

### Preparación y Configuración de un Proceso

#### Crear un Proceso con ProcessBuilder

Para ejecutar un comando del sistema operativo, puedes usar ProcessBuilder. Aquí un ejemplo que ejecuta el comando ping desde Java:

```java
import java.io.IOException;

public class EjecutarProceso {
    public static void main(String[] args) {
        // Crear un nuevo proceso que ejecuta el comando 'ping'
        ProcessBuilder pb = new ProcessBuilder("ping", "-c", "3", "google.com");

        try {
            // Iniciar el proceso
            Process proceso = pb.start();
            
            // Esperar a que el proceso termine
            int exitCode = proceso.waitFor();
            System.out.println("Código de salida: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

## Transiciones entre estados

| Estado Actual                    | Evento                                         | Nuevo Estado                  | Correspondencia en Java (`ProcessBuilder`)                 |
|-----------------------------------------------------|------------------------------------------------|-------------------------------------------------|-------------------------------------------------------------|
| **Nuevo** (**New**)                               | Asignación de recursos                        | **Listo** (**Ready**)                          | Creación del `ProcessBuilder`                             |
| **Listo** (**Ready**)                             | Asignación de CPU por el planificador          | **En ejecución** (**Running**)                 | Llamada a `processBuilder.start()`                          |
| **En ejecución** (**Running**)                    | Espera de un evento externo                   | **Bloqueado** (**Blocked/Waiting**)             | Uso de `process.waitFor()` para esperar la terminación     |
| **En ejecución** (**Running**)                    | Interrupción para dar la CPU a otro proceso   | **Listo** (**Ready**)                          | `processBuilder.redirectOutput()` para redirigir salida y controlar el proceso |
| **Bloqueado** (**Blocked/Waiting**)                | Ocurre el evento esperado                     | **Listo** (**Ready**)                          | `process.waitFor()` para esperar eventos o resultados      |
| **En ejecución** (**Running**)                    | Terminación del proceso                       | **Terminado** (**Terminated/Exit**)            | `process.waitFor()` devuelve el código de salida           |
| **Listo** o **Bloqueado** (**Ready** or **Blocked/Waiting**) | Movimiento a memoria secundaria               | **Suspendido** (**Suspended**)                 | Java no maneja explícitamente el estado suspendido; el proceso debe ser reiniciado si es necesario |
| **Suspendido** (**Suspended**)                    | Regreso a memoria principal                   | **Listo** (**Ready**) o **Bloqueado** (**Blocked/Waiting**) | Java no maneja explícitamente el regreso a memoria; el proceso debe ser reiniciado si es necesario |


#### Modificar el Comando en Tiempo de Ejecución

Puedes modificar el comando dependiendo de las condiciones del sistema, por ejemplo, adaptando el comando según el sistema operativo.

```java
import java.io.IOException;

public class ModificarComando {
    public static void main(String[] args) {
        String sistemaOperativo = System.getProperty("os.name").toLowerCase();
        //DEBE DE EXISTIR LA VARIABLE DE ENTORNO
        ProcessBuilder pb;

        // Modificar el comando según el sistema operativo
        if (sistemaOperativo.contains("win")) {
            pb = new ProcessBuilder("cmd.exe", "/c", "dir"); // Comando para Windows
        } else {
            pb = new ProcessBuilder("ls", "-la"); // Comando para Linux/Mac
        }

        try {
            Process proceso = pb.start();
            int exitCode = proceso.waitFor();
            System.out.println("Código de salida: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

#### Configuraciones Adicionales de un Proceso

___ProcessBuilder___ permite configurar aspectos adicionales, como el directorio de trabajo o las variables de entorno del proceso.

```java
import java.io.IOException;
import java.util.Map;

public class ConfigurarProceso {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("printenv"); // Comando para listar variables de entorno

        // Establecer un directorio de trabajo
        pb.directory(new java.io.File("/tmp"));

        // Modificar variables de entorno
        Map<String, String> env = pb.environment();
        env.put("MY_VAR", "12345");

        try {
            Process proceso = pb.start();
            int exitCode = proceso.waitFor();
            System.out.println("Código de salida: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

### Acceso al Proceso una Vez en Ejecución

#### Capturar la Salida del Proceso

Puedes capturar la salida estándar ___(stdout)___ del proceso que has lanzado con ___ProcessBuilder___.

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CapturarSalidaProceso {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("ping", "-c", "3", "google.com");

        try {
            Process proceso = pb.start();

            // Capturar la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            // Esperar a que termine el proceso
            int exitCode = proceso.waitFor();
            System.out.println("Código de salida: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

#### Lanzar una Clase Java como Proceso Desde Otra Clase Java (IMP)

Es posible ejecutar una clase Java desde otra clase Java utilizando ___ProcessBuilder___.

___Primero___, creamos una clase simple llamada ___ClaseSecundaria.java___:

```java
public class ClaseSecundaria {
    public static void main(String[] args) {
        System.out.println("Hola desde ClaseSecundaria!");
    }
}
```

Luego, desde otra clase, puedes ejecutar ClaseSecundaria como un proceso:

```java
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EjecutarClaseJava {
    public static void main(String[] args) {
        // Comando para ejecutar una clase Java desde otra clase Java
        ProcessBuilder pb = new ProcessBuilder("java", "ClaseSecundaria");

        // Establecer el directorio donde se encuentra la clase compilada
        pb.directory(new java.io.File("./out/production/tu-proyecto"));

        try {
            Process proceso = pb.start();

            // Capturar la salida de la clase ejecutada
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            // Esperar a que termine el proceso
            int exitCode = proceso.waitFor();
            System.out.println("Código de salida: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

Si ejecutamos el código deberiamos de obtener:

```code
Hola desde ClaseSecundaria!
Código de salida: 0
```

> ___Este ejemplo muestra cómo puedes ejecutar la clase ClaseSecundaria como un proceso desde la clase EjecutarClaseJava, capturando su salida en el proceso padre___.

## Tarea Propuesta

Realiza un programa que realice lo siguiente:

- Una clase (__ClaseB__) que realice la moficiación de un fichero:
  - Lo cree si no existe a través de su nombre.
  - Añada una línea al final del fichero (__soy una línea del proceso(n)__).
- Crea una clase padre (__ClaseA__) que lance un número determinado de procesos, y que pasen como mensaje el proceso que es.

> [Métodos importantes y ejemplos](METODOS-IMPORTANTES.md)

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>