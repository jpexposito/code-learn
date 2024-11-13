<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios)

## Programación de comunicaciones en red

<div align="center">
<img src=images/conexion-cliente-servidor.png width="400">
</div>

## Funcionamiento

Funcionamiento básico de comunicación entre **cliente y servidor** mediante `sockets` en `Java`, incluyendo las clases principales.

### ServerSocket

*Clase para crear un socket de servidor que espera conexiones entrantes*.

- `accept()`: Acepta conexiones de clientes y devuelve un Socket que representa la conexión con el cliente.

```java
ServerSocket serverSocket = new ServerSocket(port);
Socket clientSocket = serverSocket.accept();
```

### Socket

*Representa una conexión de red entre cliente y servidor*.

```java
Socket socket = new Socket(host, port);
```

**¿Para qué lo utilizamos?**  

- `En el cliente`, se usa **para conectarse al servidor**.
- `En el servidor`, representa una **conexión específica con un cliente**.

### InputStream y OutputStream

Flujos de entrada y salida para leer y enviar datos entre cliente y servidor.

**¿Qué métodos tengo que utilizar?**:

- `getInputStream()`: Obtiene el flujo de entrada desde el socket.
- `getOutputStream()`: Obtiene el flujo de salida desde el socket.

```java
InputStream in = clientSocket.getInputStream();
OutputStream out = clientSocket.getOutputStream();
```

### PrintWriter y BufferedReader

Clases de apoyo para leer y escribir datos de texto.

**¿Qué métodos tengo que utilizar?**:

- `readLine()`: Lee una línea completa del flujo de entrada.
- `println()`: Escribe una línea en el flujo de salida.

```java
PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
```

### Multithreading

Permite que el servidor maneje múltiples conexiones de clientes simultáneamente.
**¿Qué tengo que hacer?**:

- Crear una clase que implemente `Runnable` o extienda `Thread` para cada conexión de cliente.

```java
new Thread(new ClienteHandler(clientSocket)).start();
```

### InetAddress

Clase que representa una dirección IP.
**¿Qué métodos utilizar?**:

- `getLocalHost()`: Obtiene la dirección IP local.
- `getInetAddress()`: Obtiene la IP del cliente conectado.

```java
InetAddress address = socket.getInetAddress();
```

### Manejo de Excepciones

Debes de manejar excepciones del tipo `IOException`, para gestionar errores de conexión o problemas de red.

> *Asegurarse de cerrar el socket y los flujos de datos en un bloque `finally` para liberar recursos*.

[El ejemplo simple](RC-3.md).

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>