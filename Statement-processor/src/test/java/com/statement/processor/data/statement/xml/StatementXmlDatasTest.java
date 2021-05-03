package com.statement.processor.data.statement.xml;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link StatementXmlDatas}.
 *
 * @author satheesh.arunachalam
 */
public class StatementXmlDatasTest {
  @Test
  public void testBuilder() {

    StatementXmlData statementXmlData = StatementXmlData.builder().reference("177666").accountNumber("NL93ABNA0585619023").description("Flowers for Rik")
        .startBalance("44.85").mutation("-22.24").endBalance("22.61").build();
    StatementXmlDatas actual = new StatementXmlDatas(Collections.singletonList(statementXmlData));
    // verify
    Assert.assertEquals("Expect to match the collection", Collections.singletonList(statementXmlData), actual.getStatements());

  }

}
