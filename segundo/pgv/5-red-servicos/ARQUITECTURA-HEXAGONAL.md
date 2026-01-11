<div align="justify">

# <img src=../../../images/coding-book.png width="40"> Code & Learn (Arquitectura Hexagonal)

<div align="center">
   <img src=images/arquitectura-hexagonal.png width="400">
</div>

Vamos a describir un **refactor guiado, paso a paso**, para transformar el proyecto  
👉 https://github.com/jpexposito/tasks-api  
desde una **arquitectura clásica en capas** a una **arquitectura hexagonal real (Ports & Adapters)**, preparando a futuro la integración **con JWT** sin romper el funcionamiento existente.

---

## 🎯 Nuestro objetivo es

- Separar claramente responsabilidades
- Aislar el dominio de frameworks
- Introducir casos de uso explícitos
- Integrar JWT como infraestructura transversal
- Preparar el proyecto para crecer (roles, tests, microservicios)

---

## 📦 Estado inicial del proyecto

Arquitectura actual (capas):

```
controller → service → repository
```

Paquete base real:

```
com.jpexposito.tasks
```

---

## 🧩 Arquitectura objetivo (hexagonal)

```
com.jpexposito.tasks
├─ domain
│  └─ model
│       └─ Task
├─ application/business
│  └─ TaskService (Casos de Uso)
│            ├─ CreateTask
│            ├─ GetTasks
│            ├─ GetTaskById
│            ├─ UpdateTask
│            └─ DeleteTask
├─ adapters
│  ├─ in
│  │  ├─ controller
│  │  │  └─ controllerTaskController  
│  │  └─ api
│  │     ├─ TaskRequest
│  │     └─ TaskResponse
│  ├─ mapper
│  └─ out
│     └─ persistence
│        ├─ TaskJpaEntity
│        └─ TaskRepositoryRepository
└─ infrastructure
   └─ ...
```

---

## 🧠 Principios clave

- El **dominio no depende de Spring**
- Los **casos de uso** se implementan en el servicio que implementa la lógica de negocio.
- Los **adaptadores** implementan puertos de entrada y salida a otros servicios ('bbdd/otros servicios externos').
- JWT vive en **infrastructure**, no en dominio, y lo veremos más adelante.
- Controllers solo traducen HTTP ↔ casos de uso

---

## ✅ Beneficios del refactor

- Código más mantenible
- Tests unitarios sencillos
- Dominio reutilizable
- Seguridad desacoplada
- Arquitectura profesional real

---

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>