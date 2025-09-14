<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Acceso a Datos. Persistencia en ficheros)

En esta unidad se estudia la persistencia en ficheros: cómo preservar la información de un objeto de forma permanente y cómo recuperarla para poder reutilizarla.

---

## Objetivos

- Emplear clases para la gestión de archivos y directorios.  
- Acceder a ficheros de varias formas y evaluar sus ventajas e inconvenientes.  
- Realizar operaciones básicas de acceso **secuencial** y **aleatorio**.  
- Usar clases para almacenamiento y recuperación en archivos **XML**.  
- Convertir datos XML a otros formatos.  
- Administrar excepciones.  
- Serializar objetos Java a representaciones XML.

---

## Contenidos

### 1. Gestión de archivos

- Clases para operaciones de ficheros y directorios: creación, borrado, copia, movimiento.  
- Clase `File` (constructores, métodos como `exists()`, `delete()`, `mkdir()`, `length()`, etc.).  
- Ejemplo de creación de ficheros en Java.

    ```java
    File fichero = new File("TestFolder", "test.txt");
    System.out.println(fichero.getName());
    System.out.println(fichero.getAbsolutePath());
    ```

    ```php
    // Crear directorio
    mkdir("TestFolder");

    // Crear archivo
    $file = fopen("TestFolder/test.txt", "w");
    fwrite($file, "Hola mundo");
    fclose($file);

    // Comprobar existencia
    if (file_exists("TestFolder/test.txt")) {
        echo "El archivo existe\n";
    }
    ```

### 2. Formas de acceso a un archivo

- **Acceso secuencial**: lectura/escritura ordenada, no permite insertar en medio.  
  - Clases: `FileInputStream`, `FileOutputStream`, `FileReader`, `FileWriter`.  
  - Ejemplo:  

    ```java
    FileWriter fw = new FileWriter(file);
    fw.write("Ejemplo");
    fw.close();
    ```

    ```php
    // Escritura
    $file = fopen("ejemplo.txt", "w");
    fwrite($file, "Ejemplo");
    fclose($file);

    // Lectura
    $file = fopen("ejemplo.txt", "r");
    echo fread($file, filesize("ejemplo.txt"));
    fclose($file);
    ```

- **Acceso aleatorio**: acceso directo a cualquier posición.  
  - Clase: `RandomAccessFile`.  
  - Ejemplo:  

    ```java
    RandomAccessFile raf = new RandomAccessFile(file, "rw");
    raf.writeBytes("Hola");
    ```

    ```php
    $file = fopen("ejemplo.txt", "r+");
    fseek($file, 5);     // mover puntero horizontalmente
    fwrite($file, "Hola");
    fclose($file);
    ```

### 3. Flujos de datos en archivos

- Archivos **de texto**: `FileReader`, `FileWriter`, `BufferedReader`, `BufferedWriter`, `PrintWriter`.  
- Archivos **binarios**: `FileInputStream`, `FileOutputStream`, `DataInputStream`, `DataOutputStream`.  
- Serialización de objetos: `ObjectInputStream`, `ObjectOutputStream`, interfaz `Serializable`.

    ```java
    // Texto
    FileWriter fw = new FileWriter("datos.txt");
    fw.write("Hola mundo");
    fw.close();

    // Binario
    FileOutputStream fos = new FileOutputStream("imagen_copia.jpg");
    FileInputStream fis = new FileInputStream("imagen.jpg");
    int b;
    while((b = fis.read()) != -1) {
        fos.write(b);
    }
    fis.close();
    fos.close();
    ```

    ```php
    // Texto
    file_put_contents("datos.txt", "Hola mundo");
    echo file_get_contents("datos.txt");

    // Binario
    $data = file_get_contents("imagen.jpg");
    file_put_contents("imagen_copia.jpg", $data);
    ```

### 4. Trabajo con archivos XML

- Procesadores (parsers):  
  - **DOM**: carga el documento completo en memoria.  
  - **SAX**: lectura secuencial por eventos.  
- Librería **XStream/Jackson** para serialización de objetos Java ↔ XML.  
- Transformación XML con **XSLT** mediante `TransformerFactory`.
  
  ```java
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse("archivo.xml");
    NodeList lista = doc.getElementsByTagName("producto");
    for (int i = 0; i < lista.getLength(); i++) {
        System.out.println(lista.item(i).getTextContent());
    }
    ```

    ```php
    //ejemplo fichero xml
    $xml = simplexml_load_file("archivo.xml");
    foreach($xml->producto as $p) {
        echo $p->nombre . " - " . $p->precio . "\n";
    }
    ```

    ```xml
    $doc = new DOMDocument("1.0");
    $doc->formatOutput = true;

    $root = $doc->createElement("productos");
    $doc->appendChild($root);

    $producto = $doc->createElement("producto");
    $producto->appendChild($doc->createElement("nombre", "Laptop"));
    $producto->appendChild($doc->createElement("precio", "1200"));
    $root->appendChild($producto);

    $doc->save("nuevo.xml");

    ```

### 5. Excepciones: detección y tratamiento

- Manejo con bloques `try-catch-finally`.  
- Lanzamiento de excepciones con `throw`.  
- Propagación con `throws`.  
- Clases principales: `Exception`, `IOException`, `InputMismatchException`, `IllegalArgumentException`.  
- Métodos de `Throwable`: `getMessage()`, `printStackTrace()`, `toString()`.

    ```java
    try {
        FileReader fr = new FileReader("noexiste.txt");
    } catch (FileNotFoundException e) {
        System.out.println("Archivo no encontrado");
    }
    ```

    ```php
    try {
        $file = fopen("noexiste.txt", "r");
        if (!$file) {
            throw new Exception("Archivo no encontrado");
        }
    } catch (Exception $e) {
        echo "Error: " . $e->getMessage();
    }
    ```

---

## Resumen

- Los ficheros permiten persistir datos en disco de forma no volátil.  
- Java proporciona clases específicas en el paquete `java.io` y `java.xml`.  
- Se diferencian los accesos **secuenciales** y **aleatorios**.  
- Es posible trabajar tanto con **texto**, **binarios**, como con **XML**.  
- El manejo de **excepciones** es fundamental para un control seguro de errores.

<img src=images/resumen.png width="400">

</div>