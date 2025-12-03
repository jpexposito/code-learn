<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (Capítulo 10: Integración con proyectos reales y buenas prácticas)

---

## Usar TypeScript con Node

### Explicación

TypeScript se integra muy bien con proyectos de **Node.js**.  
Normalmente se compila a JavaScript y luego se ejecuta con Node.

### Ejemplo

```ts
// ejemplos/09-integracion-node.ts
import fs from "fs";

function leerArchivo(ruta: string): string {
  return fs.readFileSync(ruta, "utf-8");
}

const contenido = leerArchivo(__filename);
console.log("Este archivo contiene", contenido.length, "caracteres.");
```

> Recuerda configurar `"moduleResolution": "node"` y `"esModuleInterop": true` si lo necesitas.

### Cambios propuestos

1. Crea una función `escribirLog(mensaje: string)` que escriba en un archivo `log.txt`.
2. Maneja errores de lectura/escritura usando `try/catch`.

---

## Usar TypeScript con React / Angular

### Explicación

- **React** usa TypeScript para tipar props, state, hooks, etc.
- **Angular** está construido directamente sobre TypeScript.

### Ejemplo simple con React

```ts
// ejemplos/10-integracion-react-angular.ts
// Ejemplo conceptual (no incluye toda la configuración de React)
type SaludoProps = {
  nombre: string;
};

function Saludo({ nombre }: SaludoProps) {
  return <h1>Hola, {nombre}</h1>;
}
```

Ejemplo conceptual con Angular (TypeScript para clases y decoradores):

```ts
// ejemplos/10-integracion-react-angular.ts
// Código simplificado solo para ilustrar el tipado
class UsuarioService {
  obtenerUsuario(id: number): Promise<{ id: number; nombre: string }> {
    return Promise.resolve({ id, nombre: "Usuario de ejemplo" });
  }
}
```

### Cambios propuestos

1. En el ejemplo de React, añade una prop opcional `edad?: number` y muéstrala si está definida.
2. En el ejemplo de Angular, cambia el tipo de retorno a un array de usuarios.

---

## Buenas prácticas y convenciones

### Explicación

Algunas buenas prácticas generales:

- Activar `strict` en `tsconfig.json`.
- Evitar `any` siempre que sea posible.
- Usar `interface` para describir contratos públicos y `type` para tipos más complejos.
- Nombrar tipos y interfaces con mayúscula inicial (`Usuario`, `ApiResponse`).
- Preferir la inferencia de tipos cuando es clara, y usar anotaciones cuando aporta claridad.

### Ejemplo

```ts
// ejemplos/10-integracion-react-angular.ts
interface Usuario {
  id: number;
  nombre: string;
}

function crearUsuario(id: number, nombre: string): Usuario {
  return { id, nombre };
}

const nuevoUsuario = crearUsuario(1, "Ana");
console.log(nuevoUsuario);
```

### Cambios propuestos

1. Modifica `crearUsuario` para aceptar un objeto con los datos en lugar de parámetros sueltos (`{ id, nombre }`).
2. Añade una propiedad opcional `email` a `Usuario` y maneja el caso en el que no se proporcione.

---

## Buenas prácticas con TypeScript

En esta sección se recopilan algunas buenas prácticas generales al trabajar con TypeScript en proyectos reales.

### Nombres de variables y constantes

- Usa **camelCase** para variables y constantes: `numeroUsuarios`, `isLoggedIn`.
- Evita abreviaturas crípticas: mejor `totalPedidos` que `tp`.
- Usa nombres que expresen intención: `tiempoDeRespuestaMs` comunica más que `t`.

Sobre `let`, `const` y `var`:

- **`const`**: úsalo por defecto siempre que el valor **no vaya a reasignarse**.
- **`let`**: úsalo cuando sepas que el valor va a cambiar (por ejemplo, en un bucle).
- **Evita `var`**: tiene ámbito de función y *hoisting*, lo que puede provocar errores difíciles de detectar.

Ejemplo:

```ts
// 👍 Recomendado
const maxUsuarios = 100;
let usuariosConectados = 0;

// 👎 Evitar
var contador = 0;
```

### Nombres de funciones

- Usa verbos en **camelCase** que describan la acción: `calcularTotal`, `enviarEmail`, `obtenerUsuarioPorId`.
- Intenta que una función haga **una sola cosa** (principio de responsabilidad única).
- Anota siempre el tipo de retorno cuando la función forma parte de una API pública:

```ts
function calcularTotal(precios: number[]): number {
  return precios.reduce((acc, precio) => acc + precio, 0);
}
```

### `type`, `interface` y `class` (semejanzas y diferencias)

En TypeScript tenemos varias formas de describir la forma de los datos y su comportamiento:

#### `type` (alias de tipo)

- Sirve para crear **alias** de tipos existentes.
- Puede representar tipos primitivos, uniones, intersecciones, funciones, tuplas, etc.
- No existe en tiempo de ejecución: solo el compilador lo usa.

```ts
type ID = number | string;
type Punto = { x: number; y: number };
type Suma = (a: number, b: number) => number;
```

#### `interface` (forma de un objeto o clase)

- Describe la **forma** de un objeto o de una clase.
- Se puede **extender** (`extends`) y se puede **reabrir** (declarar varias veces para añadir propiedades).
- Es muy útil para describir contratos públicos en librerías o módulos.

```ts
interface Usuario {
  id: ID;
  nombre: string;
  email?: string;
}
```

#### `class` (implementación + tipo)

- Existe en tiempo de ejecución: genera código JavaScript real.
- Define tanto la **estructura** (propiedades) como el **comportamiento** (métodos).
- Puede implementar interfaces (`implements`) y usarse como tipo.

```ts
class UsuarioActivo implements Usuario {
  constructor(
    public id: ID,
    public nombre: string,
    public email?: string
  ) {}

  saludar(): void {
    console.log(`Hola, soy ${this.nombre}`);
  }
}
```

**Semejanzas entre `type` y `class`:**

- Ambos se pueden usar como **tipos** al anotar variables o parámetros:
  - `let u: UsuarioActivo;`
  - `let id: ID;`

**Diferencias importantes:**

- `type`:
  - No genera código en tiempo de ejecución.
  - Es solo una etiqueta para el sistema de tipos.
  - Ideal para uniones, intersecciones y combinaciones complejas.

- `class`:
  - Sí genera código JavaScript.
  - Se instancia con `new` y tiene métodos.
  - Ideal cuando necesitas **estado y comportamiento** juntos.

En general:

- Usa `type` o `interface` para **describir datos**.
- Usa `class` cuando necesites **lógica de negocio + estado** (por ejemplo, en modelos de dominio o servicios con comportamiento).

---

## Angular y React: semejanzas, diferencias y relación con TypeScript

Tanto **Angular** como **React** se usan habitualmente junto con TypeScript para construir aplicaciones web modernas.

### Semejanzas entre Angular y React

- Ambos se utilizan para construir aplicaciones **SPA** (Single Page Applications).
- Se basan en la idea de **componentes**:
  - En Angular: componentes definidos con decoradores (`@Component`) y templates HTML.
  - En React: componentes como funciones o clases que retornan JSX/TSX.
- Se integran muy bien con TypeScript:
  - Angular está diseñado desde el inicio para usar TypeScript.
  - React puede usarse con JavaScript o con TypeScript; hoy en día es muy común usarlo con TS.
- Tienen ecosistemas grandes, CLI y tooling:
  - Angular CLI (`ng`) para generar proyectos, componentes, servicios, etc.
  - Create React App (en proyectos antiguos) o herramientas como Vite/Next.js con plantillas en TypeScript.

### Diferencias principales

**Angular:**

- Es un **framework completo**:
  - Incluye en el propio framework: enrutador (router), manejo de formularios, inyección de dependencias, comunicación HTTP, etc.
  - Usa **decoradores** (por ejemplo, `@Component`, `@Injectable`) para definir componentes y servicios.
- La sintaxis de plantillas es HTML con directivas (`*ngFor`, `*ngIf`, etc.).
- Obliga a utilizar TypeScript como lenguaje principal: la documentación oficial y los ejemplos están todos en TS.

**React:**

- Es una **biblioteca de UI**:
  - Se enfoca en la capa de vista (componentes), y se combina con otras librerías para routing, estado global, etc.
- Usa **JSX/TSX**: una sintaxis similar a HTML dentro de JavaScript/TypeScript.
- TypeScript es **opcional** pero muy habitual en proyectos modernos:
  - Se define el tipo de las `props`, `state`, hooks, etc.

Ejemplo muy simplificado de componente en React con TypeScript:

```tsx
type BotonProps = {
  texto: string;
  onClick: () => void;
};

function Boton({ texto, onClick }: BotonProps) {
  return <button onClick={onClick}>{texto}</button>;
}
```

### Relación con TypeScript

- En **Angular**, TypeScript es parte fundamental del framework:
  - Los decoradores, la inyección de dependencias y muchas APIs están diseñadas pensando en TS.
  - El CLI genera directamente ficheros `.ts`.

- En **React**, TypeScript añade seguridad y autocompletado:
  - Las `props` de los componentes, el estado y los hooks se tipan para evitar errores.
  - Se usan tipos y genéricos para describir componentes reutilizables.

En los proyectos reales, es muy común que el manual de TypeScript sirva como base para entender el código de componentes y servicios tanto en Angular como en React.

---

## Documentación oficial recomendada

Para seguir aprendiendo TypeScript y su uso con frameworks:

- **TypeScript**: https://www.typescriptlang.org/docs/
- **Angular + TypeScript**: https://angular.io/docs
- **React + TypeScript** (guía de la comunidad): https://react-typescript-cheatsheet.netlify.app/

Estas referencias complementan el manual y muestran ejemplos más avanzados y específicos de cada ecosistema.

---

[⬅ Capítulo 9: Tipos utilitarios y características avanzadas](./capitulo-09-tipos-utilitarios-y-avanzados.md) · [Ir al índice](./README.md) · [Capítulo 11: Tests unitarios en TypeScript ➡](./capitulo-11-test-unitarios.md)

</div>