<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Expresiones Regulares)

Las ___expresiones regulares___, también conocidas como __regex__ o __regexp__, son __patrones de búsqueda__ y __manipulación de cadenas__ de texto. Son _herramientas poderosas y flexibles utilizadas en diversos lenguajes_ de programación, incluyendo Java, para realizar operaciones avanzadas en cadenas. 

## ¿Cómo trabajamos con expresiones?

Las expresiones regulares en Java se gestionan a través de la clase java.__util.regex.Pattern__. La clase __Pattern__ representa un patrón de expresión regular compilado. Puedes usar __Pattern__ para crear un objeto Matcher, que se utiliza para realizar operaciones de coincidencia en una cadena.

```java
//Creamos un patron
import java.util.regex.*;

String regex = "patron";
Pattern pattern = Pattern.compile(regex);
```

y a continuación:

```java
Matcher matcher = pattern.matcher("cadena");
```

### Funciones Básicas

#### matches()

Verifica si toda la cadena coincide con el patrón.

```java
boolean esCoincidencia = matcher.matches();
```

#### find()

Busca la próxima subcadena que coincide con el patrón.

```java
boolean encontrado = matcher.find();
```

### Operadores en expresiones regulares

| Operador  | Descripción                                      | Ejemplo                |
|-----------|--------------------------------------------------|------------------------|
| `.`       | Coincide con cualquier carácter excepto nueva línea | `a.b` coincide con "aab", "abb", "acb", etc. |
| `^`       | Coincide con el inicio de la cadena               | `^abc` coincide con "abc" al inicio de la cadena. |
| `$`       | Coincide con el final de la cadena                | `xyz$` coincide con "xyz" al final de la cadena. |
| `*`       | Coincide con cero o más repeticiones del elemento anterior | `a*b` coincide con "ab", "aab", "aaab", etc. |
| `+`       | Coincide con una o más repeticiones del elemento anterior | `a+b` coincide con "ab", "aab", "aaab", etc. |
| `?`       | Coincide con cero o una repetición del elemento anterior | `a?b` coincide con "ab" o "b". |
| `\`       | Escapa el significado especial de un carácter      | `\.` coincide con el carácter punto literal. |
| `[]`      | Coincide con cualquier carácter dentro de los corchetes | `[aeiou]` coincide con cualquier vocal. |
| `[^]`     | Coincide con cualquier carácter que no esté dentro de los corchetes | `[^0-9]` coincide con cualquier carácter que no sea un dígito. |
| `()`      | Agrupa elementos para aplicar operadores a una expresión completa | `(abc)+` coincide con "abc", "abcabc", etc. |
| `\d`      | Coincide con un dígito (equivalente a `[0-9]`)   | `\d{3}` coincide con tres dígitos. |
| `\w`      | Coincide con un carácter de palabra (letras, dígitos, guiones bajos) | `\w+` coincide con una o más palabras. |
| `\s`      | Coincide con un carácter de espacio en blanco     | `\s*` coincide con cero o más espacios en blanco. |
| `|`       | Operador lógico "o"                               | `a|b` coincide con "a" o "b". |

### Grupos y Capturas

Los __grupos__ y las __capturas__ en expresiones regulares se utilizan para __agrupar partes específicas__ de una coincidencia y para extraer esa información después de realizar una búsqueda. Puedes crear grupos utilizando paréntesis __()__ en tu expresión regular. 

```java
String regex = "(\\d{3})-(\\d{2})";
Pattern pattern = Pattern.compile(regex);
Matcher matcher = pattern.matcher("123-45");

if (matcher.matches()) {
    String grupo1 = matcher.group(1); // Contiene "123"
    String grupo2 = matcher.group(2); // Contiene "45"
}
```

## Similitudes con las Bases de Datos

Las expresiones regulares en Java tienen algunas similitudes con las funcionalidades que ofrecen las bases de datos para trabajar con texto, como los operadores de búsqueda con patrones o la validación de formatos. A continuación, se detallan las similitudes clave:

### 1. Validación de formato de datos

- __En Java__: Las expresiones regulares son comúnmente utilizadas para validar el formato de datos como correos electrónicos, números de teléfono, códigos postales, etc.

    ```java
    public class ValidacionEmail {
    public static void main(String[] args) {
        String email = "usuario@dominio.com";
        String patron = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // Expresión regular para validar un correo electrónico

        Pattern p = Pattern.compile(patron);
        Matcher m = p.matcher(email);

        if (m.matches()) {
            System.out.println("El correo electrónico es válido.");
        } else {
            System.out.println("El correo electrónico no es válido.");
        }
    }
    ```

- __En bases de datos__: En bases de datos, se pueden utilizar expresiones regulares o funciones específicas como `REGEXP` en SQL para realizar validaciones similares, asegurando que los datos ingresados sigan un patrón específico.

    ```sql
    SELECT * FROM usuarios WHERE email REGEXP '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$';
    ```

### 2. Búsqueda de patrones en texto

- __En Java__: Las expresiones regulares permiten buscar patrones complejos dentro de una cadena de texto, como encontrar todas las ocurrencias de una palabra específica o una secuencia de caracteres.

    ```java
        public class BusquedaPatron {
        public static void main(String[] args) {
            String texto = "El número de teléfono es 555-1234 y el otro es 555-5678.";
            String patron = "\\d{3}-\\d{4}"; // Expresión regular para encontrar un número de teléfono en el formato 555-1234

            Pattern p = Pattern.compile(patron);
            Matcher m = p.matcher(texto);

            while (m.find()) {
                System.out.println("Número de teléfono encontrado: " + m.group());
            }
        }
    }
    ```

- __En bases de datos__: En SQL, se puede usar el operador `REGEXP` para realizar búsquedas de patrones en campos de texto. Esto permite realizar consultas avanzadas basadas en patrones.

    ```sql
    SELECT * FROM productos WHERE descripcion REGEXP '^[A-Za-z]+';
    ```

### 3. Reemplazo de texto

- __En Java__: Las expresiones regulares se utilizan para reemplazar partes de una cadena que coincidan con un patrón definido.

    ```java
    public class ReemplazoTexto {
        public static void main(String[] args) {
            String texto = "La fecha de hoy es 2025-03-14.";
            String patron = "\\d{4}-\\d{2}-\\d{2}"; // Expresipn regular para identificar una fecha en formato yyyy-MM-dd

            // Reemplazar la fecha por otro texto
            String nuevoTexto = texto.replaceAll(patron, "fecha desconocida");
            System.out.println(nuevoTexto);
        }
        }
    ```

- __En bases de datos__: Aunque no es tan común, algunas bases de datos ofrecen funciones para reemplazar texto basado en patrones. Por ejemplo, MySQL tiene la función `REGEXP_REPLACE` para reemplazar partes de una cadena que coincidan con una expresión regular.

    ```sql
    UPDATE usuarios SET nombre = REGEXP_REPLACE(nombre, 'Juan', 'Carlos');
    ```

### 4. Optimización de búsquedas de texto

- __En Java__: Las expresiones regulares son muy eficientes cuando se trata de buscar patrones complejos en grandes cantidades de texto.
- __En bases de datos__: Las bases de datos también pueden optimizar las búsquedas de patrones mediante el uso de índices de texto completo o funcionalidades como `REGEXP`.

    ```sql
    
    ```

</div>