BookServe QA Framework
This project is an API automation framework built to validate the core functionalities of a FastAPI-based BookStore application. It ensures comprehensive test coverage across CRUD operations, error handling, and authentication flows, with detailed reporting and CI integration.

🔧 Tools & Framework Components
Java (17): Core programming language for BDD cucumber framework
Framework: BDD cucumber
RestAssured: RESTful API automation and validation
TestNG or Junit: Test execution and configuration management
ExtentReports: Generation of interactive HTML test reports
Maven: Project build, dependency, and lifecycle management
GitHub Actions: CI/CD pipeline for automated testing and deployment

📊 Validation Scope
The framework covers the following test scenarios:
Scenario 1: Verify Successful Book Creation(Post Request) and Get(Get Request) by corresponding Id's   --> post request and get request both
Scenario 2: Get all books and verify list of above created books and updated book available in response --> Get all books request
Scenario 3: Verify Update the book details  ---> PUT request for update books
Scenario 4: Verify Delete Book by its Id's --> Delete Request for Book delete
Scenario 5: Create a book and validate that creating a duplicate book with the same ID returns a validation error --> Error validations
Scenario 6: Error validation for Invalid or Book id not present in get request -->-- Error validations


3. Challenges & Solutions
Challenge	Solution
APIs returned 500 instead of 422 for invalid input. those cases are failing	Validated payloads and added assertions to catch backend issues
APIs returned 400 with msg but not mentioned in swagger	Mention all the API response code in swagger
Token reuse across tests	Used a context class to store and inject the token dynamically
Managing test data collisions	Validated payloads and added assertions to catch backend issues
No Delete Api for user, which increases the test users	Provide API for delete users or test cases to maintain first created users.
Cross-class test dependencies	Switched from dependsOnMethods to dependsOnGroups for better control
No Validation for input request. creating with empty email, book name etc	Fix to be done on application

⚙️CI/CD Pipeline
🔁 Trigger:
Runs on every push and pull request to main.

🚀 Steps in CICD process:
Checkout code
Setup Java (Temurin 17)
Build and run tests via Maven
Upload test reports:
Surefire Reports (TestNG)
ExtentReports (HTML)
🧱 Framework Structure
Sample Extent Report

▶️How to Run the Tests
Prerequisites
Java 17+
Maven 3.6+
Git
💻 Clone the repo in the required directory on your PC if you want to run locally:
Clone the repository: git repo shared in mail
Update config.properties with the correct base URL: url=http://localhost:8000
Run the test suite: mvn clean test
View the report: Open the generated HTML report at: test-output/ExtentReport.html

📈 Sample Report
A sample Extent Report is generated after every test run, showing:
Test name and description
Pass/Fail/Skip status
Request/response logs
Assertion results
Sample Extent Report


✍Thanks By
Sangappa k