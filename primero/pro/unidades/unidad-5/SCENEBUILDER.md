<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Trabajando con Interfaces-SceneBuilder)

<div align="center">
    <img src=images/SceneBuilderLogo.png width="200">
</div>

**SceneBuilder** es un editor **WYSIWYG** (lo que ves es lo que obtienes) de ficheros __FXML__ multiplataforma que hace que el diseño de interfaces usando FXML para JavaFX se convierta en una tarea muy sencilla. ___SceneBuilder nos permite diseñar la interfaz de una forma visual arrastrando los diferentes controles, paneles de diseño, etc. a dicha interfaz para así crear la estructura de la misma___. Entre otras cosas permite:

- Modificar las diferentes propiedades de los elementos que la componen.
- Definir el controlador para dicha vista.
- Asignar identificadores a los diferentes componentes para luego poder acceder a ellos desde el controlador.
- Definir los manejadores para los principales eventos para un componente dado.
La herramienta la puedes descargar desde el siguiente enlace: [SceneBuilder](https://gluonhq.com/products/scene-builder/).

Para poder trabajar debemos abrir los archivos con extensión ___*.fxml___.

En la siguiente imagen podemos ver el aspecto de SceneBuilder para el fichero **app-init.fxml** del apartado anterior.

<div align="center">
    <img src=images/app-init.fxml.png width="400">
</div>

Como podéis observar la interfaz cuenta con varios paneles:

- **Panel Library** situado arriba a la izquierda. En el que encontramos los diferentes controles, paneles de diseño, etc. agrupados por categorías y que podremos arrastrar al panel central de diseño.
Panel Document situado abajo a la izquierda. Este panel contiene dos categorías: una primera nombrada como Hierarchy y en la que podremos observar la jerarquía de nodos de nuestro diseño y una segunda categoría nombrada como Controller en la que podemos indicar el controlador para este fichero .fxml y en la que nos aparecen los controles que tenemos mapeados entre el fichero .fxml y el controlador (por medio de las anotaciones @FXML del controlador).
Panel Inspector situado a la derecha. En este panel podremos ir cambiando las diferentes propiedades del control que tengamos seleccionado. Se agrupa en tres categorías: una primera categoría nombrada como Properties en la que podremos cambiar las propiedades generales de dicho control, otra segunda categoría nombrada como Layout en la que podremos cambiar propiedades que afectan al diseño del control como los margenes, el relleno, etc. y una tercera categoría nombrada como Code en la que podremos asignar un identificador al control (que luego deberemos mapear en el controlador por medio de las anotaciones **@FXML**) y en la que podremos indicar el nombre del método que hará de manejador para un evento dado de dicho control (que también deberemos tener mapeado en el controlador por medio de anotaciones **@FXML**).
- **Un panel central** de diseño en el que visualmente iremos diseñando la interfaz arrastrando y soltando controles, paneles de diseño, etc.
El uso de este editor es bastante intuitivo y no me detendré más en su manejo, porque con los conocimientos adquiridos hasta ahora no te debería plantear ningún problema, ya que si has sido capaz de crear las interfaces de los ejemplos mediante código, hacerlo de esta forma debe ser pan comido.

</div>