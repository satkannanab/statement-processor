package com.statement.processor.app.statement.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.statement.processor.app.parser.exception.ValidationException;
import com.statement.processor.app.parser.service.impl.CustomerStatementFileHandler;
import com.statement.processor.data.statement.CustomerStatements;
import com.statement.processor.data.statement.validator.CustomerStatementValidator;

/**
 * Unit test for {@link StatementProcessorServiceImpl}.
 *
 * @author satheesh.arunachalam
 */
@RunWith(MockitoJUnitRunner.class)
public class StatementProcessorServiceImplTest {

  @Mock
  private CustomerStatementFileHandler customerStatementFileHandler;
  @Mock
  private CustomerStatementValidator customerStatementValidator;
  @Mock
  private MultipartFile file;

  @InjectMocks
  private StatementProcessorServiceImpl statementProcessorServiceImpl;

  @Test
  public void testProcessStatement() {
    CustomerStatements statements = CustomerStatements.builder().build();
    Mockito.when(customerStatementFileHandler.processFile(file)).thenReturn(statements);
    // when
    CustomerStatements actual = statementProcessorServiceImpl.processStatement(file);
    // verify
    Assert.assertEquals("Expect to match the statements", statements, actual);
    Mockito.verify(customerStatementValidator).validate(Mockito.eq(statements), Mockito.any(BeanPropertyBindingResult.class));
  }

  @Test(expected = ValidationException.class)
  public void testProcessStatement_with_validation_error() {
    CustomerStatements statements = CustomerStatements.builder().build();
    Mockito.when(customerStatementFileHandler.processFile(file)).thenReturn(statements);
    Mockito.doAnswer(invocation -> {
      Object[] args = invocation.getArguments();
      BeanPropertyBindingResult errors = (BeanPropertyBindingResult) args[1];
      errors.reject("validation.error");
      return null;
    }).when(customerStatementValidator).validate(Mockito.eq(statements), Mockito.any(BeanPropertyBindingResult.class));
    // when
    statementProcessorServiceImpl.processStatement(file);
    // verify exception
  }

}
