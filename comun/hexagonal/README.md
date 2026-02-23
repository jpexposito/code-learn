<div align="justify">

# Construyendo una aplicación modular en arquitectura hexagonal sobre wildfly.

## Arquitectura Hexagonal (Rest/Soap + Core + Adapters) — codeLearn

```markdown

                         ┌─────────────────────────────────────┐
                         │          codeLearn-war              │
                         │                                     │
                         │  - web.xml / CXFServlet             │
                         │  - configuración de la solución.    |
                         |  - WEB-INF\lib\librerias de la sol. |
                         │                                     │
                         │                                     │
                         └──────────────────┬──────────────────┘
                                            │ 
                                            │ 
                                            ▼
        ┌─────────────────────────────────────────────────────────────────────────┐
        │                         codeLearn-service (CORE)                        │
        │                    (Dominio + Aplicación + Puertos)                     │
        │                                                                         │
        │   ┌──────────────────────────────┐      ┌─────────────────────────────┐ │
        │   │          DOMINIO             │      │         APLICACIÓN          │ │
        │   │  Clases de negocio:          │      │  Casos de uso (Use Cases)   │ │
        │   │  - Alumno                    │      │  - MatricularAlumnoEnCurso  │ │
        │   │  - Curso                     │      │  - CrearTarea               │ │
        │   │  - Tarea                     │      │  - AsignarTareaAAlumno      │ │
        │   │  Reglas:                     │      │  - ListarCursosDeAlumno     │ │
        │   │  - Un alumno no dup. curso   │      │  - CompletarTarea           │ │
        │   │  - Tarea debe pertenecer     │      └──────────────┬──────────────┘ │
        │   │    a un curso                │                     │                │
        │   └──────────────────────────────┘                     │ usa            │
        │                                                        │                |
        │ Puertos (interfaces)                                   ▼                │
        │ - IN: AlumnoRestApi/SoapApi, CursoRestApi/SoapApi, TareaRestApi/SoapApi │
        │ - OUT: AlumnoRepositoryPort, CursoRepositoryPort, TareaRepositoryPort   │
        │          AuditPort, NotifierPort (opcional)                             │
        └─────────────────────────────────────┬───────────────────────────────────┘
                                              │ Utiliza otros elementos 
                                              │ como
                                              ▼
                            ┌─────────────────────────────────────┐
                            │          codeLearn-adapter          │
                            │Tendremos dos varios tipos de apater:│
                            │                                     │
                            │  Persistencia:                      │
                            │  - JPA/Hibernate repos              │
                            │    (implements *RepositoryPort)     │
                            │                                     │
                            │  Integraciones (opcional):          │
                            │  - Cliente externo (correo, etc.)   │
                            |                                     |
                            |  Otros soliciones:                  |  
                            |  - Clientes Soap/Clientes Rest      |  
                            │                                     │
                            │  Auditoría (opcional):              │
                            │  - implements AuditPort             │
                            └─────────────────────────────────────┘


        ┌─────────────────────────────┐
        │        codeLearn-parent     │
        │ (Parent Maven: versiones,   │
        │  plugins, dependency mgmt)  │
        └─────────────────────────────┘
```

A continuación se muestra la estructura de módulos Maven para implementar la solución descrita (WildFly + CXF + Spring) siguiendo Arquitectura Hexagonal.

```
- codeLearn-parent (pom)
─ codeLearn-service (jar)          <- CORE (dominio + aplicación + in (rest/soap))
─ codeLearn-adapter (pom)          <- Agrupador de adaptadores
  ├─ codeLearn-adapter-persistence (jar) <- JPA/Hibernate (Repository Relacional/No Relacional)
  ├─ codeLearn-adapter-audit (jar)   <- Auditoría (AuditPort) (opcional)
  ├─ codeLearn-adapter-notifier-jms (jar) <- Notificaciones (NotifierPort) (integración con un servicio de notificación jms)
  ├─ codeLearn-adapter-client-rest-service-1 (jar)   <- Clientes REST externos
  └─ codeLearn-adapter-client-soap-service-2 (jar)   <- Clientes SOAP externos
─ codeLearn-war (war)              <- Inbound + bootstrap (web.xml, CXF, wiring)
```

---

## codeLearn-parent (packaging: pom)

**Responsabilidad:** Gestión común del proyecto.

- dependencyManagement (versiones: Spring 6, CXF 4, Jakarta, etc.)
- plugins Maven (compiler JDK 17, surefire, jacoco, etc.)
- propiedades compartidas, versiones de librerias

---

## codeLearn-service (packaging: jar) — CORE

**Responsabilidad:** Núcleo hexagonal puro.

Incluye:

### Dominio
- Entidades: Alumno, Curso, Tarea
- Reglas de negocio

### Aplicación
- Casos de uso:
  - MatricularAlumnoEnCurso
  - CrearTarea
  - AsignarTareaAAlumno
  - ListarCursosDeAlumno
  - CompletarTarea

## codeLearn-adapter-persistence (jar)

**Responsabilidad:** Persistencia.

- Implementa RepositoryPort
- Entidades JPA
- Repositorios Hibernate/JPA
- Mapeo Entity ⇄ Dominio

> Se relaciona con `codeLearn-service` a través de `mappers -> mapStruct` transformando elementos del `domino - entidades`.

---

## codeLearn-adapter-audit (jar) — opcional

**Responsabilidad:** Auditoría.

- Implementa AuditInterface
- Persistencia o envío de eventos de auditoría

---

## codeLearn-adapter-notifier (jar) — opcional

**Responsabilidad:** Notificaciones.

- Implementa NotificaInterface
- Email, mensajería, etc.

---

## codeLearn-adapter-client-rest (jar) — opcional

**Responsabilidad:** Integración REST externa.

- Clientes HTTP
- DTOs externos
- Mappers
> Realiza la comunicación con `servicios rest de terceros`

---

## codeLearn-adapter-client-soap (jar) — opcional

**Responsabilidad:** Integración SOAP externa.

- Clientes SOAP (CXF)
- Stubs
- Mappers
> Realiza la comunicación con `servicios soap de terceros`

---

## codeLearn-service-rest (packaging: jar) 

Exponer `APIs REST`
- Seguridad a nivel de entrada

Depende de:
- codeLearn-service


## codeLearn-service-soap (packaging: jar) 

Exponer `APIs HTTP/SOAP`
- Seguridad a nivel de entrada

Depende de:
- codeLearn-service

## codeLearn-war (packaging: war) 

**Responsabilidad:** Publicación del servicio y ensamblaje de componentes.

Incluye:

- web.xml
- CXFServlet
- Expone el conjunto de los `endpoints` `REST` y/o `SOAP`
- Configuración Spring 
- WEB-INF/lib con dependencias

Funciones principales:

- Exponer APIs HTTP/SOAP
- Invocar los puertos IN del core
- Conectar implementaciones de puertos OUT

Depende de:
- codeLearn-service
- codeLearn-adapter-*

---

## Dependencias entre módulos

Regla fundamental de Arquitectura Hexagonal:

- codeLearn-service-rest → invoca de codeLearn-service
- codeLearn-service-soap → invoca de codeLearn-service
- codeLearn-service → negocio y utiliza a codeLearn-adapter-*
- codeLearn-adapter-* → da soluciones a codeLearn-service
- codeLearn-war → integra y unifica todo

---

## Resumen

<img src=images/resumen-arquitectura.png width=400/>


</div>