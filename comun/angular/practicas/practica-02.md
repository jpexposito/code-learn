<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (Práctica 02: Consumir una API REST con Angular — TaskController)

<div align="center">
  <img src=../images/practicas/practica-02.png width="350">
</div>

**Objetivo:** Partiendo de la **Práctica 01** (ya funciona), reemplazar el almacenamiento en memoria por peticiones HTTP a una **API REST** (CRUD completo) expuesta por `TaskController.java`.

---

## 0) Requisitos

- Proyecto de **Práctica 01** funcionando (routing + páginas + formulario).
- Una API REST en ejecución que expone las rutas del controlador:

**Base URL (según `TaskController`):**
- `http://localhost:8080/api/v1/tasks`

**Endpoints disponibles:**
- `GET    /api/v1/tasks` → lista de tareas
- `GET    /api/v1/tasks/{id}` → una tarea
- `POST   /api/v1/tasks` → crea una tarea
- `PUT    /api/v1/tasks/{id}` → actualiza una tarea
- `DELETE /api/v1/tasks/{id}` → elimina una tarea

> ⚠️ Si la API NO está disponible, tu app Angular debe mostrar un **error controlado** (mensaje visible) y no romperse.

---

## 1) Configurar HttpClient (Angular moderno)

En `src/app/app.config.ts` añade `provideHttpClient()`:

```ts
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
  ],
};
```

---

## 2) Activar HttpClient

En Angular (standalone) añade `provideHttpClient()` en `src/app/app.config.ts`:

```ts
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
  ],
};
```

---

## 3) Configurar la URL de la API

En `src/environments/environment.ts`:

```ts
export const environment = {
  apiBaseUrl: 'http://localhost:8080/api/v1',
};
```

---

## 4) Modelo local

`src/app/models/task.model.ts`

```ts
export interface Task {
  id: number;
  titulo: string;
  descripcion?: string;
  completada: boolean;
}

export type NewTask = Omit<Task, 'id'>;
```

---

## 5) Crear el mapper (API ↔ Local)

Crea `src/app/services/task-mapper.ts`:

```ts
import { Task, NewTask } from '../models/task.model';


export interface ApiTask {
  id: number;
  title: string;
  description?: string;
  completed: boolean;
}

export type ApiNewTask = Omit<ApiTask, 'id'>;

export function fromApiTask(a: ApiTask): Task {
  return {
    id: a.id,
    titulo: a.title,
    descripcion: a.description,
    completada: a.completed,
  };
}

export function toApiNewTask(t: NewTask): ApiNewTask {
  return {
    title: t.titulo,
    description: t.descripcion ?? '',
    completed: t.completada,
  };
}
```

---

## 6) Servicio Angular para consumir la API (con error controlado)

Crea el servicio:

```bash
ng g s services/tasks-api
```

Implementa `src/app/services/tasks-api.service.ts`:

```ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { NewTask, Task } from '../models/task.model';
import { ApiTask, fromApiTask, toApiNewTask } from './task-mapper';

@Injectable({ providedIn: 'root' })
export class TasksApiService {
  private baseUrl = `${environment.apiBaseUrl}/tasks`;

  constructor(private http: HttpClient) {}

  list(): Observable<Task[]> {
    return this.http.get<ApiTask[]>(this.baseUrl).pipe(
      map(arr => arr.map(fromApiTask)),
      catchError(() => throwError(() => new Error('No se pudo conectar con la API de tareas.')))
    );
  }

  getById(id: number): Observable<Task> {
    return this.http.get<ApiTask>(`${this.baseUrl}/${id}`).pipe(
      map(fromApiTask),
      catchError(() => throwError(() => new Error('No se pudo obtener la tarea.')))
    );
  }

  create(data: NewTask): Observable<Task> {
    return this.http.post<ApiTask>(this.baseUrl, toApiNewTask(data)).pipe(
      map(fromApiTask),
      catchError(() => throwError(() => new Error('No se pudo crear la tarea.')))
    );
  }

  update(id: number, data: NewTask): Observable<Task> {
    return this.http.put<ApiTask>(`${this.baseUrl}/${id}`, toApiNewTask(data)).pipe(
      map(fromApiTask),
      catchError(() => throwError(() => new Error('No se pudo actualizar la tarea.')))
    );
  }

  remove(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      catchError(() => throwError(() => new Error('No se pudo eliminar la tarea.')))
    );
  }
}
```

✅ Si la API cae o no responde, el error que llega al componente es **controlado** (un `Error` con un mensaje claro).

---

## 7) Ejemplo de componente con manejo de errores

En tu `TasksComponent`, actualiza y apunta a la api-tasks:

```ts
import { Component, OnInit } from '@angular/core';
import { Task } from '../../models/task.model';
import { TasksApiService } from '../../services/tasks-api.service';

@Component({
  selector: 'app-tasks',
  standalone: true,
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css',
})
export class TasksComponent implements OnInit {
  tasks: Task[] = [];
  errorMsg: string | null = null;
  loading = false;

  constructor(public api: TasksApiService) {}

  ngOnInit(): void {
    this.load();
  }

  load() {
    this.loading = true;
    this.errorMsg = null;

    this.api.list().subscribe({
      next: data => {
        this.tasks = data;
        this.loading = false;
      },
      error: (e: Error) => {
        this.tasks = [];
        this.errorMsg = e.message;
        this.loading = false;
      }
    });
  }

  remove(id: number) {
    this.errorMsg = null;

    this.api.remove(id).subscribe({
      next: () => this.load(),
      error: (e: Error) => this.errorMsg = e.message
    });
  }
}

```

En el HTML:

```html
@if (errorMsg) {
  <p class="error">{{ errorMsg }}</p>
}
```

quedando como sigue:

```html
<section class="card">
  <h2>Lista de tareas</h2>

  @if (loading) {
    <p>Cargando...</p>
  }

  @if (errorMsg) {
    <p class="error">{{ errorMsg }}</p>
  }

  @if (!loading && !errorMsg && tasks.length === 0) {
    <p>No hay tareas todavía.</p>
  } @else if (!loading && !errorMsg) {
    <ul class="list">
      @for (t of tasks; track t.id) {
        <li class="item">
          <div>
            <strong>{{ t.titulo }}</strong>
            @if (t.descripcion) {
              <div class="muted">{{ t.descripcion }}</div>
            }
          </div>

          <button class="btn btn-ghost" (click)="remove(t.id)">Eliminar</button>
        </li>
      }
    </ul>
  }
</section>
```

---

## 8) CORS / Proxy (si el navegador bloquea)

Si Angular está en `http://localhost:4200` y API en `http://localhost:8080`, puede aparecer error CORS.

### Proxy (recomendado)
Crea `proxy.conf.json` en la raíz del proyecto Angular:

```json
{
  "/api": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true
  }
}
```

Arranca Angular:

```bash
ng serve --proxy-config proxy.conf.json
```

Y en `environment.ts` usa:

```ts
export const environment = {
  apiBaseUrl: '/api/v1',
};
```

---

## Checklist 
- [ ] La lista de tareas se carga desde `GET /api/v1/tasks`
- [ ] Se puede crear una tarea con `POST /api/v1/tasks`
- [ ] Se puede eliminar una tarea con `DELETE /api/v1/tasks/{id}`
- [ ] Se muestra un **mensaje controlado** si la API no está disponible
