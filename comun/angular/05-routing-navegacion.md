<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (Routing (navegación))

## 5.1. Configurar el router

El router de Angular te permite definir qué componente se muestra para cada URL.

```ts
// src/app/app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TaskListComponent } from './tasks/task-list/task-list.component';
import { TaskFormComponent } from './tasks/task-form/task-form.component';

const routes: Routes = [
  { path: '', redirectTo: 'tasks', pathMatch: 'full' },
  { path: 'tasks', component: TaskListComponent },
  { path: 'tasks/new', component: TaskFormComponent },
  { path: 'tasks/edit/:id', component: TaskFormComponent },
  { path: '**', redirectTo: 'tasks' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
```

- `/tasks` → lista de tareas.
- `/tasks/new` → formulario de creación.
- `/tasks/edit/:id` → formulario de edición (usando el mismo componente).

---

## 5.2. `<router-outlet>` y `routerLink`

En `app.component.html` suele estar el “marco” de la aplicación:

```html
<nav>
  <a routerLink="/tasks">Lista</a>
  <a routerLink="/tasks/new">Nueva tarea</a>
</nav>

<hr />

<router-outlet></router-outlet>
```

- `<router-outlet>` es el lugar donde Angular renderiza el componente correspondiente a la ruta actual.
- `routerLink` genera enlaces que el router intercepta sin recargar la página.

---

## 5.3. Parámetros de ruta

Para editar una tarea, puedes usar una ruta como `/tasks/edit/3` (`id = 3`).

En el componente:

```ts
import { ActivatedRoute, Router } from '@angular/router';

constructor(
  private route: ActivatedRoute,
  private router: Router,
  private taskApi: TaskApiService,
) {}

ngOnInit(): void {
  const idParam = this.route.snapshot.paramMap.get('id');
  if (idParam) {
    this.mode = 'edit';
    this.taskId = Number(idParam);
    this.loadTaskForEdit(this.taskId);
  } else {
    this.mode = 'create';
  }
}
```

De esta forma el mismo componente `TaskFormComponent` sirve para crear y para editar.

---

## 5.4. Ejercicio práctico

1. Añadir la ruta `/tasks/edit/:id`.
2. En el listado, poner un botón “Editar” que navegue a esa ruta usando `[routerLink]="['/tasks/edit', task.id]"`.
3. Adaptar `TaskFormComponent` para que si hay `id` en la ruta cargue la tarea, y si no, cree una nueva.


