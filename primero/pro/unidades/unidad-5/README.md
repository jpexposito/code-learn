<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Trabajando con Interfaces)

<div align="center">
    <img src=images/javafx.png width="600">
</div>

**JavaFX** es una tecnología creada por Oracle para el desarrollo de interfaces gráficas para nuestras aplicaciones en el lenguaje de programación Java. Para crear interfaces gráficas en Java, antiguamente se utilizaba AWT y luego posteriormente Swing, pero la potencia de JavaFX no tiene ni punto de comparación a la de sus predecesoras. `JavaFX fue anunciado en mayo de 2007 y liberado en diciembre de 2008`.

En el jdk 8, JavaFX venía incluido. Oracle desde entonces ha hecho muchos cambios en la filosofía adoptada con el jdk. Como ya sabéis ya dejó de ser libre su jdk, aunque lo liberó en el openjdk el cual impulsa activamente. También, debido a su política de actualización del jdk y dado que JavaFX no evolucionaba a ese ritmo, decidió independizar el jdk de JavaFX. También decidió no generar binarios de su herramienta de diseño para dicha tecnología, Scene Builder. Por todo ello, la empresa Gluon, fue la encargada de empaquetar las librerías de JavaFX y los binarios de Scene Builder.

Para crear aplicaciones utilizando JavaFX debemos tener el SDK de JavaFX. El SDK de JavaFX podemos descargarlo de la página de Gluon en la que podemos elegir la versión empaquetada para nuestro SO.

La evolución del proyecto la podemos observar en el siguiente [enlace](https://gluonhq.com/products/javafx/).

## Diseño de interfaces

Antes de empezar a trabajar con las interfaces gráficas de usuario debemos saber cómo diseñarlas:

- De qué elementos pueden estar compuestas
- Para qué podemos utilizar cada uno de estos elementos.
- Cómo distribuir dichos elementos en las interfaces.
- Las diferentes posibilidades que nos ofrece JavaFX para llevar a cabo este diseño, etc.

Para ello primero mostraré cómo diseñar las interfaces gráficas de usuario utilizando código para ello y luego veremos la alternativa a la utilización del código que nos ofrece **JavaFX** mediante el uso de ficheros **XML (llamados en JavaFX FXML)**.

Un esqueleto básico de una aplicación **JavaFx** podría ser el que muestro en la plantilla. En Java FX solemos tener un escenario principal en el que podemos mostrar una o varias escenas. Éstas escenas serán las que contendrán la jerarquía de nodo, partiendo de un nodo raíz.

Aún así, os dejo el código básico que debe contener el esqueleto de una aplicación **JavaFX**:

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PrincipalApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("app-init.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Soy tu primera app visual!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
```

Ni que decir tiene, que el nombre del paquete y de la clase lo deberéis cambiar a vuestra conveniencia.

El código del esqueleto anterior generará la siguiente interfaz.

<div align="center">
    <img src=images/primera-ventana.png width="200">
</div>

A continuación veremos cómo ir añadiendo elementos a dicha interfaz para adecuarla a nuestras necesidades.

JavaFX es un framework para la creación de interfaces gráficas en Java. A continuación, se presentan los principales elementos de JavaFX divididos por categoría:

## Contenedores

Los contenedores son estructuras que organizan otros nodos en la interfaz de usuario.

| **Elemento**       | **Descripción** | **Documentación** |
|--------------------|-----------------|-------------------|
| `Pane`             | Contenedor básico sin restricciones de disposición. | [Pane](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/layout/Pane.html) |
| `HBox`             | Organiza elementos en una fila horizontal. | [HBox](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/layout/HBox.html) |
| `VBox`             | Organiza elementos en una columna vertical. | [VBox](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/layout/VBox.html) |
| `BorderPane`       | Divide la UI en cinco regiones (top, bottom, left, right, center). | [BorderPane](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/layout/BorderPane.html) |
| `GridPane`         | Dispone los nodos en una cuadrícula. | [GridPane](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/layout/GridPane.html) |
| `StackPane`        | Organiza los elementos en capas. | [StackPane](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/layout/StackPane.html) |
| `FlowPane`         | Organiza los nodos en una fila o columna según el espacio disponible. | [FlowPane](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/layout/FlowPane.html) |
| `AnchorPane`       | Organiza nodos anclados a los bordes de la ventana. | [AnchorPane](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/layout/AnchorPane.html) |

## Controles

Los controles son elementos interactivos para la entrada y salida de datos.

| **Elemento**       | **Descripción** | **Documentación** |
|--------------------|-----------------|-------------------|
| `Button`           | Botón clickeable. | [Button](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/Button.html) |
| `Label`            | Etiqueta de texto no editable. | [Label](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/Label.html) |
| `TextField`        | Campo de entrada de texto de una línea. | [TextField](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/TextField.html) |
| `PasswordField`    | Campo de texto para contraseñas. | [PasswordField](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/PasswordField.html) |
| `CheckBox`         | Casilla de verificación para opciones booleanas. | [CheckBox](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/CheckBox.html) |
| `RadioButton`      | Botón de opción, utilizado para seleccionar entre varias opciones. | [RadioButton](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/RadioButton.html) |
| `ComboBox`         | Lista desplegable con opciones. | [ComboBox](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/ComboBox.html) |
| `Slider`           | Control deslizante para seleccionar valores en un rango. | [Slider](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/Slider.html) |
| `ProgressBar`      | Barra de progreso para mostrar el avance de una tarea. | [ProgressBar](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/ProgressBar.html) |
| `ListView`         | Muestra una lista de elementos. | [ListView](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/ListView.html) |
| `TableView`        | Muestra una tabla de datos organizados en filas y columnas. | [TableView](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/TableView.html) |

## Menús y Barras

Elementos para organizar menús y herramientas en la interfaz.

| **Elemento**       | **Descripción** | **Documentación** |
|--------------------|-----------------|-------------------|
| `MenuBar`          | Barra de menús con opciones desplegables. | [MenuBar](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/MenuBar.html) |
| `Menu`             | Representa un menú dentro de una barra de menú. | [Menu](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/Menu.html) |
| `MenuItem`         | Elemento dentro de un menú. | [MenuItem](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/MenuItem.html) |
| `ToolBar`          | Barra de herramientas con botones y controles. | [ToolBar](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/ToolBar.html) |

## Multimedia y Gráficos

Elementos para gráficos 2D/3D y reproducción multimedia.

| **Elemento**       | **Descripción** | **Documentación** |
|--------------------|-----------------|-------------------|
| `Canvas`           | Permite dibujar gráficos 2D. | [Canvas](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/canvas/Canvas.html) |
| `ImageView`        | Muestra imágenes en la interfaz. | [ImageView](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/image/ImageView.html) |
| `MediaPlayer`      | Reproduce archivos de audio y video. | [MediaPlayer](https://openjfx.io/javadoc/21/javafx.media/javafx/scene/media/MediaPlayer.html) |
| `MediaView`        | Muestra un video o audio en la interfaz. | [MediaView](https://openjfx.io/javadoc/21/javafx.media/javafx/scene/media/MediaView.html) |
| `Chart`            | Para representar gráficos estadísticos (por ejemplo, `PieChart`, `LineChart`). | [Chart](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/chart/package-summary.html) |

## Animaciones y Efectos

Elementos para crear animaciones y aplicar efectos visuales.

| **Elemento**       | **Descripción** | **Documentación** |
|--------------------|-----------------|-------------------|
| `Animation`        | Clase base para las animaciones en JavaFX. | [Animation](https://openjfx.io/javadoc/21/javafx.animation/javafx/animation/Animation.html) |
| `FadeTransition`   | Transición de desvanecimiento (fade). | [FadeTransition](https://openjfx.io/javadoc/21/javafx.animation/javafx/animation/FadeTransition.html) |
| `RotateTransition` | Transición de rotación de un nodo. | [RotateTransition](https://openjfx.io/javadoc/21/javafx.animation/javafx/animation/RotateTransition.html) |
| `ScaleTransition`  | Transición que cambia el tamaño de un nodo. | [ScaleTransition](https://openjfx.io/javadoc/21/javafx.animation/javafx/animation/ScaleTransition.html) |
| `DropShadow`       | Efecto de sombra para un nodo. | [DropShadow](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/effect/DropShadow.html) |
| `Glow`             | Efecto de resplandor. | [Glow](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/effect/Glow.html) |

## Eventos

JavaFX utiliza un sistema basado en eventos para gestionar la interacción del usuario.

| **Elemento**       | **Descripción** | **Documentación** |
|--------------------|-----------------|-------------------|
| `ActionEvent`      | Evento de acción, como un clic de botón. | [ActionEvent](https://openjfx.io/javadoc/21/javafx.base/javafx/event/ActionEvent.html) |
| `MouseEvent`       | Eventos del ratón como clics o desplazamiento. | [MouseEvent](https://openjfx.io/javadoc/21/javafx.scene.input/javafx/scene/input/MouseEvent.html) |
| `KeyEvent`         | Eventos de teclado, como pulsaciones de teclas. | [KeyEvent](https://openjfx.io/javadoc/21/javafx.scene.input/javafx/scene/input/KeyEvent.html) |

## FXML

JavaFX permite definir interfaces en archivos FXML, lo que facilita la separación de la lógica de la interfaz.

| **Elemento**       | **Descripción** | **Documentación** |
|--------------------|-----------------|-------------------|
| `FXMLLoader`       | Carga archivos FXML para definir la UI. | [FXMLLoader](https://openjfx.io/javadoc/21/javafx.fxml/javafx/fxml/FXMLLoader.html) |
| `@FXML`            | Anotación para enlazar los elementos de la UI con el controlador. | [FXML](https://openjfx.io/javadoc/21/javafx.fxml/javafx/fxml/package-summary.html) |

## Escena y Ventana Principal

La estructura base de una aplicación JavaFX.

| **Elemento**       | **Descripción** | **Documentación** |
|--------------------|-----------------|-------------------|
| `Stage`            | Representa la ventana principal de la aplicación. | [Stage](https://openjfx.io/javadoc/21/javafx.stage/javafx/stage/Stage.html) |
| `Scene`            | Contenedor para los nodos y elementos visuales de la aplicación. | [Scene](https://openjfx.io/javadoc/21/javafx.scene/javafx/scene/Scene.html) |
| `Application`      | Clase base para la creación de aplicaciones JavaFX. | [Application](https://openjfx.io/javadoc/21/javafx.application/javafx/application/Application.html) |

---

Para más información, visita la [documentación oficial de JavaFX](https://openjfx.io/).

## Trabajo en Proyectos

Para la realización de proyectos basado en **javafx**, vamos a trabajar con **maven** para la inyección de librerias y **Scenebuilder** para definir los elemenos de una forma gráfica.

- [Scenebuilder.md](SCENEBUILDER.md).
- [Mi](PROYECTO-MAVEN.md).

</div>