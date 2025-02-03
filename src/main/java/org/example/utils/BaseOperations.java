package org.example.utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class BaseOperations {
    public static Response getRequest(String baseUrl, String endpoint, Map<String, String> headers) {
        return given()
                .baseUri(baseUrl)
                .auth().basic("admin", "password123")
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static void postRequest(String baseUrl, String endpoint, Map<String, String> headers, String pathToJsonFile) {
        RequestSpecification requestSpecification = given()
                .baseUri(baseUrl)
                .basePath(endpoint)
                .headers(headers)
                .auth().preemptive().basic("admin", "password123")
                .body(pathToJsonFile);
        requestSpecification
                .when()
                .post();

    }

    public static int getAllItemsIds() {
        String baseUrl = "https://restful-booker.herokuapp.com";
        String endpoint = "/booking";
        Map<String, String> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer your-token-here");

        Response response= (Response) given()
                .baseUri(baseUrl)
                .basePath(endpoint)
                .headers(headers)
                .auth().preemptive().basic("admin", "password123")
                .when()
                .get()
                .then()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        List<Integer> bookingIds = jsonPath.getList("bookingid", Integer.class);
        return bookingIds.isEmpty() ? -1 : bookingIds.get(0);
    }
}
