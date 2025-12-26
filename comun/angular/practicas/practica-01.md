<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (Práctica 1: Gestor de tareas con páginas, estilos y formulario (Angular moderno)

<div align="center">
  <img src=../images/practicas/practica-01.png
   width="350">
</div>

**Objetivo:** crear una aplicación Angular con varias páginas (routing), estilos y un formulario para **crear** y **eliminar** tareas (CRUD básico en memoria).

> Esta práctica está pensada para Angular CLI **21** con componentes **standalone**.

---

## 0) Requisitos

- Node.js 22.x
- Angular CLI 21.x
- npm 10.x

---

## 1) Crear el proyecto

En una carpeta fuera de cualquier workspace Angular:

```bash
ng new gestor-tareas
cd gestor-tareas
ng serve -o
```

Opciones:
- Styles: **CSS**
- Routing: **Yes**
- SSR: **No**
- AI tools: **None**

---

## 2) Generar componentes y servicio

Ejecuta los comandos desde la **raíz del proyecto** (donde está `angular.json`):

```bash
ng g c pages/home
ng g c pages/tasks
ng g c pages/task-new
ng g c shared/navbar

ng g s services/tasks
```

Estructura resultante:

```text
src/app/
├─ pages/
│  ├─ home/
│  ├─ tasks/
│  └─ task-new/
├─ shared/
│  └─ navbar/
└─ services/
   └─ tasks.service.ts
```

---

## 3) Crear el modelo Task

Crea el archivo:

**`src/app/models/task.model.ts`**

```ts
export interface Task {
  id: number;
  titulo: string;
  descripcion?: string;
  completada: boolean;
}

export type NewTask = Omit<Task, 'id'>;
```

> **Nota:**  
> Se utilizan dos tipos (`Task` y `NewTask`) para diferenciar entre una tarea **ya creada** y una tarea **nueva**.  
> `Task` incluye el campo `id`, que identifica de forma única a la tarea, mientras que `NewTask` lo omite porque el `id` se genera automáticamente en el servicio y no en el formulario.  
> Esto mejora la claridad del código, evita errores y aprovecha el tipado fuerte de TypeScript.

---

## 4) Routing: crear páginas

Edita **`src/app/app.routes.ts`**:

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

> **Nota**: Si te genera un error de importación comprueba el nombre de los ficheros y de los componentes que te ha generado. Serán sin 'component', y los componentes serán `Task` en lugar de `TasksComponent` y así sucesivamente.

> ```js
> import { HomeComponent } from './pages/home/home.component';
> import { TasksComponent } from './pages/tasks/tasks.component';
> import { TaskNewComponent } from './pages/task-new/task-new.component';
> ```

---

## 5) AppComponent: navbar + router-outlet

En **`src/app/app.component.html`**:

```html
<app-navbar></app-navbar>

<main class="container">
  <router-outlet></router-outlet>
</main>
```

En **`src/app/app.component.ts`** (importa RouterOutlet + NavbarComponent):

```ts
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './shared/navbar/navbar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {}
```

---

## 6) Navbar con enlaces

**`src/app/shared/navbar/navbar.component.ts`**

```ts
import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {}
```

**`src/app/shared/navbar/navbar.component.html`**

```html
<nav class="nav">
  <a routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }">Home</a>
  <a routerLink="/tareas" routerLinkActive="active">Tareas</a>
  <a routerLink="/tareas/nueva" routerLinkActive="active">Nueva tarea</a>
</nav>
```

**`src/app/shared/navbar/navbar.component.css`**

```css
.nav { display: flex; gap: 12px; padding: 12px 16px; background: white; border-bottom: 1px solid #eee; }
.nav a { text-decoration: none; color: #333; padding: 8px 10px; border-radius: 10px; }
.nav a.active { background: #ffe6e6; color: #b71c1c; font-weight: 600; }
```

---

## 7) Estilos globales (mínimos)

Edita **`src/styles.css`**:

```css
body { margin: 0; font-family: system-ui, sans-serif; background: #f6f7fb; }
.container { max-width: 900px; margin: 0 auto; padding: 16px; }
.card { background: white; border-radius: 12px; padding: 16px; box-shadow: 0 2px 12px rgba(0,0,0,.06); }
.row { display: flex; gap: 12px; flex-wrap: wrap; }
.btn { border: 0; padding: 10px 12px; border-radius: 10px; cursor: pointer; }
.btn-primary { background: #d32f2f; color: white; }
.btn-ghost { background: transparent; border: 1px solid #ddd; }
.input { width: 100%; padding: 10px 12px; border-radius: 10px; border: 1px solid #ddd; }
.error { color: #b71c1c; font-size: 0.9rem; margin-top: 6px; }
```

---

## 8) Servicio `TasksService` (en memoria)

Edita **`src/app/services/tasks.service.ts`**:

```ts
import { Injectable } from '@angular/core';
import { NewTask, Task } from '../models/task.model';

@Injectable({ providedIn: 'root' })
export class TasksService {
  private tasks: Task[] = [
    { id: 1, titulo: 'Instalar Angular', descripcion: 'CLI + Node', completada: false },
    { id: 2, titulo: 'Crear primera página', completada: true },
  ];
  private nextId = 3;

  list(): Task[] {
    return this.tasks;
  }

  add(data: NewTask): Task {
    const created: Task = { id: this.nextId++, ...data };
    this.tasks = [created, ...this.tasks];
    return created;
  }

  remove(id: number): void {
    this.tasks = this.tasks.filter(t => t.id !== id);
  }
}
```

---

## 9) Página Home

**`src/app/pages/home/home.component.html`**

```html
<section class="card">
  <h2>Bienvenido/a</h2>
  <p>Esta app es un ejercicio de Angular moderno: páginas, estilos y formularios.</p>
</section>
```

---

## 10) Página Tareas (listar y eliminar)

**`src/app/pages/tasks/tasks.component.ts`**

```ts
import { Component } from '@angular/core';
import { TasksService } from '../../services/tasks.service';

@Component({
  selector: 'app-tasks',
  standalone: true,
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css',
})
export class TasksComponent {
  constructor(public tasksService: TasksService) {}

  remove(id: number) {
    this.tasksService.remove(id);
  }
}
```

**`tasks.component.html`**

```html
<section class="card">
  <h2>Lista de tareas</h2>

  @if (tasksService.list().length === 0) {
    <p>No hay tareas todavía.</p>
  } @else {
    <ul class="list">
      @for (t of tasksService.list(); track t.id) {
        <li class="item">
          <div>
            <strong>{{ t.titulo }}</strong>
            @if (t.descripcion) { <div class="muted">{{ t.descripcion }}</div> }
          </div>

          <button class="btn btn-ghost" (click)="remove(t.id)">Eliminar</button>
        </li>
      }
    </ul>
  }
</section>
```

**`tasks.component.css`**

```css
.list { list-style: none; padding: 0; margin: 12px 0 0; }
.item { display: flex; justify-content: space-between; align-items: center; padding: 10px 0; border-top: 1px solid #eee; }
.muted { color: #666; font-size: .95rem; margin-top: 4px; }
```

---

## 11) Página Nueva tarea (formulario reactivo)

### 11.1 Importante: usar ReactiveFormsModule
En el componente `TaskNewComponent` importamos `ReactiveFormsModule`.

**`src/app/pages/task-new/task-new.component.ts`**

```ts
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TasksService } from '../../services/tasks.service';

@Component({
  selector: 'app-task-new',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './task-new.component.html',
  styleUrl: './task-new.component.css',
})
export class TaskNewComponent {
  constructor(
    private fb: FormBuilder,
    private tasks: TasksService,
    private router: Router
  ) {}

  form = this.fb.group({
    titulo: ['', [Validators.required, Validators.minLength(3)]],
    descripcion: [''],
    completada: [false],
  });

  save() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.tasks.add(this.form.getRawValue());
    this.router.navigateByUrl('/tareas');
  }
}
```

**`task-new.component.html`**

```html
<section class="card">
  <h2>Nueva tarea</h2>

  <form (ngSubmit)="save()">
    <label>
      Título
      <input class="input" formControlName="titulo" />
    </label>

    @if (form.controls.titulo.touched && form.controls.titulo.invalid) {
      <div class="error">
        El título es obligatorio y debe tener al menos 3 caracteres.
      </div>
    }

    <label>
      Descripción (opcional)
      <input class="input" formControlName="descripcion" />
    </label>

    <label class="check">
      <input type="checkbox" formControlName="completada" />
      Completada
    </label>

    <div class="row">
      <button class="btn btn-primary" type="submit">Guardar</button>
      <button class="btn btn-ghost" type="button" (click)="router.navigateByUrl('/tareas')">Cancelar</button>
    </div>
  </form>
</section>
```

**`task-new.component.css`**

```css
form { display: grid; gap: 12px; margin-top: 12px; }
label { display: grid; gap: 6px; }
.check { display: flex; align-items: center; gap: 8px; }
```

---

##  Ejercicios para entregar

1. Cambia el tema de colores (fondo, navbar y botones).
2. Añade validación: si hay descripción, mínimo 5 caracteres.
3. Añade un botón “Marcar completada” en la lista.
4. Crea una página “Acerca de” y añádela al router.

---

</div>
