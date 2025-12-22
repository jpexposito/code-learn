<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (Gestor de tareas REST + JWT (Angular moderno))

<div align="center">
  <img src=../images/practicas/practica-04.png
   width="350">
</div>

## Objetivo
Desarrollar una aplicación Angular (standalone, CLI 21) que permita:

1. **Iniciar sesión** contra una API REST securizada (JWT)
2. Guardar el token y enviar `Authorization: Bearer <token>` automáticamente
3. **Proteger rutas** (solo accesibles si hay sesión)
4. Consumir un CRUD de tareas **real** desde backend:
   - listar tareas
   - crear tarea (formulario)
   - eliminar tarea
   - (ampliación) marcar completada / editar

> Esta práctica engloba lo aprendido en: routing, componentes, servicios, formularios, HTTP, interceptor y guard.

---

## Requisitos previos
- Backend REST disponible con endpoints de autenticación y tareas
- CORS configurado correctamente
- Node 22 / npm 10 / Angular CLI 21

---

## 1) Estructura mínima requerida

```text
src/app/
├─ pages/
│  ├─ login/
│  ├─ tasks/
│  └─ task-new/
├─ shared/
│  └─ navbar/
├─ models/
│  ├─ task.model.ts
│  └─ auth.model.ts
├─ services/
│  ├─ auth.service.ts
│  └─ tasks-api.service.ts
├─ guards/
│  └─ auth.guard.ts
├─ interceptors/
│  └─ auth.interceptor.ts
├─ app.routes.ts
└─ app.config.ts
```

---

## 2) Modelo de autenticación

`src/app/models/auth.model.ts`

```ts
export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}
```

---

## 3) AuthService (login, logout, token)

`src/app/services/auth.service.ts`

- `login()`: POST al endpoint de login
- `setToken()`: guardar en `localStorage`
- `getToken()`: leer token
- `logout()`: borrar token
- `isLoggedIn()`: true/false

> **Consejo:** mantener AuthService muy simple y claro.

---

## 4) Interceptor: añadir JWT a todas las peticiones

`src/app/interceptors/auth.interceptor.ts`

- Si hay token:
  - añadir cabecera `Authorization: Bearer <token>`
- Si respuesta 401:
  - logout
  - redirigir a /login (opcional)

---

## 5) Guard: proteger rutas privadas

`src/app/guards/auth.guard.ts`

- Si `isLoggedIn()`:
  - permite acceso
- Si no:
  - redirige a `/login`

---

## 6) Routing: rutas públicas y privadas

Ejemplo recomendado:

- `/login` (pública)
- `/tareas` (privada)
- `/tareas/nueva` (privada)
- `/` redirige a `/tareas` (si login) o `/login` (si no)

---

## 7) Pantallas mínimas

### 7.1 Login
- formulario reactivo
- validar required
- al login correcto → navegar a `/tareas`
- al login incorrecto → mostrar error

### 7.2 Lista de tareas
- GET `/api/tasks`
- botón eliminar: DELETE `/api/tasks/{id}`
- botón “nueva tarea” → `/tareas/nueva`

### 7.3 Nueva tarea
- formulario reactivo
- POST `/api/tasks`
- volver a la lista tras crear

---

## 8) Entregable
- Repositorio Git con commits claros
- README del proyecto explicando:
  - cómo arrancar
  - endpoints usados
  - estructura de carpetas
  - credenciales de prueba (si las hay)

---

## ✅ Rúbrica de evaluación

### Funcionalidad (40%)
- [ ] Login funciona y guarda token (10)
- [ ] Lista tareas desde API (10)
- [ ] Crear tarea con formulario (10)
- [ ] Eliminar tarea (10)

### Seguridad frontend (30%)
- [ ] Interceptor añade `Authorization: Bearer` (15)
- [ ] Guard protege rutas privadas (15)

### Calidad y organización (20%)
- [ ] Carpetas y nombres correctos (10)
- [ ] Servicios separados de UI (10)

### Presentación y documentación (10%)
- [ ] README claro y completo (10)

**Nota:** ampliaciones (editar/completar) pueden sumar hasta +1 punto.

---

## Ampliaciones 

- Marcar tarea completada (PUT)
- Filtros: completadas / pendientes
- Página “perfil” mostrando el username del token
- Manejo de expiración del token

---

</div>
