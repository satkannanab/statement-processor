
package com.statement.processor.app.rest.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.statement.processor.app.framework.config.StatementProcessorConfiguration;

/**
 * Holds the configurations to set up an integration test.
 *
 * @author satheesh.arunachalam
 */
@ContextConfiguration(classes = {StatementProcessorConfiguration.class})
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public abstract class AbstractResourceControllerIntegrationTest {

  @Autowired
  protected MockMvc mockMvc;

  protected InputStream getInputStream(String resourceName) throws IOException {
    return new DefaultResourceLoader().getResource("./data/" + resourceName).getInputStream();
  }

  protected String getFileAsString(String resourceName) throws IOException {
    return IOUtils.toString(getInputStream(resourceName), StandardCharsets.UTF_8.name());
  }

}
