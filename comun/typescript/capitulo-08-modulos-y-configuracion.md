<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (Capítulo 8: Módulos y organización del código)

---

## `import` / `export`

### Explicación

TypeScript usa la sintaxis de módulos de ES para organizar el código en archivos separados:

- `export`: expone funciones, clases, constantes, etc.
- `import`: permite usar lo exportado desde otro archivo.

### Ejemplo

```ts
// ejemplos/07-modulos.ts

// archivo: ejemplos/utilidades.ts
export function saludar(nombre: string): void {
  console.log(`Hola, ${nombre}`);
}

export const PI = 3.1416;

// archivo: ejemplos/07-modulos.ts
import { saludar, PI } from "./utilidades";

saludar("Ana");
console.log("PI vale", PI);
```

### Cambios propuestos

1. Añade una nueva función `despedir(nombre: string)` en `utilidades.ts` y expórtala; luego imprta y úsala en `07-modulos.ts`.
2. Exporta `saludar` como `default` y ajusta el `import` correspondiente.

---

## Configuración básica de `tsconfig.json`

### Explicación

`tsconfig.json` define cómo se comporta el compilador de TypeScript.

Propiedades básicas:

- `target`: versión de JavaScript generada.
- `module`: sistema de módulos (por ejemplo, `commonjs`, `esnext`).
- `strict`: activa todas las comprobaciones estrictas.
- `outDir`: carpeta donde se colocará el JavaScript compilado.

### Ejemplo

```json
{
  "compilerOptions": {
    "target": "ES2019",
    "module": "commonjs",
    "strict": true,
    "outDir": "dist",
    "esModuleInterop": true
  },
  "include": ["ejemplos/**/*"]
}
```

### Cambios propuestos

1. Cambia el `module` a `"esnext"` y observa cómo cambia la salida JavaScript.
2. Añade `"noImplicitAny": true` y corrige los posibles errores en tus archivos `.ts`.

## Relación con Java y Python

- Los **módulos** de TypeScript (`import` / `export`) se parecen a:
  - Los **módulos** de **Python**: `import modulo`, `from modulo import algo`.
  - Los **paquetes** de **Java**: `package com.ejemplo; import com.ejemplo.otro.*;`.

Ejemplos equivalentes:

- TypeScript:

  ```ts
  import { sumar } from "./utilidades";
  ```

- Python:

  ```python
  from utilidades import sumar
  ```

- Java:

  ```java
  import com.ejemplo.utilidades.Matematicas; // suponiendo una clase estática con sumar()
  ```

En TypeScript y JavaScript, los módulos se resuelven en tiempo de compilación/ejecución según la configuración (`module`, `moduleResolution`), mientras que en Python se resuelven en tiempo de ejecución según el `PYTHONPATH` y la estructura de carpetas.

---

[⬅ Capítulo 7: Genéricos (Generics)](./capitulo-07-genericos.md) · [Ir al índice](./README.md) · [Capítulo 9: Tipos utilitarios y características avanzadas ➡](./capitulo-09-tipos-utilitarios-y-avanzados.md)

</div>