<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (“Lo básico en Docker”)

Este documento es una guía básica para aprender a trabajar con Docker y desplegar proyectos **PHP** de manera sencilla.

---

## 🧩 ¿Qué es Docker?

- **Docker** es una plataforma para crear y correr contenedores.  
- Un **contenedor** es como una "cajita" con todo lo necesario para ejecutar una aplicación (sistema base, dependencias, librerías, código).  
- Permite que un proyecto se ejecute **igual en todos los equipos**.

---

## 📦 Conceptos básicos

- **Imagen** → plantilla inmutable (ej: `php:8.3-apache`).  
- **Contenedor** → instancia de una imagen en ejecución.  
- **Dockerfile** → receta para crear una imagen personalizada.  
- **Docker Compose** → herramienta para definir varios servicios con un solo comando.  
- **Volúmenes** → carpetas persistentes para guardar datos o compartir código.  

---

## 📘 Comandos básicos de Docker

Mini cheat sheet con los comandos más usados para trabajar con Docker y Docker Compose.

---

### 🔍 Información general

| Comando              | Descripción                                  |
|-----------------------|----------------------------------------------|
| `docker version`      | Muestra la versión de cliente y servidor     |
| `docker info`         | Información del daemon y sistema             |
| `docker ps`           | Lista contenedores corriendo                 |
| `docker ps -a`        | Lista todos los contenedores (incluidos parados) |
| `docker images`       | Lista imágenes locales                       |
| `docker volume ls`    | Lista volúmenes creados                      |
| `docker network ls`   | Lista redes disponibles                      |

---

### 📦 Imágenes

| Comando                         | Descripción                          |
|---------------------------------|--------------------------------------|
| `docker pull nginx`              | Descarga una imagen desde Docker Hub |
| `docker build -t miapp:1.0 .`    | Construye una imagen desde Dockerfile |
| `docker images`                  | Lista imágenes locales               |
| `docker rmi miapp:1.0`           | Borra una imagen                     |

---

### ▶️ Contenedores

| Comando                                   | Descripción                             |
|-------------------------------------------|-----------------------------------------|
| `docker run -d --name web -p 8080:80 nginx` | Corre contenedor en segundo plano        |
| `docker run -it ubuntu bash`              | Corre interactivo con terminal           |
| `docker stop web`                         | Detiene un contenedor                    |
| `docker start web`                        | Inicia un contenedor detenido            |
| `docker restart web`                      | Reinicia un contenedor                   |
| `docker rm web`                           | Borra un contenedor                      |
| `docker logs -f web`                      | Muestra logs en tiempo real              |
| `docker exec -it web bash`                | Entra a la terminal de un contenedor     |

---

### 🛠️ Volúmenes y bind mounts

| Comando                                  | Descripción                                  |
|------------------------------------------|----------------------------------------------|
| `docker run -v mi_vol:/data nginx`       | Usa un volumen persistente                   |
| `docker run -v $(pwd):/app php:8.3-cli`  | Monta carpeta local dentro del contenedor    |
| `docker volume rm mi_vol`                | Borra un volumen                             |

---

### 🧹 Limpieza (⚠️ elimina recursos sin usar)

| Comando                   | Descripción                                   |
|----------------------------|-----------------------------------------------|
| `docker system prune`      | Borra contenedores, redes y volúmenes parados |
| `docker system prune -a`   | Borra también imágenes sin usar               |
| `docker volume prune`      | Borra volúmenes sin uso                       |

---

### 🧩 Docker Compose (v2)

| Comando                        | Descripción                                   |
|--------------------------------|-----------------------------------------------|
| `docker compose up`            | Levanta servicios                             |
| `docker compose up -d`         | Levanta en segundo plano                      |
| `docker compose down`          | Detiene y borra servicios/redes creadas       |
| `docker compose build`         | Reconstruye imágenes                          |
| `docker compose logs -f`       | Muestra logs en tiempo real                   |
| `docker compose exec app bash` | Entra en el servicio `app` con una shell bash |

### 🔗 Conectar VS Code al contenedor

1. **Instala las extensiones en VS Code:**
   - [Docker](https://marketplace.visualstudio.com/items?itemName=ms-azuretools.vscode-docker)
   - [Dev Containers](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers)

2. **Usa uno de estos métodos para conectarte:**
   - **Panel Docker en VS Code** → pestaña **Containers** → clic derecho en `app` → **Attach Visual Studio Code**.
   - O: `Ctrl/Cmd + Shift + P` → **Dev Containers: Attach to Running Container** → selecciona `app`.

---

### Comenzamos

#### 📄 index.php

```php
<?php
echo "¡Hola desde Docker con PHP!";
```

---

#### ▶️ Pasos para desplegar

**Construir y levantar el proyecto:**

```bash
docker compose up --build
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../LICENSE) para detalles.

</div>