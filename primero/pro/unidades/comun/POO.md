<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Características Principales de la Programación Orientada a Objetos (POO))

<img src=images/caracteristicas.png width="400">

La **Programación Orientada a Objetos (POO)** es un paradigma de programación basado en objetos, que se definen por sus atributos y comportamientos. Las principales características de la POO son:

---

## 1. Abstracción

- **Definición**: La abstracción es el proceso de ocultar los detalles de implementación y mostrar solo la funcionalidad relevante al usuario.
- **Objetivo**: Permitir a los desarrolladores centrarse en los aspectos esenciales de un objeto, ignorando los detalles innecesarios.
- **Cómo se logra**: A través de clases y objetos, utilizando métodos y propiedades públicas para exponer solo lo necesario.

---

## 2. Encapsulamiento

- **Definición**: El encapsulamiento es la ocultación del estado interno de un objeto y la protección de sus datos, proporcionando acceso controlado mediante métodos.
- **Objetivo**: Asegurar que los datos internos de un objeto solo puedan ser modificados de manera controlada, previniendo cambios no deseados o incorrectos.
- **Cómo se logra**: Definiendo atributos privados y proporcionando métodos públicos (getters y setters) para acceder a esos atributos.

---

## 3. Herencia

- **Definición**: La herencia es el mecanismo por el cual una clase puede heredar propiedades y comportamientos de otra clase.
- **Objetivo**: Reutilizar código y crear una jerarquía de clases. Las subclases heredan los métodos y atributos de una superclase.
- **Cómo se logra**: Utilizando la palabra clave `extends` en Java.

---

## 4. Polimorfismo

- **Definición**: El polimorfismo permite que un objeto o método adopte múltiples formas. Se refiere a la capacidad de un objeto de una clase derivada de ser tratado como un objeto de la clase base.
- **Objetivo**: Mejorar la flexibilidad y extensibilidad del código, permitiendo que un mismo método se comporte de manera diferente según el objeto que lo invoque.
- **Cómo se logra**: 
  - **Polimorfismo en tiempo de compilación**: Se logra mediante la sobrecarga de métodos.
  - **Polimorfismo en tiempo de ejecución**: Se logra mediante la sobrescritura (override) de métodos.

---

## 5. Composición

- **Definición**: La composición es un tipo de relación entre objetos donde un objeto contiene otros objetos como parte de su estructura. Es una forma de construir objetos complejos a partir de objetos más simples.
- **Objetivo**: Representar relaciones "tiene un" (en lugar de "es un" que corresponde a la herencia) y crear objetos más modulares.
- **Cómo se logra**: Usando atributos de tipo objeto dentro de una clase.

---

## 6. Abstracción (adicional)

- **Definición**: La abstracción también puede ser implementada mediante clases abstractas o interfaces, que definen métodos sin implementación, dejando que las subclases concreten los detalles.
- **Objetivo**: Reducir la complejidad del sistema al proporcionar solo las características esenciales de un objeto.
- **Cómo se logra**: Utilizando clases abstractas o interfaces para definir métodos que no se implementan hasta que una subclase o clase concreta los utilice.

---

## Resumen

1. **Abstracción**: Oculta los detalles internos y expone solo lo necesario.
2. **Encapsulamiento**: Protege los datos internos de un objeto y proporciona acceso controlado.
3. **Herencia**: Permite que una clase herede propiedades y comportamientos de otra.
4. **Polimorfismo**: Permite que objetos de diferentes clases respondan a un mismo mensaje de manera distinta.
5. **Composición**: Construye objetos complejos a partir de objetos más simples, representando relaciones "tiene un".
6. **Abstracción (más)**: Se implementa con clases abstractas o interfaces para definir métodos sin implementación.

Estas características facilitan la creación de software modular, flexible, reutilizable y fácil de mantener dentro de la **Programación Orientada a Objetos (POO)**.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles

</div>