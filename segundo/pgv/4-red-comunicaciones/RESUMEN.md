<div align="justify">

# <img src=../../../images/computer.png width="40"> Code & Learn (Programación de Servicios - Programación de comunicaciones en red)

## Resumen

<div align="center">

<img src=images/client-request.png width="400">

</div>

El objetivo principal de las redes es interconectar varios dispositivos para compartir total o parcialmente sus recursos.

Las aplicaciones distribuidas son aquellas que se ejecutan en varios dispositivos a la vez.

La comunicación es un proceso complejo en el que se produce una transferencia de información entre agentes independientes. La forma más clásica de comunicar dispositivos digitales es aplicando el modelo de cliente servidor. El servidor es un dispositivo que contiene información a compartir con otros agentes llamados clientes. La conectividad basada en los protocolos TCP/IP sólo es factible bajo una arquitectura organizada en capas que segmente y automatice los procesos más rutinarios de cualquier comunicación.

Los procesos de una comunicación son aquellos que permiten a los dispositivos generar, transmitir y recibir un conjunto de señales secuenciadas de acuerdo con una convención común de forma que el receptor sea capaz de interpretar lo que el emisor quiere comunicar.

La información se genera siempre en las capas más superiores y para conseguir enviarse va atravesando sucesivas capas que irán incorporando información propia hasta llegar a los elementos físicos. La recepción se realizará en sentido contrario, comenzando por los elementos físicos y terminando en las capas superiores. Cada capa extraerá la información específica que le permitirá darle el tratamiento adecuado y ascenderá la información a la capa superior.

La capa más elevada de los nodos intermedios es la capa de red. El protocolo TCP está orientado a conexión. El protocolo UDP no. TCP es un protocolo pensado para comunicar sólo dos interlocutores. Las aplicaciones que necesiten transmitir a múltiples dispositivos tendrán que escoger entre aplicar el protocolo UDP, con el riesgo de pérdida de información que esto supone, o bien implementar múltiples conexiones dos a dos en las que poder controlar a través de TCP la transmisión.

Decimos que TCP es un protocolo fiable, con canales de comunicación full-duplex y orientados a flujos ( streaming-oriented ). TCP va modulando la velocidad de transmisión de acuerdo a las indicaciones de ambos extremos de la conexión y las suposiciones extraídas del ritmo de respuestas recibidas.

Las direcciones IP identifican las conexiones directas de un dispositivo de forma única. Las conexiones directas se conocen como interfaces de red. Cada dispositivo puede tener más de una. Una dirección IP base es aquella que, combinada con una máscara, permite definir un rango de direcciones IP de forma jerárquica. Es decir, el rango de todas las direcciones que empiecen por el valor de la dirección base comparando el número de bits de valor 1 de la máscara.

El nombre de un dominio es un identificador lógico (un nombre) que puede tener asociado varias direcciones IP. Los servidores DNS son servicios de Internet que devuelven las direcciones asociadas a un nombre de dominio. La clase del lenguaje Java que interroga el servicio DNS de la máquina y resuelve el direccionamiento es InetAdress.

Los recursos de Internet se identifican por medio de su URL o localizador único. Java dispone de clases específicas para referenciar recursos remotos mediante su URL . Las clases principales son URLy URLConnection.

Los zócalos de una conexión se definen como los extremos de una comunicación bidireccional. Representan la puerta de entrada y salida a la red y constituyen la base de cualquier aplicación distribuida. Los zócalos se encuentran asociados a una IP ya un puerto de forma que sea posible dirigir información a través de la red utilizando alguno de los protocolos sobre IP disponibles (TCP o UDP). Según el protocolo utilizado hablaremos de zócalos no orientados a conexión cuando utilicen el protocolo UDP y de zócalos orientados a conexión cuando utilicen el protocolo TCP. La clase Java que implementa un zócalo UDP es DatagramSockety las clases que implementan una conexión TCP son Sockety ServerSocket.

Java permite usar conexiones multicast (envíos UDP a varios destinos a la vez) con la clase [MulticastSocket](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/net/MulticastSocket.html).

_Los servicios de red son aquellos procesos que ejecutan una tarea bien definida y dirigida a responder a peticiones concretas realizadas desde dispositivos remotos que actúan a modo de clientes_. FTP permite gestionar la transferencia de archivos entre dos sitios situados en diferentes dispositivos. Se encuentra especificado en el documento RFC -959. FTP establece el uso de canales de comunicación diferentes para el tratamiento de los pedidos propios del protocolo y para la transferencia de información extra derivada de la resolución de algunos de estos pedidos.

El correo electrónico sigue un formato perfectamente estructurado en una cabecera y un cuerpo. La cabecera está constituida de varios campos con información extra que ayuda a realizar el tratamiento que necesite. La adjunción de archivos y el formato de texto en un correo son posibles gracias a la especificación MIME. El servicio de transmisión de mensajes se llama SMTP y se encarga de enviar los mensajes al almacén final donde se encuentra ubicada la dirección destino y que acostumbramos a conocer como buzón. Del acceso del destinatario a los buzones pueden encargarse varios protocolos. El más extendido es POP3.

Java dispone de una clase para implementar clientes de correos que se llama JavaMail. Genera correos que cumplen el formato estándar de forma bastante sencilla, trabaja con cualquier servidor SMTP y puede utilizar varios protocolos de acceso a los buzones.

El servir WWW se basa en transferir documentos definidos por medio del lenguaje de marcas HTML utilizando el protocolo de comunicación HTTP. Actualmente, los recursos estáticos han dejado paso a las aplicaciones que generan documentos HTML dinámicamente. Es lo que se conoce como aplicaciones web. Java ofrece la biblioteca Servlets para crear aplicaciones web de forma muy simple.

El principal problema a la hora de implementar un servicio o aplicación distribuida es la escalabilidad. La monitorización de las conexiones y el diseño basado en componentes puede ayudarnos a crear servicios y aplicaciones distribuidas escalables.

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>