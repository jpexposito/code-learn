<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Introducción a la programación. Algoritmia)

## Introducción

La programación es el arte y la ciencia de dar instrucciones a un ordenador para que realice tareas específicas. A través de ella podemos resolver problemas, automatizar procesos y crear aplicaciones que mejoran la vida cotidiana. Para entenderla, es necesario conocer no solo los lenguajes, sino también los paradigmas, fases y técnicas que intervienen en el desarrollo de software.

---

## 2. Programas y programación

#### 2.1 Buscando una solución

Todo proceso de programación nace de un problema que se quiere resolver. El programador debe analizarlo, comprenderlo y diseñar una estrategia para solucionarlo de forma eficiente y clara.

#### 2.2 Algoritmos y programas

Un **algoritmo** es un conjunto ordenado y finito de pasos que resuelven un problema.  
Un **programa** es la implementación de ese algoritmo en un lenguaje de programación para que el ordenador lo ejecute.

#### 2.3 ¿En qué consiste la programación?

La programación consiste en traducir ideas y soluciones en instrucciones precisas que una máquina pueda entender. Implica lógica, creatividad, disciplina y un enfoque sistemático.

---

## 3. Paradigmas de la programación

Los paradigmas son estilos o enfoques de programación. Algunos de los más importantes son:

- **Programación estructurada**: basada en bloques, secuencias, bucles y condiciones.
- **Programación orientada a objetos**: utiliza clases, objetos, herencia y encapsulación.
- **Programación funcional**: se centra en funciones matemáticas y evita estados mutables.
- **Programación lógica**: se basa en reglas y hechos para deducir soluciones.

---

## 4. Fases de la programación

Las fases principales son:

1. Análisis del problema.  
2. Diseño del algoritmo.  
3. Codificación en un lenguaje.  
4. Prueba y depuración.  
5. Documentación.  
6. Mantenimiento y mejora.

---

## 5. Ciclo de vida del software

El ciclo de vida describe todo el proceso desde que surge la necesidad hasta que el software deja de usarse:

1. Análisis de requisitos.  
2. Diseño.  
3. Implementación.  
4. Pruebas.  
5. Implantación.  
6. Mantenimiento.  

---

## 6. Técnicas de programación

Algunas técnicas empleadas para mejorar el desarrollo son:

- **Programación modular**: dividir el programa en partes pequeñas y manejables.  
- **Programación estructurada**: usar estructuras de control claras.  
- **Programación orientada a objetos**: modelar con clases y objetos.  
- **Programación ágil**: aplicar metodologías iterativas y colaborativas.  

---

## 7. Fases en la creación de un programa

1. Planteamiento del problema.  
2. Diseño de la solución.  
3. Elección del lenguaje.  
4. Codificación.  
5. Verificación y pruebas.  
6. Documentación.  
7. Distribución y mantenimiento.  

---

## 8. Lenguajes de programación

#### 8.1 Lenguaje máquina

Es el lenguaje binario (0s y 1s) que entiende directamente la computadora. Es difícil para los humanos pero esencial para el hardware.

#### 8.2 Lenguaje ensamblador

Es una representación simbólica del lenguaje máquina. Usa mnemónicos para simplificar la programación a bajo nivel.

#### 8.3 Lenguajes compilados

El código fuente se traduce completamente a lenguaje máquina antes de ejecutarse. Ejemplos: C, C++ o Rust.

#### 8.4 Lenguajes interpretados

El código se traduce y ejecuta línea por línea en tiempo real. Ejemplos: ***Java,Python, JavaScript o PHP***.

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