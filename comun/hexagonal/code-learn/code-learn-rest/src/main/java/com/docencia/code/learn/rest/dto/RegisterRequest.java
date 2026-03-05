package com.docencia.code.learn.rest.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterRequest {
  @NotBlank @Size(min = 5)
  public String nombre;

  @NotBlank @Email
  public String email;

  @NotBlank @Size(min = 6)
  public String password;
}
