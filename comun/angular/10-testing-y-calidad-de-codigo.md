<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (10. Testing y calidad de código)

## 10.1. Test de servicios

Angular crea por defecto archivos `*.spec.ts` para tests unitarios (Jasmine).

Ejemplo sencillo para un servicio en memoria:

```ts
import { TestBed } from '@angular/core/testing';
import { TaskMemoryService } from './task-memory.service';

describe('TaskMemoryService', () => {
  let service: TaskMemoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TaskMemoryService);
  });

  it('debería añadir una tarea', () => {
    const initial = service.getAll().length;
    service.create({ title: 'Test', description: '', completed: false });
    const final = service.getAll().length;
    expect(final).toBe(initial + 1);
  });
});
```

---

## 10.2. Test de componentes (introducción)

Se puede comprobar que un componente renderiza correctamente según sus inputs.

Ejemplo muy simplificado:

```ts
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TaskListComponent } from './task-list.component';

describe('TaskListComponent', () => {
  let component: TaskListComponent;
  let fixture: ComponentFixture<TaskListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TaskListComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(TaskListComponent);
    component = fixture.componentInstance;
  });

  it('debería mostrar el título', () => {
    component.title = 'Mis tareas';
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h2')?.textContent).toContain('Mis tareas');
  });
});
```

---

## 10.3. Linting y formateo

- **ESLint** para detectar problemas de estilo y errores comunes.
- **Prettier** para formateo automático.

Recomendación:

- Configurar un script en `package.json` para lint:
  ```json
  "scripts": {
    "lint": "ng lint"
  }
  ```

- Usar Prettier + ESLint en el editor para mantener un estilo consistente.

---

## 10.4. Ejercicio práctico

1. Escribir un test para verificar que `TaskApiService` llama a la URL correcta
   (usando `HttpTestingController`).
2. Escribir un test muy sencillo de un componente (comprobar que renderiza un texto
   en función de una propiedad).

</div>