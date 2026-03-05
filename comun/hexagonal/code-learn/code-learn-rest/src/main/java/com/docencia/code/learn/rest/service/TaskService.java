package com.docencia.code.learn.rest.service;

import com.docencia.code.learn.persistence.TaskEntity;
import com.docencia.code.learn.persistence.TaskRepository;
import com.docencia.code.learn.rest.dto.TaskRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class TaskService {

  private final TaskRepository repo;

  public TaskService(TaskRepository repo) {
    this.repo = repo;
  }

  public List<TaskEntity> list(Boolean completada) {
    if (completada == null) return repo.findAll();
    return repo.findByCompletada(completada);
  }

  public TaskEntity get(long id) {
    return repo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task no encontrada: " + id));
  }

  public TaskEntity create(TaskRequest req) {
    TaskEntity t = new TaskEntity(req.getTitulo(), req.getDescripcion());
    if (req.getCompletada() != null) t.setCompletada(req.getCompletada());
    return repo.save(t);
  }

  public TaskEntity update(long id, TaskRequest req) {
    TaskEntity t = get(id);
    t.setTitulo(req.getTitulo());
    t.setDescripcion(req.getDescripcion());
    if (req.getCompletada() != null) t.setCompletada(req.getCompletada());
    return repo.save(t);
  }

  public void delete(long id) {
    if (!repo.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task no encontrada: " + id);
    }
    repo.deleteById(id);
  }
}
