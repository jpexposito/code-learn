<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (“Gestión de imágenes en Docker”)

## 1. Introducción

Hasta ahora, hemos visto cómo descargar y trabajar con imágenes de terceros en Docker. En esta unidad explicaremos cómo gestionar las imágenes de contenedores Docker (listado, eliminación, historia, etc.) así como su creación tanto de forma manual como utilizando el comando “***docker build***” con los llamados “***Dockerfiles***”.

## 2. Listando imágenes locales y descarga
   ### 2.1  Listando imágenes locales

Podemos obtener información de qué imágenes tenemos almacenadas localmente usando

|docker images|
| :- |
Obteniendo un resultado similar al siguiente, donde vemos información acerca de las imágenes

![imagen](../images/imagenes1.png)

Podemos utilizar filtros sencillos usando la nomenclatura ***“docker images [REPOSITORIO[:TAG]]”***.

|docker images ubuntu:14.04|
| :- |
Nos mostrará la imágen del repositorio “ubuntu” en su versión “14.04”.

![imagen](../images/imagenes2.png)

Si queremos utilizar algún filtro avanzado, podemos usar la opción “-f”. Aquí un ejemplo, filtrando las imágenes que empiecen por “u” y acabe su etiqueta en “04”.

|docker images -f=reference="u\*:\*04"|
| :- |
![imagen](../images/imagenes3.png)

` `❕ **Atención:**  no confundir este comando con ***“docker image”*** (sin la s final).

Más información en <https://docs.docker.com/engine/reference/commandline/images/>

### 2.2 Listando imágenes para su descarga

Podemos obtener información de imágenes que podemos descargar en el registro (por defecto, Docker Hub) utilizando el comando “docker search”. Por ejemplo con el siguiente comando:

|docker search ubuntu|
| :- |

Nos aparecen aquellas imágenes disponibles en el registro (Docker Hub) con esa palabra.

## 3. Descargando y eliminando imágenes (y contenedores) locales

### 3.1  Descargando imágenes con “docker pull”

Podemos almacenar imágenes localmente desde el registro sin necesidad de crear un contenedor mediante el comando ***“docker pull”***, claramente inspirado en sistemas de control de versiones como ***“git”***. Para conocer sus nombres y versiones, podemos usar el comando “docker search” explicado anteriormente o visitar <https://hub.docker.com/>.

|docker pull alpine:3.10|
| :- |
Este comando nos descarga la imagen ***“alpine”*** con el tag ***“3.10”***, como vemos aquí:

![imagen](../images/imagenes4.png)

### 3.2 Observar el historial de una imagen descargada

Podéis observar el historial de una imagen descargada, es decir, en qué versiones se basa, usando el comando ***“docker history”.*** Por ejemplo con:

|docker history nginx|
| :- |
Obtenemos lo siguiente:

![imagen](../images/imagenes5.png)

### 3.3 Eliminando imágenes con “docker rmi”

Con el comando ***“docker rmi”*** podemos eliminar imágenes almacenadas localmente.

|docker rmi ubuntu:14.04|
| :- |
Elimina la imagen ubuntu con la etiqueta 14.04

![imagen](../images/imagenes6.png)

Una forma de eliminar **todas** las imagenes locales, que no estén siendo usadas por un contenedor, combinando ***“docker images -q”*** para obtener la lista y ***“docker rmi”*** es la siguiente:

|docker rmi $(docker images -q)|
| :- |
Aquí se observa el borrado, excepto de aquellas usadas por un contenedor:

![imagen](../images/imagenes7.png)

### 3.4 Eliminando contenedores con “docker rm”

Aprovechando que tratamos el borrado de imágenes, comentamos cómo borrar contenedores parados (si un contenedor está en marcha, debe ser parado antes del borrado).

Con la siguiente orden se puede borrar un contenedor por identificador o nombre

docker rm IDENTIFICADOR/NOMBRE
Asimismo, una forma de borrar todos los contenedores (que estén parados), de forma similar a como vimos en el anterior punto, es la siguiente:

**Paso 1 (opcional):** paramos todos los contenedores:

docker stop $(docker ps -a -q)

**Paso 2: borramos todos los contenedores:**

docker rm $(docker ps -a -q)

![imagen](../images/imagenes8.png)

### 3.5 Eliminando todas las imágenes y contenedores con “docker system prune -a”

Una forma de realizar las operaciones anteriores de golpe, es usando “docker system prune -a”, que elimina toda imagen y contenedor parado.

**Paso 1 (opcional):** paramos todos los contenedores:

docker stop $(docker ps -a -q)

**Paso 2: borramos todos los contenedores:**

docker system prune -a

Obteniendo algo similar a esto:

![imagen](../images/imagenes9.png)

## 4. Creando nuestras propias imágenes a partir de un contenedor existente

El sistema de imágenes de Docker funciona como un control de versiones por capas, de forma similar a la herramienta ***“git”*** para control de versiones. Podemos entender que un contenedor es como una “capa temporal” de una imagen, por lo cual, podemos hacer un “***commit***” y convertir esa “capa temporal” en una imagen. La sintaxis más habitual es la siguiente

docker commit -a "autor" -m "comentario" ID/NOMBRE-CONTENEDOR usuario/imagen:[version]

Por ejemplo, si tenemos un contenedor con nombre “***ubuntumod***” que simplemente es un contenedor basado en la imagen “***ubuntu***” en el que se ha instalado un programa y hacemos:

docker commit -a "Sergi" -m "Ubuntu modificado" IDCONTENEDOR sergi/ubuntumod:2021

y tras ello, comprobamos las imágenes con

docker images

observamos lo siguiente:

![imagen](../images/imagenes10.png)

Hemos obtenido lo siguiente: una nueva imágen, con nombre “***sergi/ubuntumod***” con tag “***2021***”, donde “sergi” actúa como nombre de usuario para usarlo en un repositorio remoto (recordamos nuevamente, que por defecto es “Docker Hub”).

Ahora ya podríamos crear nuevos contenedores con esa imagen, usando por ejemplo:

docker run -it sergi/ubuntumod:2021

Si quisiéramos añadir una nueva etiqueta a la imagen, como “latest”, podemos usar el comando “***docker tag***”, teniendo en cuenta que una misma imagen puede tener varias etiquetas:

docker tag sergi/ubuntumod:2021 sergi/ubuntumod:latest

Obtendremos algo similar a:

![imagen](../images/imagenes11.png)

Para eliminar una etiqueta, simplemente deberemos borrar la imagen  con docker rmi. La imagen se mantendrá mientras al menos tenga una etiqueta. Por ejemplo con:

|docker rmi sergi/ubuntumod:2021|
| :- |
quedaría así:

![imagen](../images/imagenes12.png)

Más información de los comandos en:

- Docker commit <https://docs.docker.com/engine/reference/commandline/commit/>
- Docker tag <https://docs.docker.com/engine/reference/commandline/tag/>

## 5. Exportando/importando imágenes locales a/desde ficheros

Una vez tengamos una imagen local en nuestro sistema, podemos hacer una copia de la misma, ya sea como copia de seguridad o como forma de transportarla a otros sistemas mediante el comando “***docker save”***. Por ejemplo se puede hacer de estas dos formas:

|docker save -o copiaSeguridad.tar sergi/ubuntumod|
| :- |
o de forma alternativa

|docker save > sergi/ubuntumod copiaSeguridad.tar|
| :- |
Si queremos importar el fichero para crear una imagen en nuestra máquina, podemos usar “docker import”. Por ejemplo se puede hacer de estas dos formas:

|docker load -i copiaSeguridad.tar|
| :- |
o de forma alternativa

|docker load < copiaSeguridad.tar|
| :- |

Más información sobre los comandos:

- Docker save: <https://docs.docker.com/engine/reference/commandline/save/>
- Docker load: <https://docs.docker.com/engine/reference/commandline/load/>

## 6. Subiendo nuestras propias imágenes a un repositorio (Docker Hub)

Podemos subir una imágen a un repositorio (por defecto Docker Hub). Para ello, debemos realizar los siguientes pasos:

### 6.1 Paso 1: creando repositorio para almacenar la imagen en Docker Hub

En primer lugar, debéis crearos una cuenta en [https://hub.docker.com](https://hub.docker.com/) y loguearos. Una vez logueados, debéis acceder a “***Repositories***” y ahí a “***Create repository***” de forma similar a como se ve en la imágen siguiente:

![imagen](../images/imagenes13.png)

Tras ello, tendréis un repositorio con vuestra cuenta, este repositorio puede ser público (cualquiera puede acceder) o privado (sólo puede acceder dueño o autorizados).

La pantalla de creación del repositorio tiene un aspecto similar a este:

![imagen](../images/imagenes14.png)

Una vez creado, si tu usuario es “***sergi***” y la imagen se llama “***prueba”***, podremos referenciarla en distintos contextos como “***sergi/prueba***”

### 6.2 Paso 2: almacenando imagen local en repositorio Docker Hub

En primer lugar, deberemos loguearnos mediante consola al repositorio mediante el comando

|docker login|
| :- |
Una vez logueado, debemos hacer un “commit” local de la imagen, siguiendo la estructura vista en puntos anteriores. Un ejemplo podría ser:

|docker commit -a "Sergi" -m "Ubuntu modificado" IDCONTENEDOR sergi/prueba|
| :- |
Hecho este commit local, debemos subirlo usando “docker push”

|docker push sergi/prueba|
| :- |
Una vez hecho eso, sí la imagen es pública (o privada con permisos), cualquiera podrá descargarla y crear contenedores usando ***“docker pull”*** o ***“docker run”***.

Más información de los comandos:

- Docker login <https://docs.docker.com/engine/reference/commandline/login/>
- Docker push <https://docs.docker.com/engine/reference/commandline/push/>

## 7. Generar automáticamente nuestras propias imágenes mediante Dockerfile

Docker nos permite generar de forma automática nuestras propias imágenes usando “***docker build***” y los llamados “***Dockerfile”***.

### 7.1 Editor Visual Studio Code y plugins asociados a Docker

Los ficheros “***Dockerfile***” pueden crearse con cualquier editor de texto, para este curso no hace falta instalar nada, pero si vais a profundizar más es recomendable el editor multiplataforma  “***Visual Studio Code***” <https://code.visualstudio.com/>

Para saber más sobre cómo usar este editor podéis usar <https://code.visualstudio.com/learn>

Al instalarlo, si detecta Docker instalado en el sistema, el propio editor nos sugerirá una serie de plugins. Merece la pena instalarlos. Si no, siempre podéis buscar en plugins  manualmente. Yo personalmente, os recomiendo estos dos que podéis ver en la imágen:

![imagen](../images/imagenes15.png)

### 7.2 Creando nuestro primer Dockerfile

Empezaremos creando un sencillo ***“Dockerfile”*** donde crearemos una imagen de Ubuntu con el editor de texto “nano” instalado. Para ello indicaremos:

- De qué imagen base partiremos.
- Que comandos lanzaremos sobre la imagen base, para crear la nueva imagen
- Qué comando se asociará por defecto al lanzar un contenedor con la nueva imagen

Creamos el fichero “***Dockerfile***” (Visual Studio Code le pondrá el icono de la ballena) y añadimos:

|**FROM** ubuntu:latest<br>**RUN** apt update && apt install -y nano<br>*# Aquí un comentario*<br>**CMD** /bin/bash|
| :- |
En el editor quedará de una forma similar a:

![imagen](../images/imagenes16.png)

Si ahora usamos el comando “docker build” de la siguiente forma:

|docker build -t ubuntunano ./|
| :- |
Obtendremos algo similar a

![imagen](../images/imagenes17.png)

Lo que hemos hecho es “ejecutar” lo que marca el “***Dockerfile***”. El resultado, se ha guardado en una nueva imagen local cuyo nombre hemos especificado con la opción ***“-t”***. El lugar donde se encontraba el “***Dockerfile***” se ha indicado mediante “***./***” (directorio actual).

` `❕ **Atención:**  el fichero debe llamarse exactamente “***Dockerfile***”, respetando mayúsculas y minúsculas.

Si queréis especificar un nombre de fichero distinto a buscar en el directorio, puede usarse la opción “-f”, como en este ejemplo:

|docker build -t ubuntunano -f Dockerfile2 ./|
| :- |
Podemos observar la historia de la imágen que hemos creado con “***docker history***”

![imagen](../images/imagenes18.png)

Donde observamos, que nuestra imagen ha crecido en “28.6MB” al hacer “***apt update && apt install -y nano***”. Aunque en el anterior “Dockerfile” hemos usado un solo RUN, podríamos haber utilizado varios RUN en lugar de uno, escrito de una forma secuencial como vemos en:

|**FROM** ubuntu:latest<br>**RUN** apt update <br>**RUN** apt install -y nano<br>**CMD** /bin/bash|
| :- |
Aquí simplemente, habría más capas intermedias, como se observa en “docker history” si generamos la imagen con la secuencia anterior

![imagen](../images/imagenes19.png)

Los comandos vistos (FROM, RUN y CMD) admiten distintos formatos. Para saber más podemos visitar su ayuda: <https://docs.docker.com/engine/reference/builder/>

### 7.3 Otros comandos importantes de Dockerfile

Al crear un “Dockerfile” hay multitud de comandos. A continuación explicamos los comandos más importantes a utilizar:

#### 7.3.1 Comando EXPOSE

En primer lugar, **repasamos la diferencia entre exponer y publicar puertos en Docker**:

- Si no se expone ni publica un puerto, este sólo es accesible desde el interior del contenedor.
- Exponer un puerto, indica que ese puerto es accesible tanto dentro del propio contenedor como por otros contenedores, pero no desde fuera (incluido el anfitrión).
- Publicar un puerto, indica que el puerto es accesible desde fuera del contenedor, por lo cual debe asociarse a un puerto del anfitrión.

La opción EXPOSE nos permite indicar los puertos por defecto expuestos que tendrá un contenedor basado en esta imagen. Es similar a la opción “***--expose***” de “***docker run***” (y de paso, recordamos que “docker run” con “***-p***” los publica). Por ejemplo, para exponer 80 443 y 8080 indicaremos:

|**EXPOSE** 80 443 8080|
| :- |

#### 7.3.2 Comando ADD/COPY

**ADD** y **COPY** son comandos para copiar ficheros de la máquina anfitrión al nuevo contenedor. Se recomienda usar **COPY**, excepto que queramos descomprimir un “zip”, que **ADD** permite su descompresión. Más información sobre la diferencia entre **ADD** y **COPY:**

<https://nickjanetakis.com/blog/docker-tip-2-the-difference-between-copy-and-add-in-a-dockerile>

Ejemplo de uso de **ADD**:

|**ADD** ./mifichero.zip /var/www/html|
| :- |
Descomprimirá el contenido de “***mifichero.zip***” en el directorio destino de la nueva imagen.

Ejemplo de uso de **COPY**:

|**COPY** ./mifichero.zip /var/www/html|
| :- |
o incluso accediendo desde la web.

|**COPY** http://miservidor.commifichero.zip /var/www/html|
| :- |
En este caso, copiará el fichero “***mifichero.zip***” en el directorio destino de la nueva imágen.

#### 7.3.3 Comando ENTRYPOINT

Por defecto, los contenedores Docker están configurados para que ejecuten los comandos que se lancen mediante **“*/bin/sh -c*”**. Dicho de otra forma, los comandos que lanzábamos, eran parámetros para  **“*/bin/sh -c*”**. Podemos cambiar qué comando se usa para esto con **ENTRYPOINT**. Por ejemplo:

|**ENTRYPOINT** ["cat"]<br>**CMD** ["/etc/passwd"]|
| :- |
Indicaremos que los comandos sean lanzados con “***cat***”. Al lanzar el comando “***/etc/passwd***”, realmente lo que haremos es que se lanzará “***cat /etc/passwd***”.

#### 7.3.4 Comando USER

Por defecto, todos los comandos lanzados en la creación de la imagen se ejecutan con el usuario root (usuario con UID=0). Para poder cambiar esto, podemos usar el comando **USER**, indicando el nombre de usuario o UID con el que queremos que se ejecute el comando. Por ejemplo:

|**USER** sergi<br>**CMD** id|
| :- |
Mostrará con el comando “id” el uid y otra información del usuario “sergi”.

#### 7.3.5 Comando WORKDIR

Cada vez que expresamos el comando **WORKDIR**, estamos cambiando el directorio de la imagen donde ejecutamos los comandos. ***Si este directorio no existe, se crea.*** Por ejemplo:

|**WORKDIR** /root<br>**CMD** mkdir tmp<br>**WORKDIR** /var/www/html<br>**CMD** mkdir tmp|
| :- |
Creará la carpeta “***tmp***” tanto en “***/root***” como en “***/var/www/html***”. Si los directorios “***/root***” o “***/var/www/html***” no hubieran existido, los hubiera creado.

#### 7.3.6 Comando ENV

El comando ENV nos permite definir variables de entorno por defecto en la imagen.

|**ENV v1=”valor1” v2=”valor2”**|
| :- |
Esto definirá las variables de entorno “v1” y “v2” con los valores “valor1” y “valor2”.

#### 7.3.7 Otros comandos útiles: ARG, VOLUME, LABEL, HEALTHCHECK

Aquí comentamos comandos útiles:

- **ARG**:  permite enviar parámetros al propior “***Dockerfile***” con la opción “***--build-arg***” del comando “***docker build***”.
- **VOLUME**: permite establecer volúmenes por defecto en la imágen. Hablaremos de los volúmenes más adelante en el curso.
- **LABEL**: permite establecer metadatos dentro de la imagen mediante etiquetas. Uno de los casos más típicos, sustituyendo al comando MAINTAINER, que esta “deprecated” es:
  - **LABEL** maintainer="sergi.profesor@gmail.com"
- **HEALTHCHECK**:  permite definir cómo se comprobará si ese contenedor está  funcionando correctamente o no. Útil para sistemas orquestadores como “***Docker swarn”***, aunque otros como “***Kubernetes***” incorporan su propio sistema

## 8. Trucos para hacer nuestras imágenes más ligeras

Al crear imágenes, es habitual aumentar el tamaño de las imágenes base. Algunos consejos para dentro de lo posible, aumentar el tamaño lo menos posible:

- Usar imágenes base ligeras, tipo “Alpine”.
- No instalar programas innecesarios, incluso evitando herramientas tipo Vim, Nano, etc.
- Minimizar capas en “Dockerfile”
  - Mejor usar “***RUN apt-get install -y <packageA> <packageB>”*** que usar ***“RUN apt-get install -y <packageA>***” y tras ello “***RUN apt-get install -y <packageB>***”
- Utiliza comandos de limpieza tras instalaciones con “***apt***”, tales como:
  - “***rm -rf /var/lib/apt/lists/\****” tras crear una imagen para borrar las listas generadas al realizar  “apt update”.
  - “***apt-get purge --auto-remove && apt-get clean***” para eliminar temporales de apt.
- Al usar “**apt install**” usar la opciṕn “***no-install-recommend***”, para que no instale paquetes recomendados asociados al paquete instalado.
- Analiza tus “Dockerfile” con <https://www.fromlatest.io/#/> y sigue sus consejos.

Más información en:

- <https://hackernoon.com/tips-to-reduce-docker-image-sizes-876095da3b34>
- <https://medium.com/sciforce/strategies-of-docker-images-optimization-2ca9cc5719b6>

## 9 Bibliografía

[1] Docker Docs <https://docs.docker.com/>

[2] Visual Studio Code Learn <https://code.visualstudio.com/learn>

[3] FROM:latest <https://www.fromlatest.io/#/>

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../LICENSE) para detalles.

</div>