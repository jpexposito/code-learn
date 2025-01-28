<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Interfaces  en Java)

En Java, las interfaces y las clases abstractas son herramientas clave para la abstracción y la herencia en la programación orientada a objetos. Ambas se utilizan para definir comportamientos comunes que pueden ser compartidos por varias clases, pero tienen diferencias significativas en su implementación y propósito.

## **1.1. Definición**

Una **interfaz** en Java es un contrato que define un conjunto de métodos que una clase debe implementar. Una interfaz no puede tener implementación de métodos, solo sus firmas. Desde Java 8, las interfaces pueden tener métodos con implementación utilizando **métodos predeterminados** (default methods) y **métodos estáticos**.

## **1.2. Características de una Interfaz**

- **Métodos Abstractos**: Los métodos definidos en una interfaz son por defecto abstractos, es decir, no tienen cuerpo.
- **Implementación obligatoria**: Las clases que implementan una interfaz deben proporcionar la implementación para todos los métodos abstractos de la interfaz.
- **No se pueden instanciar**: No se pueden crear objetos directamente de una interfaz.
- **Herencia múltiple**: Una clase puede implementar múltiples interfaces, lo que permite simular herencia múltiple.
- **Métodos predeterminados (default methods)**: Introducidos en Java 8, los métodos predeterminados permiten proporcionar una implementación por defecto en la interfaz.
- **Métodos estáticos**: Las interfaces también pueden tener métodos estáticos, que pueden ser invocados sin necesidad de implementar la interfaz.

## **1.3. Ventajas de las Interfaces**

- Permiten **herencia múltiple**, ya que una clase puede implementar varias interfaces.
- **Flexibilidad**: Pueden ser implementadas por cualquier clase, independientemente de la jerarquía de clases.
- Permiten **la separación de preocupaciones** mediante la definición de contratos y comportamientos comunes.

## Ejemplos

Pulsa el siguiente [enlace](https://www.w3schools.com/java/java_interface.asp) para ver y practicar algunos ejemplos.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles

</div>