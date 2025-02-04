package org.example.utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
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

        Response response = (Response) given()
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

    public static boolean isItemExistInTheArray(RequestSpecification requestSpecification, String array, String itemKey, String itemValue) {
        int page = 1;
        boolean found = false;
        while (true) {
            Response response = given()
                    .queryParam("page", page)
                    .spec(requestSpecification)
                    .when()
                    .get();
            //extract all first names into a list
            List<Map<String, Object>> values = response.jsonPath().getList(array);
            if (values != null) {
                //iterate through each value and check for the itemKey and itemValue
                for (Map<String, Object> record : values) {
                    if (record.containsKey(itemKey) && record.get(itemKey).equals(itemValue)) {
                        System.out.println("Found '" + itemValue + "' under '" + itemKey + "' on page " + page);
                        found = true;
                        break;
                    }
                }
            }

            if (found) break;

            Integer totalPages = response.jsonPath().getInt("total_pages");
            if (page >= totalPages) break;
            page++;
        }

        if (!found) {
            System.out.println("Item '" + itemValue + "' not found under '" + itemKey + "' in any page.");
        }

        return found;
    }

}
