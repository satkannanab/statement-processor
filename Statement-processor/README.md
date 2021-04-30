# Customer Statement Processor



### Repository url to clone
https://github.com/satkannanab/statement-processor.git

### Run the application in command line using maven
mvn spring-boot:run

### Build the package
mvn clean package

### How to process the files?
The application can be accessed at http://localhost:8080/

Below url can be used to process the file.
http://localhost:8080/api/statements/process and upload the csv or xml file as 'file' attribute in POST body

### Using integration test
StatementProcessorResourceControllerIntegrationTest can be used to test the files in IDE environment. Already a validation error flow and happy flow is covered for both csv and xml file format.

### Running application in IDE environnment.
StatementProcessorApplication is used to run the spring boot application.


