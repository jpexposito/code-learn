<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (Práctica 4: Doble persistencia de tareas (SQLite3 local + API REST remota con H2)).

## 1. Descripción general

En esta práctica vas a construir un **mini gestor de tareas** que sea capaz de trabajar con **dos fuentes de datos**:

1. Una **base de datos local SQLite3**, accesible directamente desde tu aplicación TypeScript (Node).
2. Una **API REST remota** de tareas (por ejemplo, un backend en Spring Boot con base de datos **H2**).

El objetivo es que tu aplicación:

- Pueda **listar, crear, actualizar y borrar** tareas en **local** (SQLite3).
- Pueda **listar, crear, actualizar y borrar** tareas en **remoto** (API REST/H2).
- Tenga una **capa de servicio** que permita sincroniza elegir el **origen de datos** (local o remoto).
- Incluya al menos una operación de **sincronización** entre remoto y local.

La idea es simular una aplicación que funciona **offline/online**, o que mantiene una copia local de los datos del servidor.

---

## 2. Modelo de datos

Trabajarás con un modelo de tarea muy sencillo. Deberás definirlo en TypeScript mediante interfaces/tipos.  
Como mínimo, una tarea tendrá:

- `id: number`
- `titulo: string`
- `descripcion?: string`
- `completada: boolean`

---

## 3. Requisitos técnicos

### 3.1. Proyecto TypeScript (Node)

Deberás crear (o reutilizar) un proyecto de TypeScript con:

- `npm` / `package.json`.
- `tsconfig.json` con salida a una carpeta `dist/` (u otra que elijas).
- Scripts básicos para:
  - Compilar (`npm run build`).
  - Ejecutar el proyecto (`npm start` o similar).
  - Opcionalmente, ejecutar directamente con `ts-node`.

### 3.2. Base de datos local SQLite3

- La base de datos se llamará, por ejemplo, `tareas.db`.
- Debe contener una tabla `tareas` alineada con tu modelo TypeScript.
- La aplicación será responsable de **asegurar que la tabla existe** (por ejemplo, ejecutando un `CREATE TABLE IF NOT EXISTS` al arrancar).

### 3.3. API REST remota (H2)

Crea o completa el servicio rest en java crear y mantener un servicio rest en java que permita:

- Consumir una API REST de tareas (por ejemplo, una app Spring Boot con H2).
- Utilizar endpoints del estilo:

  - `GET /api/tareas`
  - `GET /api/tareas/{id}`
  - `POST /api/tareas`
  - `PUT /api/tareas/{id}`
  - `DELETE /api/tareas/{id}`

Tu código **no accede directamente a H2**; lo hace a través del servicio REST.

---

## 4. Estructura y capas mínimas

Tu solución deberá estar organizada por **capas**, separando responsabilidades:

1. **Modelo** (`models.ts` o similar)
   - Interfaces y tipos (`Tarea`, `IdTarea`, etc.).

2. **Persistencia local** (SQLite3)
   - Un módulo tipo `repositorioTareasSqlite` (nombre orientativo).
   - Encargado de realizar las operaciones CRUD contra la base de datos `tareas.db`.
   - No debe contener lógica de negocio (solo acceso a datos).

3. **Cliente REST remoto**
   - Un módulo que encapsule el acceso a la API REST de tareas (por ejemplo, `apiTareasRemota`).
   - Debe proporcionar funciones/métodos para:
     - Obtener todas las tareas remotas.
     - Obtener una tarea remota por id.
     - Crear una tarea remota.
     - Actualizar una tarea remota.
     - Borrar una tarea remota.

4. **Servicio de dominio con doble origen**
   - Un módulo de **servicio** que reciba como dependencias:
     - El repositorio local (SQLite).
     - El cliente REST remoto (H2 vía API).
   - Debe ofrecer métodos para:
     - Listar tareas según un filtro (todas/pendientes/completadas) y un **origen** (`local` o `remoto`).
     - Crear tareas en el origen indicado.
     - (Opcional, pero recomendado) Actualizar y borrar tareas en el origen indicado.
   - Debe incluir al menos una operación de **sincronización** de datos, por ejemplo:
     - Sincronizar **del servidor remoto a la base local**.
     - Estrategia simple aceptada: borrar todas las locales y volver a importarlas desde el servidor.

5. **Punto de entrada / CLI simple**
   - Un `index.ts` (u otro fichero equivalente) que:
     - Instancie las dependencias.
     - Ejecute un flujo de ejemplo, como:
       - Mostrar tareas remotas.
       - Sincronizar remoto → local.
       - Mostrar tareas locales.
       - Crear tareas en local y remoto, y mostrarlas.

---

## 5. Funcionalidades mínimas a implementar

### 5.1. Sobre SQLite3 (local)

- Crear la base de datos si no existe.
- Crear la tabla `tareas` si no existe.
- Implementar operaciones:
  - Insertar una nueva tarea.
  - Listar todas las tareas.
  - Buscar una tarea por id.
  - Actualizar una tarea existente.
  - Borrar una tarea por id.

### 5.2. Sobre la API REST remota (H2)

- Implementar una capa cliente (con `fetch` u otra librería HTTP).
- Implementar operaciones:
  - Listar todas las tareas remotas.
  - Obtener una tarea remota por id.
  - Crear tareas remotas.
  - Actualizar tareas remotas.
  - Borrar tareas remotas.

### 5.3. Servicio de dominio con doble API

- Diseñar una API de servicio que acepte un parámetro que indique el **origen de los datos** (`local` o `remoto`).
- Delegar en el repositorio apropiado (local o remoto) según dicho parámetro.
- Implementar al menos una operación de **sincronización remoto → local**, con la estrategia que acuerdes (documentándola en el README).

---

## 6. Entregables

Deberás entregar:

1. El proyecto completo (código TypeScript, `package.json`, `tsconfig.json`, etc.).
2. Un fichero `README.md` en el que expliques:
   - Cómo se ejecuta el proyecto.
   - Cómo está organizada la estructura de carpetas.
   - Qué endpoints remotos utilizas y cómo se configuran (URL base).
   - Cómo funciona la sincronización remoto ↔ local (al menos remoto → local).
3. Tests automáticos que verifiquen la lógica de tu servicio o repositorios.

¡Ya estás haciendo aplicaciones con base de datos real! Recuerda seguir sin la IA 🚀

</div>
