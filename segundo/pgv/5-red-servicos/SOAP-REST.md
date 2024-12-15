<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios (SOAP-REST))

<div align="center">

<img src=images/web-services.png width="400">

</div>

Los servicios web son una forma de comunicación entre aplicaciones distribuidas a través de redes, generalmente utilizando Internet. Dos enfoques populares para implementar servicios web son **SOAP** y **REST**.

---

## ¿Qué es SOAP?

**SOAP (Simple Object Access Protocol)** es un protocolo estándar para el intercambio de información estructurada en servicios web. SOAP utiliza XML como formato para los mensajes y está diseñado para funcionar en cualquier red.

### Características principales de SOAP:

1. **Estandarización**:  
   SOAP es un protocolo basado en estándares definidos por la W3C, lo que garantiza interoperabilidad entre diferentes sistemas.

2. **Formato XML**:  
   Todos los mensajes SOAP están escritos en XML, lo que asegura su compatibilidad entre diferentes plataformas.

3. **Contrato definido**:  
   SOAP utiliza un archivo **WSDL (Web Services Description Language)** que describe las operaciones disponibles en el servicio y cómo interactuar con él.

4. **Transporte**:  
   SOAP suele funcionar sobre HTTP/HTTPS, pero también puede usar otros protocolos como SMTP o TCP.

### Ventajas de SOAP:

- **Estándares bien definidos**:  
  Es ideal para aplicaciones empresariales que requieren seguridad, transacciones o confiabilidad.

- **Compatibilidad con WS-Security**:  
  Proporciona un marco robusto para manejar autenticación, cifrado y firmas digitales.

- **Independencia de transporte**:  
  No está limitado a HTTP; puede utilizarse con otros protocolos.

### Desventajas de SOAP:

- **Complejidad**:  
  Su formato basado en XML y el uso de WSDL lo hacen más complejo de implementar.

- **Mayor sobrecarga**:  
  Los mensajes SOAP son más pesados debido al uso de XML y los encabezados extensos.

---

## ¿Qué es REST?

**REST (Representational State Transfer)** es un estilo arquitectónico para servicios web que se centra en los recursos y sus representaciones. REST utiliza métodos HTTP estándar para interactuar con los recursos, como **GET**, **POST**, **PUT** y **DELETE**.

### Características principales de REST:

1. **Basado en recursos**:  
   Los recursos (como un usuario, producto o pedido) se identifican mediante una URL única.

2. **Operaciones estándar de HTTP**:  
   Utiliza métodos HTTP para realizar operaciones sobre los recursos.

3. **Formatos flexibles**:  
   REST puede usar JSON, XML, texto plano, HTML, etc., pero JSON es el más común debido a su simplicidad y eficiencia.

4. **Sin estado (stateless)**:  
   Cada solicitud REST es independiente; el servidor no mantiene información de estado sobre las solicitudes previas.

### Ventajas de REST:

- **Simplicidad**:  
  Es más fácil de implementar y consumir en comparación con SOAP.

- **Mayor eficiencia**:  
  Los mensajes son más ligeros, especialmente cuando se utiliza JSON.

- **Flexibilidad**:  
  Permite usar diferentes formatos de datos y es compatible con múltiples plataformas y lenguajes.

- **Escalabilidad**:  
  La independencia de las solicitudes facilita la escalabilidad de los sistemas.

### Desventajas de REST:

- **Sin estado**:  
  La falta de estado puede complicar ciertas interacciones que requieren seguimiento, como transacciones.

- **Falta de estándares para seguridad avanzados**:  
  Aunque REST puede implementar seguridad mediante HTTPS, no tiene un marco tan robusto como WS-Security en SOAP.

---

## Comparación entre SOAP y REST

| Aspecto                     | SOAP                                            | REST                                          |
|-----------------------------|------------------------------------------------|----------------------------------------------|
| **Modelo**                  | Protocolo estándar definido por W3C.            | Estilo arquitectónico.                       |
| **Formato de datos**        | Solo XML.                                       | JSON, XML, HTML, texto, etc.                 |
| **Independencia de estado** | Estado puede ser manejado.                      | Sin estado (stateless).                      |
| **Velocidad**               | Más lento debido al uso de XML.                 | Más rápido, especialmente con JSON.          |
| **Seguridad**               | WS-Security para seguridad avanzada.            | Basado en HTTPS (menos robusto).             |
| **Complejidad**             | Más complejo de implementar y mantener.         | Más sencillo y ligero.                       |
| **Casos de uso**            | Aplicaciones empresariales con alta seguridad o transacciones complejas. | Servicios web ligeros y escalables.         |

---

## Cuándo usar SOAP o REST

- **SOAP**:  
  Ideal para aplicaciones que necesitan:
  - Seguridad avanzada (WS-Security).
  - Transacciones complejas y confiabilidad (por ejemplo, banca o aplicaciones empresariales).
  - Soporte para múltiples protocolos de transporte (no solo HTTP).

- **REST**:  
  Ideal para aplicaciones que necesitan:
  - Simplicidad y rapidez en el desarrollo.
  - Uso eficiente de recursos en redes limitadas.
  - Escalabilidad y flexibilidad (por ejemplo, servicios móviles y APIs públicas).

---

En resumen, SOAP y REST son herramientas poderosas, pero cada una es adecuada para diferentes necesidades. REST es generalmente preferido por su simplicidad y rendimiento, mientras que SOAP es más adecuado para entornos empresariales con requisitos complejos.

> [Spring](SPRING.md.md).

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles.

</div>