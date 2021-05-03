package com.statement.processor.app.framework.config;

import java.util.Collections;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.AbstractErrors;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.WebRequest;

import com.statement.processor.app.parser.exception.FileProcesException;
import com.statement.processor.app.parser.exception.ValidationException;
import com.statement.processor.data.RestErrorResponse;
import com.statement.processor.data.Severity;

/**
 * Unit test for RestApiErrorHandler.
 *
 * @author satheesh.arunachalam
 */
@RunWith(MockitoJUnitRunner.class)
public class RestApiErrorHandlerTest {

  @Mock
  private MessageSource messageSource;
  @Mock
  private WebRequest request;
  @Mock
  private ObjectError fieldError;
  @Mock
  private AbstractErrors errors;
  @InjectMocks
  private RestApiErrorHandler restApiErrorHandler;

  @Test
  public void testHandleFileProcess() {
    FileProcesException ex = new FileProcesException("Error in file upload");
    // when
    ResponseEntity<Object> handleFileProcess = restApiErrorHandler.handleFileProcess(ex, request);
    // verify
    RestErrorResponse errorResponse = (RestErrorResponse) handleFileProcess.getBody();
    assertFileProcessException(handleFileProcess, errorResponse, HttpStatus.BAD_REQUEST, Severity.ERROR);
    Assert.assertEquals("Expected to match the message from the exception", "Error in file upload", errorResponse.getResponseMessages().get(0).getMessage());
  }

  @Test
  public void testHandleFileProcess_withEmptyMessage() {
    FileProcesException ex = new FileProcesException(null);
    // when
    ResponseEntity<Object> handleFileProcess = restApiErrorHandler.handleFileProcess(ex, request);
    // verify
    RestErrorResponse errorResponse = (RestErrorResponse) handleFileProcess.getBody();
    assertFileProcessException(handleFileProcess, errorResponse, HttpStatus.BAD_REQUEST, Severity.ERROR);
    Assert.assertEquals("Expected to match the default message", "Resource not found for given data.", errorResponse.getResponseMessages().get(0).getMessage());
  }

  @Test
  public void testHandleServerError() {
    RuntimeException ex = new RuntimeException();
    // when
    ResponseEntity<Object> handleFileProcess = restApiErrorHandler.handleServerError(ex, request);
    // verify
    RestErrorResponse errorResponse = (RestErrorResponse) handleFileProcess.getBody();
    Assert.assertEquals("Expected to match the status code", HttpStatus.INTERNAL_SERVER_ERROR, handleFileProcess.getStatusCode());
    Assert.assertEquals("Expected to have 1 response message", 1, errorResponse.getResponseMessages().size());
    Assert.assertEquals("Expected to match the status code", HttpStatus.INTERNAL_SERVER_ERROR, errorResponse.getResponseMessages().get(0).getHttpStatus());
    Assert.assertEquals("Expected to match the message severity", Severity.ERROR, errorResponse.getResponseMessages().get(0).getSeverity());
    Assert.assertEquals("Expected to match the default message", "Unexpected error occured in the server, please contact administrator.",
        errorResponse.getResponseMessages().get(0).getMessage());
  }

  @Test
  public void testHandleValidationError() {
    ValidationException ex = new ValidationException("Validation error", errors);
    Mockito.when(errors.getAllErrors()).thenReturn(Collections.singletonList(fieldError));
    Mockito.when(messageSource.getMessage(fieldError, Locale.getDefault())).thenReturn("Message from property file");
    // when
    ResponseEntity<Object> handleFileProcess = restApiErrorHandler.handleValidationError(ex, request);
    // verify
    RestErrorResponse errorResponse = (RestErrorResponse) handleFileProcess.getBody();
    assertFileProcessException(handleFileProcess, errorResponse, HttpStatus.BAD_REQUEST, Severity.VALIDATION_ERROR);
    Assert.assertEquals("Expected to match the message", "Message from property file", errorResponse.getResponseMessages().get(0).getMessage());
  }

  private void assertFileProcessException(ResponseEntity<Object> handleFileProcess, RestErrorResponse errorResponse, HttpStatus httpStatus, Severity severity) {
    Assert.assertEquals("Expected to match the status code", httpStatus, handleFileProcess.getStatusCode());
    Assert.assertEquals("Expected to have 1 response message", 1, errorResponse.getResponseMessages().size());
    Assert.assertEquals("Expected to match the message severity", httpStatus, errorResponse.getResponseMessages().get(0).getHttpStatus());
    Assert.assertEquals("Expected to match the status code", severity, errorResponse.getResponseMessages().get(0).getSeverity());
  }
}
