<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Entornos de Desarrollo - Diagramas de Secuencia)

# Diagrama de Secuencia en UML

El **Diagrama de Secuencia** en UML es un tipo de diagrama conductual que se utiliza para representar la interacción entre objetos o componentes de un sistema a lo largo del tiempo. Este diagrama muestra cómo los objetos interactúan entre sí enviándose mensajes de un lado a otro, y cómo esos mensajes se suceden en el tiempo.

## Propósito
El objetivo principal del diagrama de secuencia es ilustrar el flujo de control de un sistema en términos de las interacciones entre los objetos, y cómo esos objetos se comunican entre sí para realizar una tarea o proceso específico.

## Componentes de un Diagrama de Secuencia

1. **Objetos (o instancias)**: Los objetos involucrados en la secuencia se representan como rectángulos en la parte superior del diagrama. Cada objeto es mostrado como una "línea de vida" (línea vertical) que indica su existencia durante la interacción.

2. **Líneas de vida**: Las líneas de vida son líneas verticales que se dibujan debajo de cada objeto y muestran su existencia en el tiempo. Las líneas de vida son activadas por la llegada de un mensaje.

3. **Mensajes**: Los mensajes son flechas horizontales que se dibujan entre las líneas de vida de los objetos. Estos mensajes representan la comunicación entre objetos. Los mensajes pueden ser sincrónicos (esperan una respuesta) o asincrónicos (no esperan respuesta inmediata).

4. **Actores**: Los actores representan entidades externas (como usuarios o sistemas) que interactúan con los objetos del sistema. Se representan en el lado izquierdo del diagrama.

5. **Activaciones**: Las activaciones son representadas por barras rectangulares que aparecen sobre la línea de vida de un objeto, indicando cuando un objeto está ejecutando una acción o proceso debido a un mensaje recibido.

6. **Retornos**: Los retornos son flechas punteadas que indican la respuesta de un objeto a un mensaje recibido. A menudo se usan para mostrar el resultado de una operación o método.

## 🛠 **Componentes y Símbolos Básicos**

En un diagrama de secuencia UML, encontramos los siguientes elementos:

- **📌 Actores**: Entidades externas que interactúan con el sistema (usuarios, otros sistemas).
- **🟦 Objetos**: Instancias de clases que participan en la secuencia.
- **📊 Líneas de vida**: Representan la existencia de un objeto o actor durante la interacción.
- **📩 Mensajes**: Comunicaciones entre objetos que indican la invocación de métodos o señales.
  - **Mensajes sincrónicos**: Se representan con una flecha sólida de una línea de vida a otra.
  - **Mensajes asincrónicos**: Se representan con una flecha con una línea en el extremo.
- **📏 Cuadros de activación**: Indican el período en que un objeto realiza una acción.
- **📦 Fragmentos combinados**: Permiten representar estructuras condicionales o paralelas en la interacción.
- **Retornos**: Se representan con flechas punteadas de la línea de vida de un objeto hacia otra, indicando el retorno de un valor o resultado.
- **Autollamadas**: Si un objeto envía un mensaje a sí mismo, se usa una flecha que regresa al mismo objeto.
---

## 🖥 **Ejemplo de Diagrama de Secuencia**

```mermaid
sequenceDiagram
    participant Usuario
    participant Sistema
    Usuario ->> Sistema: Ingresa credenciales
    Sistema ->> Usuario: Validar usuario
    Usuario ->> Sistema: Solicita información
    Sistema ->> Usuario: Retorna datos
 ```

## Compra en línea

Consideremos un diagrama de secuencia para el proceso de compra en línea de un producto:

```mermaid
sequenceDiagram
    participant Usuario
    participant "Sistema de Tienda" as Tienda
    participant "Sistema de Pago" as Pago

    Usuario ->> Tienda: 1. Iniciar sesión
    Tienda ->> Pago: 2. Verificar usuario
    
    Usuario ->> Tienda: 3. Seleccionar producto
    
    Usuario ->> Tienda: 4. Realizar pago
    Tienda ->> Pago: 5. Autorizar pago
    Pago -->> Tienda: Confirmación de pago

    Tienda ->> Usuario: 6. Confirmar compra
```

## Casos de Uso

El Diagrama de Secuencia se usa comúnmente en los siguientes escenarios:

- **Modelado de procesos de negocio**: Para mostrar cómo las entidades interactúan en el marco de un proceso de negocio.
- **Desarrollo de software**: Durante la fase de diseño, para especificar interacciones entre componentes del sistema.
- **Comunicación entre sistemas**: Para representar la interacción entre diferentes sistemas o subsistemas en un entorno distribuido.

</div>