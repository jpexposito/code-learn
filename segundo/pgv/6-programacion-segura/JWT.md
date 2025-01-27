<div align="justify">

## <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios - JWT)

## **1. Introducción**

El propósito de esta integración es proteger las rutas de una aplicación Spring Boot usando JSON Web Tokens (JWT). Esto permite que los usuarios autenticados puedan acceder a ciertos recursos, mientras que los no autenticados no podrán hacerlo. JWT es ampliamente utilizado por su ligereza, facilidad de implementación y porque se puede usar de manera eficiente para la autenticación basada en tokens.

## **2. Requisitos**

Para integrar JWT en Spring Boot, necesitarás tener configurado lo siguiente:

- Spring Boot
- Spring Security
- Dependencia para manejar JWT, como `jjwt` o cualquier otra biblioteca de tu preferencia.
  
## **3. Clases Principales**

### **3.1. Clase de Configuración de Seguridad (`SecurityConfig`)**

Esta clase es responsable de configurar la seguridad de la aplicación. Aquí se define cómo se protegerán las rutas y se indica que se va a utilizar un filtro de autenticación para validar los tokens JWT.

- [Ejemplo](https://github.com/jpexposito/spring-boot-persistence-h2/blob/jwt/src/main/java/es/system/jpexposito/springboot/security/SecurityConfig.java)

- **Propósito**: Configurar las rutas protegidas y el filtro JWT.
- **Configuraciones principales**:
  - Deshabilitar CSRF.
  - Permitir acceso sin autenticación a ciertas rutas.
  - Requiere autenticación para todas las demás rutas.
  - Agregar el `JwtAuthenticationFilter` para interceptar las solicitudes.

### **3.2. Filtro de Autenticación JWT (`JwtAuthenticationFilter`)**

Este filtro se encarga de interceptar las solicitudes y verificar el token JWT presente en los encabezados HTTP. Si el token es válido, extrae la información del usuario y la asigna al contexto de seguridad.

- **Propósito**: Verificar la validez del JWT en cada solicitud.
- **Configuraciones principales**:
  - Comprobar la existencia del token en el encabezado `Authorization`.
  - Validar el token.
  - Extraer la información del usuario y almacenarla en el contexto de seguridad de Spring.

- [Ejemplo](https://github.com/jpexposito/spring-boot-persistence-h2/blob/jwt/src/main/java/es/system/jpexposito/springboot/security/JwtAuthorizationFilter.java).

### **3.3. Clase de Utilidades JWT (`JwtTokenProvider`)**

Esta clase maneja la generación, resolución y validación de los tokens JWT. Es responsable de crear el JWT, verificar su validez y extraer la información contenida en él.

- **Propósito**: Gestionar la creación, validación y extracción de datos de los tokens JWT.
- **Métodos importantes**:
  - `generateToken()`: Crea un nuevo JWT para un usuario específico.
  - `resolveToken()`: Extrae el token del encabezado `Authorization`.
  - `validateToken()`: Valida que el token no esté expirado y sea legítimo.
  - `getUsernameFromToken()`: Extrae el nombre de usuario del JWT.

- [Ejemplo](https://github.com/jpexposito/spring-boot-persistence-h2/blob/jwt/src/main/java/es/system/jpexposito/springboot/security/JwtUtils.java)

### **3.4. Servicio de Autenticación (`AuthService`)**

Este servicio se encarga de manejar la lógica de autenticación. Se usa para autenticar al usuario usando sus credenciales (nombre de usuario y contraseña) y generar un token JWT cuando la autenticación es exitosa.

- **Propósito**: Autenticar al usuario y generar el JWT correspondiente.
- **Métodos importantes**:
  - `authenticate()`: Realiza la autenticación del usuario y devuelve el token JWT.

### **3.5. Controlador de Autenticación (`AuthController`)**

Este controlador proporciona los puntos finales para la autenticación de usuarios. Recibe las credenciales de inicio de sesión, las valida y devuelve el JWT en caso de éxito.

- **Propósito**: Exponer los puntos finales de autenticación (login).
- **Métodos importantes**:
  - `/login`: Recibe las credenciales del usuario y devuelve el JWT.

- [Ejemplo](https://github.com/jpexposito/spring-boot-persistence-h2/blob/jwt/src/main/java/es/system/jpexposito/springboot/controller/AuthController.java)

### **3.6. Clase de Detalles de Usuario Personalizado (`CustomUserDetails`)**

Esta clase es una implementación personalizada de `UserDetails`, utilizada por Spring Security para almacenar información relevante sobre el usuario autenticado.

- **Propósito**: Contener la información del usuario autenticado en el contexto de seguridad.
- **Configuraciones principales**:
  - Implementación de `UserDetails`.
  - Opcionalmente, puede contener más detalles del usuario como roles o privilegios.
  
### **3.7. Configuración en `application.properties`**

En el archivo `application.properties` o `application.yml` se deben definir propiedades esenciales como la clave secreta para la firma del JWT y el tiempo de expiración.

- **Propiedades importantes**:
  - `jwt.secret`: La clave secreta utilizada para firmar los tokens.
  - `jwt.expiration`: El tiempo en milisegundos durante el cual el token es válido.

## **4. Flujo de Autenticación con JWT**

El proceso de autenticación con JWT generalmente sigue estos pasos:

1. **Autenticación del Usuario**: El usuario envía sus credenciales (nombre de usuario y contraseña) al endpoint `/auth/login`.
2. **Generación del Token**: Si las credenciales son correctas, el servidor genera un JWT y lo devuelve al cliente.
3. **Acceso a Rutas Protegidas**: En solicitudes posteriores a rutas protegidas, el cliente incluye el JWT en el encabezado `Authorization` usando el esquema `Bearer`.
4. **Verificación del Token**: El servidor verifica la validez del token en cada solicitud. Si el token es válido, el servidor extrae los detalles del usuario del token y establece el contexto de seguridad.

<div align="center">
    <img src=images/jwt-sequence-2.png width="400">
</div>

## **5. Dependencias Necesarias**

Asegúrate de agregar las siguientes dependencias en tu archivo `pom.xml` (para Maven) o `build.gradle` (para Gradle):

### **Maven (pom.xml)**

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>