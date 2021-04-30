
package com.statement.processor.data.statement.validator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.statement.processor.data.statement.CustomerStatement;
import com.statement.processor.data.statement.CustomerStatements;

/**
 * Spring validator to validate the CustomerStatements.
 *
 * @author satheesh.arunachalam
 */
@Component
public class CustomerStatementValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CustomerStatements.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CustomerStatements statements = (CustomerStatements) target;

    List<String> duplicateReferencesValues = statements.getCustomerStatments().stream()//
        .map(CustomerStatement::getReference)//
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))//
        .entrySet()//
        .stream()//
        .filter(e -> e.getValue() > 1)//
        .map(Entry::getKey)//
        .collect(Collectors.toList());

    if (!duplicateReferencesValues.isEmpty()) {
      errors.reject("stmt.error.duplicate_references", duplicateReferencesValues.toArray(new String[duplicateReferencesValues.size()]),
          "Reference numbers are duplicated");
    }
    List<String> incorrectBalanceReferences = statements.getCustomerStatments().stream()//
        .filter(this::balanceDoesntMatch)//
        .map(CustomerStatement::getReference)//
        .collect(Collectors.toList());

    if (!incorrectBalanceReferences.isEmpty()) {
      errors.reject("stmt.error.incorrect_balances", incorrectBalanceReferences.toArray(new String[incorrectBalanceReferences.size()]),
          "Reference numbers have incorrect balances.");
    }
  }

  private boolean balanceDoesntMatch(CustomerStatement statement) {
    BigDecimal endBalance = statement.getStartBalance().add(statement.getMutation());
    return !endBalance.equals(statement.getEndBalance());
  }

}
