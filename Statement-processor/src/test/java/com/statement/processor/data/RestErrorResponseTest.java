package com.statement.processor.data;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

/**
 * Unit test for {@link RestErrorResponse}.
 *
 * @author satheesh.arunachalam
 */
public class RestErrorResponseTest {

  @Test
  public void testBuilder() {
    RestResponseMessageData data = RestResponseMessageData.builder().httpStatus(HttpStatus.ACCEPTED).severity(Severity.ERROR).message("message-1").build();

    RestErrorResponse actual = RestErrorResponse.builder().responseMessages(Collections.singletonList(data)).build();
    // verify
    Assert.assertEquals("Expect to match size as 1", 1, actual.getResponseMessages().size());
    Assert.assertEquals("Expected to match the http status", HttpStatus.ACCEPTED, actual.getResponseMessages().get(0).getHttpStatus());
    Assert.assertEquals("Expected to match the severity", Severity.ERROR, actual.getResponseMessages().get(0).getSeverity());
    Assert.assertEquals("Expected to match the message", "message-1", actual.getResponseMessages().get(0).getMessage());
  }

}
