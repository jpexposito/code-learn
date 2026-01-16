<div align="justify">

# <img src="../../images/coding-book.png" width="40"> Test & Mockito (`en 1 hora`)

<div align="center">
  <img src=images/portada-1.png width="350">
</div>

Vamos describir que es y cómo escribir **tests unitarios** con **JUnit 5** y **Mockito** en proyectos **Spring 6** `(Spring Framework 6)`, cómo configurar **dependencias Maven**, cómo generar **cobertura con JaCoCo** y cómo integrarlo en **Integración Continua** (ej. GitHub Actions).

---

## <img src="images/junit-logo.svg" width="100"> 

**JUnit 5** (a.k.a. *JUnit Jupiter*) es el framework estándar de Java para:
- Definir y ejecutar tests (`@Test`, `@BeforeEach`, etc.)
- Agrupar y organizar tests (`@Nested`, `@Tag`)
- Comprobar resultados con aserciones (`assertEquals`, `assertThrows`, etc.)

### Dependencias clave (JUnit 5)
- `org.junit.jupiter:junit-jupiter` → API + engine

### Anotaciones útiles

- `@Test` → test unitario
- `@BeforeEach` / `@AfterEach` → se ejecutan antes/después de cada test
- `@BeforeAll` / `@AfterAll` → una vez por clase (métodos `static`)
- `@DisplayName` → nombre legible del test
- `@Nested` → agrupar escenarios
- `@ParameterizedTest` → tests con datos (con `@ValueSource`, `@CsvSource`, etc.)
- `@ExtendWith(...)` → extensiones (por ejemplo Mockito)

### Asserts comunes

- `assertEquals(expected, actual)`
- `assertTrue(...)`, `assertFalse(...)`
- `assertThrows(Exception.class, () -> ...)`
- `assertAll(...)` (agrupa asserts)

---

## <img src="images/mokito-logo.png" width="120"> 

**Mockito** es una librería para crear **mocks** (dobles de prueba) y así:
- `Aislar` la unidad bajo test (controlador/servicio/clase) de sus `dependencias reales`
- `Simular respuestas` de dependencias (stubbing con `when(...).thenReturn(...)`)
- Verificar interacciones (con `verify(...)`)
- Probar escenarios de error `sin depender de BBDD, HTTP, etc`.

**¿Cuándo usar Mockito?**
- Cuando tu clase depende de servicios externos o componentes complejos (repositorios, gateways, mappers, etc.)
- Para tests unitarios rápidos, deterministas y centrados en una única unidad

**¿Cuándo NO usar Mockito?**
- Para tests de integración reales (ahí conviene levantar Spring context o usar Testcontainers, etc.)
- Para testear “Mockito contra Mockito”: si no hay lógica, el test aporta poco

### Crear mocks

- `mock(MiClase.class)` crea un mock “vacío” (sin comportamiento).
- `when(...).thenReturn(...)` define qué devolverá.
- `when(...).thenThrow(...)` define una excepción.
- `verify(mock).metodo(...)` verifica que se llamó.

---

## Dependencias Maven (Spring 6 + JUnit 5 + Mockito)

### Opción A: Proyecto Spring “puro” (Spring Framework 6, sin Spring Boot)

En función de las características de tu proyecto debes de ajustar las dependencias de tu `pom.xml`:

```xml
<properties>
  <maven.compiler.release>17</maven.compiler.release>
  <junit.jupiter.version>5.10.2</junit.jupiter.version>
  <mockito.version>5.11.0</mockito.version>
  <jacoco.version>0.8.12</jacoco.version>
  <surefire-plugin.test>3.2.5</surefire-plugin.test>
</properties>

<dependencies>

  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>${junit.jupiter.version}</version>
    <scope>test</scope>
  </dependency>

  <dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>${mockito.version}</version>
    <scope>test</scope>
  </dependency>

  <dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>${mockito.version}</version>
    <scope>test</scope>
  </dependency>
</dependencies>

<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>${surefire-plugin.test}</version>
      <configuration>
        <useModulePath>false</useModulePath>
      </configuration>
    </plugin>

    <!-- JaCoCo: cobertura -->
    <plugin>
      <groupId>org.jacoco</groupId>
      <artifactId>jacoco-maven-plugin</artifactId>
      <version>${jacoco.version}</version>
      <executions>

        <execution>
          <id>prepare-agent</id>
          <goals>
            <goal>prepare-agent</goal>
          </goals>
        </execution>

        <execution>
          <id>report</id>
          <phase>test</phase>
          <goals>
            <goal>report</goal>
          </goals>
        </execution>

        <!-- (Opcional) Falla el build si no se cumple un mínimo -->
        <execution>
          <id>check</id>
          <goals>
            <goal>check</goal>
          </goals>
          <configuration>
            <rules>
              <rule>
                <element>BUNDLE</element>
                <limits>
                  <limit>
                    <counter>INSTRUCTION</counter>
                    <value>COVEREDRATIO</value>
                    <minimum>0.80</minimum>
                  </limit>
                </limits>
              </rule>
            </rules>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

### Opción B: Si usas Spring Boot 3.x (ya incluye JUnit/Mockito)

Con Boot suele bastar con:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <scope>test</scope>
</dependency>
```

Spring-Boot trae JUnit 5 y Mockito (entre otras). Sólo debes de añadir JaCoCo sigue siendo recomendable.

---

## 4) Ejemplo de test con Mockito 

Test de **TaskController** que:
- Mockea `ITaskService` y `TaskMapper`
- Stubbea respuestas
- Ejecuta `controller.getAll()`
- Verifica tamaño/valores
- Verifica interacciones

```java
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class TaskControllerTest {

  @Test
  void getAllResponsesTest() {
    ITaskService serviceMock = mock(ITaskService.class);
    TaskMapper mapperMock = mock(TaskMapper.class);
    TaskController controller = new TaskController(serviceMock, mapperMock);

    Task task = new Task(1L, "nombre", "descripcion", false);

    when(serviceMock.getAll()).thenReturn(List.of(task));
    when(mapperMock.toResponse(task)).thenReturn(new TaskResponse(1L, "nombre", "descripcion", false));

    List<TaskResponse> res = controller.getAll();

    assertEquals(1, res.size());
    assertEquals(1L, res.get(0).getId());

    verify(service).getAll();
    verify(mapper).toResponse(task);
  }
}
```

### ¿Qué hace Mockito aquí?

Mockito te permite crear **mocks** (dobles de prueba) para **aislar** la unidad bajo test (el `TaskController`) de sus dependencias:

- `ITaskService` (origen de datos)
- `TaskMapper` (transforma `Task` → `TaskResponse`)

Así, el test valida **solo la lógica del controller** sin depender de:
- BBDD
- repositorios
- servicios reales
- lógica real del mapper


---

## 5) Variante “más limpia” con `@ExtendWith(MockitoExtension.class)` (recomendado)

En vez de crear mocks a mano, puedes usar anotaciones:

```java
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

  @Mock ITaskService serviceMock;
  @Mock TaskMapper mapperMock;

  @InjectMocks TaskController controller;

  @Test
  void getAllResponsesTest() {
    Task task = new Task(1L, "nombre", "descripcion", false);

    when(service.getAll()).thenReturn(List.of(task));
    when(mapper.toResponse(task)).thenReturn(new TaskResponse(1L, "nombre", "descripcion", false));

    List<TaskResponse> res = controller.getAll();

    assertEquals(1, res.size());
    assertEquals(1L, res.get(0).getId());
    verify(service).getAll();
    verify(mapper).toResponse(task);
  }
}
```

---

## 6) Ejemplos típicos útiles con Mockito

### 6.1 Simular excepciones

```java
when(service.getAll()).thenThrow(new RuntimeException("boom"));
assertThrows(RuntimeException.class, () -> controller.getAll());
```

### 6.2 Verificar que NO se llama algo

```java
verify(mapper, never()).toResponse(any());
```

### 6.3 Verificar número de llamadas

```java
verify(service, times(1)).getAll();
```

### 6.4 Capturar argumentos (ArgumentCaptor)

```java
var captor = org.mockito.ArgumentCaptor.forClass(Task.class);

verify(mapper).toResponse(captor.capture());
Task captured = captor.getValue();
assertEquals(1L, captured.getId());
```

---

## <img src="images/jacoco-logo.png" width="200"> 


### Ejecutar tests + generar reporte

```bash
mvn clean test
```

El reporte HTML se genera típicamente en:

- `target/site/jacoco/index.html`

---

<img src="images/jacoco-report.png" width="420"> 

---

## 8) Integración Continua (CI) con GitHub Actions (ejemplo)

La combinación **JUnit + Mockito + JaCoCo + CI** permite automatizar la validación del código en cada cambio:

```text
Push / Pull Request
    ↓
    CI
    ↓
mvn clean test
    ↓
JUnit  → ejecuta tests
Mockito → valida interacciones y lógica
JaCoCo → mide cobertura
        ↓
     Resultado
   ✅ OK   ❌ FAIL
```

El objetivo es **detectar errores lo antes posible**, antes de que el código llegue a producción.


Crea `.github/workflows/ci.yml`:

```yaml
name: CI

on:
  push:
  pull_request:

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Set up JDK
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: "17"
          cache: maven

      - name: Run tests (with JaCoCo)
        run: mvn -B test

      - name: Upload JaCoCo report
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: jacoco-report
          path: target/site/jacoco
```

---

## 9) Buenas prácticas (resumen)

- **Unit tests**: rápidos, sin Spring context, usando Mockito para aislar dependencias.
- **Integration tests**: con Spring context solo cuando aporta valor.
- Verifica:
  - Resultado (asserts)
  - Interacciones relevantes (verify)
- Evita tests frágiles: no verifiques detalles irrelevantes.

---

### Flujo completo en CI

1. CI clona el repositorio
2. Instala JDK (ej. Java 17)
3. Ejecuta:
   ```bash
   mvn clean test
   ```
4. Durante ese comando:
   - JUnit ejecuta los tests
   - Mockito valida mocks e interacciones
   - JaCoCo recoge cobertura
5. Resultado:
   - ✅ Todo correcto → pipeline OK
   - ❌ Test roto o cobertura insuficiente → pipeline FAIL

---
</div>