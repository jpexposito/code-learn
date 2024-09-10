<div align="justify">

# <img src=.../../../../images/computer.png width="40"> Code & Learn (JDK)

## Open-Jdk

<div align="center">

<img src=images/open-jdk-logo.png width="400">
</div>

### Introducción

Java sin dudas es un lenguaje de programación que es utilizado para diversos propósitos y es un complemento casi esencial para la ejecución y funcionamiento de diversas herramientas, la instalación de java es prácticamente una tarea esencial después de haber realizado la instalación de este.

Es por ello que en esta ocasión compartiré con ustedes una sencilla guía de como instalar __OpenJDK(Java)__ en nuestro sistema con el JDK el cual es un entorno de desarrollo y el entorno de ejecución JRE.

#### ¿Cómo instalar Java en linux desde repositorios?

Lo primero debemos de actualizar el sistema con:

```
  sudo apt-get update
```

e instalamos Java con este comando:

```
  sudo apt-get install default-jdk
```

comprobamos que tenemos instalado Java en nuestro sistema solo debemos de ejecutar:
```
  java --version
```

#### ¿Cómo instalar una versión específica de Java?

Para instalar Ubuntu Java Open JDK ("la que utilizaremos en 1º").
 - OpenJDK:
   - 11
   ```
   sudo apt install openjdk-11-jdk
   ```
    - 9
   ```
   sudo apt install openjdk-9-jdk
   ```
    - 8
   ```
   sudo apt install openjdk-8-jdk
   ```
La versión que se debe de trabajar es la versión 8. Para ello verificaremos la versión de java que se esta ejecutando con la sentencia:

```console
  java --version
```
En caso que no se ejecuta la versión 8 se debe configurar las variables de entorno.

#### Configuración de las variables de entorno

 El siguiente paso consiste en establecer  las variables de entorno. Es necesario porque cuando se usa Java, Linux necesita saber dónde está ubicado el programa para ejecutarlo y qué versión de Java usar de forma predeterminada. Para modificar esto, usaremos el editor de texto nano. Primero, abra el archivo en Nano.

#### Listar la versiones de OpenJDK instaladas

 Ejecuta el siguiente comando para verificar que se han descargado las diferentes versiones de OpenJDK.

```console
 ls /usr/lib/jvm
```

#### Actualización de las variables de entorno

 Edita y modifica el fichero profile, con los comandos:

```console
sudo update-alternatives --config java
```
 y selecciona la version _8_.

 Otra opción es : añadir el siguiente código:

```console
# Java version
JAVA_HOME=/usr/lib/jvm/_____openJdk_____
PATH=$PATH:$HOME/bin:$JAVA_HOME/bin
export JAVA_HOME
export JRE_HOME
export PATH
```

 en

```console
/etc/profile.d/java.sh
```
Haga que el script sea ejecutable con chmod:

```console
sudo chmod +x /etc/profile.d/java.sh
```

Finalmente, cargue las variables de entorno usando el comando de source

```console
source /etc/profile.d/java.sh
```

#### Instalación de OpenJDK en Windows y Linux

#### Instalación en Windows

#### Paso 1: Descarga OpenJDK
1. Ve a la página oficial de [AdoptOpenJDK](https://adoptopenjdk.net/) o [OpenJDK](https://jdk.java.net/).
2. Selecciona la versión de OpenJDK que deseas instalar (por ejemplo, OpenJDK 11 o 17).
3. Descarga el instalador para **Windows** en formato `.msi` o `.zip`.

#### Paso 2: Instala OpenJDK

- Si descargaste el archivo `.msi`:
  1. Ejecuta el instalador.
  2. Sigue las instrucciones y selecciona la ruta donde instalar OpenJDK.
  
- Si descargaste el archivo `.zip`:
  1. Descomprime el archivo en una carpeta (por ejemplo, `C:\OpenJDK`).

#### Paso 3: Configura las variables de entorno

1. Abre **Panel de Control** > **Sistema** > **Configuración avanzada del sistema**.

2. Haz clic en **Variables de entorno**.

3. En **Variables del sistema**, busca `Path` y haz clic en **Editar**.
   - Añade la ruta al directorio `bin` de OpenJDK (por ejemplo, `C:\OpenJDK\bin`).
4. Crea una nueva variable de entorno llamada `JAVA_HOME`:
   - Valor: la ruta donde instalaste OpenJDK (por ejemplo, `C:\OpenJDK`).

### Paso 4: Verifica la instalación
1. Abre **CMD** o **PowerShell**.
2. Ejecuta:
   ```bash
   java -version
   javac -version


</div>

</div>