// ejemplos/01-tipos-basicos.ts

// --- ¿Qué es TypeScript? ---
let mensaje: string = "Hola TypeScript";
let veces: number = 3;

for (let i = 0; i < veces; i++) {
  console.log(mensaje);
}

// --- Tipos básicos: number, string, boolean ---
let edad: number = 30;
let nombre: string = "Lucía";
let esActivo: boolean = true;

console.log(`Usuario: ${nombre}, edad: ${edad}, activo: ${esActivo}`);

// --- null, undefined, any, unknown, never ---
let valorNulo: null = null;
let valorIndefinido: undefined = undefined;

let datoFlexible: any = "hola";
datoFlexible = 123; // permitido, pero peligroso

let datoDesconocido: unknown = "podría ser cualquier cosa";

function lanzarError(mensaje: string): never {
  throw new Error(mensaje);
}

if (typeof datoDesconocido === "string") {
  console.log(datoDesconocido.toUpperCase());
}

// --- Inferencia de tipos ---
let contador = 0; // number
contador += 1;
// contador = "uno"; // ❌ Error si descomentas

const PI = 3.1416;
