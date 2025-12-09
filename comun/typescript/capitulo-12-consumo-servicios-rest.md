<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (Capítulo 12: Consumo de servicios REST desde TypeScript)

## Introducción

En aplicaciones reales, es muy común que nuestro código TypeScript tenga que **hablar con un backend** mediante
**servicios REST**. Es decir, hacer peticiones HTTP (GET, POST, PUT, DELETE, etc.) a una API que devuelve datos en
formato **JSON**.

En este capítulo veremos:

- Conceptos básicos de consumo de APIs REST.
- Cómo usar `fetch` con **TypeScript** (tipado de las respuestas).
- Manejo básico de errores.
- Una propuesta de práctica basada en la **Práctica 01** el manual básico que estamos siguiendo:  
  <https://github.com/jpexposito/code-learn/blob/main/comun/typescript/practicas/practica-01.md>

---

## Conceptos básicos de una API REST

Una API REST suele exponer recursos mediante URLs, por ejemplo:

- `GET /api/tareas` → devuelve una lista de tareas.
- `GET /api/tareas/1` → devuelve la tarea con id 1.
- `POST /api/tareas` → crea una tarea nueva.
- `PUT /api/tareas/1` → actualiza la tarea 1.
- `DELETE /api/tareas/1` → elimina la tarea 1.

Normalmente la comunicación se hace en **JSON** y desde TypeScript queremos:

1. **Tipar** la estructura de los datos (interfaces / tipos).
2. Usar `fetch` (o librerías como `axios`) para hacer peticiones.
3. Manejar casos de **error** (códigos HTTP 4xx / 5xx).

---

## Definir tipos para los datos de la API

Supongamos que tenemos un recurso `Tarea` en la API:

```ts
// ejemplos/11-consumo-rest.ts
export interface Tarea {
  id: number;
  titulo: string;
  descripcion?: string;
  completada: boolean;
}
```

La API podría devolver, por ejemplo, una lista de tareas en JSON:

```json
[
  { "id": 1, "titulo": "Aprender TypeScript", "completada": false },
  { "id": 2, "titulo": "Hacer la práctica 01", "completada": true }
]
```

En TypeScript, lo ideal es **tipar también la respuesta**, por ejemplo:

```ts
export type ListaTareas = Tarea[];
```

---

## Hacer peticiones HTTP con `fetch`

### `fetch` básico con TypeScript

La función global `fetch` (en navegadores y en algunos runtimes como Deno o Node con `undici`) permite hacer
peticiones HTTP de forma sencilla.

Ejemplo de función que obtiene todas las tareas de una API:

```ts
// ejemplos/11-consumo-rest.ts

const API_URL = "https://ejemplo-api.local/api/tareas";

export async function obtenerTareas(): Promise<Tarea[]> {
  const respuesta = await fetch(API_URL);

  if (!respuesta.ok) {
    throw new Error(`Error al cargar tareas: ${respuesta.status} ${respuesta.statusText}`);
  }

  const datos: unknown = await respuesta.json();

  // Aquí hacemos un "cast" sencillo. En un proyecto real podrías validar la forma del JSON.
  return datos as Tarea[];
}
```

Puntos importantes:

- `fetch` devuelve una **Promise** con un objeto `Response`.
- Comprobamos `respuesta.ok` para detectar errores (códigos 4xx/5xx).
- Llamamos a `respuesta.json()` para obtener el cuerpo parseado.
- Tipamos el resultado como `Tarea[]` (en proyectos grandes es muy recomendable validar el shape del JSON).

---

## Crear, actualizar y borrar recursos

### Crear una tarea (`POST`)

```ts
export async function crearTarea(nueva: Omit<Tarea, "id">): Promise<Tarea> {
  const respuesta = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(nueva),
  });

  if (!respuesta.ok) {
    throw new Error(`Error al crear tarea: ${respuesta.status}`);
  }

  const datos: unknown = await respuesta.json();
  return datos as Tarea;
}
```

Aquí usamos `Omit<Tarea, "id">` para indicar que el cliente envía una tarea sin `id` (lo genera el servidor).

### Actualizar una tarea (`PUT`)

```ts
export async function actualizarTarea(tarea: Tarea): Promise<Tarea> {
  const respuesta = await fetch(`${API_URL}/${tarea.id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(tarea),
  });

  if (!respuesta.ok) {
    throw new Error(`Error al actualizar tarea: ${respuesta.status}`);
  }

  const datos: unknown = await respuesta.json();
  return datos as Tarea;
}
```

### Borrar una tarea (`DELETE`)

```ts
export async function borrarTarea(id: number): Promise<void> {
  const respuesta = await fetch(`${API_URL}/${id}`, {
    method: "DELETE",
  });

  if (!respuesta.ok) {
    throw new Error(`Error al borrar tarea: ${respuesta.status}`);
  }
}
```

---

## Manejo básico de errores

Cuando consumimos APIs, es fundamental manejar errores:

- Servidor no disponible.
- Error de red.
- Códigos 4xx (petición incorrecta, no autorizado…).
- Códigos 5xx (error interno del servidor).

Ejemplo de uso con `try/catch`:

```ts
async function ejemploUso() {
  try {
    const tareas = await obtenerTareas();
    console.log("Tareas cargadas:", tareas);
  } catch (error) {
    if (error instanceof Error) {
      console.error("No se pudieron cargar las tareas:", error.message);
    } else {
      console.error("Error desconocido al cargar tareas");
    }
  }
}
```

En una aplicación real (por ejemplo React, Angular o una app de consola interactiva), normalmente:

- Mostramos un **mensaje de error** al usuario.
- Registramos el error en logs.
- Permitimos reintentar la operación.

---

## Relación con la práctica 01

En la **Práctica 01** que has realizado (en el repositorio `code-learn`) se propone construir una pequeña aplicación de
gestión de tareas en TypeScript. Una extensión natural de esa práctica es que las tareas no estén solo en memoria,
sino que se lean/escriban contra una **API REST**.

Algunas ideas para conectar este capítulo con la práctica:

1. Definir la interfaz `Tarea` y los tipos auxiliares en un fichero `models.ts`.
2. Crear un módulo `api.ts` que implemente las funciones:
   - `obtenerTareas()`
   - `crearTarea(tarea)`
   - `actualizarTarea(tarea)`
   - `borrarTarea(id)`
3. Desde tu código principal (por ejemplo `main.ts` o el componente principal en React), usar esas funciones para:
   - Cargar la lista inicial de tareas al arrancar.
   - Enviar al backend las altas, modificaciones y borrados.

---

## Ejercicios propuestos

1. **Conexión a una API pública**  
   Elige una API pública sencilla (por ejemplo, Pockemo: https://pokeapi.co/) e
   implementa funciones TypeScript para cada uno de los ejemplos:
   - `pokemon/ditto`
   - `pokemon-species/aegislash`
   - `type/3, ability/battle-armor`
   - `pokemon?limit=100000&offset=0`.

2. **Módulo de servicio para la práctica 01**  
   Crea un fichero `servicioTareas.ts` que contenga todas las funciones de acceso a la API de tareas. Haz que el resto
   de tu práctica 01 use siempre este módulo (en vez de trabajar con datos en memoria).

3. **Manejo avanzado de errores**  
   Mejora el manejo de errores para distinguir entre:
   - Errores de red (no hay conexión).
   - Errores 4xx (entradas inválidas, no autorizado).
   - Errores 5xx (problema interno del servidor).
   Muestra mensajes diferentes según el tipo de error.

4. **Tipado más estricto de respuestas**  
   Investiga librerías como [zod](https://zod.dev/) o [io-ts](https://www.npmjs.com/package/io-ts-types) y crea un pequeño ejemplo donde:
   - Valides el JSON recibido de la API.
   - Solo lo conviertas a `Tarea[]` si pasa la validación.

---

[⬅ Capítulo 11: Tests unitarios en TypeScript](./capitulo-11-test-unitarios.md) · [Ir al índice](./README.md) · [Capítulo 12: Consumo de base datos en TypeScript ➡](./capitulo-13-consumo-base-datos.md)
