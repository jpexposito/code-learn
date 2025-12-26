<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (5. Routing y navegación entre páginas (Angular moderno – Standalone))

<div align="center">
  <img src=images/5-routing-navegacion.png
   width="350">
</div>

En Angular, el **routing** permite crear una SPA (Single Page Application) con varias “páginas” sin recargar el navegador.

En Angular CLI **21** (y Angular moderno), los proyectos se crean por defecto con **Standalone Components**, por lo que:

-  No se usa `AppRoutingModule`
-  No se usa `RouterModule.forRoot(...)`
-  El router se configura con `provideRouter(routes)` en `app.config.ts`

---

## 1) Archivos clave del routing

En un proyecto generado con `--routing` encontrarás:

- `src/app/app.routes.ts` → definición de rutas
- `src/app/app.config.ts` → providers globales (router, http, etc.)
- `src/main.ts` → arranque con `bootstrapApplication(...)`

---

## 2) Definir rutas en `app.routes.ts`

Ejemplo de rutas para una app con páginas:

```ts
import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TasksComponent } from './pages/tasks/tasks.component';
import { TaskNewComponent } from './pages/task-new/task-new.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'tareas', component: TasksComponent },
  { path: 'tareas/nueva', component: TaskNewComponent },
  { path: '**', redirectTo: '' },
];
```

📌 La ruta `**` es la “ruta comodín” (si escriben una URL que no existe, redirige a Home).

---

## 3) Activar el router en `app.config.ts`

En proyectos standalone, el router se activa con `provideRouter(...)`:

```ts
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
  ],
};
```

---

## 4) Mostrar las páginas con `<router-outlet>`

El `router-outlet` es el “hueco” donde Angular dibuja la página correspondiente a la ruta.

En `src/app/app.component.html`:

```html
<router-outlet></router-outlet>
```

Y en `src/app/app.component.ts` asegúrate de importar `RouterOutlet`:

```ts
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {}
```

---

## 5) Navegar con enlaces (`routerLink`)

Para crear un menú de navegación se utilizan `routerLink` y `routerLinkActive`.

Ejemplo de navbar:

```html
<nav class="nav">
  <a routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }">Home</a>
  <a routerLink="/tareas" routerLinkActive="active">Tareas</a>
  <a routerLink="/tareas/nueva" routerLinkActive="active">Nueva tarea</a>
</nav>
```

En el componente navbar (standalone) hay que importar:

```ts
import { RouterLink, RouterLinkActive } from '@angular/router';
```

---

## 6) Navegación programática (desde TypeScript)

A veces queremos navegar tras un evento (por ejemplo, después de guardar un formulario).

```ts
import { Router } from '@angular/router';

constructor(private router: Router) {}

save() {
  // ...guardar
  this.router.navigateByUrl('/tareas');
}
```

---

## 7) Ejercicios

1. Crea una página `about` y añade una ruta `/acerca-de`.
2. Añade un enlace al navbar para esa página.
3. Haz que la ruta `**` redirija a `/` (Home) o a `/tareas` (elige una).

---

</div>
