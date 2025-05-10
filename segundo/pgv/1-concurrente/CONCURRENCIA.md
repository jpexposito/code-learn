<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios)

## Concurrencia

<div align="center">

<img src=images/concurrencia.png width="400">
</div>

Según el diccionario de la __RAEA__ brir en una ventana nueva una de las acepciones de concurrencia es

>💡 Coincidencia, concurso simultáneo de varias circunstancias.

___Si cambiamos circunstancias por procesos, ya tendríamos una definición cercana a lo que significa concurrencia en el mundo digital.___

Si nos fijamos, no es la primera vez que surge la palabra proceso en este texto, y ___es que los procesos son una pieza fundamental del puzle, por no decir la parte más importante___.

### Concurrencia vs Paralelismo

Ahora que ya sabemos qué es un proceso, vamos a ver la relación que éstos tienen con el hardware en el que se ejecutan.

#### Monoproceso

Por mucho que tengamos varios procesos procesos ejecutándose a la vez, si sólo tenemos un microprocesador para atenderlos a todos, estas tareas nunca van a poder ejecutarse a la vez.

Una posibilidad sería la ejecución secuencias de las tareas en el sistema. Se empieza a ejecutar una tarea y, hasta que esta no finaliza, el sistema no empieza a ejecutar la siguiente. Esto se correspondería con sistemas que sólo son capaces de hacer una tarea a la vez, algo raro hoy en día.

<div align="center">

<img src=images/proceso-1.svg width="400">
</div>

### Multiprogramación

Para que los procesos no tengan que esperar a que todos los demás se ejecuten, los sistemas aprovechan y exprimen los recursos al máximo, permitiendo la ilusión de que varios procesos se ejecutan de forma simultánea. Esto es lo que se conoce como multitarea.

En estos sistemas, se aprovecha el diseño de los sistemas operativos modernos, y de las operaciones que realizan los procesos que no requieren el uso del procesador (esperar a una operación de E/S, una interacción con el usuario, la recepción de información desde la red, etc.) para poder ejecutar otros procesos. La ejecución se multiplexa en el tiempo.

<div align="center">

<img src=images/proceso-2.svg width="400">
</div>

Como se puede observar en las dos imágenes anteriores (aunque se trata sólo de un modelo), el tiempo de uso total del procesador es igual en ambos casos, es decir, que el sistema tardará el mismo tiempo en completar todas las tareas. Sin embargo, la sensación es que todas las tareas se están realizando a la vez.

### Paralelismo

Con el avance de la tecnología ahora la gran mayoría de dispositivos, desde los equipos de escritorio, portátiles, dispositivos móviles, ... hasta los dispositivos IoT, tienen capacidades de multiproceso, es decir, tienen más de un procesador para poder realizar varias tareas a la vez de forma real, no simulada. A este tipo de ejecución es a lo que llamamos paralelismo.

<div align="center">

<img src=images/proceso-3.svg width="400">
</div>

En este caso, a mayor número de unidades de proceso, menor tiempo tardarán las tareas en completarse y mayor será la sensación de rapidez que notará el usuario. Este es uno de los retos de los sistemas operativos, planificar adecuadamente las tareas para minimizar los tiempos de ejecución, de espera y el uso de los recursos del sistema, el procesador principalmente.

>💡 núcleos vs hilos
>
> Si habéis comprado un procesador hace poco, o estáis al día en cuanto al hardware, sabréis que una de las características de los procesadores es su número de núcleos (4, 8, 16).
>
>Pero además, al número de núcleos lo acompaña otra característica que es el número de hilos o threads, que suele ser el doble que el de núcleos.
>
>¿Qué implicación tienen los threads de un procesador con respecto a la concurrencia? ¿Si un equipo tiene 8 núcleos / 16 hilos significa eso que puede ejecutar 16 procesos a la vez?

#### Sistemas distribuidos

>📌 "Un sistema distribuido es una colección de computadores independientes que aparecen ante los usuarios como un único sistema coherente"
>
>___"Andrew S. Tanembaum"___

Posiblemente el ejemplo más famoso y conocido de sistema distribuido sea Internet.Internet aparece ante los usuarios como un enorme repositorio de documentos, es decir, como un único sistema capaz de proveer casi cualquier tipo de información o servicio que se necesite. No obstante, sabemos que está compuesta por millones de equipos ubicados en localizaciones diferentes e interconectados entre sí.

Nace de la necesidad de compartir recursos. Actualmente el máximo exponente de este tipo de sistemas es el Cloud Computing o servicios en la nube. Un sistema es distribuido cuando los componentes software están distribuidos en la red, se comunican y coordinan mediante el paso de mensajes.

Las características de este tipo de sistemas son::

- Concurrencia: ejecución de programas concurrentes.
- Inexistencia de un reloj global. - Implica sincronizarse con el paso de mensajes.
- Fallos independientes: cada componente del sistema puede fallar sin que perjudique la ejecución de los demás.

#### Ventajas e inconvenientes

Ventajas del procesamiento paralelo:

- Ejecución simultánea de tareas.
- Disminuye el tiempo total de ejecución
- Resuelve problemas complejos y de grandes dimensiones.
- Utilización de recursos no locales distribuidos en la red
- Disminución de costos, aprevechando los recursos distribuidos, no es necesario gastar en un único supercomputardor, se puede alcanzar el mismo poder de computación con equipos más modestos distribuidos.
Inconvenientes del procesamiento paralelo:

Los compiladores y entornos de programación para sistemas paralelos son más complicados de desarrollar.
Los programas paralelos son más difíciles de escribir
Hay mayor consumo de energía
Mayor complejidad en el acceso a datos
Complejidad a la hora de la comunicación y sincronización de las diferentes subtareas. cuidado
Ventajas de la programación distribuida

Se comparten recursos y datos
Crecimiento bajo demanda
Mayor flexibildad para distribuir la carga
Alta disponibilidad
Soporte de aplicaciones distribuidas
Filosofía abierta y hetereogénea
Escalado de sistemas

Con escalado nos referimos a la posibilidad de incrementar las capacidades de un sistema.

Investiga las diferencias, ventajas e inconvenientes del escalado horizontal y el escalado vertical.

Inconvenientes de la programación distribuida

- Aumenta la complejidad
- Se necesita software nuevo especializado
- Problemas derivados de las comunicaciones (perdidas, saturaciones, etc.)
- Problemas de seguridad, ataques DDoS
- Ejemplos de utilización de la programación paralela y distribuida

  - Estudios meteorológicos
  - Estudios del genoma humano
  - Modelado de la biosfera
  - Predicciones sísmicas
  - Simulación de moléculas
  - Ejemplo de programación paralela y distribuida

>❗ Búsqueda de inteligencia extraterrestre - Proyecto SETI

### Condiciones de Bernstein

Una vez que sabemos qué es un programa concurrente y las distintas arquitecturas hardware que pueden soportarlo, vamos a ver qué partes de un programa se pueden ejecutar de forma concurrente y cuáles no.

Si observamos el siguiente código, queda claro que la primera instrucción se debe ejecutar antes que la segunda para que el resultado sea siempre el mismo (para los mismos datos de entrada).

```java
x = x + 1;
y = x + 1;
```

Sin embargo, en un código como el siguiente el órden en el que se ejecuten las instrucciones no influye en el resultado final (valor de las variables). En este caso se pueden ejecutar las tres sentencias a la vez incrementando la velocidad de procesamiento.

```java
x = 1;
y = 2;
z = 3;
```

>💡 A.J. Bernstein definió unas condiciones para determinar si dos conjuntos de instrucciones Si y Sj se pueden ejecutar concurrentemente.

Para poder determinar si dos conjuntos de instrucciones se pueden ejecutar concurrentemente, se definen en primer lugar los siguientes conjuntos

- L(Sk) = {a1, a2, a3, ...} como el conjunto de lectura formado por todas las variables cuyos valores se leen durante la ejecución de las instrucciones del conjunto k.
- E(Sk) = {b1, b2, b3, ...} como el conjunto de escritura formado por todas las variables cuyos valores se actualizan durante la ejecución de las instrucciones del conjunto k.
Para que dos conjuntos de instrucciones Si y Sj se puedan ejecutar concurrentemente, se deben cumplir estas tres condiciones de forma simultánea.

- L(Si) ∩ E(Sj)
- E(Si) ∩ L(Sj)
- E(Si) ∩ E(Sj)
Cuales de estas instrucciones se pueden ejecutar de forma concurrente

```java
a = x + y;
b = z - 1;
c = a - b;
w = c + 1;
```

Primero deberíamos obtener los conjuntos L y E para cada sentencia

- L(S1) = {x, y}

- E(S1) = {a}

- L(S2) = {z}

- E(S2) = {b}

- L(S3) = {a, b}

- E(S3) = {c}

- L(S4) = {c}

- E(S4) = {w}

Y ahora aplicarlas entre cada par de sentencias

- L(S1) ∩ E(S2) = ∅
- E(S1) ∩ L(S2) = ∅ 
- E(S1) ∩ E(S2) = ∅ 

> Sí se pueden ejecutar concurrentemente

- L(S1) ∩ E(S3) = ∅
- E(S1) ∩ L(S3) = {a} ≠ ∅ 
- E(S1) ∩ E(S3) = ∅ // 
- > NO se pueden ejecutar concurrentemente

- L(S1) ∩ E(S4) = ∅
- E(S1) ∩ L(S4) = ∅
- E(S1) ∩ E(S4) = ∅

> Sí se pueden ejecutar concurrentemente

- L(S2) ∩ E(S3) = ∅
-  E(S2) ∩ L(S3) = {b] ≠ E(S2) ∩ E(S3) = ∅

> NO se pueden ejecutar concurrentemente

- L(S2) ∩ E(S4) = ∅
- E(S2) ∩ L(S4) = ∅
- E(S2) ∩ E(S4) = ∅

>Sí se pueden ejecutar concurrentemente

- L(S3) ∩ E(S4) = ∅
- E(S3) ∩ L(S4) = {c} ≠ ∅ E(S3) ∩ E(S4) = ∅
> NO se pueden ejecutar concurrentemente

[Procesos-so](PROCESOS-SO.md)

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>