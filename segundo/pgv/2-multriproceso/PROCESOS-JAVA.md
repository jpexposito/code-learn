<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Programación de Servicios)

## Creación rápida de procesos con Java con Runtime

<div align="center">

<img src=images/procesos.png width="400">
</div>

### Creación rápida de procesos

La clase __java.lang.Runtime__ se usa principalmente para interactuar con el _JRE de Java_. Esta clase proporciona métodos para lanzar procesos, llamar al recolector de basura (__Garbage Collector__), saber la _cantidad de memoria disponible y libre, etc_.

[Especificación java.lang.Runtime](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Runtime.html)

Cada aplicación en Java tiene acceso a una única instancia de _java.lang.Runtime_ a través del método __Runtime.getRuntime()__ que devuelve la instancia singleton de la clase __Runtime__.

> Patrones de diseño: Singleton
> 
> __¿Qué son los patrones de diseño? ¿Qué es y para qué se usa el patrón de diseño singleton?__
>
> Investiga cómo realizar una clase que siga el patrón de diseño singleton.
> [Diseño basado en patrones](https://refactoring.guru/es/design-patterns/java).

El método que nos interesa a nosotros para la creación de procesos es

```java
public Process exec(String command) throws IOException
```

Veamos un ejemplo sencillo de uso de este método

```java
public static void main(String[] args) throws IOException {
    // Launch notepad app
    Runtime.getRuntime().exec("notepad.exe");
}
```

Se puede observar que en el parámetro que pasamos al método exec __indicamos el programa que queremos ejecutar__. En este caso, como el __notepad__ se encuentra en el __PATH__ del sistema, no es necesario indicar la ruta donde se encuentra el programa. _En otro caso, sí tendríamos que hacerlo_.

### Propiedades del sistema y comandos del sistema

Si tenemos pensado desarrollar aplicaciones que funcionen en diferentes SO tendremos que enfrentarnos a la problemática del funcionamiento diferente de los distintos SO.

Vamos a ver algunos ejemplos que pueden servir como guía para otros problemas similares a los expuestos.

>💡File separator
>
>Para indicar las rutas en un sistema los sistemas UNIX emplean el caracter / como separador mientras que los sistemas Windows usan el caracter \ . En resumen, / en *X y \ en Windows.
>
>¿Cómo podemos hacer entonces que nuestras aplicaciones sean independientes del SO en el que se ejecutan?
>
>Para este tipo de cuestiones vamos a utilizar de forma recurrente las propiedades del sistema mediante System.getProperty(String propName). Estas propiedades se configuran con el propio sistema operativo, aunque las podemos modificar usando los parámetros de ejecución de la máquina virtual
>
>String separator = System.getProperty("file.separator");
>
>o
>
>-Dfile.separator
>
>Aunque siempre es una buena práctica usar el caracter / en las rutas ya que Java es capaz de convertirlas al sistema en el que se ejecuta.

Si lo que queremos es ejecutar un comando del SO, tenemos que hacerlo, al igual que si lo hacemos manualmente, a través del shell del sistema, donde volvemos a encontrar la dicotomía entre sistemas UNIX y sistemas Windows.

Vamos a ver el código que, a través de las propiedades del sistema, nos permite obtener un listado de los archivos existentes en la carpeta personal del usuario.

```java
// Primero obtenemos la carpeta del usuario
String homeDirectory = System.getProperty("user.home");
boolean isWindows = System.getProperty("os.name")
  .toLowerCase().startsWith("windows");

if (isWindows) {
    Runtime.getRuntime()
      .exec(String.format("cmd.exe /c dir %s", homeDirectory));
} else {
    Runtime.getRuntime()
      .exec(String.format("sh -c ls %s", homeDirectory));
}
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>