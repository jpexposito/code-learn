<div align="justify">

# ![logo](../../../images/coding-book.png) Code & Learn (JDK)

## OpenJDK 17

![OpenJDK logo](images/open-jdk-logo.png)

---

### Introducción

Java es un lenguaje de programación usado para múltiples propósitos.  
Para compilar y ejecutar aplicaciones modernas usaremos **OpenJDK 17 (LTS)**.  
A continuación, verás una guía sencilla para instalarlo en Linux y Windows, verificar la instalación y (si hace falta) configurar variables de entorno.

---

## Instalación en Linux (Ubuntu/Debian)

### 1) Actualiza índices y instala JDK 17

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk
```

### 2) Verifica la instalación

```bash
java -version
javac -version
```

Deberías ver algo como:
```
openjdk version "17.0.x" ...
```

### 3) (Opcional) Listar versiones instaladas

```bash
ls /usr/lib/jvm
```

### 4) (Opcional) Seleccionar JDK por defecto si tienes varias versiones

```bash
sudo update-alternatives --config java
sudo update-alternatives --config javac
```

Elige la opción que apunte a **java-17**.

### 5) (Opcional) Configurar variables de entorno

En la mayoría de casos no es necesario porque `update-alternatives` ya deja todo listo.  
Si tu herramienta requiere `JAVA_HOME`, añade este script:

Edita (como root) `/etc/profile.d/java.sh` y pega:

```bash
# Java 17 - JAVA_HOME y PATH
# Ubuntu/Debian (x64):
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH="$PATH:$JAVA_HOME/bin"
```

Guarda, haz ejecutable y recarga:

```bash
sudo chmod +x /etc/profile.d/java.sh
source /etc/profile.d/java.sh
```

> En Fedora/RHEL suele ser:
> ```bash
> export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
> export PATH="$PATH:$JAVA_HOME/bin"
> ```

---

## Instalación en Windows

### Opción A) Instalador gráfico (recomendado)

1. Descarga un JDK 17 confiable, por ejemplo [Eclipse Temurin (Adoptium)](https://adoptium.net/) o [OpenJDK](https://jdk.java.net/17/).
2. Ejecuta el instalador (`.msi`) y completa los pasos (deja la ruta por defecto).

### Opción B) Gestor de paquetes

- **winget**:
  ```powershell
  winget install EclipseAdoptium.Temurin.17.JDK
  ```
- **chocolatey**:
  ```powershell
  choco install temurin17-jdk
  ```

### Verificar la instalación

Abre **CMD** o **PowerShell** y ejecuta:

```powershell
java -version
javac -version
```

Deberías ver `version "17.0.x"`.

### (Opcional) Configurar variables de entorno

Si alguna herramienta te pide `JAVA_HOME`:

1. **Panel de control** → **Sistema** → **Configuración avanzada del sistema** → **Variables de entorno**.
2. En **Variables del sistema**:
   - Crea `JAVA_HOME` con la ruta del JDK, por ejemplo:  
     `C:\Program Files\Eclipse Adoptium\jdk-17`
   - Edita `Path` y añade:  
     `C:\Program Files\Eclipse Adoptium\jdk-17in`

Cierra y vuelve a abrir la terminal para que tome efecto.

---

## Comandos útiles de comprobación

```bash
# Mostrar ruta real del binario en Linux
which java
readlink -f "$(which java)"

# Mostrar JAVA_HOME (si lo configuraste)
echo $JAVA_HOME
```

---

## Notas finales

- Con **OpenJDK 17** tendrás soporte LTS y compatibilidad con la mayoría de herramientas modernas.
- No es necesario definir `JRE_HOME`; el JDK ya incluye el entorno de ejecución.


</div>