<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Introducción a la programación. Algoritmia)

## 1. Introducción

La programación es el arte y la ciencia de dar instrucciones a un ordenador para que realice tareas específicas. A través de ella podemos resolver problemas, automatizar procesos y crear aplicaciones que mejoran la vida cotidiana. Para entenderla, es necesario conocer no solo los lenguajes, sino también los paradigmas, fases y técnicas que intervienen en el desarrollo de software.

---

## 2. Programas y programación

### 2.1 Buscando una solución

Todo proceso de programación nace de un problema que se quiere resolver. El programador debe analizarlo, comprenderlo y diseñar una estrategia para solucionarlo de forma eficiente y clara.

### 2.2 Algoritmos y programas

Un **algoritmo** es un conjunto ordenado y finito de pasos que resuelven un problema.  
Un **programa** es la implementación de ese algoritmo en un lenguaje de programación para que el ordenador lo ejecute.

<img src=images/programas.png width="300">

### 2.3 ¿En qué consiste la programación?

La programación consiste en traducir ideas y soluciones en instrucciones precisas que una máquina pueda entender. Implica lógica, creatividad, disciplina y un enfoque sistemático.

---

## 3. Paradigmas de la programación

Un **paradigma de programación** es un estilo o modelo que define la forma en que los programadores estructuran y organizan su código.  
Cada paradigma responde a diferentes necesidades y proporciona herramientas conceptuales distintas para resolver problemas.

- **Programación estructurada**  
  Se basa en dividir el código en bloques secuenciales que utilizan estructuras de control como **condiciones** y **bucles**.  
  Elimina el uso excesivo del "goto" de los primeros lenguajes, mejorando la claridad.  
  *Ejemplo:* dividir un programa en funciones como `calcularSuma()` o `mostrarResultado()`.

- **Programación orientada a objetos (POO)**  
  Representa entidades del mundo real como **objetos** que tienen **atributos** (datos) y **métodos** (acciones).  
  Se apoya en principios como:  
  - *Encapsulación*: ocultar detalles internos.  
  - *Herencia*: reutilizar código de clases existentes.  
  - *Polimorfismo*: usar métodos con el mismo nombre que se comportan diferente.  
  *Ejemplo:* Una clase `Vehiculo` con subclases `Auto` y `Moto`.

- **Programación funcional**  
  Basada en la idea matemática de funciones.  
  Evita modificar variables globales y estados, promoviendo código más seguro y predecible.  
  *Ejemplo:* En Python, usar funciones puras como `map()`, `filter()` o `reduce()`.

- **Programación lógica**  
  Se basa en reglas y hechos para que la máquina deduzca soluciones por sí misma.  
  Usada en inteligencia artificial y resolución de problemas complejos.  
  *Ejemplo:* En Prolog, definir hechos como `padre(juan, maria).` y reglas para consultar parentescos.

---

## 4. Fases de la programación

Desarrollar un programa requiere seguir fases bien estructuradas:  

1. **Análisis del problema**  
   - Identificar lo que se desea resolver.  
   - Determinar entradas (datos iniciales) y salidas (resultados esperados).  

2. **Diseño del algoritmo**  
   - Plasmar la solución paso a paso en forma de **pseudocódigo** o **diagramas de flujo**.  

3. **Codificación en un lenguaje**  
   - Traducir el algoritmo al lenguaje elegido (Python, C, Java, etc.).  

4. **Prueba y depuración**  
   - Ejecutar el programa para detectar errores (bugs).  
   - Ajustar y corregir hasta lograr la funcionalidad deseada.  

5. **Documentación**  
   - Explicar el código, decisiones de diseño y cómo usar el programa.  
   - Facilita la comprensión a otros programadores.  

6. **Mantenimiento y mejora**  
   - Actualizar el software para añadir funciones, optimizar rendimiento o corregir fallos futuros.  

---

## 5. Ciclo de vida del software

El **ciclo de vida** de un software describe todas las etapas desde que se concibe hasta que deja de usarse:  

1. **Análisis de requisitos**  
   - Reunir información del cliente o usuario final.  
   - Documentar qué se espera que haga el software.  

2. **Diseño**  
   - Crear diagramas y arquitecturas.  
   - Definir cómo interactúan los módulos.  

3. **Implementación (programación)**  
   - Escribir el código siguiendo el diseño.  

4. **Pruebas**  
   - Validar que cumple con los requisitos.  
   - Hacer pruebas unitarias, de integración y de aceptación.  

5. **Implantación**  
   - Instalar el sistema en el entorno real.  
   - Capacitar a los usuarios finales.  

6. **Mantenimiento**  
   - Corregir errores que aparezcan.  
   - Adaptar el software a nuevas necesidades o entornos tecnológicos.  

---

## 6. Técnicas de programación

Son estrategias que ayudan a programar de manera más eficiente:  

- **Programación modular**  
  - Dividir un sistema grande en módulos más pequeños e independientes.  
  - Cada módulo se encarga de una tarea específica.  

- **Programación estructurada**  
  - Uso de estructuras claras como secuencia, selección y repetición.  
  - Permite un flujo lógico más fácil de entender y depurar.  

- **Programación orientada a objetos**  
  - Ideal para proyectos grandes y escalables.  
  - Facilita la reutilización del código y la abstracción de datos.  

- **Programación ágil**  
  - Basada en ciclos cortos de desarrollo (sprints).  
  - Promueve la colaboración con el cliente y la adaptación a cambios.  
  - Metodologías como **Scrum** o **Kanban** son comunes.  

---

## 7. Fases en la creación de un programa

La **creación de un programa** sigue un flujo ordenado:  

1. **Planteamiento del problema**: definir con claridad qué se desea resolver.  
2. **Diseño de la solución**: crear algoritmos, diagramas de flujo o pseudocódigo.  
3. **Elección del lenguaje**: seleccionar el más adecuado según el proyecto (ej. C para sistemas, Python para ciencia de datos).  
4. **Codificación**: traducir el diseño al lenguaje elegido.  
5. **Verificación y pruebas**: comprobar que funciona correctamente y cumple requisitos.  
6. **Documentación**: explicar funciones, uso y estructura del programa.  
7. **Distribución y mantenimiento**: entregar el software y garantizar su funcionamiento a lo largo del tiempo.  

---

## 8. Lenguajes de programación

Los lenguajes son herramientas que permiten expresar algoritmos en instrucciones que entiende la computadora. Se dividen en distintos niveles:  

### 8.1 Lenguaje máquina

- Compuesto exclusivamente por **ceros y unos** (binario).  
- Directamente entendido por la CPU.  
- Muy eficiente pero impracticable para el ser humano.  

#### 8.2 Lenguaje ensamblador

- Usa **mnemónicos** para representar instrucciones de máquina.  
- Depende del tipo de procesador (x86, ARM, etc.).  
- Ejemplo: `MOV AX, 5` para mover el valor 5 al registro AX.  

#### 8.3 Lenguajes compilados

- El programa se **traduce completo** a lenguaje máquina mediante un compilador antes de ejecutarse.  
- Ofrecen gran velocidad en ejecución.  
- Ejemplos: **Java,C, C++, Rust, Go**.  

#### 8.4 Lenguajes interpretados

- Se **ejecutan línea a línea** mediante un intérprete.  
- Permiten gran flexibilidad y rapidez en el desarrollo.  
- Más lentos que los compilados, pero ideales para scripting y aplicaciones dinámicas.  
- Ejemplos: **Python, JavaScript, PHP, Ruby**.  

---

## 9. Pseudocódigo y resolución de problemas

El **pseudocódigo** es una herramienta que permite describir algoritmos de manera informal, utilizando un lenguaje cercano al natural, pero con una estructura lógica semejante a la de los lenguajes de programación.  
Su objetivo es ayudar a **planificar** y **organizar** las soluciones antes de codificarlas en un lenguaje real.  
No sigue reglas estrictas de sintaxis, lo que lo hace flexible y fácil de entender para programadores y no programadores.

---

### 9.1 Variables

Las **variables** son espacios de memoria donde se guardan valores que pueden cambiar durante la ejecución del programa.  
En pseudocódigo se definen indicando su tipo y nombre, y se les puede asignar un valor inicial.

```code
entero edad ← 25
```

### 9.2 Condiciones

Las **estructuras condicionales** permiten que un algoritmo tome decisiones.  
Su función es evaluar una expresión lógica y, en función de si es **verdadera** o **falsa**, ejecutar diferentes instrucciones.

```code
si edad ≥ 18 entonces
    mostrar "Eres mayor de edad"
fin si
```

---

### 9.3 Bucles

Los **bucles** son estructuras de control que repiten un conjunto de instrucciones hasta que se cumpla una condición o un número de repeticiones.  
Existen diferentes tipos de bucles, como los definidos por un contador (para) o los controlados por una condición (mientras).

```code
para i desde 1 hasta 5 hacer
    mostrar i
fin para
```

---

### 9.4 Funciones

Las **funciones** son bloques de instrucciones que realizan una tarea específica.  
Se utilizan para dividir el programa en partes más pequeñas y reutilizables, lo que facilita la legibilidad y el mantenimiento del código.

```code
funcion suma(a, b)
    retornar a + b
fin funcion
```

---

### 9.5 Arrays

Los **arrays** o **vectores** son estructuras de datos que permiten almacenar varios valores bajo un mismo nombre de variable.  
Cada elemento se identifica mediante un índice, lo que facilita el acceso y la manipulación de conjuntos de datos.

```code
numeros ← [2, 4, 6, 8]
mostrar numeros[2]   // imprime 6
```

#### Problema 1

Diseñar un algoritmo que pida al usuario su edad y muestre un mensaje indicando si es mayor o menor de edad.  

#### Problema 2

Diseñar un algoritmo que pida un número N y calcule la suma de los primeros N números naturales.

#### Problema 3

Diseñar un algoritmo que pida la cantidad de calificaciones de un estudiante, las guarde en un array, y calcule el promedio.  
Luego, mostrar si el estudiante **aprueba** (promedio ≥ 6) o **reprueba** (promedio < 6).


</div>