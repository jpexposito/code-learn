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

# 3.1.4 Métodos de la clase `java.lang.Thread`

La clase `Thread` proporciona varios métodos clave para gestionar hilos:

- `start()`: Inicia el hilo.
- `run()`: El método donde se define el código que se ejecutará en el hilo.
- `sleep(long millis)`: Suspende el hilo durante el tiempo especificado (en milisegundos).
- `interrupt()`: Interrumpe el hilo si está en ejecución o en espera.
- `join()`: Espera a que un hilo termine su ejecución.
- `isAlive()`: Indica si un hilo está en ejecución.

# Cómo pausar un hilo

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

# Gestión de la prioridad de los hilos

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

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>