<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (Capítulo 3: Tipos compuestos)

---

## Arrays y tuplas

### Explicación

- Un **array** almacena una lista de elementos del mismo tipo: `number[]`, `string[]`, etc.
- Una **tupla** define una lista con un número fijo de elementos y tipos específicos en cada posición.

### Ejemplo

```ts
// ejemplos/02-tipos-compuestos.ts
let numeros: number[] = [1, 2, 3];
let nombres: Array<string> = ["Ana", "Luis", "Marta"];

let tuplaUsuario: [string, number] = ["Ana", 30];

console.log(numeros[0]);       // 1
console.log(tuplaUsuario[0]);  // "Ana"
```

### Cambios propuestos

1. Añade un nuevo elemento al array `numeros` usando `push` y muestra el resultado.
2. Intenta cambiar el orden de la tupla (`[30, "Ana"]`) y observa el error de tipos.

---

## Objetos

### Explicación

Los objetos se describen con **tipos de objeto**, definiendo sus propiedades y tipos.

### Ejemplo

```ts
// ejemplos/02-tipos-compuestos.ts
let usuario: {
  nombre: string;
  edad: number;
  activo: boolean;
} = {
  nombre: "Carlos",
  edad: 28,
  activo: true,
};

console.log(usuario.nombre);
```

### Cambios propuestos

1. Añade una nueva propiedad `email: string` al tipo del objeto y actualiza la declaración de `usuario`.
2. Intenta asignar un objeto que no tenga todas las propiedades requeridas y observa el error.

---

## Uniones (`|`) e intersecciones (`&`)

### Explicación

- Una **unión** `A | B` permite valores que pueden ser de **uno u otro tipo**.
- Una **intersección** `A & B` combina tipos, es decir, el valor debe cumplir **todos los tipos** a la vez.

### Ejemplo

```ts
// ejemplos/02-tipos-compuestos.ts
type Id = number | string;

let id1: Id = 123;
let id2: Id = "abc-123";

type ConFecha = { creadoEn: Date };
type ConId = { id: number };

type EntidadConIdYFecha = ConFecha & ConId;

const registro: EntidadConIdYFecha = {
  id: 1,
  creadoEn: new Date(),
};
```

### Cambios propuestos

1. Crea una función `imprimirId(id: Id)` que acepte tanto `number` como `string` y trate cada caso de forma distinta.
2. Añade una nueva propiedad a `ConId` (por ejemplo `activo: boolean`) y actualiza la definición de `registro`.

---

## Type aliases (`type`)

### Explicación

Un **alias de tipo** permite dar nombre a un tipo complejo para reutilizarlo y hacer el código más legible.

### Ejemplo

```ts
// ejemplos/02-tipos-compuestos.ts
type UsuarioBasico = {
  id: number;
  nombre: string;
  email: string;
};

let u1: UsuarioBasico = {
  id: 1,
  nombre: "Ana",
  email: "ana@example.com",
};
```

### Cambios propuestos

1. Añade una propiedad opcional `telefono?: string` a `UsuarioBasico` y crea otro usuario que la utilice.
2. Crea un nuevo alias `Admin` que extienda `UsuarioBasico` con una propiedad `permisos: string[]`.

## Relación con Java y Python

En este capítulo aparecen conceptos que no existen de forma directa en Java, pero que se pueden comparar:

- **Uniones de tipos** (`A | B`):
  - En **Python**, se parecen a `typing.Union[A, B]` o a la notación moderna `A | B` en las anotaciones.
  - En **Java**, no hay uniones de tipos como tal. Se suele resolver con jerarquías de clases o interfaces padre (por ejemplo, una interfaz `Figura` con implementaciones `Circulo`, `Cuadrado`, etc.).

- **Intersecciones de tipos** (`A & B`):
  - En **Java**, recuerda a una clase que implementa varias interfaces: `class C implements A, B { ... }`.
  - En **Python**, sería parecido a un objeto que cumple varios protocolos o a usar `Protocol` en `typing` para describir que tiene ciertos métodos.

- **Tipos literales** (`"admin" | "user"`):
  - En **Java**, algo parecido se consigue con `enum` o con constantes estáticas.
  - En **Python**, se parecen a `Literal["admin", "user"]` en `typing`.

Puedes pensar en los tipos compuestos de TypeScript como una forma de expresar, en el sistema de tipos, cosas que en Java y Python solemos representar con jerarquías de clases, enums o validaciones manuales en tiempo de ejecución.

---

[⬅ Capítulo 2: Tipos básicos](./capitulo-02-tipos-basicos.md) · [Ir al índice](./README.md) · [Capítulo 4: Funciones ➡](./capitulo-04-funciones.md)

</div>
