package com.docencia.code.learn.rest.controller;

import com.docencia.code.learn.persistence.UserRepository;
import com.docencia.code.learn.rest.dto.UserResponse;
import com.docencia.code.learn.rest.service.AuthService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "basicAuth")
public class UserController {

  private final UserRepository repo;

  public UserController(UserRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<UserResponse> list() {
    return repo.findAll().stream()
        .map(u -> UserResponse.of(u.getId(), u.getNombre(), u.getEmail()))
        .collect(Collectors.toList());
  }

  @DeleteMapping("/{email}")
  public boolean delete(@PathVariable String email) {
    long deleted = repo.deleteByEmail(AuthService.normalizeEmail(email));
    return deleted > 0;
  }
}
