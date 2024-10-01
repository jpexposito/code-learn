<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Definición de Clases en Java)

Una **clase** en Java es una plantilla que define la estructura y el comportamiento de los objetos que se crearán a partir de ella. Las clases encapsulan datos y métodos que operan sobre esos datos, permitiendo la creación de objetos que representan entidades del mundo real o conceptos abstractos en un programa.

Las clases son fundamentales en la programación orientada a objetos, permitiendo la creación de programas modulares y reutilizables.

## Componentes de una Clase

1. **Modificadores de Acceso**
   - Controlan la visibilidad de la clase y sus miembros (atributos y métodos).
   - **`public`**: Accesible desde cualquier parte del programa.
   - **`private`**: Accesible solo dentro de la misma clase.
   - **`protected`**: Accesible desde la misma clase, clases derivadas y otras clases en el mismo paquete.
   - **Sin modificador** (default): Accesible solo dentro del mismo paquete.

2. **Atributos (Variables de Instancia)**
   - Son las características o propiedades de un objeto.
   - Pueden ser de diferentes tipos de datos (int, String, boolean, etc.).
   - Se declaran dentro de la clase pero fuera de los métodos.
  
   ```java
   private String nombre;
   private int edad;
   ```

3. **Constructor**

- Es un método especial que se llama cuando se crea un objeto de la clase.
- Tiene el mismo nombre que la clase y no tiene tipo de retorno.
- Se utiliza para inicializar los atributos de un objeto.

    ```java
    public NombreDeLaClase(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
    ```


4. **Métodos**

- Definen las acciones que un objeto de la clase puede realizar.
- Pueden recibir parámetros y devolver valores.
- Pueden ser métodos de __instancia (operan sobre instancias de la clase)__ o __métodos estáticos (pertenecen a la clase en sí)__.

    ```java
    public String obtenerNombre() {
        return nombre;
    }

    public void establecerNombre(String nombre) {
        this.nombre = nombre;
    }
    ```

5. **this**
   
- Es una referencia al objeto actual.
- Se utiliza para diferenciar entre atributos de clase y __parámetros de métodos o constructores con el mismo nombre__.

    ```java
    public void establecerNombre(String nombre) {
        this.nombre = nombre;  // `this` se refiere al atributo de la clase
    }
    ```

6. **Métodos static**

- Son métodos que pertenecen a la clase en lugar de a las instancias de la clase.
- Pueden ser llamados sin crear una instancia de la clase.

    ```java
    public static void metodoEstatico() {
        System.out.println("Este es un método estático.");
    }
    ```

7. **Método main**

- Es el punto de entrada de un programa Java.
- Es un método estático que se utiliza para ejecutar el código.

```java
public static void main(String[] args) {
    // Código que se ejecuta al iniciar el programa
}
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles

</div>