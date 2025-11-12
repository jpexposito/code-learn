<div align="justify">

# <img src=../../../../images/coding-book.png width="40"> Code & Learn (Desarrollo y organización de clases)

En esta unidad se consolida la **creación de clases** y su **organización** en proyectos `Java`. Se aplican principios de **visibilidad**, **encapsulación** y **uso de librerías**, preparando el terreno para **herencia**, **modularidad** y buenas prácticas de diseño. Con Java 17 (LTS) incorporamos, además, **records** para modelos inmutables, **clases selladas (sealed)** para controlar jerarquías y **pattern matching para `instanceof`** para código más claro.

---

## ¿Qué vamos a tratar?

| Sesión | Contenidos/Actividades | Entregables |
|---:|---|---|
| 1 | Anatomía de una clase, **paquetes** y convenciones (nombres, estructura del repo). | Clase `Persona` básica. |
| 2 | **Encapsulación**: visibilidad (public, private, protected, package), getters/setters, `this`. | Refactor de `Persona`. |
| 3 | **Constructores**: sobrecarga, delegación con `this(...)`, `super(...)`. | Clase `CuentaBancaria`. |
| 4 | **Métodos** y estado: inmutabilidad parcial, `final`, contratos básicos. | Tests simples con `main`. |
| 5 | **Static**: campos y métodos estáticos, factorías y utilidades. | `Validador` estático. |
| 6 | **Herencia** y **sobrescritura** (`@Override`), composición vs herencia. | `Empleado` ← `Persona`. |
| 7 | **Java 17**: **records** (datos inmutables) y **clases selladas (sealed)** para jerarquías controladas. | Mini-ejercicios. |
| 8 | **Colecciones** (List, Set, Map), genéricos, `equals/hashCode/toString`, comparadores. | Ejercicios de colecciones. |
| 9 | **Paquetes y librerías**: creación de **JAR**, reutilización; estructura Maven/Gradle (visión). | Librería `com.docencia.util`. |

---

## 🧠 Concepto + 🎯 Ejemplo por sesión

> La idea es **aprender el concepto** y **aterrizarlo** con un ejemplo pequeñito que puedas ejecutar. Todos los fragmentos son compatibles con **Java 17**.

### 1) Anatomía de una clase, paquetes y convenciones

**Concepto (qué es):** una **clase** es el molde de tus objetos; un **paquete** (package) es la carpeta lógica donde viven esas clases. Las **convenciones** dan orden: nombres claros, dominio invertido (`com.docencia.curso`).  
**Ejemplo:** `Persona` mínima dentro del paquete correcto.

```java
package com.docencia.curso;

public class Persona {
  String nombre;
  int edad;

  public static void main(String[] args) {
    Persona p = new Persona();
    p.nombre = "Ada";
    p.edad = 36;
    System.out.println(p.nombre + " (" + p.edad + ")");
  }
}
```

---

### 2) Encapsulación: visibilidad, getters/setters, `this`

**Concepto:** la **encapsulación** protege el estado: campos `private`, acceso controlado con **getters/setters** y validación. `this` referencia a **esta** instancia.  
**Ejemplo:** refactor de `Persona` para que sea segura.

```java
package com.docencia.curso;

public class Persona {
  private String nombre;
  private int    edad;

  public Persona(String nombre, int edad) {
    setNombre(nombre);
    setEdad(edad);
  }

  public String getNombre() { return nombre; }
  public void setNombre(String nombre) {
    if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("nombre");
    this.nombre = nombre;
  }
  public int getEdad() { return edad; }
  public void setEdad(int edad) {
    if (edad < 0) {
      throw new IllegalArgumentException("edad");
    }
    this.edad = edad;
  }
}
```

---

### 3) Constructores: sobrecarga, `this(...)`, `super(...)`

**Concepto:** un **constructor** deja el objeto listo para usarse. Puedes **sobrecargar** (varias firmas) y **delegar** con `this(...)`. `super(...)` llama al constructor del padre.  
**Ejemplo:** `CuentaBancaria` con validación y dos constructores.

```java
package com.docencia.curso;

public class CuentaBancaria {
  private final String iban;
  private double saldo;

  public CuentaBancaria(String iban) {
    this(iban, 0.0);
  }     
  
  public CuentaBancaria(String iban, double saldo) {
    if (iban == null || iban.isBlank()) throw new IllegalArgumentException("iban");
    if (saldo < 0) {
      throw new IllegalArgumentException("saldo");
    }
    this.iban = iban; this.saldo = saldo;
  }
  // getters...
}
```

---

### 4) Métodos y estado: inmutabilidad parcial, `final`, contratos

**Concepto:** los **métodos** cambian (o no) el estado; `final` ayuda a fijar partes inmutables; los **contratos** (`toString`, `equals`, `hashCode`) mejoran depuración y colecciones.  
**Ejemplo:** operaciones seguras en `CuentaBancaria` y un `main` de prueba.

```java
public void ingresar(double cantidad) {
  if (cantidad <= 0) {
    throw new IllegalArgumentException("cantidad");
  }
  saldo += cantidad;
}
public void retirar(double cantidad) {
  if (cantidad <= 0 || cantidad > saldo){
    throw new IllegalArgumentException("cantidad");
  } 
  saldo -= cantidad;
}
@Override public String toString() { 
  return "Cuenta(" + iban + ", saldo=" + saldo + ")"; }

public static void main(String[] args) {
  var cuenta = new CuentaBancaria("ES00...", 100);
  cuenta.ingresar(50); cuenta.retirar(30);
  System.out.println(cuenta);
}
```

---

### 5) `static`: campos/métodos estáticos, factorías y utilidades

**Concepto:** `static` pertenece a la **clase**, no a la instancia: perfecto para **utilidades** y **factorías con nombre** (`of`, `from`).  
**Ejemplo:** `Validador` y factorías en `CuentaBancaria`.

```java
public final class Validador {
  private Validador() {}
  public static boolean esIban(String iban) {
    return iban != null && iban.matches("[A-Z]{2}\\d{2}.*");
  }
}

public static CuentaBancaria of(String iban, double saldo) {
  if (!Validador.esIban(iban)) {
    throw new IllegalArgumentException("IBAN inválido");
  }
  return new CuentaBancaria(iban, saldo);
}
```

---

### 6) Herencia y sobrescritura; composición vs herencia

**Concepto:** con **herencia** (`extends`) reutilizas y especializas; con **sobrescritura** (`@Override`) cambias el comportamiento. La **composición** (usar) suele ser preferible a heredar si no hay un claro “es-un”.  
**Ejemplo:** `Empleado` **es** una `Persona` con salario; `Direccion` se **compone** dentro de `Persona` (no hereda).

```java
class Direccion { String calle; String ciudad; /* ... */ }

class Persona {
  private String nombre; private Direccion dir;
  /* ... */
}

class Empleado extends Persona {
  private double salario;
  @Override public String toString() { /* añade info del salario */       return super.toString(); 
  }
}
```

---

### 7) Java 17: `record` (inmutable) y clases selladas (`sealed`)

**Concepto:** un **record** define datos **inmutables** con `equals/hashCode/toString` automáticos. Las **clases selladas** controlan **quién** puede heredar. Con **pattern matching** para `instanceof` el código se lee solo.  
**Ejemplo:** `Money` como `record` y jerarquía `Notificacion` sellada.

```java
public record Money(double amount, String currency) {
  public Money {
    if (amount < 0) {
      throw new IllegalArgumentException();
    } 
    if (currency == null || currency.length() != 3) {
      throw new IllegalArgumentException();
    } 
  }
}

public sealed interface Notificacion permits Email, Sms {
  String destino();
}
public final class Email implements Notificacion {
  /* ... */
  public String destino(){ 
    return "..."; 
  } 
}
public non-sealed class Sms implements Notificacion {
  /* permite más subclases */
  public String destino(){
    return "...";
  }
}

static void enviar(Notificacion notificacion) {
  if (notificacion instanceof Email email) {
    System.out.println("Email a " + email.destino());
  }
  else if (notificacion instanceof Sms sms){
    System.out.println("SMS a " + sms.destino());
  } 
}
```

---

### 8) Colecciones, genéricos y comparadores

**Concepto:** `List`, `Set`, `Map` + **genéricos** permiten trabajar con grupos de objetos de forma **segura**. Los **comparadores** ordenan; `equals/hashCode` evitan duplicados inesperados en `Set`/`Map`.  
**Ejemplo:** ordenar personas por apellido/nombre y evitar IBAN duplicado.

```java
List<Persona> personas = /* ... */;
personas.sort(
  java.util.Comparator
    .comparing((Persona p) -> p.getApellido())
    .thenComparing(Persona::getNombre)
    .thenComparing((Persona p) -> p.getEdad(), java.util.Comparator.reverseOrder())
);

// En Set, dos cuentas con mismo IBAN deben considerarse iguales
@Override public boolean equals(Object o) { /* usa iban */ }
@Override public int hashCode() { /* usa iban */ }
```

---

### 9) Paquetes y librerías: JAR y reutilización (Maven/Gradle)

**Concepto:** una **librería** empaqueta utilidades reutilizables en un **JAR** que otros proyectos consumen. Con Maven: `compile → test → package → install`.  
**Ejemplo:** módulo `com.docencia.util` con `Validador` y `Money`.

```bash
mvn clean install          # publica en el repo local (~/.m2)
```

```xml
<!-- Proyecto consumidor -->
<dependency>
  <groupId>com.docencia</groupId>
  <artifactId>util</artifactId>
  <version>1.0.0</version>
</dependency>
```

---

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../../LICENSE) para detalles.

</div>
