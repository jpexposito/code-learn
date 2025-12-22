<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (9. Estado, comunicación y RxJS (introducción))

<div align="center">
  <img src=images/9-estado-comunicacion-rxjs.png
   width="350">
</div>

## 9.1. Observables básicos

Angular utiliza **RxJS** para trabajar con datos asíncronos mediante `Observable<T>`.

Con `HttpClient` ya lo has visto:

```ts
this.taskApi.getAll().subscribe(tasks => {
  this.tasks = tasks;
});
```

- `getAll()` devuelve un `Observable<Task[]>`.
- `subscribe` recibe los datos cuando llegan.

---

## 9.2. Compartir estado con `BehaviorSubject`

Si varios componentes necesitan la misma información (lista de tareas, usuario
autenticado, etc.), puedes usar un servicio con `BehaviorSubject`:

```ts
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TaskStateService {
  private tasksSubject = new BehaviorSubject<Task[]>([]);
  tasks$ = this.tasksSubject.asObservable();

  constructor(private api: TaskApiService) {}

  load(): void {
    this.api.getAll().subscribe(tasks => this.tasksSubject.next(tasks));
  }

  add(task: Task): void {
    const current = this.tasksSubject.value;
    this.tasksSubject.next([...current, task]);
  }
}
```

Cualquier componente puede suscribirse a `tasks$` y reaccionar a los cambios.

---

## 9.3. Suscripciones y limpieza

En componentes más complejos, evita fugas de memoria limpiando suscripciones.

Patrón típico con `takeUntil`:

```ts
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

export class SomeComponent implements OnDestroy {
  private destroy$ = new Subject<void>();

  ngOnInit() {
    this.taskState.tasks$
      .pipe(takeUntil(this.destroy$))
      .subscribe(tasks => (this.tasks = tasks));
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
```

Para el micro-proyecto simple, mientras el componente viva toda la vida de la
app, esto no es crítico, pero conviene conocer el patrón.

---

## 9.4. Ejercicio práctico

1. Crear un `TaskStateService` que mantenga la lista de tareas en un `BehaviorSubject`.
2. Hacer que `TaskListComponent` se suscriba a `tasks$` en lugar de llamar directamente a `TaskApiService`.
3. Cuando se cree o borre una tarea, actualizar el estado en `TaskStateService`.

</div>