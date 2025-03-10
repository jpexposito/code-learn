# <img src=../../images/coding-book.png width="40"> Code & Learn (Base de Datos)

Este repositorio está diseñado para ser un recurso educativo sobre **bases de datos**. Su objetivo es proporcionar una serie de materiales, ejercicios y ejemplos prácticos que ayuden a estudiantes y profesionales a entender los conceptos fundamentales, así como a desarrollar habilidades prácticas en el manejo de bases de datos.

## Unidades

- [Unidad 1: Introducción a las Bases de Datos](unidad-1/)
- [Unidad 2:Modelo Entidad/Relación](unidad-2/)
- [Unidad 3:Modelo Relacional](unidad-3/)
- [Unidad 4: Normalización de BBDD](unidad-4/)
- [Unidad 5: Lenguage de Definición/Consula/Manipulación](unidad-5/)
  - [5.1: Lenguaje de Definición de Datos (DDL - Data Definition Language](unidad-5/sqlite/)
  - [5.2: Consultas sobre una tabla (DQL - Data Query Language](unidad-5/sqlite/)
  - [5.3: Consultas sobre varias tablas – Composición interna y cruzada](unidad-5/sqlite/)
  - [5.4: Consultas sobre varias tablas – Composición externa](unidad-5/sqlite/)
  - [5.5: Consultas resumen (Funciones Agregadas y Agrupaciones](unidad-5/sqlite/)
  - [5.6: Subconsultas (Subqueries)](unidad-5/sqlite/)
- [Unidad 6:X](unidad-x/)
- [Unidad 7:X](unidad-x/)
  
## Requisitos

- Conocimiento básico de programación.
- Software recomendado:
  - [Sqlite](https://www.sqlite.org/index.html)
  - [PostgreSQL](https://www.postgresql.org/)
  - [MySQL](https://www.mysql.com/)
  - [MongoDB](https://www.mongodb.com/)

## Instalación

### Sqlite3

SQLite3 es una base de datos ligera y de código abierto que se puede utilizar tanto en sistemas operativos Linux, Windows y macOS. A continuación, te guiaré en el proceso de instalación para cada uno de estos sistemas.

#### Linux

```bash
   sudo apt update
   sudo apt install sqlite3
   sudo apt install libsqlite3-dev
```

Verifica la versión instalada:

```bash
sqlite3 --version
```

#### Windows

En Windows, la instalación de SQLite3 no es tan directa como en Linux, ya que no se encuentra en un paquete de administrador. En su lugar, necesitas descargar el archivo binario desde el sitio web oficial.

- Visita la página de descargas de [SQLite](https://www.sqlite.org/download.html).
- En la sección Precompiled Binaries for Windows, descarga el archivo sqlite-tools correspondiente a tu arquitectura (32-bit o 64-bit). El archivo debería tener un nombre similar a sqlite-tools-win32-x86-*.zip.
- Extrae el contenido del archivo ZIP descargado en una carpeta de tu elección.
- Agrega el directorio donde extrajiste el archivo a las variables de entorno (PATH) para que puedas ejecutar sqlite3 desde cualquier lugar en la terminal:

  - Haz clic derecho en Este PC o Mi PC > Propiedades > Configuración avanzada del sistema > Variables de entorno.
  - En Variables de sistema, selecciona la variable Path y haz clic en Editar.
  - Añade el directorio donde extrajiste el archivo de SQLite.

Para verificar que SQLite se instaló correctamente, abre una terminal o Símbolo del sistema y ejecuta:

```bash
sqlite3 --version
```

### macOS

SQLite3 generalmente viene preinstalado en las versiones más recientes de macOS. Si ya está instalado, puedes verificarlo directamente ejecutando:

```bash
sqlite3 --version
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0).
