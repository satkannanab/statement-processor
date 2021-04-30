
package com.statement.processor.data.statement.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Builder;
import lombok.Data;

/**
 * Statment record from xml file.
 *
 * @author satheesh.arunachalam
 */
@Data
@Builder
public class StatementXmlData {
  @JacksonXmlProperty(isAttribute = true)
  private String reference;

  @JacksonXmlProperty
  private String accountNumber;

  @JacksonXmlProperty
  private String description;

  @JacksonXmlProperty
  private String startBalance;

  @JacksonXmlProperty
  private String mutation;

  @JacksonXmlProperty
  private String endBalance;
}
