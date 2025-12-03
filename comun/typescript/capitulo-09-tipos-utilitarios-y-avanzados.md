<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (Capítulo 9: Tipos utilitarios y características avanzadas)

---

## `Partial`, `Pick`, `Omit`, `Record`, etc.

### Explicación

TypeScript incluye varios **tipos utilitarios** que transforman otros tipos:

- `Partial<T>`: vuelve todas las propiedades de `T` opcionales.
- `Pick<T, K>`: toma solo algunas propiedades de `T`.
- `Omit<T, K>`: toma todas las propiedades de `T` excepto algunas.
- `Record<K, T>`: crea un tipo de objeto con claves de tipo `K` y valores de tipo `T`.

### Ejemplo

```ts
// ejemplos/08-tipos-utilitarios.ts
interface Usuario {
  id: number;
  nombre: string;
  email: string;
  activo: boolean;
}

type UsuarioParcial = Partial<Usuario>;
type UsuarioBasico = Pick<Usuario, "id" | "nombre">;
type UsuarioSinEmail = Omit<Usuario, "email">;
type MapaUsuarios = Record<string, Usuario>;

const uParcial: UsuarioParcial = { nombre: "Ana" };
const uBasico: UsuarioBasico = { id: 1, nombre: "Ana" };
const uSinEmail: UsuarioSinEmail = { id: 1, nombre: "Ana", activo: true };

const usuariosPorId: MapaUsuarios = {
  "1": { id: 1, nombre: "Ana", email: "ana@example.com", activo: true },
};
```

### Cambios propuestos

1. Crea un tipo `UsuarioSoloEstado` usando `Pick` con la propiedad `activo` y úsalo en una función que active/desactive usuarios.
2. Usa `Record<number, string>` para mapear códigos de estado HTTP a mensajes de texto.

---

## Tipos condicionales básicos

### Explicación

Los **tipos condicionales** tienen la forma `T extends U ? X : Y` y permiten elegir un tipo en función de otro.

### Ejemplo

```ts
// ejemplos/08-tipos-utilitarios.ts
type EsString<T> = T extends string ? "es string" : "no es string";

type Resultado1 = EsString<string>;  // "es string"
type Resultado2 = EsString<number>;  // "no es string"
```

Ejemplo más práctico:

```ts
type IdDe<T> = T extends { id: infer U } ? U : never;

type IdUsuario = IdDe<{ id: number; nombre: string }>; // number
```

### Cambios propuestos

1. Crea un tipo condicional `SoloSiString<T>` que sea `T` si es `string` y `never` en caso contrario.
2. Define un tipo `TipoRespuesta<T>` que sea `Promise<T>` si `T` no es `Promise<any>`, y `T` si ya lo es.

## Relación con Java y Python

Los **tipos utilitarios** de TypeScript (`Partial`, `Pick`, `Omit`, etc.) no tienen un equivalente directo en Java o Python, pero la idea es conocida:

- En **Java**, muchas veces se recurre a:
  - Crear clases nuevas a mano con solo algunas propiedades.
  - Usar patrones como **Builder** para construir objetos parcialmente.
  - Usar librerías de mapeo (MapStruct, ModelMapper) para transformar entre tipos.

- En **Python**, se puede lograr algo parecido con:
  - Diccionarios (`dict`) que solo contienen algunas claves.
  - `dataclasses.replace` para clonar y modificar parcialmente objetos.
  - Herramientas como `pydantic` que permiten generar modelos derivados.

La diferencia es que en TypeScript:

- Estas transformaciones se describen en el **sistema de tipos** (en tiempo de compilación).
- El compilador puede avisarte si intentas acceder a una propiedad que ya no existe en el tipo transformado.

Puedes ver los tipos utilitarios como “**funciones sobre tipos**” que automatizan patrones que en Java y Python suelen resolverse con clases auxiliares, diccionarios o librerías externas.

---

[⬅ Capítulo 8: Módulos y organización del código](./capitulo-08-modulos-y-configuracion.md) · [Ir al índice](./README.md) · [Capítulo 10: Integración con proyectos reales y buenas prácticas ➡](./capitulo-10-integracion-y-buenas-practicas.md)

</div>