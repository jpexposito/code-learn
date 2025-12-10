<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (Práctica 2: Consumo de resvicios rest en TypeScript)

Este ejercicio guiado es una **extensión directa de la Práctica 1 (Proyecto Tareas)**.  
El objetivo ahora es que tu aplicación de tareas **no trabaje solo en memoria**, sino que se comunique con un **servicio REST** real (local) usando TypeScript.

Al terminar, serás capaz de:

- Montar una **API REST local** con `json-server`.
- Consumir la API desde TypeScript usando `fetch`.
- Separar la lógica en módulos: **modelo**, **lógica de negocio**, **servicio REST**.
- Escribir **tests** para las funciones que hablan con la API (mockeando `fetch`).

> Para cada apartado se indica de qué parte de la documentación te puedes apoyar (manual de TypeScript + capítulo de consumo REST).

---

## 0. Preparación del proyecto

**Objetivo:** Partir de la Práctica 1 o crear un proyecto similar, y añadir una API REST local.

**De dónde viene en la documentación:**

- Capítulos de *“Instalación y primeros pasos con TypeScript”*.
- Capítulo 12 del manual: *“Consumo de servicios REST”*.
- Práctica 1: estructura básica de proyecto `mini-gestor-tareas`.

### Pasos

1. Si ya tienes la **Práctica 1** hecha (`mini-gestor-tareas` con `src/models.ts`, `src/tareas.ts`, etc.), úsala como base.
2. Si no, crea un proyecto nuevo siguiendo la sección 0 de la Práctica 1 hasta tener:
   - `npm init -y`
   - TypeScript y `ts-node` instalados.
   - `tsconfig.json` parecido a:

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

3. Asegúrate de tener la interfaz `Tarea` y tipos asociados en `src/models.ts` (igual que en la Práctica 1):

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

---

## 1. Definir la API REST con `json-server`

**Objetivo:** Levantar un backend REST muy sencillo para tareas usando `json-server`.

**De dónde viene en la documentación:**

- Capítulo 12: se habla de consumo de APIs REST.
- Recursos externos sobre `json-server` (simular una API a partir de un `db.json`).

### 1.1. Instalar `json-server`

1. Desde la raíz del proyecto, instala `json-server` como dependencia de desarrollo:

   ```bash
   npm install --save-dev json-server
   ```
Obtendrás una salida similar a la siguiente:

```code
added 45 packages, and audited 378 packages in 5s

59 packages are looking for funding
  run `npm fund` for details

found 0 vulnerabilities
```


2. En `package.json`, añade un script para levantar la API:

   ```json
   {
     "scripts": {
       "api": "json-server --watch db.json --port 3000",
       "build": "tsc",
       "start": "node dist/index.js"
     }
   }
   ```

   > Puedes ajustar los scripts de `build` y `start` según lo que ya tuvieras en la Práctica 1.

### 1.2. Crear la base de datos `db.json`

1. Crea un archivo `db.json` en la **raíz** del proyecto con el contenido inicial:

   ```json
   {
     "tareas": [
       { "id": 1, "titulo": "Estudiar TypeScript", "descripcion": "Repasar tipos y funciones", "completada": false },
       { "id": 2, "titulo": "Hacer la práctica 1", "descripcion": "Proyecto tareas en memoria", "completada": true }
     ]
   }
   ```

2. Levanta la API:

   ```bash
   npm run api
   ```

3. Abre en el navegador:

   - `http://localhost:3000/tareas` → debería devolver el array de tareas en JSON.
   - `http://localhost:3000/tareas/1` → debería devolver la tarea con id 1.

### 1.3. Especificación de la API REST de tareas

Con `json-server`, la API para `tareas` queda así (recurso `tareas` en `db.json`):

- `GET /tareas`  
  - Devuelve todas las tareas.
  - **Respuesta 200**: `Tarea[]`

- `GET /tareas/:id`  
  - Devuelve una tarea por id.
  - **200** si existe, **404** si no existe.

- `POST /tareas`  
  - Crea una nueva tarea.
  - **Body JSON** (sin `id`, lo genera el servidor):
    ```json
    { "titulo": "Texto", "descripcion": "Texto", "completada": false }
    ```
  - **201** y devuelve la tarea creada (con `id`).

- `PUT /tareas/:id`  
  - Reemplaza la tarea completa con ese `id`.
  - **Body JSON** debe tener todos los campos: `id`, `titulo`, `descripcion?`, `completada`.

- `PATCH /tareas/:id` (opcional)  
  - Actualiza parcialmente la tarea.

- `DELETE /tareas/:id`  
  - Elimina la tarea con ese id.
  - **200** o **204** si todo va bien.

> Documenta esta API en tu propio `README.md` de la práctica (puedes copiar este apartado y adaptarlo).

---

## 2. Módulo de servicio REST: `src/apiTareas.ts`

**Objetivo:** Encapsular todo el acceso HTTP a la API en un módulo de servicio.

**De dónde viene en la documentación:**

- Capítulo 12 (funciones `obtenerTareas`, `crearTarea`, etc.).
- Capítulos de módulos e import/export.

### 2.1. Crear el módulo

1. Crea `src/apiTareas.ts` con el siguiente contenido base:

   ```ts
   import { Tarea, IdTarea } from "./models";

   const API_URL = "http://localhost:3000/tareas";

   export async function obtenerTareas(): Promise<Tarea[]> {
     const respuesta = await fetch(API_URL);

     if (!respuesta.ok) {
       throw new Error(`Error al cargar tareas: ${respuesta.status} ${respuesta.statusText}`);
     }

     const datos: unknown = await respuesta.json();
     return datos as Tarea[];
   }

   export async function obtenerTarea(id: IdTarea): Promise<Tarea> {
     const respuesta = await fetch(`${API_URL}/${id}`);

     if (!respuesta.ok) {
       throw new Error(`Error al cargar tarea ${id}: ${respuesta.status} ${respuesta.statusText}`);
     }

     const datos: unknown = await respuesta.json();
     return datos as Tarea;
   }

   export type NuevaTarea = Omit<Tarea, "id">;

   export async function crearTareaRemota(nueva: NuevaTarea): Promise<Tarea> {
     const respuesta = await fetch(API_URL, {
       method: "POST",
       headers: {
         "Content-Type": "application/json",
       },
       body: JSON.stringify(nueva),
     });

     if (!respuesta.ok) {
       throw new Error(`Error al crear tarea: ${respuesta.status} ${respuesta.statusText}`);
     }

     const datos: unknown = await respuesta.json();
     return datos as Tarea;
   }

   export async function actualizarTareaRemota(tarea: Tarea): Promise<Tarea> {
     const respuesta = await fetch(`${API_URL}/${tarea.id}`, {
       method: "PUT",
       headers: {
         "Content-Type": "application/json",
       },
       body: JSON.stringify(tarea),
     });

     if (!respuesta.ok) {
       throw new Error(`Error al actualizar tarea ${tarea.id}: ${respuesta.status} ${respuesta.statusText}`);
     }

     const datos: unknown = await respuesta.json();
     return datos as Tarea;
   }

   export async function borrarTareaRemota(id: IdTarea): Promise<void> {
     const respuesta = await fetch(`${API_URL}/${id}`, {
       method: "DELETE",
     });

     if (!respuesta.ok) {
       throw new Error(`Error al borrar tarea ${id}: ${respuesta.status} ${respuesta.statusText}`);
     }
   }
   ```

2. Relaciona esto con lo visto en el manual:
   - Uso de **promesas** y `async/await`.
   - Tipos genéricos (`Promise<Tarea[]>`, `Omit<Tarea, "id">`).
   - Manejo básico de errores verificando `respuesta.ok`.

> Nota: Para que `fetch` funcione en Node, es recomendable usar Node 18+ (donde ya viene integrado). Si usas una versión anterior, deberías instalar una librería como `node-fetch` y configurarla.

---

## 3. Integrar la API REST con la lógica de tareas

**Objetivo:** Que tu aplicación utilice el backend REST en lugar de trabajar solo con arrays en memoria.

**De dónde viene en la documentación:**

- Capítulos sobre organización por capas (modelo, lógica de negocio, integración).
- Práctica 1: `index.ts`, `cli.ts` trabajando con `tareas.ts`.

### 3.1. Crear un nuevo punto de entrada `src/index-rest.ts`

1. Crea `src/index-rest.ts`:

   ```ts
   import { obtenerTareas, crearTareaRemota } from "./apiTareas";
   import { Tarea } from "./models";

   async function main() {
     console.log("Cargando tareas desde la API...");
     const tareas: Tarea[] = await obtenerTareas();
     console.log("Tareas iniciales:", tareas);

     console.log("Creando una nueva tarea remota...");
     const nueva = await crearTareaRemota({
       titulo: "Tarea creada desde index-rest.ts",
       descripcion: "Probando POST contra json-server",
       completada: false,
     });

     console.log("Tarea creada:", nueva);

     const tareasActualizadas = await obtenerTareas();
     console.log("Tareas tras la creación:", tareasActualizadas);
   }

   main().catch((error) => {
     console.error("Error en main:", error);
   });
   ```

2. Compila y ejecuta:

   ```bash
   npx ts-node src/index-rest.ts
   ```

3. Verifica en la consola que:
   - Primero se muestran las tareas iniciales.
   - Luego se muestra la tarea creada.
   - Después, la lista actualizada con la tarea nueva.

> Documenta en tu README qué está ocurriendo en cada paso.

---

## 4. Tests del servicio REST (`apiTareas.ts`) mockeando `fetch`

**Objetivo:** Escribir tests para las funciones que consumen el servicio REST sin necesidad de levantar realmente la API.

**De dónde viene en la documentación:**

- Capítulo 11: *Testing en TypeScript*.
- Sección de la Práctica 1 donde se testean funciones puras (`tareas.test.ts`).

### 4.1. Preparar Jest (si no lo hiciste en la Práctica 1)

Si ya tienes Jest configurado por la Práctica 1, puedes reutilizarlo.  
Si no, pasos mínimos (ejemplo con Jest + ts-jest):

```bash
npm install --save-dev jest ts-jest @types/jest
npx ts-jest config:init
```

Esto crea un `jest.config.js` básico.

### 4.2. Crear `tests/apiTareas.test.ts`

1. Crea la carpeta `tests/` (si no existe).
2. Crea `tests/apiTareas.test.ts`:

   ```ts
   import {
     obtenerTareas,
     crearTareaRemota,
     actualizarTareaRemota,
     borrarTareaRemota,
   } from "../src/apiTareas";
   import { Tarea } from "../src/models";

   // Hacemos que TypeScript sepa que vamos a mockear fetch
   declare const global: typeof globalThis & {
     fetch: jest.Mock;
   };

   beforeEach(() => {
     global.fetch = jest.fn();
   });

   describe("apiTareas (servicio REST)", () => {
     test("obtenerTareas hace un GET y devuelve la lista de tareas", async () => {
       const tareasMock: Tarea[] = [
         { id: 1, titulo: "A", completada: false },
         { id: 2, titulo: "B", completada: true },
       ];

       global.fetch.mockResolvedValue({
         ok: true,
         status: 200,
         statusText: "OK",
         json: async () => tareasMock,
       });

       const resultado = await obtenerTareas();

       expect(global.fetch).toHaveBeenCalledTimes(1);
       expect(global.fetch).toHaveBeenCalledWith("http://localhost:3000/tareas");
       expect(resultado).toEqual(tareasMock);
     });

     test("crearTareaRemota hace POST y devuelve la tarea creada", async () => {
       const tareaCreada: Tarea = {
         id: 10,
         titulo: "Nueva tarea",
         completada: false,
       };

       global.fetch.mockResolvedValue({
         ok: true,
         status: 201,
         statusText: "Created",
         json: async () => tareaCreada,
       });

       const resultado = await crearTareaRemota({
         titulo: "Nueva tarea",
         completada: false,
       });

       expect(global.fetch).toHaveBeenCalledTimes(1);
       const [url, opciones] = global.fetch.mock.calls[0];
       expect(url).toBe("http://localhost:3000/tareas");
       expect(opciones.method).toBe("POST");
       expect(resultado).toEqual(tareaCreada);
     });

     test("actualizarTareaRemota hace PUT y devuelve la tarea actualizada", async () => {
       const tareaActualizada: Tarea = {
         id: 1,
         titulo: "Actualizada",
         completada: true,
       };

       global.fetch.mockResolvedValue({
         ok: true,
         status: 200,
         statusText: "OK",
         json: async () => tareaActualizada,
       });

       const resultado = await actualizarTareaRemota(tareaActualizada);

       expect(global.fetch).toHaveBeenCalledTimes(1);
       const [url, opciones] = global.fetch.mock.calls[0];
       expect(url).toBe("http://localhost:3000/tareas/1");
       expect(opciones.method).toBe("PUT");
       expect(resultado).toEqual(tareaActualizada);
     });

     test("borrarTareaRemota hace DELETE y no devuelve contenido", async () => {
       global.fetch.mockResolvedValue({
         ok: true,
         status: 200,
         statusText: "OK",
         json: async () => ({}),
       });

       await borrarTareaRemota(1);

       expect(global.fetch).toHaveBeenCalledTimes(1);
       const [url, opciones] = global.fetch.mock.calls[0];
       expect(url).toBe("http://localhost:3000/tareas/1");
       expect(opciones.method).toBe("DELETE");
     });

     test("obtenerTareas lanza error si la respuesta no es ok", async () => {
       global.fetch.mockResolvedValue({
         ok: false,
         status: 500,
         statusText: "Internal Server Error",
         json: async () => [],
       });

       await expect(obtenerTareas()).rejects.toThrow("Error al cargar tareas");
     });
   });
   ```

3. Ejecuta los tests:

   ```bash
   npx jest
   ```

4. Verifica que todos pasan y entiende **qué se está testeando**:
   - Que las funciones llaman a `fetch` con la **URL correcta** y el **método correcto**.
   - Que devuelven los datos esperados cuando `fetch` responde bien.
   - Que lanzan un error cuando `fetch` responde con `ok: false`.

> Documenta en el README cómo estás testeando un módulo que habla con una API, sin necesidad de acceder realmente a la red.

---

## 5. Qué deberías saber si has hecho la práctica tú (y no la IA 😉)

Al terminar esta práctica deberías sentirte cómodo con:

- **Diseñar y documentar una pequeña API REST** (lista de endpoints, métodos, payloads).
- Consumir la API desde TypeScript usando `fetch` y `async/await`.
- Separar el código en capas:
  - `models.ts` → tipos e interfaces.
  - `tareas.ts` → lógica de negocio sobre arrays.
  - `apiTareas.ts` → acceso HTTP a la API REST.
  - `index-rest.ts` → punto de entrada que orquesta todo.
- Escribir **tests con Jest** que mockean `fetch` para probar tus funciones sin depender de una API real.
- Entender cómo una aplicación “tonta” en memoria se puede convertir en una aplicación que habla con un backend real.

Cuando tengas esto funcionando, estarás listo para dar el salto a:

- Integrar esta lógica en una interfaz web (React, Angular, etc.).
- Añadir autenticación a las llamadas.
- Trabajar con APIs más complejas.

¡Buen trabajo! y sigue sin la IA 🚀

</div>
