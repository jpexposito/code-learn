<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (0. Prerrequisitos y entorno)

## 0.1. HTML y CSS básicos

Antes de meterte de lleno en Angular, es importante que controles lo siguiente:

- **HTML**
  - Estructura básica: `<!DOCTYPE html>`, `<html>`, `<head>`, `<body>`.
  - Etiquetas comunes: `div`, `span`, `h1`–`h6`, `p`, `a`, `img`, `ul`/`ol`/`li`,
    `form`, `input`, `button`, `textarea`.
  - Atributos: `id`, `class`, `href`, `src`, `type`, `placeholder`.

- **CSS**
  - Selectores básicos: por etiqueta (`p`), clase (`.clase`), id (`#id`).
  - Propiedades habituales: `color`, `background-color`, `margin`, `padding`,
    `display`, `flex`, `justify-content`, `align-items`.
  - Concepto de **archivo CSS separado** y uso de `<link>`.

Angular no reemplaza HTML/CSS: los usa. Los templates de Angular son HTML
con superpoderes (interpolaciones, directivas, bindings…).

---

## 0.2. Repaso de TypeScript

TypeScript es JavaScript con tipos. Angular está pensado para usarse con TS.

Puntos clave que debes tener claros:

```ts
// Tipos básicos
const nombre: string = 'Ana';
const edad: number = 30;
const activo: boolean = true;

// Arrays y objetos
const numeros: number[] = [1, 2, 3];

interface Usuario {
  id: number;
  nombre: string;
}

const usuario: Usuario = { id: 1, nombre: 'Ana' };
```

Clases y métodos:

```ts
class Persona {
  constructor(public nombre: string, private edad: number) {}

  saludar(): string {
    return `Hola, soy ${this.nombre}`;
  }
}
```

Funciones tipadas:

```ts
function sumar(a: number, b: number): number {
  return a + b;
}
```

Imports/exports:

```ts
// user.model.ts
export interface User {
  id: number;
  name: string;
}

// otro archivo
import { User } from './user.model';
```

---

## 0.3. Herramientas necesarias

- **Node.js LTS**: necesario para usar Angular CLI y ejecutar el dev server.
- **Angular CLI** (instalación global):

  ```bash
  npm install -g @angular/cli
  ```
Obtendrás algo similar a lo siguiente:

```code
npm install -g @angular/cli

added 311 packages in 11s

69 packages are looking for funding
  run `npm fund` for details

```

Extensiones útiles para VS Code:

- Angular Language Service
- ESLint
- Prettier

---

## 0.4. Creación de un proyecto de prueba

Prueba rápida para verificar que todo funciona:

```bash
ng new prueba-angular
```

Aceptamos todo/enter y obtenemos algo similar a:

```code
...
CREATE prueba-angular/src/app/app.routes.ts (77 bytes)
CREATE prueba-angular/src/app/app.config.server.ts (426 bytes)
CREATE prueba-angular/src/app/app.routes.server.ts (166 bytes)
CREATE prueba-angular/public/favicon.ico (15086 bytes)
✔ Packages installed successfully.
    Successfully initialized git.
```

```bash
cd prueba-angular
ng serve
```

Y pulsamos yes/enter

Abre `http://localhost:4200` en el navegador. Si ves la página de Angular,
estás listo para el resto del manual.

</div>
