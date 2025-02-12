<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: Ficheros XML)

## Trabajando con Archivos XML

### Introducción

`XML (eXtensible Markup Language)` es un formato de texto ampliamente utilizado para `almacenar y transportar datos`. A continuación, se presentan los conceptos sobre cómo trabajar con archivos XML.

## 🏗 **Elementos Principales de un Archivo XML**

### 1️⃣ **Declaración XML**

- Es la primera línea del archivo y define la versión y codificación del documento.
- No es obligatoria, pero se recomienda incluirla.

### 2️⃣ **Elemento Raíz**

- Es el **elemento principal** que contiene todos los demás elementos del XML.
- Un documento XML **debe tener un único elemento raíz**.

### 3️⃣ **Elementos o Nodos**

- Representan los datos y pueden contener otros elementos anidados.
- Se escriben con **etiquetas de apertura y cierre**.

### 4️⃣ **Atributos**

- Se usan para almacenar información adicional dentro de las etiquetas.
- Se definen en la etiqueta de apertura y **no pueden contener múltiples valores**.

### 5️⃣ **Comentarios**

- Permiten agregar notas dentro del XML sin afectar su estructura.
- Se escriben entre `<!--` y `-->`.

### 6️⃣ **Datos de Texto**

- Son los valores almacenados dentro de un elemento.
- XML diferencia entre **elementos** y **contenido de texto**.

### 7️⃣ **Espacios de Nombres (Namespaces)**

- Se usan para evitar conflictos de nombres cuando se combinan diferentes XML.
- Se declaran con `xmlns`.

### 8️⃣ **CData (Character Data)**

- Se usa para incluir datos que no deben ser interpretados como XML.
- Se encierra entre `<![CDATA[ ... ]]>`.

```xml
<!-- Yo soy un comentario -->
<!-- Tipo documento y codificacion -->
<?xml version="1.0" encoding="UTF-8"?>

<!-- Elemento raiz -->
<empresa>
    <!-- Elemento nodo de tipo empleado -->
    <empleado id="1">
              <!-- Propiedad del nodo (di) -->      
        <nombre>Juan</nombre>
               <!-- Nodo hijo de empleado --> 
        <edad>30</edad>
    </empleado>
    <empleado id="2">
        <nombre>María</nombre>
        <edad>25</edad>
    </empleado>
</empresa>
```

## 📌 ¿Qué es el DOM en XML?

El **DOM (Document Object Model)** es una representación estructurada en memoria de un documento XML en forma de **árbol jerárquico**. Permite a los programas acceder, modificar y manipular la estructura y contenido del XML de manera programática.

### 🏗 Características del DOM en XML

- **Estructura en árbol:** Cada nodo representa un elemento, atributo o texto dentro del XML.
- **Acceso y manipulación:** Se pueden recorrer, leer, modificar o eliminar nodos de XML.
- **Interfaz estandarizada:** Definida por el **W3C**, compatible con varios lenguajes ___(JavaScript, Python, Java, etc.)___.
- **Carga completa en memoria:** Todo el documento se almacena en memoria, lo que facilita la manipulación pero puede ser costoso en términos de recursos si el XML es muy grande.

### 🌳 Estructura del DOM en XML

Dado el siguiente XML:

```xml
<empresa>
    <empleado id="1">
        <nombre>Juan</nombre>
        <edad>30</edad>
    </empleado>
    <empleado id="2">
        <nombre>María</nombre>
        <edad>25</edad>
    </empleado>
</empresa>
´´´

El DOM lo representaría como un árbol de nodos:

```bash
Raíz: empresa
 ├── Nodo: empleado (id="1")
 │   ├── Nodo: nombre → "Juan"
 │   ├── Nodo: edad → "30"
 ├── Nodo: empleado (id="2")
     ├── Nodo: nombre → "María"
     ├── Nodo: edad → "25"
```

### Ejemplo de Uso

En javascript

```js
let xmlDoc = new DOMParser().parseFromString(xmlString, "text/xml");
let nombre = xmlDoc.getElementsByTagName("nombre")[0].textContent;
console.log(nombre);
// Output: Juan
```

### 📌 Aplicaciones del DOM en XML

- Manipulación de XML en navegadores.
- Interacción con APIs basadas en XML.
- Procesamiento de ___datos estructurados en servidores___.

### Conceptos Básicos

- `Lectura de XML`: Proceso de analizar un archivo XML y extraer información de sus nodos y atributos.

- `Creación y Escritura`: Construcción de un nuevo documento XML desde código y su almacenamiento en disco.

- `Modificación`: Actualización de valores dentro de un archivo XML existente.

## Fichero XML

```xml
<?xml version="1.0" encoding="UTF-8"?>
<empleados>
    <empleado>
        <id>1</id>
        <nombre>Juan Pérez</nombre>
        <fechaNacimiento>1993/05/12</fechaNacimiento>
        <puesto>Desarrollador</puesto>
    </empleado>
    <empleado>
        <id>2</id>
        <nombre>María López</nombre>
        <fechaNacimiento>1995/08/22</fechaNacimiento>
        <puesto>Diseñador</puesto>
    </empleado>
</empleados>
```

> Formato de la fecha: __aaaa/MM/dd__, _como se puede observar_.

## Código básico

### Lectura

```java
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;

public class LeerXML {
    public static void main(String[] args) throws Exception {
        File archivo = new File("empleados.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(archivo);
        
        NodeList lista = doc.getElementsByTagName("empleado");
        for (int i = 0; i < lista.getLength(); i++) {
            Node nodo = lista.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;
                System.out.println("ID: " + elemento.getElementsByTagName("id").item(0).getTextContent());
                System.out.println("Nombre: " + elemento.getElementsByTagName("nombre").item(0).getTextContent());
                System.out.println("Fecha de Nacimiento: " + elemento.getElementsByTagName("fechaNacimiento").item(0).getTextContent());
                System.out.println("Puesto: " + elemento.getElementsByTagName("puesto").item(0).getTextContent());
                System.out.println("---------------------");
            }
        }
    }
}
```

### Escritura

```java
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class CrearXML {
    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        
        Element root = doc.createElement("empleados");
        doc.appendChild(root);
        
        Element empleado = doc.createElement("empleado");
        root.appendChild(empleado);
        
        Element id = doc.createElement("id");
        id.appendChild(doc.createTextNode("1"));
        empleado.appendChild(id);
        
        Element nombre = doc.createElement("nombre");
        nombre.appendChild(doc.createTextNode("Juan Pérez"));
        empleado.appendChild(nombre);
        
        Element fechaNacimiento = doc.createElement("fechaNacimiento");
        fechaNacimiento.appendChild(doc.createTextNode("1993-05-12"));
        empleado.appendChild(fechaNacimiento);
        
        Element puesto = doc.createElement("puesto");
        puesto.appendChild(doc.createTextNode("Desarrollador"));
        empleado.appendChild(puesto);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("empleados.xml"));
        transformer.transform(source, result);
    }
}
```

### Modificar

```java
import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class ModificarXML {
    public static void main(String[] args) throws Exception {
        File archivo = new File("empleados.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(archivo);
        
        NodeList lista = doc.getElementsByTagName("empleado");
        if (lista.getLength() > 0) {
            Element empleado = (Element) lista.item(0);
            empleado.getElementsByTagName("nombre").item(0).setTextContent("Carlos Gómez");
        }
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("empleados_modificado.xml"));
        transformer.transform(source, result);
    }
}
```

## Explicación de los Elementos Clave en la Manipulación de Archivos XML en Java

A continuación, te explico los elementos clave de programación a tener en cuenta para manipular archivos XML en Java. El código se divide en tres secciones: __Lectura__, __Escritura__, y __Modificación__ de archivos XML.

### 1. __Lectura de XML__

### Importación de Bibliotecas

Para trabajar con XML en Java, es necesario importar las bibliotecas correspondientes:

```java
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
```

- `javax.xml.parsers.*`: Proporciona las clases necesarias para crear un `DocumentBuilder` y procesar documentos XML.
- `org.w3c.dom.*`: Contiene las clases que permiten representar y manipular el XML como un __DOM (Document Object Model)__.
- `java.io.*`: Se usa para manejar la entrada y salida de archivos, como el `File` para especificar la ruta del archivo XML.

### Creación del `DocumentBuilder`

```java
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();
```

Se utiliza el `DocumentBuilderFactory` para obtener una instancia de `DocumentBuilder`, que es la clase encargada de analizar el archivo XML y convertirlo en una estructura de objetos en memoria (DOM).

### Lectura del archivo XML

```java
File archivo = new File("empleados.xml");
Document doc = builder.parse(archivo);
```

Un archivo XML se puede leer utilizando el `DocumentBuilder`. Este convierte el archivo XML en un objeto `Document`, que es una representación del archivo XML en forma de un árbol de nodos.

### Acceso a los Elementos XML

```java
NodeList lista = doc.getElementsByTagName("empleado");
```

Una vez que el archivo XML ha sido cargado en un objeto `Document`, se pueden acceder a los nodos del documento mediante métodos como `getElementsByTagName()`, que permite obtener una lista de nodos que contienen una etiqueta específica.

### Iteración y Extracción de Datos

```java
for (int i = 0; i < lista.getLength(); i++) {
    Node nodo = lista.item(i);
    if (nodo.getNodeType() == Node.ELEMENT_NODE) {
        Element elemento = (Element) nodo;
    }
}
```

Los nodos obtenidos se pueden recorrer utilizando un bucle, y luego se puede acceder a la información de cada nodo (por ejemplo, los valores de los elementos) mediante métodos como `getTextContent()`.

## 2. __Escritura de XML__

### Creación del Documento XML

```java
Document doc = builder.newDocument();
```

Para escribir un archivo XML, se utiliza `DocumentBuilder` para crear un nuevo documento vacío en memoria. A partir de este documento, se pueden crear elementos y estructurarlos de acuerdo con la jerarquía deseada.

### Creación de Elementos XML

```java
Element root = doc.createElement("empleados");
doc.appendChild(root);
```

Los elementos XML se crean utilizando el método `createElement()`, que permite generar nuevos nodos en el árbol del documento. Estos nodos pueden ser agregados al documento mediante el método `appendChild()`.

### Adición de Texto a los Elementos

```java
id.appendChild(doc.createTextNode("1"));
```

Para agregar texto a un elemento XML, se usa `createTextNode()`. Este método genera un nodo de texto, que se agrega a un elemento específico mediante `appendChild()`.

### Guardar el XML en un Archivo

```java
TransformerFactory transformerFactory = TransformerFactory.newInstance();
Transformer transformer = transformerFactory.newTransformer();
DOMSource source = new DOMSource(doc);
StreamResult result = new StreamResult(new File("empleados.xml"));
transformer.transform(source, result);
```

Una vez que el documento XML ha sido creado y estructurado, se puede guardar en un archivo utilizando un `Transformer`. El `Transformer` convierte el objeto `Document` en un archivo XML, y se utiliza junto con `DOMSource` (para la entrada) y `StreamResult` (para la salida) para escribir el contenido en un archivo.

## 3. **Modificación de XML**

### Lectura del XML

El proceso de lectura para modificar un archivo XML es el mismo que para la lectura básica: se utiliza `DocumentBuilder` para cargar el archivo XML en un objeto `Document`.

### Modificación del Contenido

```java
empleado.getElementsByTagName("nombre").item(0).setTextContent("Carlos Gómez");
```

Una vez que el archivo XML ha sido cargado, los elementos dentro de él pueden ser modificados. Para esto, se localizan los nodos específicos que se desean cambiar y se utilizan métodos como `setTextContent()` para actualizar el contenido de los elementos.

### Guardar el XML Modificado

Después de realizar las modificaciones necesarias, el archivo XML se guarda utilizando el mismo proceso que para la escritura inicial, es decir, mediante el uso de un `Transformer` para escribir el documento modificado en un nuevo archivo.

### Consideraciones y Buenas Prácticas

- __Manejo de Excepciones__: Es recomendable manejar las excepciones adecuadamente utilizando bloques `try-catch` para evitar que el programa termine abruptamente si ocurre un error durante el procesamiento del XML.
  
- __Optimización__: Para documentos XML muy grandes, puede ser más eficiente utilizar un parser basado en eventos como el `SAXParser` en lugar del modelo DOM, ya que el DOM carga todo el archivo XML en memoria.

- __Validación XML__: En algunos casos, es necesario validar el archivo XML contra un esquema (XSD). Para esto, se puede usar la clase `SchemaFactory` para realizar la validación.

</div>