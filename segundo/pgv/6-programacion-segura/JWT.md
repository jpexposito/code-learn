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
  <version>0.12.5</version>
</dependency>
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-impl</artifactId>
  <version>0.12.5</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-jackson</artifactId>
  <version>0.12.5</version>
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
# Un ejemplo:Kraj8AxPPe5XdByv9wN4o4cwhW8ExUoxH3kGIG9oY3MobGgN7zbPmmG2aomaZ7RP6EH17Le6RdX6+k0DPxqbfQ==
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

## 1) Idea general 

- **Controller**: expone endpoints HTTP (ej: login).
- **Service**: contiene lógica reutilizable (ej: crear/validar JWT).
- **Filter (Spring Security)**: intercepta peticiones antes de llegar al controller.
- **SecurityConfig**: "cablea" Spring Security: rutas públicas, rutas protegidas, filtros, sesiones, etc.
- **OpenApiConfig**: configura Swagger/OpenAPI para que el alumnado pueda probar endpoints con el botón **Authorize**.

---

## 2) Responsabilidad de cada clase

### 2.1 `AuthController` (capa web)
**Qué hace**
- Expone el endpoint `POST /login`.
- Recibe credenciales (usuario/password).
- Pide a Spring Security que autentique mediante `AuthenticationManager`.
- Si las credenciales son correctas, genera un JWT y lo devuelve al cliente.

**Dependencias típicas**
- `AuthenticationManager` para autenticar usuario/password.
- `JwtService` para generar el token.

**Qué NO hace**
- No valida JWT en cada request (eso lo hace el filtro).
- No decide reglas globales de seguridad (eso es `SecurityConfig`).

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

### 2.2 `JwtService` 
**Qué hace**
- Genera tokens JWT (`generateToken(UserDetails)` o similar).
- Extrae información del token (ej: `extractUsername`, `extractClaims`).
- Valida token (firma, expiración, sujeto, etc).

**Por qué es un Service**
- Porque la lógica JWT se reutiliza en:
  - el login (para emitir token)
  - el filtro (para validar token en cada request)
- Mantiene el controller y el filtro más limpios.

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

---

### 2.3 `JwtAuthenticationFilter` (filtro de seguridad)
**Qué hace**
- Intercepta **todas** las peticiones (según se registre en la cadena de filtros).
- Lee la cabecera HTTP: `Authorization: Bearer <token>`.
- Si hay token:
  - lo valida usando `JwtService`
  - obtiene el usuario (normalmente vía `UserDetailsService`)
  - crea un `Authentication` y lo guarda en `SecurityContext`
- Si no hay token o es inválido:
  - no autentica (y la petición quedará como anónima)
  - el acceso final dependerá de las reglas de `SecurityConfig`

> **Idea clave:**
>El filtro es el `portero`: decide si la petición llega como autenticada o anónima.

```java
Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authHeader.substring("Bearer ".length()).trim();
    if (token.isEmpty() || SecurityContextHolder.getContext().getAuthentication() != null) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String username = jwtService.extractUsername(token);
      if (username == null || username.isBlank()) {
        filterChain.doFilter(request, response);
        return;
      }

      UserDetails user = userDetailsService.loadUserByUsername(username);
      if (jwtService.isTokenValid(token, user)) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            user,
            null,
            user.getAuthorities()
        );
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (Exception ignored) {
      // Invalid token -> proceed without authentication (will be blocked by security rules)
    }

    filterChain.doFilter(request, response);
  }
}
```

>**Nota:**Esta clase tiene como misión obtener la cabecera de seguridad (`Authorization`), verificar que comienza por `Bearer `.
> Obtenemos el usuario: `String username = jwtService.extractUsername(token);`
> Obtenemos los roles de usuario: `authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));`
> Generamos la autenticación `new UsernamePasswordAuthenticationToken(username, null, authorities);`

---

### 2.4 `SecurityConfig` (configuración principal de Spring Security)
**Qué hace**
- Define el `SecurityFilterChain`:
  - rutas `permitAll()` (públicas)
  - rutas `authenticated()` (protegidas)
  - gestión de sesiones (`STATELESS` para JWT)
  - registro del `JwtAuthenticationFilter` en el orden correcto
- Expone beans típicos:
  - `PasswordEncoder`
  - `AuthenticationManager`
  - (a veces) `UserDetailsService` para usuarios en memoria o BD

**Por qué es importante**
- Sin esta clase, Spring Security no sabe:
  - qué endpoints son públicos
  - qué endpoints requieren token
  - cuándo se ejecuta el filtro JWT

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

ó

```java
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/api/v1/auth/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/h2-console/**"
            ).permitAll()

            .requestMatchers(HttpMethod.GET, "/api/v1/tasks/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/v1/tasks/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/v1/tasks/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/v1/tasks/**").hasRole("ADMIN")

            .anyRequest().authenticated()
        )
        .httpBasic(httpBasic -> httpBasic.disable());

    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
```

> **Nota:** Esta parte es la que filtra la parte pública y la parte securizada. ¿Qué debemos de colocar aquí?, Todo aquello que querramos acceder de forma libre dentro del servidor.

---

### 2.5 `OpenApiConfig` (Swagger / OpenAPI)
**Qué hace**
- Define un `OpenAPI` bean con un `SecurityScheme` tipo **HTTP Bearer** (JWT).
- Eso habilita el botón **Authorize** en Swagger UI.
- Permite probar endpoints protegidos pegando el token.

**Idea clave**
- Swagger no protege nada por sí solo; solo documenta y facilita pruebas.
- La seguridad real está en `SecurityConfig` + `JwtAuthenticationFilter`.

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

---

## 3) Relación entre clases (mapa mental)

### Dependencias (quién usa a quién)

- `AuthController` -> usa -> `AuthenticationManager`
- `AuthController` -> usa -> `JwtService`
- `JwtAuthenticationFilter` -> usa -> `JwtService`
- `JwtAuthenticationFilter` -> usa -> `UserDetailsService` (detallles del usuario)
- `SecurityConfig` -> registra -> `JwtAuthenticationFilter`
- `OpenApiConfig` -> no depende de las otras, pero documenta el esquema Bearer para Swagger

---


<div align="center">
    <img src=images/dependencias-clases-seguridad.png width="400">
</div>

## 4) Flujo 1: Login (obtener JWT)

### Paso a paso
1. Cliente envía `POST /login` con credenciales.
2. `AuthController` llama a `authenticationManager.authenticate(...)`.
3. Si autentica OK:
   - `AuthController` llama `jwtService.generateToken(userDetails)`.
4. `AuthController` responde: `{ token: "...", roles: [...] }`.

---

## 5) Flujo 2: Acceder a un endpoint protegido (con JWT)

### Paso a paso
1. Cliente llama a un endpoint protegido (ej: `GET /tasks`).
2. Envía cabecera: `Authorization: Bearer <token>`.
3. `JwtAuthenticationFilter` intercepta:
   - extrae token
   - valida con `JwtService`
   - si OK, coloca autenticación en `SecurityContext`
4. Spring Security evalúa reglas de `SecurityConfig`:
   - si requiere `authenticated()`, la petición pasa
   - si no hay autenticación, responde `401`


## Proceso de validación 

<div align="center">
    <img src=images/validacion-roles-jwt.png width="400">
</div>

---

## 6) Dónde se decide "público vs protegido"

Esto vive en `SecurityConfig`.

Ejemplos típicos (conceptuales):

- Público:
  - `/login`
  - `/swagger-ui/**`
  - `/v3/api-docs/**`
- Protegido:
  - cualquier otra ruta por defecto

---

## 7) Cómo probar en Swagger

1. Lanza la app.
2. Abre Swagger UI.
3. Ejecuta `POST /login` y copia el token.
4. Pulsa **Authorize**.
5. Pega solo el token (o con prefijo `Bearer ` según configuración).
6. Prueba endpoints protegidos.

---

<div align="center">
    <img src=images/api-task-secure.png width="400">
</div>


## 8) ¿Qué debes de implementar?

- Securizar la `api-task` siguiendo los pasos y construyendo las clases indicadas en el paquete `infraestructura`.

  - `com.docencia.tasks.infrastructure`
    - `security`
      - `JwtService`
      - `JwtAuthenticationFilter`
      - `SecurityConfig`
    - `openapi`
      - `OpenApiConfig`

- Completar el conjunto de test del servicio, utilizando `mokito`.

---

## 9) Errores comunes y cómo detectarlos

- **El token no se lee**: revisar cabecera `Authorization` y prefijo `Bearer `.
- **El filtro no corre**: revisar que `SecurityConfig` lo agrega en la cadena.
- **Swagger no muestra Authorize**: revisar `OpenApiConfig` y `SecurityScheme`.
- **401 aunque hay token**:
  - token expirado
  - firma distinta (secret incorrecto)
  - usuario no encontrado (si se valida contra `UserDetailsService`)
- **403 (prohibido)**:
  - autenticado pero sin rol requerido

---


## Proyecto ejemplo completo

En el siguiente [enlace](https://github.com/jpexposito/spring-boot-persistence-h2/tree/bug-fix-jwt) contiene un ejemplo completo de securización.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>