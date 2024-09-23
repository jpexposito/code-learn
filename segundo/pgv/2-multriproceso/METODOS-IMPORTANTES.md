<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Programación de Servicios)

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
assertThat("Los resultados deberían contener la versión de java: ", lines, hasItem(containsString("java version")));
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>