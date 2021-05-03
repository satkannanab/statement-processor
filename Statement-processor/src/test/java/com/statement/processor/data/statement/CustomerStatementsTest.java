package com.statement.processor.data.statement;

import java.math.BigDecimal;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link CustomerStatements}.
 *
 * @author satheesh.arunachalam
 */
public class CustomerStatementsTest {

  @Test
  public void testBuilder() {

    CustomerStatement customerStatement = CustomerStatement.builder().reference("177666").accountNumber("NL93ABNA0585619023").description("Flowers for Rik")
        .startBalance(new BigDecimal("44.85")).mutation(new BigDecimal("-22.24")).endBalance(new BigDecimal("22.61")).build();
    CustomerStatements actual = CustomerStatements.builder().customerStatments(Collections.singletonList(customerStatement)).build();
    // verify
    Assert.assertEquals("Expect to match the collection", Collections.singletonList(customerStatement), actual.getCustomerStatments());

  }

}
