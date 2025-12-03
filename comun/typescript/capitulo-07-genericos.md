<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (Capítulo 7: Genéricos (Generics))

Los **genéricos** permiten escribir código reutilizable que funciona con múltiples tipos, manteniendo el tipado fuerte.

---

## Funciones genéricas

### Explicación

Una función genérica usa parámetros de tipo, como `<T>`, para indicar que su comportamiento es independiente del tipo concreto.

### Ejemplo

```ts
// ejemplos/06-genericos.ts
function identidad<T>(valor: T): T {
  return valor;
}

const numero = identidad<number>(42);
const texto = identidad<string>("hola");

console.log(numero, texto);
```

### Cambios propuestos

1. Crea una función genérica `envolverEnArray<T>(valor: T): T[]` que devuelva un array con ese valor.
2. Llama a la función con diferentes tipos (`number`, `string`, un objeto) y comprueba el tipado.

---

## Interfaces y clases genéricas

### Explicación

Las **interfaces** y **clases** también pueden ser genéricas, permitiendo definir estructuras más flexibles.

### Ejemplo

```ts
// ejemplos/06-genericos.ts
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
```

### Cambios propuestos

1. Crea una `Caja` de `string` y otra de un objeto (por ejemplo, un usuario).
2. Añade un método `actualizar(nuevoContenido: T)` a la clase `Caja` y pruébalo.

---

## `Promise<T>` como ejemplo

### Explicación

`Promise<T>` es un ejemplo muy común de tipo genérico: la promesa resuelve a un valor de tipo `T`.

### Ejemplo

```ts
// ejemplos/06-genericos.ts
function obtenerNumeroAsync(): Promise<number> {
  return new Promise((resolve) => {
    setTimeout(() => resolve(42), 1000);
  });
}

obtenerNumeroAsync().then((n) => {
  console.log("Número obtenido:", n);
});
```

### Cambios propuestos

1. Crea una función `obtenerUsuarioAsync(): Promise<{ id: number; nombre: string }>` y muéstralo por consola.
2. Usa `async/await` en lugar de `.then()` para consumir `obtenerNumeroAsync`.

## Relación con Java y Python

Los **genéricos** de TypeScript se parecen mucho a los de **Java**:

- `Array<T>` en TypeScript es muy similar a `List<T>` en Java.
- Una función genérica:

```ts
function primero<T>(items: T[]): T | undefined {
  return items[0];
}
```

se parece a una versión genérica en Java:

```java
public static <T> T primero(List<T> items) {
    return items.isEmpty() ? null : items.get(0);
}
```

En **Python**, algo parecido se consigue con los genéricos de `typing`:

```python
from typing import TypeVar, List, Optional

T = TypeVar("T")

def primero(items: List[T]) -> Optional[T]:
    return items[0] if items else None
```

La idea en los tres lenguajes es la misma:  
**reusar lógica cambiando el tipo de los datos**, manteniendo la seguridad de tipos.

---

[⬅ Capítulo 6: Clases y Programación Orientada a Objetos en TypeScript](./capitulo-06-clases-y-poo.md) · [Ir al índice](./README.md) · [Capítulo 8: Módulos y organización del código ➡](./capitulo-08-modulos-y-configuracion.md)

</div>