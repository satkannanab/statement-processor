
package com.statement.processor.app.statement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.statement.processor.app.parser.exception.ValidationException;
import com.statement.processor.app.parser.service.impl.CustomerStatementFileHandler;
import com.statement.processor.app.statement.service.StatementProcessorService;
import com.statement.processor.data.statement.CustomerStatements;
import com.statement.processor.data.statement.validator.CustomerStatementValidator;

/**
 * Default implementation of {@link StatementProcessorService}.
 *
 * @author satheesh.arunachalam
 */
@Service
public class StatementProcessorServiceImpl implements StatementProcessorService {

  @Autowired
  private CustomerStatementFileHandler customerStatementFileHandler;
  @Autowired
  private CustomerStatementValidator customerStatementValidator;

  @Override
  public CustomerStatements processStatement(MultipartFile file) {
    CustomerStatements statements = customerStatementFileHandler.processFile(file);
    BeanPropertyBindingResult errors = new BeanPropertyBindingResult(statements, "customerStatements");
    customerStatementValidator.validate(statements, errors);
    if (errors.hasErrors()) {
      throw new ValidationException("Validation error while processing the request", errors);
    }
    return statements;
  }

}
