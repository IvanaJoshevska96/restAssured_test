package org.example.utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Assertions {
    public static void assertStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();

        if (actualStatusCode == expectedStatusCode) {
            System.out.println("Status Code is correct: " + actualStatusCode);
        } else {
            System.out.println("Expected Status Code: " + expectedStatusCode + ", but got: " + actualStatusCode);
        }

        assertThat(actualStatusCode, is(expectedStatusCode));
    }

    public static void assertStatusCodeIsNot(Response response, int notExpectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        if (actualStatusCode != notExpectedStatusCode) {
            System.out.println("Status Code is correct: " + actualStatusCode + " not " + notExpectedStatusCode);
        } else {
            System.out.println("Expected Status Code should not be: " + notExpectedStatusCode);
        }

        assertThat(actualStatusCode, not(notExpectedStatusCode));
    }

    public static void assertResponseTimeIsAcceptable(Response response, long maxTimeMillis) {
        long actualResponseTime = response.getTime();
        if (actualResponseTime <= maxTimeMillis) {
            System.out.println("Response time is acceptable: " + actualResponseTime);
        } else {
            throw new AssertionError("Response took too long: " + actualResponseTime + " ms. Max allowed: " + maxTimeMillis + " ms");
        }
    }

    public static void assertJsonContainsObjects(Response response, int expectedObjectCount, String key) {
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);
        try {
            JsonPath jsonPath = new JsonPath(responseBody);
            List<Object> bookingIds = jsonPath.getList("$");
            System.out.println("Booking IDs List: " + bookingIds);
            int actualCount = bookingIds.size();
            System.out.println("Expected count of 'bookingid': " + expectedObjectCount + ", Actual count: " + actualCount);
            assertThat(actualCount, greaterThanOrEqualTo(expectedObjectCount));

        } catch (Exception e) {
            System.out.println("Error parsing the response body: " + e.getMessage());
        }
    }

}

