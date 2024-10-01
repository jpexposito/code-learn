<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Clase Math en Java)


La clase `Math` en Java proporciona métodos estáticos para realizar operaciones matemáticas comunes, como exponentes, raíces, logaritmos, funciones trigonométricas y más. Esta clase no puede ser instanciada porque todos sus métodos son estáticos.

Pertenece al paquete `java.lang`, por lo que no es necesario importar ninguna clase adicional para su uso.

## Características clave de la clase `Math`

- Todos los métodos son estáticos (`static`), lo que significa que se pueden llamar directamente a través de la clase sin necesidad de crear un objeto.
- Soporta funciones matemáticas básicas y avanzadas, incluyendo trigonometría, logaritmos y exponenciación.
- Proporciona constantes matemáticas como `PI` y `E`.

| Método                                      | Descripción                                                         |
| ------------------------------------------- | ------------------------------------------------------------------- |
| `abs(int a)`                                | Devuelve el valor absoluto de un número entero.                     |
| `sqrt(double a)`                            | Devuelve la raíz cuadrada de un número.                             |
| `pow(double a, double b)`                   | Calcula `a` elevado a la potencia de `b`.                           |
| `random()`                                  | Devuelve un número aleatorio entre 0.0 y 1.0.                       |
| `max(int a, int b)`                         | Devuelve el mayor de dos números enteros.                           |
| `min(int a, int b)`                         | Devuelve el menor de dos números enteros.                           |
| `sin(double a)`                             | Devuelve el seno de un ángulo (en radianes).                        |
| `cos(double a)`                             | Devuelve el coseno de un ángulo (en radianes).                      |
| `log(double a)`                             | Devuelve el logaritmo natural de un número (base `e`).              |
| `round(double a)`                           | Redondea un número decimal al entero más cercano.                   |

## Ejemplo de uso de la clase `Math`

```java
public class MathExample {
    public static void main(String[] args) {
        // 1. Valor absoluto
        int absVal = Math.abs(-25);
        System.out.println("Valor absoluto: " + absVal); // Salida: 25

        // 2. Raíz cuadrada
        double sqrtVal = Math.sqrt(64);
        System.out.println("Raíz cuadrada de 64: " + sqrtVal); // Salida: 8.0

        // 3. Potencia (2^3)
        double powVal = Math.pow(2, 3);
        System.out.println("2 elevado a 3: " + powVal); // Salida: 8.0

        // 4. Número aleatorio
        double randomVal = Math.random();
        System.out.println("Número aleatorio entre 0.0 y 1.0: " + randomVal);

        // 5. Valor máximo
        int maxVal = Math.max(10, 20);
        System.out.println("Mayor valor entre 10 y 20: " + maxVal); // Salida: 20

        // 6. Valor mínimo
        int minVal = Math.min(10, 20);
        System.out.println("Menor valor entre 10 y 20: " + minVal); // Salida: 10

        // 7. Seno de un ángulo (π/2 radianes)
        double sinVal = Math.sin(Math.PI / 2);
        System.out.println("Seno de π/2: " + sinVal); // Salida: 1.0

        // 8. Logaritmo natural (base e)
        double logVal = Math.log(Math.E);
        System.out.println("Logaritmo natural de e: " + logVal); // Salida: 1.0

        // 9. Redondear un número decimal
        long roundedVal = Math.round(9.7);
        System.out.println("Redondear 9.7: " + roundedVal); // Salida: 10
    }
}
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles

</div>