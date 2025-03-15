<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Trabajando con Interfaces-Maven)

Este documento te proporciona una guía paso a paso para crear un proyecto Maven que utilice JavaFX para la interfaz de usuario, pruebas unitarias y el patrón de diseño Modelo-Vista-Controlador (MVC).

## Requisitos Previos

Asegúrate de tener los siguientes requisitos instalados en tu sistema:

- **Java Development Kit (JDK)**: Versión 11 o superior (preferentemente JDK 17+).
- **Apache Maven**: Para gestionar dependencias y construir el proyecto.
- **IDE**: Puedes usar IntelliJ IDEA, Eclipse o cualquier otro IDE compatible con Maven y JavaFX.

---

## 1. Crear el Proyecto Maven

### Estructura básica del proyecto

Primero, crea un proyecto Maven básico con la siguiente estructura:

```code
mi-proyecto-javafx/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── es/
│   │   │   │   ├── ies/
│   │   │   │   │   ├── puerto/
│   │   │   │   │   │   ├── MainApp.java            # Clase principal
│   │   │   │   │   │   ├── controladores/
│   │   │   │   │   │   │   ├── PrincipalController.java
│   │   │   │   │   │   ├── modelos/
│   │   │   │   │   │   │   ├── Usuario.java
│   │   │   │   │   │   │   ├── GestorUsuarios.java
│   │   │   │   │   │   ├── vistas/                 # (Opcional: si tienes clases de vista)
│   │   ├── resources/
│   │   │   ├── fxml/
│   │   │   │   ├── principal.fxml                   # Interfaz principal
│   │   │   ├── css/
│   │   │   │   ├── estilos.css                      # Estilos personalizados
│   │   │   ├── images/
│   │   │   │   ├── logo.png
│   ├── test/
│   │   ├── java/
│   │   │   ├── es/
│   │   │   │   ├── ies/
│   │   │   │   │   ├── puerto/
│   │   │   │   │   │   ├── MainAppTest.java         # Pruebas unitarias
│── pom.xml
│── README.md
```

## 2. Configuración del archivo `pom.xml`

### Dependencias necesarias en `pom.xml`

Tu archivo `pom.xml` debe incluir las siguientes dependencias para JavaFX, pruebas unitarias y cualquier otra librería adicional.

```xml
<properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <javafx.version>21</javafx.version>
        <junit.version>5.9.2</junit.version>
        <javafx-maven-plugin.version>0.0.8</javafx-maven-plugin.version>
        <maven-compiler-plugin.version>3.11.0</maven-compiler-plugin.version>
        <controlsfx.version>11.2.0</controlsfx.version>
    </properties>
    <!-- Dependencias de JavaFX -->
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

        <!-- Biblioteca ControlsFX para UI mejorada -->
        <dependency>
            <groupId>org.controlsfx</groupId>
            <artifactId>controlsfx</artifactId>
            <version>${controlsfx.version}</version>
        </dependency>
        ...
```

El plugin que es más importante es el siguiente:

```xml
<plugin>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-maven-plugin</artifactId>
    <version>${javafx-maven-plugin.version}</version>
    <executions>
        <execution>
            <id>default-cli</id>
            <goals>
                <goal>run</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <mainClass>es.ies.puerto.PrincipalApplication</mainClass>
    </configuration>
</plugin>
```

Como podemos observar, la clase **es.ies.puerto.PrincipalApplication** es la clase principal **(Contiene el Main)**, y lanza la aplicación.

## Estructura de dependencias de responsabilidad

### Modelo

- Representa los datos y la lógica de negocio.
- Puede incluir clases que gestionan acceso a bases de datos, cálculos, etc.
- No tiene ninguna referencia a **JavaFX**.

```java
public class Usuario {
    private String nombre;
    private int edad;

    public Usuario(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
}
```

### Vista (View)

- Es el archivo FXML o el código en Java que define la interfaz gráfica.
- No contiene lógica de negocio, solo la estructura visual.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<VBox xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1"
      fx:controller="controlador.UsuarioController">
    <Label text="Nombre:"/>
    <TextField fx:id="nombreField"/>
    <Label text="Edad:"/>
    <TextField fx:id="edadField"/>
    <Button text="Mostrar" onAction="#mostrarUsuario"/>
</VBox>
```

### Controlador (Controller)

- Se encarga de manejar la interacción del usuario.
- Usa los datos del Modelo y los actualiza en la Vista.
- Se conecta con los elementos del FXML.

```java
package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import modelo.Usuario;

public class UsuarioController {
    @FXML private TextField nombreField;
    @FXML private TextField edadField;

    public void mostrarUsuario() {
        String nombre = nombreField.getText();
        int edad = Integer.parseInt(edadField.getText());

        Usuario usuario = new Usuario(nombre, edad);
        System.out.println("Usuario: " + usuario.getNombre() + ", Edad: " + usuario.getEdad());
    }
}
```

En JavaFX, el **FXML** define la interfaz gráfica, mientras que el **Controlador** gestiona la lógica de la aplicación. La relación entre ambos se establece a través de `fx:controller` y la vinculación de elementos con `@FXML`.

#### Inyección de depedencias en un Controlador

En **JavaFX**, cuando usamos `FXML`, normalmente el controlador se instancia automáticamente mediante el `FXMLLoader`. Sin embargo, si queremos **inyectar una clase en su constructor**, debemos hacerlo manualmente.

##### Paso 1: Inyectar `ServicioUsuario` en el Constructor  

En este enfoque, `UsuarioController` recibe una instancia de `ServicioUsuario` a través del **constructor**.

###### 📌 **1. Crear la Clase `ServicioUsuario`**

Esta es la clase que queremos inyectar en el controlador.

```java
public class ServicioUsuario {
    public String obtenerNombre() {
        return "Juan Pérez";
    }
}
```

###### 📌 2. Crear el Controlador con Inyección en el Constructor

```java
package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import modelo.Usuario;

public class UsuarioController {

    private final ServicioUsuario servicioUsuario;

    @FXML private TextField nombreField;
    @FXML private TextField edadField;

    public ControladorPrincipal(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }
    /**
     * Si no tuviera el setter tendria que inicializar la clase en el metodo inicialize realizando lo siguiente
     * servicioUsuario = new ServicioUsuario();
     **/


    @FXML
    public void initialize() {
        // En este caso no es necesario inicializar el constructor ya que ya esta instanciado
    }

    public void mostrarUsuario() {
        String nombre = nombreField.getText();
        int edad = Integer.parseInt(edadField.getText());

        Usuario usuario = new Usuario(nombre, edad);
        System.out.println("Usuario: " + usuario.getNombre() + ", Edad: " + usuario.getEdad());
    }
}
```

###### 📌 3. Cargar el FXML con el FXMLLoader y Configurar la Fábrica de

```java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class PrincipalAplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Crear la instancia de ServicioUsuario
        ServicioUsuario servicioUsuario = new ServicioUsuario();

        // Configurar FXMLLoader con una instancia para el controlador
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vista.fxml"));
        loader.setControllerFactory(param -> new ControladorPrincipal(servicioUsuario));

        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

> **NOTA**: La carga de los servicios en el controlador no siempre se puede realizar de esta forma y necesario realizarlo desde el constructor del controlador realizando una instancia de cada uno de los servicios que utilice ese controlador, tal y como se muestra en el siguiente ejemplo.

```java
public class UsuarioController {

    private final ServicioUsuario servicioUsuario;

    @FXML private TextField nombreField;
    @FXML private TextField edadField;

    @FXML
    public void initialize() {
        servicioUsuario = new ServicioUsuario();
    }
}
```

## ⚡ Interacción entre FXML y el Controlador

1. **El FXML define la interfaz** con etiquetas XML.
2. **El Controlador maneja eventos** en respuesta a interacciones del usuario.
3. **`fx:controller` enlaza el FXML con su Controlador**.
4. **Los elementos de la UI se vinculan con `@FXML`** en el controlador para manipularlos desde el código.

---

## Arquitectura y responsabilidades

```mermaid
graph TD;
    Vista["🖥️ Vista (JavaFX)"] -->|Solicita datos| Controlador["🎮 Controlador"];
    Controlador -->|Obtiene datos| Modelo["🗄️ Modelo"];
    Modelo -->|Devuelve datos| Controlador;
    Controlador -->|Actualiza UI| Vista;

    subgraph "🔥 JavaFX"
        Vista
    end

    subgraph "🛠️ Lógica de Negocio"
        Modelo
    end

    subgraph "🧩 Controlador"
        Controlador
    end
```

## module-info.java

## ¿Qué es `module-info.java` en JavaFX?

A partir de la versión 9 se introdujo el **Sistema de Módulos** para mejorar la encapsulación del código.  
Un proyecto JavaFX es modular, y requiere un **archivo `module-info.java`** para declarar los paquetes y dependencias utilizadas.
---

### 📌 **1. Declarar un Módulo en JavaFX**

Cada aplicación JavaFX modular necesita un archivo `module-info.java`, donde se define el módulo principal.

```java
module com.ejemplo.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ejemplo.controladores to javafx.fxml;
    exports com.ejemplo;
}
```

 **Palabra Clave**                               | **Descripción** |
|-------------------------------------------------|----------------|
| `module com.ejemplo.app`                        | Define el nombre del módulo. (__Páquete principal del proyecto__) |
| `requires javafx.controls;`                     | Importa el módulo JavaFX para controles de UI. |
| `requires javafx.fxml;`                         | Importa el módulo JavaFX para archivos FXML. |
| `opens com.ejemplo.controladores to javafx.fxml;` | Permite a `FXMLLoader` acceder a las clases del paquete `com.ejemplo.controladores`. |
| `exports com.ejemplo;`                          | Expone el paquete `com.ejemplo` a otros módulos. |

### Trabjando con las dependencias de nuestro MVC

Si deseas usar **Jackson** para trabajar con JSON y otras librerías para XML o CSV, debes agregar los módulos correspondientes en `module-info.java`:

```java
module com.ejemplo.app {
    requires javafx.controls;
    requires javafx.fxml;

    // Permitir acceso a los controladores desde FXMLLoader
    opens com.ejemplo.controladores to javafx.fxml;

    // Exportar paquetes para que otros modulos puedan usarlos
    exports com.ejemplo;
    exports com.ejemplo.modelo;

    // Agregar dependencias para JSON (Jackson)
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;

    // Dependencias para manipular archivos CSV y XML
    requires java.xml;   // Para XML
    requires org.apache.commons.csv;  // Para CSV
}
```

## Comando Maven

Para lanzar nuestra app en maven debemos ejecutar el siguiente código:

```code
mvn clean javafx:run
```

</div>