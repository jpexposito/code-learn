<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Cazadores de Monstruos en Threads)

## Descripción del Juego

Imagina un mundo lleno de monstruos y cazadores. El objetivo del juego es que cada cazador intente atrapar tantos monstruos como sea posible en un tiempo limitado. Cada cazador se moverá aleatoriamente por el mapa y tendrá una tasa de éxito para atrapar un monstruo cuando se encuentre en la misma ubicación que uno. Cada cazador también tendrá un tiempo de espera entre cada intento de captura, lo que simula la acción de cazar.

## Requisitos del Juego

1. **Clases Principales**:
   - `Cazador`: Representa a un cazador que se mueve por el mapa y puede intentar atrapar monstruos.
   - `Monstruo`: Representa a un monstruo que aparece en ubicaciones aleatorias en el mapa.
   - `Mapa`: Una clase que mantiene el estado del mapa, incluyendo la ubicación de los monstruos y los cazadores.

2. **Características**:
   - Cada cazador debe ser un hilo (`Thread`) que se mueve por el mapa en intervalos de tiempo aleatorios.
   - Los monstruos deben aparecer en el mapa en ubicaciones aleatorias y deben desaparecer después de un tiempo si no son atrapados.
   - Los cazadores intentan atrapar monstruos en su ubicación actual y tienen una tasa de éxito (por ejemplo, 70% de probabilidad de atrapar un monstruo).
   - El juego debe finalizar después de un tiempo determinado, y se debe mostrar la cantidad total de monstruos atrapados por cada cazador.

3. **Interacción**:
   - Los cazadores y los monstruos deben interactuar en el mapa: si un cazador y un monstruo se encuentran en la misma ubicación, el cazador intenta atrapar al monstruo.

## Implementación

La implementación puede seguir los siguientes pasos:

1. **Crear las Clases**:
   - Implementar las clases `Cazador`, `Monstruo`, y `Mapa`.
   - La clase `Cazador` debe extender `Thread` y contener la lógica de movimiento y captura de monstruos.
   - La clase `Monstruo` puede ser una clase simple que tenga propiedades como su ubicación y estado (atrapado o no).
   - La clase `Mapa` debe gestionar la posición de los cazadores y los monstruos.

2. **Lógica de Juego**:
   - En el método `run()` de la clase `Cazador`, implementar la lógica de movimiento aleatorio y captura de monstruos.
   - Usar `Thread.sleep()` para simular el tiempo de espera entre intentos de captura.
   - Generar monstruos en el mapa de forma aleatoria y permitir que los cazadores intenten atraparlos.

3. **Finalización del Juego**:
   - Implementar un temporizador para finalizar el juego después de un tiempo determinado y mostrar el resultado final.

La implementación del juego a través de **threads** es la siguiente:

```java
import java.util.Random;

class Mapa {
    // Lógica del mapa y gestión de monstruos
}

class Monstruo {
    // Propiedades del monstruo
}

class Cazador extends Thread {
    private String nombre;
    private int monstruosAtrapados = 0;

    public Cazador(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        while (true) {
            // Lógica de movimiento
            // Intentar atrapar un monstruo
            // Aumentar el contador de monstruos atrapados
            try {
                Thread.sleep(new Random().nextInt(1000)); // Espera aleatoria
            } catch (InterruptedException e) {
                break; // Terminar el hilo si es interrumpido
            }
        }
    }
}

public class JuegoCazadores {
    public static void main(String[] args) {
        // Crear instancias de cazadores y comenzar el juego
    }
}
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>