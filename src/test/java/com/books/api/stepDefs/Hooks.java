package com.books.api.stepDefs;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.jkTech.configManager.pojo;
import com.jkTech.utilities.readData;
import io.restassured.RestAssured;
import io.cucumber.java.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Hooks {

    public static RestAssured request;
    public static Response response;
    public static JsonPath jsonPath;
    public static pojo loadData = new pojo();
    public static String bearer_Token;


    @Before
    public void login_for_Access_Token() throws IOException {

        JSONObject loginPayload = new JSONObject();
        loginPayload.put("id", loadData.getID());
        loginPayload.put("email", loadData.getUserName());
        loginPayload.put("password", loadData.getPassword());
        System.out.println(loginPayload.toString());
        int retryCount = 0;

        if (bearer_Token == null) {
            while (retryCount < 3) {
                try {

                    request.baseURI = loadData.getBaseURI();
                    response = given()
                            .header("Content-Type", "application/json")
                            .when()
                            .body(loginPayload.toString())
                            .post(loadData.getLoginAPI())
                            .then().extract().response();

                    if (response != null && response.statusCode() == 200) {
                        System.out.println("Login is Successful");
                        System.out.println("Response body " + response.body());
                        bearer_Token = response.jsonPath().getString("access_token");
                        System.out.println("Created Bearer token " + bearer_Token);
                        loadData.setBearerToken(bearer_Token);
                        break;
                    } else {
                        System.out.println("Login in is Failed");
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
}






