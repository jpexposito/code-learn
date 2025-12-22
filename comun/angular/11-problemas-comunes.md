<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (11. Problemas y soluciones para Angular)

Este documento recoge los **errores más habituales al desarrollar aplicaciones Angular** y sus soluciones, especialmente en entornos de **local de desarrollo**.

---

## 11.1 Los cambios en HTML o CSS no se reflejan en el navegador

### Síntomas

- Se modifica un archivo `.html` o `.css`
- El navegador **no se actualiza**
- No aparece ningún error en consola

### Solución recomendada

Arrancar el servidor forzando la detección de cambios:

```bash
ng serve --poll=2000 --live-reload
```

> ***Alternativa***
>
>Borrar la caché local del proyecto:
>
> ```bash
>rm -rf .angular
>ng serve
>```

---

## 11.2 Errores relacionados con caché o versiones

### Ejemplos de mensajes

- `outdated request`
- referencias a `.angular/cache/21.x.x`
- errores de Vite al cargar dependencias

### Causa

Caché corrupta o mezcla de versiones de Angular CLI.

### Solución

```bash
rm -rf .angular
rm -rf ~/.angular/cache
rm -rf node_modules
npm install
ng serve
```

---

## 11.3 El componente existe pero Angular no detecta el HTML o CSS

### Síntomas

- Los archivos `.ts`, `.html` y `.css` existen
- Angular no aplica los cambios
- El editor muestra avisos

### Comprobaciones

1. Los archivos están en la **misma carpeta**
2. Las rutas son relativas y correctas
3. El componente está bien definido

Ejemplo correcto (Angular moderno):

```ts
@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
```

> En Angular moderno se recomienda `styleUrl` (singular) si solo hay un CSS.

---

## 11.4 Error “is not a module”

### Ejemplo

```
app.component.ts is not a module
```

### Causas habituales
- El archivo está vacío
- Falta `export class`
- Hay restos de un conflicto (`<<<<<<< ======= >>>>>>>`)

### Solución
Asegurarse de que el archivo contiene:

```ts
export class AppComponent {}
```

---

## 11.5 `ng new` no funciona (workspace)

### Mensaje

```
This command is not available when running the Angular CLI inside a workspace
```

### Causa

El comando se está ejecutando dentro de otro proyecto Angular.

### Solución

Salir a una carpeta superior o crear el proyecto en otra ruta:

```bash
cd ..
ng new gestor-tareas
```

---

## 11.6 El editor muestra errores pero la aplicación funciona

### Causa

Angular Language Service desincronizado.

### Solución

En VS Code:
1. `Cmd + Shift + P`
2. Ejecutar **Developer: Reload Window**

O cerrar y volver a abrir el editor.

---

## 11.7 Nada funciona y no sabes por qué

### Solución de emergencia (reset total)

```bash
rm -rf node_modules .angular ~/.angular/cache
npm install
ng serve
```

> Esta solución es segura y suele arreglar la mayoría de los problemas de entorno.

---

## ✅ Buenas prácticas para evitar errores

- Ejecutar siempre `ng serve` desde la **raíz del proyecto**
- No mezclar versiones de Angular CLI
- Usar el CLI local del proyecto (`npx ng serve`)
- Guardar los archivos antes de probar
- **Importante**: _Borrar la cache y reiniciar el servidor si algo no tiene sentido_.

---

## Importante

> “La mayoría de los errores en Angular no son de código,  
> sino de **entorno, caché o configuración**.”

</div>