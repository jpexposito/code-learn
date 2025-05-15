<div align="justify">

# 🤝 Similitudes y diferencias entre programar en Bases de Datos y en JavaScript

Programar en una base de datos como MySQL no es lo mismo que desarrollar en un lenguaje como JavaScript, pero hay varios puntos en común que pueden ayudarte a entender mejor cómo funcionan ambos.

---

## 🧠 Similitudes

| Concepto                    | MySQL (BBDD)                                              | JavaScript (JS)                                          |
|----------------------------|-----------------------------------------------------------|----------------------------------------------------------|
| Variables                  | `DECLARE total INT;`                                       | `let total = 0;`                                         |
| Condicionales              | `IF condicion THEN ... END IF;`                            | `if (condicion) { ... }`                                |
| Bucles                     | `WHILE cond DO ... END WHILE;`                            | `while (cond) { ... }`                                  |
| Funciones / Procedimientos | `CREATE FUNCTION / PROCEDURE`                             | `function nombre() { ... }`                             |
| Eventos                    | Triggers automáticos (`AFTER INSERT`)                     | Listeners (`onclick`, `onload`, etc.)                   |
| Reutilización              | Procedimientos y funciones                                | Funciones y módulos                                     |

---

## 🧾 Ejemplo paralelo: calcular total

### 🧮 En MySQL

```sql
CREATE FUNCTION calcular_total(precio DECIMAL(10,2), cantidad INT)
RETURNS DECIMAL(10,2)
BEGIN
    RETURN precio * cantidad;
END;
```

### 💻 En JavaScript

```javascript
function calcularTotal(precio, cantidad) {
    return precio * cantidad;
}
```

---

## 📌 Diferencias clave

| Aspecto               | MySQL (BBDD)                                      | JavaScript                                               |
|-----------------------|--------------------------------------------------|----------------------------------------------------------|
| Enfoque               | Declarativo: pide el resultado                   | Imperativo: define los pasos para lograr el resultado    |
| Ubicación             | Ejecuta en el servidor de base de datos         | Ejecuta en el navegador o servidor (Node.js)             |
| Propósito principal   | Almacenar, consultar y transformar datos         | Interactividad, lógica de negocio, interfaces de usuario |
| Persistencia          | Opera sobre datos permanentes                    | Opera sobre datos temporales en ejecución                |
| Tiempo de ejecución   | Cuando hay eventos SQL (consultas, triggers)     | Cuando se dispara en el frontend o backend               |

---

## 🧠 Conclusión

- Ambos lenguajes usan **estructuras similares**: variables, funciones, condicionales.
- MySQL se enfoca en **datos y persistencia**, mientras que JavaScript se enfoca en **comportamiento y experiencia de usuario**.
- Comprender cómo se organiza y reutiliza el código en ambos entornos permite construir sistemas más sólidos y conectados entre frontend, backend y base de datos.

</div>