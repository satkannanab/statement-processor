package com.statement.processor.data;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

/**
 * Unit test for {@link RestResponseMessageData}.
 *
 * @author satheesh.arunachalam
 */
public class RestResponseMessageDataTest {

  @Test
  public void testBuilder() {

    RestResponseMessageData data = RestResponseMessageData.builder().httpStatus(HttpStatus.ACCEPTED).severity(Severity.ERROR).message("message-1").build();
    // verify
    Assert.assertEquals("Expected to match the http status", HttpStatus.ACCEPTED, data.getHttpStatus());
    Assert.assertEquals("Expected to match the severity", Severity.ERROR, data.getSeverity());
    Assert.assertEquals("Expected to match the message", "message-1", data.getMessage());
  }

}
