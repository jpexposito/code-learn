<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (7. Formularios y validación con Reactive Forms)

<div align="center">
  <img src=images/7-formularios-validacion.png
   width="350">
</div>

En Angular existen dos enfoques para formularios:
- **Template-driven** (más simple, menos control)
- **Reactive Forms** (recomendado en proyectos reales)

 Trabajaremos principalmente con **Reactive Forms**, porque es el enfoque más usado en empresa y facilita validación y mantenimiento.

---

## 7.1. Activar Reactive Forms en un componente standalone

En proyectos standalone, se importa `ReactiveFormsModule` directamente en el componente:

```ts
import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-task-new',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './task-new.component.html',
})
export class TaskNewComponent {}
```

---

## 7.2. FormGroup y validadores (ejemplo de “Nueva tarea”)

```ts
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-task-new',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './task-new.component.html',
})
export class TaskNewComponent {
  constructor(private fb: FormBuilder) {}

  form = this.fb.group({
    titulo: ['', [Validators.required, Validators.minLength(3)]],
    descripcion: [''],
    completada: [false],
  });

  save() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const data = this.form.getRawValue();
    console.log('Enviar al servicio:', data);
  }
}
```

---

## 7.3. Plantilla HTML con mensajes de error

```html
<section class="card">
  <h2>Nueva tarea</h2>

  <form (ngSubmit)="save()">
    <label>
      Título
      <input class="input" formControlName="titulo" />
    </label>

    @if (form.controls.titulo.touched && form.controls.titulo.invalid) {
      <div class="error">
        El título es obligatorio y debe tener al menos 3 caracteres.
      </div>
    }

    <label>
      Descripción (opcional)
      <input class="input" formControlName="descripcion" />
    </label>

    <label class="check">
      <input type="checkbox" formControlName="completada" />
      Completada
    </label>

    <button class="btn btn-primary" type="submit">Guardar</button>
  </form>
</section>
```

---

## 7.4. Validación opcional (descripción)

Ejemplo: si hay descripción, mínimo 5 caracteres.

```ts
import { AbstractControl, ValidationErrors } from '@angular/forms';

export function minLengthIfNotEmpty(min: number) {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = (control.value ?? '').toString().trim();
    if (value.length === 0) return null;
    return value.length >= min ? null : { minLengthIfNotEmpty: true };
  };
}
```

Y en el formulario:

```ts
descripcion: ['', [minLengthIfNotEmpty(5)]],
```

---

## 7.5. Buenas prácticas

- Validar en el formulario (frontend) **y** en el backend.
- Mostrar mensajes de error claros.
- `markAllAsTouched()` ayuda a que el usuario vea qué falta.
- No mezclar dos enfoques (template + reactive) en el mismo ejercicio.

---

</div>
