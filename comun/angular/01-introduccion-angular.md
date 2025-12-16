<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (1. Introducción a Angular)

## 1.1. ¿Qué es Angular?

**Angular** es un framework para construir **Single Page Applications (SPA)**
del lado del cliente usando **TypeScript**.

Características principales:

- Arquitectura basada en **componentes**.
- Sistema de **módulos** para organizar la app.
- **Servicios** e **inyección de dependencias (DI)** para lógica compartida.
- Soporte integrado para **formularios**, **HTTP**, **routing**, **testing**, etc.

En una SPA:

- El navegador descarga una **app JavaScript**.
- Navegar entre pantallas no recarga toda la página, solo cambia la vista.

---

## 1.2. Arquitectura general

Piensa en Angular como varias piezas que encajan:

- **Módulos (`@NgModule`)**: agrupan componentes, servicios, pipes, etc.
- **Componentes (`@Component`)**: bloques de UI + lógica asociada.
- **Servicios (`@Injectable`)**: lógica de negocio, acceso a datos, utilidades.
- **Routing**: define las rutas (`/usuarios`, `/tareas`, etc.) y qué componente
  se muestra para cada una.
- **Formularios**: manejo de entrada de datos (template-driven o reactivos).
- **HTTP**: `HttpClient` para consumir APIs REST.

En el micro-proyecto que construiremos:

- Un módulo principal `AppModule`.
- Un módulo de funcionalidad `TasksModule` (opcional) o simplemente una carpeta
  `tasks/` con componentes y servicios.
- Un servicio `TaskApiService` que habla con la API REST en Spring Boot.
- Componentes para listar, crear y editar tareas.

---

## 1.3. Crear el primer proyecto con Angular CLI

Comando básico:

```bash
ng new gestor-tareas
cd gestor-tareas
ng serve
```

Esto genera una estructura inicial:

```text
src/
├─ main.ts
├─ index.html
└─ app/
   ├─ app.module.ts
   ├─ app.component.ts
   ├─ app.component.html
   └─ ...
```

- `main.ts`: punto de entrada, arranca el módulo raíz (AppModule).
- `app.module.ts`: módulo raíz de la aplicación.
- `app.component.ts`: primer componente que se muestra.
- `app.component.html`: plantilla asociada al `AppComponent`.

---

## 1.4. Flujo de arranque

1. `main.ts` llama a `bootstrapApplication` (o a `platformBrowserDynamic().bootstrapModule(AppModule)`
   según versión) para arrancar la app.
2. Se carga `AppModule`, que declara `AppComponent` como componente de arranque.
3. El `selector` de `AppComponent` (por defecto `<app-root>`) se inserta en `index.html`.
4. Todo lo demás se construye a partir de ahí (routing, otros componentes, etc.).

Entender este flujo te ayuda a ubicar dónde empieza realmente tu aplicación.

</div>
