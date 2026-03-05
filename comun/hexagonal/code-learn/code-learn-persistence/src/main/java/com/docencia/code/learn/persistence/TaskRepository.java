package com.docencia.code.learn.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
  List<TaskEntity> findByCompletada(boolean completada);
}
