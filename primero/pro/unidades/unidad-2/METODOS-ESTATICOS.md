<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Métodos Estáticos en Java)

En Java, un método estático es un método que pertenece a la clase en lugar de a una instancia específica de la clase. Esto significa que puedes llamar a un método estático sin necesidad de crear una instancia de la clase.

## Definición de un Método Estático

Un método estático se define utilizando la palabra clave static. La sintaxis es la siguiente:

```java
public static tipo_de_retorno nombre_del_metodo(tipo_de_parametro1 parametro1, tipo_de_parametro2 parametro2, ...) {
    // Código del método
    return valor_de_retorno; // (si el método devuelve un valor)
}
```

- __public__: Modificador de acceso que determina la visibilidad del método.
- __static__: Indica que el método es estático.
- __tipo_de_retorno__: Es el tipo de dato que el método devuelve. Puede ser cualquier tipo de dato válido en Java.
nombre_del_metodo: Es el nombre del método.
- __tipo_de_parametro1, tipo_de_parametro2, etc.__: Son los tipos de datos de los parámetros que el método recibe. Pueden ser múltiples o incluso ninguno.
- __parametro1, parametro2, etc.__: Son los nombres que se utilizan dentro del método para referirse a los valores que se pasan como argumento.

## Llamada a un Método Estático

Para llamar a un método estático, se utiliza el nombre de la clase seguido del nombre del método:

```java
tipo_de_retorno resultado = NombreDeLaClase.nombre_del_metodo(argumento1, argumento2, ...);
```

## Uso de Métodos Estáticos

Acceso a Variables Estáticas: Los métodos estáticos pueden acceder a variables estáticas (también conocidas como variables de clase) sin necesidad de una instancia de la clase.

```java
public class Ejemplo {
    static int contador = 0;
    
    public static void incrementarContador() {
        contador++;
    }
}
```

- __Métodos de Utilidad__: Los métodos estáticos se utilizan a menudo para crear funciones de utilidad que no dependen del estado de una instancia específica.

```java
public class MathUtils {
    public static int suma(int a, int b) {
        return a + b;
    }
}
```

- __Factorías__: Se pueden utilizar métodos estáticos para crear instancias de una clase, como en el patrón de diseño de fábrica.

```java
public class Persona {
    private String nombre;
    private int edad;
    
    public static Persona crearPersona(String nombre, int edad) {
        Persona nuevaPersona = new Persona();
        nuevaPersona.nombre = nombre;
        nuevaPersona.edad = edad;
        return nuevaPersona;
    }
}
```

## Conclusiones

Los métodos estáticos en Java son útiles cuando quieres definir comportamientos o características que son compartidas por todas las instancias de una clase. No pueden acceder a variables de instancia y deben ser invocados a través del nombre de la clase en lugar de una instancia de la misma.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles.

</div>