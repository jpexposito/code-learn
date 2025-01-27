<div align="justify">

## <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios - Ataques)

<div align="center">
    <img src=images/ataques.png width="400">
</div>

## **Ataques Más Comunes a Servicios**

La seguridad de los servicios y aplicaciones está constantemente en riesgo debido a diversos tipos de ataques. A continuación se describen los ataques más comunes a servicios web y aplicaciones, que pueden comprometer la confidencialidad, integridad o disponibilidad de los sistemas.

### **1. Inyección SQL (SQL Injection)**

- **Descripción**: Este ataque ocurre cuando un atacante introduce código malicioso (generalmente comandos SQL) en un campo de entrada de una aplicación para manipular las consultas SQL ejecutadas en la base de datos.
- **Consecuencias**:
  - Acceso no autorizado a bases de datos.
  - Modificación o eliminación de datos sensibles.
  - Ejecución de comandos maliciosos en la base de datos.
- **Prevención**:
  - Usar consultas parametrizadas o preparadas.
  - Utilizar ORM (Object-Relational Mapping).
  - Validar y sanitizar entradas de usuario.

### **2. Cross-Site Scripting (XSS)**

- **Descripción**: En un ataque XSS, el atacante inyecta scripts maliciosos en el navegador de la víctima a través de un sitio web vulnerable. Estos scripts se ejecutan en el contexto de la víctima, permitiendo al atacante robar cookies, robar información, o realizar acciones en nombre del usuario.
- **Consecuencias**:
  - Robo de cookies o credenciales de sesión.
  - Ejecución de acciones no autorizadas en nombre de la víctima.
  - Redirección a sitios web maliciosos.
- **Prevención**:
  - Escapar o sanear datos de entrada y salida.
  - Usar cabeceras de seguridad como **Content Security Policy (CSP)**.
  - Validar y filtrar entradas de usuario.

### **3. Cross-Site Request Forgery (CSRF)**

- **Descripción**: En un ataque CSRF, el atacante induce al usuario autenticado a realizar una acción no deseada en una aplicación web, como cambiar configuraciones o realizar transferencias, sin su conocimiento.
- **Consecuencias**:
  - Modificación no autorizada de los datos del usuario.
  - Ejecución de acciones maliciosas sin el consentimiento del usuario.
- **Prevención**:
  - Utilizar tokens CSRF en formularios y peticiones.
  - Verificar las cabeceras **Referer** y **Origin**.
  - Implementar autenticación multifactor (MFA).

### **4. Denegación de Servicio (DoS) y Denegación de Servicio Distribuida (DDoS)**

- **Descripción**: Los ataques DoS y DDoS buscan hacer que un servicio sea inaccesible al sobrecargarlo con tráfico excesivo. La diferencia entre ambos es que en un DDoS, los atacantes usan múltiples sistemas (botnets) para generar el ataque, haciéndolo mucho más difícil de bloquear.
- **Consecuencias**:
  - Inaccesibilidad del servicio.
  - Pérdida de disponibilidad de la infraestructura.
  - Costos adicionales por la escalabilidad de los recursos.
- **Prevención**:
  - Implementar límites de tasa (rate limiting).
  - Usar servicios de mitigación de DDoS como **Cloudflare** o **AWS Shield**.
  - Configurar firewalls y proxies inversos.

### **5. Man-in-the-Middle (MITM)**

- **Descripción**: Un atacante MITM intercepta la comunicación entre dos partes (como un usuario y un servidor) para robar o modificar datos. Este tipo de ataque es común cuando las conexiones no están cifradas adecuadamente.
- **Consecuencias**:
  - Robo de credenciales o información confidencial.
  - Modificación de datos en tránsito.
  - Suplantación de identidad.
- **Prevención**:
  - Usar **TLS/SSL** para cifrar la comunicación (HTTPS).
  - Validar los certificados de servidor.
  - Implementar autenticación mutua en conexiones sensibles.

### **6. Elevación de Privilegios**

- **Descripción**: En este tipo de ataque, el atacante intenta obtener un nivel de acceso más alto del que le corresponde, lo que puede incluir el acceso a información o funciones que normalmente estarían fuera de su alcance.
- **Consecuencias**:
  - Acceso no autorizado a recursos protegidos.
  - Realización de acciones maliciosas en el sistema.
  - Manipulación de configuraciones de seguridad.
- **Prevención**:
  - Aplicar el principio de **mínimos privilegios**.
  - Auditar y revisar permisos de usuario regularmente.
  - Utilizar autenticación multifactor y mecanismos de control de acceso estrictos.

### **7. Robo de Sesión**

- **Descripción**: Este ataque ocurre cuando un atacante obtiene acceso a un token de sesión válido (por ejemplo, cookies de sesión) de otro usuario, permitiéndole suplantar su identidad.
- **Consecuencias**:
  - Acceso no autorizado a cuentas de usuario.
  - Realización de transacciones o acciones en nombre del usuario comprometido.
- **Prevención**:
  - Usar **HTTPOnly** y **Secure** para las cookies de sesión.
  - Implementar autenticación multifactor.
  - Utilizar **tokens JWT** con expiración corta y un sistema de revocación.

### **8. Inyección de Comandos (Command Injection)**

- **Descripción**: En una inyección de comandos, el atacante introduce comandos maliciosos que se ejecutan en el sistema operativo del servidor, generalmente a través de una entrada no validada.
- **Consecuencias**:
  - Ejecución de comandos arbitrarios en el servidor.
  - Acceso a datos sensibles o control del servidor.
- **Prevención**:
  - Validar las entradas de usuario rigurosamente.
  - Usar mecanismos de escape y validación al interactuar con el sistema operativo.
  - Ejecutar comandos en un entorno seguro con permisos restringidos.

### **9. Phishing**

- **Descripción**: El phishing es un ataque en el que un atacante intenta obtener información confidencial (como contraseñas o números de tarjetas de crédito) engañando al usuario para que ingrese estos datos en un sitio web falso que simula ser legítimo.
- **Consecuencias**:
  - Robo de credenciales.
  - Acceso no autorizado a cuentas personales y sistemas.
- **Prevención**:
  - Educar a los usuarios sobre los peligros del phishing y cómo identificar correos electrónicos fraudulentos.
  - Implementar autenticación multifactor.
  - Usar herramientas de prevención de phishing como filtros de correo electrónico.

### **10. Exposición de Datos Sensibles**

- **Descripción**: Este ataque ocurre cuando una aplicación o servicio expone información sensible, como contraseñas, tokens, o datos personales, ya sea a través de canales inseguros o por errores en el diseño del sistema.
- **Consecuencias**:
  - Robo de información confidencial.
  - Exposición de datos privados.
  - Violación de normativas de protección de datos como GDPR o CCPA.
- **Prevención**:
  - Encriptar datos sensibles en reposo y en tránsito.
  - Minimizar el almacenamiento de información sensible.
  - Implementar políticas de control de acceso y auditoría.

### **11. Exploits de Vulnerabilidades en Software**

- **Descripción**: Los exploits aprovechan vulnerabilidades conocidas en el software para ejecutar código malicioso o acceder a recursos del sistema. Esto puede incluir vulnerabilidades en frameworks, bibliotecas, o incluso configuraciones incorrectas.
- **Consecuencias**:
  - Ejecución de código arbitrario en el sistema.
  - Explotación de fallos de seguridad.
  - Acceso no autorizado a recursos críticos.
- **Prevención**:
  - Mantener el software actualizado con parches de seguridad.
  - Utilizar herramientas de escaneo de vulnerabilidades.
  - Configurar correctamente el software y las dependencias.

### **12. Ransomware**

- **Descripción**: El ransomware es un tipo de malware que cifra los datos del usuario o del sistema y exige un rescate a cambio de la clave de descifrado.
- **Consecuencias**:
  - Pérdida de acceso a datos importantes.
  - Pérdida económica debido a los pagos de rescate.
  - Daño reputacional.
- **Prevención**:
  - Mantener copias de seguridad regulares y desconectadas.
  - Usar software antivirus actualizado.
  - Aplicar controles de acceso estrictos y limitar el uso de privilegios administrativos.

---

## **Conclusión**

Los ataques a servicios son una amenaza constante para cualquier tipo de aplicación o sistema. Es fundamental comprender los riesgos y aplicar las mejores prácticas de seguridad para mitigar estas amenazas. Implementar mecanismos de prevención adecuados y mantener una vigilancia constante puede ayudar a proteger los sistemas y garantizar la seguridad de los usuarios y los datos.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>