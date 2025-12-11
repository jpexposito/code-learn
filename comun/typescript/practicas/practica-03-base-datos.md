<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (Práctica 3: Persistencia de tareas en SQLite3 con TypeScript)

Esta práctica es una **variante de la Práctica 2 (Consumo de servicios REST)**, pero ahora el objetivo es
que tu aplicación de tareas **guarde y lea los datos desde una base de datos SQLite3**, en lugar de usar
solo memoria o `json-server`.

---

## 0. Preparación del proyecto

**Objetivo:** Partir de la Práctica 1/2 (mini gestor de tareas) o crear un proyecto equivalente,
y prepararlo para usar SQLite3.

**De dónde viene en la documentación:**

- Capítulos de *“Instalación y primeros pasos con TypeScript”*.
- Capítulos donde se habla de organización por capas (modelo, servicio, infraestructura).
- Práctica 1 y 2: estructura básica de proyecto `mini-gestor-tareas`.

### Pasos

1. Si ya tienes la **Práctica 1** o la **Práctica 2** funcionando, úsala como base.  
   Asegúrate de tener algo parecido a:

   - `src/models.ts` con la interfaz `Tarea`.
   - `src/tareas.ts` o similar con lógica de negocio.
   - `package.json`, `tsconfig.json`, etc.

2. Si no tienes proyecto, crea uno nuevo:

   ```bash
   mkdir mini-gestor-tareas-sqlite
   cd mini-gestor-tareas-sqlite
   npm init -y
   npm install typescript ts-node --save-dev
   npx tsc --init
   ```

3. Ajusta tu `tsconfig.json` (si hace falta) para que se parezca a:

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

4. En `src/models.ts` define (o comprueba que ya tienes) tu modelo de tareas:

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

## 1. Instalar y preparar SQLite3

**Objetivo:** Instalar las dependencias necesarias y crear la base de datos `tareas.db`con la tabla `tareas`. 

**De dónde viene en la documentación:**

- Parte de Node / acceso a ficheros / módulos externos.
- (Si tenéis apartado de BBDD, enlázalo aquí).

### 1.1. Instalar `better-sqlite3`

Usaremos [`better-sqlite3`](https://github.com/WiseLibs/better-sqlite3) porque tiene una API
sencilla y sin callbacks.

1. Instala las dependencias:

   ```bash
   npm install better-sqlite3
   npm install --save-dev @types/better-sqlite3
   ```

2. En `package.json`, añade un script para probar el proyecto:

   ```json
   {
     "scripts": {
       "start": "node dist/index.js",
       "build": "tsc",
       "dev": "ts-node src/index.ts"
     }
   }
   ```

   > Puedes adaptar estos scripts a cómo lo tengas en las prácticas anteriores.

### 1.2. Crear la base de datos `tareas.db` y la tabla `tareas`

Hay dos opciones:

#### Opción A – Crear la tabla desde la propia aplicación

Crearemos un módulo `src/db.ts` que:

- Abra (o cree) el fichero `tareas.db`.
- Ejecute un `CREATE TABLE IF NOT EXISTS` para asegurar que la tabla existe.

```ts
// src/db.ts
import Database from "better-sqlite3";

const DB_FILE = "tareas.db";

let db: Database.Database | null = null;

export function getDb(): Database.Database {
  if (!db) {
    db = new Database(DB_FILE);

    db.exec(`
      CREATE TABLE IF NOT EXISTS tareas (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        titulo TEXT NOT NULL,
        descripcion TEXT,
        completada INTEGER NOT NULL
      )
    `);
  }

  return db;
}
```

#### Opción B – Crear la tabla desde la consola de sqlite3

Si tienes instalado el binario `sqlite3`, puedes:

```bash
sqlite3 tareas.db
```

y dentro escribir:

```sql
CREATE TABLE tareas (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  titulo TEXT NOT NULL,
  descripcion TEXT,
  completada INTEGER NOT NULL
);
.exit
```

> En ambos casos, la tabla tendrá el mismo modelo que tu interfaz `Tarea` (booleans representados como `0`/`1`).

Como última opción tendrás la bbdd creada y la puedes [descargas](files/tareas.db).

---

## 2. Módulo repositorio: `src/repositorioTareasSqlite.ts`

**Objetivo:** Encapsular todo el acceso a la base de datos en un módulo tipo “repositorio”.

**De dónde viene en la documentación:**

- Capítulos de organización por capas (repositorios).
- Ejemplos de la Práctica 1 donde tenías funciones con arrays en memoria.

### 2.1. Interfaz del repositorio

Crea `src/repositorioTareasSqlite.ts` con **la interfaz** del repositorio y la implementación vacía
(o con TODOs) para ir completando:

```ts
// src/repositorioTareasSqlite.ts
import { getDb } from "./db";
import { Tarea, IdTarea } from "./models";

export class RepositorioTareasSqlite {

  private db = getDb();

  obtenerTodas(): Tarea[] {
    // TODO: SELECT * FROM tareas
    // Pista: this.db.prepare("SELECT id, titulo, descripcion, completada FROM tareas")
    //       .all();
    return [];
  }

  obtenerPorId(id: IdTarea): Tarea | undefined {
    // TODO: SELECT ... WHERE id = ?
    return undefined;
  }

  crear(titulo: string, descripcion?: string): Tarea {
    // TODO:
    // 1. INSERT INTO tareas (titulo, descripcion, completada) VALUES (?, ?, 0)
    // 2. Recuperar el id generado (stmt.run().lastInsertRowid)
    // 3. Devolver la tarea completa
    throw new Error("No implementado");
  }

  actualizar(tarea: Tarea): Tarea | undefined {
    // TODO:
    // 1. UPDATE tareas SET titulo = ?, descripcion = ?, completada = ? WHERE id = ?
    // 2. Comprobar cambios (stmt.changes)
    // 3. Si no se actualiza ninguna fila, devolver undefined
    // 4. Si se actualiza, devolver la tarea
    throw new Error("No implementado");
  }

  borrar(id: IdTarea): boolean {
    // TODO:
    // 1. DELETE FROM tareas WHERE id = ?
    // 2. Devolver true si se ha borrado 1 registro, false en otro caso
    throw new Error("No implementado");
  }
}
```

**Detalles importantes:**

- En SQLite, el campo `completada` es un `INTEGER` (0 o 1).  
  Tendrás que convertir entre `boolean` ↔ `0/1` cuando leas/escribas:

  ```ts
  const completadaBool = fila.completada === 1;
  const completadaInt = tarea.completada ? 1 : 0;
  ```

---

## 3. Integrar el repositorio con la lógica de negocio

**Objetivo:** Que el resto de tu código (CLI, UI, etc.) use el repositorio SQLite en lugar
de arrays en memoria.

**De dónde viene en la documentación:**

- Capítulos donde separas dominio / servicio / infraestructura.
- Práctica 1: funciones de gestión de tareas en memoria.

### 3.1. Servicio de tareas basado en el repositorio

Si en la Práctica 1 tenías un módulo tipo `src/servicioTareas.ts` que trabajaba con arrays, ahora puedes crear uno nuevo que reciba un repositorio como dependencia:

```ts
// src/servicioTareas.ts
import { Tarea, IdTarea, FiltroTarea } from "./models";
import { RepositorioTareasSqlite } from "./repositorioTareasSqlite";

export class ServicioTareas {
  constructor(private repo: RepositorioTareasSqlite) {}

  listar(filtro: FiltroTarea): Tarea[] {
    const todas = this.repo.obtenerTodas();
    switch (filtro) {
      case "pendientes":
        return todas.filter((t) => !t.completada);
      case "completadas":
        return todas.filter((t) => t.completada);
      case "todas":
      default:
        return todas;
    }
  }

  crear(titulo: string, descripcion?: string): Tarea {
    if (!titulo || titulo.trim().length === 0) {
      throw new Error("El título no puede estar vacío");
    }
    return this.repo.crear(titulo, descripcion);
  }

  // EJERCICIO: añade métodos para actualizar, borrar, marcar como completada, etc. Un CRUD de toda la vida
}
```

### 3.2. Punto de entrada `src/index.ts`

Crea un `index.ts` sencillo para probarlo:

```ts
// src/index.ts
import { RepositorioTareasSqlite } from "./repositorioTareasSqlite";
import { ServicioTareas } from "./servicioTareas";

async function main() {
  const repo = new RepositorioTareasSqlite();
  const servicio = new ServicioTareas(repo);

  console.log("Tareas actuales:");
  console.log(servicio.listar("todas"));

  console.log("Creando una nueva tarea...");
  const nueva = servicio.crear("Aprender SQLite3 con TypeScript", "Práctica 3");
  console.log("Tarea creada:", nueva);

  console.log("Tareas tras la creación:");
  console.log(servicio.listar("todas"));
}

main().catch((error) => {
  console.error("Error en main:", error);
});
```

Compila y ejecuta:

```bash
npx ts-node src/index.ts
```

Comprueba en la consola que:

1. Al principio, la lista está vacía (o con las tareas que tú añadas manualmente en la BD).
2. Después de crear, la nueva tarea aparece en la BD (puedes confirmarlo con el CLI de sqlite3).

---

## 4. (Opcional) Tests del repositorio sobre una base de datos de pruebas

**Objetivo:** Aprender a testear código que accede a una BD sin machacar tu base real.

**De dónde viene en la documentación:**

- Capítulo de tests en TypeScript.
- Ejemplos con Jest de la Práctica 1/2.

### 4.1. Idea general

1. Crear una base de datos **temporal** (por ejemplo, `:memory:` en SQLite o un fichero `test.db`).
2. Inyectar esa conexión en el repositorio en lugar de usar `getDb()` global.

Ejemplo de constructor alternativo:

```ts
// en RepositorioTareasSqlite
constructor(db?: Database.Database) {
  this.db = db ?? getDb();
}
```

En tests, podrías hacer:

```ts
const dbTest = new Database(":memory:");
dbTest.exec("CREATE TABLE tareas (...);");
const repo = new RepositorioTareasSqlite(dbTest);
```

3. Escribir tests que:
   - Creen registros.
   - Los lean.
   - Los actualicen.
   - Los borren.
   - Comprueben el comportamiento cuando el `id` no existe, etc.

> Esta parte puede ser un “extra” para subir nota o para practicar más a fondo.

---

## 5. Qué deberías manejar al terminar la práctica

Si has seguido todos los pasos y **has rellenado tú** los TODOs, deberías:

- Ser capaz de **levantar una BD SQLite3** desde Node/TypeScript.
- Crear la tabla `tareas` alineada con tu modelo TypeScript.
- Escribir un **repositorio** que haga CRUD contra la BD usando SQL (SELECT, INSERT, UPDATE, DELETE).
- Integrar ese repositorio con un **servicio de dominio** (`ServicioTareas`) que aplica reglas de negocio.
- Probar el conjunto desde un `index.ts` o desde una interfaz (CLI, tests, etc.).
- Entender la diferencia entre:
  - La **capa de persistencia** (SQLite).
  - La **capa de dominio/servicio** (reglas de negocio).
  - La **capa de presentación** (CLI, REST, etc., si la añades).

¡Ya estás haciendo aplicaciones con base de datos real! Recuerda seguir sin la IA 🚀

</div>
