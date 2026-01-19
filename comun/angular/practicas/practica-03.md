<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn — Práctica 3 (Parte B)  

## Acceso a una API REST securizada con JWT en Angular (Login + Token + Interceptor + Guard + Logout)

<div align="center">
  <img src=../images/practicas/practica-03.png width="420">
</div>

**Objetivo de esta práctica (Frontend):** construir **paso a paso** los elementos necesarios para autenticarse contra una API securizada con JWT y consumir endpoints protegidos.

**Flujo final:** Login → guardar token → interceptor añade Bearer → guard protege rutas → logout borra token → manejo 401/403.

> ⚠️ Nota: “atacar la API” en esta práctica significa **consumir/probar** endpoints protegidos de forma correcta, no hacking.

---

## Requisitos previos
- Backend levantado (ejemplo): `http://localhost:8080`
- Endpoint de login: `POST /api/auth/login` → `{ "token": "..." }`
- Endpoint protegido de ejemplo: `GET /api/tasks`
- Angular funcionando (ejemplo): `http://localhost:4200`
- `HttpClientModule` (Angular clásico) o `provideHttpClient` (standalone) configurado.

---

# B0) Crear la página de Login (obligatorio)

## B0.1 Crear estructura base
Crea la carpeta:

```
src/app/auth/
```

Dentro iremos creando:
- `auth.models.ts`
- `auth.service.ts`
- `auth.interceptor.ts`
- `auth.guard.ts`
- `login/` (componente)

---

## B0.2 Crear el componente Login
Con Angular CLI:

```bash
ng generate component auth/login
```

Se generan:
- `src/app/auth/login/login.component.ts`
- `src/app/auth/login/login.component.html`
- `src/app/auth/login/login.component.css|scss`

---

## B0.3 Crear el formulario (HTML)
Editar:

`src/app/auth/login/login.component.html`

```html
<h2>Login</h2>

<form (ngSubmit)="onSubmit()" #f="ngForm">
  <label>Usuario</label>
  <input name="username" [(ngModel)]="username" required />

  <label>Contraseña</label>
  <input type="password" name="password" [(ngModel)]="password" required />

  <button type="submit" [disabled]="f.invalid">Entrar</button>

  <p *ngIf="error">{{ error }}</p>
</form>
```

---

## B0.4 Lógica del componente (TS)
Editar:

`src/app/auth/login/login.component.ts`

```ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  username = '';
  password = '';
  error: string | null = null;

  constructor(private auth: AuthService, private router: Router) {}

  onSubmit() {
    // Se completa cuando exista AuthService (ver B2)
  }
}
```

---

## B0.5 Crear la ruta /login
### Angular clásico (AppRoutingModule)
En `app-routing.module.ts`:

```ts
{ path: 'login', component: LoginComponent }
```

### Standalone (app.routes.ts)
En `app.routes.ts`:

```ts
{ path: 'login', loadComponent: () => import('./auth/login/login.component').then(m => m.LoginComponent) }
```

---

## 2) Modelos del Login

Crear:

```
src/app/auth/auth.models.ts
```

```ts
export interface AuthRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  token: string;
}
```

---

## 2) AuthService (login + guardar token + recuperar token + logout)

### 2.1 Crear el servicio
Crear:

```
src/app/auth/auth.service.ts
```

Contenido **completo** (sin TODOs):

```ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { AuthRequest, AuthResponse } from './auth.models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly TOKEN_KEY = 'token';
  private readonly API = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  login(username: string, password: string) {
    const body: AuthRequest = { username, password };

    return this.http.post<AuthResponse>(`${this.API}/login`, body).pipe(
      tap(res => {
        // Guardar el token al hacer login correctamente
        localStorage.setItem(this.TOKEN_KEY, res.token);
      })
    );
  }

  logout() {
    // Borrar el token (cerrar sesión)
    localStorage.removeItem(this.TOKEN_KEY);
  }

  getToken(): string | null {
    // Leer el token (si existe) para poder enviarlo en requests
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
```

✅ **Esto cubre lo que pedías**:
```ts
// borrar token
localStorage.removeItem(this.TOKEN_KEY);
```

> Si prefieres `sessionStorage` (sesión por pestaña), sustitúyelo por `sessionStorage.setItem/getItem/removeItem`.

---

### 2.2 Completar `onSubmit()` en LoginComponent
Editar:

`src/app/auth/login/login.component.ts`

```ts
onSubmit() {
  this.error = null;

  this.auth.login(this.username, this.password).subscribe({
    next: () => this.router.navigate(['/tareas']),
    error: () => this.error = 'Credenciales inválidas'
  });
}
```

---

## 3) Interceptor JWT (añadir Authorization: Bearer ...)

### 3.1 Crear interceptor
Crear:

```
src/app/auth/auth.interceptor.ts
```

```ts
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { AuthService } from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private auth: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const token = this.auth.getToken();

    // Recomendado: no interceptar login
    const isLogin = req.url.includes('/api/auth/login');
    if (isLogin) return next.handle(req);

    if (!token) return next.handle(req);

    const authReq = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });

    return next.handle(authReq);
  }
}
```

### 3.2 Registrar interceptor
### Angular clásico (AppModule)
En `app.module.ts`:

```ts
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './auth/auth.interceptor';

providers: [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
]
```

---

## 4) AuthGuard (proteger rutas)

### 4.1 Crear guard
Crear:

```
src/app/auth/auth.guard.ts
```

```ts
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {

  constructor(private auth: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (!this.auth.isLoggedIn()) {
      this.router.navigate(['/login']);
      return false;
    }
    return true;
  }
}
```

### 4.2 Proteger rutas
Ejemplo en routing:

```ts
{ path: 'tareas', component: TasksComponent, canActivate: [AuthGuard] }
```

---

## 5) Consumir endpoint protegido

Crear/actualizar un servicio (ejemplo):

```
src/app/tasks/tasks.service.ts
```

```ts
return this.http.get<TaskResponse[]>('http://localhost:8080/api/tasks');
```

✅ Con el interceptor activo, esta petición irá con `Authorization: Bearer <token>` automáticamente.

---

## 6) Logout (obligatorio)

### 6.1 Crear botón de Logout (ejemplo simple)
En un componente de menú o navbar (o donde prefieras):

```html
<button (click)="logout()">Logout</button>
```

En el TS:

```ts
logout() {
  this.auth.logout();
  this.router.navigate(['/login']);
}
```

✅ Verificación: tras logout, `/tareas` debe volver a redirigir a `/login` (guard).



</div>
