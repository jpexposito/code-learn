<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (Práctica 1: Proyecto Tareas)

Esta práctica guiada te permitirá **crear una aplicación Angular desde cero** y construir, paso a paso, una mini-app de **Tasks** con **CRUD** contra un **servicio REST**. La persistencia en **BBDD** se realiza en el **backend** (por ejemplo Spring Boot + H2), y Angular se limita a consumir la API REST.

---

## ✅ Requisitos

- **Node.js 18+** (recomendado)
- **npm**
- **Angular CLI**
- Un backend REST accesible (recomendado: `http://localhost:8080` con Swagger)

### Instalar Angular CLI

```bash
npm i -g @angular/cli
ng version
```

---

## 🧱 0) Crear el proyecto Angular

### 0.1. Crear el proyecto

```bash
ng new gestor-tareas --routing --style=css
cd gestor-tareas
ng serve -o
```

Si todo está bien, verás la app en:

- `http://localhost:4200`

---

## 🔌 1) Preparar HttpClient (para consumir REST)

> En Angular moderno (standalone), lo habitual es registrar `provideHttpClient()` en `src/main.ts`.

Abre `src/main.ts` tienes algo similar a esto:

```ts
import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('gestor-tareas');
}
```

debes de añadir:

```ts
import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
```

> Si tienes referencias a `AppComponent` debes de crear los ficheros y relacionar:

```ts
import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes';

bootstrapApplication(AppComponent, {
  providers: [provideHttpClient(), provideRouter(routes)],
});
```


> Si tu proyecto usa `AppModule`, alternativa: importar `HttpClientModule` en `app.module.ts`.

> **Nota**: Si no existe _app.component.ts_. Debes de crear los ficheros app.component.ts, pp.component.css, y pp.component.html.

### Ficheros app.component.*

1) app.component.ts

```ts
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'Recarga gestor-tareas';
}
```

2) app.component.html

```html
<h1>{{ title }}</h1>
<router-outlet></router-outlet>
```

3) app.component.css

```css
h1 {
  font-family: system-ui, sans-serif;
}
```

---

## 🧩 2) Crear estructura base (modelos, componentes y servicio)

### 2.1. Crear carpetas

Crea estas carpetas dentro de `src/app`:

```text
src/app/
  models/
  services/
  components/
```

### 2.2. Generar componentes y servicio

A continuación vamos a generar los componentes y los servicios.

```bash
ng g c components/task-list
ng g c components/task-form
ng g s services/tasks-api
```

Debemos de obtener una salida similar a:

```bash
CREATE src/app/components/task-form/task-form.spec.ts (546 bytes)
CREATE src/app/components/task-form/task-form.ts (197 bytes)
CREATE src/app/components/task-form/task-form.html (24 bytes)
CREATE src/app/services/tasks-api.spec.ts (332 bytes)
CREATE src/app/services/tasks-api.ts (113 bytes)
```

Y la estructura de directorios quedaría similar a :

```text
src/app/
├─ components/
│  ├─ task-list/
│  └─ task-form/
└─ services/
   └─ tasks-api.service.ts
```

> **Nota**: Los comandos ***ng g ...*** se ejecutan en la raíz del proyecto, pero Angular siempre crea el código dentro de src/app.

> **Cada componente**: _representa una parte concreta de la aplicación (una vista o un bloque de la vista)_.

---

## 🧠 3) Modelo TypeScript (Task)

Crea el archivo:

📄 `src/app/models/task.ts`

```ts
export interface Task {
  id: number;
  titulo: string;
  descripcion?: string;
  completada: boolean;
}

export type NewTask = Omit<Task, 'id'>;
```

> **Sintaxis typeScript**: Una interfaz que contiene las propiedades y un tipo que hace uso de esa interfaz.

El modo de uso será:

```ts
const task1: Task = { id: 1, titulo: 'Estudiar Angular', completada: false };

const task2: Task = { id: 2, titulo: 'Hacer ejercicio', descripcion: '30 min', completada: true };

const nueva: NewTask = {
  titulo: 'Comprar pan',
  completada: false
};
// Crear una tarea pero sin el identificador id
```


---

## 🧾 4) Componente TaskForm (crear tareas)

### 4.1. Activar FormsModule (standalone)

Edita:

📄 `src/app/components/task-form/task-form.component.ts`

```ts
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import type { NewTask } from '../../models/task';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './task-form.component.html',
})
export class TaskFormComponent {
  @Output() crear = new EventEmitter<NewTask>();

  titulo: string = '';
  descripcion: string = '';
  error: string | null = null;

  enviar(): void {
    const t = this.titulo.trim();
    if (!t) {
      this.error = 'El título es obligatorio';
      return;
    }
    this.error = null;

    this.crear.emit({
      titulo: t,
      descripcion: this.descripcion.trim() || undefined,
      completada: false,
    });

    this.titulo = '';
    this.descripcion = '';
  }
}
```

Edita:

📄 `src/app/components/task-form/task-form.component.html`

```html
<h3>Nueva tarea</h3>

<p *ngIf="error" style="color: red">{{ error }}</p>

<input [(ngModel)]="titulo" placeholder="Título" />
<input [(ngModel)]="descripcion" placeholder="Descripción" />

<button (click)="enviar()" [disabled]="titulo.trim().length === 0">Crear</button>
```

---

## 📋 5) Servicio REST (TasksApiService)

Edita:

📄 `src/app/services/tasks-api.service.ts`

```ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import type { Observable } from 'rxjs';
import type { Task, NewTask } from '../models/task';

@Injectable({ providedIn: 'root' })
export class TasksApiService {
  private readonly baseUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Task[]> {
    return this.http.get<Task[]>(this.baseUrl);
  }

  create(t: NewTask): Observable<Task> {
    return this.http.post<Task>(this.baseUrl, t);
  }

  update(t: Task): Observable<Task> {
    return this.http.put<Task>(`${this.baseUrl}/${t.id}`, t);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
```

---

## 🧩 6) Componente TaskList (listar + crear + update + delete)

Edita:

📄 `src/app/components/task-list/task-list.component.ts`

```ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import type { Task, NewTask } from '../../models/task';
import { TasksApiService } from '../../services/tasks-api.service';
import { TaskFormComponent } from '../task-form/task-form.component';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule, TaskFormComponent],
  templateUrl: './task-list.component.html',
})
export class TaskListComponent implements OnInit {
  tareas: Task[] = [];
  cargando = false;
  error: string | null = null;

  constructor(private api: TasksApiService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.cargando = true;
    this.error = null;

    this.api.getAll().subscribe({
      next: (t) => {
        this.tareas = t;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudo cargar la lista de tareas';
        this.cargando = false;
      },
    });
  }

  crear(nueva: NewTask): void {
    this.api.create(nueva).subscribe({
      next: () => this.cargar(),
      error: () => (this.error = 'No se pudo crear la tarea'),
    });
  }

  toggle(t: Task): void {
    const actualizado: Task = { ...t, completada: !t.completada };
    this.api.update(actualizado).subscribe({
      next: () => this.cargar(),
      error: () => (this.error = 'No se pudo actualizar la tarea'),
    });
  }

  borrar(id: number): void {
    this.api.delete(id).subscribe({
      next: () => this.cargar(),
      error: () => (this.error = 'No se pudo borrar la tarea'),
    });
  }

  trackById(index: number, t: Task): number {
    return t.id;
  }
}
```

Edita:

📄 `src/app/components/task-list/task-list.component.html`

```html
<h2>Gestor de tareas</h2>

<app-task-form (crear)="crear($event)"></app-task-form>

<p *ngIf="cargando">Cargando...</p>
<p *ngIf="error" style="color: red">{{ error }}</p>

<p *ngIf="!cargando && tareas.length === 0">No hay tareas.</p>

<ul>
  <li *ngFor="let t of tareas; trackBy: trackById">
    <input type="checkbox" [checked]="t.completada" (change)="toggle(t)" />
    <strong>{{ t.titulo }}</strong>
    <span *ngIf="t.descripcion"> — {{ t.descripcion }}</span>
    <button (click)="borrar(t.id)">Borrar</button>
  </li>
</ul>
```

---

## 🧭 7) Routing + AppComponent (pantalla principal)

Edita:

📄 `src/app/app.routes.ts`

```ts
import { Routes } from '@angular/router';
import { TaskListComponent } from './components/task-list/task-list.component';

export const routes: Routes = [{ path: '', component: TaskListComponent }];
```

Edita:

📄 `src/app/app.component.ts`

```ts
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  template: `<router-outlet />`,
})
export class AppComponent {}
```

---

## 🌐 8) Backend REST + Swagger + BBDD 

Este README asume que tu backend ofrece:

- REST: `http://localhost:8080/api/tasks`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

> La BBDD (H2/SQLite/etc.) vive en el backend. Angular no accede a ella directamente.

### Nota sobre CORS
Si Angular (4200) y backend (8080) están separados, activa CORS en el backend:

```java
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TaskController { ... }
```

---

## ▶️ 9) Ejecutar todo

### 9.1. Levantar backend
Arranca tu backend (Spring Boot, etc.) en `http://localhost:8080`.

### 9.2. Levantar Angular

```bash
ng serve -o
```

- Abre `http://localhost:4200`
- Deberías poder:
  - listar tareas (GET)
  - crear (POST)
  - marcar completada (PUT)
  - borrar (DELETE)


</div>