package com.docencia.code.learn.rest.dto;

public class TaskResponse {

  private Long id;
  private String titulo;
  private String descripcion;
  private boolean completada;

  public static TaskResponse of(Long id, String titulo, String descripcion, boolean completada) {
    TaskResponse r = new TaskResponse();
    r.id = id;
    r.titulo = titulo;
    r.descripcion = descripcion;
    r.completada = completada;
    return r;
  }

  public Long getId() { return id; }
  public String getTitulo() { return titulo; }
  public String getDescripcion() { return descripcion; }
  public boolean isCompletada() { return completada; }
}
