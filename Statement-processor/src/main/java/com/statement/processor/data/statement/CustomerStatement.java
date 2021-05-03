package com.statement.processor.data.statement;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

/**
 * Holds the data for customer statement.
 *
 * @author satheesh.arunachalam
 */
@Data
@Builder
public class CustomerStatement implements Serializable {

  private static final long serialVersionUID = 4915207231830328444L;

  private String reference;
  private String accountNumber;
  private String description;
  private BigDecimal startBalance;
  private BigDecimal mutation;
  private BigDecimal endBalance;

}
