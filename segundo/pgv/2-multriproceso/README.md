<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Programación de Servicios)

## Proramación Multiproceso

<div align="center">

<img src=images/procesos.png width="400">

</div>

Una vez hemos aprendido a diferenciar entre programas, procesos e hilos, en este segundo tema vamos a aprender cómo desde un programa creado por nosotros podemos lanzar otros programas, es decir, desde un proceso en ejecución, podemos crear otro proceso.

Además de lanzarlos, al establecerse una relación padre-hijo estos procesos pueden comunicarse entre sí intercambiando información. De esta forma nuestros programas podrán lanzar otras aplicaciones, comandos del SO e incluso otras aplicaciones nuestras, permitiendo cierto grado de sincronización y comunicación entre ellas.

## Objetivos

Los objetivos que alcanzaremos tras esta unidad son:

- Conocer las clases de Java para la creación de procesos
- Monitorizar y controlar el ciclo de vida de un proceso
- Controlar la comunicación entre procesos padre/hijo
Usar métodos para la sincronización entre procesos y subprocesos
- Entender el mecanismo de comunicación mediante tuberías (pipes)
- Aprender la sintaxis y uso del comando curl para probar API REST desde un programa
- Crear programas que ejecuten tareas en paralelo.

## Estados de un proceso

# Estados de un Proceso

1. **Nuevo (New)**:  
   El proceso ha sido creado, pero aún no está listo para ser ejecutado. Está en la cola de nuevos procesos.

2. **Listo (Ready)**:  
   El proceso está en la memoria principal, esperando a ser asignado a la CPU para ejecutarse.

3. **En ejecución (Running)**:  
   El proceso está siendo ejecutado por la CPU. Solo un proceso puede estar en este estado por CPU en un momento dado.

4. **Bloqueado/Espera (Blocked/Waiting)**:  
   El proceso no puede continuar hasta que ocurra algún evento externo, como la finalización de una operación de E/S o la recepción de datos.

5. **Terminado (Terminated/Exit)**:  
   El proceso ha finalizado su ejecución, ya sea de manera normal o debido a algún error.

6. **Suspendido (Suspended)**:  
   El proceso ha sido trasladado a la memoria secundaria, lo que implica que ha sido pausado temporalmente para liberar recursos.

## Transiciones entre estados

| Estado Actual                      | Evento                                         | Nuevo Estado                   |
|-----------------------------------------------------|------------------------------------------------|-------------------------------------------------|
| **Nuevo** (**New**)                               | Asignación de recursos                        | **Listo** (**Ready**)                          |
| **Listo** (**Ready**)                             | Asignación de CPU por el planificador          | **En ejecución** (**Running**)                 |
| **En ejecución** (**Running**)                    | Espera de un evento externo                   | **Bloqueado** (**Blocked/Waiting**)             |
| **En ejecución** (**Running**)                    | Interrupción para dar la CPU a otro proceso   | **Listo** (**Ready**)                          |
| **Bloqueado** (**Blocked/Waiting**)                | Ocurre el evento esperado                     | **Listo** (**Ready**)                          |
| **En ejecución** (**Running**)                    | Terminación del proceso                       | **Terminado** (**Terminated/Exit**)            |
| **Listo** o **Bloqueado** (**Ready** or **Blocked/Waiting**) | Movimiento a memoria secundaria               | **Suspendido** (**Suspended**)                 |
| **Suspendido** (**Suspended**)                    | Regreso a memoria principal                   | **Listo** (**Ready**) o **Bloqueado** (**Blocked/Waiting**) |

[Procesos en Java](PROCESOS-JAVA.md)

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>