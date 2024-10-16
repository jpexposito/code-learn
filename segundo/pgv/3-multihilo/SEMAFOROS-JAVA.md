<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Programación de Servicios)

## Semáforos en Java

<div align="center">

<img src=images/semaforo.png width="400">
</div>

Java tiene soporte para semáforos a través de la clase `Semaphore`, que forma parte del paquete `java.util.concurrent`. Los semáforos son mecanismos de sincronización que permiten controlar el acceso a un recurso compartido por varios hilos.

### Descripción general de la clase `Semaphore`

Un **semáforo** es una herramienta que controla el acceso a recursos compartidos, utilizando un contador interno para permitir o bloquear el acceso de hilos. En Java, el semáforo puede tener uno o más permisos:

- **Semáforo binario**: Tiene solo dos estados: disponible o ocupado, funcionando de manera similar a un lock o cerrojo.
- **Semáforo con más de un permiso**: Se utiliza cuando se permite que varios hilos accedan simultáneamente a un recurso, pero hay un límite en la cantidad de accesos concurrentes.

### Constructores de `Semaphore`

1. `Semaphore(int permits)`: Crea un semáforo con un número inicial de permisos. Los permisos determinan cuántos hilos pueden acceder al recurso al mismo tiempo.
2. `Semaphore(int permits, boolean fair)`: Crea un semáforo con un número de permisos y un indicador de equidad. Si `fair` es `true`, los hilos accederán al recurso en orden de llegada (FIFO), garantizando equidad.

### Métodos importantes de `Semaphore`

- **`void acquire()`**: Adquiere un permiso del semáforo, bloqueando si no hay permisos disponibles.
- **`void acquire(int permits)`**: Adquiere la cantidad especificada de permisos.
- **`void release()`**: Libera un permiso, devolviéndolo al semáforo.
- **`void release(int permits)`**: Libera varios permisos.
- **`int availablePermits()`**: Devuelve el número de permisos disponibles en el semáforo.
- **`boolean tryAcquire()`**: Intenta adquirir un permiso sin bloquear. Devuelve `true` si tiene éxito, de lo contrario, devuelve `false`.

### Ejemplo básico de uso

Este es un ejemplo simple en el que varios hilos intentan acceder a un recurso limitado controlado por un semáforo:

```java
import java.util.concurrent.Semaphore;

public class SemaforoEjemplo {
    // Creamos un semáforo con 3 permisos (máximo 3 hilos pueden acceder simultáneamente)
    private static final Semaphore semaforo = new Semaphore(3);

    public static void main(String[] args) {
        // Creamos varios hilos para simular el acceso concurrente a un recurso
        for (int i = 1; i <= 5; i++) {
            new Trabajador("Trabajador " + i).start();
        }
    }

    static class Trabajador extends Thread {
        private String nombre;

        public Trabajador(String nombre) {
            this.nombre = nombre;
        }

        public void run() {
            try {
                System.out.println(nombre + " esperando para acceder al recurso...");
                
                // Adquirimos un permiso del semáforo
                semaforo.acquire();
                System.out.println(nombre + " ha obtenido acceso al recurso.");
                
                // Simulamos trabajo con el recurso
                Thread.sleep(2000);

                // Liberamos el permiso después de usar el recurso
                System.out.println(nombre + " ha terminado y libera el recurso.");
                semaforo.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

#### Explicación del código de semáforo en Java

En este ejemplo, se muestra cómo utilizar un semáforo en Java para controlar el acceso concurrente a un recurso limitado.

#####  `Semaphore semaforo = new Semaphore(3);`

Este semáforo permite que solo **3 hilos** accedan simultáneamente al recurso. El número pasado como argumento (en este caso, 3) define la cantidad de **permisos** disponibles. Cada permiso representa la posibilidad de que un hilo acceda al recurso.

##### `semaforo.acquire()`

Cada hilo que quiera acceder al recurso debe **adquirir un permiso** del semáforo. Si hay permisos disponibles, el hilo puede continuar. Si no hay permisos disponibles, el hilo **se bloqueará** y esperará hasta que un permiso esté disponible.

##### `semaforo.release()`

Después de que un hilo termina de usar el recurso, debe liberar el permiso que adquirió. Esto se hace llamando al método `release()`. Una vez que el permiso es liberado, otro hilo bloqueado puede adquirirlo y acceder al recurso.

##### Simulación del uso del recurso

En el código, cada hilo simula que está trabajando con el recurso utilizando `Thread.sleep(2000);`, lo que hace que el hilo se duerma durante 2 segundos para simular que está utilizando el recurso. Después de ese tiempo, el hilo libera el permiso.

### Flujo del programa

1. Se crean **5 hilos** en total.
2. Cada hilo intenta adquirir un permiso del semáforo.
3. Solo **3 hilos** pueden acceder al recurso al mismo tiempo debido al límite de permisos del semáforo.
4. Cuando un hilo termina de trabajar con el recurso (después de los 2 segundos), **libera el permiso**, lo que permite a otro hilo bloqueado acceder al recurso.

#### Resultado esperado

El resultado sería algo como lo siguiente:

```bash
Trabajador 1 esperando para acceder al recurso... Trabajador 2 esperando para acceder al recurso... Trabajador 3 esperando para acceder al recurso... Trabajador 1 ha obtenido acceso al recurso. Trabajador 2 ha obtenido acceso al recurso. Trabajador 3 ha obtenido acceso al recurso. Trabajador 4 esperando para acceder al recurso... Trabajador 5 esperando para acceder al recurso... Trabajador 1 ha terminado y libera el recurso. Trabajador 4 ha obtenido acceso al recurso. Trabajador 2 ha terminado y libera el recurso. Trabajador 5 ha obtenido acceso al recurso. Trabajador 3 ha terminado y libera el recurso.
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>