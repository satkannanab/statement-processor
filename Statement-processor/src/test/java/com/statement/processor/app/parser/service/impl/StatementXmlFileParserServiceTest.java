package com.statement.processor.app.parser.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.statement.processor.app.parser.exception.FileProcesException;
import com.statement.processor.data.statement.CustomerStatement;
import com.statement.processor.data.statement.xml.StatementXmlData;
import com.statement.processor.data.statement.xml.StatementXmlDatas;

/**
 * Unit test for {@link StatementXmlFileParserService}.
 *
 * @author satheesh.arunachalam
 */
@RunWith(MockitoJUnitRunner.class)
public class StatementXmlFileParserServiceTest {

  @Mock
  private ObjectMapper xmlMapper;
  @Mock
  private InputStream inputStream;
  @InjectMocks
  private StatementXmlFileParserService fileParserService;

  @Test
  public void testFileProessor() throws JsonParseException, JsonMappingException, IOException {
    StatementXmlData statementXmlData = StatementXmlData.builder().reference("187997").accountNumber("NL91RABO0315273637").description("Clothes for Rik King")
        .startBalance("57.6").mutation("-32.98").endBalance("24.62").build();
    StatementXmlData statementXmlData1 = StatementXmlData.builder().reference("187998").accountNumber("NL91RABO0315273638")
        .description("Clothes for Rik King - new").startBalance("57.8").mutation("-32.99").endBalance("24.63").build();

    StatementXmlDatas statementXmlDatas = new StatementXmlDatas(Arrays.asList(statementXmlData, statementXmlData1));

    Mockito.when(xmlMapper.readValue(inputStream, StatementXmlDatas.class)).thenReturn(statementXmlDatas);
    // when
    List<CustomerStatement> actuals = fileParserService.fileProessor(inputStream);
    // verify
    Assert.assertEquals("Expect to have 2 customer statements", 2, actuals.size());
    CustomerStatement customerStatement = actuals.get(0);
    Assert.assertEquals("Expect to match the reference number", "187997", customerStatement.getReference());
    Assert.assertEquals("Expect to match the acc number", "NL91RABO0315273637", customerStatement.getAccountNumber());
    Assert.assertEquals("Expect to match the desc", "Clothes for Rik King", customerStatement.getDescription());
    Assert.assertEquals("Expect to match the start balance", new BigDecimal("57.6"), customerStatement.getStartBalance());
    Assert.assertEquals("Expect to match the mutation", new BigDecimal("-32.98"), customerStatement.getMutation());
    Assert.assertEquals("Expect to match the end balance", new BigDecimal("24.62"), customerStatement.getEndBalance());

    CustomerStatement customerStatement1 = actuals.get(1);
    Assert.assertEquals("Expect to match the reference number", "187998", customerStatement1.getReference());
    Assert.assertEquals("Expect to match the acc number", "NL91RABO0315273638", customerStatement1.getAccountNumber());
    Assert.assertEquals("Expect to match the desc", "Clothes for Rik King - new", customerStatement1.getDescription());
    Assert.assertEquals("Expect to match the start balance", new BigDecimal("57.8"), customerStatement1.getStartBalance());
    Assert.assertEquals("Expect to match the mutation", new BigDecimal("-32.99"), customerStatement1.getMutation());
    Assert.assertEquals("Expect to match the end balance", new BigDecimal("24.63"), customerStatement1.getEndBalance());
  }

  @Test(expected = FileProcesException.class)
  public void testFileProessor_unable_to_parseTheFile() throws JsonParseException, JsonMappingException, IOException {
    Mockito.when(xmlMapper.readValue(inputStream, StatementXmlDatas.class)).thenThrow(new IOException());
    // when
    fileParserService.fileProessor(inputStream);
    // verify
    // exception
  }

  @Test(expected = FileProcesException.class)
  public void testFileProessor_error_while_parsing_data() throws JsonParseException, JsonMappingException, IOException {
    StatementXmlData statementXmlData = StatementXmlData.builder().reference("187997").accountNumber("NL91RABO0315273637").description("Clothes for Rik King")
        .startBalance("57.6").mutation("-32.98dfd").endBalance("24.62").build();
    StatementXmlData statementXmlData1 = StatementXmlData.builder().reference("187998").accountNumber("NL91RABO0315273638")
        .description("Clothes for Rik King - new").startBalance("57.8").mutation("-3asdfsdf2.99").endBalance("24.63").build();

    StatementXmlDatas statementXmlDatas = new StatementXmlDatas(Arrays.asList(statementXmlData, statementXmlData1));

    Mockito.when(xmlMapper.readValue(inputStream, StatementXmlDatas.class)).thenReturn(statementXmlDatas);
    // when
    fileParserService.fileProessor(inputStream);
    // verify
    // exception
  }

}
