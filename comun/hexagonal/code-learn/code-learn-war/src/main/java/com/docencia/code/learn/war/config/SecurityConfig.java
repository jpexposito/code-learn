package com.docencia.code.learn.war.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Basic Auth sencillo para ejemplo.
   *
   * Usuarios:
   * - admin / admin
   * - user  / user
   */
  @Bean
  public UserDetailsService users() {
    UserDetails admin = User.withUsername("admin")
        .password("{noop}admin")
        .roles("ADMIN")
        .build();

    UserDetails user = User.withUsername("user")
        .password("{noop}user")
        .roles("USER")
        .build();

    return new InMemoryUserDetailsManager(admin, user);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // Para APIs y Swagger suele ser más cómodo desactivar CSRF en este ejemplo.
    http.csrf(csrf -> csrf.disable());

    http.authorizeRequests(auth -> auth
        // Swagger / OpenAPI
        .antMatchers(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**")
        .permitAll()
        // Endpoints públicos de auth
        .antMatchers("/api/auth/**")
        .permitAll()
        // Tasks: GET -> USER o ADMIN; resto -> solo ADMIN
        .antMatchers(HttpMethod.GET, "/api/tasks/**").hasAnyRole("USER", "ADMIN")
        .antMatchers("/api/tasks/**").hasRole("ADMIN")
        // Resto de endpoints: protegido
        .anyRequest().authenticated()
    );

    http.httpBasic(Customizer.withDefaults());
    return http.build();
  }
}
