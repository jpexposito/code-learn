<div align="justify">

# <img src=.../../../../../images/coding-book.png width="40"> Code & Learn (Práctica 3: Login JWT + Interceptor + Rutas protegidas)

<div align="center">
  <img src=../images/practicas/practica-02.png
   width="350">
</div>

**Objetivo:** autenticar al usuario con JWT, enviar el token en cada petición y proteger páginas.

---

## 1) Crear servicio de autenticación

- POST `/api/auth/login` → devuelve `{ token: "..." }`
- Guardar token en `localStorage`

---

## 2) Crear Interceptor

El interceptor añade:

`Authorization: Bearer <TOKEN>`

a las peticiones hacia la API protegida.

---

## 3) Crear Guard

Proteger la ruta `/tareas` y `/tareas/nueva`:
- Si no hay token → redirigir a `/login`

---

## 4) Añadir página Login

- Formulario con usuario/contraseña
- Botón “Entrar”
- Si login OK → navegar a `/tareas`

---

##  Entregables

- Login funcional (token guardado)
- Interceptor funcionando (peticiones con Bearer)
- Guard funcionando (rutas protegidas)
- Botón Logout (borrar token)

---

</div>
