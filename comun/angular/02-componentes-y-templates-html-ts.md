<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (2. Componentes y templates: trabajar con HTML + TypeScript)

Este es uno de los puntos más importantes. Aquí es donde se ve perfectamente
la relación entre **HTML (template)** y **TypeScript (clase del componente)**.

## 2.1. Anatomía de un componente

Un componente típico está formado por:

- Un archivo `.ts` → clase TypeScript, decorada con `@Component`.
- Una plantilla `.html` → HTML con bindings.
- Opcionalmente, estilos `.css` o `.scss`.

Ejemplo:

```ts
// src/app/tasks/task-list/task-list.component.ts
import { Component, OnInit } from '@angular/core';
import { TaskApiService } from '../task-api.service';
import { Task } from '../task.model';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css'],
})
export class TaskListComponent implements OnInit {
  title = 'Listado de tareas';
  tasks: Task[] = [];
  loading = false;
  error?: string;

  constructor(private taskApi: TaskApiService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.loading = true;
    this.error = undefined;

    this.taskApi.getAll().subscribe({
      next: tasks => {
        this.tasks = tasks;
        this.loading = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar las tareas';
        this.loading = false;
      },
    });
  }

  delete(task: Task): void {
    if (!confirm(`¿Eliminar tarea "${task.title}"?`)) return;
    this.taskApi.delete(task.id).subscribe({
      next: () => this.loadTasks(),
      error: () => (this.error = 'No se pudo eliminar la tarea'),
    });
  }
}
```

Y la plantilla asociada:

```html
<!-- src/app/tasks/task-list/task-list.component.html -->
<h2>{{ title }}</h2>

<button (click)="loadTasks()" [disabled]="loading">
  Recargar
</button>

<p *ngIf="loading">Cargando...</p>
<p *ngIf="error" class="error">{{ error }}</p>

<ul *ngIf="!loading && !error">
  <li *ngFor="let task of tasks">
    <strong [class.completed]="task.completed">{{ task.title }}</strong>
    <span> - {{ task.description }}</span>

    <button (click)="delete(task)">Eliminar</button>
  </li>
</ul>
```

Fíjate cómo el **HTML se apoya totalmente** en las propiedades y métodos que
expones desde la clase TypeScript.

---

## 2.2. Unidireccional, bidireccional y eventos

### 2.2.1. Interpolación (TS → HTML)

```html
<h1>{{ title }}</h1>
```

- `title` es una propiedad de la clase TS.
- En cada cambio de detección, Angular evalúa `title` y lo pinta en el HTML.

### 2.2.2. Property binding (TS → HTML)

```html
<button [disabled]="loading">Recargar</button>
```

- `[disabled]` indica que la propiedad HTML `disabled` depende de la expresión
  TypeScript `loading`.

### 2.2.3. Event binding (HTML → TS)

```html
<button (click)="loadTasks()">Recargar</button>
```

- `(click)` escucha el evento del DOM y ejecuta el método `loadTasks()` en la
  clase.
- Así viaja la información **del usuario (clic)** hacia tu lógica.

### 2.2.4. Two-way binding (TS ↔ HTML)

Requiere `FormsModule`. Patrón:

```html
<input [(ngModel)]="newTitle" />
```

- Combina `[value]="newTitle"` y `(input)="newTitle = $event.target.value"`.
- Ideal para formularios sencillos.

Ejemplo en contexto:

```ts
newTitle = '';

addTask(): void {
  if (!this.newTitle.trim()) return;
  // Llamada a servicio para crear tarea...
}
```

```html
<input [(ngModel)]="newTitle" placeholder="Título de la tarea" />
<button (click)="addTask()">Añadir</button>
```

---

## 2.3. Pensar primero en la clase, luego en el HTML

Un buen patrón de trabajo:

1. **Define el modelo de datos** en TypeScript (interfaces).
2. **Crea la clase del componente**:
   - qué propiedades necesita (`tasks`, `loading`, `error`…)
   - qué métodos va a usar el usuario (`loadTasks`, `delete`, `edit`, etc.)
3. **Escribe el HTML** usando esos nombres con `{{ }}` y bindings.

Ejemplo de diseño mental para un listado:

- Datos necesarios: `Task[]`, `loading`, `error`.
- Acciones: recargar, eliminar, navegar a editar.

Primero defines la clase con esas propiedades y métodos, luego haces el HTML.

---

## 2.4. Uso de directivas en el HTML

### `*ngIf`

```html
<p *ngIf="loading">Cargando...</p>
<p *ngIf="error" class="error">{{ error }}</p>
```

La expresión tras `*ngIf` debe ser booleana. La lógica para decidir **cuándo**
se muestra está en TypeScript; el HTML solo refleja el resultado.

### `*ngFor`

```html
<li *ngFor="let task of tasks">{{ task.title }}</li>
```

- `tasks` viene de TS.
- El template decide **cómo** mostrar cada tarea.

### Combinando directivas

```html
<ul *ngIf="tasks.length > 0; else noTasks">
  <li *ngFor="let task of tasks">
    {{ task.title }}
  </li>
</ul>

<ng-template #noTasks>
  <p>No hay tareas todavía.</p>
</ng-template>
```

---

## 2.5. Comunicación entre componentes (padre ↔ hijo)

### Paso de datos con `@Input()`

Componente hijo (`task-item`):

```ts
// task-item.component.ts
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Task } from '../task.model';

@Component({
  selector: 'app-task-item',
  templateUrl: './task-item.component.html',
})
export class TaskItemComponent {
  @Input() task!: Task;
  @Output() delete = new EventEmitter<Task>();

  onDeleteClick(): void {
    this.delete.emit(this.task);
  }
}
```

```html
<!-- task-item.component.html -->
<li>
  <span>{{ task.title }}</span>
  <button (click)="onDeleteClick()">Eliminar</button>
</li>
```

Componente padre (`task-list`):

```html
<ul>
  <app-task-item
    *ngFor="let task of tasks"
    [task]="task"
    (delete)="delete($event)"
  ></app-task-item>
</ul>
```

En TypeScript del padre:

```ts
delete(task: Task): void {
  // Lógica para borrar, delegando en el servicio
}
```

Así tienes una **separación clara**:

- El hijo se encarga de cómo se ve una tarea individual.
- El padre decide qué hacer cuando se pide borrar.

---

## 2.6. Ciclo de vida del componente aplicado a HTML+TS

Método más importante para empezar: `ngOnInit()`.

```ts
ngOnInit(): void {
  this.loadTasks();
}
```

- Se ejecuta una vez que Angular ha inicializado el componente.
- Buen sitio para:
  - Llamadas iniciales a la API.
  - Suscribirse a datos de un servicio.

También existen otros hooks (`ngOnChanges`, `ngOnDestroy`, etc.), pero al
principio céntrate en `ngOnInit` para lógica de carga inicial.

---

## 2.7. Buenas prácticas HTML + TS

- Mantén la **lógica compleja en TypeScript**, no en el HTML.
  - MAL: `*ngIf="usuarios.filter(u => u.activo).length > 0"`
  - MEJOR: tener una propiedad o método en TS que ya devuelva la lista filtrada.
- Nombres claros y consistentes:
  - `tasks`, `loadTasks()`, `deleteTask()`…
- Evita acceder directamente al DOM (`document.querySelector`); usa bindings
  y el sistema de plantillas de Angular.

Si tienes clara esta relación entre clase TS y template HTML, el resto de temas
(routing, formularios, HTTP…) se vuelven mucho más fáciles de entender.
