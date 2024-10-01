<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Clase Integer en Java)

La clase `Integer` en Java es una **clase envolvente** para el tipo primitivo `int`. Se utiliza para encapsular un valor primitivo en un objeto, lo que permite tratar los enteros como objetos en contextos donde los tipos primitivos no son aceptados (como en colecciones como `List`, `Set`, o `Map`).

La clase `Integer` pertenece al paquete `java.lang` y es **inmutable**, lo que significa que una vez que se crea un objeto `Integer`, su valor no puede cambiar.

## Características clave de la clase `Integer`
- Permite trabajar con enteros como objetos.
- Proporciona métodos para convertir entre cadenas y enteros.
- Soporta métodos útiles para comparar, sumar, obtener el valor máximo y mínimo de dos enteros, etc.
- Implementa las interfaces `Comparable<Integer>` y `Serializable`.

| Método                                   | Descripción                                                         |
| ---------------------------------------- | ------------------------------------------------------------------- |
| `parseInt(String s)`                     | Convierte una cadena en un valor primitivo `int`.                   |
| `valueOf(String s)`                      | Convierte una cadena en un objeto `Integer`.                        |
| `intValue()`                             | Devuelve el valor primitivo `int` de un objeto `Integer`.           |
| `toString()`                             | Convierte el valor de `Integer` en una cadena.                      |
| `compareTo(Integer anotherInteger)`      | Compara dos objetos `Integer`. Devuelve negativo, 0 o positivo.     |
| `max(int a, int b)`                      | Devuelve el mayor de dos valores enteros.                           |
| `min(int a, int b)`                      | Devuelve el menor de dos valores enteros.                           |
| `sum(int a, int b)`                      | Devuelve la suma de dos valores enteros.                            |

## Ejemplo de uso

```java
public class IntegerExample {
    public static void main(String[] args) {
        // 1. Convertir cadena a primitivo int
        int num = Integer.parseInt("123");
        System.out.println("Número entero: " + num); // Salida: 123

        // 2. Convertir cadena a objeto Integer
        Integer numObj = Integer.valueOf("456");
        System.out.println("Objeto Integer: " + numObj); // Salida: 456

        // 3. Obtener valor primitivo int de un Integer
        int primitiveInt = numObj.intValue();
        System.out.println("Valor primitivo: " + primitiveInt); // Salida: 456

        // 4. Convertir un Integer a cadena
        String str = numObj.toString();
        System.out.println("Cadena: " + str); // Salida: "456"

        // 5. Comparar dos Integer
        Integer num1 = 100;
        Integer num2 = 200;
        int comparison = num1.compareTo(num2);
        System.out.println("Resultado de comparación: " + comparison); // Salida: negativo (100 < 200)

        // 6. Obtener el mayor de dos enteros
        int maxVal = Integer.max(10, 20);
        System.out.println("Mayor valor: " + maxVal); // Salida: 20

        // 7. Obtener el menor de dos enteros
        int minVal = Integer.min(10, 20);
        System.out.println("Menor valor: " + minVal); // Salida: 10

        // 8. Sumar dos enteros
        int sumVal = Integer.sum(10, 20);
        System.out.println("Suma: " + sumVal); // Salida: 30
    }
}
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles

</div>