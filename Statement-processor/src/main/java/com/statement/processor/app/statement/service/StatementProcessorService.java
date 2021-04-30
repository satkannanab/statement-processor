
package com.statement.processor.app.statement.service;

import org.springframework.web.multipart.MultipartFile;

import com.statement.processor.data.statement.CustomerStatements;

/**
 * Statement processor service which handles the file, validates and processes it.
 *
 * @author satheesh.arunachalam
 */
public interface StatementProcessorService {

  /**
   * Process the given customer statement and validates the customer references and their mutual balances.
   *
   * @param file of type MultipartFile.
   * @return CustomerStatements with all processed customer details.
   */
  public CustomerStatements processStatement(MultipartFile file);

}
