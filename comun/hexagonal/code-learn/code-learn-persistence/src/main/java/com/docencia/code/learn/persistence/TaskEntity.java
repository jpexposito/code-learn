package com.docencia.code.learn.persistence;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tasks")
public class TaskEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 3, max = 120)
  @Column(name = "titulo", nullable = false)
  private String titulo;

  @Size(max = 2000)
  @Column(name = "descripcion")
  private String descripcion;

  @Column(name = "completada", nullable = false)
  private boolean completada = false;

  protected TaskEntity() {}

  public TaskEntity(String titulo, String descripcion) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.completada = false;
  }

  public Long getId() { return id; }

  public String getTitulo() { return titulo; }
  public void setTitulo(String titulo) { this.titulo = titulo; }

  public String getDescripcion() { return descripcion; }
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

  public boolean isCompletada() { return completada; }
  public void setCompletada(boolean completada) { this.completada = completada; }
}
