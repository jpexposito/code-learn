<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Scanner)

## Introducción

La clase `Scanner` (perteneciente al paquete `java.util`) es una utilidad para **escaneo de texto**. Permite leer datos de distintas fuentes (por ejemplo, `System.in`, archivos, cadenas) y convertirlos a tipos primitivos (int, double, boolean, etc.) o cadenas.

El escáner divide su entrada en *tokens* usando un *delimitador* (por defecto, espacios en blanco). Luego esos tokens pueden transformarse en diferentes tipos mediante métodos `nextXXX()`.

---

## Declaración de la clase

```java
public final class Scanner extends Object implements Iterator<String>, Closeable
```

- `final`: no permite subclases.  
- Implementa `Iterator<String>`: permite iterar tokens de tipo `String`.  
- Implementa `Closeable`: puede cerrarse para liberar recursos.  

---

## Constructoras comunes

| Constructor | Fuente / Descripción |
|---|---|
| `Scanner(InputStream source)` | Escanea desde un `InputStream` como `System.in`. |
| `Scanner(String source)` | Escanea desde una cadena de texto. |
| `Scanner(File source)` | Escanea desde un archivo. |
| `Scanner(File source, String charsetName)` | Escanea desde un archivo con codificación específica. |

---

## Principales métodos

### Lectura de tokens / datos

- `boolean hasNext()` — Indica si hay otro token en la entrada.  
- `String next()` — Retorna el siguiente token (como `String`).  
- `String nextLine()` — Lee la línea completa restante (incluyendo espacios).  
- `int nextInt()` — Lee el siguiente token y lo convierte a `int`.  
- `long nextLong()` — Similar para `long`.  
- `double nextDouble()` — Similar para `double`.  
- `boolean nextBoolean()` — Lee el siguiente token y lo interpreta como `true` o `false`.  
- `byte nextByte()`, `short nextShort()`, `float nextFloat()` — versiones para otros tipos numéricos.  

### Verificaciones previas

- `boolean hasNextInt()` — True si el próximo token puede convertirse a `int`.  
- `boolean hasNextLong()`, `hasNextDouble()`, etc. — versiones para otros tipos.  
- `boolean hasNextBoolean()` — True si el próximo token es `true` o `false` (ignorando mayúsculas).  
- `boolean hasNext(String pattern)` / `hasNext(Pattern pattern)` — True si el siguiente token coincide con el patrón dado.

### Delimitadores, localización y radix

- `Scanner useDelimiter(String pattern)` / `useDelimiter(Pattern pattern)` — cambia el patrón de delimitador (por defecto, espacios en blanco).  
- `Pattern delimiter()` — Obtiene el patrón actual de delimitador.  
- `Scanner useLocale(Locale locale)` — Establece la *localización* (influyendo en formatos numéricos).  
- `Locale locale()` — Obtiene la localización actual.  
- `Scanner useRadix(int radix)` — Cambia la base numérica usada (por defecto 10).  
- `int radix()` — Retorna la base numérica usada actualmente.  
- `reset()` — Restablece delimitador, localización y radix a sus valores por defecto.  

### Otras operaciones útiles

- `String findInLine(Pattern pattern)` / `findWithinHorizon(...)` — busca un patrón ignorando delimitadores.  
- `Scanner skip(Pattern pattern)` — salta entrada que coincida con el patrón, sin respetar delimitadores.  
- `MatchResult match()` — obtiene el resultado de la última operación de coincidencia (por ejemplo, después de `nextInt()`).  
- `void close()` — cierra el scanner y libera recursos.  

---

## Comportamientos y advertencias

- `Scanner` **no es seguro para uso simultáneo (multihilo)** sin sincronización externa.  
- Operaciones pueden **bloquearse** esperando entrada (por ejemplo, `next()` esperará hasta recibir un token).  
- Si un `hasNextXXX()` falla por tipo, el token no se consume, permitiendo que sea leído o descartado después.  
- Al cerrar el `Scanner`, también se cierra su fuente si esta implementa `Closeable`.  
- Pasar `null` como argumento a muchos métodos lanzará `NullPointerException`.  

---

## Ejemplo de uso

```java
import java.util.Scanner;

public class EjemploScanner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingresa tu nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Ingresa tu edad: ");
        int edad = sc.nextInt();

        System.out.println("Hola " + nombre + ", tienes " + edad + " años.");

        sc.close();
    }
}
```

---

## Referencias

- [Documentación oficial Java 8 - Scanner](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html)
- [Documentación oficial Java 17 - Scanner](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Scanner.html)

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles

</div>