
package com.statement.processor.data;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * Error response data to handle all the validation and error response from APIs.
 *
 * @author satheesh.arunachalam
 */
@Data
@Builder
public class RestErrorResponse implements Serializable {

  private static final long serialVersionUID = -3113768624720802834L;

  @Getter
  private List<RestResponseMessageData> responseMessages;

  public void addMessage(RestResponseMessageData message) {
    getResponseMessages().add(message);
  }

}
