<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Objetos en Java)

En Java, un objeto es una instancia de una clase. Una clase es una plantilla que define el comportamiento y las características de un tipo de objeto. Los objetos son fundamentales en la programación orientada a objetos (OOP) y representan entidades con propiedades y comportamientos asociados.

## Definición de una Clase en Java

En Java, una clase se define de la siguiente manera:

```java
public class MiClase {
    // Atributos (variables de instancia)
    int atributo1;
    String atributo2;
    
    // Constructor
    public MiClase(int valor1, String valor2) {
        atributo1 = valor1;
        atributo2 = valor2;
    }
    
    // Métodos
    public void metodo1() {
        System.out.println("Método 1 ejecutado.");
    }
    
    public String metodo2() {
        return "Método 2 ejecutado.";
    }
}
```

## Constructores de clase

# Objetos en Java

En Java, un objeto es una instancia de una clase. Una clase es una plantilla que define la estructura y el comportamiento de los objetos. Los objetos son la base de la programación orientada a objetos (POO) y se utilizan para modelar conceptos del mundo real en software.

## Definición de una Clase y Creación de Objetos

Para definir una clase en Java, se utiliza la palabra clave `class`. A continuación, se muestra un ejemplo de una clase simple llamada `Persona`:

```java
public class Persona {
    // Atributos
    String nombre;
    int edad;
    
    // Constructor
    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
    
    // Método
    public void saludar() {
        System.out.println("Hola, soy " + nombre + " y tengo " + edad + " años.");
    }
}
```

### Constructores en Java

En Java, un constructor es un bloque de código especial dentro de una clase que se utiliza para inicializar un objeto recién creado. Su objetivo principal es asegurarse de que un objeto tenga un estado inicial válido antes de que se utilice.

#### Sintaxis de un Constructor

```java
public class MiClase {
    // Atributos
    
    // Constructor
    public MiClase() {
        // Código para inicializar atributos
    }
}
```

- __El constructor tiene el mismo nombre que la clase__.
- __No tiene un tipo de retorno, ni siquiera VOID__.
- __Puede aceptar parámetros para inicializar los atributos del objeto__.

### Creación de Objetos en Java

Para crear un objeto a partir de una clase, se utiliza el operador __new__. Por ejemplo:

```java
Persona persona1 = new Persona("Juan", 30);
``` 

Ahora, puedes crear objetos a partir de esta clase. Por ejemplo:

```java
// Crear objetos de la clase Persona
Persona persona1 = new Persona("Juan", 30);
Persona persona2 = new Persona("María", 25);

// Llamar al método saludar
persona1.saludar();
persona2.saludar();
```

### Ejemplo Práctico: Manipulación de Objetos

Supongamos que estamos creando una aplicación que gestiona una biblioteca. Podemos definir una clase Libro y luego crear objetos de esa clase para representar libros en la biblioteca. Aquí tienes un ejemplo:

```java
public class Libro {
    String titulo;
    String autor;
    int añoPublicacion;
    
    public Libro(String titulo, String autor, int añoPublicacion) {
        this.titulo = titulo;
        this.autor = autor;
        this.añoPublicacion = añoPublicacion;
    }
    
    public void mostrarInformacion() {
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Año de Publicación: " + añoPublicacion);
    }
}

// Crear objetos de la clase Libro
Libro libro1 = new Libro("El Gran Gatsby", "F. Scott Fitzgerald", 1925);
Libro libro2 = new Libro("Cien años de soledad", "Gabriel García Márquez", 1967);

// Mostrar información de los libros
libro1.mostrarInformacion();
libro2.mostrarInformación();

```

_En este ejemplo, hemos creado una clase Libro con atributos y métodos para representar libros. Luego, creamos objetos_ ___libro1 y libro2__ _y llamamos al método mostrarInformación para mostrar los detalles de cada libro_.

_Los objetos en Java permiten modelar datos y comportamientos de una manera orientada a objetos, lo que facilita la organización y la manipulación de la información en el software._

## Propiedades y Encapsulación en Java

En Java, las **propiedades** se refieren a las características o atributos que tienen los objetos de una clase. Estas propiedades pueden representar datos como números, texto, fechas, etc.

### Definición de Propiedades

Las propiedades se definen dentro de una clase y pueden ser de diferentes tipos de datos. Aquí hay un ejemplo de una clase `Persona` con propiedades `nombre` y `edad`:

```java
public class Persona {
    String nombre;  // Propiedad: nombre
    int edad;       // Propiedad: edad
}
```

## Encapsulación

La encapsulación es un concepto de la programación orientada a objetos que implica ocultar el estado interno de un objeto y proporcionar acceso controlado a través de métodos públicos. Esto se logra utilizando modificadores de acceso __(como public, private, etc.)__ y métodos getter y setter.

### Modificadores de Acceso

- __public__: Indica que la propiedad o método es accesible desde cualquier parte del programa.
- __private__: Indica que la propiedad o método solo es accesible dentro de la clase donde se declara.
- __protected__: Permite el acceso desde la clase actual y sus subclases.

### Getter y Setter

Los getter son métodos utilizados para obtener el valor de una propiedad, mientras que los setter se utilizan para establecer o modificar el valor de una propiedad.

```java
public class Persona {
    private String nombre;  // Propiedad: nombre
    private int edad;       // Propiedad: edad
    
    // Getter para nombre
    public String getNombre() {
        return nombre;
    }
    
    // Setter para nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    // Getter para edad
    public int getEdad() {
        return edad;
    }
    
    // Setter para edad
    public void setEdad(int edad) {
        this.edad = edad;
    }
}
```

### Beneficios de la Encapsulación

- __Control del Acceso__: Permite controlar quién puede acceder y modificar las propiedades de un objeto.
- __Validación de Datos__: Permite validar los datos antes de asignarlos a una propiedad.
- __Mantenimiento y Flexibilidad__: Facilita la modificación interna de una clase sin afectar el código que la utiliza.

_La encapsulación es una práctica fundamental en Java y en la programación orientada a objetos en general. Ayuda a crear código más robusto y mantenible al proteger y controlar el acceso a las propiedades de un objeto._

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles.

</div>
