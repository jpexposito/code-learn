<div align="justify">

# <img src=.../../../../images/coding-book.png width="40"> Code & Learn (Capítulo 11: Tests unitarios en TypeScript)

En este capítulo veremos los conceptos básicos de **tests unitarios** y cómo se suelen aplicar en proyectos TypeScript.

---

## ¿Qué es un test unitario?

Un **test unitario** verifica el comportamiento de una unidad pequeña de código:
- Una función.
- Un método de una clase.
- Un componente (en el contexto de frameworks como Angular o React).

La idea es comprobar que, dado un conjunto de **entradas**, la unidad produce las **salidas esperadas**.

Si vienes de **Java**, los tests unitarios en TypeScript se parecen a los tests con **JUnit**.  
Si vienes de **Python**, se parecen a los tests con el módulo `unittest` o con **pytest**.

En todos los casos, el patrón es parecido:

1. Preparar los datos de entrada.
2. Ejecutar la función que quieres probar.
3. Comprobar (con aserciones) que el resultado es el esperado.

---

## Librerías populares para test unitarios en TypeScript

En TypeScript, las librerías de test más utilizadas son:

- **Jest**
  - Muy popular en proyectos de Node.js, React y también en TypeScript.
  - Sintaxis sencilla: `test(...)`, `expect(...)`.
  - Incluye un *runner* de tests, aserciones y *mocks* en un solo paquete.

- **Vitest**
  - Inspirado en Jest, se integra muy bien con proyectos basados en **Vite**.
  - Muy rápido y con una API casi idéntica a Jest.

- **Mocha + Chai**
  - Mocha proporciona el *runner de tests*.
  - Chai proporciona las **aserciones** (`expect`, `should`, `assert`).
  - Fue una combinación muy popular antes del auge de Jest/Vitest.

- **Jasmine**
  - Usado tradicionalmente en proyectos Angular (Angular CLI lo ha utilizado internamente).

En proyectos modernos de TypeScript, lo más habitual es usar **Jest** o **Vitest**.

---

## Ejemplo conceptual con Jest

La estructura típica de un test con Jest en TypeScript sería algo así:

```ts
// archivo: suma.ts
export function sumar(a: number, b: number): number {
  return a + b;
}
```

```ts
// archivo: suma.test.ts
import { sumar } from "./suma";

test("sumar 1 + 2 debe dar 3", () => {
  const resultado = sumar(1, 2);
  expect(resultado).toBe(3);
});
```

En Java, esto se parecería a un test con JUnit:

```java
@Test
void sumar_debe_dar_3() {
    int resultado = sumar(1, 2);
    assertEquals(3, resultado);
}
```

En Python, sería similar a un test con `unittest` o `pytest`:

```python
def test_sumar():
    resultado = sumar(1, 2)
    assert resultado == 3
```

---

## Integración de TypeScript con Jest (visión general)

Para usar Jest con TypeScript, normalmente se hace algo como:

1. Instalar las dependencias necesarias (en un proyecto real):

   ```bash
   npm install --save-dev jest ts-jest @types/jest
   ```

2. Inicializar la configuración de `ts-jest`:

   ```bash
   npx ts-jest config:init
   ```

3. Añadir un script en `package.json`:

   ```json
   {
     "scripts": {
       "test": "jest"
     }
   }
   ```

4. Escribir tests en archivos `.test.ts` o `.spec.ts` y ejecutarlos con:

   ```bash
   npm test
   ```

El detalle exacto de configuración puede variar según el framework (Node puro, React, Angular, etc.), pero la idea general es siempre la misma:

- Escribir tests en TypeScript.
- Compilarlos (normalmente a través de `ts-jest` o integraciones similares).
- Ejecutarlos con el *runner* de tests (Jest, Vitest…).

---

## Buenas prácticas en tests unitarios

- **Nombrar bien los tests**: el nombre debería explicar qué comportamiento estás comprobando.
- **Un caso por test**: cada test debería validar un escenario concreto.
- **Evitar dependencias externas**:
  - No llamar a la base de datos ni a servicios externos en un test unitario.
  - Para eso se usan *mocks* o tests de integración.
- **Mantener los tests rápidos**:
  - Los tests unitarios deben poder ejecutarse con frecuencia (ideal en cada guardado o antes de cada commit).

Piensa en los tests unitarios como una **red de seguridad**: te permiten cambiar el código con más confianza, sabiendo que si rompes algo, un test debería avisarte.

### Ejemplo incluido en este proyecto

En esta carpeta encontrarás un ejemplo real de test unitario:

- Código a probar: `ejemplos/suma.ts`
- Test unitario: `ejemplos/suma.test.ts`

Puedes ejecutarlo con:

```bash
npm install
npm test
```

El comando `npm test` ejecutará Jest usando la configuración de `jest.config.cjs` y buscará los archivos que terminen en `.test.ts`.

---

[⬅ Capítulo 10: Integración con proyectos reales y buenas prácticas](./capitulo-10-integracion-y-buenas-practicas.md) · [Ir al índice](./README.md) · [Capítulo 12: Consumo de servicios rest en typeSript ➡](./capitulo-12-consumo-servicios-rest.md)

</div>