
package com.statement.processor.app.rest.api.statement;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.statement.processor.app.statement.service.StatementProcessorService;
import com.statement.processor.data.statement.CustomerStatements;

/**
 * Rest api resource controller for statement processor.
 *
 * @author satheesh.arunachalam
 */
@RestController
@RequestMapping(path = "/api/statements")
public class StatementProcessorResourceController {

  @Autowired
  private StatementProcessorService statementProcessorService;

  /**
   * Processes the statement file.
   *
   * @param file of type MultipartFile
   */
  @PostMapping(path = "/process")
  public ResponseEntity<CustomerStatements> processStatement(@RequestPart("file") @Valid MultipartFile file) {
    CustomerStatements customerStatements = statementProcessorService.processStatement(file);
    return new ResponseEntity<>(customerStatements, HttpStatus.OK);
  }

  /**
   * Test page to check make sure app runs
   */
  @GetMapping(path = "/")
  public ResponseEntity<String> home() {
    return new ResponseEntity<>("Home page", HttpStatus.OK);
  }

}
