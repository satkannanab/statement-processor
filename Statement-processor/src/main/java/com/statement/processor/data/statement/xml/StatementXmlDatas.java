package com.statement.processor.data.statement.xml;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

/**
 * Holds all the statements.
 *
 * @author satheesh.arunachalam
 */
@JacksonXmlRootElement(localName = "records")
@Data
public class StatementXmlDatas {

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "record")
  private List<StatementXmlData> statements;

  public StatementXmlDatas() {
    // nothing to do. empty constructor is required by jackson
  }

  public StatementXmlDatas(List<StatementXmlData> statements) {
    this.statements = statements;
  }
}
