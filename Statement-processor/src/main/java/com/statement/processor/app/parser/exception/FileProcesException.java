
package com.statement.processor.app.parser.exception;

/**
 * Exception that handles all the file process related exception.
 *
 * @author satheesh.arunachalam
 */
public class FileProcesException extends RuntimeException {

  private static final long serialVersionUID = 8464728650619863380L;

  public FileProcesException(String message) {
    super(message);
  }

  public FileProcesException(String message, Throwable cause) {
    super(message, cause);
  }

}
