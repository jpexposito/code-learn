<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (Capítulo 13: Consumo de Base Datos desde TypeScript)

## Introducción

En este documento se explican varias formas de acceder a bases de datos desde **TypeScript** ejecutándose sobre **Node.js**:

- Conexión directa con **drivers** (MySQL, SQLite).
- Uso de un **ORM** moderno (Prisma).
- Comentario sobre el uso de **H2** desde TypeScript.
- Recomendaciones para proyectos de enseñanza/prácticas.

> ⚠️ Importante: Todo lo que sigue es para **Node.js** (backend).  
> Desde Angular (frontend) NO se conecta directamente a la BBDD, se habla con un backend (por ejemplo un API REST).

---

## 0. Preparación del proyecto TypeScript + Node

Suponemos que tienes un proyecto similar a:

```text
mi-proyecto-ts/
  ├─ src/
  │   └─ index.ts
  ├─ package.json
  ├─ tsconfig.json
  └─ node_modules/
```

### 0.1. `tsconfig.json` básico

Ejemplo de configuración mínima para trabajar con Node + TypeScript:

```json
{
  "compilerOptions": {
    "target": "ES2019",
    "module": "CommonJS",
    "moduleResolution": "node",
    "strict": true,
    "esModuleInterop": true,
    "outDir": "dist",
    "skipLibCheck": true
  },
  "include": ["src"],
  "exclude": ["node_modules", "dist"]
}
```

### 0.2. Scripts típicos en `package.json`

```jsonc
{
  "scripts": {
    "build": "tsc",
    "start": "node dist/index.js",
    "dev": "ts-node src/index.ts"
  }
}
```

---

## 1. Conexión directa con drivers

Esta aproximación es la más “baja nivel”: creas tú mismo las conexiones, escribes SQL a mano y procesas los resultados.

### 1.1. MySQL con `mysql2`

#### 1.1.1. Instalación

```bash
npm install mysql2
npm install --save-dev @types/mysql2
```

#### 1.1.2. Ejemplo de conexión y consulta simple

**`src/db/mysql.ts`**

```ts
import mysql, { Pool } from 'mysql2/promise';

let pool: Pool | null = null;

export function getPool(): Pool {
  if (!pool) {
    pool = mysql.createPool({
      host: 'localhost',
      user: 'root',
      password: 'tu_password',
      database: 'mi_basedatos',
      waitForConnections: true,
      connectionLimit: 10,
      queueLimit: 0,
    });
  }
  return pool;
}

export interface Usuario {
  id: number;
  nombre: string;
  email: string;
}

export async function obtenerUsuarios(): Promise<Usuario[]> {
  const pool = getPool();

  const [rows] = await pool.query('SELECT id, nombre, email FROM usuarios');

  // rows es de tipo RowDataPacket[], lo convertimos a nuestro tipo
  return rows as Usuario[];
}
```

**`src/index.ts`**

```ts
import { obtenerUsuarios } from './db/mysql';

async function main() {
  try {
    const usuarios = await obtenerUsuarios();
    console.log('Usuarios:', usuarios);
  } catch (err) {
    console.error('Error al obtener usuarios:', err);
  }
}

main();
```

> Ventajas:
> - Control total sobre el SQL.
> - Pocas dependencias.
>
> Inconvenientes:
> - Escribes mucho SQL a mano.
> - Menos ayuda de tipos (salvo que tú modeles bien los interfaces).

---

### 1.2. SQLite con `better-sqlite3` (sencillo para proyectos pequeños)

SQLite es muy cómodo para **prácticas** porque la BBDD es un solo fichero.

#### 1.2.1. Instalación

```bash
npm install better-sqlite3
npm install --save-dev @types/better-sqlite3
```

#### 1.2.2. Ejemplo de uso

**`src/db/sqlite.ts`**

```ts
import Database from 'better-sqlite3';

export interface Tarea {
  id: number;
  titulo: string;
  completada: number; // 0 o 1 en la tabla
}

const db = new Database('tareas.db');

// Crear tabla si no existe
db.exec(`
  CREATE TABLE IF NOT EXISTS tareas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    titulo TEXT NOT NULL,
    completada INTEGER NOT NULL DEFAULT 0
  )
`);

export function insertarTarea(titulo: string): void {
  const stmt = db.prepare('INSERT INTO tareas (titulo) VALUES (?)');
  stmt.run(titulo);
}

export function obtenerTareas(): Tarea[] {
  const stmt = db.prepare('SELECT id, titulo, completada FROM tareas');
  return stmt.all() as Tarea[];
}
```

**`src/index.ts`**

```ts
import { insertarTarea, obtenerTareas } from './db/sqlite';

insertarTarea('Aprender TypeScript con SQLite');

const tareas = obtenerTareas();
console.log(tareas);
```

> `better-sqlite3` es **sincrónica**, lo que simplifica los ejemplos en prácticas.  
> Para aplicaciones más grandes puedes preferir librerías asíncronas.

---

## 2. ORMs con soporte fuerte para TypeScript

Un **ORM** (Object-Relational Mapper) te permite:

- Definir los modelos en TypeScript.
- Generar o sincronizar el esquema de la BBDD.
- Escribir consultas con una API de alto nivel en lugar de SQL a mano.
- Disfrutar de autocompletado y tipos en las consultas.

### 2.1. Prisma (recomendado para enseñar TypeScript + BBDD)

Prisma es un ORM moderno muy cómodo de usar con TypeScript.  
Funciona muy bien con **MySQL**, **PostgreSQL**, **SQLite**, etc.

#### 2.1.1. Instalación y configuración inicial

Dentro de tu proyecto TypeScript:

```bash
npm install prisma --save-dev
npm install @prisma/client
```

Inicializar Prisma:

```bash
npx prisma init
```

Esto crea:

- `.env` → donde configuras la URL de la BBDD.
- `prisma/schema.prisma` → donde defines el modelo de datos.

#### 2.1.2. Ejemplo con SQLite

En `.env`:

```env
DATABASE_URL="file:./dev.db"
```

En `prisma/schema.prisma`:

```prisma
datasource db {
  provider = "sqlite"
  url      = env("DATABASE_URL")
}

generator client {
  provider = "prisma-client-js"
}

model Tarea {
  id          Int     @id @default(autoincrement())
  titulo      String
  descripcion String?
  completada  Boolean @default(false)
}
```

Crear la base de datos y aplicar el esquema:

```bash
npx prisma migrate dev --name init
```

Esto crea el fichero `dev.db` y genera el cliente.

#### 2.1.3. Usar Prisma desde TypeScript

**`src/db/prisma.ts`**

```ts
import { PrismaClient } from '@prisma/client';

export const prisma = new PrismaClient();
```

**`src/index.ts`**

```ts
import { prisma } from './db/prisma';

async function main() {
  // Insertar una tarea
  await prisma.tarea.create({
    data: {
      titulo: 'Práctica Prisma',
      descripcion: 'Probar acceso a BBDD con TypeScript',
    },
  });

  // Consultar todas las tareas
  const tareas = await prisma.tarea.findMany();
  console.log('Tareas:', tareas);

  // Actualizar
  if (tareas[0]) {
    await prisma.tarea.update({
      where: { id: tareas[0].id },
      data: { completada: true },
    });
  }
}

main()
  .catch((e) => console.error(e))
  .finally(async () => {
    await prisma.$disconnect();
  });
```

> Ventajas de Prisma para docencia:
> - Modelos declarativos en un esquema único (`schema.prisma`).
> - Generación de tipos automáticamente.
> - Muy buena integración con TypeScript (autocompletado contextual).

#### 2.1.4. Cambiar de SQLite a MySQL con Prisma

Simplemente cambias la configuración en `.env` y en `schema.prisma`:

`.env`:

```env
DATABASE_URL="mysql://usuario:password@localhost:3306/mi_basedatos"
```

`schema.prisma`:

```prisma
datasource db {
  provider = "mysql"
  url      = env("DATABASE_URL")
}
```

(resto del esquema igual)

Luego aplicas migraciones:

```bash
npx prisma migrate dev --name switch-to-mysql
```

Tu código en TypeScript apenas cambia (las queries Prisma son casi iguales), solo cambias el motor de la base de datos.

---

## 3. ¿Y qué pasa con H2?

**H2** es una base de datos **embebida en Java** muy usada con **Spring Boot**.  
Técnicamente podrías intentar conectarte a H2 desde Node.js, pero:

- H2 habla principalmente **JDBC** (mundo Java).
- No existe un driver “oficial” para Node/TypeScript como sí lo hay para MySQL/PostgreSQL/SQLite.

Por eso, en la práctica, lo más habitual es:

### 3.1. Opción recomendada: usar H2 desde Java y exponer una API REST

1. Montas tu aplicación **Spring Boot** con H2 (como ya haces):
   - Spring Boot + Spring Data JPA + H2.
2. Expones tus entidades como **servicios REST** (`@RestController`).
3. Desde TypeScript/Node (o Angular) **no hablas directamente con H2**, sino con la **API REST** de Spring Boot.

Es decir:

```text
TypeScript (Node o Angular)  <-->  REST (Spring Boot)  <-->  H2
```

Ventajas:

- Cada tecnología juega en su terreno natural (Java+H2, TS+HTTP).

---

[⬅ Capítulo 12: Consumo de servicios rest desde TypeScript](./capitulo-12-consumo-servicios-rest.md) · [Ir al índice](./README.md)
