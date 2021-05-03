package com.statement.processor.app.parser.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import com.statement.processor.app.parser.exception.FileProcesException;
import com.statement.processor.app.parser.service.FileParserService;
import com.statement.processor.data.statement.CustomerStatement;
import com.statement.processor.data.statement.CustomerStatements;

/**
 * Unit test for {@link CustomerStatementFileHandler}.
 *
 * @author satheesh.arunachalam
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerStatementFileHandlerTest {

  @Mock
  private FileParserService statementCsvFileParserService;
  @Mock
  private FileParserService statementXmlFileParserService;
  @Mock
  private MultipartFile file;
  @Mock
  private InputStream inputStream;
  @InjectMocks
  private CustomerStatementFileHandler customerStatementFileHandler;

  @Test
  public void testProcessFile_withCsvFile() throws IOException {
    Mockito.when(file.getInputStream()).thenReturn(inputStream);
    Mockito.when(file.getOriginalFilename()).thenReturn("file.csv");
    CustomerStatement customerStatement =
        CustomerStatement.builder().reference("ref-123").accountNumber("123-acc").description("desc1").mutation(BigDecimal.ONE).build();
    Mockito.when(statementCsvFileParserService.fileProessor(inputStream)).thenReturn(Collections.singletonList(customerStatement));
    // sut
    CustomerStatements actuals = customerStatementFileHandler.processFile(file);
    // verify
    assertCustomerStatements(customerStatement, actuals);
  }

  @Test
  public void testProcessFile_withXmlFile() throws IOException {
    Mockito.when(file.getInputStream()).thenReturn(inputStream);
    Mockito.when(file.getOriginalFilename()).thenReturn("file.xml");
    CustomerStatement customerStatement =
        CustomerStatement.builder().reference("ref-123").accountNumber("123-acc").description("desc1").mutation(BigDecimal.ONE).build();
    Mockito.when(statementXmlFileParserService.fileProessor(inputStream)).thenReturn(Collections.singletonList(customerStatement));
    // sut
    CustomerStatements actuals = customerStatementFileHandler.processFile(file);
    // verify
    assertCustomerStatements(customerStatement, actuals);
  }

  @Test(expected = FileProcesException.class)
  public void testProcessFile_withoutOriginalfileName() throws IOException {
    Mockito.when(file.getInputStream()).thenReturn(inputStream);
    // sut
    customerStatementFileHandler.processFile(file);
  }

  private void assertCustomerStatements(CustomerStatement customerStatement, CustomerStatements actuals) {
    Assert.assertNotNull("Expect not null CustomerStatements", actuals);
    Assert.assertEquals("Expect to match the customer statements", (Collections.singletonList(customerStatement)), actuals.getCustomerStatments());
  }

}
