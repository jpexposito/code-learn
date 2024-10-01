<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Proramación Multihilo
)

<div align="center">

<img src=images/threads.png width="400">

</div>

Tras aprender los conceptos básicos de la programación concurrente y ver cómo los procesos pueden colaborar para conseguir __multitarea__, en este tema vamos a poner la mirada ___dentro de un proceso___.

La ___ejecución de un proceso comienza con un único hilo___, pero se pueden _crear más sobre la marcha_. Los distintos hilos de un mismo proceso comparten:

- El _espacio_ de __memoria asignado al proceso__.
- La _información_ de acceso a __ficheros (incluyendo stdin, stdout y stderr)__.

> ___Las características anteriores son las que los diferencias de los procesos___.

En cambio, cada hilo tiene sus propios valores para:

- Los registros del procesador.
- El estado de su pila (stack), donde entre otras cosas se almacenan las variables locales.

> ___Por lo tanto, vamos a utilizar los threads para realizar programación concurrente y/o paralela dentro de un proceso___.

Aunque ___los hilos se ejecutan en el contexto de un proceso, cada uno tiene su TCB (Tread Control Block) que es sensiblemente más pequeños que el PCB (Process Control Block) porque entre los hilos comparten gran parte de ese PCB___. Por eso veremos que a los hilos también se le llama __lightweight processes__ (_procesos ligeros_) y por tanto ___los cambios de contexto en el procesador son mucho menos costosos para los hilos que para los procesos___.

> __Hilos: comunicación vs sincronización__
>
>Por todo lo comentado, el intercambio de información entre hilos es sencillo, dado que los distintos hilos de un mismo proceso comparten la memoria asignada al proceso.
>
>Sin embargo, los hilos deben coordinarse para el acceso a los contenidos de la memoria y a los ficheros, lo cual hace que esa coordinación y sincronización sea la parte complicada de uso.

[Hilos en Java](HILOS-JAVA.md)

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>