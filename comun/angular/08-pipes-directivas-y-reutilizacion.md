<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (8. Pipes, directivas y reutilización) 


<div align="center">
  <img src=images/8-pipes-directivas-reutilizacion.png
   width="350">
</div>

## 8.1. Pipes incorporados

Los **pipes** transforman valores en el template.

Ejemplos:

```html
<p>{{ task.title | uppercase }}</p>
<p>{{ today | date: 'short' }}</p>
```

- `uppercase`: pone en mayúsculas.
- `date`: formatea fechas.

---

## 8.2. Pipe personalizado

Ejemplo: pipe que marca el título de tareas completadas con un prefijo:

```ts
// src/app/tasks/pipes/task-label.pipe.ts
import { Pipe, PipeTransform } from '@angular/core';
import { Task } from '../task.model';

@Pipe({
  name: 'taskLabel',
})
export class TaskLabelPipe implements PipeTransform {
  transform(task: Task): string {
    const prefix = task.completed ? '[✓]' : '[ ]';
    return `${prefix} ${task.title}`;
  }
}
```

Uso en el HTML:

```html
<li *ngFor="let task of tasks">
  {{ task | taskLabel }}
</li>
```

---

## 8.3. Directiva de atributo personalizada

Ejemplo: directiva que pinta en gris las tareas completadas.

```ts
// src/app/tasks/directives/task-completed.directive.ts
import { Directive, ElementRef, Input, OnChanges } from '@angular/core';

@Directive({
  selector: '[appTaskCompleted]',
})
export class TaskCompletedDirective implements OnChanges {
  @Input('appTaskCompleted') completed = false;

  constructor(private el: ElementRef) {}

  ngOnChanges(): void {
    if (this.completed) {
      this.el.nativeElement.style.color = 'gray';
      this.el.nativeElement.style.textDecoration = 'line-through';
    } else {
      this.el.nativeElement.style.color = '';
      this.el.nativeElement.style.textDecoration = '';
    }
  }
}
```

Uso:

```html
<li *ngFor="let task of tasks">
  <span [appTaskCompleted]="task.completed">
    {{ task.title }}
  </span>
</li>
```

---

## 8.4. Componentes reutilizables

Ejemplo de un componente `CardComponent` genérico:

```ts
// src/app/shared/card/card.component.ts
@Component({
  selector: 'app-card',
  template: `
    <div class="card">
      <h3 *ngIf="title">{{ title }}</h3>
      <ng-content></ng-content>
    </div>
  `,
  styleUrls: ['./card.component.css'],
})
export class CardComponent {
  @Input() title?: string;
}
```

Uso:

```html
<app-card title="Lista de tareas">
  <ul>
    <!-- aquí la lista -->
  </ul>
</app-card>
```

Así vas construyendo **bloques de UI reutilizables** para toda tu app.

---

## 8.5. Ejercicio práctico

1. Crear un pipe personalizado para mostrar solo las tareas pendientes.
2. Crear una directiva que resalte una tarea al pasar el ratón (hover).
3. Crear un componente `CardComponent` y usarlo tanto en el listado como en el formulario.

</div>
