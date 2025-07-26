package com.books.api.stepDefs;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.jkTech.configManager.pojo;
import com.jkTech.utilities.RestUtil;
import io.cucumber.java.en.And;
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
import java.util.Map;

public class BooksStepDef {
    public static RestUtil restUtil;
    public static Response response;
    public static JsonPath jsonPath;
    public static pojo loadData = new pojo();
    public static String bearer_Token;
    static int retryCount = 0;
    JSONObject requestBody;
    HashMap<String, Object> created_Book_Details = new HashMap<String, Object>();
    ArrayList<Integer> bookIds = new ArrayList<>();


    @Given("I have a books with id {int},name {string},author {string},year {int},summery {string}")
    public void i_have_a_books_with_id_name_author_year_summery(Integer id, String name, String author, Integer year, String summary) {
        requestBody = new JSONObject();
        requestBody.put("id", id);
        requestBody.put("name", name);
        requestBody.put("author", author);
        requestBody.put("published_year", year);
        requestBody.put("book_summary", summary);
        created_Book_Details.putAll(requestBody);

    }

    @When("I send POST request to create the book")
    public void iSendPOSTRequestToCreateTheBook() {
        while (retryCount < 3) {
            try {
                response = restUtil.retry_Mechanism_For_Post_APICall(this.loadData.getBaseURI(), requestBody.toString(), loadData.getCreateBookAPI(), loadData.getBearerToken());
                if (response != null && response.statusCode() == 200) {
                    System.out.println("create book is Successful");
                    ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Request Body: " + requestBody.toString());
                    ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response Code: " + response.statusCode());
                    ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response Body: " + response.getBody().asPrettyString());
                    break;
                } else {
                    System.out.println("create book is  is Failed");
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

    @Then("I should receive status code {int} and the response should contain the created book details")
    public void iShouldReceiveStatusCode(int statusCode) {

        response.then().statusCode(statusCode);
        Assert.assertEquals(created_Book_Details.get("id"), response.jsonPath().getInt("id"));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Actual BookId " + created_Book_Details.get("id") + " and response BookId in the response " + response.jsonPath().getInt("id") + " are validated");
        Assert.assertEquals(created_Book_Details.get("name"), response.jsonPath().getString("name"));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Actual Book name " + created_Book_Details.get("name") + " and response book name in the response " + response.jsonPath().getString("name") + " are validated");
        Assert.assertEquals(created_Book_Details.get("author"), response.jsonPath().getString("author"));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Actual book author " + created_Book_Details.get("author") + " and response book author  in the response " + response.jsonPath().getString("author") + " are validated");
        Assert.assertEquals(created_Book_Details.get("book_summary"), response.jsonPath().getString("book_summary"));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Actual book_summary " + created_Book_Details.get("book_summary") + " and response book_summary in the response " + response.jsonPath().getString("book_summary") + " are validated");
        Assert.assertEquals(created_Book_Details.get("published_year"), response.jsonPath().getInt("published_year"));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Actual published_year " + created_Book_Details.get("published_year") + " and response published_year in the response " + response.jsonPath().getInt("published_year") + " are validated");
    }

    @And("I send a GET request for book with id {int}")
    public void iSendAGETRequestForBookWithIdId(int bookid) {
        while (retryCount < 3) {
            try {
                response = restUtil.retry_Mechanism_For_Get_APICall(this.loadData.getBaseURI(), loadData.getCreateBookAPI(), loadData.getBearerToken(), bookid);
                if (response != null && response.statusCode() == 200) {
                    System.out.println("Get book is Successful");
                    ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Request Body: " + requestBody.toString());
                    ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response Code: " + response.statusCode());
                    ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "Response Body: " + response.getBody().asPrettyString());
                    break;
                } else {
                    System.out.println("get book by its id is Failed");
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
}
