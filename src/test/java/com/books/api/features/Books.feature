Feature: CRUD operations on Books API

  @CreateBook
  Scenario Outline: Verify Successful Book Creation(Post Request) and Get(Get Request) by corresponding Id's

    Given I have a books with id <id>,name "<name>",author "<author>",year <published_year>,summery "<book_summary>"
    When  I send POST request to create the book
    Then   I should receive status code 200 and the response should contain the created book details
    And  I send a GET request for book with id <id>
    Then   I should receive status code 200 and the response should contain the created book details
    Examples:
      | id | name                  | author | published_year | book_summary                           |
      | 42 | The Alchemist         | Paulo  | 1603           | A journey of self-discovery            |
      | 43 | Hobbit                | Jhon   | 1937           | A hobbit adventure to recover treasure |
      | 45 | Sapiens               | Alex   | 1603           | Psychological thriller                 |
      | 46 | To Kill a Mockingbird | Harper | 2005           | History of humankind                   |

  @GetAllBooks
  Scenario: Get all books and verify list of above created books and updated book available in response
    When I sent get request for all books
    Then i should see all books with created books "The Alchemist","Hobbit","Sapiens","To Kill a Mockingbird" from previous test cases and status code 200

  @UpdateBook
  Scenario: Verify Update the book details
    When I update the book "Hobbit" to name "The Hobbits", republished year to 2010 and book summary to "hobbit adventure" for bookId 46
    Then the book should be updated successfully with status code 200

  @DeleteBooks
  Scenario: Verify Delete Book by its Id
    When I delete the book with name "The Alchemist" and bookId 42
    Then the books should be deleted successfully with status code 200
    And I delete the book with name "To Kill a Mockingbird" and bookId 46
    Then the books should be deleted successfully with status code 200
    When I delete the book with name "Hobbit" and bookId 43
    Then the books should be deleted successfully with status code 200
    And I delete the book with name "Sapiens" and bookId 45
    Then the books should be deleted successfully with status code 200


