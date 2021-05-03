package com.statement.processor.data.statement.validator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;

import com.statement.processor.data.statement.CustomerStatement;
import com.statement.processor.data.statement.CustomerStatements;

/**
 * Unit test for {@link CustomerStatementValidator}.
 *
 * @author satheesh.arunachalam
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerStatementValidatorTest {

  @InjectMocks
  private CustomerStatementValidator customerStatementValidator;

  @Test
  public void testSupports() {
    Assert.assertTrue("Expect to be true", customerStatementValidator.supports(CustomerStatements.class));
  }

  @Test
  public void testSupports_wrongClass() {
    Assert.assertFalse("Expect to be false", customerStatementValidator.supports(CustomerStatement.class));
  }

  @Test
  public void testvalidate_validationError_duplicateReferenceNumbers() {
    CustomerStatement customerStatement = CustomerStatement.builder().reference("ref-123").accountNumber("123-acc").description("desc1")
        .mutation(BigDecimal.ONE).startBalance(new BigDecimal("10")).endBalance(new BigDecimal("11")).build();
    CustomerStatement customerStatement1 = CustomerStatement.builder().reference("ref-123").accountNumber("123-acc1").description("desc2")
        .mutation(BigDecimal.ONE).startBalance(new BigDecimal("10")).endBalance(new BigDecimal("11")).build();
    CustomerStatements customerStatements = CustomerStatements.builder().customerStatments(Arrays.asList(customerStatement, customerStatement1)).build();
    BeanPropertyBindingResult errors = new BeanPropertyBindingResult(customerStatement, "customerStatements");
    // when
    customerStatementValidator.validate(customerStatements, errors);
    // verify
    List<ObjectError> allErrors = errors.getAllErrors();
    Assert.assertEquals("Expect number of errors to be 1", 1, allErrors.size());
    Assert.assertEquals("Expect to match the error code", "stmt.error.duplicate_references", allErrors.get(0).getCode());
  }

  @Test
  public void testvalidate_validationError_endBalance() {
    CustomerStatement customerStatement = CustomerStatement.builder().reference("ref-123").accountNumber("123-acc").description("desc1")
        .mutation(BigDecimal.ONE).startBalance(new BigDecimal("10")).endBalance(new BigDecimal("11")).build();
    CustomerStatement customerStatement1 = CustomerStatement.builder().reference("ref-123-123").accountNumber("123-acc1").description("desc2")
        .mutation(BigDecimal.ONE).startBalance(new BigDecimal("13")).endBalance(new BigDecimal("11")).build();
    CustomerStatements customerStatements = CustomerStatements.builder().customerStatments(Arrays.asList(customerStatement, customerStatement1)).build();
    BeanPropertyBindingResult errors = new BeanPropertyBindingResult(customerStatement, "customerStatements");
    // when
    customerStatementValidator.validate(customerStatements, errors);
    // verify
    List<ObjectError> allErrors = errors.getAllErrors();
    Assert.assertEquals("Expect number of errors to be 1", 1, allErrors.size());
    Assert.assertEquals("Expect to match the error code", "stmt.error.incorrect_balances", allErrors.get(0).getCode());
  }

  @Test
  public void testvalidate_noValidationError() {
    CustomerStatement customerStatement = CustomerStatement.builder().reference("ref-123").accountNumber("123-acc").description("desc1")
        .mutation(BigDecimal.ONE).startBalance(new BigDecimal("10")).endBalance(new BigDecimal("11")).build();
    CustomerStatement customerStatement1 = CustomerStatement.builder().reference("ref-123-123").accountNumber("123-acc1").description("desc2")
        .mutation(BigDecimal.ONE).startBalance(new BigDecimal("10")).endBalance(new BigDecimal("11")).build();
    CustomerStatements customerStatements = CustomerStatements.builder().customerStatments(Arrays.asList(customerStatement, customerStatement1)).build();
    BeanPropertyBindingResult errors = new BeanPropertyBindingResult(customerStatement, "customerStatements");
    // when
    customerStatementValidator.validate(customerStatements, errors);
    // verify
    List<ObjectError> allErrors = errors.getAllErrors();
    Assert.assertTrue("Expect empty errors", allErrors.isEmpty());
  }

}
