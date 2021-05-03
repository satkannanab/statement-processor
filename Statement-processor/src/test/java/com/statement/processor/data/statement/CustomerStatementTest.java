package com.statement.processor.data.statement;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link CustomerStatementBuilder}.
 *
 * @author satheesh.arunachalam
 */
public class CustomerStatementTest {

  @Test
  public void testBuilder() {

    CustomerStatement customerStatement = CustomerStatement.builder().reference("177666").accountNumber("NL93ABNA0585619023").description("Flowers for Rik")
        .startBalance(new BigDecimal("44.85")).mutation(new BigDecimal("-22.24")).endBalance(new BigDecimal("22.61")).build();
    // verify
    Assert.assertEquals("Expect to match the reference number", "177666", customerStatement.getReference());
    Assert.assertEquals("Expect to match the acc number", "NL93ABNA0585619023", customerStatement.getAccountNumber());
    Assert.assertEquals("Expect to match the desc", "Flowers for Rik", customerStatement.getDescription());
    Assert.assertEquals("Expect to match the start balance", new BigDecimal("44.85"), customerStatement.getStartBalance());
    Assert.assertEquals("Expect to match the mutation", new BigDecimal("-22.24"), customerStatement.getMutation());
    Assert.assertEquals("Expect to match the end balance", new BigDecimal("22.61"), customerStatement.getEndBalance());

  }

}
