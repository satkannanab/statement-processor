
package com.statement.processor.data.statement;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * Holds the list of all customer statements for processes.
 *
 * @author satheesh.arunachalam
 */
@Builder
public class CustomerStatements implements Serializable {

  private static final long serialVersionUID = -4166868754306474288L;

  @Getter
  private List<CustomerStatement> customerStatments;

}
