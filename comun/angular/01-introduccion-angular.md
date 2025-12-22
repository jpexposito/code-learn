<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (1. Introducción a Angular)

<div align="center">
  <img src=images/1-introduccion.png width="350">
</div>

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

## 1.3.1. Configuración del sistemas de estilos en Angular

Obtendremos:

```bash
? Which stylesheet system would you like to use? ❯
 CSS [ https://developer.mozilla.org/docs/Web/CSS ] 
 Tailwind CSS [ https://tailwindcss.com ] 
 Sass (SCSS) [ https://sass-lang.com/documentation/syntax#scss ]
 Sass (Indented) [ https://sass-lang.com/documentation/syntax#the-indented-syntax ]
 Less [ http://lesscss.org
```

### Sistemas de estilos en Angular

Al crear un proyecto con Angular CLI, se debe elegir el sistema de estilos que se utilizará en la aplicación. Esta elección **no afecta al funcionamiento de Angular**, solo a la forma en que se escriben los estilos visuales.

---

### CSS (recomendado al inicio)

- CSS puro
- Sin preprocesadores

#### Inconveniente

- Menos potente para proyectos grandes o muy complejos

---

### Sass (SCSS)

- CSS con funcionalidades adicionales:
  - Variables.
  - Anidación de estilos.
  - Mixins y reutilización de código.

##### Ventajas

- Muy utilizado en entornos profesionales.
- Totalmente compatible con CSS estándar.

---

### Tailwind CSS

- Framework de utilidades CSS.
- Los estilos se escriben directamente en el HTML mediante clases.

### Ventajas

- Muy demandado en el mercado laboral.
- Permite diseñar interfaces rápidamente.

### Inconvenientes

- Mezcla estructura (HTML) y estilo.
- Puede dificultar la comprensión inicial de Angular.

---

### Sass (Indented)

- Sintaxis antigua de Sass sin llaves ni punto y coma

> **Nota**:  _Prácticamente no se usa en la actualidad_.

---

### Less

- Preprocesador CSS alternativo a Sass

> **Nota**: _Poco utilizado hoy en proyectos Angular modernos_.

---

## 1.3.2. Configuración de “agentes de IA” en `ng new` (Angular CLI 21)

Al crear un proyecto con Angular CLI 21, aparece esta pregunta:

> **Which AI tools do you want to configure with Angular best practices?**  
> (Más info: guía oficial) :contentReference[oaicite:0]{index=0}

### ¿Qué significa esta opción?

Angular CLI **no instala** herramientas de IA, ni “activa” ninguna IA por sí mismo.

Lo que hace es **generar un archivo de instrucciones** (un prompt/guía) para que, si usas una herramienta de IA (Copilot, Cursor, Claude, etc.), esa herramienta lea el archivo y **genere código siguiendo buenas prácticas oficiales de Angular**. :contentReference[oaicite:1]{index=1}

> En resumen: **solo crea archivos de texto con instrucciones** para asistentes de IA.

---

## Opciones del menú (qué son y cuándo usarlas)

### ◉ None (Utilizaremos esta)
- **No se genera ningún archivo** de instrucciones para IA. _El proyecto queda más limpio_.

---

### ◯ Agents.md (estándar abierto)

Genera un **`AGENTS.md`** en la raíz del repositorio con instrucciones para agentes de IA.

- `AGENTS.md` es un formato abierto y predecible para “dar contexto” a agentes de IA en proyectos. :contentReference[oaicite:2]{index=2}

---

### ◯ Claude

Genera un archivo de instrucciones para **Claude Code** (Anthropic), en la ubicación esperada por esa herramienta (p.ej. carpeta `.claude/`). :contentReference[oaicite:3]{index=3}

---

### ◯ Cursor

Genera reglas para **Cursor** (editor con IA), típicamente en la ubicación de reglas de Cursor (por ejemplo `.cursor/rules`). :contentReference[oaicite:4]{index=4}

---

### ◯ Gemini

Genera un archivo de instrucciones para **Gemini**, con la ubicación/nombre que ese ecosistema reconoce (por ejemplo `.gemini/…`). :contentReference[oaicite:5]{index=5}

---

### ◯ GitHub Copilot

Genera instrucciones para Copilot en el formato típico de VS Code/GitHub, como un archivo tipo:

- `.github/copilot-instructions.md` (según convención de Copilot/VS Code). :contentReference[oaicite:6]{index=6}

---

### ◯ JetBrains AI

Genera instrucciones para los asistentes IA del entorno JetBrains (según sus guías de “guidelines”/customization). :contentReference[oaicite:7]{index=7}

---


> _Angular CLI usa un esquema interno (“ai-config”) que define estas opciones y genera archivos distintos según herramienta_, pero **el contenido base (buenas prácticas Angular) es el mismo**. :contentReference[oaicite:8]{index=8}

---


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
