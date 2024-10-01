<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Programación de Servicios)

## Clases Java para la gestión de hilos

<div align="center">

<img src=images/threads.png width="400">
</div>

### La interfaz Runnable

Un __hilo (thread en adelante)__ puede ejecutar código Java dentro de tu aplicación Java.

<div align="center">

<img src=images/single-vs-multiThreaded.png width="400">
</div>

Cuando un __programa Java se lanza (se convierte en un proceso)__ empieza a ejecutarse por su __método main() que lo ejecuta el thread principal (main)__, un ___hilo especial creado___ por la Java VM para ejecutar la aplicación. ___Desde un proceso se pueden crear e iniciar tantos threads como necesites___. Estos hilos _ejecutarán partes del código de la aplicación en paralelo con el thread principal_.

Los _thread_ en Java son objetos como cualquier otro. Un thread es una instancia de la clase __java.lang.Thread__, o instancias de clases que _heredan de ésta_. Como ya hemos dicho, además de ser objetos, __los threads tienen la capacidad de ejecutar código__.

La forma más usada para indicar a un thread qué código queremos que ejecute es creando una clase que implemente la ___interfaz java.lang.Runnable__.

Esta interfaz es una _interfaz estándar que viene con la plataforma Java_. La interfaz __Runnable 
sólo tiene un único método, void run()__.

[Runnable en Java](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Runnable.html)

Sea lo que sea lo que el thread tenga que hacer, __debe estar incluido en la implementación del método run__. Tenemos tres posibilidades de implementar dicha interfaz:

- Crear una clase Java que _implemente la interfaz Runnable_.
- Crear una clase anónima que implemente la interfaz Runnable.
- Crear una expresión Lambda que implemente la __interfaz Runnable__.

    > Veremos ahora como utilizar cada una de ellas.

#### 1. Clase que implementa `Runnable`

Definimos una clase que implementa explícitamente la interfaz `Runnable`, donde se sobrescribe el método `run()`.

```java
class MiRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Ejecución en un hilo con clase que implementa Runnable.");
    }
}

public class Main {
    public static void main(String[] args) {
        MiRunnable miRunnable = new MiRunnable();
        Thread thread = new Thread(miRunnable);
        thread.start(); // Inicia el hilo y ejecuta el método run()
    }
}
```

>Salida:
>
>Ejecución en un hilo con clase que implementa Runnable.
>

#### 2. Clase anónima que implementa Runnable

En este caso, creamos una clase anónima que implementa la interfaz Runnable. Esto es útil cuando no queremos definir una clase concreta.

```java
public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Ejecución en un hilo con clase anónima que implementa Runnable.");
            }
        });
        thread.start(); // Inicia el hilo y ejecuta el método run()
    }
}
```

>Salida: Ejecución en un hilo con clase anónima que implementa Runnable.

#### Expresión Lambda que implementa Runnable

A partir de __Java 8__, es posible usar expresiones lambda para implementar interfaces funcionales, como Runnable. Esto permite un código más compacto.

```java
public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println("Ejecución en un hilo con expresión Lambda que implementa Runnable."));
        thread.start(); // Inicia el hilo y ejecuta el método run()
    }
}
```

>Salida: Ejecución en un hilo con expresión __Lambda que implementa Runnable__.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>