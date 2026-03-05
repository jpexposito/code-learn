package com.docencia.code.learn.persistence;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name="uk_users_email", columnNames="email"))
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 5)
  @Column(name = "nombre", nullable = false)
  private String nombre;

  @NotBlank
  @Email
  @Column(name = "email", nullable = false, updatable = false)
  private String email;

  @NotBlank
  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "intentos_fallidos", nullable = false)
  private int intentosFallidos = 0;

  @Column(name = "bloqueado", nullable = false)
  private boolean bloqueado = false;

  protected UserEntity() {}

  public UserEntity(String nombre, String email, String password) {
    this.nombre = nombre;
    this.email = email;
    this.password = password;
    this.intentosFallidos = 0;
    this.bloqueado = false;
  }

  public Long getId() { return id; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public String getEmail() { return email; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
  public int getIntentosFallidos() { return intentosFallidos; }
  public void setIntentosFallidos(int intentosFallidos) { this.intentosFallidos = intentosFallidos; }
  public boolean isBloqueado() { return bloqueado; }
  public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }

  @PrePersist
  void prePersist() {
    // Seguridad extra por si alguna vez cambias a wrappers (Integer/Boolean)
    if (this.intentosFallidos < 0) this.intentosFallidos = 0;
  }

}
