// ejemplos/04-interfaces.ts

// --- interface vs type ---
interface Usuario {
  id: number;
  nombre: string;
}

type UsuarioTipo = {
  id: number;
  nombre: string;
};

const uInterface: Usuario = { id: 1, nombre: "Ana" };
const uTipo: UsuarioTipo = { id: 2, nombre: "Luis" };

// --- Extensión de interfaces ---
interface UsuarioBase {
  id: number;
  nombre: string;
}

interface Admin extends UsuarioBase {
  permisos: string[];
}

const admin: Admin = {
  id: 1,
  nombre: "Admin",
  permisos: ["usuarios:leer", "usuarios:editar"],
};

// --- Tipos indexados ---
interface DiccionarioDePrecios {
  [producto: string]: number;
}

const precios: DiccionarioDePrecios = {
  manzana: 1.2,
  pera: 1.5,
};

precios.naranja = 1.8;

console.log("Precio manzana:", precios.manzana);
