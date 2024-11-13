<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios)

## 2. API de ProcessBuilder

### Resumen de métodos

Para crear un nuevo constructor de procesos con el programa y argumentos del sistema operativo especificados, podemos usar este conveniente constructor. 

Podemos sobrescribir el directorio de trabajo predeterminado del proceso actual llamando al método `directory` y pasando un objeto `File`. Por defecto, el directorio de trabajo actual se establece en el valor devuelto por la propiedad del sistema `user.dir`.

Si queremos obtener las variables de entorno actuales, simplemente podemos llamar al método `environment()`. Este método nos devuelve una copia del entorno del proceso actual usando `System.getenv()`, pero como un `Map`.

Si queremos especificar que la entrada y salida estándar de nuestro subproceso debe ser la misma que la del proceso Java actual, podemos usar el método `inheritIO()`.

Cuando queramos redirigir la entrada, salida y error estándar del constructor del proceso a un archivo, tenemos estos tres métodos de redirección similares a nuestra disposición.

Por último, para iniciar un nuevo proceso con lo que hemos configurado, simplemente llamamos a `start()`.

**Métodos importantes:**

- `ProcessBuilder(String... command)`
- `directory(File directory)`
- `environment()`
- `inheritIO()`
- `redirectInput(File file), redirectOutput(File file), redirectError(File file)`
- `start()`
- `getOutputStream()`. El método ___getOutputStream()___ se utiliza para obtener el flujo de salida de un proceso creado mediante ProcessBuilder o el método Runtime.exec(). Este flujo permite enviar datos al proceso, es decir, escribir en la entrada estándar del proceso que se está ejecutando. Esto es útil cuando el proceso requiere alguna entrada del usuario o algún tipo de datos para continuar su ejecución. Este método es usado cuando queremos enviar datos a un proceso que está esperando la entrada del usuario o necesita datos para procesar, por ejemplo, con comandos como cat en Unix o scripts que leen de la entrada estándar.

- `getInputStream()`. El método __getInputStream()__ se utiliza para obtener el flujo de entrada del proceso creado. Este flujo permite leer los datos que el proceso genera a través de su salida estándar. En otras palabras, se usa para capturar la salida que normalmente se vería en la consola (por ejemplo, los resultados de un comando). Es útil cuando se desea capturar o procesar la salida del proceso que se está ejecutando. Esto podría ser la salida de comandos como echo, ls, o cualquier aplicación que devuelva resultados mediante la consola.

## 3. Ejemplos

Ahora que tenemos una comprensión básica de la API ProcessBuilder, veamos algunos ejemplos.

### 3.1. Usando ProcessBuilder para Imprimir la Versión de Java

```java
Process process = new ProcessBuilder("java", "-version").start();
List<String> results = readOutput(process.getInputStream());
assertThat("Los resultados no deberían estar vacíos", results, is(not(empty())));
assertThat("Los resultados deberían contener la versión de java: ", results, hasItem(containsString("java version")));
int exitCode = process.waitFor();
assertEquals("No se deberían detectar errores", 0, exitCode);
```

### 3.2. Iniciando un Proceso con un Entorno Modificado

```java
ProcessBuilder processBuilder = new ProcessBuilder();
Map<String, String> environment = processBuilder.environment();
environment.put("GREETING", "Hola Mundo");
processBuilder.command("/bin/bash", "-c", "echo $GREETING");
Process process = processBuilder.start();
List<String> results = readOutput(process.getInputStream());
assertThat("Los resultados no deberían estar vacíos", results, is(not(empty())));
assertThat("Los resultados deberían contener la versión de java: ", results, hasItem(containsString("Hola Mundo")));
```

### 3.3. Iniciando un Proceso con un Directorio de Trabajo Modificado

```java
@Test
public void givenProcessBuilderWhenModifyWorkingDirThenSuccess() throws IOException, InterruptedException {
    ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", "ls");
    processBuilder.directory(new File("src"));
    Process process = processBuilder.start();
    List<String> results = readOutput(process.getInputStream());
    assertThat("Los resultados no deberían estar vacíos", results, is(not(empty())));
    assertThat("Los resultados deberían contener la lista de directorios: ", results, contains("main", "test"));
    int exitCode = process.waitFor();
    assertEquals("No se deberían detectar errores", 0, exitCode);
}
```

#### 3.4. Redirigiendo Entrada y Salida Estándar

```java
ProcessBuilder processBuilder = new ProcessBuilder("java", "-version");
processBuilder.redirectErrorStream(true);
File log = folder.newFile("java-version.log");
processBuilder.redirectOutput(log);
Process process = processBuilder.start();
assertEquals("Si se redirige, debería ser -1 ", -1, process.getInputStream().read());
List<String> lines = Files.lines(log.toPath()).collect(Collectors.toList());
assertThat("Los resultados deberían contener la versión de java: ", lines, hasItem(contain
sString("java version")));
```

#### 3.5. Capturando el Input/Out Stream

##### Input

```java
import java.io.*;

public class EjemploEntradaSalidaProcessBuilder {
    public static void main(String[] args) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        
        // El comando 'cat' es usado para leer la entrada y devolverla.
        processBuilder.command("cat");

        try {
            // Iniciar el proceso
            Process proceso = processBuilder.start();

            // Escribir en la entrada del proceso (OutputStream)
            OutputStream outputStream = proceso.getOutputStream();
            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(outputStream));
            
            escritor.write("¡Hola desde Java!");
            escritor.newLine();
            escritor.flush();
            escritor.close(); // Cerrar el stream después de escribir

            // Capturar la salida del proceso usando getInputStream
            InputStream inputStream = proceso.getInputStream();
            BufferedReader lector = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            System.out.println("Salida del proceso:");
            while ((linea = lector.readLine()) != null) {
                System.out.println(linea);
            }

            // Esperar a que el proceso termine
            int codigoSalida = proceso.waitFor();
            System.out.println("\nEl proceso salió con el código: " + codigoSalida);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```


##### OutPut

```java
ProcessBuilder pb = new ProcessBuilder("cat");
Process proceso = pb.start();

// Escribimos en la entrada estándar del proceso
OutputStream outputStream = proceso.getOutputStream();
BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(outputStream));
escritor.write("Hola, proceso!");
escritor.flush();
escritor.close();
```

##### Otros Ejemplos

```java
ProcessBuilder processBuilder = new ProcessBuilder();
        
        // Set the command to run. For this example, we are using the 'echo' command.
        processBuilder.command("echo", "Hello, ProcessBuilder!");

        try {
            // Start the process
            Process process = processBuilder.start();

            // Capture the output of the process using getInputStream
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            System.out.println("Output of the process:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to exit
            int exitCode = process.waitFor();
            System.out.println("\nExited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
```

```java

```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>