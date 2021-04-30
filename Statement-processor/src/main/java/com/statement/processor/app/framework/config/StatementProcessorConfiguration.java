
package com.statement.processor.app.framework.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * Spring configuration file which holds all the custom beans.
 *
 * @author satheesh.arunachalam
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.statement.processor"})
public class StatementProcessorConfiguration implements WebMvcConfigurer {

  @Bean
  public ObjectMapper xmlMapper() {
    ObjectMapper xmlMapper = new XmlMapper();
    xmlMapper.registerModule(new ParameterNamesModule());
    xmlMapper.registerModule(new Jdk8Module());
    xmlMapper.registerModule(new JavaTimeModule());
    return xmlMapper;
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Bean
  public MultipartResolver multipartResolver() {
    return new CommonsMultipartResolver();
  }

}
