<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Programación de Servicios)

## Sockets

<div align="center">
<img src=images/client-request.png width="400">
</div>

### Servidor

El siguiente código muestra un servidor básico que acepta conexiones de clientes y responde con un eco de los mensajes recibidos.

```java
import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) throws IOException {
        int port = 1234;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor escuchando en el puerto " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();  // Espera una conexión de cliente
            System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Recibido: " + message);
                out.println("Eco: " + message);  // Responde al cliente
            }

            clientSocket.close();
        }
    }
}
```

### Cliente

Este código de cliente permite al usuario enviar mensajes al servidor, que devuelve el mensaje como respuesta.

```java
import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 1234;
        Socket socket = new Socket(host, port);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        String userInput;
        while ((userInput = console.readLine()) != null) {
            out.println(userInput); 
            System.out.println("Respuesta del servidor: " + in.readLine());
        }

        socket.close();
    }
}
```

[Con hilos](RC-4.md).

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>