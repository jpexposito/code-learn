<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Atrapafichas)

`Descripción`:
**Atrapafichas** es un juego de estrategia y rapidez donde múltiples jugadores compiten por capturar un número limitado de fichas. Cada jugador debe apresurarse para atrapar una ficha antes de que se agoten, ya que solo unos pocos podrán salir victoriosos. El juego simula una situación de competencia en tiempo real, donde los jugadores deben coordinarse, ser rápidos y aprovechar las oportunidades antes que los demás.

`Dinámica`:

- Cada partida comienza con un número fijo de fichas, que son compartidas entre todos los jugadores.
    > El número de fichas y jugadores podría ser un elemento variable que solicitaramos al comienzo de la ejecución del juego.
- Los **jugadores**, representados por hilos en la programación, intentan "atrapar" las fichas de una **colección común**. Este concepto es **importante** ya que es el recurso que **comparten todos los juegadores**.
- El **juego termina cuando todas las fichas han sido capturadas**. *Los jugadores que no logren obtener una ficha a tiempo perderán la oportunidad de participar*.

`Características`:

- **Jugabilidad rápida**: Las partidas son cortas e intensas, lo que obliga a los jugadores a reaccionar velozmente.
- **Competencia limitada**: Con un número fijo de fichas y más jugadores de los que pueden capturarlas, los jugadores deben competir ferozmente por una oportunidad.
- **Sorpresa**: El orden en que los jugadores atrapan las fichas es impredecible debido a la naturaleza concurrente del juego.

## Implementación del juego

```java
import java.util.ArrayList;
import java.util.List;

public class FichaManager {
    private List<String> fichas;

    public FichaManager(int numeroDeFichas) {
        this.fichas = new ArrayList<>();
        // Inicializa las fichas disponibles
        for (int i = 1; i <= numeroDeFichas; i++) {
            fichas.add("Ficha " + i);
        }
    }
    /**
     * Metodo sincronizado para atrapar una ficha
    */
    public synchronized String atraparFicha() {
        if (!fichas.isEmpty()) {
            // Remover la primera ficha disponible
            return fichas.remove(0);
        }
        return null;  // Si no hay fichas disponibles
    }
    /*
    * Metodo opcional para liberar una ficha (si deseas agregarla de nuevo)
    */
    public synchronized void liberarFicha(String ficha) {
        if (ficha != null) {
            fichas.add(ficha);
        }
    }
}
```

> **Recurso compartido**

```java
List<String> fichas = new ArrayList<>();
```


Veamos ahora como implementar la clase jugador:

```java
public class Player implements Runnable {
    private String nombre;
    private FichaManager fichaManager;

    public Player(String nombre, FichaManager fichaManager) {
        this.nombre = nombre;
        this.fichaManager = fichaManager;
    }

    @Override
    public void run() {
        try {
            System.out.println(nombre + " está intentando atrapar una ficha...");

            // Intenta atrapar una ficha desde el gestor de fichas
            String ficha = fichaManager.atraparFicha();

            if (ficha != null) {
                System.out.println(nombre + " atrapó " + ficha + "!");
                
                // Simula que el jugador esta usando la ficha por algún tiempo
                Thread.sleep(1000);

                // Puedes optar por liberar la ficha de nuevo (opcional)
                // fichaManager.liberarFicha(ficha);

            } else {
                System.out.println(nombre + " no pudo atrapar una ficha, no quedan más disponibles.");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
```

```java
public class TokenGame {

    public static void main(String[] args) {
        // Crear el gestor de fichas con 5 fichas iniciales
        FichaManager fichaManager = new FichaManager(5);

        // Crear y empezar 10 hilos que intentarán atrapar una ficha
        for (int i = 1; i <= 10; i++) {
            // Crear un jugador y pasarlo al hilo
            Player jugador = new Player("Jugador " + i, fichaManager);
            new Thread(jugador).start();  // Iniciar el hilo del jugador
        }
    }
}
```

## Clase TokenGame

Esta es la clase principal donde se inicia el programa. Crea una instancia del **FichaManager**, que contiene las fichas, y luego lanza múltiples hilos (Player) que intentarán atraparlas.

El número de fichas y jugadores (Player´s) podría pedirse como parámetro de entrada.

## Clase FichaManager

Esta clase gestiona las fichas mediante una lista (ArrayList).
Tiene dos métodos:

- **atraparFicha()**: Un método sincronizado que remueve la primera ficha disponible de la lista.
- **liberarFicha()**: Un método opcional para agregar una ficha de nuevo a la lista (dependiendo de las reglas del juego).

>***Al estar sincronizados, los métodos garantizan que solo un hilo accede a la lista de fichas al mismo tiempo***.

## Clase Player

Representa un jugador que intentará atrapar una ficha llamando al **método atraparFicha() del FichaManager**. Si lo logra, ***simula que usa la ficha durante un tiempo (sleep)** . Si no hay fichas, se le informa que no pudo atrapar ninguna.

> ***Cada Player es ejecutado en un hilo independiente, lo que simula la competencia simultánea por las fichas***.

La clase Player extendiendo de **Thread** seria del siguiente modo:

```java
public class Player extends Thread {
    private String nombre;
    private FichaManager fichaManager;

    // Constructor que inicializa al jugador con su nombre y el gestor de fichas
    public Player(String nombre, FichaManager fichaManager) {
        this.nombre = nombre;
        this.fichaManager = fichaManager;
    }

    @Override
    public void run() {
        try {
            System.out.println(nombre + " está intentando atrapar una ficha...");

            // Intenta atrapar una ficha desde el gestor
            String ficha = fichaManager.atraparFicha();

            if (ficha != null) {
                // Si logra atrapar una ficha
                System.out.println(nombre + " atrapó " + ficha + "!");
                
                // Simula el uso de la ficha por un tiempo
                Thread.sleep(1000);

                // Puedes optar por liberar la ficha (opcional)
                // fichaManager.liberarFicha(ficha);

            } else {
                // Si no quedan fichas disponibles
                System.out.println(nombre + " no pudo atrapar una ficha, no quedan más disponibles.");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>