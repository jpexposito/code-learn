<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Trabajando con Interfaces-Maven)

Esta guía muestra cómo crear paso a paso un proyecto **Maven** funcional con **JavaFX**, estructura **MVC**, pruebas unitarias con **JUnit 5** y una configuración coherente de paquetes, clases y recursos.

---

## Requisitos previos

Antes de empezar, asegúrate de tener instalado:

- **JDK 17 o superior**.
- **Apache Maven**.
- Un **IDE compatible con Maven**, por ejemplo IntelliJ IDEA, Eclipse o Visual Studio Code.

Puedes comprobar las versiones con:

```bash
java -version
mvn -version
```

---

## 1. Vamos a construir un ejemplo real

Uno de los puntos más importantes para mejorar la calidad del proyecto es mantener una nomenclatura coherente.

En esta guía usaremos siempre los siguientes nombres:

| Elemento | Nombre elegido |
|---|---|
| Paquete base | `es.ies.puerto` |
| Clase principal | `PrincipalApplication` |
| Controlador | `UsuarioController` |
| Modelo | `Usuario` |
| Servicio | `UsuarioService` |
| Repositorio | `UsuarioRepository` |
| Implementación fichero | `UsuarioFileRepository` |
| Implementación BBDD | `UsuarioDatabaseRepository` |
| FXML principal | `usuario-view.fxml` |
| CSS principal | `estilos.css` |

### Reglas recomendadas

- Usar siempre el mismo idioma en nombres de clases y paquetes.
- Evitar mezclar nombres como `MainApp`, `PrincipalApplication`, `PrincipalAplication` o `ControladorPrincipal` para la misma responsabilidad.
- El nombre de la clase principal debe coincidir con el indicado en el `pom.xml`.
- El `fx:controller` del FXML debe coincidir exactamente con el paquete y nombre del controlador.
- Los paquetes declarados en Java deben coincidir con la ubicación real de los archivos.

---

## 2. Añadir un proyecto completo funcional paso a paso

### Paso 1: Crear la estructura del proyecto

Crea la siguiente estructura:

```text
mi-proyecto-javafx/
│── pom.xml
│── README.md
│
└── src/
    ├── main/
    │   ├── java/
    │   │   └── es/
    │   │       └── ies/
    │   │           └── puerto/
    │   │               ├── PrincipalApplication.java
    │   │               ├── controllers/
    │   │               │   └── UsuarioController.java
    │   │               ├── models/
    │   │               │   └── Usuario.java
    │   │               ├── repositories/
    │   │               │   ├── UsuarioRepository.java
    │   │               │   ├── UsuarioFileRepository.java
    │   │               │   └── UsuarioDatabaseRepository.java
    │   │               └── services/
    │   │                   └── UsuarioService.java
    │   │
    │   └── resources/
    │       └── es/
    │           └── ies/
    │               └── puerto/
    │                   ├── usuario-view.fxml
    │                   └── css/
    │                       └── estilos.css
    │
    └── test/
        └── java/
            └── es/
                └── ies/
                    └── puerto/
                        └── models/
                            └── UsuarioTest.java
```

---

### Paso 2: Crear el archivo `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>es.ies.puerto</groupId>
    <artifactId>mi-proyecto-javafx</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <javafx.version>21</javafx.version>
        <junit.version>5.10.2</junit.version>
        <javafx-maven-plugin.version>0.0.8</javafx-maven-plugin.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>${javafx-maven-plugin.version}</version>
                <configuration>
                    <mainClass>es.ies.puerto.PrincipalApplication</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

---

### Paso 3: Crear la clase principal

Archivo:

```text
src/main/java/es/ies/puerto/PrincipalApplication.java
```

```java
package es.ies.puerto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PrincipalApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                PrincipalApplication.class.getResource("usuario-view.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        scene.getStylesheets().add(
                PrincipalApplication.class.getResource("css/estilos.css").toExternalForm()
        );

        stage.setTitle("Proyecto JavaFX con Maven");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
```

---

### Paso 4: Crear el modelo

Archivo:

```text
src/main/java/es/ies/puerto/models/Usuario.java
```

```java
package es.ies.puerto.models;

public class Usuario {

    private String nombre;
    private int edad;

    public Usuario(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return nombre + " (" + edad + " años)";
    }
}
```


---

### Paso 5: Añadir el concepto de repositorio

En una arquitectura más mantenible, el **controlador** no debería guardar datos directamente en ficheros ni conectarse directamente a una base de datos. Para eso añadimos una nueva capa: el **repositorio**.

El repositorio es la clase responsable de decidir **dónde** y **cómo** se guardan o recuperan los datos.

```text
Vista JavaFX → Controlador → Servicio → Repositorio → Fichero / Base de datos
```

### ¿Qué hace cada capa?

| Capa | Responsabilidad |
|---|---|
| Modelo | Representa los datos de la aplicación. Por ejemplo, `Usuario`. |
| Repositorio | Guarda, busca, actualiza o elimina datos. Puede trabajar con ficheros, BBDD, API, etc. |
| Servicio | Contiene la lógica de negocio y decide cuándo usar el repositorio. |
| Controlador | Recibe acciones de la interfaz y llama al servicio. |
| Vista | Muestra la interfaz al usuario. |

### Modelo `Usuario`

El modelo no debe saber si se guarda en un fichero, en SQLite, en MySQL o en una API. Solo representa información.

```java
package es.ies.puerto.models;

public class Usuario {

    private String nombre;
    private int edad;

    public Usuario(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return nombre + ";" + edad;
    }
}
```

### Interfaz del repositorio

Archivo:

```text
src/main/java/es/ies/puerto/repositories/UsuarioRepository.java
```

```java
package es.ies.puerto.repositories;

import es.ies.puerto.models.Usuario;
import java.util.List;

public interface UsuarioRepository {

    void guardar(Usuario usuario);

    List<Usuario> obtenerTodos();
}
```

Esta interfaz define **qué operaciones existen**, pero no indica todavía si se trabaja con fichero o base de datos.

### Repositorio basado en fichero

Archivo:

```text
src/main/java/es/ies/puerto/repositories/UsuarioFileRepository.java
```

```java
package es.ies.puerto.repositories;

import es.ies.puerto.models.Usuario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class UsuarioFileRepository implements UsuarioRepository {

    private static final Path RUTA_FICHERO = Path.of("usuarios.txt");

    @Override
    public void guardar(Usuario usuario) {
        
    }

    @Override
    public List<Usuario> obtenerTodos() {
        
    }
}
```

### Repositorio basado en base de datos

Archivo:

```text
src/main/java/es/ies/puerto/repositories/UsuarioDatabaseRepository.java
```

```java
package es.ies.puerto.repositories;

import es.ies.puerto.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDatabaseRepository implements UsuarioRepository {

    @Override
    public void guardar(Usuario usuario) {
        // Aquí iría la lógica JDBC, JPA, Hibernate, SQLite, MySQL, PostgreSQL, etc.
        // Ejemplo conceptual:
        // INSERT INTO usuarios(nombre, edad) VALUES (?, ?)
        System.out.println("Guardando usuario en base de datos: " + usuario);
    }

    @Override
    public List<Usuario> obtenerTodos() {
        // Aquí iría la consulta a base de datos.
        // Ejemplo conceptual:
        // SELECT nombre, edad FROM usuarios
        return new ArrayList<>();
    }
}
```

### Elegir si trabajamos con fichero o BBDD

La decisión se puede centralizar en el servicio. Para proyectos pequeños es suficiente hacerlo así:

```java
package es.ies.puerto.services;

import es.ies.puerto.models.Usuario;
import es.ies.puerto.repositories.UsuarioDatabaseRepository;
import es.ies.puerto.repositories.UsuarioFileRepository;
import es.ies.puerto.repositories.UsuarioRepository;

import java.util.List;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService() {
        // Cambia esta línea para decidir dónde se guardan los datos.
        this.usuarioRepository = new UsuarioFileRepository();
        // this.usuarioRepository = new UsuarioDatabaseRepository();
    }

    public Usuario crearUsuario(String nombre, int edad) {
        Usuario usuario = new Usuario(nombre, edad);
        usuarioRepository.guardar(usuario);
        return usuario;
    }

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.obtenerTodos();
    }
}
```

También se puede hacer mediante una propiedad de configuración:

```properties
repositorio.tipo=fichero
```

Y después decidir:

```java
if (tipoRepositorio.equals("bbdd")) {
    usuarioRepository = new UsuarioDatabaseRepository();
} else {
    usuarioRepository = new UsuarioFileRepository();
}
```

De esta forma, si mañana cambiamos de fichero a base de datos, el controlador y la vista no tienen que modificarse.

---

### Paso 6: Crear el servicio actualizado

Archivo:

```text
src/main/java/es/ies/puerto/services/UsuarioService.java
```

```java
package es.ies.puerto.services;

import es.ies.puerto.models.Usuario;
import es.ies.puerto.repositories.UsuarioFileRepository;
import es.ies.puerto.repositories.UsuarioRepository;

import java.util.List;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioFileRepository();
    }

    public Usuario crearUsuario(String nombre, int edad) {
        Usuario usuario = new Usuario(nombre, edad);
        usuarioRepository.guardar(usuario);
        return usuario;
    }

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.obtenerTodos();
    }
}
```

---

### Paso 7: Crear el controlador

Archivo:

```text
src/main/java/es/ies/puerto/controllers/UsuarioController.java
```

```java
package es.ies.puerto.controllers;

import es.ies.puerto.models.Usuario;
import es.ies.puerto.services.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UsuarioController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField edadField;

    @FXML
    private Label resultadoLabel;

    private UsuarioService usuarioService;

    @FXML
    public void initialize() {
        usuarioService = new UsuarioService();
    }

    @FXML
    public void mostrarUsuario() {
        try {
            String nombre = nombreField.getText();
            int edad = Integer.parseInt(edadField.getText());

            Usuario usuario = usuarioService.crearUsuario(nombre, edad);
            resultadoLabel.setText(usuario.toString());
        } catch (NumberFormatException exception) {
            mostrarError("La edad debe ser un número válido.");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Dato no válido");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
```

---

### Paso 8: Crear la vista FXML

Archivo:

```text
src/main/resources/es/ies/puerto/usuario-view.fxml
```

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/21"
      xmlns:fx="http://javafx.com/fxml/1"
      fx:controller="es.ies.puerto.controllers.UsuarioController"
      spacing="10"
      alignment="CENTER"
      styleClass="contenedor">

    <padding>
        <Insets top="20" right="20" bottom="20" left="20"/>
    </padding>

    <Label text="Crear usuario" styleClass="titulo"/>

    <TextField fx:id="nombreField" promptText="Nombre"/>
    <TextField fx:id="edadField" promptText="Edad"/>

    <Button text="Mostrar usuario" onAction="#mostrarUsuario"/>

    <Label fx:id="resultadoLabel" text="Resultado"/>

</VBox>
```

---

### Paso 9: Crear la hoja de estilos

Archivo:

```text
src/main/resources/es/ies/puerto/css/estilos.css
```

```css
.contenedor {
    -fx-background-color: #f4f4f4;
}

.titulo {
    -fx-font-size: 20px;
    -fx-font-weight: bold;
}
```

---

### Paso 10: Crear una prueba unitaria

Archivo:

```text
src/test/java/es/ies/puerto/models/UsuarioTest.java
```

```java
package es.ies.puerto.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioTest {

    @Test
    void crearUsuarioCorrectamente() {
        Usuario usuario = new Usuario("Ana", 25);

        assertEquals("Ana", usuario.getNombre());
        assertEquals(25, usuario.getEdad());
    }
}
```

---

### Paso 11: Ejecutar el proyecto

Para compilar y ejecutar las pruebas:

```bash
mvn clean test
```

Para lanzar la aplicación JavaFX:

```bash
mvn clean javafx:run
```

Otros comandos útiles:

```bash
mvn clean package
mvn dependency:tree
```

---

# Resumen de responsabilidades MVC

| Capa | Responsabilidad | No debería hacer |
|---|---|---|
| Modelo | Representar datos y reglas básicas | Depender de JavaFX |
| Vista | Mostrar la interfaz gráfica | Contener lógica de negocio |
| Controlador | Gestionar eventos de la interfaz | Acceder directamente a ficheros o bases de datos |
| Servicio | Coordinar la lógica de negocio | Manipular elementos gráficos |
| Repositorio | Persistir y recuperar datos | Contener lógica de interfaz |


---

## Construir una APK para Android con GluonFX

JavaFX no genera una APK directamente con el plugin estándar `javafx-maven-plugin`. Para Android se utiliza **GluonFX**, que permite compilar aplicaciones Java/JavaFX a aplicaciones nativas para distintos sistemas, incluyendo Android.

### Requisitos para Android

Antes de compilar la APK necesitas instalar y configurar:

| Elemento | Uso |
|---|---|
| JDK 17 o superior | Compilar el proyecto Java. |
| Maven | Ejecutar los objetivos del proyecto. |
| GraalVM | Compilar la aplicación Java/JavaFX como binario nativo. |
| Android Studio o Android SDK | Generar el proyecto Android y la APK. |
| Variables de entorno | Permitir que Maven, GraalVM y Android SDK sean localizados. |

### Cambios necesarios en `pom.xml`

Añade una propiedad para la versión del plugin de GluonFX:

```xml
<properties>
    <gluonfx-maven-plugin.version>1.0.25</gluonfx-maven-plugin.version>
</properties>
```

Después añade el plugin dentro de `<build><plugins>`:

```xml
<plugin>
    <groupId>com.gluonhq</groupId>
    <artifactId>gluonfx-maven-plugin</artifactId>
    <version>${gluonfx-maven-plugin.version}</version>
    <configuration>
        <target>android</target>
        <mainClass>es.ies.puerto.PrincipalApplication</mainClass>
    </configuration>
</plugin>
```

También puedes definir un perfil específico para Android:

```xml
<profiles>
    <profile>
        <id>android</id>
        <build>
            <plugins>
                <plugin>
                    <groupId>com.gluonhq</groupId>
                    <artifactId>gluonfx-maven-plugin</artifactId>
                    <version>${gluonfx-maven-plugin.version}</version>
                    <configuration>
                        <target>android</target>
                        <mainClass>es.ies.puerto.PrincipalApplication</mainClass>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </profile>
</profiles>
```

Si aparece el error:

```text
GraalVM installation directory not found
```

puedes solucionarlo de dos formas:

1. Definiendo la variable `GRAALVM_HOME`.
2. Indicando la ruta directamente en el plugin:

```xml
<configuration>
    <target>android</target>
    <mainClass>es.ies.puerto.PrincipalApplication</mainClass>
    <graalvmHome>/ruta/a/graalvm</graalvmHome>
</configuration>
```

En Windows:

```xml
<graalvmHome>C:\ruta\a\graalvm</graalvmHome>
```

### Adaptar el proyecto para móvil

Aunque el código JavaFX puede ejecutarse en Android mediante GluonFX, conviene tener en cuenta estas recomendaciones:

- Evitar ventanas grandes pensadas solo para escritorio.
- Usar diseños adaptables como `VBox`, `HBox`, `BorderPane` o `StackPane`.
- Evitar rutas absolutas a ficheros.
- Guardar datos en ubicaciones compatibles con Android.
- Separar bien la lógica de negocio de la interfaz.
- Mantener la capa `repository` para poder cambiar entre fichero, SQLite, API REST o BBDD externa.

### Comandos para generar la APK

Desde la raíz del proyecto:

```bash
mvn -Pandroid gluonfx:build
mvn -Pandroid gluonfx:package
```

La APK suele generarse en una ruta similar a:

```text
target/gluonfx/aarch64-android/gvm/android_project/app/build/outputs/apk/debug/app-debug.apk
```

Para instalarla en un dispositivo Android conectado por USB:

```bash
mvn -Pandroid gluonfx:install
```

También puedes comprobar que el dispositivo está conectado con:

```bash
adb devices
```

### Flujo recomendado

```text
1. Probar primero en escritorio con mvn clean javafx:run
2. Verificar tests con mvn clean test
3. Configurar GRAALVM_HOME y ANDROID_HOME
4. Ejecutar mvn -Pandroid gluonfx:build
5. Ejecutar mvn -Pandroid gluonfx:package
6. Instalar o copiar la APK generada
```

### Problemas frecuentes al generar la APK

| Error | Posible causa | Solución |
|---|---|---|
| `GraalVM installation directory not found` | No está definida la ruta de GraalVM | Configurar `GRAALVM_HOME` o `graalvmHome` |
| `ANDROID_HOME not found` | No está configurado el SDK de Android | Configurar `ANDROID_HOME` |
| `adb not found` | No está en el `PATH` | Añadir `platform-tools` al `PATH` |
| FXML no encontrado | Ruta incorrecta del recurso | Revisar `getResource(...)` |
| Error de reflexión con FXML | Falta `opens` en `module-info.java` | Abrir el paquete del controlador a `javafx.fxml` |

## `module-info.java` recomendado

Archivo:

```text
src/main/java/module-info.java
```

```java
module es.ies.puerto {
    requires javafx.controls;
    requires javafx.fxml;

    opens es.ies.puerto.controllers to javafx.fxml;

    exports es.ies.puerto;
    exports es.ies.puerto.models;
    exports es.ies.puerto.services;
    exports es.ies.puerto.repositories;
}
```

---

## Errores comunes

### `Location is not set`

El archivo FXML no se encuentra. Revisa la ruta usada en:

```java
PrincipalApplication.class.getResource("usuario-view.fxml")
```

### `NullPointerException` en campos con `@FXML`

Puede ocurrir si el `fx:id` del FXML no coincide con el nombre del atributo del controlador.

### El botón no ejecuta el método

Comprueba que el método existe en el controlador y que el FXML tiene:

```xml
onAction="#mostrarUsuario"
```

### Error con el controlador

Comprueba que el `fx:controller` coincide con el paquete real:

```xml
fx:controller="es.ies.puerto.controllers.UsuarioController"
```

---

## Continuando con el desarrollo

- [Uso de ListView](FX-LISTVIEW.md)
- [Uso de TabelView](FX-TABELVIEW.md)
- [Incorporando Multi-idioma](FX-MULTI-IDIOMA.md)

</div>