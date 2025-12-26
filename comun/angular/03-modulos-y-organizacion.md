<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (3. Organización del proyecto en Angular moderno)

<div align="center">
  <img src=images/3-modulo-organizacion.png
   width="350">
</div>


> **Angular CLI 21** crea proyectos **standalone** por defecto. En este enfoque, no necesitas `AppModule` ni `@NgModule` para empezar.
>
> Nos interesa sobre todo: **organizar bien carpetas**, entender **componentes/servicios/rutas** y trabajar con un código mantenible.

---

## 3.1. Standalone vs NgModules (qué debes saber)

### Standalone (recomendado hoy)
- Los componentes pueden declararse como `standalone: true`.
- La aplicación se arranca con `bootstrapApplication(...)`.
- Los *providers* globales van en `app.config.ts` (router, http, etc.).

**Ventajas**
- Menos “boilerplate”
- Más fácil de entender al principio
- Es el estándar en Angular moderno

### NgModules (legado / proyectos antiguos)
- Se organizaba todo en `@NgModule` (por ejemplo, `AppModule`).
- Sigue existiendo, pero **no es lo habitual en proyectos nuevos**.

>  En este manual trabajaremos en **standalone**.  
> 🧾 Mencionamos NgModules solo para entender código antiguo.

---

## 3.2. Estructura recomendada para proyectos pequeños/medios

Una organización simple y profesional:

```text
src/app/
├─ pages/            # Páginas (vistas asociadas a rutas)
├─ components/       # Componentes reutilizables (UI)
├─ shared/           # Navbar, footer, elementos comunes
├─ services/         # Acceso a datos (API, almacenamiento, auth)
├─ models/           # Interfaces y tipos (Task, User, etc.)
├─ guards/           # Protecciones de rutas (si hay login)
├─ interceptors/     # Interceptores HTTP (JWT)
├─ app.routes.ts     # Definición de rutas
└─ app.config.ts     # Providers globales (router, http, etc.)
```

**Idea clave:**  
- *pages* = pantallas  
- *components* = piezas reutilizables  
- *services* = lógica y datos (sin HTML)

---

## 3.3. Ejemplo: componente standalone con imports

Un componente standalone declara qué necesita en `imports`:

```ts
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  template: `
    <h2>Home</h2>
    <a routerLink="/tareas">Ir a tareas</a>
  `,
})
export class HomeComponent {}
```

---

## 3.4. `app.config.ts`: dónde se configura router y http

En Angular moderno, configuramos la app en `app.config.ts`:

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

## 3.5. Buenas prácticas rápidas

- Un componente debe tener **una responsabilidad**.
- Evitar lógica de negocio en el HTML.
- Los componentes **llaman a servicios**, no al revés.
- Los modelos (`interface`) van en `models/`.
- Las rutas siempre en `app.routes.ts`.

---

</div>
