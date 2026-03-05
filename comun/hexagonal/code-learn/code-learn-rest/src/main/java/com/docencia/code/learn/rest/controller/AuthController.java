package com.docencia.code.learn.rest.controller;

import com.docencia.code.learn.persistence.UserEntity;
import com.docencia.code.learn.rest.dto.LoginRequest;
import com.docencia.code.learn.rest.dto.RegisterRequest;
import com.docencia.code.learn.rest.dto.UserResponse;
import com.docencia.code.learn.rest.service.AuthService;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService auth;

  public AuthController(AuthService auth) {
    this.auth = auth;
  }

  @PostMapping("/register")
  public UserResponse register(@Valid @RequestBody RegisterRequest req) {
    UserEntity u = auth.register(req.nombre, req.email, req.password);
    return UserResponse.of(u.getId(), u.getNombre(), u.getEmail());
  }

  @PostMapping("/login")
  public boolean login(@Valid @RequestBody LoginRequest req) {
    return auth.login(req.email, req.password);
  }
}
