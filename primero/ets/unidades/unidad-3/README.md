# <img src=../../../../images/computer.png width="40"> Code & Learn (Entornos de Desarroll: Elaboración de diagramas de comportamiento)

## Tipos de Diagramas UML
1. **Diagramas Estructurales**
   - Representan la estructura estática del sistema.
   - Ejemplos: Diagrama de Clases, Diagrama de Componentes, Diagrama de Despliegue.

2. **Diagramas de Comportamiento**
   - Representan el comportamiento dinámico del sistema.
   - Ejemplos: Diagrama de Casos de Uso, Diagrama de Secuencia, Diagrama de Colaboración, Diagrama de Actividad, Diagrama de Estado.

---

## 1. Diagrama de Casos de Uso

### Campo de Aplicación
- Se utiliza para capturar y definir los requisitos funcionales de un sistema.
- Ideal para identificar cómo los usuarios (actores) interactúan con el sistema y los objetivos que desean alcanzar.

### Componentes
- **Actores**: 
  - Entidades externas que interactúan con el sistema (usuarios, otros sistemas).
  - Pueden ser humanos, sistemas externos, o dispositivos.

- **Escenario**: 
  - Una descripción específica de cómo un actor interactúa con el sistema para lograr un objetivo.
  - Puede incluir pasos y condiciones.

- **Relación de Comunicación**:
  - Conexiones entre actores y casos de uso que muestran cómo interactúan.
  - Puede ser de tipo `include`, `extend` o relaciones generales.

### Ejemplo

```plaintext
[Actor] -- (Caso de Uso)
```

## 2. Diagrama de Secuencia

### Campo de Aplicación
- Se utiliza para modelar cómo los objetos interactúan en un flujo temporal a lo largo de un escenario específico.
- Ideal para representar la lógica de un proceso o una funcionalidad dentro del sistema.

### Componentes

- **Línea de Vida de un Objeto**:
  - Representa la existencia de un objeto en el tiempo.
  - Se visualiza como una línea vertical que se extiende hacia abajo.

- **Activación**:
  - Representa el período durante el cual un objeto está activo (ejecutando una acción).
  - Se representa como un rectángulo vertical sobre la línea de vida.

- **Envío de Mensajes**:
  - Indica la comunicación entre objetos mediante flechas.
  - Se puede especificar el tipo de mensaje (síncrono, asíncrono).

### Ejemplo

```texplain
Objeto1 [1] -- Mensaje --> Objeto2 [2]
```

## 3. Diagrama de Colaboración

### Campo de Aplicación
- Se utiliza para mostrar cómo los objetos colaboran en un escenario específico, enfatizando las relaciones entre ellos.
- Ideal para ilustrar la interacción en un contexto más amplio que el proporcionado por un diagrama de secuencia.

### Componentes

- **Objetos**:
  - Representan instancias de clases que participan en la interacción.
  - Se representan como rectángulos etiquetados.

- **Mensajes**:
  - Indican la comunicación entre objetos.
  - Se etiquetan con el nombre del mensaje y el número de secuencia (opcional) para indicar el orden de la interacción.

### Ejemplo
```plaintext
Objeto1 [1] -- Mensaje --> Objeto2 [2]
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../LICENSE.md) para detalles
