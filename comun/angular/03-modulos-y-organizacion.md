<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (3. Módulos y organización del proyecto)

# 

## 3.1. `AppModule` y módulos de funcionalidad

En Angular, un **módulo** (`@NgModule`) agrupa componentes, directivas, pipes y servicios.

Ejemplo simplificado de `AppModule`:

```ts
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { TaskListComponent } from './tasks/task-list/task-list.component';
import { TaskFormComponent } from './tasks/task-form/task-form.component';
import { AppRoutingModule } from './app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    TaskListComponent,
    TaskFormComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
```

- `declarations`: componentes (y directivas/pipes) pertenecientes a este módulo.
- `imports`: otros módulos que quieres usar.
- `bootstrap`: componente raíz que arranca la app.

---

## 3.2. Creación de un módulo de tareas (opcional)

Para proyectos más grandes es buena idea separar por módulos de funcionalidad.

```bash
ng g module tasks
```

Esto crea `tasks.module.ts`. Podrías mover `TaskListComponent` y `TaskFormComponent`
a este módulo, y dejar `AppModule` más limpio.

```ts
// tasks/tasks.module.ts
@NgModule({
  declarations: [TaskListComponent, TaskFormComponent],
  imports: [CommonModule, ReactiveFormsModule],
})
export class TasksModule {}
```

Luego importas `TasksModule` en `AppModule` o lo cargas de forma perezosa con
el router (lazy loading).

---

## 3.3. Organización por carpetas

Un enfoque habitual:

```text
src/app/
├─ core/           # servicios globales (auth, layout, etc.)
├─ shared/         # componentes/pipes reutilizables
├─ tasks/          # módulo de tareas
│  ├─ task.model.ts
│  ├─ task-api.service.ts
│  ├─ task-list/
│  └─ task-form/
└─ app.module.ts
```

- Cada “zona” de tu app tiene su propia carpeta.
- Dentro de cada carpeta, los componentes tienen su propio subdirectorio.

---

## 3.4. Ejercicio práctico

1. Crear un módulo `TasksModule` e importar `TaskListComponent` y `TaskFormComponent` ahí.
2. Mantener en `AppModule` solo lo mínimo (AppComponent, AppRoutingModule, etc.).
3. Más adelante, convertir `TasksModule` en un módulo cargado por el router.

</div>
