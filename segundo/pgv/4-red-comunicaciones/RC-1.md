<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Programación de Servicios)

## Programación de comunicaciones en red

<div align="center">
<img src=images/client-request.png width="400">
</div>

> Actualmente es imposible concebir ningún dispositivo de procesamiento de la información (sea ordenador, móvil, libro electrónico…) sin capacidad para comunicarse. La gran mayoría de aplicaciones actuales necesitan una conexión para instalarse o para actualizarse y buena parte de ellas la necesitan también para poder ejecutarse con normalidad.

A menudo, las aplicaciones trabajan con recursos en la nube o extraen los datos de un SGBD situado en un servidor remoto. Gracias a la conectividad de nuestros dispositivos podemos ver películas sin tener que almacenarlas en un disco local, podemos sincronizar los relojes con la hora oficial, comprar entradas para ir al teatro sin movernos de casa o hacer una partida online de nuestro videojuego preferido. Toda esa capacidad de comunicación sólo es posible gracias a las redes.

> ***El objetivo principal de las redes es interconectar varios dispositivos para compartir total o parcialmente sus recursos**.

> ***¿Qué son los protocolos?***
>
>Los protocolos ***son especificaciones que definen cuál debe ser el comportamiento de las distintas partes de un sistema***. En concreto, ***los protocolos de comunicación a través de la red describen el papel de todos los elementos de la red (sean físicos o lógicos) implicados en la intercomunicación***.

Para conseguir la interconectividad de los dispositivos, las redes han ido definiendo elementos estándares físicos (enrutadores, conmutadores, concentradores…) o lógicos (protocolos y bibliotecas de comunicación) para que las aplicaciones puedan acceder a los recursos remotos fácilmente, reduciendo en lo posible la complejidad de la implementación. Esto ha permitido crear aplicaciones distribuidas cada vez más transparentes al usuario.

> ***Entendemos por aplicación distribuida aquella que se ejecuta en más de un dispositivo. Entendemos por aplicación distribuida transparente aquella aplicación distribuida que no necesita un tratamiento especial por estar distribuida o, al menos, lo reduce considerablemente***.

> ***Desde el punto de vista del programador el concepto de transparencia se aplica en referencia a la menor cantidad de código que habrá que escribir utilizando herramientas y bibliotecas que automaticen los procesos básicos de comunicación en la red***.

***La evolución de Internet ha permitido también la creación de una única red de comunicación en la que todos los dispositivos o redes inicialmente aisladas (locales) adquieren la capacidad de interactuar***. Se acuña así el término de red global. El éxito de la red global hace aparecer nuevos elementos estándar que avanzan en la línea de la programación distribuida transparente.

### Aspectos teóricos de la comunicación

La comunicación es un proceso complejo en el que se produce una transferencia de información entre agentes independientes. Es importante subrayar el término independiente porque esto implica que cada agente dispone de su propio sistema de información que no comparte de forma directa ni simple con ningún otro agente.

Para que la comunicación sea posible es necesario que los agentes compartan una misma forma de representar la información aunque ésta no tiene porqué coincidir con la representación interna que cada agente mantiene. También es necesario que los agentes tengan órganos o dispositivos funcionales propios que les permitan por un lado generar representaciones comunes por una parte de la información que mantienen y por otra interiorizar las representaciones elaboradas por otros agentes. La parte de información que se transmite se llama mensaje.

***El mensaje se representa mediante códigos y símbolos. Por eso llamaremos codificación al proceso de representar la información de una manera concreta. El mensaje, codificado de algún modo común, llega a los agentes a través del medio por el que se transmite***. Hablamos de medios para referirnos a los medios que soportan la transmisión de la información. El agente que genera una representación y la traspasa al medio se llama emisor. El agente que recibe la información a través de un medio se llama receptor. Cuando la información elaborada por un agente emisor llega a un agente receptor a través de algún medio diremos que entre ellos se ha establecido un canal de comunicación para abstraer y enfatizar el proceso de traspaso.

El proceso de elaboración y de interpretación de la información que llevan a cabo los agentes emisores y receptores respectivamente no son procesos pasivos, requieren de la atención de los agentes en ambos casos. Aunque depende de las situaciones, los agentes implicados en la comunicación pueden alternar sus papeles de emisores y receptores dando lugar a un diálogo.

<img src="https://2.bp.blogspot.com/-pvlqcD2BBTs/VaCdRyWTwSI/AAAAAAAAIEw/LHnWnrAGkIc/s1600/protocolo1.jpg" width="400"> 

### Roles cliente y servidor

La forma más clásica de comunicar dispositivos digitales es aplicando el modelo de **cliente-servidor***. `El servidor` es un dispositivo que *contiene información a compartir con otros agentes llamados clientes, pero no sabe cuándo los clientes necesitarán su información. Por eso *el servidor deberá estar escuchando a la espera de que algún cliente le haga una petición*, pidiendo qué parte de la información necesita. La petición es emitida por un cliente y codificada de tal forma que el servidor pueda interpretarla.
> *Cuando el mensaje llega al servidor, éste detecta que la petición es por él, la interpreta, genera la respuesta adecuada y la envía al cliente peticionario. Una vez resuelta la demanda, el servidor quedará de nuevo a la espera de nuevas peticiones.*

Actualmente muchas aplicaciones siguen **modelos mixtos** en los que los dispositivos *pueden hacer de clientes y servidores* a la vez, así permiten una ***mayor distribución de los datos y procesos*** y consiguen un abaratamiento del hardware. Sin embargo, los papeles de servidor y cliente no se han perdido sino que conviven en un mismo dispositivo, a menudo como procesos independientes ejecutados a la vez.

Tampoco debe confundirse el rol del servidor con el del receptor, ya que el servidor además de recibir las peticiones de los clientes debe procesarlas, obtener una respuesta y enviarla de vuelta al cliente.

<img src=https://i.ytimg.com/vi/Bzt6ZaykcBg/maxresdefault.jpg width="300">

### Caracteristicas de una Arquitectura cliente-servidor

En una aplicación que siga el modelo de la arquitectura cliente-servidor existe una aplicación servidor que ofrece sus servicios a través de la red ***(LAN, Internet, . . .)*** a múltiples hosts o clientes que pueden conectarse con ella.

- Protocolos como la ***Web, FTP, correo electrónico, mensajería instantánea y otros*** se apoyan en esta arquitectura para funcionar
- Es una ***arquitectura centralizada***, es el servidor ***(o conjunto de ellos)*** quién/es da/an el servicio de ***forma concurrente*** a *todos los clientes que se conectan a él*. Si éste cae, todo el servicio se detiene.
- El servidor tendrá que ***diseñarse como multihilo***, *puesto que tiene que dar servicio a muchos clientes al mismo tiempo*.

> Hoy en día no tendría sentido pensar en una aplicación servidor que sólo pudiera atender a un cliente al mismo tiempo.

- Si el ***cliente es también multihilo*** mucho mejor. Así, éste podrá estar atento a lo que el servidor le diga y a lo que el usuario quiera hacer en todo momento.
- Las conexiones realizadas necesitan conocer **IP** o ***url*** y **puerto** para establecerse, aunque el puerto a veces no se indique si se conoce de antemano por la aplicación cliente ***(well known ports)*** como ocurre, por ejemplo, cuando solicitamos una ***URL*** desde el navegador (no hace falta indicar el puerto de conexión).

[Sockets en Java](RC-1.md).

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>