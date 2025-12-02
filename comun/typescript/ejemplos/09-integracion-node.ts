// ejemplos/09-integracion-node.ts

import fs from "fs";

function leerArchivo(ruta: string): string {
  return fs.readFileSync(ruta, "utf-8");
}

const contenido = leerArchivo(__filename);
console.log("Este archivo contiene", contenido.length, "caracteres.");
