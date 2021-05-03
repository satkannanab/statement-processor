package com.statement.processor.app.parser.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.statement.processor.app.parser.exception.FileProcesException;
import com.statement.processor.data.statement.CustomerStatement;

/**
 * Unit test for {@link StatementCsvFileParserService}.
 *
 * @author satheesh.arunachalam
 */
public class StatementCsvFileParserServiceTest {

  private StatementCsvFileParserService statementCsvFileParserService = new StatementCsvFileParserService();

  @Test
  public void testFileProessor() {
    String csvFile = new StringBuffer().append("Reference,").append("Account Number,").append("Description,").append("Start Balance,").append("Mutation,")
        .append("End Balance")//
        .append("\n")//
        .append("177666,").append("NL93ABNA0585619023,").append("Flowers for Rik,").append("44.85,").append("-22.24,").append("22.61")//
        .append("\n")//
        .append("137666,").append("NL93ABNA0585619024,").append("Flowers for Me,").append("54.85,").append("344,").append("23")//
        .append("\n")//
        .toString();
    InputStream inputStream = new ByteArrayInputStream(csvFile.getBytes());
    // when
    List<CustomerStatement> actuals = statementCsvFileParserService.fileProessor(inputStream);
    // verify
    Assert.assertEquals("Expect to have 2 customer statements", 2, actuals.size());
    CustomerStatement customerStatement = actuals.get(0);
    Assert.assertEquals("Expect to match the reference number", "177666", customerStatement.getReference());
    Assert.assertEquals("Expect to match the acc number", "NL93ABNA0585619023", customerStatement.getAccountNumber());
    Assert.assertEquals("Expect to match the desc", "Flowers for Rik", customerStatement.getDescription());
    Assert.assertEquals("Expect to match the start balance", new BigDecimal("44.85"), customerStatement.getStartBalance());
    Assert.assertEquals("Expect to match the mutation", new BigDecimal("-22.24"), customerStatement.getMutation());
    Assert.assertEquals("Expect to match the end balance", new BigDecimal("22.61"), customerStatement.getEndBalance());
  }

  @Test(expected = RuntimeException.class)
  public void testFileProessor_csv_notWell_formed() {
    String csvFile_error_in_header = new StringBuffer().append("Reference,reffccc,").append("Account Number,").append("Description,").append("Start Balance,")
        .append("Mutation,").append("End Balance")//
        .append("\n")//
        .append("177666,").append("NL93ABNA0585619023,").append("Flowers for Rik,").append("44.85,").append("-22.24,").append("22.61")//
        .append("\n")//
        .append("137666,").append("NL93ABNA0585619024,").append("Flowers for Me,").append("54.85,").append("344,").append("23")//
        .append("\n")//
        .toString();
    InputStream inputStream = new ByteArrayInputStream(csvFile_error_in_header.getBytes());
    // when
    statementCsvFileParserService.fileProessor(inputStream);
    // verify
    // exception
  }

  @Test(expected = FileProcesException.class)
  public void testFileProessor_csvValues_notWell_formed() {
    String csvFile_error_in_balance_value = new StringBuffer().append("Reference,").append("Account Number,").append("Description,").append("Start Balance,")
        .append("Mutation,").append("End Balance")//
        .append("\n")//
        .append("177666,").append("NL93ABNA0585619023,").append("Flowers for Rik,").append("44...85,").append("-22.24,").append("22.61")//
        .append("\n")//
        .append("137666,").append("NL93ABNA0585619024,").append("Flowers for Me,").append("54.85,").append("344,").append("23")//
        .append("\n")//
        .toString();
    InputStream inputStream = new ByteArrayInputStream(csvFile_error_in_balance_value.getBytes());
    // when
    statementCsvFileParserService.fileProessor(inputStream);
    // verify
    // exception
  }

}
