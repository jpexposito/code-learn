<div align="justify">

<img src=../../../../../images/coding-book.png width="40"> Code & Learn (Entornos de Desarrollo: Diagrama de Casos de Uso - Sistema de Biblioteca)

**Descripción**: El sistema de biblioteca permite a los usuarios buscar y solicitar préstamos de libros, y al personal de la biblioteca gestionar dichos préstamos. Además, el sistema puede enviar notificaciones y recordatorios de devolución cuando el préstamo está próximo a vencer, utilizando una relación de **inclusión** (`<<include>>`) y de **extensión** (`<<extend>>`).

---

## Elementos del Diagrama de Casos de Uso

| Elemento                          | Descripción                                                                                          |
|-----------------------------------|------------------------------------------------------------------------------------------------------|
| **Actor: Usuario**                | Cliente de la biblioteca que busca y solicita préstamos de libros.                                   |
| **Actor: Bibliotecario**          | Personal de la biblioteca que registra y gestiona los préstamos de libros.                           |
| **Actor: Sistema de Notificaciones** | Sistema externo encargado de enviar recordatorios de devolución.                                |
| **Caso de Uso: Buscar Libro**     | Permite al Usuario buscar libros en el catálogo.                                                    |
| **Caso de Uso: Solicitar Préstamo** | Permite al Usuario solicitar el préstamo de un libro.                                             |
| **Caso de Uso: Registrar Préstamo** | Permite al Bibliotecario registrar el préstamo de un libro.                                      |
| **Caso de Uso: Enviar Notificación** | Envía una notificación al Usuario cuando se registra un préstamo (`<<include>>`).                  |
| **Caso de Uso: Recordatorio de Devolución** | Extiende el caso de uso "Enviar Notificación" cuando el préstamo está por vencer (`<<extend>>`). |

---

## Relaciones entre los Elementos

| Relación                                          | Descripción                                                                                     |
|---------------------------------------------------|-------------------------------------------------------------------------------------------------|
| **Usuario → Buscar Libro**                        | Asociación: El Usuario interactúa con el sistema para buscar libros en el catálogo.             |
| **Usuario → Solicitar Préstamo**                  | Asociación: El Usuario interactúa con el sistema para solicitar un préstamo de libro.           |
| **Bibliotecario → Registrar Préstamo**            | Asociación: El Bibliotecario registra un préstamo cuando se solicita un libro.                 |
| **Registrar Préstamo → Enviar Notificación**      | **Inclusión** (`<<include>>`): Al registrar el préstamo, se envía una notificación automática al Usuario. |
| **Enviar Notificación → Recordatorio de Devolución** | **Extensión** (`<<extend>>`): Se envía un recordatorio adicional si el préstamo está próximo a vencer. |

---

## Explicación de las Relaciones de Inclusión (`<<include>>`) y Extensión (`<<extend>>`)

- **Incluir Enviar Notificación**: Cada vez que el Bibliotecario registra un préstamo (caso de uso "Registrar Préstamo"), se activa automáticamente el caso de uso "Enviar Notificación" como una operación obligatoria para informar al Usuario sobre el préstamo.
  
- **Extender con Recordatorio de Devolución**: **Recordatorio de Devolución** extiende el caso de uso **Enviar Notificación**. La extensión (`<<extend>>`) se activa solo cuando el préstamo está próximo a vencer, generando un recordatorio adicional para el Usuario sobre la fecha de devolución.

---

## Diagrama Completo

**Descripción Visual**: A continuación se representa el diagrama de casos de uso, con los actores y los casos de uso, y las relaciones de asociación, inclusión y extensión.

---

Este modelo de diagrama de casos de uso para un sistema de biblioteca ayuda a visualizar cómo interactúan los actores (Usuario, Bibliotecario y Sistema de Notificaciones) con las funcionalidades clave, y resalta los escenarios opcionales que extienden la funcionalidad básica, como los recordatorios de devolución en préstamos próximos a vencer.

</div>