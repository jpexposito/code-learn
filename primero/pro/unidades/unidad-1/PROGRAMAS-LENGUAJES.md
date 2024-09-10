<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Programas, lenguajes y compiladores)

## Conceptos iniciales

### Software

Si buscamos la definición de software en Internet, podemos encontrar algunas de ellas, aunque básicamente son las mismas. Llamamos software al conjunto de programas, documentación y datos estrechamente relacionados entre sí para conformar una aplicación informática.

Cada producto software es (o puede ser) diferente al resto, porque está desarrollado para un cliente distinto, o para cumplir un propósito distinto. Por tanto, desarrollar ese producto implica unos pasos previos: entender lo que tenemos que hacer, hacer un diseño previo e implementarlo, como veremos más adelante. Por tanto, no podemos comparar el desarrollo de software con la producción industrial (como la fabricación de teclados, por ejemplo), donde todo está mucho más automatizado. El desarrollo de software requiere la creación de un proyecto de software, y un grupo de personas trabajando de forma coordinada. Además, el software no se estropea con el tiempo, aunque sí puede reducir su rendimiento por sus propias actualizaciones y mejoras.

### Componentes del software

De la definición anterior de software, podemos deducir que está compuesto de tres elementos:

- __Programas__ : conjuntos de instrucciones que proporcionan la funcionalidad deseada. Están escritos en un lenguaje de programación específico.
__Datos__ : los programas necesitan datos para trabajar con ellos. Estos datos se pueden recuperar y/o almacenar desde bases de datos o archivos.
__Documentación__ : los documentos del software explican cómo usarlo, qué elementos lo forman y cómo están interconectados, para que pueda actualizarse o corregirse en el futuro.

### Tipos de software

Hay dos tipos principales de software:

- __Software de aplicación__ : aportan algún tipo de servicio al cliente, como procesadores de texto, hojas de cálculo, dibujo, etc. Dentro de este tipo de software podemos encontrar algunas subcategorías, como software de gestión (nóminas, contabilidad), software de ingeniería o científico (CAD, simuladores…), software de red (navegadores web, clientes o servidores FTP…), etc.
- __Software de sistema__ : gestionan un sistema informático. Básicamente son programas que dan soporte o ayudan a otros programas. En esta categoría podemos hablar de sistemas operativos o compiladores, por ejemplo.

## Lenguajes y compiladores

Hemos visto que los programas son conjuntos de instrucciones que se le proporcionan a un ordenador para que realice una tarea. Estas instrucciones están escritas en un lenguaje de programación de nuestra elección, y de esta manera creamos unos archivos de texto llamados código fuente , escritos en el lenguaje elegido.

#### Tipos de lenguaje

Cuando queremos elegir un lenguaje de programación específico, distinguimos entre lenguajes de alto nivel (cercanos al lenguaje humano, y por tanto, más fáciles de entender por los programadores), y lenguajes de bajo nivel (cercanos al lenguaje máquina, y por tanto, más difíciles de entender por los programadores, pero más eficientes).

Disponemos de una gran variedad de lenguajes de alto nivel entre los que elegir, dependiendo del tipo de aplicación que queramos implementar. Podemos hablar de C, C++, C#, Java, Javascript, PHP, Python, etc.
Entre los lenguajes de bajo nivel , quizás el más popular sea el lenguaje ensamblador, un conjunto muy concreto de instrucciones que se traducen fácilmente al lenguaje máquina.
Aquí podemos ver un programa simple escrito en Java que simplemente imprime “Hola” en la pantalla:

```java
public class Test {
    public static void main(String[] args)
    {
        System.out.println("Hello");
    }
}
```

El mismo programa escrito en C podría verse más o menos así:

```c
#include <stdio.h>

int main(){
    printf("Hello");
    return 0;
}
```

### Procesadores de lenguaje

Las computadoras no pueden entender ninguno de los lenguajes de programación que los humanos usan para crear sus programas. Para que funcionen, sus instrucciones deben traducirse a un lenguaje que las computadoras puedan entender. Este lenguaje se llama código de máquina y está compuesto de bits (ceros y unos).

Si queremos traducir un lenguaje de programación determinado a código máquina, utilizamos una herramienta llamada compilador , aunque esta afirmación no es del todo cierta. Existen diferentes procesadores de lenguaje que se pueden utilizar, dependiendo del lenguaje en sí:

- __Compiladores__ : traducen el código escrito en un lenguaje específico a código máquina y generan un archivo ejecutable con el resultado. Por ejemplo, si compilamos un programa escrito en C bajo Windows, obtendremos un archivo EXE que podremos ejecutar.
Intérpretes : traducen del lenguaje de programación especificado a código máquina “al vuelo”. Es decir, no generan ningún archivo ejecutable. Por lo que, cada vez que necesitemos ejecutar el programa, necesitaremos tener disponible el archivo fuente. Este tipo de procesadores de lenguaje son muy habituales en lenguajes como PHP o Python. De esta forma, el tiempo de respuesta aumenta un poco, pero el programa puede ejecutarse en múltiples plataformas.
- __Máquinas virtuales__ : una solución intermedia entre compiladores e intérpretes es la que utilizan lenguajes como Java. Estos programas no se compilan a código máquina nativo (en Java no existe ningún archivo EXE , por ejemplo), ni se interpretan. Java compila el código fuente y lo traduce a su propio código máquina intermedio. Después, ejecuta su máquina virtual (JVM, Java Virtual Machine ), que se encarga de interpretar y ejecutar ese código cada vez que lo necesitemos. De esta forma, no necesitamos tener disponible el código fuente antes de ejecutar el programa, ni dependemos de una plataforma determinada (Windows, Linux, etc.). Tan solo necesitamos tener una JVM instalada en nuestro sistema para poder ejecutar nuestros programas Java. Lo mismo ocurre con C# y su plataforma virtual .NET .

### Algunos idiomas populares

Existen algunos estudios y análisis que intentan clasificar los lenguajes de programación según su popularidad o uso actual. Algunos de los más populares son:

- Índice TIOBE.
- Clasificación de RedMonk.

En cuanto a este último, se basa en cruzar datos entre el repositorio principal de código fuente __(GitHub)__ y la página principal de ayuda de programación __(StackOverflow)__. Echa un vistazo a ambos rankings, comprueba los resultados y comprueba si difieren en algún lenguaje importante. como Java, C, Python, JavaScript o PHP.

## Editores Online

- [Ideone](https://www.ideone.com/).

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles

</div>