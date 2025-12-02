// ejemplos/02-tipos-compuestos.ts

// --- Arrays y tuplas ---
let numeros: number[] = [1, 2, 3];
let nombres: Array<string> = ["Ana", "Luis", "Marta"];

let tuplaUsuario: [string, number] = ["Ana", 30];

console.log(numeros[0]);
console.log(tuplaUsuario[0]);

// --- Objetos ---
let usuario: {
  nombre: string;
  edad: number;
  activo: boolean;
} = {
  nombre: "Carlos",
  edad: 28,
  activo: true,
};

console.log(usuario.nombre);

// --- Uniones e intersecciones ---
type Id = number | string;

let id1: Id = 123;
let id2: Id = "abc-123";

type ConFecha = { creadoEn: Date };
type ConId = { id: number };

type EntidadConIdYFecha = ConFecha & ConId;

const registro: EntidadConIdYFecha = {
  id: 1,
  creadoEn: new Date(),
};

// --- Type aliases ---
type UsuarioBasico = {
  id: number;
  nombre: string;
  email: string;
};

let u1: UsuarioBasico = {
  id: 1,
  nombre: "Ana",
  email: "ana@example.com",
};
