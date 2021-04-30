
package com.statement.processor.app.parser.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statement.processor.app.parser.exception.FileProcesException;
import com.statement.processor.app.parser.service.FileParserService;
import com.statement.processor.data.statement.CustomerStatement;
import com.statement.processor.data.statement.xml.StatementXmlData;
import com.statement.processor.data.statement.xml.StatementXmlDatas;

/**
 * Xml file parser that parses the Customer statement records.
 *
 * @author satheesh.arunachalam
 */
@Service
public class StatementXmlFileParserService implements FileParserService {

  @Autowired
  private ObjectMapper xmlMapper;

  @Override
  public List<CustomerStatement> fileProessor(InputStream inputStream) {
    // parse the data from xml and add it to a dto as a raw data
    StatementXmlDatas statementRecords = null;
    try {
      statementRecords = xmlMapper.readValue(inputStream, StatementXmlDatas.class);
    } catch (IOException e) {
      throw new FileProcesException("Error while parsing the XML data, please check the xml file.", e);
    }
    return convert(statementRecords);
  }

  private List<CustomerStatement> convert(StatementXmlDatas statementRecords) {
    return statementRecords.getStatements().stream().map(this::xmlDataToCustomStatement).collect(Collectors.toList());
  }

  private CustomerStatement xmlDataToCustomStatement(StatementXmlData record) {
    // from xml raw data convert to a proper dto with proper data type.
    CustomerStatement statementRecord = null;
    try {
      statementRecord = CustomerStatement.builder().reference(record.getReference()).accountNumber(record.getAccountNumber())
          .description(record.getDescription()).startBalance(new BigDecimal(record.getStartBalance())).mutation(new BigDecimal(record.getMutation()))
          .endBalance(new BigDecimal(record.getEndBalance())).build();
    } catch (NumberFormatException exception) {
      throw new FileProcesException("Error while parsing the XML data, please check the xml values.", exception);
    }
    return statementRecord;
  }

}
