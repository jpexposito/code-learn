<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Programación de Servicios)

## La Subclase Thread

<div align="center">

<img src=images/threads.png width="400">
</div>

En Java, una forma de crear un hilo es extendiendo la clase `Thread`. Para ello, es necesario sobreescribir el método `run()`, que contiene el código que se ejecutará en el nuevo hilo.

```java
class MiHilo extends Thread {
    @Override
    public void run() {
        // Código que se ejecutará en el hilo
        System.out.println("El hilo está en ejecución.");
    }
}

public class Main {
    public static void main(String[] args) {
        MiHilo hilo = new MiHilo();
        hilo.start(); // Inicia el hilo y ejecuta el método run()
    }
}
```

## Subclase Thread o Runnable?

Elegir entre extender la clase `Thread` o implementar la interfaz `Runnable` depende del diseño del programa:

- **Extender `Thread`**: Útil cuando deseas crear una clase especializada de `Thread`. Sin embargo, limita la herencia, ya que en Java solo se puede extender de una clase a la vez.
- **Implementar `Runnable`**: Ofrece más flexibilidad, ya que la clase puede heredar de otras y puede implementar `Runnable` para ejecutar código concurrente.

## Métodos de la clase `java.lang.Thread`

La clase `Thread` proporciona varios métodos clave para gestionar hilos:

- `start()`: Inicia el hilo.
- `run()`: El método donde se define el código que se ejecutará en el hilo.
- `sleep(long millis)`: Suspende el hilo durante el tiempo especificado (en milisegundos).
- `interrupt()`: Interrumpe el hilo si está en ejecución o en espera.
- `join()`: Espera a que un hilo termine su ejecución.
- `isAlive()`: Indica si un hilo está en ejecución.

## Cómo pausar un hilo

Para pausar un hilo, se puede usar el método `sleep(long millis)`, el cual suspende la ejecución del hilo actual por un tiempo determinado.

```java
public class Main {
    public static void main(String[] args) {
        Thread hilo = new Thread(() -> {
            try {
                System.out.println("Hilo en pausa...");
                Thread.sleep(3000); // Pausa el hilo por 3 segundos
                System.out.println("Hilo reanudado.");
            } catch (InterruptedException e) {
                System.out.println("El hilo fue interrumpido.");
            }
        });

        hilo.start();
    }
}
```

## Gestión de la prioridad de los hilos

La prioridad de un hilo se gestiona utilizando los métodos `setPriority(int priority)` y `getPriority()`. Los valores de prioridad pueden variar entre `Thread.MIN_PRIORITY` (1) y `Thread.MAX_PRIORITY` (10), con un valor por defecto de `Thread.NORM_PRIORITY` (5).

```java
public class Main {
    public static void main(String[] args) {
        Thread hilo1 = new Thread(() -> System.out.println("Hilo 1 en ejecución."));
        Thread hilo2 = new Thread(() -> System.out.println("Hilo 2 en ejecución."));

        hilo1.setPriority(Thread.MAX_PRIORITY); // Prioridad máxima
        hilo2.setPriority(Thread.MIN_PRIORITY); // Prioridad mínima

        hilo1.start();
        hilo2.start();
    }
}
```

## Interrupción de un hilo

En Java, **interrumpir un hilo** implica enviar una señal a dicho hilo para que sepa que debe detener su tarea actual o realizar una acción específica. La interrupción no detiene inmediatamente el hilo; ___es responsabilidad del hilo verificar esta interrupción y manejarla adecuadamente___.

Para interrumpir un hilo, utilizamos el método `interrupt()` de la clase `Thread`. Existen dos maneras en que un hilo puede responder a una interrupción:

- **`Thread.currentThread().isInterrupted()`**: Este método permite que el hilo verifique si ha sido interrumpido. El hilo puede utilizar esta verificación periódicamente para decidir si debe continuar o detenerse.
- **`InterruptedException`**: Si el hilo está en espera o durmiendo (usando métodos como `sleep()` o `wait()`), la interrupción generará una excepción `InterruptedException`. Esto le permite al hilo manejar la interrupción adecuadamente.

## Ejemplo típico de interrupción:

1. Llamar al método `interrupt()` para notificar al hilo que debe detenerse.
2. En el código del hilo, verificar el estado de interrupción con `isInterrupted()` o capturar la excepción `InterruptedException`.

```java
class MiHilo extends Thread {
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("El hilo está corriendo.");
                Thread.sleep(1000); // Simula una tarea que tarda
            }
        } catch (InterruptedException e) {
            System.out.println("El hilo fue interrumpido mientras dormía.");
            // El hilo puede salir limpiamente si lo deseamos
        }
        System.out.println("El hilo ha terminado su ejecución.");
    }
}

public class InterrupcionEjemplo {
    public static void main(String[] args) {
        MiHilo hilo = new MiHilo();
        hilo.start();

        try {
            Thread.sleep(3000); // Esperamos 3 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hilo.interrupt(); // Interrumpimos el hilo
        System.out.println("El hilo ha sido interrumpido.");
    }
}
```

## Reanudar un hilo

En Java, no existe un método directo para **reanudar** un hilo que ha sido interrumpido. Una vez interrumpido, el hilo debe manejar la interrupción y terminar su ejecución o realizar una acción específica. Si necesitas un comportamiento de pausa y reanudación, puedes implementar una solución utilizando un **flag booleano**.

## Implementación de pausa y reanudación:

- Un **flag booleano** como `pausado` puede utilizarse para controlar el flujo del hilo.
- Mientras el flag está en `true`, el hilo puede entrar en un estado de espera utilizando `wait()`.
- Para reanudar la ejecución, cambias el flag a `false` y utilizas `notify()` para despertar al hilo y que continúe su ejecución.

```java
class HiloConPausa extends Thread {
    private boolean pausado = false;

    public synchronized void pausar() {
        pausado = true;
    }

    public synchronized void reanudar() {
        pausado = false;
        notify(); // Notifica al hilo para que se despierte
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (pausado) {
                    try {
                        wait(); // El hilo entra en espera
                    } catch (InterruptedException e) {
                        System.out.println("El hilo fue interrumpido.");
                    }
                }
            }
            
            System.out.println("El hilo está corriendo.");
            try {
                Thread.sleep(1000); // Simula una tarea en progreso
            } catch (InterruptedException e) {
                System.out.println("El hilo fue interrumpido mientras dormía.");
                break; // Salimos del bucle si es interrumpido
            }
        }
    }
}

public class PausarReanudarEjemplo {
    public static void main(String[] args) {
        HiloConPausa hilo = new HiloConPausa();
        hilo.start();

        try {
            Thread.sleep(3000); // El hilo corre por 3 segundos
            hilo.pausar(); // Pausamos el hilo
            System.out.println("El hilo está pausado.");

            Thread.sleep(3000); // Esperamos 3 segundos más
            hilo.reanudar(); // Reanudamos el hilo
            System.out.println("El hilo ha sido reanudado.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

## Diferencia entre interrupción y pausa/reanudar

- **Interrumpir un hilo** implica enviar una señal para que el hilo detenga su tarea. No se puede reanudar directamente un hilo interrumpido.
- Para **pausar y reanudar** un hilo, se puede implementar una lógica personalizada utilizando un flag booleano, que controle cuándo el hilo debe esperar y cuándo continuar.

## Resumen

- La **interrupción** de un hilo se realiza mediante el método `interrupt()`, y el hilo puede manejar la interrupción verificando el estado con `isInterrupted()` o mediante la excepción `InterruptedException`.
- **Reanudar** un hilo interrumpido directamente no es posible, pero puedes controlar el flujo del hilo mediante un flag que gestione las pausas y reanudaciones utilizando los métodos `wait()` y `notify()`.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>