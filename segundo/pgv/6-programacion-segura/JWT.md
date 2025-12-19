<div align="justify">

## <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios - JWT)

 A continuación vamos se va a detallar **paso a paso** cómo securizar un API REST en **Spring Boot 3.x / Spring Security 6** usando **JWT (Bearer Token)**, habilitar **CORS** para permitir llamadas desde un frontend, y configurar **Swagger/OpenAPI** para mostrar el **candado** y el botón **Authorize**.  

<div align="center">
    <img src=images/jwt_spring.png width="400">
</div>

> El paquete que utilizaremos es: `com.docencia.*`

---

## Nuestro objetivo

- Exponer un endpoint público de autenticación: `POST /api/auth/login` para poder utilizar el tocken de seguridad.
- Proteger el resto del API REST bajo: `/api/**`
- Autenticar peticiones con: `Authorization: Bearer <TOKEN>`
- Permitir CORS desde orígenes concretos (frontends)
- Ver en Swagger UI el “candado” 🔒 y usar **Authorize** para enviar el token
- Evolucionar el sistema hacia **usuarios + roles** (autorización)

---

## Dependencias (Maven)

A continuación el conjunto de dependencias maven que debemos de añadir para cada una de las capas es la siguiente:

### Seguridad + Web

```xml

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### JWT (jjwt)

```xml
<!-- declara jjwt.version=0.12.6 y las incluyes en las propierties. La referencias con ${jjwt.version} dentro de la etiquete version -->

<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-api</artifactId>
  <version>0.12.6</version>
</dependency>
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-impl</artifactId>
  <version>0.12.6</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-jackson</artifactId>
  <version>0.12.6</version>
  <scope>compile</scope>
</dependency>
```

### Swagger/OpenAPI (springdoc)

```xml
<!-- declara openapi.version=2.6.0 y las incluyes en las propierties. La referencias con ${openapi.version}  dentro de la etiquete version -->

<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.6.0</version>
</dependency>
```

---

## Configuración (application.properties)

```properties

# JWT (HS256) - recomendado cargar desde entorno

app.jwt.secret=${APP_JWT_SECRET:CAMBIA_ESTE_SECRETO_MUY_LARGO_MIN_32_CHARS}
app.jwt.expiration-minutes=60

# CORS: URLs permitidas (frontends)

app.cors.allowed-origins=http://localhost:3000,http://localhost:4200

# Swagger

springdoc.swagger-ui.path=/swagger-ui.html 
```

### ¿Qué hace cada propiedad?

- `app.jwt.secret`: clave para firmar/verificar JWT (si usas HS256). Si cambia, tokens anteriores dejan de ser válidos.
- `app.jwt.expiration-minutes`: caducidad del token; al expirar, obtendrás 401.
- `app.cors.allowed-origins`: lista de frontends permitidos; si falta el origen, el navegador bloquea.
- `springdoc.swagger-ui.path`: dónde se publica Swagger UI; `recuerda permitirlo en Security.

---

## Alternativas a `app.jwt.secret` (recomendado en entornos reales)

1) **Variables de entorno / secretos del despliegue**

- Kubernetes Secrets / Docker secrets / Secret Managers cloud
- En Spring: `${APP_JWT_SECRET}`

2) **Firma asimétrica (RSA/ECDSA)**

- Private key firma (backend)
- Public key verifica (otros servicios / gateway)
- Mejor para sistemas distribuidos

3) **Keystore (JKS/PKCS12)**

- Claves/certificados en `.p12` con contraseña (evita texto plano)

4) **Rotación de claves (`kid`)**

- Varias claves vigentes; permite rotar sin “tirar” todo de golpe

---

# ¿Qué son los `record` en Java y por qué usarlos aquí?

Los **`record`** (Java 17+) son una forma concisa de declarar **clases inmutables orientadas a transportar datos**.  
Generan automáticamente constructor, accessors, `equals/hashCode/toString`.

> **Nota:** Imagina que un **`record`** es una clase normal de java, con `propiedades finales` con **funciones** que se generan de forma automática, que son los `equals/hashCode/toString` que has `conocido hasta este momento`.

### Ejemplo

```java
// Con record (recomendado para clases de Dtos/Inputs en función de lo que construyes)
public record User(String username, String password) {}
```

> Nota: los accesos son `user.username()` (no `user.getUsername()`).

---

# Implementación paso a paso (con ejemplos)

## Paso 1 — Records/Dtos de login (con `record`)

```java
package com.docencia.auth.records; // o package com.docencia.auth.dto;

public record LoginRequest(String username, String password) {}
```

```java
package com.docencia.auth.records; // o package com.docencia.auth.dto;

public record TokenResponse(String token) {}
```

---

## Paso 2 — `JwtService` (JJWT 0.12.x)

```java
package com.docencia.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

  private final SecretKey key;
  private final long expirationMinutes;

  public JwtService(
      @Value("${app.jwt.secret}") String secret,
      @Value("${app.jwt.expiration-minutes}") long expirationMinutes
  ) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.expirationMinutes = expirationMinutes;
  }

  public String generateToken(String username) {
    Instant now = Instant.now();
    Instant exp = now.plusSeconds(expirationMinutes * 60);

    return Jwts.builder()
        .subject(username)
        .issuedAt(Date.from(now))
        .expiration(Date.from(exp))
        .signWith(key)
        .compact();
  }

  public String extractUsername(String token) {
    return parseClaims(token).getSubject();
  }

  public boolean isValid(String token) {
    try {
      Claims c = parseClaims(token);
      return c.getExpiration().after(new Date());
    } catch (Exception ex) {
      return false;
    }
  }

  private Claims parseClaims(String token) {
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}
```

> **Nota**: Existe mucho código que no comprendes, pero la implementación de los métodos indican que se obtiene de cada una de ellas, y lo deberás de utilizar en una u otra parte del código para verifcar si el tocken `es válido`, o el `nombre del usuario`, etc.

---

## Paso 3 — Login público (`AuthController`) + validación mínima (`AuthService`)

### `AuthService` (versión simple)


```java
package com.docencia.auth.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

  public boolean validateCredentials(String username, String password) {
    return "user".equals(username) && "pass".equals(password);
  }
}
```

> **Nota**: Esta clase es muy simple y se entiende perfectamente lo que hace. En la realidad se complica mucho más. Se puede integrar con **bbdd**, etc.

### `AuthController`

```java
package com.docencia.auth.controller;

import com.docencia.auth.dto.LoginRequest;
import com.docencia.auth.dto.TokenResponse;
import com.docencia.auth.service.AuthService;
import com.docencia.auth.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;
  private final JwtService jwtService;

  public AuthController(AuthService authService, JwtService jwtService) {
    this.authService = authService;
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  public TokenResponse login(@RequestBody LoginRequest req) {
    if (!authService.validateCredentials(req.username(), req.password())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
    return new TokenResponse(jwtService.generateToken(req.username()));
  }
}
```

> **Nota**: Api Rest pública que hace uso de los servicios implementados anteriormente. Esta devuelve el **tocken** o **credenciales inválidas**.

---

## Paso 4 — Filtro JWT (`JwtAuthenticationFilter`)

```java
package com.docencia.security.filter;

import com.docencia.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  public JwtAuthenticationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException {

    String auth = request.getHeader("Authorization");
    if (auth == null || !auth.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = auth.substring("Bearer ".length());
    if (!jwtService.isValid(token)) {
      filterChain.doFilter(request, response);
      return;
    }

    String username = jwtService.extractUsername(token);

    var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
    var authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }
}
```

>**Nota:**Esta clase tiene como misión obtener la cabecera de seguridad (`Authorization`), verificar que comienza por `Bearer `.
> Obtenemos el usuario: `String username = jwtService.extractUsername(token);`
> Obtenemos los roles de usuario: `authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));`
> Generamos la autenticación `new UsernamePasswordAuthenticationToken(username, null, authorities);`

---

## Paso 5 — Configurar Spring Security (`SecurityFilterChain`)

```java
package com.docencia.security.config;

import com.docencia.auth.service.JwtService;
import com.docencia.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService) throws Exception {
    return http
        .csrf(csrf -> csrf.disable())
        .cors(Customizer.withDefaults())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .requestMatchers("/api/**").authenticated()
            .anyRequest().permitAll()
        )
        .addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}
```

> **Nota:** Esta parte es la que filtra la parte pública y la parte securizada. ¿Qué debemos de colocar aquí?, Todo aquello que querramos acceder de forma libre dentro del servidor.

---

## Paso 6 — CORS (`CorsConfigurationSource`)

```java
package com.docencia.security.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

  @Bean
  public CorsConfigurationSource corsConfigurationSource(
      @Value("${app.cors.allowed-origins}") String allowedOriginsCsv
  ) {
    List<String> allowedOrigins = Arrays.stream(allowedOriginsCsv.split(","))
        .map(String::trim)
        .filter(s -> !s.isBlank())
        .toList();

    CorsConfiguration cfg = new CorsConfiguration();
    cfg.setAllowedOrigins(allowedOrigins);
    cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    cfg.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    cfg.setExposedHeaders(List.of("Authorization"));
    cfg.setAllowCredentials(false);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);
    return source;
  }
}
```

---

## Paso 7 — Swagger/OpenAPI con candado (Authorize)
```java
package com.docencia.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  public static final String SECURITY_SCHEME = "bearerAuth";

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME))
        .components(new Components()
            .addSecuritySchemes(SECURITY_SCHEME,
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            )
        );
  }
}
```

## Proyecto ejemplo completo

En el siguiente [enlace](https://github.com/jpexposito/spring-boot-persistence-h2/tree/bug-fix-jwt) contiene un ejemplo completo de securización.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>