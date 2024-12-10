<div align="justify">

# <img src=../../../../../images/coding-book.png width="40"> Code & Learn (Vector)

En Java, un **Vector** es una clase de la `colección` que implementa una estructura de datos dinámica, similar a un **[ArrayList](ARRAYLIST.md)**, pero con dos características principales:

1. **Es sincronizado (Thread-safe):** Esto significa que los métodos de la clase `Vector` están sincronizados, lo que permite que varios hilos accedan al mismo objeto `Vector` de manera segura. Sin embargo, esto puede tener un impacto negativo en el rendimiento en escenarios de un solo hilo.

> Se suelen utilizar en escenarios de mucha carga.

2. **Capacidad dinámica:** Un `Vector` puede cambiar su tamaño dinámicamente para acomodar más elementos, eliminando la necesidad de declarar una capacidad fija al momento de crearlo.

El `Vector` pertenece al paquete `java.util` y es parte de las `colecciones heredadas`.

## Características principales

- **Capacidad inicial y crecimiento:** Cuando un `Vector` alcanza su capacidad máxima, su tamaño se incrementa automáticamente (por defecto se duplica).
- **Compatibilidad con enumeraciones:** Además de iteradores, los `Vector` soportan enumeraciones (`Enumeration`) para recorrer sus elementos.
- **Herencia:** `Vector` hereda de la clase `AbstractList` e implementa las interfaces `List`, `RandomAccess`, `Cloneable` y `Serializable`.

## Métodos comunes de la clase Vector

| **Método**                 | **Descripción**                                                                                   |
|-----------------------------|---------------------------------------------------------------------------------------------------|
| `add(E e)`                 | Agrega un elemento al final del vector.                                                          |
| `add(int index, E element)`| Inserta un elemento en una posición específica.                                                  |
| `remove(Object o)`         | Elimina la primera ocurrencia del objeto especificado.                                           |
| `size()`                   | Devuelve el número de elementos en el vector.                                                   |
| `capacity()`               | Devuelve la capacidad actual del vector.                                                        |
| `get(int index)`           | Devuelve el elemento en la posición especificada.                                               |
| `set(int index, E element)`| Reemplaza el elemento en la posición especificada.                                               |
| `isEmpty()`                | Devuelve `true` si el vector no contiene elementos.                                              |
| `contains(Object o)`       | Devuelve `true` si el vector contiene el objeto especificado.                                    |
| `clear()`                  | Elimina todos los elementos del vector.                                                         |

## Ventajas y desventajas de usar un Vector

### Ventajas

- **Sincronización:** Los métodos de `Vector` son seguros para el acceso concurrente.
- **Capacidad dinámica:** El vector puede crecer automáticamente según sea necesario.
- **Versatilidad:** Compatible con enumeraciones y métodos del marco de colecciones.

### Desventajas

- **Rendimiento:** Debido a la sincronización, puede ser más lento que otras estructuras como `ArrayList`.
- **Obsolescencia relativa:** En la mayoría de los casos, se prefiere usar `ArrayList` a menos que se requiera sincronización.

## Ejemplo de uso básico

```java
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        // Crear un vector
        Vector<String> vector = new Vector<>();

        // Agregar elementos
        vector.add("Manzana");
        vector.add("Banana");
        vector.add("Cereza");

        // Imprimir el vector
        System.out.println("Elementos del vector: " + vector);

        // Obtener un elemento
        System.out.println("Primer elemento: " + vector.get(0));

        // Eliminar un elemento
        vector.remove("Banana");
        System.out.println("Después de eliminar: " + vector);

        // Ver la capacidad del vector
        System.out.println("Capacidad del vector: " + vector.capacity());
    }
}
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../../../../../../LICENSE) para detalles.

</div>
