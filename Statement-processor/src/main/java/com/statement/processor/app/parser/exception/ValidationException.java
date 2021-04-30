
package com.statement.processor.app.parser.exception;

import org.springframework.validation.AbstractErrors;

/**
 * Validation exception to handle all kinds of validation failures.
 *
 * @author satheesh.arunachalam
 */
public class ValidationException extends RuntimeException {

  private static final long serialVersionUID = 981097094399518885L;

  private AbstractErrors errors;

  public ValidationException(String message) {
    super(message);
  }

  public ValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public ValidationException(String message, AbstractErrors errors) {
    super(message);
    this.errors = errors;
  }

  /**
   * @return the errors
   */
  public AbstractErrors getErrors() {
    return errors;
  }
}
