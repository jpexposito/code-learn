<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (“Instalación de Docker”)

## 1. Introducción

En esta unidad explicaremos diversos itinerarios para la instalación de Docker, realizando nuestras recomendaciones. Aunque el curso debería poder realizarse en sistemas Windows y MacOS, recomendamos siempre que sea posible realizar el curso (y en general, usar Docker) en sistemas Linux, ya que su implementación es más robusta y puede evitarnos muchos problemas.

---

## 2. Instalación de Docker en sistemas Linux (Ubuntu)

### 2.1 Instalación desde el repositorio oficial de Ubuntu (No recomendado)

Es posible instalar Docker Engine desde el repositorio oficial de Ubuntu, pero no está recomendado, ya que instala versiones antiguas. En este curso, instalaremos Docker CE desde la fuente oficial.

### 2.2 Instalación desde el repositorio de Docker-CE (Recomendado)

Las versiones de Ubuntu soportadas (todas de 64 bits) son:

- Ubuntu Kinetic
- Ubuntu Jammy
- Ubuntu Focal
- Ubuntu Bionic

#### 2.2.1 Eliminando versiones antiguas de Docker Engine

Para eliminar versiones antiguas, usa el siguiente comando:

```bash
sudo apt-get remove docker docker-engine docker.io containerd runc
```

#### 2.2.2 Configurar repositorios

1. Actualizar el sistema e instalar paquetes requeridos:

```code
sudo apt-get update
sudo apt-get install -y ca-certificates curl gnupg lsb-release
```

2. Añadir la clave GPG oficial de Docker:

```code
sudo mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
```

3. Configurar el repositorio de Docker:

```code
echo \
"deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
$(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```

### 2.2.3 Instalar Docker

1. Actualizar los índices de paquetes:
   
```code
sudo apt-get update
```

2. Instalar Docker Engine, CLI y containerd:

```code
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

### 2.2.4 Verificar la instalación

Comprueba que Docker se instaló correctamente ejecutando:

```code
sudo docker --version
```

### 2.2.5 Ejecutar Docker sin sudo

Si quieres ejecutar Docker como un usuario no root:

1. Añade tu usuario al grupo docker:

```code
sudo usermod -aG docker $USER
```

2. Cierra la sesión y vuelve a iniciarla o ejecuta:

```code
newgrp docker
```

3. Verifica el funcionamiento

Verifica el correcto funcionamiento ejecutando:

```code
docker run hello-world
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../LICENSE) para detalles.

</div>