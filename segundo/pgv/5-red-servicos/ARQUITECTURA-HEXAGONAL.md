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


## Dependencias necesarias

```xml
<properties>
    <jacoco.version>0.8.12</jacoco.version>
    <mapstruct.version>1.5.5.Final</mapstruct.version>
</properties>
<dependency>
   <groupId>org.mapstruct</groupId>
   <artifactId>mapstruct</artifactId>
   <version>${mapstruct.version}</version>
</dependency>
<dependency>
   <groupId>org.mockito</groupId>
   <artifactId>mockito-core</artifactId>
   <scope>test</scope>
</dependency>
<dependency>
   <groupId>org.mockito</groupId>
   <artifactId>mockito-junit-jupiter</artifactId>
   <scope>test</scope>
</dependency>


<build>
    <plugins>
      <!-- Compiler plugin: necesario para annotation processors (MapStruct) -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <source>${java.version}</source>
          <target>${java.version}</target>
          <annotationProcessorPaths>
            <path>
              <groupId>org.mapstruct</groupId>
              <artifactId>mapstruct-processor</artifactId>
              <version>${mapstruct.version}</version>
            </path>
          </annotationProcessorPaths>
        </configuration>
      </plugin>

      <!-- JaCoCo: cobertura de tests -->
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
        </executions>
      </plugin>
    </plugins>
  </build>
```


## ✅ Beneficios del refactor

- Código más mantenible
- Tests unitarios sencillos
- Dominio reutilizable

---

## Declaración de mappers

Para la declaración de mappers, dado que hacemos uso de `spring`, lo haremos de la siguiente forma:

```java
@Mapper(componentModel = "spring")
public interface TaskMapper { ... }
```

> **Nota**: Permite inyectar a través de `spring` los `mappers`.


## Utilización de Mokito en el Testing

```java
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateTaskTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private UpdateTask updateTask;

    @Test
    void shouldUpdateTaskTitlewhenTaskExistsTest() {
        Long id = 1L;
        Task existing = new Task(id, "Titulo antiguo");

        when(taskRepository.findById(id)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); 

        Task result = updateTask.execute(id, "Titulo nuevo");

        assertEquals(id, result.getId());
        assertEquals("Titulo nuevo", result.getTitle());

        verify(taskRepository).findById(id);
        verify(taskRepository).save(any(Task.class));

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(captor.capture());
        Task savedTask = captor.getValue();

        assertEquals("Titulo nuevo", savedTask.getTitle());
    }
   @Test
    void shouldThrowExceptionWhenTaskDoesNotExistTest() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> updateTask.execute(99L, "Da igual")
        );

        assertEquals("Task not found", ex.getMessage());

        verify(taskRepository, never()).save(any());
    }  
```

## Licencia 📄

Este proyecto está bajo la Licencia (Apache 2.0) - mira el archivo [LICENSE.md](../../../LICENSE) para detalles

</div>