<div align="justify">

## <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios)

### Utilización de técnicas de programación segura

<div align="center">
    <img src=images/server-security.png width="400">
</div>

## **Tipos de Seguridad en Servicios**

La seguridad en servicios es un aspecto crítico para garantizar que solo usuarios o sistemas autorizados puedan acceder a ciertas funcionalidades o datos. Existen diferentes enfoques y mecanismos de seguridad que se pueden utilizar dependiendo del tipo de aplicación y las necesidades específicas de seguridad.

### **1. Autenticación**

La autenticación es el proceso de verificar la identidad de un usuario o sistema. Algunos de los mecanismos más comunes incluyen:

#### **1.1. Autenticación Básica (Basic Authentication)**

- **Descripción**: Este es un mecanismo simple en el que el cliente proporciona un nombre de usuario y una contraseña en cada solicitud HTTP.
- **Uso común**: Usado en aplicaciones pequeñas o servicios internos.
- **Desventajas**: No es seguro sin cifrado (debe usarse con HTTPS) ya que las credenciales se envían con cada solicitud.

#### **1.2. Autenticación con JWT (JSON Web Tokens)**

- **Descripción**: JWT es un estándar abierto que se utiliza para transmitir información de manera segura entre las partes. Se usa típicamente para autenticar a los usuarios en aplicaciones web y móviles.
- **Uso común**: Autenticación en aplicaciones móviles, servicios RESTful, aplicaciones SPA (Single Page Applications).
- **Ventajas**: Tokens de corta vida, no se requiere mantener sesión en el servidor, escabilidad.
- **Desventajas**: Si no se maneja adecuadamente, puede ser vulnerable a ataques de tipo *token replay*.

#### **1.3. OAuth 2.0 (Autorización Delegada)**

- **Descripción**: OAuth 2.0 es un marco de autorización que permite a las aplicaciones obtener acceso limitado a los recursos de un usuario en otro servicio, sin necesidad de compartir las credenciales.
- **Uso común**: Autorización en aplicaciones de terceros, integración con proveedores externos como Google, Facebook, etc.
- **Ventajas**: Acceso delegado, gran seguridad, soporte para múltiples flujos de autenticación (por ejemplo, para aplicaciones móviles, clientes confidenciales, etc.).
- **Desventajas**: Complejidad en la implementación.

#### **1.4. SSO (Single Sign-On)**

- **Descripción**: Permite a los usuarios iniciar sesión una sola vez para acceder a múltiples aplicaciones o servicios relacionados.
- **Uso común**: Empresas con múltiples servicios y aplicaciones.
- **Ventajas**: Mejora la experiencia del usuario, reduce el número de inicios de sesión y mejora la seguridad al centralizar el proceso de autenticación.
- **Desventajas**: Si el sistema SSO se ve comprometido, todas las aplicaciones relacionadas son vulnerables.

### **2. Autorización**

Una vez que un usuario ha sido autenticado, es necesario definir qué recursos o acciones puede realizar. Esto se logra mediante un sistema de autorización.

#### **2.1. Control de Acceso Basado en Roles (RBAC)**

- **Descripción**: El Control de Acceso Basado en Roles es un modelo de autorización en el que se asignan permisos a los roles, y los usuarios reciben permisos a través de los roles a los que pertenecen.
- **Uso común**: Aplicaciones con diferentes tipos de usuarios (administradores, clientes, empleados, etc.).
- **Ventajas**: Fácil de gestionar y escalar.
- **Desventajas**: Puede ser inflexible si los roles son demasiado amplios o no se adaptan a cambios rápidos en los permisos.

#### **2.2. Control de Acceso Basado en Atributos (ABAC)**

- **Descripción**: En el control de acceso basado en atributos, las decisiones de autorización se toman en función de los atributos del usuario, del recurso y del entorno. Los atributos pueden incluir roles, localización, hora del día, etc.
- **Uso común**: Aplicaciones que requieren un control de acceso más dinámico y contextual.
- **Ventajas**: Gran flexibilidad, adaptabilidad a necesidades complejas.
- **Desventajas**: Más complejo de implementar y gestionar que RBAC.

#### **2.3. Control de Acceso Basado en Políticas (PBAC)**

- **Descripción**: En PBAC, las decisiones de acceso se toman basadas en políticas definidas, que pueden involucrar cualquier tipo de factor, como roles, atributos, relaciones entre usuarios, etc.
- **Uso común**: Sistemas que requieren un enfoque muy flexible y detallado de las políticas de acceso.
- **Ventajas**: Gran nivel de personalización.
- **Desventajas**: Muy complejo de gestionar y auditar.

### **3. Encriptación de Datos**

La encriptación asegura que los datos no puedan ser leídos por personas no autorizadas, incluso si los datos se interceptan.

#### **3.1. Encriptación en Tránsito (Transport Layer Security - TLS)**

- **Descripción**: Asegura que los datos que se transmiten entre el cliente y el servidor no puedan ser interceptados ni alterados. TLS es el sucesor de SSL y se utiliza ampliamente en conexiones HTTP seguras (HTTPS).
- **Uso común**: Cualquier servicio web accesible a través de Internet.
- **Ventajas**: Garantiza la privacidad y la integridad de los datos durante la transmisión.
- **Desventajas**: Requiere certificados válidos, y puede afectar ligeramente al rendimiento.

#### **3.2. Encriptación en Reposo (Data-at-Rest Encryption)**

- **Descripción**: Protege los datos cuando están almacenados en repositorios (bases de datos, discos duros, etc.) para evitar que personas no autorizadas accedan a la información.
- **Uso común**: Bases de datos que contienen datos sensibles como contraseñas, números de tarjetas de crédito, etc.
- **Ventajas**: Proporciona una capa adicional de seguridad en caso de acceso no autorizado al almacenamiento físico.
- **Desventajas**: Puede afectar al rendimiento y requiere una adecuada gestión de claves.

### **4. Protección contra Amenazas Comunes**

#### **4.1. Prevención de CSRF (Cross-Site Request Forgery)**

- **Descripción**: Un ataque CSRF permite que un atacante ejecute acciones no deseadas en una aplicación web en la que el usuario está autenticado.
- **Uso común**: Aplicaciones web.
- **Solución**: Implementar tokens CSRF o configurar protección en el marco de seguridad (por ejemplo, Spring Security).

#### **4.2. Prevención de XSS (Cross-Site Scripting)**

- **Descripción**: XSS es un tipo de vulnerabilidad en la que un atacante inyecta scripts maliciosos en las páginas web vistas por otros usuarios.
- **Uso común**: Aplicaciones web que permiten entradas de usuario no validadas.
- **Solución**: Validar y desinfectar todas las entradas de usuario, utilizar cabeceras de seguridad (Content Security Policy - CSP), y evitar la ejecución de scripts no deseados.

#### **4.3. Prevención de Inyección SQL (SQL Injection)**

- **Descripción**: Los ataques de inyección SQL permiten a los atacantes ejecutar código SQL malicioso en la base de datos.
- **Uso común**: Aplicaciones que utilizan bases de datos relacionales.
- **Solución**: Usar consultas parametrizadas, ORM (Object-Relational Mapping), y validación de entradas.

### **5. Autenticación Multifactor (MFA)**

La autenticación multifactor añade una capa adicional de seguridad al requerir más de una forma de verificación para autenticar al usuario.

- **Descripción**: Combina diferentes métodos de autenticación, como contraseñas, dispositivos físicos (tokens, autenticación biométrica), o códigos enviados por SMS o correo electrónico.
- **Uso común**: Servicios que manejan información sensible o acceso a sistemas críticos.
- **Ventajas**: Aumenta significativamente la seguridad.
- **Desventajas**: Requiere un proceso adicional para el usuario y mayor complejidad en la implementación.

### **6. Auditoría y Monitoreo**

Mantener registros detallados de las actividades del sistema y monitorear constantemente las solicitudes puede ayudar a detectar actividades sospechosas y prevenir accesos no autorizados.

#### **6.1. Logs de Seguridad**

- **Descripción**: Es una práctica esencial registrar eventos importantes relacionados con la seguridad, como inicios de sesión, intentos fallidos, cambios en la configuración, etc.
- **Uso común**: Aplicaciones web y servicios que requieren cumplimiento de normativas de seguridad.

#### **6.2. Monitoreo de Seguridad en Tiempo Real**

- **Descripción**: Implementar herramientas para monitorear actividades en tiempo real puede ayudar a detectar amenazas y responder rápidamente a incidentes de seguridad.
- **Uso común**: Sistemas críticos que requieren monitoreo continuo.

---

## **Conclusión**

La seguridad en servicios es un campo amplio y multifacético. Elegir el enfoque adecuado depende de los requisitos específicos del sistema, el tipo de datos que se manejan, y el nivel de riesgo aceptable. Implementar una combinación de las estrategias mencionadas puede proporcionar una solución robusta para proteger los servicios de aplicaciones.


### Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>