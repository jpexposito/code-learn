<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Programación de Servicios)

## Patrón Singleton

<div align="center">

<img src=images/patron-singleton-en-la-practica.png width="400">
</div>

### Definición global

El patrón __Singleton__ es un patrón de diseño creacional que asegura que una __clase tenga solo una instancia__ y proporciona un punto de acceso global a esa instancia. En otras palabras, _el patrón Singleton_ ___garantiza___ _que solo haya una única instancia de una clase en el sistema y que todos los componentes que necesiten esa instancia puedan_ ___acceder a ella de manera controlada___.

### Conceptos Clave del Patrón Singleton

1. __Instancia Única__: Se crea una sola instancia de la clase para evitar duplicados.
2. __Acceso Global__: Proporciona un punto de acceso global, generalmente mediante un método estático.
3. __Control de Acceso__: La instancia es gestionada internamente, evitando la creación de múltiples instancias.

### ¿Para Qué Sirve el Patrón Singleton?

El patrón Singleton es útil en los siguientes escenarios:

- __Recursos Compartidos__: Garantiza que todos los componentes utilicen el mismo recurso compartido, como una conexión a una base de datos.
- __Control de Acceso a Recursos__: Evita conflictos en recursos limitados, como acceso a una impresora o red.
- __Configuración Global__: Se utiliza para mantener configuraciones globales consistentes en la aplicación.
- __Gestión de Estado__: Permite que un estado global sea compartido a lo largo de toda la aplicación.

### Implementación del Patrón Singleton en Java

Aquí tienes un ejemplo básico de implementación del patrón Singleton en Java:

```java
public class Singleton {
    // La única instancia de la clase, inicializada como null.
    private static Singleton instanciaUnica = null;

    // Constructor privado para evitar la creación de instancias fuera de la clase.
    private Singleton() {
        // Inicialización del recurso compartido
    }

    // Método estático que proporciona el punto de acceso global a la instancia única.
    public static Singleton getInstancia() {
        if (instanciaUnica == null) {
            // Sincronización para el acceso seguro en entornos multihilo.
            synchronized (Singleton.class) {
                if (instanciaUnica == null) {
                    instanciaUnica = new Singleton();
                }
            }
        }
        return instanciaUnica;
    }

    // Métodos para realizar operaciones con la instancia única.
    public void realizarOperacion() {
        // Operación específica de la instancia
    }
}
```

### Observa que

- __Instancia Única__: La variable instanciaUnica es la única instancia de la clase Singleton.
- __Constructor Privado__: El constructor privado asegura que no se puedan crear nuevas instancias desde fuera de la clase.
- __Método Estático getInstancia()__: _Este método proporciona un punto de acceso global_. Si la instancia no existe, se crea; si ya existe, se devuelve la instancia existente.
- __Sincronización__: En entornos ___multihilo___, se utiliza la sincronización para evitar que varios hilos creen múltiples instancias simultáneamente.

> __Uso del Patrón Singleton en Procesos__
>_En el contexto de procesos o sistemas operativos, el patrón Singleton se puede utilizar para gestionar recursos globales y evitar la creación innecesaria de múltiples instancias de un recurso único. Algunos ejemplos incluyen_:
>
> - ___Gestión de Conexiones a Bases de Datos___: _Un Singleton puede gestionar una única conexión para evitar que múltiples procesos creen conexiones adicionales a la misma base de datos_.
> - ___Configuración Global del Sistema___: Un Singleton puede manejar configuraciones globales, garantizando que diferentes componentes del sistema utilicen la misma configuración.

### Ejemplo de Uso en Procesos

- Conexiones a Bases de Datos: Un Singleton asegura que todos los procesos utilicen la misma instancia de conexión.
- Recursos Compartidos: Como el acceso a una impresora o una API, para evitar colisiones y garantizar que solo una instancia maneje el recurso.

> __El patrón Singleton es útil para controlar la creación de instancias y gestionar recursos compartidos en aplicaciones Java, proporcionando un único punto de acceso global__.


### Ejemplo en BBDD

```java
public class DbConnection {

    private static DbConnection instance = null;
    private Connection conn = null;

    private DbConnection() {}

    private void init() throws SQLException {
        final String DB_URL = "jdbc:mysql://localhost:3306/library";
        final String USER = "root";
        final String PASS = "12345";
        
        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        System.out.println("Connected to database");
    }

    public Connection getConnection() {
        return conn;
    }

    public static Connection getInstance() throws SQLException {
        if (instance != null && !instance.getConnection().isClosed()) {
            return instance;
        } else {
            instance = new DbConnection();
            instance.init();
        }
    }
}
```

> Pregunta:__¿Es necesario en BBDD? (Si|No) Justifica la respuesta__

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>