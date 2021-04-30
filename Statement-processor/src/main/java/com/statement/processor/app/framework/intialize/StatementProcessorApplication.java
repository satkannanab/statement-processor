package com.statement.processor.app.framework.intialize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Initializes and bootstraps a spring boot application.
 *
 * @author satheesh.arunachalam
 */
@EnableWebMvc
@SpringBootApplication(scanBasePackages = {"com.statement.processor"})
public class StatementProcessorApplication {

  public static void main(String[] args) {
    SpringApplication.run(StatementProcessorApplication.class, args);
  }

}
