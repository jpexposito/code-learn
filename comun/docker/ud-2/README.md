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
- Ubuntu Kinetic 22.10
- Ubuntu Jammy 22.04 (LTS)
- Ubuntu Focal 20.04 (LTS)
- Ubuntu Bionic 18.04 (LTS)

#### 2.2.1 Paso 1: Eliminando versiones antiguas de Docker Engine

Para eliminar versiones antiguas, usa el siguiente comando:

```bash
sudo apt-get remove docker docker-engine docker.io containerd runc
```


## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../LICENSE) para detalles.

</div>