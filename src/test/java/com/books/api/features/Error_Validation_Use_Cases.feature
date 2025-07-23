Feature: Verify error validation uses cases


  @Error_validation_for_duplicate_book_creation
  Scenario: Create a book and validate that creating a duplicate book with the same ID returns a validation error
    Given I have a book with details name "TestAutomation",author "Sangu",published year 2010 , summery "Learn automaton topics" and bookId 214
    When  I send a POST request to create the book
    Then  I should receive status code 200
    And  I have a book with details name "TestAutomation",author "Sangu",published year 2010 , summery "Learn automaton topics" and bookId 214
    When  I send a POST request to create the book
    Then i Should see book creation is failed  due to duplicate with 500 error
    When I delete the book with name "Hobbit" and bookId 214
    Then the books should be deleted successfully with status code 200

  @Error_validation_for_InvalidBookId
  Scenario: Error validation for Invalid or Book id not present in get request
    When  I send a GET request to fetch the book details with Bookid 2354
    Then   I should receive status code 404 and "Book not found" in response

