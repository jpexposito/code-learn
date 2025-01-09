<div align="justify">

# Diagramas de Páquetes

## ¿Qué son los Diagramas de Paquetes?

Los diagramas de paquetes son un tipo de diagrama en UML que se utiliza para mostrar la organización, la estructura y las dependencias entre los paquetes en un sistema. Un paquete es una agrupación lógica de elementos como clases, interfaces u otros subpaquetes dentro de un proyecto.

## Elementos Principales

1. **Paquetes**:
   - Representan una agrupación lógica de elementos UML, como clases, interfaces o subpaquetes.
   - Se visualizan como un rectángulo con una pestaña superior izquierda.

```text
+-------------+
|  Paquete A  |
+-------------+
| (contenidos)|
+-------------+

```

2. **Relaciones entre Paquetes**:
   - **Dependencias**: Representan una relación en la que un paquete depende de otro para funcionar.
   - **Generalización**: Indica una relación jerárquica entre paquetes, donde un paquete hereda de otro.

3. **Contenido**:
  
    Los paquetes pueden contener elementos UML como clases, interfaces u otros paquetes. Estos elementos se organizan para reflejar la modularidad del sistema.

   1. **Notación Gráfica**
   2. Paquetes y Dependencias

    ```text
    +-------------+      «dependencia»
    |  Paquete A  | ---------------------> +-------------+
    +-------------+                        |  Paquete B  |
                                          +-------------+
    ```

   2. Paquetes Jerárquicos

    ```text
    +-------------+
    |  Paquete A  |
    | +---------+ |
    | | Sub-A1  | |
    | +---------+ |
    +-------------+
    ```

3. Generalización entre Paquetes

    ```text
    +-------------+      ---------------------->  +-------------+
    |  Paquete A  |       «generalización»        |  Paquete B  |
    +-------------+                               +-------------+
    ```

### Diferencias entre Generalización y Dependencia

| Aspecto                  | Generalización                                           | Dependencia                                               |
|--------------------------|----------------------------------------------------------|----------------------------------------------------------|
| **Definición**           | Es una relación jerárquica entre un elemento "general" y un elemento "específico". | Representa que un elemento utiliza o depende del funcionamiento de otro. |
| **Tipo de relación**     | Relación estructural, permanente y fuerte.               | Relación temporal, débil y menos acoplada.               |
| **Dirección**            | Va del elemento específico al general.                   | Va del elemento que depende al elemento del que depende. |
| **Notación**             | Línea continua con una flecha hueca apuntando al elemento general. | Línea punteada con una flecha apuntando al elemento dependido. |
| **Significado**          | Representa herencia, es decir, que un elemento hereda características y comportamientos del otro. | Representa que un cambio en el elemento dependido podría afectar al que depende. |

### Ejemplo de Generalización y Dependencia

#### Generalización

La **generalización** representa una relación jerárquica donde un elemento específico hereda características de un elemento general.  
Por ejemplo:

- Clase `Vehículo` es general.
- Clases específicas como `Coche` y `Motocicleta` heredan de `Vehículo`.

```text
+----------------+
|   Vehículo     |
+----------------+
       ▲
       |
+------+-------+
|              |
|    Coche     |    Motocicleta
+--------------+    +--------------+
```

#### Dependencia

La dependencia es una relación entre dos elementos en UML en la que un elemento (el dependiente) utiliza o depende de otro elemento (el independiente) para su funcionamiento. Esta relación indica que un cambio en el elemento independiente puede afectar al elemento dependiente.

##### Características de la Dependencia

- **Relación débil y temporal**: La dependencia no implica una relación estructural permanente entre los elementos.
- **Dirección**: Va desde el elemento dependiente hacia el elemento independiente.
- **Notación**: Se representa mediante una línea punteada con una flecha que apunta al elemento del que depende.
- **Uso**: Se utiliza para mostrar que un elemento usa, conoce o interactúa con otro elemento.

##### Ejemplo Común

Un ejemplo típico de dependencia es cuando una clase utiliza los métodos o datos de otra clase en su implementación.

Por ejemplo, una clase `Orden` depende de una clase `Cliente`, porque una orden necesita datos del cliente (como su nombre o dirección) para ser procesada.

```text
+------------+           +------------+
|   Cliente  |<----------|   Orden    |
+------------+  dependencia (usa)    +------------+
```

## Beneficios de los Diagramas de Paquetes

- **Organización**: Facilitan la estructuración y modularización de sistemas complejos.
- **Comprensión**: Ayudan a visualizar las dependencias y relaciones entre diferentes componentes del sistema.
- **Escalabilidad**: Permiten diseñar sistemas modulares que son más fáciles de mantener y escalar.

<!--
Los __diagramas de paquetes__ son una herramienta de modelado en UML que se utiliza para __organizar y estructurar__ _elementos en una aplicación_. Se centran en la agrupación lógica de elementos relacionados, proporcionando una vista jerárquica y modular del sistema.

## Estructura Básica del Diagrama

Un diagrama de paquetes está compuesto por "__paquetes__" que representan unidades de organización y agrupación. Estos paquetes pueden contener elementos como __clases, interfaces, subpaquetes__, y __otros elementos__ del sistema.


<img src="https://diagramasuml.com/wp-content/uploads/2018/08/paq2.png"/>

Un paquete además puede contener otro paquete.

Como ejemplo, un paquete que contiene otros paquetes tendría la siguiente representación:

<img src="https://diagramasuml.com/wp-content/uploads/2018/08/paq6.png" />

### Dependencia entre paquetes

Una dependencia entre paquetes representan que un paquete necesita de los elementos de otro paquete para poder funcionar con normalidad.

Se representa con una __flecha discontinua__ que va desde el paquete que requiere la función hasta el paquete que ofrece esa función.

<img src="https://diagramasuml.com/wp-content/uploads/2018/08/paq11.png" />

### Ejemplo

Ejemplo de paquete que incluye otros paquetes
En esta imagen se dice que el Paquete Origen depende del Paquete Destino para dar su servicio.

<img src="https://diagramasuml.com/wp-content/uploads/2018/08/paq12.png" />
-->

### Ejemplo

A continuación se muestra, a modo de ejemplo, un diagrama de paquetes de una aplicación:

- La aplicación, que tiene como finalidad la recepción y gestión de quejas y sugerencias, estaría compuesta por los siguientes paquetes:
  - Capa de presentación. Incluye a su vez los paquetes Interfaz de Usuario e Interfaz Admin
  - Capa de Lógica de Negocio, con los siguientes paquetes:
    - Subsistema de recepción de dudas y sugerencias.
     - Subsistema de asignación de responsable.
     - Subsistema de creación de informes.
  - Gestor documental.
     - Subsistema de gestión de usuarios.
   - Envío de notificaciones.
   - Base de datos.
   - CRM.
   - DataWarehouse.

>__Nota__:Como puedes observar, cada uno de los subpaquetes podría expandirse en otros paquetes, hasta llegar al punto de tener unos paquetes primitivos que no pueden volver a explotarse.

<img src="https://diagramasuml.com/wp-content/uploads/2018/08/psq15.png">

>__Nota__: __mermaid__ no soporta diagrama de paquetes pero puedes simularlo de la siguiente forma.
---


</div>