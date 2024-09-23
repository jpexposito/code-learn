<div align="justify">

# <img src=.../../../../images/computer.png width="40"> Code & Learn (VirutalBox)

## VirtualBox

<div align="center">
<img src=images/virtualBox.png width="400">
</div>

VirtualBox es un software de virtualización de código abierto desarrollado por Oracle, que permite ejecutar múltiples sistemas operativos (SO) de manera simultánea en una única máquina física. Es una herramienta multiplataforma, disponible para sistemas operativos como __Windows, macOS, Linux y Solaris__, y permite ejecutar una amplia variedad de sistemas operativos como máquinas virtuales (VM), tales como __Windows, Linux, macOS, FreeBSD y más__.

Principales características de VirtualBox:

- __Multiplataforma__: VirtualBox puede instalarse en diferentes sistemas operativos anfitriones (host) y ejecutar casi cualquier sistema operativo como invitado (guest). Esto lo hace ideal para desarrolladores y profesionales de TI que necesitan probar software en diferentes plataformas.

- __Soporte para múltiples sistemas operativos__: VirtualBox soporta una gran cantidad de sistemas operativos, incluidos Windows, Linux (en varias distribuciones), macOS (con ciertas limitaciones), FreeBSD, y otros. Esto permite crear entornos de pruebas o entornos de desarrollo aislados para trabajar con distintos SO sin cambiar la configuración principal del equipo.

- __Disco duro virtual__: Las máquinas virtuales en VirtualBox utilizan archivos de disco duro virtual (con extensiones como .vdi, .vmdk, .vhd, etc.) que simulan un disco duro físico en tu sistema. Esto permite aislar las máquinas virtuales del hardware del host y facilita el manejo, exportación e importación de VMs.

- __Snapshots__: VirtualBox permite capturar el estado de una máquina virtual en un punto específico en el tiempo mediante "snapshots". Esto es útil para realizar pruebas, ya que puedes regresar a un estado anterior de la VM si algo falla.

- __Compatibilidad con periféricos y red__: Soporta conexiones de red configurables, como NAT, red puente, adaptadores internos y de solo anfitrión. Además, permite el uso de dispositivos USB, carpetas compartidas y soporte para video 3D, ofreciendo así una experiencia completa de emulación de hardware.

- __Portabilidad__: VirtualBox permite exportar máquinas virtuales a archivos OVA/OVF (Open Virtualization Format), lo que facilita el transporte y despliegue de VMs en otros sistemas.

- __Gestión de recursos__: VirtualBox ofrece una interfaz para gestionar los recursos asignados a cada máquina virtual, como CPU, RAM, almacenamiento, y red. Esto permite un control detallado sobre cómo las máquinas virtuales afectan el rendimiento del sistema host.

> __VirtualBox__ es una potente herramienta de virtualización que permite gestionar y ejecutar múltiples sistemas operativos en un solo equipo físico, facilitando tareas como pruebas de software, educación y configuración de entornos de desarrollo. Además, al ser de código abierto y gratuito, es una opción accesible para una amplia gama de usuarios.

## Importar máquinas virtuales

Asegúrate de tener un archivo de máquina virtual. Estos suelen tener las siguientes extensiones:

- __.ova o .ovf__ (archivos de exportación de VirtualBox).
- __.vdi, .vmdk, o .vhd__ (archivos de discos duros virtuales).

### Importar archivo OVA/OVF

Si el archivo es de tipo .ova o .ovf:

- En VirtualBox, ve al menú principal y selecciona Archivo → Importar servicio virtualizado....
- Selecciona el archivo .ova o .ovf que deseas importar y haz clic en Siguiente.

<div align="center">
<img src=images/virtualBox2.png width="400">
</div>

- Revisa la configuración de la máquina virtual que aparece en la pantalla de configuración. Ajusta si es necesario (memoria, CPU, etc.).
- Haz clic en Importar para iniciar el proceso. Esto puede tardar dependiendo del tamaño de la máquina virtual.

<div align="center">
<img src=images/virtualBox3.png width="200">
</div>

### Añadir un disco virtual (VDI, VMDK, VHD)

Si tienes un archivo de disco duro virtual ___(.vdi, .vmdk, .vhd)___, deberás crear manualmente una máquina virtual y agregar el disco existente:

- Haz clic en Nueva en el menú principal de VirtualBox.
- Asigna un nombre, elige el tipo y la versión del sistema operativo que vas a usar.
- Configura la cantidad de memoria RAM para la máquina virtual.
- En la opción Disco duro, selecciona Usar un archivo de disco duro existente.
Navega y selecciona tu archivo .vdi, .vmdk, o .vhd, y haz clic en Crear.
Ahora, la máquina virtual debería estar lista para ejecutarse.

## Exportar máquinas Virtuales

1. Seleccionar la máquina virtual.
En la ventana principal de VirtualBox, selecciona la máquina virtual que deseas exportar.

2. Iniciar el proceso de exportación. Con la máquina seleccionada, ve al menú superior y selecciona:

    ```code
    Archivo → Exportar servicio virtualizado...
    ```

3. Seleccionar la máquina a exportar.
Se abrirá una ventana donde podrás seleccionar la máquina que deseas exportar.
Verifica que la máquina seleccionada sea la correcta y haz clic en Siguiente.

4. Configurar el formato de exportación.
A continuación, se te pedirá que elijas un formato de exportación. Generalmente, se utiliza el formato ___Open Virtualization Format 1.0 (OVA)___.
Define la ubicación donde se guardará el archivo exportado _(usualmente con extensión .ova)_.
Haz clic en ___Siguiente___.

5. Configurar las opciones de exportación
En la pantalla siguiente, puedes ajustar las opciones de exportación, como la versión de VirtualBox o los parámetros de la máquina.
Normalmente, puedes dejar las opciones por defecto a menos que necesites algo específico.
Haz clic en Exportar para continuar.

6. Esperar la exportación
VirtualBox comenzará el proceso de exportación. Esto puede tardar varios minutos, dependiendo del tamaño de la máquina virtual.
Una barra de progreso te mostrará el estado de la exportación.

7. Archivo exportado
Una vez finalizada la exportación, el archivo .ova estará en la ubicación que seleccionaste. Este archivo contiene toda la configuración y los discos de la máquina virtual, listo para ser importado en otro sistema.

</div>