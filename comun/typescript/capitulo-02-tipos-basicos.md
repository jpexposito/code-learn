<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (Capítulo 2: Tipos básicos)

En este capítulo veremos los tipos primitivos más importantes de TypeScript y cómo nos ayudan a evitar errores.

---

## `number`, `string`, `boolean`

### Explicación

- `number`: números (enteros y decimales).
- `string`: cadenas de texto.
- `boolean`: valores lógicos `true` o `false`.

### Ejemplo

```ts
// ejemplos/01-tipos-basicos.ts
let edad: number = 30;
let nombre: string = "Lucía";
let esActivo: boolean = true;

console.log(`Usuario: ${nombre}, edad: ${edad}, activo: ${esActivo}`);
```

### Cambios propuestos

1. Intenta asignar una cadena a `edad` y verifica el error de tipos.
2. Crea una variable `apellido: string` y concaténala con `nombre` para mostrar el nombre completo.

---

## `null`, `undefined`, `any`, `unknown`, `never`

### Explicación

- `null` y `undefined`: representan ausencia de valor; con `strictNullChecks` activado, hay que tratarlos explícitamente.
- `any`: desactiva el sistema de tipos (evítalo salvo casos muy concretos).
- `unknown`: tipo “desconocido” pero seguro; obliga a comprobar el tipo antes de usar el valor.
- `never`: tipo de valores que **nunca** ocurren (funciones que lanzan errores o no terminan).

### Ejemplo

```ts
// ejemplos/01-tipos-basicos.ts
let valorNulo: null = null;
let valorIndefinido: undefined = undefined;

let datoFlexible: any = "hola";
datoFlexible = 123; // permitido, pero peligroso

let datoDesconocido: unknown = "podría ser cualquier cosa";

function lanzarError(mensaje: string): never {
  throw new Error(mensaje);
}
```

Uso seguro de `unknown`:

```ts
if (typeof datoDesconocido === "string") {
  console.log(datoDesconocido.toUpperCase());
}
```

### Cambios propuestos

1. Cambia `datoFlexible` de `any` a `unknown` e intenta usarlo sin comprobar su tipo. Arregla los errores añadiendo comprobaciones con `typeof`.
2. Crea una función que use `never` lanzando un error si se le pasa un número negativo.

---

## Type inference (inferencia de tipos)

### Explicación

TypeScript puede **deducir el tipo** de muchas variables a partir de su valor inicial:

```ts
let contador = 0;        // inferido como number
const saludo = "Hola";   // inferido como "Hola" (string literal)
```

No siempre es necesario escribir el tipo explícito, pero sí es útil cuando queremos ser más claros o cuando la inferencia no es suficiente.

### Ejemplo

```ts
// ejemplos/01-tipos-basicos.ts
let contador = 0; // number
contador += 1;
// contador = "uno"; // ❌ Error, string no es asignable a number

const PI = 3.1416; // number (literal)
```

### Cambios propuestos

1. Declara una variable `mensajeInferido = "Hola inferencia"` y luego intenta asignarle un número. Observa el error.
2. Cambia una variable inferida por una con tipo explícito y comprueba que el comportamiento sigue siendo el mismo.

## Relación con Java y Python

- En **Java**, los tipos básicos de TypeScript se parecen a:
  - `number` → combinación de `int`, `long`, `double`… (TypeScript no distingue entre enteros y decimales).
  - `string` → `String`.
  - `boolean` → `boolean`.
  - `number[]` / `Array<number>` → `int[]` o `List<Integer>` (según uses arrays o colecciones).
- En **Python**, son muy parecidos a:
  - `number` → `int` o `float` (TypeScript usa un solo tipo para ambos).
  - `string` → `str`.
  - `boolean` → `bool`.
  - `number[]` → `list[int]` (si usas anotaciones de tipos) o simplemente `list`.

Otros tipos:

- `any` se parece a:
  - **Java**: `Object` o un genérico sin tipo (`List` sin `<T>`), pero aún más permisivo.
  - **Python**: una variable sin anotación de tipo, que puede contener cualquier cosa.
- `unknown` recuerda a los tipos genéricos sin concretar, pero obliga a comprobar el tipo antes de usarlo.
- `tuple` en TypeScript (`[string, number]`) es muy similar a la **tupla de Python** (`tuple[str, int]`).  
  En Java no hay una tupla estándar, se suele representar con clases auxiliares o `record` en versiones modernas.

---

[⬅ Capítulo 1: Introducción](./capitulo-01-introduccion.md) · [Ir al índice](./README.md) · [Capítulo 3: Tipos compuestos ➡](./capitulo-03-tipos-compuestos.md)

</div>