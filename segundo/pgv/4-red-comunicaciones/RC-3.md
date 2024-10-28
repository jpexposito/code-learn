<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Programación de Servicios (Chat))

## Sockets

<div align="center">
<img src=images/client-request.png width="400">
</div>

## Descripción del Proyecto

Vamos a crear una aplicación de chat en Java que permita la comunicación en tiempo real entre múltiples clientes a través de un servidor. El servidor se encargará de recibir los mensajes de los clientes y redistribuirlos a todos los demás clientes conectados.

## Funcionalidades

- **Servidor**:
  - Escuchar conexiones entrantes en un puerto específico.
  - Aceptar múltiples conexiones de clientes.
  - Recibir mensajes de un cliente y enviarlos a todos los demás clientes conectados.
  - Manejo adecuado de la desconexión de clientes.

- **Cliente**:
  - Conectarse al servidor de chat.
  - Enviar mensajes al servidor.
  - Recibir y mostrar mensajes de otros clientes.

## Estructura del Código

### Servidor de Chat

```java
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final Set<PrintWriter> clientWriters = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        System.out.println("Servidor de chat iniciado...");
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                clientWriters.add(out);

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + message);
                    sendMessageToAllClients(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clientWriters.remove(out);
            }
        }

        private void sendMessageToAllClients(String message) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }
    }
}
```

### Cliente

```java
import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        System.out.println("Cliente de chat iniciado...");
        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            Thread readThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println("Mensaje: " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readThread.start();

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Ejecuta el servidor

- Crea el proyecto y compila

```java
java ChatServer
```

> **Con Maven**: mvn exec:java -Dexec.mainClass="com.tu_paquete.ChatServer"

### Ejecuta el Cliente

```java
mvn exec:java -Dexec.mainClass="com.tu_paquete.ChatClient"
```

> Puedes abrir múltiples terminales y ejecutar el cliente varias veces para simular varios usuarios.

### Funcionamiento

#### Servidor

El servidor escucha conexiones en el puerto 12345. Cada vez que un cliente se conecta, se crea un nuevo hilo (ClientHandler) que maneja la comunicación con ese cliente. Los mensajes son enviados a todos los clientes conectados utilizando un conjunto concurrente, garantizando la seguridad en un entorno multihilo.

#### Cliente

El cliente se conecta al servidor y permite al usuario enviar mensajes a través de la consola. También escucha los mensajes enviados por otros clientes y los muestra en la consola.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>