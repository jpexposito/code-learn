<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (Práctica 2: Conectar el Gestor de Tareas a una API REST - CRUD)

<div align="center">
  <img src=../images/practicas/practica-02.png
   width="350">
</div>

**Objetivo:** reemplazar el almacenamiento en memoria por peticiones HTTP a una API REST.

> Esta práctica parte del proyecto de la **Práctica 01**.

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

## 2) Crear un servicio `TasksApiService`

```bash
ng g s services/tasks-api
```

Ejemplo (`src/app/services/tasks-api.service.ts`):

```ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NewTask, Task } from '../models/task.model';

@Injectable({ providedIn: 'root' })
export class TasksApiService {
  private baseUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) {}

  list(): Observable<Task[]> {
    return this.http.get<Task[]>(this.baseUrl);
  }

  create(data: NewTask): Observable<Task> {
    return this.http.post<Task>(this.baseUrl, data);
  }

  remove(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
```

---

## 3) Actualizar la página de tareas para usar la API

En `TasksComponent`:
- guarda el listado en una variable `tasks: Task[]`
- carga datos en `ngOnInit`
- borra llamando a `remove` y recargando

📌 Consejo: empieza con `subscribe` y luego refactoriza.

---

## 4) Actualizar el formulario para crear en API

En `TaskNewComponent`, al guardar:
- llama a `TasksApiService.create(...)`
- al terminar, navega a `/tareas`

---

## ✅Entregables

- Listado de tareas cargado desde API
- Crear tarea desde formulario (POST)
- Eliminar tarea (DELETE)
- Manejo mínimo de errores (mostrar mensaje si falla)

---

</div>
