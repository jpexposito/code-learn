package com.docencia.code.learn.rest.service;

import com.docencia.code.learn.persistence.UserEntity;
import com.docencia.code.learn.persistence.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class AuthService {

  private final UserRepository repo;


  @Value("${auth.max-failed-attempts:3}")
  private int maxFailed;

  public AuthService(UserRepository repo) {
    this.repo = repo;
  }

  public static String normalizeEmail(String email) {
    return email == null ? null : email.trim().toLowerCase(Locale.ROOT);
  }

  @Transactional
  public UserEntity register(String nombre, String email, String password) {
    String e = normalizeEmail(email);
    if (repo.existsByEmail(e)) {
      throw new IllegalArgumentException("Email ya registrado");
    }
    return repo.save(new UserEntity(nombre, e, password));
  }

  @Transactional
  public boolean login(String email, String password) {
    String e = normalizeEmail(email);

    var opt = repo.findByEmail(e);
    if (opt.isEmpty()) return false;

    UserEntity u = opt.get();
    if (u.isBloqueado()) return false;

    if (!u.getPassword().equals(password)) {
      u.setIntentosFallidos(u.getIntentosFallidos() + 1);
      if (u.getIntentosFallidos() >= maxFailed) {
        u.setBloqueado(true);
      }
      repo.save(u);
      return false;
    }

    // login correcto
    u.setIntentosFallidos(0);
    repo.save(u);
    return true;
  }
}
