<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (7. Formularios y validación)

En Angular tienes dos enfoques principales para formularios:

- **Template-driven** (más sencillo, apoyado en `ngModel`).
- **Reactive forms** (más estructurado, ideal para proyectos grandes).

En este manual usaremos **reactive forms**.

## 7.1. Preparación del módulo

```ts
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    // ...
    ReactiveFormsModule,
  ],
})
export class AppModule {}
```

---

## 7.2. FormGroup, FormControl y FormBuilder

En `TaskFormComponent`:

```ts
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

form!: FormGroup;

constructor(
  private fb: FormBuilder,
  // ...
) {}

ngOnInit(): void {
  this.form = this.fb.group({
    title: ['', Validators.required],
    description: [''],
    completed: [false],
  });
}
```

- `FormGroup`: conjunto de campos (controles).
- `FormControl`: campo individual.
- `FormBuilder`: ayuda para construir formularios más rápido.

---

## 7.3. Validadores

Usamos los validadores integrados de Angular:

- `Validators.required`
- `Validators.email`
- `Validators.minLength(n)`, etc.

Ejemplo:

```ts
this.form = this.fb.group({
  title: ['', [Validators.required, Validators.minLength(3)]],
  description: [''],
  completed: [false],
});
```

En el template:

```html
<input formControlName="title" />
<div *ngIf="form.controls['title'].invalid && form.controls['title'].touched">
  <span *ngIf="form.controls['title'].errors?.['required']">
    El título es obligatorio.
  </span>
  <span *ngIf="form.controls['title'].errors?.['minlength']">
    El título debe tener al menos 3 caracteres.
  </span>
</div>
```

---

## 7.4. Envío del formulario

```html
<form [formGroup]="form" (ngSubmit)="onSubmit()">
  <!-- campos... -->
  <button type="submit" [disabled]="form.invalid || saving">
    Guardar
  </button>
</form>
```

En TS:

```ts
onSubmit(): void {
  if (this.form.invalid) {
    this.form.markAllAsTouched();
    return;
  }

  const value = this.form.value;
  // llamar a create/update en el servicio...
}
```

---

## 7.5. Formulario para crear y editar

Como vimos en el capítulo de HTTP, el mismo formulario puede servir para
crear y editar. La diferencia está en:

- Si hay `id` en la ruta → cargar datos (`patchValue`) y llamar a `update()`.
- Si no hay `id` → dejar form vacío y llamar a `create()`.

Esto te evita duplicar componentes.

---

## 7.6. Ejercicio práctico

1. Añadir validación de mínimo 5 caracteres para la descripción.
2. Mostrar mensajes de error debajo de cada campo.
3. Después de crear/editar, limpiar el formulario o navegar al listado.

</div>