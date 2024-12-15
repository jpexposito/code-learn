<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios (Micro Servicios))


<img src=images/micro-servicios.png width="500">

Los **microservicios** son un estilo arquitectónico utilizado en el desarrollo de software. Este enfoque divide una aplicación en componentes pequeños e independientes que funcionan como servicios individuales. Cada microservicio se centra en realizar una función o tarea específica, y juntos colaboran para construir aplicaciones más grandes y complejas.

## ¿Qué es la arquitectura monolítica?

Antes de entender los microservicios, es importante conocer la **arquitectura monolítica**, ya que ambas son enfoques contrastantes.

En una arquitectura monolítica, toda la funcionalidad de la aplicación se construye como un solo bloque. Todos los módulos (gestión de usuarios, pagos, inventarios, etc.) están interconectados y ejecutan sus funciones en un único programa o servicio. 

### Características de la arquitectura monolítica:

1. **Centralización**:  
   Toda la lógica del negocio, las interfaces de usuario y el acceso a datos están en una única base de código y se ejecutan en un único proceso.

2. **Despliegue único**:  
   La aplicación se construye y se implementa como una sola unidad. Esto significa que cualquier cambio, por pequeño que sea, requiere reconstruir y desplegar toda la aplicación.

3. **Fuerte acoplamiento**:  
   Las distintas partes de la aplicación están estrechamente vinculadas, lo que dificulta la modificación o actualización de una sola parte.

### Ventajas de la arquitectura monolítica:

- **Simplicidad inicial**:  
  Es más fácil de desarrollar y gestionar cuando la aplicación es pequeña y el equipo es reducido.

- **Menor complejidad**:  
  Toda la lógica está en un único lugar, lo que simplifica la depuración y la gestión.

- **Rendimiento eficiente**:  
  Al estar en un único proceso, la comunicación entre módulos es más rápida.

### Desventajas de la arquitectura monolítica:

1. **Escalabilidad limitada**:  
   Escalar una parte de la aplicación implica escalar todo el sistema, lo que puede ser costoso e ineficiente.

2. **Falta de flexibilidad tecnológica**:  
   Todas las partes de la aplicación deben usar la misma tecnología, incluso si no es la mejor para ciertas tareas.

3. **Despliegue lento y riesgoso**:  
   Cambios pequeños requieren desplegar todo el sistema, aumentando el riesgo de fallos.

4. **Mantenimiento complicado**:  
   A medida que la aplicación crece, la base de código puede volverse difícil de entender y modificar.

---

## ¿Cómo se comparan los microservicios con la arquitectura monolítica?

| Aspecto                  | Arquitectura Monolítica                                   | Microservicios                                      |
|--------------------------|---------------------------------------------------------|---------------------------------------------------|
| **Estructura**           | Un solo bloque, todo en un mismo lugar.                 | Dividida en pequeños servicios independientes.    |
| **Despliegue**           | Un único despliegue para toda la aplicación.            | Despliegue independiente por cada microservicio.  |
| **Escalabilidad**        | Escala todo el sistema a la vez.                        | Escala solo los servicios necesarios.             |
| **Flexibilidad tecnológica** | Tecnología única para toda la aplicación.              | Diferentes tecnologías para cada servicio.         |
| **Resiliencia**          | Si falla un componente, falla toda la aplicación.       | Si falla un servicio, los demás siguen operativos.|
| **Complejidad inicial**  | Más sencillo de configurar y desarrollar al principio.  | Requiere una mayor planificación desde el inicio. |

---

## Características principales de los microservicios

1. **Descentralización**:  
   Cada microservicio opera de forma autónoma y no depende directamente de otros. Esto permite desarrollar, implementar y escalar componentes de manera independiente.

2. **Independencia tecnológica**:  
   Los microservicios pueden estar desarrollados en diferentes lenguajes de programación o tecnologías. Por ejemplo, un servicio podría usar Python y otro Java.

3. **Escalabilidad**:  
   Es posible escalar solo los servicios que necesitan mayor capacidad, en lugar de toda la aplicación.

4. **Comunicación ligera**:  
   Los microservicios se comunican entre sí generalmente mediante APIs ligeras, como REST o gRPC, o sistemas de mensajería.

5. **Tolerancia a fallos**:  
   Si un microservicio falla, el resto de la aplicación puede continuar funcionando, ya que están desacoplados.

---

## Ventajas de los microservicios

- **Flexibilidad para los equipos de desarrollo**:  
  Los equipos pueden trabajar en diferentes microservicios sin interferir en el trabajo de otros.
  
- **Rapidez en el desarrollo y despliegue**:  
  Los servicios pueden implementarse y actualizarse por separado, lo que acelera el ciclo de vida del software.
  
- **Reutilización de componentes**:  
  Algunos servicios pueden usarse en diferentes aplicaciones.

- **Mejor adaptabilidad al cambio**:  
  Es más fácil modificar o reemplazar partes específicas de una aplicación.

---

## Desafíos de los microservicios

1. **Gestión de la complejidad**:  
   Aunque cada microservicio es sencillo, coordinar y administrar varios servicios puede ser complicado.

2. **Comunicación y latencia**:  
   La comunicación entre servicios introduce una sobrecarga que puede afectar el rendimiento.

3. **Monitorización y depuración**:  
   Seguir el rastro de errores o problemas en un entorno distribuido es más difícil.

4. **Dependencias en la red**:  
   Si las redes no son confiables, pueden surgir problemas en las interacciones entre microservicios.

---

## Ejemplo de aplicación de microservicios

Una tienda en línea puede estructurarse en microservicios como estos:

- Un microservicio para la gestión de usuarios (registro, inicio de sesión, etc.).
- Un microservicio para el catálogo de productos.
- Un microservicio para el carrito de compras.
- Un microservicio para el procesamiento de pagos.

Cada uno funciona de forma independiente, pero se comunican entre sí para ofrecer la experiencia completa.

---

En resumen, los microservicios ofrecen una forma eficiente y moderna de construir aplicaciones complejas, especialmente en comparación con la arquitectura monolítica, aunque requieren una planificación cuidadosa para manejar los retos asociados.

> [Servicios soap y rest](SOAP-REST.md).

</div>

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>