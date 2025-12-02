// ejemplos/06-genericos.ts

// --- Funciones genéricas ---
function identidad<T>(valor: T): T {
  return valor;
}

const numero = identidad<number>(42);
const texto = identidad<string>("hola");

console.log(numero, texto);

// --- Interfaces y clases genéricas ---
interface ApiResponse<T> {
  datos: T;
  exito: boolean;
}

const respuestaUsuarios: ApiResponse<string[]> = {
  datos: ["Ana", "Luis"],
  exito: true,
};

class Caja<T> {
  private contenido: T;

  constructor(contenido: T) {
    this.contenido = contenido;
  }

  obtener(): T {
    return this.contenido;
  }
}

const cajaNumero = new Caja<number>(123);
console.log(cajaNumero.obtener());

// --- Promise<T> ---
function obtenerNumeroAsync(): Promise<number> {
  return new Promise((resolve) => {
    setTimeout(() => resolve(42), 1000);
  });
}

obtenerNumeroAsync().then((n) => {
  console.log("Número obtenido:", n);
});
