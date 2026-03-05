package com.docencia.code.learn.war;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.docencia.code.learn")
@EnableJpaRepositories(basePackages = "com.docencia.code.learn.persistence")
@EntityScan(basePackages = "com.docencia.code.learn.persistence")
public class CodeLearnApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(CodeLearnApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(CodeLearnApplication.class);
  }
}
