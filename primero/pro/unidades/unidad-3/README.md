<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Estructuras de almacenamiento)

El árbol de dependencias de las __interfaces__ de __colecciones__ y __mapas__ se organiza de manera __jerárquica__. A continuación, te proporcionaré un esquema básico del árbol de dependencias:

```mathematica
java.util.Collection
├── java.util.List
│   ├── java.util.ArrayList
│   └── java.util.LinkedList
├── java.util.Set
│   ├── java.util.HashSet
│   ├── java.util.LinkedHashSet
│   └── java.util.TreeSet
└── java.util.Queue
    └── java.util.LinkedList

java.util.Map
├── java.util.HashMap
├── java.util.Hashtable
├── java.util.LinkedHashMap
├── java.util.TreeMap
└── java.util.IdentityHashMap
```

| **Interfaz/Clase Principal** | **Subinterfaces** / **Implementaciones**                     | **Descripción**                                             |
|------------------------------|------------------------------------------------------------|-------------------------------------------------------------|
| **`java.util.Collection`**   | **`java.util.List`**                                       | Colección ordenada, permite duplicados                      |
|                              | - `java.util.ArrayList`                                    | Lista basada en array dinámico                              |
|                              | - `java.util.LinkedList`                                   | Lista doblemente enlazada                                   |
|                              | - `java.util.Vector`                                       | Lista sincronizada                                          |
|                              |   - `java.util.Stack`                                      | Pila (LIFO), extensión de Vector                            |
|                              | **`java.util.Set`**                                        | No permite duplicados                                       |
|                              | - `java.util.HashSet`                                      | Set basado en hash table                                    |
|                              | - `java.util.LinkedHashSet`                                | Set con orden de inserción                                  |
|                              | - `java.util.TreeSet`                                      | Set ordenado, basado en árbol                               |
|                              | **`java.util.Queue`**                                      | Maneja elementos en orden FIFO                              |
|                              | - `java.util.PriorityQueue`                                | Cola con prioridad                                          |
|                              | **`java.util.Deque`** (Subinterfaz de Queue)               | Doble extremo, soporta FIFO y LIFO                          |
|                              | - `java.util.ArrayDeque`                                   | Deque basado en array dinámico                              |
|                              | - `java.util.LinkedList`                                   | Deque basado en lista enlazada                              |
|                              | **`java.util.SortedSet`**                                  | Set ordenado                                                |
|                              | - `java.util.TreeSet`                                      | Implementación de SortedSet                                 |
| **`java.util.Map`**          | **`java.util.SortedMap`**                                  | Mapa con llaves ordenadas                                   |
|                              | - `java.util.TreeMap`                                      | Implementación de SortedMap                                 |
|                              | **`java.util.NavigableMap`**                               | Extiende SortedMap, navegación extendida                    |
|                              | - `java.util.TreeMap`                                      | Implementación de NavigableMap                              |
|                              | **Implementaciones Directas**                             |                                                             |
|                              | - `java.util.HashMap`                                      | Mapa basado en hash table                                   |
|                              | - `java.util.LinkedHashMap`                                | Mapa con orden de inserción                                 |
|                              | - `java.util.Hashtable`                                    | Mapa sincronizado                                           |
|                              |   - `java.util.Properties`                                 | Subclase para manejo de configuraciones                     |
| **`java.util.Collections`**  | Métodos estáticos                                         | Clase utilitaria para manipular colecciones                 |
|                              | - Ordenar (`Collections.sort`)                             | Ordenamiento de listas                                      |
|                              | - Buscar (`Collections.binarySearch`)                      | Búsqueda binaria                                            |
|                              | - Sincronizar (`Collections.synchronizedX`)                | Crear colecciones sincronizadas                             |
|                              | - Inmutabilidad (`Collections.unmodifiableX`)              | Crear colecciones inmutables                                |


En este árbol:
__Collection__ es la __interfaz__ principal para las __colecciones__.
__List__, __Set__, y __Queue__ son subinterfaces de __Collection__, cada una __PROPORCINA DIFERENTES COMPORTAMIENTOS__:
_ArrayList_, _LinkedList_, _HashSet_, _LinkedHashSet_, _TreeSet_, y _LinkedList_ son __IMPLEMENTACIONES ESPECÍFICAS__ de estas _interfaces_.

En el caso de los mapas:
__Map__ es la __interfaz__ principal para los __mapas__.
_HashMap_, _Hashtable_, _LinkedHashMap_, _TreeMap_, y _IdentityHashMap_ son __IMPLEMENTACIONES ESPECÍFICAS__ de la interfaz __Map__.
Es importante destacar que _Hashtable_ es una implementación más antigua y está sincronizada, lo que significa que es segura para operaciones en __entornos concurrentes__ pero puede tener un rendimiento inferior en comparación con las implementaciones no sincronizadas más modernas, como HashMap.

- [Creación de arrays](CREACION-DE-ARRAYS.md)
- [Colecciones](collection/README.md)
  - [ArrayList](collection/ARRAYLIST.md)
    - [Ejemplo](ejemplos/ARRAYLIST.md)
- [Maps](Map/README.md)

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles.

</div>