<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (Capítulo 4: Funciones)

---

## Tipado de parámetros y retorno

### Explicación

En TypeScript se pueden (y se recomienda) tipar tanto los **parámetros** de una función como su **valor de retorno**.

### Ejemplo

```ts
// ejemplos/03-funciones.ts
function sumar(a: number, b: number): number {
  return a + b;
}

const resultado = sumar(2, 3);
console.log(resultado); // 5
```

### Cambios propuestos

1. Cambia el tipo de retorno de `sumar` a `string` y ajusta la función para que devuelva un mensaje como `"Resultado: 5"`.
2. Intenta llamar a `sumar` con un solo argumento y observa el error de compilación.

---

## Parámetros opcionales y por defecto

### Explicación

- Los **parámetros opcionales** se marcan con `?`.
- Los **parámetros por defecto** tienen un valor asignado si no se pasa ninguno.

### Ejemplo

```ts
// ejemplos/03-funciones.ts
function saludar(nombre: string, saludo?: string): void {
  const mensaje = saludo ?? "Hola";
  console.log(`${mensaje}, ${nombre}`);
}

function incrementar(valor: number, paso: number = 1): number {
  return valor + paso;
}

saludar("Ana");
saludar("Luis", "Buenas tardes");

console.log(incrementar(10));    // 11
console.log(incrementar(10, 5)); // 15
```

### Cambios propuestos

1. Haz que `saludar` tenga un tercer parámetro opcional `enMayusculas?: boolean` que, si es `true`, convierta el mensaje a mayúsculas.
2. Cambia el valor por defecto de `paso` en `incrementar` a `2` y comprueba el resultado.

---

## Funciones flecha

### Explicación

Las **funciones flecha** (`=>`) son una sintaxis más concisa. También tienen un comportamiento especial respecto a `this`, pero aquí nos centraremos en el tipado.

### Ejemplo

```ts
// ejemplos/03-funciones.ts
const multiplicar = (a: number, b: number): number => {
  return a * b;
};

const alCuadrado = (x: number): number => x * x;

console.log(multiplicar(2, 3)); // 6
console.log(alCuadrado(4));     // 16
```

### Cambios propuestos

1. Crea una función flecha `esPar` que reciba un `number` y devuelva un `boolean`.
2. Reescribe `sumar` del primer ejemplo como función flecha.

---

## Overloads (sobrecarga de funciones)

### Explicación

La **sobrecarga de funciones** permite definir **una función** con tipados distintos, pero una única implementación.

### Ejemplo

```ts
// ejemplos/03-funciones.ts
function formatear(valor: number): string;
function formatear(valor: Date): string;

function formatear(valor: number | Date): string {
  if (typeof valor === "number") {
    return valor.toFixed(2);
  } else {
    return valor.toISOString();
  }
}

console.log(formatear(3.14159));     // "3.14"
console.log(formatear(new Date()));  // fecha en ISO
```

### Cambios propuestos

1. Añade una nueva sobrecarga de `formatear` para `string`, que devuelva el texto en mayúsculas.
2. Intenta llamar a `formatear(true)` y observa cómo TypeScript lo rechaza.

## Relación con Java y Python

- Una función en **TypeScript**:

  ```ts
  function sumar(a: number, b: number): number {
    return a + b;
  }
  ```

  se parece a:

  - En **Java** (como método estático):

    ```java
    static int sumar(int a, int b) {
        return a + b;
    }
    ```

  - En **Python** (con anotaciones de tipos):

    ```python
    def sumar(a: int, b: int) -> int:
        return a + b
    ```

- Las **funciones flecha** (`(a, b) => a + b`) se parecen a:
  - **Lambdas en Java**: `(a, b) -> a + b` usadas en streams o funciones de orden superior.
  - **Funciones lambda en Python**: `lambda a, b: a + b`.

La diferencia principal es que en TypeScript es muy habitual tratar las funciones como **valores** (funciones de orden superior, callbacks), igual que en JavaScript y Python, pero con el añadido del **tipado estático**.

---

[⬅ Capítulo 3: Tipos compuestos](./capitulo-03-tipos-compuestos.md) · [Ir al índice](./README.md) · [Capítulo 5: Interfaces y tipos avanzados de objetos ➡](./capitulo-05-interfaces-y-objetos-avanzados.md)

</div>