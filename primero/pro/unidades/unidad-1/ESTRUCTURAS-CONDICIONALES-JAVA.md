# <img src=../../../../images/computer.png width="40"> Code & Learn (Programación: Estructuras Condicionales en Java (if, if else, if else if y switch)

En Java, el flujo de ejecución es lineal, ejecutándose línea por línea en el orden en que aparece. Sin embargo, es fundamental tener sentencias para controlar el flujo de ejecución. Las estructuras condicionales permiten ejecutar partes del código según se cumplan ciertas condiciones. En Java, las estructuras condicionales básicas son:

- `if`
- `if else`
- `if else if`
- `switch`

## If en Java

La estructura condicional más sencilla es `if`. Evalúa una condición, y si se cumple, ejecuta el código entre llaves `{}`. Si no se usan llaves, solo se ejecutará la primera instrucción tras el `if`.

```java
if (condicion) {
    // Código a ejecutar si la condición es verdadera
}
```

El uso de llaves es opcional cuando solo hay una línea de código, pero puede generar errores si se olvida añadirlas al añadir más líneas.

```java
if (temperatura > 25) {
    System.out.println("A la playa!!!");
}

if (haceSol) {
    System.out.println("No te olvides la sombrilla");
}

if (nevando || haceSol) {
    System.out.println("Qué bien");
}

if (nevando && (temperatura >= 20 && temperatura <= 30)) {
    System.out.println("No me lo creo");
}

if ((temperatura < 0 || temperatura > 30) && haceSol) {
    System.out.println("Mejor me quedo en casa");
}
```

## If Else en Java

El _if else_ permite ejecutar una instrucción alternativa cuando la condición no se cumple.

```java
if (condicion) {
    // Código si la condición es verdadera
} else {
    // Código si la condición es falsa
}
```

### Ejemplo de uso

```java
if (temperatura > 25) {
    System.out.println("A la playa!!!");
} else {
    System.out.println("Esperando al buen tiempo...");
}
```

## If Else If en Java

Permite evaluar __múltiples condiciones consecutivas__. El último bloque else se ejecuta si ninguna de las condiciones anteriores es verdadera.

```java
if (condicion1) {
    // Código si la condición1 es verdadera
} else if (condicion2) {
    // Código si la condición2 es verdadera
} else {
    // Código si ninguna de las condiciones anteriores es verdadera
}
```

### Ejemplo de uso

Veamos un ejemplo:

```java
if (temperatura > 25) {
    System.out.println("A la playa!!!");
} else if (temperatura > 15) {
    System.out.println("A la montaña!!!");
} else if (temperatura < 5 && nevando) {
    System.out.println("A esquiar!!!");
} else {
    System.out.println("A descansar... zZz");
}
```

## Operador Ternario en Java (IMP)

El operador ternario __?:__ es una forma compacta de escribir un __if-else__ que devuelve un valor u otro según una condición.

```code
resultado = (condicion) ? valor_si_se_cumple : valor_si_no_se_cumple;
```

Un Ejemplo de uso sería:

```java
String queHacer = (temperatura > 25) ? "A la playa!!!" : "Esperando al buen tiempo...";

double numero = (temperatura > 10) ? (Math.random() * 10) : 0;

Comida miComida = cansado ? pedirComida("china") : hacerComida();
```

## Switch en Java

El __switch__ es útil cuando se tiene un conjunto de valores conocidos. Evalúa una expresión y ejecuta el código asociado al caso que coincida.

```java
switch (variable) {
    case valor1:
        // Código si variable es igual a valor1
        break;
    case valor2:
        // Código si variable es igual a valor2
        break;
    default:
        // Código si ninguno de los valores coincide
        break;
}
```

__Un ejemplo de uso sería__:

```java
Integer dia = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

switch (dia) {
    case 1:
        System.out.println("Domingo");
        break;
    case 2:
        System.out.println("Lunes");
        break;
    case 3:
        System.out.println("Martes");
        break;
    case 4:
        System.out.println("Miércoles");
        break;
    case 5:
        System.out.println("Jueves");
        break;
    case 6:
        System.out.println("Viernes");
        break;
    case 7:
        System.out.println("Sábado");
        break;
    default:
        System.out.println("La semana solo tiene 7 días");
        break;
}
```

Con __String__ como condición:

```java
String tipoVehiculo = "coche";

switch (tipoVehiculo) {
    case "coche":
        System.out.println("Puedes pasar de 00:00 a 08:00");
        break;
    case "camion":
        System.out.println("Puedes pasar de 08:00 a 16:00");
        break;
    case "moto":
        System.out.println("Puedes pasar de 16:00 a 24:00");
        break;
    default:
        System.out.println("No se puede pasar con un " + tipoVehiculo);
        break;
}
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../LICENSE.md) para detalles.