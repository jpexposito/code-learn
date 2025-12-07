<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (Práctica 1: Proyecto Tareas)

Este ejercicio guiado está pensado como **tarea global** de la documentación de TypeScript que has ido trabajando.

La idea es que, al terminar, seas capaz de:

- Crear y configurar un proyecto en TypeScript.
- Definir **tipos e interfaces**.
- Escribir **funciones puras** bien tipadas.
- Organizar el código en **módulos** (`import` / `export`).
- Ejecutar el código desde la terminal con `ts-node` o `tsc + node`.
- Añadir **tests** a tus funciones.
- Documentar el código con comentarios de tipo JSDoc/TSDoc.

> Para ayudarte en cada paso se indica **de qué parte de la documentación sale la información necesaria** para resolverlo.

---

## 0. Preparación del proyecto

**Objetivo:** tener un proyecto TypeScript mínimo listo para ejecutar código desde la terminal.

**De dónde viene en en la documentación:**

- Apartado *“Instalación y primeros pasos (tsc, ts-node, etc.)”*.
- Ejemplo de `tsconfig.json` y uso de `npx tsc`, `npx ts-node`.

### Pasos

1. Crea una carpeta para el proyecto, por ejemplo:

   ```bash
   mkdir mini-gestor-tareas
   cd mini-gestor-tareas
   ```

2. Inicializa el proyecto con npm:

   ```bash
   npm init -y
   ```
> Verifica que te ha creado el `package.json` dentro del directorio.

3. Instala TypeScript y ts-node como dependencias de desarrollo:

   ```bash
   npm install --save-dev typescript ts-node
   ```
> Debes de obtener un mensaje similar a este:
>
```code
added 20 packages, and audited 21 packages in 2s

found 0 vulnerabilities
```

> Lista con un ls el directorio y debes de tener la carpeta `node_modules`, ...

4. Crea un archivo de configuración de TypeScript:

   ```bash
   npx tsc --init
   ```
> Debes de obtener un mensaje como:

```code
Created a new tsconfig.json     
``

5. Edita `tsconfig.json` para asegurarte de que tienes algo parecido a:

   ```json
   {
     "compilerOptions": {
       "target": "ES2019",
       "module": "CommonJS",
       "strict": true,
       "esModuleInterop": true,
       "outDir": "dist"
     },
     "include": ["src/**/*"]
   }
   ```

6. Crea la carpeta `src/` y un archivo de prueba `src/ejemplo-basico.ts`:

   ```ts
   console.log("Proyecto TypeScript listo.");
   ```

7. Ejecuta el archivo con:

   ```bash
   npx ts-node src/ejemplo-basico.ts
   ```

Si ves el mensaje en consola, tienes el entorno preparado.

---

## 1. Modelo de datos: interfaz `Tarea`

**Objetivo:** practicar tipos básicos, interfaces y tipos propios.

**De dónde viene en en la documentación:**

- Capítulos de *“Tipos básicos”* e *“Interfaces y tipos (`interface`, `type`)”*.
- Ejemplos como `Usuario`, `CrearUsuarioDto`, etc.

### Pasos

1. Crea `src/models.ts` con la interfaz `Tarea`:

   ```ts
   export interface Tarea {
     id: number;
     titulo: string;
     descripcion?: string;
     completada: boolean;
   }

   export type IdTarea = number;

   export type FiltroTarea = "todas" | "pendientes" | "completadas";
   ```

2. Relaciona esto con lo que has visto en en la documentación sobre:

   - Tipos primitivos (`number`, `string`, `boolean`).
   - Propiedades opcionales (`descripcion?`).
   - Tipos unión para representar conjuntos de valores (`"todas" | "pendientes" | "completadas"`).

> Documenta en el README.md (markdown) de la tarea.

---

## 2. Funciones puras para gestionar tareas

**Objetivo:** escribir funciones bien tipadas que trabajen con arrays de `Tarea`.

**De dónde viene en en la documentación:**

- Capítulo de *“Funciones en TypeScript”* (parámetros, tipo de retorno).
- Ejemplos de manejo de arrays (`map`, `filter`).
- Patrones de documentación (comentarios JSDoc sobre funciones como `sumar`, `saludar`, `formatear`).

### Pasos

1. Crea `src/tareas.ts`:

   ```ts
   import { Tarea, IdTarea, FiltroTarea } from "./models";

   /**
    * Crea una nueva tarea a partir de un titulo.
    *
    * @param id Identificador unico de la tarea.
    * @param titulo Titulo visible de la tarea.
    * @param descripcion (Opcional) Detalle adicional.
    * @returns Tarea inicializada como no completada.
    */
   export function crearTarea(
     id: IdTarea,
     titulo: string,
     descripcion?: string
   ): Tarea {
     return {
       id,
       titulo,
       descripcion,
       completada: false,
     };
   }

   /**
    * Marca una tarea como completada.
    *
    * @param tareas Lista original de tareas.
    * @param id Identificador de la tarea a completar.
    * @returns Nueva lista de tareas con la tarea marcada.
    */
   export function completarTarea(tareas: Tarea[], id: IdTarea): Tarea[] {
     return tareas.map((t) =>
       t.id === id ? { ...t, completada: true } : t
     );
   }

   /**
    * Filtra las tareas segun el filtro indicado.
    *
    * @param tareas Lista original de tareas.
    * @param filtro "todas", "pendientes" o "completadas".
    * @returns Lista filtrada de tareas.
    */
   export function filtrarTareas(
     tareas: Tarea[],
     filtro: FiltroTarea
   ): Tarea[] {
     if (filtro === "pendientes") {
       return tareas.filter((t) => !t.completada);
     } else if (filtro === "completadas") {
       return tareas.filter((t) => t.completada);
     }
     return tareas;
   }
   ```
> **Problemas en los imports**: Si te encuentras problemas en los imports deberías de revisar `tsconfig.json`. El motivo es el siguiente: `TypeScript intenta comportarse como Node ESM, y ahí hay una regla importante:En ESM de Node, en los import relativos hay que poner la extensión del archivo (.js, .mjs, etc.)`. Por ese motivo debes de modificar el fichero a:

```ts
{
  "compilerOptions": {
    "target": "es2019",
    "module": "commonjs",
    "moduleResolution": "node",
    "strict": true,
    "jsx": "react-jsx",
    "skipLibCheck": true
  }
}
```

2. Observa cómo se aplican aquí:

   - Tipado de parámetros y retorno (`Tarea[]`, `IdTarea`, etc.).
   - Comentarios de documentación siguiendo el mismo estilo de la documentación.
   - Uso de funciones de array (`map`, `filter`) como has visto en los ejemplos.

> Documenta en el README.md (markdown) como funciona a través de ejemplos.

---

## 3. Módulos: organizar el código con `import` / `export`

**Objetivo:** separar el código en archivos y consumirlo desde un punto de entrada.

**De dónde viene en en la documentación:**

- Apartado de *“Módulos, import/export”*.
- Ejemplos donde se separan modelos, servicios y funciones en archivos diferentes.

### Pasos

1. Crea un archivo `src/index.ts` que use lo que has exportado:

   ```ts
   import {
     crearTarea,
     completarTarea,
     filtrarTareas,
   } from "./tareas";
   import { Tarea } from "./models";

   let tareas: Tarea[] = [];

   tareas.push(crearTarea(1, "Estudiar TypeScript"));
   tareas.push(crearTarea(2, "Hacer la tarea global"));
   tareas.push(crearTarea(3, "Descansar un rato"));

   tareas = completarTarea(tareas, 1);

   console.log("Todas:", tareas);
   console.log("Pendientes:", filtrarTareas(tareas, "pendientes"));
   console.log("Completadas:", filtrarTareas(tareas, "completadas"));
   ```

2. Ejecuta el archivo:

   ```bash
   npx ts-node src/index.ts
   ```

Debes de obtener una salida similar a la siguiente:

```code
Todas: [
  {
    id: 1,
    titulo: 'Estudiar TypeScript',
    descripcion: undefined,
    completada: true
  },
  {
    id: 2,
    titulo: 'Hacer la tarea global',
    descripcion: undefined,
    completada: false
  },
  {
    id: 3,
    titulo: 'Descansar un rato',
    descripcion: undefined,
    completada: false
  }
]
Pendientes: [
  {
    id: 2,
    titulo: 'Hacer la tarea global',
    descripcion: undefined,
    completada: false
  },
  {
    id: 3,
    titulo: 'Descansar un rato',
    descripcion: undefined,
    completada: false
  }
]
Completadas: [
  {
    id: 1,
    titulo: 'Estudiar TypeScript',
    descripcion: undefined,
    completada: true
  }
]
```

3. Relaciona esto con los ejemplos de la documentación donde:

   - Tenías archivos como `usuario.ts`, `servicios/usuario.service.ts`, etc.
   - Importabas interfaces y funciones en otros archivos.

> Documenta en el README.md (markdown) como funciona a través de ejemplos.

---

## 4. Compilar el proyecto con `tsc` y ejecutar con `node`

**Objetivo:** practicar el flujo de compilación completo `.ts` → `.js`.

**De dónde viene en en la documentación:**

- Apartado *“Cómo compilar y ejecutar TypeScript”*.
- Diferencias entre `tsc` y `ts-node`.

### Pasos

1. Compila todo el proyecto:

   ```bash
   npx tsc
   ```

   Esto generará los `.js` dentro de `dist/`.

> Si no fuera así debes de completar el fichero:

```code
    "rootDir": "./src",
    "outDir": "./dist",
```

Ya que por defecto o el cambio anterior no se ha modificado.

2. Ejecuta la versión compilada:

   ```bash
   node dist/index.js
   ```

3. Relaciona lo que estás haciendo con lo que se explica en en la documentación:

   - `tsc` como compilador puro (TS → JS).
   - `node` como runtime que ejecuta el JavaScript generado.
   - Ventajas de tener una carpeta `dist/` para distribución/despliegue.

> Documenta en el README.md (markdown).

---

## 5. Sencilla “CLI” por parámetros

**Objetivo:** añadir un pequeño “modo consola” leyendo parámetros de la línea de comandos.

**De dónde viene en en la documentación:**

- Ejemplos donde ejecutas scripts desde Node.
- Apartado donde se explica la diferencia entre ejecutar con `ts-node` y con `node dist/archivo.js`.

### Pasos

1. Crea `src/cli.ts`:

   ```ts
   import { crearTarea, filtrarTareas } from "./tareas";
   import { Tarea, FiltroTarea } from "./models";

   let tareas: Tarea[] = [
     crearTarea(1, "Estudiar TS"),
     crearTarea(2, "Hacer ejercicio"),
     crearTarea(3, "Leer un libro"),
   ];

   const [, , filtroArg] = process.argv;
   const filtro: FiltroTarea = (filtroArg as FiltroTarea) ?? "todas";

   console.log("Filtro:", filtro);
   console.log(filtrarTareas(tareas, filtro));
   ```

2. Compila y ejecuta:

   ```bash
   npx tsc
   node dist/cli.js
   node dist/cli.js pendientes
   node dist/cli.js completadas
   ```

3. Piensa cómo se relaciona esto con los ejemplos de la documentación sobre:

   - Ejecución desde terminal.
   - Uso de `node archivo.js` tras compilar.

> Documenta en el README.md (markdown) como funciona a través de ejemplos.   

---

## 6. Tests de las funciones de tareas

**Objetivo:** comprobar con tests que las funciones se comportan como esperas.

**De dónde viene en en la documentación:**

- Capítulo de *“Testing en TypeScript”*.
- Ejemplos de tests de funciones con `describe`, `it`/`test`, `expect`.

> **Nota:** aquí debes adaptar los comandos y la configuración al framework de test que se use en esta documentación (Jest, Vitest, etc.). El ejemplo de abajo supone `Jest`.

### Pasos (ejemplo con Jest)

1. Instalar Jest y ts-jest (si en tu manual se trabaja así):

   ```bash
   npm install --save-dev jest ts-jest @types/jest
   npx ts-jest config:init
   ```

> Debe de crear el fichero `jest.config.js`.

2. Crea un archivo de test (por ejemplo, `tests/tareas.test.ts`):

   ```ts
   import {
     crearTarea,
     completarTarea,
     filtrarTareas,
   } from "../src/tareas";

   describe("Funciones de tareas", () => {
     test("crearTarea crea una tarea no completada", () => {
       const tarea = crearTarea(1, "Probar función");
       expect(tarea.completada).toBe(false);
       expect(tarea.titulo).toBe("Probar función");
     });

     test("completarTarea marca la tarea como completada", () => {
       const tareas = [
         crearTarea(1, "A"),
         crearTarea(2, "B"),
       ];
       const resultado = completarTarea(tareas, 2);
       const tarea2 = resultado.find((t) => t.id === 2)!;

       expect(tarea2.completada).toBe(true);
     });

     test("filtrarTareas filtra por completadas", () => {
       const tareas = [
         { id: 1, titulo: "A", completada: false },
         { id: 2, titulo: "B", completada: true },
       ];

       const completadas = filtrarTareas(tareas, "completadas");
       expect(completadas).toHaveLength(1);
       expect(completadas[0].id).toBe(2);
     });
   });
   ```

3. Ejecuta los tests con el comando que indique en la documentación, por ejemplo:

   ```bash
   npx jest
   ```

Debes de obtener algo similar a lo siguiente:

```code
 PASS  test/tareas.test.ts
  Funciones de tareas
    ✓ crearTarea crea una tarea no completada (1 ms)
    ✓ completarTarea marca la tarea como completada
    ✓ filtrarTareas filtra por completadas (1 ms)

Test Suites: 1 passed, 1 total
Tests:       3 passed, 3 total
Snapshots:   0 total
Time:        0.44 s
```

> Documenta en el README.md (markdown) como funciona a través de ejemplos y crea otros test.
---

## 7. Documentación y buenas prácticas

**Objetivo:** aplicar el estilo de documentación y buenas prácticas visto en en la documentación.

**De dónde viene en en la documentación:**

- Apartado de *“Patrones de diseño de código: definición de variables, funciones, comentarios, etc.”*.
- Ejemplos de documentación de funciones:
  - `sumar(a: number, b: number): number`
  - `saludar(nombre: string, saludo?: string): void`
  - `formatear(valor: number | Date): string` con overloading.

### Pasos

1. Asegúrate de que:

   - Las funciones principales (`crearTarea`, `completarTarea`, `filtrarTareas`) tienen comentarios JSDoc.
   - Los nombres de variables siguen el patrón visto: `camelCase` para variables y funciones, `PascalCase` para interfaces y tipos.
   - El código es legible y formateado.

2. Añade, si quieres, documentación también en `models.ts`:

   ```ts
   /**
    * Representa una tarea del gestor.
    */
   export interface Tarea {
     /**
      * Identificador numerico unico.
      */
     id: number;
     /**
      * Titulo breve de la tarea.
      */
     titulo: string;
     /**
      * Detalle opcional de la tarea.
      */
     descripcion?: string;
     /**
      * Indica si la tarea esta completada.
      */
     completada: boolean;
   }
   ```

> La documentación de la interface deberías de realizarla en la cabecera de esta, pero como estamos comenzando, se que es más sencillo.
> Documenta en el README.md (markdown).

---

## 8. ¿Qué deberías saber al final si lo has realizado tú y NO la IA?

- **Tipos básicos e interfaces** → en `models.ts`.
- **Funciones tipadas y arrays** → en `tareas.ts`.
- **Módulos e import/export** → en `index.ts`, `cli.ts`.
- **Ejecución con `ts-node` y compilación con `tsc`** → scripts de consola.
- **Testing** → `tareas.test.ts` (o los tests equivalentes según tu manual).
- **Documentación y buenas prácticas** → comentarios JSDoc, nombres consistentes, código organizado.

Cuando tengas todo funcionando, podrás decir que dominas los fundamentos de TypeScript a nivel de:

- Proyecto real en Node.
- Código tipado y estructurado.
- Pruebas automatizadas.
- Ejecución desde línea de comandos.

 🚀 A por el siguiente paso.

</div>