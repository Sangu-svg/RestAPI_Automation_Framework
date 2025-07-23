package com.books.api.stepDefs;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.jkTech.configManager.pojo;
import com.jkTech.utilities.RestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooksUpdate {

    public static RestUtil restUtil;
    public static RestAssured request;
    public static Response response;
    public static JsonPath jsonPath;
    public static pojo loadData = new pojo();
    public static String bearer_Token;
    static int retryCount = 0;
    JSONObject updatedBook;


    @When("I update the book {string} to name {string}, republished year to {int} and book summary to {string} for bookId {int}")
    public void iUpdateTheBookToNameAndRepublishedYearToAndBookSummeryIs(String Name, String updateBookName, int republished_year, String renamed_title, int bookId) {
        updatedBook = new JSONObject();
        updatedBook.put("id", bookId);
        updatedBook.put("name", updateBookName);
        updatedBook.put("published_year", republished_year);
        updatedBook.put("book_summary", renamed_title);

        while (retryCount < 3) {
            try {
                response = restUtil.retry_Mechanism_For_PUT_APICall(loadData.getBaseURI(), updatedBook.toString(),
                        loadData.getCreateBookAPI(), loadData.getBearerToken(), bookId);
                if (response != null && response.statusCode() == 200) {
                    System.out.println("update book is Successful");
                    ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Request Body: " + updatedBook.toString());
                    ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response Code: " + response.statusCode());
                    ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response Body: " + response.getBody().asPrettyString());
                    break;
                } else {
                    System.out.println("update book is  is Failed");
                }
            } catch (Exception e) {
                System.out.println("Error during API call: " + e.getMessage());
            }
            retryCount++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
        }
        if (response != null) {
            System.out.println("Final Response:\n" + response.prettyPrint());
        } else {
            System.out.println("Request failed after retries.");
        }
    }

    @Then("the book should be updated successfully with status code {int}")
    public void theBookShouldBeUpdatedSuccessfullyWithStatusCode(int statusCode) {

        response.then().statusCode(statusCode);
        Assert.assertEquals(updatedBook.get("id"), response.jsonPath().getInt("id"));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Actual BookId " + updatedBook.get("id") + " and response BookId in the response " + response.jsonPath().getInt("id") + " are validated");
        Assert.assertEquals(updatedBook.get("name"), response.jsonPath().getString("name"));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Actual updated  Book name " + updatedBook.get("name") + " and response updated book name in the response " + response.jsonPath().getString("name") + " are validated");
        Assert.assertEquals(updatedBook.get("book_summary"), response.jsonPath().getString("book_summary"));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Actual updated book_summary " + updatedBook.get("book_summary") + " and response book_summary in the response " + response.jsonPath().getString("book_summary") + " are validated");
        Assert.assertEquals(updatedBook.get("published_year"), response.jsonPath().getInt("published_year"));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Actual updated published_year " + updatedBook.get("published_year") + " and updated published_year in the response " + response.jsonPath().getInt("published_year") + " are validated");
    }

    @When("Remove book name {string} has bookid {int}")
    public void removeBookNameHasBookid(String bookname, int bookId) {
        while (retryCount < 3) {
            try {
                response = restUtil.retry_Mechanism_For_DELETE_APICall(loadData.getBaseURI(),
                        loadData.getCreateBookAPI(), loadData.getBearerToken(), bookId);
                if (response != null && response.statusCode() == 200) {
                    System.out.println("Delete book API is Successful and deleted the book name " + bookname);
                    break;
                } else {
                    System.out.println("Delete book API is Failed");
                }
            } catch (Exception e) {
                System.out.println("Error during API call: " + e.getMessage());
            }
            retryCount++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
        }
        if (response != null) {
            System.out.println("Final Response:\n" + response.prettyPrint());
        } else {
            System.out.println("Request failed after retries.");
        }
    }

    @When("I sent get request for all books")
    public void iSentGetRequestForAllBooks() {
        while (retryCount < 3) {
            try {
                response = restUtil.retry_Mechanism_For_GetALLBooks_APICall(loadData.getBaseURI(), loadData.getCreateBookAPI(), loadData.getBearerToken());
                System.out.println("Bearer token for get call " + loadData.getBearerToken());
                if (response != null && response.statusCode() == 200) {
                    System.out.println("Get All book API is Successful ");
                    ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response Code: " + response.statusCode());
                    ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response Body: " + response.getBody().asPrettyString());
                    break;
                } else {
                    System.out.println("Get All book API is Failed");
                }
            } catch (Exception e) {
                System.out.println("Error during API call: " + e.getMessage());
            }
            retryCount++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
        }
        if (response != null) {
            System.out.println("Final Response:\n" + response.prettyPrint());
        } else {
            System.out.println("Request failed after retries.");
        }
    }

    @Then("i should see all books with created books {string},{string},{string},{string} from previous test cases and status code {int}")
    public void iShouldSeeAllBooksWithCreatedBooksFromPreviousTestCasesAndStatusCode(String Book1, String Book2, String Book3, String Book4, int statusCode) {

        response.then().statusCode(statusCode);
        List<String> bookNames = response.jsonPath().getList("name");
        Assert.assertTrue(bookNames.size() > 0, "Book list should not be empty");
        Assert.assertTrue(!bookNames.isEmpty() == true, "response has lis tof books and no empty");
        for (String bookName : bookNames) {
            ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "All books from get all booksAPI  : " + bookName);
        }
        Assert.assertTrue(bookNames.contains(Book1));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response has Book  : " + Book1);
        Assert.assertTrue(bookNames.contains(Book2));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response has Book  : " + Book2);
        Assert.assertTrue(bookNames.contains(Book3));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response has Book  : " + Book3);
        Assert.assertTrue(bookNames.contains(Book4));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response has Book  : " + Book4);
    }

    @When("I delete the book with name {string} and bookId {int}")
    public void iDeleteTheBookWithNameAndBookId(String book1, int bookId) {
        while (retryCount < 3) {
            try {
                response = restUtil.retry_Mechanism_For_DELETE_APICall(loadData.getBaseURI(),
                        loadData.getCreateBookAPI(), loadData.getBearerToken(), bookId);
                if (response != null && response.statusCode() == 200) {
                    System.out.println("Get All book API is Successful ");
                    System.out.println("Bearer token for get all books " + loadData.getBearerToken());
                    ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response Code: " + response.statusCode());
                    ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response Body: " + response.getBody().asPrettyString());
                    break;
                } else {
                    System.out.println("Get All book API is Failed");
                }
            } catch (Exception e) {
                System.out.println("Error during API call: " + e.getMessage());
            }
            retryCount++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
        }
        if (response != null) {
            System.out.println("Final Response:\n" + response.prettyPrint());
        } else {
            System.out.println("Request failed after retries.");
        }

    }

    @Then("the books should be deleted successfully with status code {int}")
    public void theBooksShouldBeDeletedSuccessfullyWithStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
        Assert.assertEquals("Book deleted successfully", response.jsonPath().getString("message"));
    }


}