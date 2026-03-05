package com.docencia.code.learn.rest.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TaskRequest {

  @NotBlank
  @Size(min = 3, max = 120)
  private String titulo;

  @Size(max = 2000)
  private String descripcion;

  private Boolean completada;

  public String getTitulo() { return titulo; }
  public void setTitulo(String titulo) { this.titulo = titulo; }

  public String getDescripcion() { return descripcion; }
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

  public Boolean getCompletada() { return completada; }
  public void setCompletada(Boolean completada) { this.completada = completada; }
}
