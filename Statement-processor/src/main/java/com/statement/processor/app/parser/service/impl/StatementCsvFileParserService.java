
package com.statement.processor.app.parser.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.statement.processor.app.parser.exception.FileProcesException;
import com.statement.processor.app.parser.service.FileParserService;
import com.statement.processor.data.statement.CustomerStatement;
import com.statement.processor.data.statement.csv.StatementCsvData;

/**
 * CSV file parser which parses the given CustomerStatement.
 *
 * @author satheesh.arunachalam
 */
@Service
public class StatementCsvFileParserService implements FileParserService {

  @Override
  public List<CustomerStatement> fileProessor(InputStream inputStream) {
    // parse the data from csv and add it to a dto as a raw data
    List<StatementCsvData> csvStatementRecords;
    csvStatementRecords = new CsvToBeanBuilder<StatementCsvData>(new BufferedReader(new InputStreamReader(inputStream))).withOrderedResults(true)
        .withType(StatementCsvData.class).build().parse();
    return convert(csvStatementRecords);
  }

  private List<CustomerStatement> convert(List<StatementCsvData> csvStatementRecords) {
    return csvStatementRecords.stream().map(this::csvDataToCustomStatement).collect(Collectors.toList());
  }

  private CustomerStatement csvDataToCustomStatement(StatementCsvData record) {
    // from csv raw data convert to a proper dto with proper data type.
    CustomerStatement statementRecord = null;
    try {
      statementRecord = CustomerStatement.builder().reference(record.getReference()).accountNumber(record.getAccountNumber())
          .description(record.getDescription()).startBalance(new BigDecimal(record.getStartBalance())).mutation(new BigDecimal(record.getMutation()))
          .endBalance(new BigDecimal(record.getEndBalance())).build();
    } catch (NumberFormatException exception) {
      throw new FileProcesException("Error while parsing the CSV data, please check the csv values.", exception);
    }
    return statementRecord;
  }

}
