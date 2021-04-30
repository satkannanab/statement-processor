
package com.statement.processor.data;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

/**
 * Error response data to handle all the validation and error response from APIs.
 *
 * @author satheesh.arunachalam
 */
@Data
@Builder
public class RestResponseMessageData implements Serializable {
  private static final long serialVersionUID = 3420729853054420906L;

  private String message;
  private Severity severity;
  private HttpStatus httpStatus;

}
