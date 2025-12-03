<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (Capítulo 5: Interfaces y tipos avanzados de objetos)

---

## `interface` vs `type`

### Explicación

Tanto `interface` como `type` permiten describir la forma de un objeto.  
Diferencias principales:

- `interface` está pensada sobre todo para describir **objetos** y se puede **extender** fácilmente.
- `type` es más genérico: puede representar uniones, intersecciones, alias a tipos primitivos, etc.

### Ejemplo

```ts
// ejemplos/04-interfaces.ts
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
```

### Cambios propuestos

1. Añade una nueva propiedad `email: string` tanto a `Usuario` como a `UsuarioTipo`.
2. Crea una función que reciba un `Usuario` y muestre su nombre por consola.

---

## Extensión de interfaces

### Explicación

Las interfaces pueden **extenderse** para reutilizar propiedades y añadir nuevas.

### Ejemplo

```ts
// ejemplos/04-interfaces.ts
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
```

### Cambios propuestos

1. Crea una nueva interfaz `Cliente` que extienda `UsuarioBase` con una propiedad `fechaAlta: Date`.
2. Añade un método `tienePermiso(permiso: string): boolean` a `Admin` e implementa una función que use ese método.

---

## Tipos indexados (index signatures)

### Explicación

Los **tipos indexados** permiten describir objetos con claves dinámicas, todas del mismo tipo de valor.

### Ejemplo

```ts
// ejemplos/04-interfaces.ts
interface DiccionarioDePrecios {
  [producto: string]: number;
}

const precios: DiccionarioDePrecios = {
  manzana: 1.2,
  pera: 1.5,
};

precios.naranja = 1.8;
console.log(precios.manzana);
```

### Cambios propuestos

1. Añade un nuevo producto al diccionario y muestra todos los precios.
2. Cambia el tipo de valor a `number | undefined` y maneja el caso en el que un producto no exista.

## Relación con Java y Python

- Una `interface` en **TypeScript** se parece mucho a una `interface` en **Java**:
  - Define la **forma** que deben cumplir las clases o los objetos.
  - Puede ser implementada por varias clases.

  ```ts
  interface Usuario {
    id: number;
    nombre: string;
  }
  ```

  ```java
  public interface Usuario {
      int getId();
      String getNombre();
  }
  ```

- En **Python**, no hay interfaces formales en el lenguaje básico, pero la idea se parece a:
  - Clases base abstractas (`abc.ABC`).
  - Protocolos de `typing` (`Protocol`) en código moderno con anotaciones.

Una diferencia importante:

- En **Java**, el tipado de interfaces es **nominal**: una clase implementa explícitamente `implements Usuario`.
- En **TypeScript**, el tipado es **estructural**: cualquier objeto que tenga las propiedades `id: number` y `nombre: string` es válido como `Usuario`, aunque no haya declarado que “implementa” esa interfaz.

Esto hace que las interfaces en TypeScript se parezcan mucho a la idea de “duck typing” de Python pero con comprobación en tiempo de compilación.

---

[⬅ Capítulo 4: Funciones](./capitulo-04-funciones.md) · [Ir al índice](./README.md) · [Capítulo 6: Clases y Programación Orientada a Objetos en TypeScript ➡](./capitulo-06-clases-y-poo.md)

</div>