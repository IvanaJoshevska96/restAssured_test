package org.example.utils;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

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
}
