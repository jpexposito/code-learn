<div align="justify">

# <img src=../../../../../images/coding-book.png width="40"> Code & Learn (Colecciones)

Java proporciona una amplia variedad de estructuras de datos a través del framework de Colecciones __(Collections Framework)__. Estas __colecciones__ facilitan el _almacenamiento, manipulación y gestión de grupos de objetos_. Las principales interfaces y clases relacionadas con colecciones en Java se encuentran en el paquete __java.util__.

## Interfaces Principales del Framework de Colecciones

### Collection

- Representa una colección de objetos. __No garantiza ningún orden específico para sus elementos__.
- Subinterfaces: __List, Set, y Queue__.

#### List

Colección ordenada que __PERMITE__ elementos duplicados.
>Nota: __Implementaciones notables: ArrayList, LinkedList, y Vector__.

#### Set

Colección que __NO PERMITE__ elementos duplicados. __NO GARANTIZA__ un orden específico.
>Nota: __Implementaciones notables: HashSet, TreeSet, y LinkedHashSet__.

#### Queue

Colección diseñada para mantener una secuencia específica para insertar y acceder a los elementos.

>Nota: __Implementaciones notables:PriorityQueue__.

#### Map

Colección de __pares clave-valor. Cada clave debe ser única__.

>Nota: __Implementaciones notables: HashMap, TreeMap, LinkedHashMap, Hashtable, y Properties__.

## Implementaciones y Características

### ArrayList

Basado en arrays dinámicos.  Permite acceso rápido a los elementos, pero puede ser más lento en operaciones de inserción y eliminación en el medio.

- Ventajas: _Tamaño dinámico, acceso rápido_, __PERMITE ELEMENTOS DUPLICADOS__.
- Uso: Cuando se necesite una __lista ordenada__ y __se realicen operaciones frecuentes de inserción y acceso__.

    ```java
    // Ejemplo de construcción de una lista con ArrayList
    List<String> lista = new ArrayList<>();
    lista.add("Elemento 1");
    lista.add("Elemento 2");
    ```
<div align="center">    
<img src="https://miro.medium.com/v2/resize:fit:670/0*5w9-ibvGwT1EpeH9.png" width="250px">
</div>

### LinkedList

Basado en una __lista doblemente enlazada__.
_Ofrece inserciones y eliminaciones rápidas en cualquier posición_.

- Ventajas: _Inserciones y eliminaciones rápidas en cualquier posición_, implementa la __interfaz Deque__.
- Uso: Cuando se _necesite_ una __lista__ y se __realicen operaciones frecuentes de inserción y eliminación en cualquier posición__.

```java
LinkedList<String> linkedList = new LinkedList<>();

// Agregar elementos al final de la lista
linkedList.add("Elemento 1");
linkedList.add("Elemento 2");
linkedList.add("Elemento 3");

// Mostrar la LinkedList
System.out.println("LinkedList actual: " + linkedList);

// Agregar un elemento al principio de la lista
linkedList.addFirst("Elemento 0");
// Agregar un elemento al final de la lista
linkedList.addLast("Elemento 4");
```

<div align="center"> 
<img src="https://qph.cf2.quoracdn.net/main-qimg-41cdfa9a815220598f2c03f1bccaeff8"/>
</div>

###  HashSet
Implementación basada en una tabla hash.
Ofrece ___operaciones rápidas para agregar, eliminar y comprobar la existencia de un elemento__.

- Ventajas: __Garantiza elementos únicos__, _operaciones_ __rápidas de inserción, eliminación y búsqueda__.
- Uso: Cuando se necesite una __colección sin duplicados__ y el _orden de los elementos_ __NO__ sea importante.

```java
// Crear un HashSet de tipo String
HashSet<String> hashSet = new HashSet<>();

// Agregar elementos al conjunto
hashSet.add("Elemento 1");
hashSet.add("Elemento 2");
hashSet.add("Elemento 3");

// Mostrar el HashSet actual
System.out.println("HashSet actual: " + hashSet);

// Intentar agregar un elemento duplicado (no se permiten duplicados en un conjunto)
boolean seAgregoDuplicado = hashSet.add("Elemento 2");
```

<div align="center"> 
<img src="https://media.geeksforgeeks.org/wp-content/uploads/ArrayListVsHashSet.png"/>
</div>

### LinkedHashSet

Mientras que __HashSet__ __NO__ garantiza un orden específico de los elementos, __LinkedHashSet__ mantiene el __orden__ de __inserción__, es decir, el orden en el que los elementos fueron agregados.

```java
inkedHashSet<String> linkedHashSet = new LinkedHashSet<>();

// Agregar elementos al conjunto
linkedHashSet.add("Elemento 3");
linkedHashSet.add("Elemento 1");
linkedHashSet.add("Elemento 2");
// Mostrar el LinkedHashSet actual (se mantendrá el orden de inserción)
System.out.println("LinkedHashSet actual: " + linkedHashSet);
// Intentar agregar un elemento duplicado (no se permiten duplicados en un conjunto)
boolean seAgregoDuplicado = linkedHashSet.add("Elemento 2");
System.out.println("¿Se agregó duplicado? " + seAgregoDuplicado);

// Mostrar el LinkedHashSet después de intentar agregar un duplicado
System.out.println("LinkedHashSet después de intentar agregar un duplicado: " + linkedHashSet);

// Verificar si el conjunto contiene un elemento específico
boolean contieneElemento = linkedHashSet.contains("Elemento 3");
System.out.println("¿Contiene 'Elemento 3'? " + contieneElemento);

// Eliminar un elemento del conjunto
linkedHashSet.remove("Elemento 1");

```

## Map

Un mapa en Java es una interfaz que representa una colección de pares __clave-valor__, donde cada __clave es única__ y se __asigna a un único valor__. La principal característica de los mapas es que __NO permiten claves duplicadas__.

### Características Principales

1. **Claves Únicas:** Cada clave en un mapa es única; no puede haber dos claves iguales en un mismo mapa.
2. **Asociación de Clave-Valor:** Cada clave se asocia a un único valor. Sin embargo, diferentes claves pueden asociarse al mismo valor.
3. **Implementaciones:** Java proporciona varias implementaciones de la interfaz `Map`, como `HashMap`, `TreeMap`, `LinkedHashMap`, entre otros, cada una con sus propias características y comportamientos.
4. **No Garantiza Orden:** Aunque algunas implementaciones, como `LinkedHashMap`, mantienen el orden de inserción, el orden en general no está garantizado para todos los mapas.

### Operaciones Comunes

- **put(K key, V value):** Asocia el valor especificado con la clave especificada en el mapa.
- **get(Object key):** Devuelve el valor asociado con la clave especificada, o `null` si el mapa no contiene la clave.
- **remove(Object key):** Elimina la clave y su valor asociado del mapa.
- **containsKey(Object key):** Verifica si el mapa contiene la clave especificada.
- **containsValue(Object value):** Verifica si el mapa contiene el valor especificado.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles.

</div>