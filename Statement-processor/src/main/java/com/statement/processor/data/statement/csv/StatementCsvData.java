package com.statement.processor.data.statement.csv;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Statement data specifically for CSV
 *
 * @author satheesh.arunachalam
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementCsvData {
  @CsvBindByName(column = "Reference")
  private String reference;

  @CsvBindByName(column = "Account Number")
  private String accountNumber;

  @CsvBindByName(column = "Description")
  private String description;

  @CsvBindByName(column = "Start Balance")
  private String startBalance;

  @CsvBindByName(column = "Mutation")
  private String mutation;

  @CsvBindByName(column = "End Balance")
  private String endBalance;

}
