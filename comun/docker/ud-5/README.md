<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (“Redes y Volúmenes en Docker”)

## Introducción

Hasta el momento hemos trabajado con contenedores aislados. En esta unidad se explorará la configuración de redes entre contenedores y la persistencia de datos mediante volúmenes.

---

## Gestionando Redes

### Redes predefinidas al instalar Docker

Docker establece 3 redes internas predefinidas:  

- **Bridge**: Red por defecto que asigna una IP propia.  
- **Host**: Usa la configuración de red de la máquina anfitriona.  
- **None**: Solo acceso a la interfaz de loopback.

### Crear redes internas en Docker

Comando básico:

```bash
docker network create redtest
```

Parámetros útiles:

- `--internal`: Red interna sin acceso exterior.
- `--gateway`: Define la puerta de enlace.
- `--ip-range`: Rango de IPs asignables.
- `--subnet`: Subred en formato CIDR.

### Inspeccionar y eliminar redes

#### Listar redes:

```bash
docker network ls
```

#### Inspeccionar redes:

```bash
docker network inspect [ID/NOMBRE-RED]
```

#### Eliminar redes

```bash
docker network rm [ID/NOMBRE-RED]
```

>***Nota***: Ningún contenedor en ejecución debe estar conectado a la red para eliminarla.

## Asignando Redes a Contenedores

### Asignar la red al crear un contenedor

Los contenedores pueden conectarse a redes específicas al ser creados. Docker permite referenciarlos mediante su nombre como hostname.

### Conectar y desconectar un contenedor de una red

Los contenedores pueden conectarse o desconectarse de redes en cualquier momento. Es posible asignar IPs fijas o alias en la red.

---

## Persistencia de Datos en Docker

### Herramientas de persistencia

1. **Binding mount**: Monta un directorio del anfitrión en el contenedor.
2. **Volúmenes Docker**: Gestionados por Docker, ofrecen mejor rendimiento en algunos sistemas.
3. **Tmpfs**: Volúmenes temporales en memoria.

### Utilizando "Binding mount"

Un directorio del anfitrión se monta en el contenedor, lo que permite compartir datos.

### Creando volúmenes Docker

#### Creación al crear contenedor

Los volúmenes pueden ser creados directamente al iniciar un contenedor.  

#### Gestionando volúmenes

Es posible crear volúmenes de manera independiente, listarlos y eliminarlos si no están en uso.

#### Poblando volúmenes

Si el directorio objetivo contiene datos, estos serán copiados al volumen.

### Creando volúmenes "tmpfs"

Estos volúmenes, sin persistencia, son útiles para acelerar operaciones de lectura/escritura. Limitados a sistemas Linux.

### Copia de seguridad de un volumen

Las copias de seguridad pueden realizarse empaquetando los datos del volumen en un archivo.

### Plugins para volúmenes

Docker soporta plugins que amplían la funcionalidad de los volúmenes, como permitir su uso en servidores remotos.

---

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../LICENSE) para detalles.

</div>