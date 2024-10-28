<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Programación de Servicios)

## Sockets

<div align="center">
<img src=images/client-request.png width="400">
</div>

***Un socket es la conexión que se establece entre dos aplicaciones en dos hosts diferentes, una aplicación cliente en un host y otra aplicación servidor en otro (siguiendo la arquitectura cliente-servidor) a través de una red (LAN, WAN, . . .)***

<img src=https://psp.codeandcoke.com/_media/apuntes:sockets.png width="300">

***Java*** dispone de toda una **API** para trabajar con sockets y todo lo necesario para desarrollar ***aplicaciones cliente-servidor***.

Para trabajar con sockets en Java disponemos de la ***clase Socket*** y ***ServerSocket*** para *realizar conexiones desde un cliente o para establecer la conexión de un servidor, respectivamente**.

### Sockets cliente

Para que una aplicación Java pueda realizar una conexión de red mediante un socket cliente, necesitaremos dos parámetros: ***dirección IP del host al que nos queremos conectar y el puerto** donde `escucha` la aplicación servidor a la que nos queremos conectar. A continuación, es esencial establecer los flujos de comunicación que permitirán comunicarnos hacia el servidor (flujo de salida) y recibir los mensaje que éste nos envíe (flujo de entrada).

```java
. . .
// Realiza la conexión con el host remoto
Socket socketCliente = new Socket("videosdeinformatica.com", 5555);
// Establece los flujos de comunicación de entrada y salida
PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
. . .
```

### Sockets servidor

Los sockets servidor o ***ServerSocket*** permiten que aplicaciones Java puedan establecer una conexión en un equipo en un puerto determinado y de esa manera ser capaces de recibir conexiones de clientes para comunicarse con dicha aplicación.

Para establecer un socket servidor sólo es necesario indicar el puerto en el que la aplicación quedará `escuchando` las conexiones de los clientes.

Una vez establecida la conexión, la clase ***ServerSocket*** dispone del método `accep()` que ***bloquea la ejecución de la aplicación hasta que se recibe la conexión de un cliente***. *En ese momento se devuelve una referencia al socket de dicho cliente y es posible establecer los flujos de comunicación con el mismo para comenzar a dar servicio*. Hay que tener en cuenta, según se puede observar en el gráfico anterior, que el flujo de entrada del socket cliente será el de salida para el servidor y viceversa.

<div align="center">
<img src=https://psp.codeandcoke.com/_media/apuntes:cliente_servidor.jpg width="400">
</div>
```java
. . .
// El servidor comienza a escuchar en el puerto 5555
ServerSocket socketServidor = new ServerSocket(5555);
. . .
// Recibe la conexión de un cliente
Socket socketCliente = socketServidor.accept();
// Establece los flujos de comunicación con ese cliente
PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);
BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
. . .
```

El ejemplo anterior describe un funcionamiento muy simple de un servidor puesto que sólo sería capaz de atender la petición de un cliente, ya que una vez recibida ésta y establecer sus flujos de comunicación no sigue esperando nuevas conexiones. ***Es por eso que necesitaríamos de la programación multihilo (Threads) para dotar a nuestra aplicación de capacidades concurrentes***.

A continuación se muestra una forma sencilla de hacer que nuestra aplicación servidor, al recibir una conexión la prepara y la lanza como un hilo. De esta forma es capaz de volver a escuchar nuevas conexiones mientras está atendiendo las ya recibidas.

<div align="center">
<img src=https://psp.codeandcoke.com/_media/apuntes:multithread.jpg width="400">
</div>

```java
. . .
// El servidor comienza a escuchar en el puerto 5555
ServerSocket socketServidor = new ServerSocket(5555);
. . .
// Recibe la conexión de un cliente
while (conectado) {
  Socket socketCliente = socketServidor.accept();
  // Establece los flujos de comunicación con ese cliente
  PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);
  BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
  // Crea y lanza un hilo para atender a ese cliente
  ConexionCliente conexionCliente = new ConexionCliente(socketCliente, salida, entrada);
  conexionCliente.start();
}
. . .
```

### Aplicaciones cliente-servidor en Java

Cliente/Servidor echo
echo es un servicio que simplemente repite el mensaje que el cliente le envía a través de un socket. Es un servicio muy sencillo (y poco útil) pero que nos permitirá comprobar la conectividad y la funcionalidad de los sockets para un primer instante.

Cliente echo

```java
. . .
// Nombre y puerto del servidor
String hostname = "videosdeinformatica.com"; 
int puerto = 7;
try {
  Socket socket = new Socket(hostname , puerto);
  PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
  BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  // Captura el teclado del usuario
  BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in) );
  String cadena = null;
  // Envia lo que el usuario escribe por teclado al servidor y lee la respuesta
  while ((cadena = teclado.readLine()) != null) { 
    salida.println(cadena); 
    System.out.println(entrada.readLine());
  }
} catch (UnknownHostException uhe) { 
  . . . 
} catch (IOException ioe) { 
  . . . 
}
. . .
Servidor echo
. . .
int puerto = 7; 
try {
  ServerSocket socketServidor = new ServerSocket(puerto);
  // Espera la conexion con un cliente
  Socket socketCliente = socketServidor.accept();
  // Establece los flujos de salida y entrada (hacia y desde el cliente, respectivamente)
  PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true); 
  BufferedReader entrada = new   BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
  // Envia algunos mensajes al cliente en cuanto este se conecta
  salida.println("Solo se repetir lo que me escribas"); 
  salida.println("Cuando escribas ’.’, se terminara la conexion");
  String linea = null;
  while ((linea = entrada.readLine()) != null) {
    if (linea.equals(".")) { 
      socketCliente.close(); 
      socketServidor.close(); 
      break;
    } 
  }
} catch (IOException ioe) { 
  . . . 
}
. . .
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>