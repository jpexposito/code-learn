<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Clase String en Java)

La clase `String` en Java es inmutable y proporciona muchos __métodos útiles__ para manipular cadenas de caracteres. Algunos de los métodos más importantes son:

| Método                                   | Descripción                                                         |
| ---------------------------------------- | ------------------------------------------------------------------- |
| `length()`                               | Devuelve la longitud de la cadena.                                  |
| `charAt(int index)`                      | Devuelve el carácter en la posición especificada.                   |
| `substring(int beginIndex, int endIndex)`| Devuelve una subcadena entre los índices especificados.             |
| `indexOf(String str)`                    | Devuelve el índice de la primera aparición de una subcadena.        |
| `toLowerCase()`                          | Convierte la cadena a minúsculas.                                   |
| `toUpperCase()`                          | Convierte la cadena a mayúsculas.                                   |
| `trim()`                                 | Elimina los espacios en blanco al principio y al final de la cadena.|
| `replace(CharSequence target, CharSequence replacement)` | Reemplaza una subcadena por otra.                                   |
| `equals(Object anObject)`                | Compara dos cadenas y devuelve `true` si son iguales.               |
| `split(String regex)`                    | Divide la cadena en partes utilizando un delimitador (expresión regular). |
| contains| El método contains() devuelve true si el String contiene la secuencia de caracteres especificada.|

## Ejemplo de uso

```java
public class StringExample {
    public static void main(String[] args) {
        String str = "  Hola Mundo  ";

        // 1. Longitud de la cadena
        int length = str.length();
        System.out.println("Longitud: " + length); // Salida: 13

        // 2. Obtener el carácter en la posición 6
        char ch = str.charAt(6);
        System.out.println("Carácter en la posición 6: " + ch); // Salida: M

        // 3. Subcadena del índice 2 al 7
        String subStr = str.substring(2, 7);
        System.out.println("Subcadena: " + subStr); // Salida: "Hola "

        // 4. Buscar la posición de "Mundo"
        int index = str.indexOf("Mundo");
        System.out.println("Índice de 'Mundo': " + index); // Salida: 7

        // 5. Convertir a minúsculas
        String lowerStr = str.toLowerCase();
        System.out.println("Minúsculas: " + lowerStr); // Salida: "  hola mundo  "

        // 6. Convertir a mayúsculas
        String upperStr = str.toUpperCase();
        System.out.println("Mayúsculas: " + upperStr); // Salida: "  HOLA MUNDO  "

        // 7. Eliminar espacios en blanco al principio y al final
        String trimmedStr = str.trim();
        System.out.println("Trimmed: " + trimmedStr); // Salida: "Hola Mundo"

        // 8. Reemplazar "Mundo" por "Java"
        String replacedStr = str.replace("Mundo", "Java");
        System.out.println("Reemplazado: " + replacedStr); // Salida: "  Hola Java  "

        // 9. Comparar dos cadenas
        String str2 = "Hola Mundo";
        boolean isEqual = trimmedStr.equals(str2);
        System.out.println("Son iguales: " + isEqual); // Salida: true

        // 10. Dividir la cadena por espacios
        String[] parts = trimmedStr.split(" ");
        for (String part : parts) {
            System.out.println(part); // Salida: "Hola", "Mundo"
            String texto = "Hola, mundo!";
            char caracter = 'm';

            boolean contiene = texto.contains(String.valueOf(caracter));

            System.out.println("¿El texto contiene el carácter '" + caracter + "'? " + contiene);
        }
    }
}
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles

</div>