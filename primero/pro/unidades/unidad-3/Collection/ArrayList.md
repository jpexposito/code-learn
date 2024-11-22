<div align="justify">

# <img src=../../../../../images/coding-book.png width="40"> Code & Learn (ArrayList)

## Método más utilizados

Los __ArrayList__ en Java proporcionan una variedad de métodos para realizar operaciones comunes.

### Agregar Elementos

- __add(E elemento)__: Agrega un elemento al final de la lista.
- __add(int índice, E elemento)__: Inserta un elemento en la posición especificada.

```java
ArrayList<String> lista = new ArrayList<>();
lista.add("Uno");
lista.add("Dos");
lista.add(1, "Tres"); // Resultado: [Uno, Tres, Dos]
```

### Obtener Elementos

- __get(int indice)__: Obtiene el elemento en la posición especificada.

```java
String elemento = lista.get(1); // Resultado: Tres
```

### Modificar Elementos

- __set(int índice, E elemento)__: Reemplaza el elemento en la posición especificada.

```java
lista.set(1, "Cuatro"); // Resultado: [Uno, Cuatro, Dos]
```

### Eliminar Elementos

- __remove__(Object objeto): Elimina la primera ocurrencia del objeto especificado.
- __remove__(int indice): Elimina el elemento en la posición especificada.
- __clear__(): Elimina todos los elementos de la lista.

```java
lista.remove("Cuatro"); // Resultado: [Uno, Dos]
lista.remove(0); // Resultado: [Dos]
lista.clear(); // Resultado: []
```

### Verificar Existencia

- __contains__(Object objeto): Verifica si la lista contiene el objeto especificado.
- __isEmpty()__: Verifica si la lista está vacía.

```java
boolean contiene = lista.contains("Dos"); // Resultado: true
boolean vacia = lista.isEmpty(); // Resultado: true/false
```

### Tamaño de la Lista

- __size__(): Devuelve el número de elementos en la lista.

```java
int tamaño = lista.size(); // Resultado: 0
```

### Convertir a Array

- __toArray__(): Convierte la lista a un array.

```java
Object[] array = lista.toArray();
```

### Iteración

Se pueden utilizar bucles __for-each__ o __iteradores__ para recorrer la lista.

```java
for (String elemento : lista) {
    System.out.println(elemento);
}

// O utilizando un iterador
Iterator<String> iterador = lista.iterator();
while (iterador.hasNext()) {
    String elemento = iterador.next();
    System.out.println(elemento);
}
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../../../../../../LICENSE) para detalles.

</div>