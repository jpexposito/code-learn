// ejemplos/11-consumo-rest.ts

// Ejemplo sencillo de consumo de un servicio REST desde TypeScript.
// Este código asume que existe una función global `fetch` (por ejemplo,
// en un navegador, en Deno o en Node 18+).

export interface Tarea {
  id: number;
  titulo: string;
  descripcion?: string;
  completada: boolean;
}

// En un proyecto real, esta URL apuntaría a tu backend (por ejemplo, el de la práctica 01).
const API_URL = "https://ejemplo-api.local/api/tareas";

export async function obtenerTareas(): Promise<Tarea[]> {
  const respuesta = await fetch(API_URL);

  if (!respuesta.ok) {
    throw new Error(`Error al cargar tareas: ${respuesta.status} ${respuesta.statusText}`);
  }

  const datos: unknown = await respuesta.json();

  // En un proyecto real convendría validar el shape del JSON.
  return datos as Tarea[];
}

export async function crearTarea(nueva: Omit<Tarea, "id">): Promise<Tarea> {
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

export async function actualizarTarea(tarea: Tarea): Promise<Tarea> {
  const respuesta = await fetch(`${API_URL}/${tarea.id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(tarea),
  });

  if (!respuesta.ok) {
    throw new Error(`Error al actualizar tarea: ${respuesta.status} ${respuesta.statusText}`);
  }

  const datos: unknown = await respuesta.json();
  return datos as Tarea;
}

export async function borrarTarea(id: number): Promise<void> {
  const respuesta = await fetch(`${API_URL}/${id}`, {
    method: "DELETE",
  });

  if (!respuesta.ok) {
    throw new Error(`Error al borrar tarea: ${respuesta.status} ${respuesta.statusText}`);
  }
}

// Pequeño ejemplo de uso. En una app real lo llamarías desde tu código principal.
async function demo() {
  try {
    console.log("Cargando tareas...");
    const tareas = await obtenerTareas();
    console.log("Tareas:", tareas);

    console.log("Creando tarea...");
    const nueva = await crearTarea({
      titulo: "Nueva tarea desde TypeScript",
      completada: false,
    });
    console.log("Tarea creada:", nueva);

    console.log("Marcando como completada...");
    const actualizada = await actualizarTarea({ ...nueva, completada: true });
    console.log("Tarea actualizada:", actualizada);

    console.log("Borrando tarea...");
    await borrarTarea(actualizada.id);
    console.log("Tarea borrada");
  } catch (error) {
    if (error instanceof Error) {
      console.error("Error en demo de consumo REST:", error.message);
    } else {
      console.error("Error desconocido en demo de consumo REST");
    }
  }
}

// Solo ejecutamos la demo si este archivo es el entrypoint principal (opcional)
if (require.main === module) {
  // En Node con `ts-node` o compilado a JS, esto lanzará la demo.
  // Ten en cuenta que necesitas que `fetch` exista en el entorno de ejecución.
  demo();
}
