package com.jkTech.utilities;

import com.jkTech.configManager.pojo;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class RestUtil {

    public static RestAssured request;
    public static Response response;
    public static JsonPath jsonPath;
    public static pojo loadData;
    static int retryCount = 0;

    public static Response retry_Mechanism_For_Post_APICall(String baseURI, String payLoad,
                                                            String endpoint, String token) {
        request.baseURI = baseURI;
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .body(payLoad)
                .post(endpoint)
                .then().extract().response();
        return response;

    }

    public static Response retry_Mechanism_For_Get_APICall(String baseURI, String endpoint, String token, int bookId) {
        request.baseURI = baseURI;
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", bookId)
                .when()
                .get(endpoint + "{id}")
                .then().extract().response();
        return response;

    }

    public static Response retry_Mechanism_For_PUT_APICall(String baseURI, String payLoad,
                                                           String endpoint, String token, int bookId) {
        request.baseURI = baseURI;
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", bookId)
                .when()
                .body(payLoad)
                .put(endpoint + "{id}")
                .then().extract().response();
        return response;

    }

    public static Response retry_Mechanism_For_DELETE_APICall(String baseURI,
                                                              String endpoint, String token, int bookId) {
        request.baseURI = baseURI;
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", bookId)
                .when()
                .delete(endpoint + "{id}")
                .then().extract().response();
        return response;

    }

    public static Response retry_Mechanism_For_GetALLBooks_APICall(String baseURI, String endpoint, String token) {
        request.baseURI = baseURI;
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(endpoint)
                .then().extract().response();
        return response;

    }
}


