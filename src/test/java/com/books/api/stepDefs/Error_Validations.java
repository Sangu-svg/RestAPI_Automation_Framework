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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Error_Validations {

    public static RestUtil restUtil;
    public static RestAssured request;
    public static Response response;
    public static JsonPath jsonPath;
    public static pojo loadData = new pojo();
    public static String bearer_Token;
    static int retryCount = 0;
    JSONObject requestBody;
    HashMap<String, Object> updatedBookDetails = new HashMap<String, Object>();

    ArrayList<Integer> bookIds = new ArrayList<>();


    @Given("I have a book with details name {string},author {string},published year {int} , summery {string} and bookId {int}")
    public void iHaveABookWithDetailsNameAuthorPublishedYearSummeryAndBookId(String name, String author, int year, String summary, int id) {
        requestBody = new JSONObject();
        requestBody.put("id", id);
        requestBody.put("name", name);
        requestBody.put("author", author);
        requestBody.put("published_year", year);
        requestBody.put("book_summary", summary);
        System.out.println(requestBody);
    }


    @When("I send a POST request to create the book")
    public void iSendAPOSTRequestToCreateTheBook() throws IOException {
        response = restUtil.retry_Mechanism_For_Post_APICall(loadData.getBaseURI(), requestBody.toString(), loadData.getCreateBookAPI(), loadData.getBearerToken());
    }

    @Then("I should receive status code {int}")
    public void iShouldReceiveStatusCode(int arg0) {
        response.then().statusCode(200);
    }

    @Then("i Should see book creation is failed  due to duplicate with {int} error")
    public void iShouldSeeBookCreationIsFailedDueToDuplicateWithError(int statuscode) {
        response.then().statusCode(500);
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "500 Error code for duplicate book creation is shown " + response.statusCode());

    }

    @When("I send a GET request to fetch the book details with Bookid {int}")
    public void iSendAGETRequestToFetchTheBookDeatilsWithId(int bookId) throws IOException {
        response = restUtil.retry_Mechanism_For_Get_APICall(loadData.getBaseURI(), loadData.getCreateBookAPI(), loadData.getBearerToken(), bookId);
    }

    @Then("I should receive status code {int} and {string} in response")
    public void iShouldReceiveStatusCodeAndInResponse(int statuscode, String bookNotFound) {
        response.then().statusCode(statuscode);
        Assert.assertEquals(bookNotFound, response.jsonPath().getString("detail"));
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, "401 Error code for Book id not present or not found " + response.statusCode());
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, " message: " + response.statusCode());

    }
}



