<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Clases Abstractas  en Java)

Las **clases abstractas** en Java son clases que no pueden ser instanciadas directamente. Su propósito es servir como una base común para otras clases y proporcionar un marco de trabajo que las subclases pueden extender. Pueden contener tanto métodos abstractos (sin implementación) como métodos con implementación concreta.

## **1. Definición**

Una **clase abstracta** es una clase que puede contener tanto métodos abstractos (métodos sin implementación) como métodos concretos (métodos con implementación). No se puede instanciar directamente una clase abstracta, pero puede ser utilizada como una clase base para otras clases que la extienden.

## **2. Características de una Clase Abstracta**

- **Métodos Abstractos**: Una clase abstracta puede declarar métodos abstractos, que son métodos sin cuerpo. Las subclases que heredan de la clase abstracta deben proporcionar una implementación de estos métodos.
- **Métodos Concretos**: Además de métodos abstractos, una clase abstracta puede tener métodos con implementación. Estos métodos no requieren que las subclases los sobrescriban, pero pueden hacerlo si lo desean.
- **No se puede Instanciar**: Una clase abstracta no se puede instanciar directamente, lo que significa que no puedes crear objetos de una clase abstracta. Sin embargo, puedes crear instancias de las clases concretas que heredan de la clase abstracta.
- **Herencia Simple**: A diferencia de las interfaces, una clase puede extender solo una clase abstracta, ya que Java no permite la herencia múltiple de clases.
- **Constructores**: Las clases abstractas pueden tener constructores, los cuales son invocados por las clases hijas cuando se crean instancias de ellas.

## **3. Ventajas de las Clases Abstractas**

- **Reutilización de Código**: Puedes proporcionar una implementación común para métodos que serán compartidos por todas las subclases. Esto reduce la duplicación de código.
- **Flexibilidad**: Permiten que las subclases puedan sobrescribir solo los métodos que necesiten modificar, mientras mantienen la funcionalidad común proporcionada por la clase abstracta.
- **Encapsulamiento y Herencia**: Permiten encapsular comportamientos comunes en una jerarquía de clases, lo que facilita la organización y el mantenimiento del código.

## **4. Diferencias entre Clases Abstractas e Interfaces**

- **Métodos Abstractos**: Ambas pueden tener métodos abstractos, pero las clases abstractas también pueden tener métodos con implementación, mientras que las interfaces (en versiones anteriores a Java 8) no pueden.
- **Herencia**: Una clase puede extender solo una clase abstracta, pero puede implementar varias interfaces. Las clases abstractas permiten una **herencia simple**, mientras que las interfaces permiten **herencia múltiple**.
- **Campos de Datos**: Las clases abstractas pueden tener variables de instancia (atributos), mientras que las interfaces solo pueden tener constantes (en versiones anteriores a Java 8).
- **Métodos Estáticos**: Las interfaces pueden tener métodos estáticos, pero las clases abstractas no están limitadas en cuanto a los métodos estáticos.

## **5. Uso de las Clases Abstractas**

Las clases abstractas son ideales para modelar jerarquías de clases en las que quieres proporcionar una funcionalidad común pero aún permitir que las subclases implementen su propio comportamiento específico. Son útiles cuando:

- Deseas tener métodos con una implementación común que puedan ser reutilizados por todas las subclases.
- Quieres definir un conjunto de comportamientos obligatorios que las subclases deben implementar.
- Quieres asegurarte de que las clases que extienden la clase abstracta sigan un contrato común.

## **6. Cuándo Usar una Clase Abstracta**

Usa una clase abstracta cuando:

- Quieras proporcionar una implementación base para clases relacionadas.
- Tienes métodos comunes que las subclases deben compartir.
- Necesitas proporcionar una implementación predeterminada de algunos métodos, pero permitir que otros métodos sean sobrescritos por las subclases.

## **7. Resumen**

- Una **clase abstracta** es una clase que no se puede instanciar directamente, pero puede contener tanto métodos abstractos como concretos.
- Proporciona un mecanismo para **reutilizar código** y **organizar jerarquías de clases**.
- Las subclases de una clase abstracta deben proporcionar implementaciones para los métodos abstractos, pero pueden usar los métodos concretos de la clase base sin necesidad de sobrescribirlos.

Las clases abstractas son una herramienta poderosa en Java para crear jerarquías de clases flexibles y mantener el código limpio y modular.

## Ejemplos

Pulsa el siguiente [enlace](https://www.w3schools.com/java/java_abstract.asp) para ver y practicar algunos ejemplos.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles

</div>