<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Añadir un multi-idioma al proyecto JavaFX MVC

## Objetivo

Ampliar el proyecto JavaFX para que permita cambiar el idioma de la interfaz usando ficheros `.properties`.

La aplicación podrá trabajar, por ejemplo, en:

- Español
- Inglés
- Otros idiomas añadidos posteriormente

---

## ¿Qué es la internacionalización?

La internacionalización, también llamada `i18n`, consiste en preparar una aplicación para mostrar textos en distintos idiomas sin modificar el código fuente principal.

En Java se suele hacer con:

```java
ResourceBundle
Locale
```

Y con ficheros:

```text
messages_es.properties
messages_en.properties
```

---

# Cambios necesarios en el proyecto

Para integrar multiidioma se deben modificar o añadir:

| Elemento | Cambio |
|---|---|
| Resources | Añadir ficheros `.properties` |
| PrincipalApplication | Cargar `ResourceBundle` |
| FXML | Usar claves de idioma |
| Controlador | Cambiar idioma dinámicamente |
| CSS | No requiere cambios obligatorios |

---

## 1. Crear los ficheros de idioma

Crear una carpeta:

```text
src/main/resources/es/ies/puerto/i18n/
```

Dentro de ella crear:

```text
messages_es.properties
messages_en.properties
```

---

## 2. Fichero en español

Archivo:

```text
src/main/resources/es/ies/puerto/i18n/messages_es.properties
```

Contenido:

```properties
app.title=Proyecto JavaFX Multiidioma
label.title=Gestión de usuarios
label.name=Nombre
label.age=Edad
button.add=Añadir
button.delete=Eliminar
button.clear=Limpiar
label.result=Resultado
label.language=Idioma
language.spanish=Español
language.english=Inglés
message.user.added=Usuario añadido
message.user.deleted=Usuario eliminado
error.title=Error
error.header=Dato no válido
error.age=La edad debe ser un número válido.
error.name=El nombre no puede estar vacío.
```

---

## 3. Fichero en inglés

Archivo:

```text
src/main/resources/es/ies/puerto/i18n/messages_en.properties
```

Contenido:

```properties
app.title=JavaFX Multilanguage Project
label.title=User management
label.name=Name
label.age=Age
button.add=Add
button.delete=Delete
button.clear=Clear
label.result=Result
label.language=Language
language.spanish=Spanish
language.english=English
message.user.added=User added
message.user.deleted=User deleted
error.title=Error
error.header=Invalid data
error.age=Age must be a valid number.
error.name=Name cannot be empty.
```

---

## 4. Modificar `PrincipalApplication`

Archivo:

```text
src/main/java/es/ies/puerto/PrincipalApplication.java
```

Código recomendado:

```java
package es.ies.puerto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class PrincipalApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Locale locale = new Locale("es");

        ResourceBundle bundle = ResourceBundle.getBundle(
                "es.ies.puerto.i18n.messages",
                locale
        );

        FXMLLoader fxmlLoader = new FXMLLoader(
                PrincipalApplication.class.getResource("usuario-view.fxml"),
                bundle
        );

        Scene scene = new Scene(fxmlLoader.load(), 500, 450);

        scene.getStylesheets().add(
                PrincipalApplication.class
                        .getResource("css/estilos.css")
                        .toExternalForm()
        );

        stage.setTitle(bundle.getString("app.title"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
```

---

## 5. Usar claves de idioma en el FXML

Archivo:

```text
src/main/resources/es/ies/puerto/usuario-view.fxml
```

Ejemplo:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.ComboBox?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.HBox?>
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

    <Label fx:id="tituloLabel"
           text="%label.title"
           styleClass="titulo"/>

    <TextField fx:id="nombreField"
               promptText="%label.name"/>

    <TextField fx:id="edadField"
               promptText="%label.age"/>

    <HBox spacing="10" alignment="CENTER">
        <Button fx:id="anadirButton"
                text="%button.add"
                onAction="#anadirUsuario"/>

        <Button fx:id="eliminarButton"
                text="%button.delete"
                onAction="#eliminarUsuario"/>

        <Button fx:id="limpiarButton"
                text="%button.clear"
                onAction="#limpiarCampos"/>
    </HBox>

    <HBox spacing="10" alignment="CENTER">
        <Label fx:id="idiomaLabel"
               text="%label.language"/>

        <ComboBox fx:id="idiomaComboBox"
                  onAction="#cambiarIdioma"/>
    </HBox>

    <Label fx:id="resultadoLabel"
           text="%label.result"/>

</VBox>
```

La sintaxis importante es:

```xml
text="%label.title"
```

JavaFX buscará la clave `label.title` dentro del fichero `.properties`.

---

## 6. Modificar el controlador para cambiar idioma

Archivo:

```text
src/main/java/es/ies/puerto/controllers/UsuarioController.java
```

Código base:

```java
package es.ies.puerto.controllers;

import es.ies.puerto.models.Usuario;
import es.ies.puerto.services.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Locale;
import java.util.ResourceBundle;

public class UsuarioController {

    @FXML
    private Label tituloLabel;

    @FXML
    private Label idiomaLabel;

    @FXML
    private Label resultadoLabel;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField edadField;

    @FXML
    private Button anadirButton;

    @FXML
    private Button eliminarButton;

    @FXML
    private Button limpiarButton;

    @FXML
    private ComboBox<String> idiomaComboBox;

    private UsuarioService usuarioService;
    private ResourceBundle bundle;

    @FXML
    public void initialize() {
        usuarioService = new UsuarioService();

        bundle = ResourceBundle.getBundle(
                "es.ies.puerto.i18n.messages",
                new Locale("es")
        );

        idiomaComboBox.getItems().addAll(
                bundle.getString("language.spanish"),
                bundle.getString("language.english")
        );

        idiomaComboBox.getSelectionModel().select(0);
    }

    @FXML
    public void anadirUsuario() {
        try {
            String nombre = nombreField.getText();

            if (nombre == null || nombre.isBlank()) {
                mostrarError(bundle.getString("error.name"));
                return;
            }

            int edad = Integer.parseInt(edadField.getText());

            Usuario usuario = usuarioService.crearUsuario(nombre, edad);

            resultadoLabel.setText(
                    bundle.getString("message.user.added")
                            + ": "
                            + usuario
            );

            limpiarCampos();

        } catch (NumberFormatException exception) {
            mostrarError(bundle.getString("error.age"));
        }
    }

    @FXML
    public void eliminarUsuario() {
        resultadoLabel.setText(
                bundle.getString("message.user.deleted")
        );
    }

    @FXML
    public void limpiarCampos() {
        nombreField.clear();
        edadField.clear();
    }

    @FXML
    public void cambiarIdioma() {
        int indice = idiomaComboBox.getSelectionModel()
                .getSelectedIndex();

        if (indice == 0) {
            cargarIdioma(new Locale("es"));
        } else {
            cargarIdioma(new Locale("en"));
        }
    }

    private void cargarIdioma(Locale locale) {
        bundle = ResourceBundle.getBundle(
                "es.ies.puerto.i18n.messages",
                locale
        );

        tituloLabel.setText(bundle.getString("label.title"));
        idiomaLabel.setText(bundle.getString("label.language"));
        nombreField.setPromptText(bundle.getString("label.name"));
        edadField.setPromptText(bundle.getString("label.age"));
        anadirButton.setText(bundle.getString("button.add"));
        eliminarButton.setText(bundle.getString("button.delete"));
        limpiarButton.setText(bundle.getString("button.clear"));
        resultadoLabel.setText(bundle.getString("label.result"));

        idiomaComboBox.getItems().setAll(
                bundle.getString("language.spanish"),
                bundle.getString("language.english")
        );

        if (locale.getLanguage().equals("es")) {
            idiomaComboBox.getSelectionModel().select(0);
        } else {
            idiomaComboBox.getSelectionModel().select(1);
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(bundle.getString("error.title"));
        alert.setHeaderText(bundle.getString("error.header"));
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
```

---

## 7. Conceptos importantes aprendidos

| Concepto | Función |
|---|---|
| `ResourceBundle` | Cargar textos desde ficheros `.properties` |
| `Locale` | Representar el idioma activo |
| `.properties` | Guardar claves y textos traducidos |
| `%clave` en FXML | Usar traducciones directamente en la vista |
| `ComboBox` | Permitir elegir idioma |
| MVC | Mantener separada la lógica de la vista |

---

## 8. ¿Qué debemos de tener claro?

La relación principal es:

```java
ResourceBundle bundle = ResourceBundle.getBundle(
        "es.ies.puerto.i18n.messages",
        new Locale("es")
);
```

Y en FXML:

```xml
<Label text="%label.title"/>
```

JavaFX sustituye `%label.title` por el texto correspondiente del fichero `.properties`.

---

## 9. ¿Qué debe de suceder?

La aplicación permitirá:

- Cargar textos desde ficheros externos.
- Cambiar entre español e inglés.
- Separar textos de la lógica Java.
- Añadir nuevos idiomas sin modificar toda la aplicación.

---

## 10. Añade un nuevo idioma

Para añadir francés, por ejemplo, crear:

```text
messages_fr.properties
```

Con contenido:

```properties
app.title=Projet JavaFX multilingue
label.title=Gestion des utilisateurs
label.name=Nom
label.age=Âge
button.add=Ajouter
button.delete=Supprimer
button.clear=Nettoyer
label.result=Résultat
label.language=Langue
language.spanish=Espagnol
language.english=Anglais
```

Y cargarlo con:

```java
cargarIdioma(new Locale("fr"));
```

---

## 11. Errores comunes

### Error: MissingResourceException

Puede aparecer si Java no encuentra el fichero `.properties`.

Comprobar que la ruta sea correcta:

```text
src/main/resources/es/ies/puerto/i18n/messages_es.properties
```

Y que se cargue así:

```java
ResourceBundle.getBundle("es.ies.puerto.i18n.messages", locale);
```

---

### Error: clave no encontrada

Si aparece un error indicando que no existe una clave, revisar que todos los ficheros tengan las mismas claves.

Ejemplo:

```properties
button.add=Añadir
```

Debe existir también en:

```properties
button.add=Add
```

---

### Los textos no cambian al seleccionar idioma

Comprobar que el `ComboBox` tenga:

```xml
onAction="#cambiarIdioma"
```

Y que el método exista en el controlador:

```java
@FXML
public void cambiarIdioma() {
    // codigo
}
```

---

## 12. Recuerda

Para añadir multiidioma en JavaFX se necesitan cuatro pasos principales:

1. Crear ficheros `.properties`.
2. Cargar los textos con `ResourceBundle`.
3. Usar claves en el FXML con `%clave`.
4. Cambiar dinámicamente los textos desde el controlador.

La estructura fundamental es:

```text
messages_es.properties
messages_en.properties
ResourceBundle
Locale
```

> Con esto el proyecto JavaFX queda preparado para trabajar con varios idiomas de forma limpia, escalable y profesional.

</div>