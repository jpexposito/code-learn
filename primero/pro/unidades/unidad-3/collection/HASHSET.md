<div align="justify">

# <img src=../../../../../images/coding-book.png width="40"> Code & Learn (HASHSET)

## Descripción

El `HashSet` es una clase en Java que implementa la interfaz `Set`. Utiliza una tabla hash para almacenar elementos, garantizando que no haya duplicados y sin garantizar el orden de los elementos.

---

## Características

- **No permite duplicados:** Solo se almacenan elementos únicos.
- **Orden no garantizado:** El orden de los elementos no es predecible.
- **Basado en hashing:** Los elementos son almacenados según su código hash.
- **Permite un único elemento `null`.**
- **Operaciones eficientes:** Las operaciones como agregar, eliminar y buscar tienen una complejidad promedio de \(O(1)\).

---

## Constructores del HashSet

| Constructor                     | Descripción                                                                 |
|----------------------------------|-----------------------------------------------------------------------------|
| `HashSet()`                      | Crea un HashSet vacío con capacidad inicial predeterminada (16) y factor de carga predeterminado (0.75). |
| `HashSet(int initialCapacity)`   | Crea un HashSet con una capacidad inicial especificada y un factor de carga predeterminado. |
| `HashSet(int initialCapacity, float loadFactor)` | Crea un HashSet con capacidad inicial y factor de carga especificados. |
| `HashSet(Collection<? extends E> c)` | Crea un HashSet que contiene los elementos de la colección especificada. |

---

### Capacidad inicial

La **capacidad inicial** es el número de "cubetas" (*buckets*) que el `HashSet` tiene al momento de su creación. Estas cubetas son posiciones en la tabla hash donde se almacenan los elementos.

- **Por defecto:** La capacidad inicial es **16**.
- Cada vez que un elemento se agrega al `HashSet`, su código hash (`hashCode`) se utiliza para determinar en qué cubeta almacenar ese elemento.
- Si el número de elementos en el `HashSet` supera la capacidad multiplicada por el **factor de carga**, la tabla hash se redimensiona automáticamente.

### Ejemplo

```java
HashSet<Integer> set = new HashSet<>(16); // Capacidad inicial de 16
```

## Factor de carga en un HashSet

El **factor de carga** es un valor que define qué tan llena puede estar una tabla hash antes de que se redimensione automáticamente. 

- **Por defecto:** El factor de carga es **0.75**.
- Este valor indica que cuando el `HashSet` esté al **75% de su capacidad**, se creará una nueva tabla hash más grande y todos los elementos existentes serán redistribuidos en esta nueva tabla (proceso llamado *rehashing*).

### Fórmula

El número máximo de elementos que pueden almacenarse antes de redimensionar se calcula con la siguiente fórmula:

[`{Capacidad actual}`**\***`{Factor de carga}`]

Por ejemplo:

- Si la capacidad inicial es **16** y el factor de carga es **0.75**, el redimensionamiento ocurrirá cuando se inserten **12 elementos** (\(16 \times 0.75 = 12\)).

---

### Ventajas y desventajas de diferentes factores de carga

- **Factor de carga alto (e.g., 0.9):**
  - Reduce el número de redimensionamientos.
  - Aumenta las colisiones, lo que puede ralentizar las búsquedas y actualizaciones.
- **Factor de carga bajo (e.g., 0.5):**
  - Minimiza las colisiones, mejorando el rendimiento de las operaciones.
  - Incrementa el consumo de memoria porque la tabla hash se redimensiona más rápido.

---

### Ejemplo

```java
import java.util.HashSet;

public class FactorDeCargaExample {
    public static void main(String[] args) {
        // HashSet con capacidad inicial 4 y factor de carga 0.75
        HashSet<Integer> set = new HashSet<>(4, 0.75f);

        // Agregar elementos
        set.add(1);
        set.add(2);
        set.add(3);

        // Al agregar el cuarto elemento, se alcanza el 75% de la capacidad,
        // por lo que se realiza un redimensionamiento.
        set.add(4);

        System.out.println("Elementos en el HashSet: " + set);
    }
}
```

## Métodos principales

| Método                   | Descripción                                                                                 |
|--------------------------|---------------------------------------------------------------------------------------------|
| `boolean add(E e)`       | Agrega el elemento al conjunto si no está presente.                                         |
| `boolean remove(Object o)` | Elimina el elemento especificado del conjunto.                                             |
| `boolean contains(Object o)` | Verifica si el conjunto contiene el elemento especificado.                                |
| `void clear()`           | Elimina todos los elementos del conjunto.                                                  |
| `int size()`             | Devuelve el número de elementos en el conjunto.                                            |
| `Iterator<E> iterator()` | Devuelve un iterador para recorrer los elementos del conjunto.                              |
| `boolean isEmpty()`      | Verifica si el conjunto está vacío.                                                         |

---

### Ejemplo de uso

```java
import java.util.HashSet;

public class HashSetExample {
    public static void main(String[] args) {
        // Crear un HashSet
        HashSet<String> set = new HashSet<>();

        // Agregar elementos
        set.add("Manzana");
        set.add("Banana");
        set.add("Naranja");

        // Intentar agregar un duplicado
        boolean isAdded = set.add("Manzana");
        System.out.println("¿Se agregó 'Manzana' de nuevo? " + isAdded); // false

        // Verificar si contiene un elemento
        System.out.println("¿Contiene 'Banana'? " + set.contains("Banana")); // true

        // Imprimir los elementos
        System.out.println("Elementos en el conjunto: " + set);

        // Eliminar un elemento
        set.remove("Naranja");
        System.out.println("Después de eliminar 'Naranja': " + set);

        // Verificar tamaño
        System.out.println("Tamaño del conjunto: " + set.size());
    }
}
```

Cuando trabajas con un `HashSet` en Java, es importante entender cómo se organiza internamente. Estas dos propiedades afectan el rendimiento y el consumo de memoria:

---

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../../../../../../LICENSE) para detalles.

</div>