<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (6. HTTP y REST) 

En este capítulo completamos el CRUD (Create, Read, Update, Delete) contra una API REST.

## 6.1. Backend Spring Boot (resumen)

Supondremos que tienes un backend con endpoints:

- `GET    /api/tasks`       → lista todas las tareas
- `GET    /api/tasks/{id}`  → obtiene una tarea
- `POST   /api/tasks`       → crea una tarea
- `PUT    /api/tasks/{id}`  → reemplaza una tarea
- `DELETE /api/tasks/{id}`  → elimina una tarea

Controlador de ejemplo simplificado:

```java
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
    // lista en memoria y métodos getAll, getById, create, update, delete...
}
```

---

## 6.2. Modelo de datos en Angular

```ts
// src/app/tasks/task.model.ts
export interface Task {
  id: number;
  title: string;
  description: string;
  completed: boolean;
}
```

---

## 6.3. Servicio `TaskApiService` completo

```ts
// src/app/tasks/task-api.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from './task.model';

@Injectable({
  providedIn: 'root',
})
export class TaskApiService {
  private readonly baseUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Task[]> {
    return this.http.get<Task[]>(this.baseUrl);
  }

  getById(id: number): Observable<Task> {
    return this.http.get<Task>(`${this.baseUrl}/${id}`);
  }

  create(task: Omit<Task, 'id'>): Observable<Task> {
    return this.http.post<Task>(this.baseUrl, task);
  }

  update(id: number, task: Omit<Task, 'id'>): Observable<Task> {
    return this.http.put<Task>(`${this.baseUrl}/${id}`, task);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
```

---

## 6.4. Usar el servicio en el listado (Read + Delete)

`TaskListComponent` ya usa `getAll()` y `delete()` en el ejemplo anterior.
Lo importante es entender el flujo:

- `ngOnInit` → `loadTasks()` → `taskApi.getAll().subscribe(...)`.
- Botón “Eliminar” → `taskApi.delete(task.id).subscribe(...)` y después `loadTasks()`.

---

## 6.5. Usar el servicio en el formulario (Create + Update)

En `TaskFormComponent`:

- Modo “crear”: sin `id` en la ruta → `create()`.
- Modo “editar”: con `id` → primero `getById()` para rellenar el formulario, luego `update()`.

Ejemplo parcial:

```ts
// src/app/tasks/task-form/task-form.component.ts
mode: 'create' | 'edit' = 'create';
taskId?: number;

ngOnInit(): void {
  const idParam = this.route.snapshot.paramMap.get('id');
  if (idParam) {
    this.mode = 'edit';
    this.taskId = Number(idParam);
    this.loadTask(this.taskId);
  }

  this.form = this.fb.group({
    title: ['', Validators.required],
    description: [''],
    completed: [false],
  });
}

loadTask(id: number): void {
  this.taskApi.getById(id).subscribe({
    next: task => this.form.patchValue(task),
    error: () => (this.error = 'No se pudo cargar la tarea'),
  });
}

onSubmit(): void {
  if (this.form.invalid) {
    this.form.markAllAsTouched();
    return;
  }

  const value = this.form.value as Omit<Task, 'id'>;

  this.saving = true;
  this.error = undefined;

  const request$ =
    this.mode === 'create'
      ? this.taskApi.create(value)
      : this.taskApi.update(this.taskId!, value);

  request$.subscribe({
    next: () => {
      this.saving = false;
      this.router.navigate(['/tasks']);
    },
    error: () => {
      this.error = 'No se pudo guardar la tarea';
      this.saving = false;
    },
  });
}
```

Con esto: **CRUD completo**:

- **Create** → `create(task)` desde formulario (modo create).
- **Read**   → `getAll()` para el listado, `getById()` para editar.
- **Update** → `update(id, task)` desde formulario (modo edit).
- **Delete** → `delete(id)` desde el listado.

---

## 6.6. Ejercicio práctico

1. Implementar todos los métodos en `TaskApiService`.
2. Completar `TaskFormComponent` para que funcione tanto para crear como para editar.
3. Probar el flujo completo contra tu backend Spring Boot.

</div>