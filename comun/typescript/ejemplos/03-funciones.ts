// ejemplos/03-funciones.ts

// --- Tipado de parámetros y retorno ---
function sumar(a: number, b: number): number {
  return a + b;
}

const resultado = sumar(2, 3);
console.log("Resultado sumar:", resultado);

// --- Parámetros opcionales y por defecto ---
function saludar(nombre: string, saludo?: string): void {
  const mensaje = saludo ?? "Hola";
  console.log(`${mensaje}, ${nombre}`);
}

function incrementar(valor: number, paso: number = 1): number {
  return valor + paso;
}

saludar("Ana");
saludar("Luis", "Buenas tardes");

console.log(incrementar(10));
console.log(incrementar(10, 5));

// --- Funciones flecha ---
const multiplicar = (a: number, b: number): number => {
  return a * b;
};

const alCuadrado = (x: number): number => x * x;

console.log(multiplicar(2, 3));
console.log(alCuadrado(4));

// --- Overloads (sobrecarga de funciones) ---
function formatear(valor: number): string;
function formatear(valor: Date): string;

function formatear(valor: number | Date): string {
  if (typeof valor === "number") {
    return valor.toFixed(2);
  } else {
    return valor.toISOString();
  }
}

console.log(formatear(3.14159));
console.log(formatear(new Date()));
