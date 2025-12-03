<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (Capítulo 6: Clases y Programación Orientada a Objetos en TypeScript)

---

## `class`, `constructor`

### Explicación

Las **clases** permiten definir plantillas para crear objetos con propiedades y métodos.  
El **constructor** es un método especial que se ejecuta al crear una instancia.

### Ejemplo

```ts
// ejemplos/05-clases.ts
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
```

### Cambios propuestos

1. Añade una propiedad `email` a la clase `Persona` y muéstrala en el método `saludar`.
2. Crea un método `cumplirAnios()` que aumente en 1 la edad.

---

## Modificadores (`public`, `private`, `protected`, `readonly`)

### Explicación

- `public`: accesible desde cualquier parte (por defecto).
- `private`: solo accesible dentro de la clase.
- `protected`: accesible dentro de la clase y de sus subclases.
- `readonly`: solo se puede asignar en la declaración o en el constructor.

### Ejemplo

```ts
// ejemplos/05-clases.ts
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
```

### Cambios propuestos

1. Intenta modificar `numeroCuenta` fuera de la clase y observa el error de `readonly`.
2. Cambia `saldo` a `protected` y crea una subclase que pueda acceder directamente a esa propiedad.

---

## Herencia

### Explicación

La **herencia** permite que una clase (subclase) extienda otra (superclase), reutilizando y ampliando su funcionalidad.

### Ejemplo

```ts
// ejemplos/05-clases.ts
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
mascota.hacerSonido(); // "Guau!"
```

### Cambios propuestos

1. Crea una nueva subclase `Gato` que sobrescriba `hacerSonido()` para decir `"Miau"`.
2. Añade un método `presentarse()` en la superclase `Animal` que muestre el nombre y pruébalo en `Perro` y `Gato`.

---

## Clases abstractas

### Explicación

Una **clase abstracta** no se puede instanciar directamente; sirve como base para otras clases y puede contener métodos abstractos que deben implementarse en las subclases.

### Ejemplo

```ts
// ejemplos/05-clases.ts
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
console.log(rect.area());
```

### Cambios propuestos

1. Crea una clase `Circulo` que extienda `Figura` y defina el método `area()` usando `PI * r^2`.
2. Añade un método abstracto `perimetro(): number` a `Figura` e impleméntalo en `Rectangulo` y `Circulo`.

## Relación con Java y Python

Las clases de TypeScript están muy inspiradas en **Java**:

- Tienen `constructor`, modificadores de acceso (`public`, `private`, `protected`), herencia (`extends`) e implementación de interfaces (`implements`).
- La sintaxis es casi idéntica:

```ts
class Persona {
  constructor(public nombre: string, private edad: number) {}

  saludar(): void {
    console.log(`Hola, soy ${this.nombre}`);
  }
}
```

```java
public class Persona {
    private int edad;
    public String nombre;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public void saludar() {
        System.out.println("Hola, soy " + this.nombre);
    }
}
```

En **Python**, una clase equivalente sería:

```python
class Persona:
    def __init__(self, nombre: str, edad: int) -> None:
        self.nombre = nombre
        self.edad = edad

    def saludar(self) -> None:
        print(f"Hola, soy {self.nombre}")
```

Diferencias clave:

- En TypeScript, las clases solo existen en tiempo de ejecución como JavaScript; los **tipos** se usan en tiempo de compilación.
- En Python, el tipado es opcional y se comprueba (si se hace) con herramientas externas como `mypy`.
- En Java, el tipado es obligatorio y el bytecode resultante mantiene la información de clases para el runtime (JVM).

---

[⬅ Capítulo 5: Interfaces y tipos avanzados de objetos](./capitulo-05-interfaces-y-objetos-avanzados.md) · [Ir al índice](./README.md) · [Capítulo 7: Genéricos (Generics) ➡](./capitulo-07-genericos.md)

</div>