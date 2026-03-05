package com.docencia.code.learn.rest.controller;

import com.docencia.code.learn.persistence.TaskEntity;
import com.docencia.code.learn.rest.dto.TaskRequest;
import com.docencia.code.learn.rest.dto.TaskResponse;
import com.docencia.code.learn.rest.service.TaskService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "basicAuth")
public class TaskController {

  private final TaskService service;

  public TaskController(TaskService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<TaskResponse>> list(@RequestParam(required = false) Boolean completada) {
    List<TaskResponse> out = service.list(completada).stream()
        .map(TaskController::toResponse)
        .collect(Collectors.toList());
    return ResponseEntity.ok(out);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskResponse> get(@PathVariable long id) {
    return ResponseEntity.ok(toResponse(service.get(id)));
  }

  @PostMapping
  public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest req, UriComponentsBuilder uriBuilder) {
    TaskEntity created = service.create(req);
    return ResponseEntity
        .created(uriBuilder.path("/api/tasks/{id}").buildAndExpand(created.getId()).toUri())
        .body(toResponse(created));
  }

  @PutMapping("/{id}")
  public ResponseEntity<TaskResponse> update(@PathVariable long id, @Valid @RequestBody TaskRequest req) {
    return ResponseEntity.ok(toResponse(service.update(id, req)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  private static TaskResponse toResponse(TaskEntity t) {
    return TaskResponse.of(t.getId(), t.getTitulo(), t.getDescripcion(), t.isCompletada());
  }
}
