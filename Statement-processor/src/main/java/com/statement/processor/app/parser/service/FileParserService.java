
package com.statement.processor.app.parser.service;

import java.io.InputStream;
import java.util.List;

import com.statement.processor.data.statement.CustomerStatement;

/**
 * File processor service which parses the given input file.
 *
 * @author satheesh.arunachalam
 */
public interface FileParserService {

  List<CustomerStatement> fileProessor(InputStream inputStream);

}
