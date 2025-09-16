<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios)

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

### Chuleta de comandos básicos de Linux

| Comando                           | Descripción |
|-----------------------------------|-------------|
| `pwd`                             | Muestra el directorio actual. |
| `ls`                              | Lista archivos y directorios. |
| `ls -l`                           | Lista archivos con permisos, propietario, tamaño, fecha. |
| `ls -a`                           | Lista todos los archivos, incluidos los ocultos. |
| `cd carpeta`                      | Cambia al directorio indicado. |
| `cd ..`                           | Sube un nivel en el árbol de directorios. |
| `cd ~`                            | Va al directorio personal del usuario. |
| `touch archivo.txt`               | Crea un archivo vacío. |
| `mkdir carpeta`                   | Crea un directorio. |
| `rmdir carpeta`                   | Elimina un directorio vacío. |
| `cp origen destino`               | Copia un archivo o directorio. |
| `mv origen destino`               | Mueve o renombra un archivo o directorio. |
| `rm archivo.txt`                  | Elimina un archivo. |
| `rm -r carpeta/`                  | Elimina un directorio y su contenido. |
| `cat archivo.txt`                 | Muestra el contenido completo de un archivo. |
| `less archivo.txt`                | Muestra el contenido de un archivo con paginación. |
| `head archivo.txt`                | Muestra las primeras líneas de un archivo. |
| `tail archivo.txt`                | Muestra las últimas líneas de un archivo. |
| `tail -f archivo.log`             | Muestra en tiempo real lo que se escribe en un archivo. |
| `echo "texto"`                    | Imprime texto en la terminal. |
| `echo $$`                         | Muestra el PID del proceso actual (shell). |
| `echo $PPID`                      | Muestra el PID del proceso padre. |
| `whoami`                          | Muestra el usuario actual. |
| `id`                              | Muestra UID, GID y grupos del usuario actual. |
| `uname -a`                        | Muestra información del sistema y kernel. |
| `date`                            | Muestra la fecha y hora actual. |
| `cal`                             | Muestra el calendario. |
| `history`                         | Muestra el historial de comandos ejecutados. |
| `clear`                           | Limpia la terminal. |
| `man comando`                     | Muestra el manual de un comando. |
| `ps -e`                           | Lista todos los procesos en ejecución. |
| `ps -f`                           | Muestra procesos con información extendida. |
| `ps -axf`                         | Muestra el árbol de procesos. |
| `pstree`                          | Muestra los procesos en formato jerárquico. |
| `top`                             | Muestra procesos en tiempo real (CPU/memoria). |
| `htop`                            | Versión mejorada de `top` (si está instalada). |
| `jobs`                            | Lista procesos en segundo plano en la sesión actual. |
| `fg`                              | Trae un proceso suspendido al primer plano. |
| `bg`                              | Reanuda un proceso en segundo plano. |
| `kill <PID>`                      | Termina un proceso por su PID. |
| `kill -9 <PID>`                   | Mata un proceso de forma forzada. |
| `sleep 100 &`                     | Lanza un proceso en segundo plano. |
| `nohup comando &`                 | Ejecuta un comando que no se detiene al cerrar la terminal. |
| `ulimit -a`                       | Muestra los límites de recursos de procesos. |
| `find /ruta -name archivo.txt`    | Busca un archivo por nombre. |
| `grep "texto" archivo.txt`        | Busca texto dentro de un archivo. |
| `grep -r "texto" carpeta/`        | Busca texto dentro de todos los archivos de una carpeta. |
| `wc -l archivo.txt`               | Cuenta las líneas de un archivo. |
| `df -h`                           | Muestra el uso de disco en formato legible. |
| `du -sh carpeta/`                 | Muestra el tamaño de un directorio. |
| `free -h`                         | Muestra el uso de la memoria RAM. |
| `uptime`                          | Muestra el tiempo que lleva encendido el sistema. |
| `ping google.com`                 | Comprueba conectividad con un host. |
| `curl http://ejemplo.com`         | Descarga el contenido de una URL. |
| `wget http://ejemplo.com/archivo` | Descarga un archivo de Internet. |
| `ifconfig` / `ip a`               | Muestra configuración de red. |
| `scp archivo usuario@host:/ruta`  | Copia archivos entre equipos por SSH. |
| `ssh usuario@host`                | Conecta a un servidor remoto por SSH. |
| `tar -cvf archivo.tar carpeta/`   | Crea un archivo tar con una carpeta. |
| `tar -xvf archivo.tar`            | Extrae un archivo tar. |
| `gzip archivo` / `gunzip archivo.gz` | Comprime y descomprime con gzip. |
| `zip archivo.zip fichero` / `unzip archivo.zip` | Comprime y descomprime con zip. |
| `apt update`                      | Actualiza la lista de paquetes (Debian/Ubuntu). |
| `apt upgrade`                     | Actualiza los paquetes instalados. |
| `apt install paquete`             | Instala un paquete. |
| `apt remove paquete`              | Desinstala un paquete. |


## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>