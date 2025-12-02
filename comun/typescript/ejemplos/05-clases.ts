// ejemplos/05-clases.ts

// --- class, constructor ---
class Persona {
  nombre: string;
  edad: number;

  constructor(nombre: string, edad: number) {
    this.nombre = nombre;
    this.edad = edad;
  }

  saludar(): void {
    console.log(`Hola, soy ${this.nombre} y tengo ${this.edad} años.`);
  }
}

const persona = new Persona("Ana", 25);
persona.saludar();

// --- Modificadores ---
class CuentaBancaria {
  public readonly numeroCuenta: string;
  private saldo: number;

  constructor(numeroCuenta: string, saldoInicial: number) {
    this.numeroCuenta = numeroCuenta;
    this.saldo = saldoInicial;
  }

  public ingresar(cantidad: number): void {
    this.saldo += cantidad;
  }

  public retirar(cantidad: number): void {
    if (cantidad > this.saldo) {
      console.log("Fondos insuficientes");
      return;
    }
    this.saldo -= cantidad;
  }

  public obtenerSaldo(): number {
    return this.saldo;
  }
}

// --- Herencia ---
class Animal {
  constructor(public nombre: string) {}

  hacerSonido(): void {
    console.log("Sonido genérico");
  }
}

class Perro extends Animal {
  hacerSonido(): void {
    console.log("Guau!");
  }
}

const mascota = new Perro("Firulais");
mascota.hacerSonido();

// --- Clases abstractas ---
abstract class Figura {
  abstract area(): number;

  descripcion(): void {
    console.log("Soy una figura geométrica.");
  }
}

class Rectangulo extends Figura {
  constructor(private ancho: number, private alto: number) {
    super();
  }

  area(): number {
    return this.ancho * this.alto;
  }
}

const rect = new Rectangulo(10, 5);
rect.descripcion();
console.log("Área rectángulo:", rect.area());
