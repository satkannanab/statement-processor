package com.statement.processor.data.statement.csv;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link StatementCsvData}
 *
 * @author satheesh.arunachalam
 */
public class StatementCsvDataTest {

  @Test
  public void testBuilder() {
    StatementCsvData actual = StatementCsvData.builder().reference("177666").accountNumber("NL93ABNA0585619023").description("Flowers for Rik")
        .startBalance("44.85").mutation("-22.24").endBalance("22.61").build();
    // verify
    Assert.assertEquals("Expect to match the reference number", "177666", actual.getReference());
    Assert.assertEquals("Expect to match the acc number", "NL93ABNA0585619023", actual.getAccountNumber());
    Assert.assertEquals("Expect to match the desc", "Flowers for Rik", actual.getDescription());
    Assert.assertEquals("Expect to match the start balance", "44.85", actual.getStartBalance());
    Assert.assertEquals("Expect to match the mutation", "-22.24", actual.getMutation());
    Assert.assertEquals("Expect to match the end balance", "22.61", actual.getEndBalance());
  }

}
