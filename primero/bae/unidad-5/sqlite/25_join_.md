<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (SQL - Comandos básicos: Uso del join)

<div align="center">
<img src="https://www.comunidadbaratz.com/wp-content/uploads/Sabes-cuales-son-los-libros-mas-vendidos-de-2017-a-traves-de-Internet-en-Espana.jpg" width="200px"/>
</div>

# Consultas sobre varias tablas. Composición interna y cruzada

_Las consultas multitabla nos permiten consultar información en más de una tabla. La única diferencia respecto a las consultas sencillas es que vamos a tener que_ ___especificar en la cláusula FROM___ _cuáles son las tablas que vamos a usar y cómo las vamos a relacionar entre sí_.

Para realizar este tipo de consultas podemos usar dos alternativas, _la sintaxis de SQL 1 (SQL-86)_, que consiste en realizar el ___producto cartesiano de las tablas___ y ___añadir un filtro para relacionar los datos___ que tienen en común, y la _sintaxis de SQL 2 (SQL-92 y SQL-2003)_ que ___incluye todas las cláusulas de tipo JOIN___.

## Consultas multitabla SQL 1

### Composiciones cruzadas (Producto cartesiano)

El producto cartesiano de dos conjuntos, es una operación que consiste en obtener otro conjunto cuyos elementos son todas las parejas que pueden formarse entre los dos conjuntos. Por ejemplo, tendríamos que coger el primer elemento del primer conjunto y formar una pareja con cada uno de los elementos del segundo conjunto. Una vez hecho esto, repetimos el mismo proceso para cada uno de los elementos del primer conjunto.

<div align="center">
<img src="img/producto-cartesiano.png" width="200px"/>
</div>

__Ejemplo__:




Hasta el momento hemos trabajado con una sola tabla, pero generalmente, se trabaja con más de una.

Para evitar la repetición de datos y ocupar menos espacio, se separa la información en varias tablas. Cada tabla almacena parte de la información que necesitamos registrar.

Por ejemplo, los datos de nuestra tabla "libros" podrían separarse en 2 tablas, una llamada "libros" y otra "editoriales" que guardará la información de las editoriales.
En nuestra tabla "libros" haremos referencia a la editorial colocando un código que la identifique.

Veamos:

```sql
drop table if exists libros;

create table libros(
	codigo integer primary key,
	titulo text,
	autor text, 
	precio real,
	codigoeditorial integer	
);

drop table if exists editoriales;

create table editoriales(
	codigo integer primary key,
	nombre text
);
```
De esta manera, evitamos almacenar tantas veces los nombres de las editoriales en la tabla "libros" y guardamos el nombre en la tabla "editoriales"; para indicar la editorial de cada libro agregamos un campo que hace referencia al código de la editorial en la tabla "libros" y en "editoriales".

Al recuperar los datos de los libros con la siguiente instrucción:

```sql
 select * from libros;
```

vemos que en el campo __codigoeditorial__ aparece el código, pero no sabemos el nombre de la editorial.
Para obtener los datos de cada libro, incluyendo el nombre de la editorial, necesitamos consultar ambas tablas, traer información de las dos.

Cuando obtenemos información de más de una tabla decimos que hacemos un "join" (combinación).

Veamos un ejemplo:
```sql
drop table if exists libros;

create table libros(
	codigo integer primary key,
	titulo text,
	autor text, 
	precio real,
	codigoeditorial integer	
);

drop table if exists editoriales;

create table editoriales(
	codigo integer primary key,
	nombre text
);

 insert into editoriales(nombre) values('Planeta');
 insert into editoriales(nombre) values('Emece');
 insert into editoriales(nombre) values('Siglo XXI');

 insert into libros (titulo, autor, codigoeditorial, precio)
  values('El aleph', 'Borges', 2, 34);
 insert into libros (titulo, autor, codigoeditorial, precio)
  values('Antología poética', 'Borges', 1, 39.50);
 insert into libros (titulo, autor, codigoeditorial, precio)
  values('Java en 10 minutos', 'Mario Molina', 1, 50.50);
 insert into libros (titulo, autor, codigoeditorial, precio)
  values('Alicia en el pais de las maravillas', 'Lewis Carroll', 2, 19.90);
 insert into libros (titulo, autor, codigoeditorial, precio)
  values('Martin Fierro', 'Jose Hernandez', 2, 25.90);
 insert into libros (titulo, autor, codigoeditorial, precio)
  values('Martin Fierro', 'Jose Hernandez', 3, 16.80);

select * 
  from libros
  join editoriales on libros.codigoeditorial=editoriales.codigo;
```

> __Resumiendo__: si distribuimos la información en varias tablas evitamos la redundancia de datos y ocupamos menos espacio físico en el disco. Un join es una operación que relaciona dos o más tablas para obtener un resultado que incluya datos (campos y registros) de ambas; las tablas participantes se combinan según los campos comunes a ambas tablas.

Hay tres tipos de combinaciones. En los siguientes conceptos explicamos cada una de ellas.

</div>