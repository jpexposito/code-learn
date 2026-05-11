<div align="justify">

# CentroPlus Connect — Proyecto Intermodular

<div align="center" width="400">
     <img src="images/centroplus.png">
</div>

## 1. Introducción

### Objetivo general

El objetivo de este proyecto es desarrollar una solución tecnológica completa e intermodular llamada:

```text
CentroPlus Connect
```

La aplicación estará orientada a la gestión de un centro académico y deportivo.

El proyecto integrará múltiples módulos profesionales del ciclo:

- Programación
- Bases de Datos
- Sistemas Informáticos
- Lenguajes de Marcas
- Inglés Técnico
- Entornos de Desarrollo
- FOL

---

## 2. Qué vamos a construir

El alumnado desarrollará un ecosistema completo formado por:

| Componente | Tecnología |
|---|---|
| Aplicación móvil | JavaFX |
| API REST | Java |
| Base de datos | SQLite / MariaDB |
| Web HTML | HTML + CSS + JavaScript |
| Despliegue | Docker |
| Repositorio remoto | GitHub |

---

## 3. Objetivo funcional

La plataforma permitirá:

- gestionar usuarios;
- consultar actividades;
- reservar plazas;
- cancelar reservas;
- registrar incidencias;
- visualizar datos desde móvil y web;
- desplegar servicios mediante Docker.

---

## 4. Arquitectura general

La arquitectura propuesta será:

```text
APP JavaFX
      ↓
 API REST
      ↓
 Servicios
      ↓
Repositorios
      ↓
 Base de datos

WEB HTML
      ↓
 API REST
```

---

## 5. Tecnologías principales

| Tecnología | Uso |
|---|---|
| Java 17 | Backend y app móvil |
| JavaFX | Aplicación móvil |
| Maven | Gestión del proyecto |
| SQLite/MariaDB | Persistencia |
| Docker | Despliegue |
| GitHub | Control de versiones |
| HTML/CSS/JS | Web |
| REST API | Comunicación |
| JSON | Intercambio de datos |

---

## 6. Estructura general del proyecto

```text
centroplus-connect/
│
├── backend-api/
│
├── mobile-app/
│
├── web-html/
│
├── database/
│
├── docs/
│
├── docker-compose.yml
│
└── README.md
```

---

## 7. Entidades principales

## Usuario

Representa a una persona registrada en el sistema.

Campos recomendados:

```text
id
nombre
dni
email
telefono
tipo_usuario
```

---

### Actividad

Representa una actividad académica o deportiva.

Campos recomendados:

```text
id
nombre
tipo_actividad
duracion
precio
plazas_maximas
plazas_ocupadas
```

---

### Reserva

Representa una reserva realizada por un usuario.

Campos recomendados:

```text
id
id_usuario
id_actividad
fecha
estado
```

---

### Incidencia

Representa una incidencia o comunicación.

Campos recomendados:

```text
id
id_usuario
asunto
descripcion
fecha
estado
```

---

## 8. Base de datos

### Esquema general

La base de datos contendrá las tablas:

```text
usuarios
actividades
reservas
incidencias
```

---

### Relaciones

```text
usuarios 1:N reservas
actividades 1:N reservas
usuarios 1:N incidencias
```

---

<div align="center" width="400">
     <img src="images/diagrama-bd.png">
</div>

---

## 9. Aplicación móvil JavaFX

## Objetivo

La aplicación móvil será la interfaz principal del usuario.

---

## Funcionalidades mínimas

- visualizar actividades;
- reservar plazas;
- cancelar reservas;
- consultar reservas;
- registrar incidencias.

---

### Componentes JavaFX recomendados

```text
BorderPane
VBox
HBox
ScrollPane
ListView
Button
Label
TextField
TextArea
Alert
```

---

## 10. API REST

## Objetivo

La API REST actuará como intermediario entre:

- la app móvil;
- la web HTML;
- la base de datos.

---

### Endpoints mínimos

| Método | Endpoint |
|---|---|
| GET | /usuarios |
| GET | /actividades |
| GET | /reservas |
| POST | /reservas |
| DELETE | /reservas/{id} |
| GET | /incidencias |
| POST | /incidencias |

---


## 11. Página web HTML

### Objetivo

La web permitirá consultar información desde navegador.

---

### Tecnologías

```text
HTML
CSS
JavaScript
Fetch API
```

---

### Funciones mínimas

- listar actividades;
- visualizar plazas;
- enviar incidencias;
- comprobar el funcionamiento de la API.

---

## 12. Docker

## Objetivo

Docker permitirá desplegar el sistema completo de forma reproducible.

---

### Servicios Docker

```text
api
db
web
```

---

### Contenedores

| Servicio | Tecnología |
|---|---|
| api | Java |
| db | MariaDB |
| web | Nginx |

---

## 13. GitHub

## Objetivo

Gestionar el proyecto profesionalmente. Para eso debes de crear un repositorio con la siguiente estructura.

---

### Ramas recomendadas

```text
main
develop
feature/backend
feature-mobile
feature-web
feature-database
feature-docker
feature-docs
```

---

### Organización

El alumnado trabajará con:

- issues;
- commits;
- tablero Kanban.

---

## 14. Testing

### Objetivo

Garantizar el correcto funcionamiento del sistema.

---

### Testing mínimo

| Tipo | Cantidad mínima |
|---|---:|
| Tests servicios | 10 |
| Tests repositorios | 5 |
| Tests API | 5 |
| Pruebas manuales | 5 |

---

### Herramientas

```text
JUnit 5
Maven Surefire
Postman/Swagger
curl
```

---

## 15. Relación con cada módulo

### PROGRAMACIÓN

#### Qué aporta

El módulo de Programación será el núcleo principal del proyecto.

---

#### Responsabilidades

##### Backend

- modelos;
- servicios;
- lógica de negocio;
- validaciones;
- API REST.

---

##### Aplicación móvil

- pantallas JavaFX;
- consumo REST;
- navegación;
- formularios;
- interacción con usuario.

---

#### Conceptos trabajados

```text
POO
MVC
REST
JSON
Servicios
Repositorios
Testing
Colecciones
Excepciones
```

---

### BASES DE DATOS

#### Qué aporta

Diseño y persistencia de datos.

---

#### Responsabilidades

- modelo relacional;
- claves primarias;
- claves foráneas;
- scripts SQL;
- relaciones;
- consultas;
- backups.

---

#### Conceptos trabajados

```text
SQL
DDL
DML
JOIN
GROUP BY
Normalización
Integridad referencial
```

---

### SISTEMAS INFORMÁTICOS

#### Qué aporta

Despliegue y administración técnica.

---

#### Responsabilidades

- Docker;
- Docker Compose;
- Nginx;
- puertos;
- logs;
- variables de entorno;
- despliegue de servicios.

---

#### Conceptos trabajados

```text
Contenedores
Redes
Linux
Servicios
Puertos
Despliegue
```

---

### LENGUAJES DE MARCAS

#### Qué aporta

Desarrollo de la parte web.

---

#### Responsabilidades

- HTML;
- CSS;
- JavaScript;
- Fetch API;
- estructura web;
- diseño responsive básico.

---

#### Conceptos trabajados

```text
HTML5
CSS3
JavaScript
DOM
Fetch
Responsive
```

---

### INGLÉS

#### Qué aporta

Documentación y comunicación técnica.

---

#### Responsabilidades

- README en inglés;
- documentación de API;
- mensajes técnicos;
- presentación final.

---

#### Conceptos trabajados

```text
Technical English
IT Vocabulary
Documentation
Communication
```

---

### ENTORNOS DE DESARROLLO

#### Qué aporta

Organización y calidad del desarrollo.

---

#### Responsabilidades

- Maven;
- GitHub;
- estructura modular;
- testing;
- control de versiones;
- integración continua conceptual.

---

#### Conceptos trabajados

```text
Maven
Git
GitHub
JUnit
Versionado
Estructura modular
```

---

### FOL

#### Qué aporta

Organización profesional y trabajo colaborativo.

---

#### Responsabilidades

- planificación;
- roles;
- Kanban;
- gestión del tiempo;
- memoria del proyecto;
- análisis de riesgos.

---

#### Conceptos trabajados

```text
Trabajo en equipo
Planificación
Roles
Kanban
Riesgos laborales
Documentación profesional
```

---

### 16. Distribución temporal

| Bloque | Horas |
|---|---:|
| Diseño inicial | 2 |
| GitHub y planificación | 2 |
| Base de datos | 3 |
| Backend Java | 5 |
| API REST | 4 |
| App móvil JavaFX | 4 |
| Web HTML | 2 |
| Testing | 3 |
| Docker y despliegue | 3 |
| Documentación y presentación | 2 |

---

## 17. Entregables

El alumnado deberá entregar:

```text
backend-api/
mobile-app/
web-html/
database/
docs/
docker-compose.yml
README.md
```

---

## 18. Documentación obligatoria

## README.md

Descripción general del proyecto.

---

### API.md

Documentación de endpoints REST.

---

### INSTALLATION.md

Pasos de instalación y despliegue.

---

### PROJECT_REPORT.md

Memoria final del proyecto.

---

## 19. Resultado final esperado

Al finalizar el proyecto el alumnado deberá ser capaz de:

- desarrollar una app móvil JavaFX;
- crear una API REST;
- trabajar con bases de datos reales;
- consumir servicios REST;
- desplegar con Docker;
- trabajar con GitHub;
- realizar testing;
- documentar profesionalmente;
- trabajar de forma colaborativa.

---


</div>
