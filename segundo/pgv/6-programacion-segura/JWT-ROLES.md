<div align="justify">

## <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios - JWT && Roles)

<div align="center">
    <img src=images/jwt_spring-roles.png width="400">
</div>

A continuación vamos se va a detallar **paso a paso** cómo securizar un API REST en **Spring Boot 3.x / Spring Security 6** usando **JWT (Bearer Token)** + **Roles**, habilitar **CORS** para permitir llamadas desde un frontend
Incluye un ejemplo completo con:
- **Rutas públicas** y **rutas protegidas**
- **Reglas por rol** (USER/ADMIN)
- Usuarios **en memoria** (rápido para pruebas) y alternativa con **base de datos**
- Recomendación para APIs: modo **stateless** + **JWT** (opcional, al final)

> Requisitos recomendados: **Spring Boot 3.x** + **Spring Security 6** + Java 17+.

---

## 0) Conceptos clave (2 minutos)

- **Autenticación**: quién eres (usuario/credenciales).
- **Autorización**: qué puedes hacer (roles/permisos).
- **Roles en Spring**: por convención se manejan como `ROLE_USER`, `ROLE_ADMIN`.
  - Si usas `.hasRole("ADMIN")`, Spring comprobará `ROLE_ADMIN` internamente.

---

## 1) Añadir/Verifica las dependencias

En `pom.xml` añade:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

---

## 2) Decide qué endpoints son públicos y cuáles van por rol

Ejemplo típico:

- Público:
  - `GET /api/public/**`
- Protegido:
  - `GET /api/books` → requiere USER o ADMIN
  - `POST /api/books` → requiere ADMIN
  - `GET /api/admin/**` → requiere ADMIN

---

## 3) Crea la configuración de seguridad (SecurityFilterChain)

Crea `SecurityConfig.java`:

```java
package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      // Para API REST: normalmente se desactiva CSRF (no hay formularios)
      .csrf(csrf -> csrf.disable())

      // Reglas de autorización por ruta + rol
      .authorizeHttpRequests(auth -> auth
        // públicas
        .requestMatchers("/api/public/**").permitAll()

        // protegidas por rol
        .requestMatchers("/api/books/**").hasAnyRole("USER", "ADMIN")
        .requestMatchers("/api/admin/**").hasRole("ADMIN")

        // todo lo demás requiere autenticación
        .anyRequest().authenticated()
      )

      // Autenticación simple para pruebas (Basic Auth)
      .httpBasic(basic -> {});

    return http.build();
  }
}
```

✅ Con esto:
- Si llamas a una ruta protegida sin credenciales → **401**
- Si te autenticas pero no tienes rol suficiente → **403**

---

## 4) Crea usuarios con roles (rápido: InMemory)

Para probar sin base de datos, crea `UsersConfig.java`:

```java
package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UsersConfig {

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

 /**
  * Sercicion que implementa en memoria un servicio de usuario para la aplicacion.
  * Se puede conectar con un servicio de bbdd donde existan los usuarios realizar a 
  * traves de un repositorio (@repository)
  */ 
  @Bean
  UserDetailsService userDetailsService(PasswordEncoder encoder) {
    UserDetails user = User.builder()
      .username("user")
      .password(encoder.encode("user123"))
      .roles("USER") // -> ROLE_USER
      .build();

    UserDetails admin = User.builder()
      .username("admin")
      .password(encoder.encode("admin123"))
      .roles("ADMIN") // -> ROLE_ADMIN
      .build();

    return new InMemoryUserDetailsManager(user, admin);
  }
}
```

---

## 5) Prueba con curl

### Endpoint público
```bash
curl -i http://localhost:8080/api/public/ping
```
Esperado: **200**

### Protegido sin credenciales
```bash
curl -i http://localhost:8080/api/books
```
Esperado: **401 Unauthorized**

### Protegido con USER
```bash
curl -i -u user:user123 http://localhost:8080/api/books
```
Esperado: **200 OK**

### ADMIN con USER (no permitido)
```bash
curl -i -u user:user123 http://localhost:8080/api/admin/stats
```
Esperado: **403 Forbidden**

### ADMIN con ADMIN
```bash
curl -i -u admin:admin123 http://localhost:8080/api/admin/stats
```
Esperado: **200 OK**

---

## 6) Autorización a nivel de método con @PreAuthorize

Si necesitas reglas finas, activa method-security:

```java
@Configuration
@EnableMethodSecurity
public class MethodSecurityConfig {}
```

Y aplica reglas:

```java
@PreAuthorize("hasRole('ADMIN')")
public void deleteBook(Long id) { ... }
```

---

## 7) Para una API REST real: Stateless + JWT

**Basic Auth** sirve para pruebas, pero en APIs reales suele usarse:
- `POST /api/auth/login` → devuelve **JWT**
- Rutas protegidas requieren `Authorization: Bearer <token>`
- Los roles salen del token y se convierten en authorities


## Proyecto ejemplo completo

En el siguiente [enlace](https://github.com/jpexposito/spring-boot-persistence-h2/tree/bug-fix-jwt) contiene un ejemplo completo de securización.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>