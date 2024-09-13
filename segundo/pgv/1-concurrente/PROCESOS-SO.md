<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Programación de Servicios)

## Procesos en el Sistema Operativo

### El kernel del SO

El kernel o núcleo de un SO se encarga de la funcionalidad básica del sistema, el responsable de la gestión de los recursos del ordenador, se accede al núcleo a través de las llamadas al sistema, es la parte más pequeña del sistema en comparación con la interfaz. El resto del sistema operativo se le denomina como programas del sistema.

Todos los programas que se ejecutan en el ordenador se organizan como un conjunto de procesos. Es el sistema operativo el que decide parar la ejecución , por ejemplo, porque lleva mucho tiempo en la CPu, y decide cuál será el siguiente proceso que pasará a ejecutarse.

Cuando se suspende la ejecución de un proceso, luego deberá reiniciarse en el mismo estado en el que se encontraba antes de ser suspendido. Esto implica que debemos almacenar en algún sitio la información referente a ese proceso para poder luego restaurarla tal como estaba antes. Esta información se almacena en el PCB (Bloque de control de procesos).

Estos cambios de contexto, que es como se conoce al reemplazo de un proceso por otro, son bastante costosos (en tiempo y recursos) por toda la información que hay que guardar. Ya veremos más adelante que existe otra unidad de ejecución, los hilos, que solucionan en parte este problema.


<div align="center">

<img src=images/proceso-so-1.png width="400">
</div>

### Control de procesos en GNU/Linux

Los sistemas Linux identifican a los procesos por su PID (Process ID) así como por su PPID (Parent PID). De esta forma, los procesos pueden clasificarse en:

Procesos padre: Son procesos que crean otros procesos durante su ejecución
Procesos hijos: son procesos creados por otros procesos
Cuando se arranca el sistema, el kernel lanza el proceso init que es la madre de todos los demás procesos. Al ser el primero que se lanza es el único que no tiene padre. El proceso init se encarga de gestionar todos los demás procesos que se van ejecutando en el SO.

> proceso init
>
> El proceso init tiene el pid 1 y, como ya hemos dicho no tiene padre.
>
>Este proceso se utiliza como padre "adoptivo" para todos aquellos procesos que se quedan huérfanos.

#### Comandos para saber el pid de los procesos

El comando __pidof cmdname__ nos dice el nombre de todos los procesos asociados a ese comando. Es importante recordar que cada vez que ejecutamos un comando, se crea un nuevo proceso.

Las variables __$$__ y __$PPID__ nos indican el pid del proceso actual y su ppid respectivamente.

```code
# pidof systemd
1
# pidof top
2060
# pidof httpd
2103 2102 2101 2100 2099 1076
# Process pid
echo $$
2109
# Process parent pid
echo $PPID
2106
```

#### Comandos para ver los procesos activos

El principal comando para conocer los procesos que se están ejecutando en un equipo es el comando __ps__. Con este comando podemos ver parte de la información asociada a un proceso.

El comando ps tiene múltiples opciones que nos permiten ver más o menos información de los procesos, así como los procesos de nuestro usuario o del resto de usuarios, estadísticas sobre el uso de recursos de cada proceso, etc.

```code
jpexposito@MacBook-Pro-de-Joatham ~ % ps -xa            
  PID TTY           TIME CMD
    1 ??        16:19.25 /sbin/launchd
  103 ??        10:27.88 /usr/libexec/logd
  104 ??         0:00.20 /usr/libexec/smd
  105 ??         1:24.98 /usr/libexec/UserEventAgent (System)
  108 ??         0:02.72 /System/Library/PrivateFrameworks/Uninstall.framework/Resources/uninstalld
  109 ??         1:57.11 /System/Library/Frameworks/CoreServices.framework/Versions/A/Frameworks/FSEvents.framework/Versions/A/Support/fseventsd
  110 ??         0:12.02 /System/Library/PrivateFrameworks/MediaRemote.framework/Support/mediaremoted
  113 ??         0:35.69 /usr/sbin/systemstats --daemon
  116 ??         1:17.18 /usr/libexec/configd
  117 ??         0:00.08 endpointsecurityd
...
```

> Useful ‘ps’ examples for Linux process monitoring
>
> https://www.tecmint.com/ps-command-examples-for-linux-process-monitoring/

</div>

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>