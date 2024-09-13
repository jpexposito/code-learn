<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Estructura de un programa Java)

## Estructura de un programa Java

Java es un lenguaje de programación __orientado a objetos__, y esto implica que necesitamos trabajar con __clases y objetos__. Más adelante veremos qué es una clase, pero, por ahora, solo necesitamos saber que cada fragmento de código en Java debe colocarse dentro de una classcláusula.

1. Nuestro primer programa Java
Veamos cómo empezar con Java, creando un programa Java simple que imprime “Hola” en la pantalla.

```java
public class MyClass
{
    public static void main(String[] args)
    {
        System.out.println("Hello");
    }
}
```

Vamos a explicar este código:

- La primera línea inicializa la clase en la que vamos a colocar el código. Estamos creando una clase llamada __MyClass__ .
- Todo código dentro de esta clase debe estar entre llaves __{ …}__
A continuación, iniciamos otro fragmento de código llamado __main__ , que es el fragmento de _código principal que se_ ___iniciará___ _cuando ejecutemos el programa_. En Java, _este bloque principal siempre debe ser_ ___public , static y void___ , y debe tener un conjunto de parámetros ___String[]___ . Más adelante aprenderemos qué significa todo esto. Nuevamente, todo el código que pertenece a este bloque principal debe estar encerrado entre sus llaves correspondientes.
- Finalmente, dentro de las llaves del bloque principal , añadimos todas las instrucciones que queremos ejecutar. En este caso, añadimos una instrucción System.out.println , que se encarga de imprimir en pantalla el texto que especifiquemos ( Hello , en este caso). Además, es importante terminar cada instrucción con un punto y coma ;. Esto le indica al compilador que la instrucción ha terminado y podemos empezar a evaluar la siguiente. Podríamos, de esta forma, escribir más de una instrucción por línea, aunque esta forma de escribir programas no es muy habitual.

2. Más sobre este ejemplo

La estructura de este programa es muy similar a la del mismo programa escrito en __C#__: siempre necesitamos definir una clase, aunque solo necesitemos un mainbloque. Este mainbloque está escrito en minúsculas en Java.

Además, cada clase pública debe tener el mismo nombre que el archivo fuente que la contiene, por lo que necesitamos almacenar el código fuente del ejemplo anterior en un archivo llamado ___MyClass.java__ _(los archivos fuente de Java tienen .javaextensión )_. Si queremos compilar este código, utilizamos javacherramientas de nuestra instalación de __JDK__. _Podemos hacerlo a través de cualquier IDE, como Geany, o IntelliJ, siempre y cuando tengamos Java JDK correctamente instalado_.

```java
javac MyClass.java
```

Luego __MyClass.class__ se generará. Este es el archivo compilado que se puede ejecutar bajo la máquina virtual de Java __(JVM)__, mediante el javacomando. Este último paso también se puede realizar bajo cualquier IDE.

```java
java MyClass
````

Después de ejecutar este programa, veremos un mensaje de __“Hola”__ en la pantalla.

</div>