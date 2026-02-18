<div align="justify">

# 📘 Recursividad en Java (Guía paso a paso con ejemplos)

La **recursividad** es una técnica de programación en la que un método **se llama a sí mismo** para resolver un problema dividiéndolo en **subproblemas más pequeños**.

En Java, cuando un método se llama a sí mismo, hablamos de un **método recursivo**.

> ✅ Idea clave: *la recursividad repite trabajo “sin bucles”, pero usando la pila de llamadas.*

---

## 🧠 ¿Qué problema resuelve la recursividad?

La recursividad es especialmente útil cuando el problema ya tiene una estructura “anidada” o “por niveles”, por ejemplo:

- Cálculos matemáticos definidos por recurrencia (factorial, Fibonacci).
- Estructuras jerárquicas (carpetas y subcarpetas, árboles).
- Backtracking (laberintos, N-reinas, combinaciones y permutaciones).

---

## 🧱 Las 3 piezas obligatorias de un método recursivo

Un método recursivo siempre necesita:

1) **Caso base (condición de parada)**  
   - La situación más simple que se puede responder directamente.
   - Evita recursividad infinita.

2) **Caso recursivo (paso recursivo)**  
   - Cómo el método se reduce a un subproblema.
   - Debe acercarse al caso base.

3) **Progreso hacia el caso base**  
   - En cada llamada, el “tamaño” del problema debe disminuir (o cambiar hacia el caso base).

> Si falta cualquiera de estas 3 piezas, es muy probable que el método falle (por ejemplo, con **StackOverflowError**).

---

## 🧵 ¿Qué es la pila de llamadas (call stack)?

Cada vez que se llama a un método, Java crea un **frame** (una “ficha”) en la **pila de llamadas**, donde guarda:

- parámetros
- variables locales
- dirección de retorno

En recursividad, se apilan muchas llamadas, y luego se “desapilan” al volver.

📌 Si hay demasiadas llamadas recursivas (o no se detiene), puede ocurrir:

- `StackOverflowError` (desbordamiento de pila)
- alto consumo de memoria
- rendimiento peor que una versión iterativa (en algunos casos)

---

## ✅ Ventajas e inconvenientes

### Ventajas
- Código más corto y expresivo en problemas naturalmente recursivos.
- Solución elegante para estructuras jerárquicas.
- Muy útil en backtracking.

### Inconvenientes
- Puede ser más lenta que un bucle (por sobrecarga de llamadas).
- Puede consumir más memoria (pila de llamadas).
- Riesgo de recursividad infinita si el caso base es incorrecto.

---

# 🧪 Ejemplo 1: Factorial (con explicación paso a paso)

El factorial se define como:

- `0! = 1`  ✅ (caso base)
- `n! = n * (n-1)!`  ✅ (caso recursivo)

## 🧩 Implementación recursiva (correcta)

```java
public class Factorial {

    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("n debe ser >= 0");

        // Caso base
        if (n == 0) return 1;

        // Caso recursivo
        return n * factorial(n - 1);
    }
}
```

## 🔍 ¿Qué pasa al ejecutar `factorial(5)`?

Se generan llamadas anidadas:

1. `factorial(5) = 5 * factorial(4)`
2. `factorial(4) = 4 * factorial(3)`
3. `factorial(3) = 3 * factorial(2)`
4. `factorial(2) = 2 * factorial(1)`
5. `factorial(1) = 1 * factorial(0)`
6. `factorial(0) = 1` ✅ caso base

Ahora “regresa” (se desapila):

- `factorial(1) = 1 * 1 = 1`
- `factorial(2) = 2 * 1 = 2`
- `factorial(3) = 3 * 2 = 6`
- `factorial(4) = 4 * 6 = 24`
- `factorial(5) = 5 * 24 = 120`

✅ Resultado final: `120`

### Ejemplos entrada/salida
- `factorial(0)` → `1`
- `factorial(5)` → `120`
- `factorial(-2)` → lanza `IllegalArgumentException`

---

# 🧪 Ejemplo 2: Fibonacci (con traza simple)

Definición:

- `F(0)=0`, `F(1)=1` ✅ (casos base)
- `F(n)=F(n-1)+F(n-2)` ✅ (caso recursivo)

## Implementación recursiva (simple)

```java
public static long fib(int n) {
    if (n < 0) throw new IllegalArgumentException("n debe ser >= 0");
    if (n <= 1) return n;
    return fib(n - 1) + fib(n - 2);
}
```

## ¿Por qué esta versión puede ser lenta?
Porque recalcula muchas veces lo mismo:

- `fib(5)` llama a `fib(4)` y `fib(3)`
- pero `fib(4)` también llama a `fib(3)` otra vez, etc.

✅ Solución típica: **memoización** (guardar resultados) o una versión iterativa.

### Ejemplos entrada/salida
- `fib(0)` → `0`
- `fib(1)` → `1`
- `fib(10)` → `55`

---

# 🧪 Ejemplo 3: Suma de 1 hasta N

Definición:

- `sum(0)=0` ✅ caso base
- `sum(n)=n+sum(n-1)` ✅ recursivo

```java
public static long sumaHastaN(int n) {
    if (n < 0) throw new IllegalArgumentException("n debe ser >= 0");
    if (n == 0) return 0;
    return n + sumaHastaN(n - 1);
}
```

### Ejemplos entrada/salida
- `sumaHastaN(0)` → `0`
- `sumaHastaN(5)` → `15`
- `sumaHastaN(-1)` → lanza `IllegalArgumentException`

---

# 📁 Ejemplo 4: Contar ficheros en un directorio (estructura jerárquica)

Este es un caso “naturalmente recursivo”: una carpeta contiene archivos y subcarpetas, y cada subcarpeta… contiene lo mismo.

## 🔁 Idea del algoritmo
1) Listar contenido del directorio actual.  
2) Por cada elemento:  
   - si es archivo → contar 1  
   - si es carpeta → contar recursivamente dentro y sumar  

```java
import java.io.File;

public class ContarArchivos {

    public static int contarArchivos(File dir) {
        if (dir == null || !dir.exists())
            throw new IllegalArgumentException("Directorio inválido");

        File[] contenido = dir.listFiles();
        if (contenido == null) return 0;

        int total = 0;

        for (File f : contenido) {
            if (f.isDirectory()) {
                total += contarArchivos(f); // llamada recursiva
            } else {
                total++;
            }
        }
        return total;
    }
}
```

### Ejemplo conceptual
Si tienes:

```
/misArchivos
  /fotos
     a.jpg
     b.jpg
  /docs
     cv.pdf
  nota.txt
```

Resultado:
- `contarArchivos(misArchivos)` → `4`

---

# 🧭 Cómo “diseñar” una solución recursiva (receta rápida)

Cuando no sabes por dónde empezar, usa esta receta:

1) **Define el caso base** (lo más pequeño).
2) **Define el caso recursivo** (cómo reducir el problema).
3) Asegúrate de que **cada llamada acerca** al caso base.
4) Prueba con valores pequeños y dibuja la traza (como hicimos en factorial).
5) Si el rendimiento es malo, considera:
   - memoización
   - versión iterativa
   - poda (en backtracking)

---

# 🚨 Errores típicos (y cómo detectarlos)

✅ **1) No hay caso base**
- Síntoma: recursión infinita → `StackOverflowError`.

✅ **2) El caso recursivo no progresa**
- Ejemplo: llamas a `f(n)` desde `f(n)` sin cambiar n.

✅ **3) Caso base incorrecto**
- Devuelve valores erróneos o se detiene demasiado pronto.

✅ **4) Desbordamiento de tipo**
- En factorial/potencias grandes puede haber overflow: usar `long` o `Math.multiplyExact`.

---

# 🧩 Extra: Recursividad vs Iteración (cuándo usar cuál)

- Usa **recursividad** cuando:
  - el problema es jerárquico (árboles, directorios)
  - el problema es de exploración (backtracking)
  - te aporta claridad

- Usa **iteración** cuando:
  - el problema es lineal y muy repetitivo
  - necesitas máximo rendimiento
  - la profundidad puede ser grande

---

</div>