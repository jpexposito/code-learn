<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Añadir un TabelView al proyecto JavaFX MVC

## Objetivo

Ampliar el proyecto JavaFX para que permita:

- Mostrar usuarios en formato tabla.
- Añadir usuarios dinámicamente.
- Seleccionar filas.
- Eliminar elementos seleccionados.
- Trabajar con listas observables.
- Aplicar arquitectura MVC correctamente.

---

## ¿Qué es un `TableView`?

`TableView` es un componente de JavaFX que permite mostrar información en formato tabular.

Ejemplo:

| Nombre | Edad |
|---|---|
| Ana | 25 |
| Luis | 31 |
| Marta | 19 |

Es uno de los componentes más utilizados en aplicaciones profesionales JavaFX.

---

## Cambios necesarios en el proyecto

Para integrar `TableView` se deben modificar:

| Elemento | Cambio |
|---|---|
| Modelo | Añadir getters |
| Controlador | Configurar columnas |
| Vista FXML | Añadir tabla |
| Servicio | Gestionar usuarios |
| CSS | Estilos visuales |

---

## 1. Crear o actualizar el modelo `Usuario`

Archivo:

```text
src/main/java/es/ies/puerto/models/Usuario.java
```

Código:

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

    public int getEdad() {
        return edad;
    }

    @Override
    public String toString() {
        return nombre + " (" + edad + ")";
    }
}
```

Los getters son obligatorios porque `TableView` los utiliza internamente.

---

## 2. Añadir el `TableView` al FXML

Archivo:

```text
src/main/resources/es/ies/puerto/usuario-view.fxml
```

Importar:

```xml
<?import javafx.scene.control.TableView?>
<?import javafx.scene.control.TableColumn?>
```

Añadir:

```xml
<TableView fx:id="usuariosTable"
           prefHeight="250">

    <columns>

        <TableColumn fx:id="nombreColumn"
                     text="Nombre"
                     prefWidth="200"/>

        <TableColumn fx:id="edadColumn"
                     text="Edad"
                     prefWidth="100"/>

    </columns>

</TableView>
```

---

## 3. Declarar el `TableView` en el controlador

Archivo:

```text
src/main/java/es/ies/puerto/controllers/UsuarioController.java
```

Importar:

```java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
```

Añadir:

```java
@FXML
private TableView<Usuario> usuariosTable;

@FXML
private TableColumn<Usuario, String> nombreColumn;

@FXML
private TableColumn<Usuario, Integer> edadColumn;
```

---

## 4. Crear la lista observable

Añadir:

```java
private final ObservableList<Usuario> usuariosObservable =
        FXCollections.observableArrayList();
```

---

## 5. Configurar las columnas

Dentro de `initialize()`:

```java
nombreColumn.setCellValueFactory(
        new PropertyValueFactory<>("nombre"));

edadColumn.setCellValueFactory(
        new PropertyValueFactory<>("edad"));
```

JavaFX buscará automáticamente:

```java
getNombre()
getEdad()
```

---

## 6. Asociar la lista observable al TableView

Dentro de `initialize()`:

```java
usuariosTable.setItems(usuariosObservable);
```

---

## 7. Añadir usuarios dinámicamente

Método:

```java
@FXML
public void agregarUsuario() {

    try {

        String nombre = nombreField.getText();
        int edad = Integer.parseInt(edadField.getText());

        Usuario usuario =
                new Usuario(nombre, edad);

        usuariosObservable.add(usuario);

        limpiarCampos();

    } catch (NumberFormatException exception) {

        Alert alert = new Alert(
                Alert.AlertType.ERROR);

        alert.setContentText(
                "La edad debe ser numérica");

        alert.showAndWait();
    }
}
```

---

## 8. Eliminar elementos seleccionados

Método:

```java
@FXML
public void eliminarUsuario() {

    Usuario seleccionado =
            usuariosTable.getSelectionModel()
                    .getSelectedItem();

    if (seleccionado != null) {

        usuariosObservable.remove(
                seleccionado);
    }
}
```

---

## 9. Obtener elemento seleccionado

```java
Usuario seleccionado =
        usuariosTable.getSelectionModel()
                .getSelectedItem();
```

---

## 10. Botones del FXML

```xml
<Button text="Agregar usuario"
        onAction="#agregarUsuario"/>

<Button text="Eliminar seleccionado"
        onAction="#eliminarUsuario"/>
```

---

## 11. Añadir estilos CSS

Archivo:

```text
src/main/resources/es/ies/puerto/css/estilos.css
```

Código:

```css
.table-view {
    -fx-border-color: #cccccc;
}

.button {
    -fx-font-weight: bold;
}
```

---

## 12. ¿Qué es lo importante aquí?

| Concepto | Función |
|---|---|
| TableView | Mostrar datos tabulares |
| TableColumn | Definir columnas |
| ObservableList | Actualización automática |
| PropertyValueFactory | Enlace columna-modelo |
| SelectionModel | Selección de filas |
| MVC | Separación de responsabilidades |

---

## 13. Recuerda qué

La relación principal es:

```java
ObservableList<Usuario> usuarios =
        FXCollections.observableArrayList();

usuariosTable.setItems(usuarios);
```

Cuando cambia la `ObservableList`, la tabla se actualiza automáticamente.

---

## 14. ¿Qué debe de suceder?

La aplicación permitirá:

- Añadir usuarios.
- Visualizarlos en tabla.
- Seleccionar filas.
- Eliminar usuarios.
- Mantener una interfaz profesional.

---

## 15. Errores comunes

## Tabla vacía

Verificar:

```java
usuariosTable.setItems(usuariosObservable);
```

---

### Columnas vacías

Comprobar:

```java
new PropertyValueFactory<>("nombre")
```

y que exista:

```java
getNombre()
```

---

### NullPointerException

Revisar:

- fx:id
- nombres iguales
- controlador correcto

---

### 16. En resumen

Para añadir un `TableView` en JavaFX se necesitan cuatro pasos principales:

1. Crear el `TableView` en el FXML.
2. Declarar las columnas en el controlador.
3. Configurar `PropertyValueFactory`.
4. Asociar una `ObservableList`.

La estructura fundamental es:

```java
TableView<Usuario>
TableColumn<Usuario, String>
ObservableList<Usuario>
```

> Con esto el proyecto evoluciona desde una interfaz básica a una aplicación JavaFX mucho más profesional y escalable.

</div>