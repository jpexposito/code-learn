<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (” Introducción a los contenedores y a Docker”)

| <img src=../images/logo-docker.png width="40"> | **Índice**                        |
|--------------------------------|-----------------------------------|
| 1                              | [Introducción](1-introducción)                                                                   |
| 2                              | [Conceptos previos](#2-conceptos-previos)                                                         |
| 2.1                            | - [Virtualización](#21-virtualización)                                                             |
| 2.2                            | - [¿Qué es una máquina virtual?](#22-qué-es-una-máquina-virtual)                                   |
| 2.3                            | - [¿Qué es una máquina virtual de proceso?](#23-qué-es-una-máquina-virtual-de-proceso)             |
| 2.4                            | - [¿Qué es un emulador?](#24-qué-es-un-emulador)                                                   |
| 2.5                            | - [¿Qué es un hipervisor?](#25-qué-es-un-hipervisor)                                               |
| 3                              | [Contenedores](#3-contenedores)                                                                   |
| 3.1                            | - [¿Qué son los contenedores?](#31-qué-son-los-contenedores)                                       |
| 3.2                            | - [Analogía con contenedores de transporte marítimo](#32-analogía-con-contenedores-de-transporte-marítimo) |
| 3.3                            | - [Contenedores para desarrollo y despliegue de aplicaciones](#33-contenedores-para-desarrollo-y-despliegue-de-aplicaciones) |
| 3.4                            | - [Contenedores para despliegue de servicios](#34-contenedores-para-despliegue-de-servicios)       |
| 3.5                            | - [Ventajas e inconvenientes del uso de contenedores](#35-ventajas-e-inconvenientes-del-uso-de-contenedores) |
| 3.6                            | - [¿Cuándo es adecuado usar contenedores?](#36-cuándo-es-adecuado-usar-contenedores)               |
| 4                              | [Contenedores en sistemas Linux](#4-contenedores-en-sistemas-linux)                              |
| 4.1                            | - [¿Es nuevo el concepto de entornos privados en sistemas Unix?](#41-es-nuevo-el-concepto-de-entornos-privados-en-sistemas-unix) |
| 4.3                            | - [¿Cómo funcionan los contenedores modernos en Linux? ](#43-cómo-funcionan-los-contenedores-modernos-en-linux) |
| 5                              | [Contenedores Docker](#5-contenedores-docker)                                                    |
| 5.1                            | - [¿Qué es Docker?](#51-qué-es-docker)                                                          |
| 6                              | [Conclusión](#conclusion)                                                                       |


## 1. Introducción

En esta unidad realizaremos una introducción al concepto de contenedores, centrándonos en contenedores Linux y en la tecnología de Docker.

---

## 2. Conceptos Previos

### 2.1 Virtualización

La virtualización es un conjunto de tecnologías de hardware y software que permiten la abstracción de hardware, creando así la “ilusión” de administrar recursos virtuales como si fueran reales. Esto permite el despliegue de sistemas, desarrollo de software, análisis de malware, escalado horizontal, etc., ofreciendo ahorro en costes de energía y mantenimiento.

### 2.2 ¿Qué es una máquina virtual?

A veces necesitamos probar un nuevo sistema operativo o una configuración específica, pero no siempre hay acceso a una máquina física para hacerlo. Las máquinas virtuales permiten simular un sistema operativo y ejecutar programas como si estuvieran utilizando una máquina independiente.

### 2.3 ¿Qué es una máquina virtual de proceso?

Las máquinas virtuales de proceso permiten ejecutar un programa diseñado para un sistema operativo específico en una máquina actual mediante la emulación de una arquitectura. Ejemplos comunes incluyen:

- **Máquina virtual de Java (JVM):** ejecuta los bytecodes de Java en cualquier sistema compatible.
- **Plataforma .NET:** similar a la JVM pero de Microsoft.

### 2.4 ¿Qué es un emulador?

Un emulador permite simular un hardware específico o una API concreta, como en los emuladores de videoconsolas antiguas o Wine (un software que permite ejecutar aplicaciones de Windows en otros sistemas operativos).

### 2.5 ¿Qué es un hipervisor?

Un hipervisor simula el hardware de una máquina, permitiendo la instalación de distintos sistemas operativos. Ejemplos comunes incluyen VirtualBox y VMWare.

---

## 3. Contenedores

### 3.1 ¿Qué son los contenedores?

Los contenedores son una forma de virtualización que, en lugar de emular un sistema completo, utilizan el sistema operativo de la máquina anfitrión para crear entornos privados aislados en procesos, memoria, archivos y red. Esta tecnología se conoce como "OS Level Virtualization".

> **Nota:** Esto implica que no se pueden ejecutar contenedores en sistemas operativos diferentes al de la máquina anfitrión sin virtualización adicional.

*La siguiente imagen puede ayudarnos a entender el concepto de contenedor.*

<img src="https://upload.wikimedia.org/wikipedia/commons/0/0a/Docker-containerized-and-vm-transparent-bg.png">

*A la derecha observamos el funcionamiento de un hipervisor, encargado de virtualizar el hardware y* ***donde cada máquina virtual tiene su propio sistema operativo***. *A la izquierda, observamos un sistema de contenedores, donde* ***no existe esa virtualización del hardware y cada contenedor es un entorno privado***.

### 3.2 Analogía con contenedores de transporte marítimo

Para entender los contenedores, es útil compararlos con los contenedores de transporte marítimo, que deben cumplir con estándares para ser transportados, pero la carga en su interior puede variar. Los contenedores virtuales también deben cumplir estándares para funcionar en diferentes entornos.

### 3.3 Contenedores para desarrollo y despliegue de aplicaciones

Los contenedores facilitan el desarrollo, distribución y despliegue de aplicaciones al permitir la configuración rápida de entornos de compilación, pruebas y despliegue, minimizando problemas de compatibilidad.

> 💬 **Interesante:** Muchos sistemas de CI/CD (Continuous Integration/Continuous Delivery) utilizan contenedores.

<img src="https://miro.medium.com/v2/resize:fit:936/1*LPKv8WUeZTLdOqANWkdIuQ.png">


### 3.4 Contenedores para despliegue de servicios

Otro uso importante de los contenedores es en el despliegue de servidores (web, correo, bases de datos, DNS, etc.). Los contenedores permiten mantener configuraciones de servidores que se pueden replicar en distintos entornos, como local o en la nube.

Además de las ventajas anteriormente citadas de mantener versiones de software, los
contenedores nos permiten unificar configuraciones de servidores en local, incluso involucrando a
distintos servicios en distintos contenedores, de forma que al desplegarlos en la nube, funcionen
exactamente igual que en las pruebas realizadas localmente.

❕ ​ Atención: ​ “En mi máquina funcionaba … falla solo al subirlo al servidor … ” ​. El uso de
contenedores contribuye a que esta situación desaparezca 🙂

### 3.5 Ventajas e inconvenientes del uso de contenedores

**Ventajas:**
- Ocupan menos espacio que las máquinas virtuales tradicionales.
- Ejecución rápida y cercana a la velocidad nativa.
- Soporte por parte de grandes empresas de software.

**Inconvenientes:**
- Menor rendimiento en comparación con un sistema real.
- Gestión tediosa de datos persistentes.
- Mayormente se operan vía línea de comandos.

### 3.6 ¿Cuándo es adecuado usar contenedores?

Los contenedores son útiles en los siguientes contextos:
- **Para usuarios:** cuando se desea probar rápidamente un servicio.
- **Para desarrolladores:** cuando se quiere desarrollar y desplegar sin problemas de configuración.
- **Para pruebas:** crear entornos de prueba variados.
- **Para escalado horizontal:** ejecutar múltiples copias de una aplicación en clúster.

---

## 4. Contenedores en sistemas Linux

### 4.1 ¿Es nuevo el concepto de entornos privados en sistemas Unix?

No es algo nuevo. Existen conceptos previos, como:
- **Chroot (Unix):** [Chroot en Wikipedia](https://es.wikipedia.org/wiki/Chroot)
- **Jail (FreeBSD):** [FreeBSD jail en Wikipedia](https://es.wikipedia.org/wiki/FreeBSD_jail)

### 4.2 Sistemas privados modernos en Linux: contenedores

Con el sistema LXC (Linux Container), los contenedores modernos aparecen en 2008.  
Más información en [Linux Containers](https://linuxcontainers.org/).

### 4.3 ¿Cómo funcionan los contenedores modernos en Linux?

- **Linux namespaces:** Aísla procesos para que vean recursos concretos.
- **Cgroups:** Aísla y limita el uso de recursos como memoria, procesos, etc.  

> Para más información: [Linux Namespaces en Wikipedia](https://en.wikipedia.org/wiki/Linux_namespaces)

---

## 5. Contenedores Docker

### 5.1 ¿Qué es Docker?

Docker es un sistema de contenedores Linux que permite el desarrollo y despliegue de aplicaciones.  
Web oficial: [Docker](https://www.docker.com/)

---

## 6. Conclusión

En esta unidad se introdujeron conceptos de virtualización, contenedores y Docker. Estos conceptos se expandirán y pondrán en práctica en unidades futuras.

---

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../LICENSE) para detalles.

</div>