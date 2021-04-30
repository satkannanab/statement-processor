
package com.statement.processor.app.rest.api.statement;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.statement.processor.app.rest.api.config.AbstractResourceControllerIntegrationTest;

/**
 * Integration test for {@link StatementProcessorResourceController}.
 *
 * @author satheesh.arunachalam
 */
public class StatementProcessorResourceControllerIntegrationTest extends AbstractResourceControllerIntegrationTest {

  @Test
  public void testProcessStatement_csv_validation_failure() throws Exception {
    MockMultipartFile multipartFile =
        new MockMultipartFile("file", "records_validation_failure.csv", "multipart/form-data; charset=UTF-8", getInputStream("records_validation_failure.csv"));

    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/statements/process").file(multipartFile)
        .contentType(MediaType.MULTIPART_FORM_DATA).accept(MediaType.APPLICATION_JSON));

    result.andExpect(MockMvcResultMatchers.status().isBadRequest())//
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//

        .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessages").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessages.[0].message").value("Reference numbers 112806 are duplicated in the customer statement."))//
        .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessages.[0].severity").value("VALIDATION_ERROR"))//
        .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessages.[0].httpStatus").value("BAD_REQUEST"))//
        .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessages.[1].message")
            .value("Reference numbers 177666 have incorrect balances in the customer statement."))//
        .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessages.[1].severity").value("VALIDATION_ERROR"))//
        .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessages.[1].httpStatus").value("BAD_REQUEST"));
  }

  @Test
  public void testProcessStatement_xml_validation_failure() throws Exception {
    MockMultipartFile multipartFile =
        new MockMultipartFile("file", "records_validation_failure.xml", "multipart/form-data; charset=UTF-8", getInputStream("records_validation_failure.xml"));

    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/statements/process").file(multipartFile)
        .contentType(MediaType.MULTIPART_FORM_DATA).accept(MediaType.APPLICATION_XML));

    String expectedXmlResponse =
        "<RestErrorResponse><responseMessages><responseMessages><message>Reference numbers 154270 have incorrect balances in the customer statement.</message><severity>VALIDATION_ERROR</severity><httpStatus>BAD_REQUEST</httpStatus></responseMessages></responseMessages></RestErrorResponse>";

    result.andExpect(MockMvcResultMatchers.status().isBadRequest())//
        .andExpect(MockMvcResultMatchers.content().xml(expectedXmlResponse));
  }

  @Test
  public void testProcessStatement_csv() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "records.csv", "multipart/form-data; charset=UTF-8", getInputStream("records.csv"));

    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/statements/process").file(multipartFile)
        .contentType(MediaType.MULTIPART_FORM_DATA).accept(MediaType.APPLICATION_JSON));

    String expectedFileContent = getFileAsString("records_verify.json");

    result.andExpect(MockMvcResultMatchers.status().isOk())//
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))//
        .andExpect(MockMvcResultMatchers.content().json(expectedFileContent));
  }

  @Test
  public void testProcessStatement_xml() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "records.xml", "multipart/form-data; charset=UTF-8", getInputStream("records.xml"));

    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/statements/process").file(multipartFile)
        .contentType(MediaType.MULTIPART_FORM_DATA).accept(MediaType.APPLICATION_XML));

    String expectedFileContent = getFileAsString("records_verify.xml");

    result.andExpect(MockMvcResultMatchers.status().isOk())//
        .andExpect(MockMvcResultMatchers.content().xml(expectedFileContent));
  }

  @Test
  public void testHomePage() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/statements/")).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Home page"));
  }

}
