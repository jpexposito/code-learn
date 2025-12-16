<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (4. Servicios e inyección de dependencias)

## 4.1. ¿Qué es un servicio en Angular?

Un **servicio** es una clase que encapsula lógica que no pertenece a la vista:
acceso a datos, lógica de negocio, utilidades compartidas…

Ejemplos típicos:

- Servicio para hablar con la API (`TaskApiService`).
- Servicio para autenticación (`AuthService`).
- Servicio para gestión de notificaciones (`NotificationService`).

---

## 4.2. Decorador `@Injectable()` y `providedIn`

```ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TaskApiService {
  // ...
}
```

- `@Injectable` indica que Angular puede inyectar este servicio.
- `providedIn: 'root'` → Angular crea una **única instancia (singleton)** para
  toda la aplicación.

---

## 4.3. Inyección por constructor

En cualquier componente:

```ts
import { Component } from '@angular/core';
import { TaskApiService } from './task-api.service';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
})
export class TaskListComponent {
  constructor(private taskApi: TaskApiService) {}

  // ahora puedes usar this.taskApi.getAll(), etc.
}
```

Angular se encargará de:

1. Crear una instancia de `TaskApiService` (si no existe aún).
2. Pasarla al constructor del componente cuando se cree.

---

## 4.4. Servicios para lógica de negocio vs HTTP

En nuestro micro-proyecto puedes distinguir:

- **Servicios API**: encargados de hacer peticiones HTTP y traducirlas a modelos TS.
- **Servicios de dominio** (si los necesitas): podrían contener lógica de negocio
  (validaciones más complejas, cálculos, etc.).

Por ejemplo, podrías tener:

- `TaskApiService` → habla con la API REST.
- `TaskService` → ofrece métodos de alto nivel (`completarTarea`, `obtenerTareasPendientes`…)
  y se apoya en `TaskApiService`.

---

## 4.5. Ejemplo: servicio de tareas en memoria (sin HTTP)

Antes de conectar con el backend, podrías practicar con un servicio en memoria:

```ts
// src/app/tasks/task-memory.service.ts
import { Injectable } from '@angular/core';
import { Task } from './task.model';

@Injectable({
  providedIn: 'root',
})
export class TaskMemoryService {
  private tasks: Task[] = [
    { id: 1, title: 'Tarea demo', description: 'Ejemplo en memoria', completed: false },
  ];
  private nextId = 2;

  getAll(): Task[] {
    return this.tasks;
  }

  create(task: Omit<Task, 'id'>): Task {
    const newTask: Task = { id: this.nextId++, ...task };
    this.tasks.push(newTask);
    return newTask;
  }

  update(id: number, changes: Partial<Task>): Task | undefined {
    const task = this.tasks.find(t => t.id === id);
    if (!task) return undefined;
    Object.assign(task, changes);
    return task;
  }

  delete(id: number): void {
    this.tasks = this.tasks.filter(t => t.id !== id);
  }
}
```

Este servicio te permite practicar el flujo HTML + TS (componente → servicio) antes
de añadir la capa HTTP.

---

## 4.6. Ejercicio práctico

1. Crear un servicio en memoria como el anterior.
2. Usarlo desde `TaskListComponent` y `TaskFormComponent` en lugar de la API REST.
3. Cuando lo controles, sustituirlo por `TaskApiService` que hace peticiones HTTP reales.

</div>