<div align="justify">

# <img src=../../../../images/computer.png width="40"> Code & Learn (Destrucción de Objetos y Liberación de Memoria en Java)

En Java, la gestión de memoria es manejada automáticamente por el __recolector de basura (garbage collector)__. Los desarrolladores __NO__ necesitan preocuparse explícitamente por la liberación de memoria, como en lenguajes de bajo nivel. Sin embargo, entender cómo funciona el proceso puede ser beneficioso. Aquí hay una descripción general:

## Garbage Collector

Java utiliza un garbage collector para identificar y eliminar los objetos que ya no son accesibles. Este proceso se ejecuta en segundo plano y se encarga de recuperar la memoria ocupada por objetos que ya no son referenciados.

## Referencias

La recolección de basura se basa en referencias. Cuando un objeto no tiene referencias que lo apunten, se considera candidato para ser recolectado.

```java
// Crear un objeto
MiClase objeto = new MiClase();

// Hacer que la referencia apunte a null
objeto = null; // El objeto ahora es candidato para la recolección de basura
```

## Método finalize()

La clase __Object en Java tiene un método finalize()__, que es llamado por el recolector de basura antes de liberar la memoria del objeto. Sin embargo, se recomienda evitar depender de este método, ya que no hay garantía de cuándo será invocado.

```java
@Override
protected void finalize() throws Throwable {
    // Código para liberar recursos antes de que el objeto sea recolectado
    super.finalize();
}
```

## Liberación de Recursos Externos

Si un objeto utiliza recursos externos como archivos o conexiones a bases de datos, se debe implementar la interfaz AutoCloseable o Closeable para garantizar que los recursos se liberen adecuadamente.

```java
public class MiRecurso implements AutoCloseable {
    // Implementar lógica para liberar recursos

    @Override
    public void close() throws Exception {
        // Cerrar recursos aquí
    }
}
```

## Uso de Bloques try-with-resources

Java 7 introdujo el bloque __try-with-resources__, que automáticamente cierra los recursos al salir del bloque, reduciendo la necesidad de gestión manual de recursos.

```java
try (MiRecurso recurso = new MiRecurso()) {
    // Código que utiliza el recurso
} // El recurso se cerrará automáticamente al salir del bloque
```

## Conclusiones

_Aunque los desarrolladores no necesitan liberar manualmente la memoria en Java, es crucial entender el manejo de recursos y referencias para evitar posibles fugas de memoria y optimizar el_ ___RENDIMIENTO___ _de la aplicación_.

_La automatización de la gestión de memoria en Java simplifica el desarrollo y reduce errores asociados con la gestión manual de memoria en lenguajes de bajo nivel_.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles.

</div>