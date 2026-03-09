package com.docencia.code.learn.war.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.docencia.code.learn.persistence.UserEntity;
import com.docencia.code.learn.persistence.UserRepository;

@Configuration
public class DataInitializer {

  @Bean
  CommandLineRunner initUsers(UserRepository userRepository) {
    return args -> {

      if (userRepository.findByEmail("admin@codelearn.local").isEmpty()) {
        UserEntity admin = new UserEntity("Administrador", "admin@codelearn.local", "admin123");
        admin.setIntentosFallidos(0);
        admin.setBloqueado(false);
        admin.setRol("ADMIN");
        userRepository.save(admin);
      }

      if (userRepository.findByEmail("user@codelearn.local").isEmpty()) {
        UserEntity user = new UserEntity("Usuario Demo", "user@codelearn.local", "user123");
        user.setIntentosFallidos(0);
        user.setBloqueado(false);
        user.setRol("USER");
        userRepository.save(user);
      }
    };
  }
}
