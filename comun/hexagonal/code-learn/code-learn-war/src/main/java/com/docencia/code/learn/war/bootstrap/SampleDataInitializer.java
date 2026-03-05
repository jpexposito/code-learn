package com.docencia.code.learn.war.bootstrap;

import com.docencia.code.learn.persistence.UserEntity;
import com.docencia.code.learn.persistence.UserRepository;
import com.docencia.code.learn.rest.service.AuthService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Inserta usuarios de ejemplo al arrancar.
 *
 * Nota: como usas H2 en memoria, estos datos se pierden al reiniciar WildFly.
 */
@Component
@ConditionalOnProperty(name = "codelearn.seed.enabled", havingValue = "true", matchIfMissing = false)
public class SampleDataInitializer implements ApplicationRunner {

  private final UserRepository repo;

  public SampleDataInitializer(UserRepository repo) {
    this.repo = repo;
  }

  @Override
  public void run(ApplicationArguments args) {
    // Solo 2 usuarios en tabla users para pruebas
    // Importante: el campo "nombre" tiene @Size(min=5), así que usamos nombres >= 5 chars
    seed("AdminUser", "admin@codelearn.local", "admin123");
    seed("UserDemo", "user@codelearn.local", "user123");
  }

  private void seed(String nombre, String email, String password) {
    String e = AuthService.normalizeEmail(email);
    if (!repo.existsByEmail(e)) {
      repo.save(new UserEntity(nombre, e, password));
    }
  }
}
