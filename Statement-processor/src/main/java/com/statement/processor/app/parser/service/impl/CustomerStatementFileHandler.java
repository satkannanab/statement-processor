
package com.statement.processor.app.parser.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.statement.processor.app.parser.exception.FileProcesException;
import com.statement.processor.app.parser.service.FileParserService;
import com.statement.processor.data.statement.CustomerStatement;
import com.statement.processor.data.statement.CustomerStatements;

/**
 * Statement file parser which parses the file of either xml or csv file and parses it to a StatementXmlDatas or StatementCsvData respectively.
 *
 * @author satheesh.arunachalam
 */
@Component
public class CustomerStatementFileHandler {

  @Autowired
  private FileParserService statementCsvFileParserService;
  @Autowired
  private FileParserService statementXmlFileParserService;

  /**
   * Processes the given file based on the content type as csv or xml.
   *
   * @param file of type MultipartFile.
   * @return list of CustomerStatement.
   */
  public CustomerStatements processFile(MultipartFile file) {
    List<CustomerStatement> statements = new ArrayList<>();
    try {
      InputStream inputStream = file.getInputStream();
      String fileName = file.getOriginalFilename();

      if (fileName != null) {
        if (fileName.endsWith("csv")) {
          statements = statementCsvFileParserService.fileProessor(inputStream);
        } else if (fileName.endsWith("xml")) {
          statements = statementXmlFileParserService.fileProessor(inputStream);
        }
      } else {
        throw new FileProcesException("Original file name not given to process the file");
      }
    } catch (IOException e) {
      throw new FileProcesException(String.format("Unable to process the file with name %s", file.getOriginalFilename()), e);
    }

    return CustomerStatements.builder().customerStatments(statements).build();
  }

  private String getMediaType(MultipartFile file) {
    return file.getContentType();
  }
}
