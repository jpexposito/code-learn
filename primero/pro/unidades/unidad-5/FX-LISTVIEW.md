<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Añadir un ListView al proyecto JavaFX MVC

## Objetivo

Ampliar el proyecto JavaFX para que permita:

- Añadir usuarios dinámicamente.
- Mostrar usuarios en una lista visual.
- Seleccionar elementos.
- Eliminar elementos.
- Mantener persistencia en fichero.

---

## ¿Qué es un `ListView`?

`ListView` es un componente visual de JavaFX que permite mostrar colecciones dinámicas de objetos.

Ejemplo visual:

```text
Ana (25 años)
Luis (31 años)
Marta (19 años)
```

JavaFX usa automáticamente el método `toString()` del objeto para representar cada elemento en pantalla.

---

## Cambios necesarios en el proyecto

Para integrar `ListView` hay que modificar:

| Elemento | Cambio |
|---|---|
| Modelo | Mejorar `toString()` |
| Repositorio | Añadir `guardarTodos()` |
| Servicio | Gestionar lista completa |
| Controlador | Crear `ObservableList` |
| Vista FXML | Añadir `ListView` |
| CSS | Estilos para la lista |

---

## 1. Actualizar el modelo `Usuario`

Archivo:

```text
src/main/java/es/ies/puerto/models/Usuario.java
```

Añadir:

```java
@Override
public String toString() {
    return nombre + " (" + edad + " años)";
}
```

Esto permite que el `ListView` muestre correctamente los usuarios.

---

## 2. Añadir el `ListView` al FXML

Archivo:

```text
src/main/resources/es/ies/puerto/usuario-view.fxml
```

Importar:

```xml
<?import javafx.scene.control.ListView?>
```

Añadir dentro del `VBox`:

```xml
<ListView fx:id="usuariosListView"
          prefHeight="180"/>
```

---

## 3. Declarar el `ListView` en el controlador

Archivo:

```text
src/main/java/es/ies/puerto/controllers/UsuarioController.java
```

Importar:

```java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
```

Añadir:

```java
@FXML
private ListView<Usuario> usuariosListView;

private ObservableList<Usuario> usuariosObservableList;
```

---

## 4. Inicializar la lista observable

Dentro de `initialize()`:

```java
usuariosObservableList = FXCollections.observableArrayList(
        usuarioService.obtenerUsuarios()
);

usuariosListView.setItems(usuariosObservableList);
```

---

## 5. Añadir usuarios dinámicamente

Modificar el método del botón:

```java
@FXML
public void anadirUsuario() {

    try {

        String nombre = nombreField.getText();
        int edad = Integer.parseInt(edadField.getText());

        Usuario usuario = usuarioService.crearUsuario(nombre, edad);

        usuariosObservableList.add(usuario);

        resultadoLabel.setText(
                "Usuario añadido: " + usuario);

    } catch (NumberFormatException exception) {

        mostrarError(
                "La edad debe ser numérica");
    }
}
```

---

## 6. Detectar selección de usuarios

Dentro de `initialize()`:

```java
usuariosListView.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable,
                      usuarioAnterior,
                      usuarioSeleccionado) -> {

            if (usuarioSeleccionado != null) {

                nombreField.setText(
                        usuarioSeleccionado.getNombre());

                edadField.setText(
                        String.valueOf(
                                usuarioSeleccionado.getEdad()));
            }
        });
```

---

## 7. Eliminar usuarios seleccionados

Añadir método:

```java
@FXML
public void eliminarUsuario() {

    Usuario usuarioSeleccionado =
            usuariosListView.getSelectionModel()
                    .getSelectedItem();

    if (usuarioSeleccionado != null) {

        usuariosObservableList.remove(
                usuarioSeleccionado);

        usuarioService.guardarUsuarios(
                usuariosObservableList);
    }
}
```

Botón en FXML:

```xml
<Button text="Eliminar"
        onAction="#eliminarUsuario"/>
```

---

## 8. Añadir estilos CSS

Archivo:

```text
src/main/resources/es/ies/puerto/css/estilos.css
```

Añadir:

```css
.list-view {
    -fx-border-color: #cccccc;
    -fx-border-radius: 4px;
}
```

---

## 9. ¿Qué debemos de tener claro?

| Concepto | Función |
|---|---|
| `ListView` | Mostrar listas visuales |
| `ObservableList` | Actualización automática |
| `FXCollections` | Crear listas observables |
| `selectedItemProperty()` | Detectar selección |
| MVC | Separación de responsabilidades |

---

## 10. Lo más importante que debes de entender con JavaFX

La relación fundamental es:

```java
ObservableList<Usuario> usuarios =
        FXCollections.observableArrayList();

usuariosListView.setItems(usuarios);

usuarios.add(new Usuario("Ana", 25));
```

> Cuando cambia la `ObservableList`, JavaFX actualiza automáticamente la interfaz gráfica.

</div>