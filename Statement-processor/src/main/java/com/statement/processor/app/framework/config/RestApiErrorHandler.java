package com.statement.processor.app.framework.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.statement.processor.app.parser.exception.FileProcesException;
import com.statement.processor.app.parser.exception.ValidationException;
import com.statement.processor.data.RestErrorResponse;
import com.statement.processor.data.RestResponseMessageData;
import com.statement.processor.data.Severity;

/**
 * Spring controller advice that handles the error and exceptions from application and creates meaning ful message as rest response.
 *
 * @author satheesh.arunachalam
 */
@ControllerAdvice
public class RestApiErrorHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  /**
   * Handles the exception when an {@link FileProcesException} and creates a response message with proper message and http status code as 400.
   *
   * @param ex exception of type FileProcesException.
   * @param request of type WebRequest
   * @return ResponseEntity with http response code and message.
   */
  @ExceptionHandler(value = FileProcesException.class)
  @ResponseBody
  protected ResponseEntity<Object> handleFileProcess(FileProcesException ex, WebRequest request) {
    String bodyOfResponse = ex.getMessage() != null ? ex.getMessage() : "Resource not found for given data.";
    RestErrorResponse responseBody = buildErrorResponse(buildBadRequest(bodyOfResponse));
    return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  /**
   * Handles the exception when an {@link ValidationException} and creates a response message with proper message and http status code as 400.
   *
   * @param ex exception of type ValidationException.
   * @param request of type WebRequest
   * @return ResponseEntity with http response code and message.
   */
  @ExceptionHandler(value = ValidationException.class)
  @ResponseBody
  protected ResponseEntity<Object> handleValidationError(ValidationException ex, WebRequest request) {
    List<RestResponseMessageData> responseMessages = createErrorResponse(ex.getErrors());
    RestErrorResponse responseBody =
        responseMessages.isEmpty() ? buildErrorResponse(Collections.singletonList(buildValidationFailure("Validation error while processing the request")))
            : buildErrorResponse(responseMessages);
    return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  /**
   * Handles any kind of Exception and creates a response message with proper message and http status code as 500.
   *
   * @param ex exception of type Exception.
   * @param request of type WebRequest
   * @return ResponseEntity with http response code and message.
   */
  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  protected ResponseEntity<Object> handleServerError(Exception ex, WebRequest request) {
    RestResponseMessageData responseBody = RestResponseMessageData.builder().severity(Severity.ERROR)
        .message("Unexpected error occured in the server, please contact administrator.").httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();

    return handleExceptionInternal(ex, buildErrorResponse(Collections.singletonList(responseBody)), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
        request);
  }

  private List<RestResponseMessageData> createErrorResponse(Errors result) {
    List<RestResponseMessageData> messages = new ArrayList<>();

    for (ObjectError fieldError : result.getAllErrors()) {
      String localizedErrorMessage = messageSource.getMessage(fieldError, Locale.getDefault());
      messages.add(buildValidationFailure(localizedErrorMessage));
    }
    return messages;
  }

  private RestErrorResponse buildErrorResponse(List<RestResponseMessageData> responseMessages) {
    return RestErrorResponse.builder().responseMessages(responseMessages).build();
  }

  private List<RestResponseMessageData> buildBadRequest(String bodyOfResponse) {
    RestResponseMessageData responseBody =
        RestResponseMessageData.builder().severity(Severity.ERROR).message(bodyOfResponse).httpStatus(HttpStatus.BAD_REQUEST).build();
    return Collections.singletonList(responseBody);
  }

  private RestResponseMessageData buildValidationFailure(String bodyOfResponse) {
    return RestResponseMessageData.builder().severity(Severity.VALIDATION_ERROR).message(bodyOfResponse).httpStatus(HttpStatus.BAD_REQUEST).build();
  }
}
