package com.docencia.code.learn.rest.dto;

public class UserResponse {
  public Long id;
  public String nombre;
  public String email;

  public static UserResponse of(Long id, String nombre, String email) {
    UserResponse r = new UserResponse();
    r.id = id;
    r.nombre = nombre;
    r.email = email;
    return r;
  }
}
