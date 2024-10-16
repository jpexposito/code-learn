<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Programación de Servicios)

## ThreadPool en Java

<div align="center">
<img src=images/threadPool2.png width="400">
</div>

En Java, un **ThreadPool** (o pool de hilos) es un conjunto de hilos reutilizables que ejecutan tareas enviadas por un programa. Esto es gestionado por la clase `ThreadPoolExecutor` y otras utilidades en el paquete `java.util.concurrent`. Un **ThreadPool** permite mejorar la eficiencia de la aplicación reutilizando hilos y evitando la sobrecarga de crear y destruir hilos de manera constante.

### Beneficios de un ThreadPool

- **Reutilización de hilos**: Los hilos pueden ser reutilizados para ejecutar múltiples tareas, lo que mejora el rendimiento y reduce la sobrecarga de la gestión de hilos.
- **Control de concurrencia**: Permite limitar la cantidad de hilos ejecutándose simultáneamente, lo que ayuda a evitar sobrecargas del sistema.
- **Mejor manejo de tareas**: Las tareas se pueden añadir a una cola y los hilos las ejecutan a medida que están disponibles.

### Clases relacionadas con ThreadPool

- **`ExecutorService`**: Es una interfaz que representa un pool de hilos. Se utiliza para administrar la ejecución de tareas concurrentes.
- **`Executors`**: Es una clase de fábrica que proporciona métodos estáticos para crear instancias de `ExecutorService`, como `newFixedThreadPool`, `newCachedThreadPool`, etc.
- **`ThreadPoolExecutor`**: Es la implementación principal de `ExecutorService` que gestiona directamente el pool de hilos.

### Métodos importantes de `ExecutorService`

- **`submit(Runnable task)`**: Envía una tarea de tipo `Runnable` para que sea ejecutada.
- **`submit(Callable task)`**: Envía una tarea de tipo `Callable` que devuelve un valor futuro (`Future`).
- **`shutdown()`**: Finaliza el pool de hilos de manera ordenada, permitiendo que las tareas en curso se completen.
- **`shutdownNow()`**: Intenta finalizar inmediatamente las tareas y detiene los hilos.
- **`awaitTermination(long timeout, TimeUnit unit)`**: Espera hasta que todas las tareas se completen o hasta que expire el tiempo límite.

### Creación de un ThreadPool

#### Tipos de ThreadPools

- **Fixed Thread Pool**: Un pool de hilos con un número fijo de hilos. Útil cuando se conoce la cantidad máxima de tareas que se ejecutarán simultáneamente.
- **Cached Thread Pool**: Crea nuevos hilos según sea necesario y reutiliza los hilos inactivos si están disponibles. Es útil para ejecutar muchas tareas cortas.
- **Single Thread Executor**: Un pool que usa un único hilo para ejecutar todas las tareas secuencialmente.

### Ejemplo de uso de un ThreadPool

#### Crear un Fixed Thread Pool

Aquí un ejemplo básico de cómo crear un **Fixed Thread Pool** con 3 hilos y enviar varias tareas al pool:

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolEjemplo {
    public static void main(String[] args) {
        // Creamos un ThreadPool con 3 hilos fijos
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        // Creamos 5 tareas de ejemplo
        for (int i = 1; i <= 5; i++) {
            Runnable tarea = new Tarea("Tarea " + i);
            threadPool.submit(tarea);  // Enviamos las tareas al ThreadPool
        }

        // Cerramos el ThreadPool de forma ordenada después de completar las tareas
        threadPool.shutdown();
    }

    static class Tarea implements Runnable {
        private String nombre;

        public Tarea(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            System.out.println(nombre + " está siendo ejecutada por el hilo " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);  // Simulamos que la tarea toma 2 segundos en ejecutarse
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(nombre + " ha terminado.");
        }
    }
}
```

# Explicación del código de ThreadPool en Java

En este ejemplo se muestra cómo crear un **ThreadPool** en Java utilizando `ExecutorService` y cómo enviar tareas para que sean ejecutadas por los hilos del pool.

### `ExecutorService threadPool = Executors.newFixedThreadPool(3);`

- Este método crea un **Fixed Thread Pool** con 3 hilos. Esto significa que un máximo de **3 hilos** pueden estar ejecutando tareas simultáneamente.
- Si se envían más tareas de las que hay hilos disponibles (en este caso, más de 3), las tareas adicionales serán **colocadas en cola** y esperarán hasta que un hilo esté disponible.

### `threadPool.submit(tarea);`

- Aquí utilizamos el método `submit()` del `ExecutorService` para enviar la tarea (de tipo `Runnable`) al pool de hilos.
- El pool asignará uno de los hilos disponibles para ejecutar la tarea. Si todos los hilos están ocupados, la tarea esperará en la cola.

### `threadPool.shutdown();`

- El método `shutdown()` se utiliza para **cerrar el pool de hilos** de manera ordenada. Esto significa que:
  - No se aceptarán más tareas.
  - Las tareas que ya están en ejecución o en la cola se completarán antes de que el pool se cierre completamente.
  
- Es importante llamar a `shutdown()` para liberar los recursos asociados al pool de hilos una vez que las tareas hayan terminado.

### La clase `Tarea` implementa `Runnable`

- La clase `Tarea` representa una tarea que se ejecutará en uno de los hilos del pool.
- El método `run()` de `Runnable` es donde definimos el comportamiento de la tarea. En este caso:
  - Se imprime un mensaje que indica que la tarea ha comenzado.
  - La tarea duerme durante 2 segundos (`Thread.sleep(2000);`) para simular una tarea que toma tiempo.
  - Luego se imprime un mensaje que indica que la tarea ha terminado.

### Flujo de ejecución del programa

1. **Creación del ThreadPool**: Se crea un pool de hilos con 3 hilos mediante `Executors.newFixedThreadPool(3)`.
2. **Envío de tareas**: Se crean 5 tareas (`Tarea 1` a `Tarea 5`) y se envían al pool utilizando el método `submit()`. Aunque hay 5 tareas, solo 3 hilos pueden ejecutarlas simultáneamente debido al límite del pool.
3. **Ejecución de tareas**:
   - Las primeras 3 tareas se ejecutan inmediatamente porque hay 3 hilos disponibles.
   - Las tareas 4 y 5 esperarán en la cola hasta que uno de los 3 hilos termine su tarea y esté disponible.
4. **Finalización de tareas**:
   - Cada tarea tarda 2 segundos en completarse.
   - Una vez que una tarea termina, el hilo que la ejecutaba queda libre y toma la siguiente tarea en la cola.
5. **Cierre del pool**: Finalmente, el pool se cierra de manera ordenada con `shutdown()`, permitiendo que las tareas en curso se terminen antes de liberar los recursos.

### Ejemplo del resultado esperado

El resultado esperado del programa sería algo similar a lo siguiente:

```code
Tarea 1 está siendo ejecutada por el hilo pool-1-thread-1 Tarea 2 está siendo ejecutada por el hilo pool-1-thread-2 Tarea 3 está siendo ejecutada por el hilo pool-1-thread-3 Tarea 1 ha terminado. Tarea 4 está siendo ejecutada por el hilo pool-1-thread-1 Tarea 2 ha terminado. Tarea 5 está siendo ejecutada por el hilo pool-1-thread-2 Tarea 3 ha terminado. Tarea 4 ha terminado. Tarea 5 ha terminado.
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>