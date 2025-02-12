<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Programación: CSV, XML y JSON)

## 1. Características de cada formato

### CSV (Comma-Separated Values)
- Formato de texto plano donde los datos están separados por un delimitador (coma, punto y coma, tabulación, etc.).
- Cada línea representa un registro y cada valor dentro de la línea corresponde a un campo.
- No admite estructuras jerárquicas ni metadatos.
- Es eficiente en tamaño y rápido de procesar, pero limitado en cuanto a representación de datos complejos.
- Se usa comúnmente en hojas de cálculo, bases de datos y exportaciones de datos simples.

### XML (Extensible Markup Language)
- Utiliza una estructura basada en etiquetas anidadas que permiten organizar los datos de manera jerárquica.
- Admite atributos dentro de las etiquetas y puede incluir metadatos.
- Es más pesado en tamaño debido a la presencia de etiquetas repetitivas.
- Se usa en servicios web (SOAP), archivos de configuración y almacenamiento de datos estructurados.

### JSON (JavaScript Object Notation)
- Basado en una estructura de clave-valor utilizando objetos `{}` y listas `[]`.
- Es más ligero que XML y más fácil de procesar en lenguajes como JavaScript.
- Soporta estructuras jerárquicas y anidadas de manera eficiente.
- Es ampliamente utilizado en APIs REST, bases de datos NoSQL y comunicación entre sistemas.

## 2. Similitudes
- Son formatos de intercambio de datos utilizados en programación.
- Son legibles por humanos y máquinas.
- Se pueden procesar en múltiples lenguajes como Python, Java y JavaScript.
- Permiten estructurar datos para almacenamiento y transmisión.

## 3. Diferencias
| Característica        | CSV             | XML             | JSON           |
|----------------------|----------------|----------------|---------------|
| **Estructura**       | Tabular         | Jerárquica      | Jerárquica    |
| **Legibilidad**      | Alta en datos simples | Baja por su verbosidad | Alta y clara |
| **Metadatos**        | No              | Sí (atributos y etiquetas) | Sí (clave-valor) |
| **Tamaño**          | Pequeño         | Grande         | Medio         |
| **Facilidad de uso** | Muy fácil       | Compleja       | Fácil        |
| **Uso común**       | Bases de datos, hojas de cálculo | Configuración, servicios web (SOAP) | APIs REST, almacenamiento NoSQL |

## 4. Entornos de Uso
- **CSV**: Se usa principalmente en bases de datos, hojas de cálculo y exportación/importación de datos simples.
- **XML**: Se emplea en configuraciones de software, intercambio de datos entre sistemas y servicios web basados en SOAP.
- **JSON**: Es el formato estándar en desarrollo web, bases de datos NoSQL y comunicación API REST.

## 5. Ejemplos

### Ejemplo de CSV

```csv
Nombre,Edad,País
Juan,25,España
María,30,México
```

### Ejemplo de XML

```xml
<personas>
    <persona>
        <nombre>Juan</nombre>
        <edad>25</edad>
        <pais>España</pais>
    </persona>
    <persona>
        <nombre>María</nombre>
        <edad>30</edad>
        <pais>México</pais>
    </persona>
</personas>
```

```json
{
  "personas": [
    {
      "nombre": "Juan",
      "edad": 25,
      "pais": "España"
    },
    {
      "nombre": "María",
      "edad": 30,
      "pais": "México"
    }
  ]
}
```
