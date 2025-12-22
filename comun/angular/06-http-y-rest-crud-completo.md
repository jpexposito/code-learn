<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (6. HTTP y REST: CRUD completo con Angular moderno)

<div align="center">
  <img src=images/6-http-rest-crud.png
   width="350">
</div>

En este tema aprenderás a consumir una API REST usando **HttpClient** en Angular **standalone** (CLI 21).

---

## 6.1. Activar HttpClient (Angular moderno)

En proyectos standalone **no importamos** `HttpClientModule` en un módulo.  
En su lugar, activamos HTTP en `app.config.ts`:

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

## 6.2. Modelo de datos: `Task` y `NewTask`

Crea el modelo en `src/app/models/task.model.ts`:

```ts
export interface Task {
  id: number;
  titulo: string;
  descripcion?: string;
  completada: boolean;
}

export type NewTask = Omit<Task, 'id'>;
```

- `Task` representa una tarea **guardada** (tiene `id`).
- `NewTask` representa una tarea **nueva** (sin `id`).

---

## 6.3. Servicio API (CRUD) con HttpClient

Crea el servicio en `src/app/services/tasks-api.service.ts`:

```ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NewTask, Task } from '../models/task.model';

@Injectable({ providedIn: 'root' })
export class TasksApiService {
  private readonly baseUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) {}

  list(): Observable<Task[]> {
    return this.http.get<Task[]>(this.baseUrl);
  }

  get(id: number): Observable<Task> {
    return this.http.get<Task>(`${this.baseUrl}/${id}`);
  }

  create(data: NewTask): Observable<Task> {
    return this.http.post<Task>(this.baseUrl, data);
  }

  update(task: Task): Observable<Task> {
    return this.http.put<Task>(`${this.baseUrl}/${task.id}`, task);
  }

  remove(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
```

---

## 6.4. Consumir el servicio desde un componente

Ejemplo: mostrar tareas en una página `TasksComponent`.

> Nota: en este manual usamos un enfoque sencillo con `subscribe()`.  
> Más adelante verás alternativas con `async` pipe.

```ts
import { Component, OnInit } from '@angular/core';
import { TasksApiService } from '../../services/tasks-api.service';
import { Task } from '../../models/task.model';

@Component({
  selector: 'app-tasks',
  standalone: true,
  templateUrl: './tasks.component.html',
})
export class TasksComponent implements OnInit {
  tasks: Task[] = [];
  loading = true;
  error: string | null = null;

  constructor(private api: TasksApiService) {}

  ngOnInit(): void {
    this.api.list().subscribe({
      next: (data) => { this.tasks = data; this.loading = false; },
      error: () => { this.error = 'No se pudieron cargar las tareas'; this.loading = false; },
    });
  }

  deleteTask(id: number) {
    this.api.remove(id).subscribe({
      next: () => this.tasks = this.tasks.filter(t => t.id !== id),
      error: () => this.error = 'No se pudo eliminar la tarea',
    });
  }
}
```

Plantilla `tasks.component.html`:

```html
<section class="card">
  <h2>Tareas</h2>

  @if (loading) {
    <p>Cargando...</p>
  } @else if (error) {
    <p class="error">{{ error }}</p>
  } @else {
    <ul>
      @for (t of tasks; track t.id) {
        <li>
          <strong>{{ t.titulo }}</strong>
          <button (click)="deleteTask(t.id)">Eliminar</button>
        </li>
      }
    </ul>
  }
</section>
```

---

## 6.5. Errores HTTP más comunes

- **0 / CORS**: el navegador bloquea la petición (CORS mal configurado).
- **401**: no estás autenticado (falta JWT).
- **403**: autenticado pero sin permisos.
- **500**: fallo del servidor.

> En el tema de JWT veremos cómo enviar el token con un **Interceptor**.

---

</div>
