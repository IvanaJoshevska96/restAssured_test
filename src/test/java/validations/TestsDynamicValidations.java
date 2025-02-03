package validations;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestsDynamicValidations {
    public static String URL = "https://reqres.in";
    public static String ENDPOINT = "/api/users";
    static Map<String, String> headers = new HashMap<>();

    static {
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer your-token-here");
    }

    RequestSpecification requestSpecification = given()
            .baseUri(URL)
            .basePath(ENDPOINT);
    // .queryParam("page", 2);

    @Test
    public void verifyThatStatusCode() {
        Response response = given()
                .spec(requestSpecification)
                .get();
        response
                .then()
                .statusCode(200)
                .body(notNullValue())
                .body("size()", greaterThanOrEqualTo(6));
    }

    @Test
    public void verifyPagination() {
        Response response = given()
                .queryParam("page", 2)
                .spec(requestSpecification)
                .when()
                .get();
        response
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("per_page", equalTo(6))
                .body("per_page", lessThan(10))
                .body("per_page", greaterThan(5));
    }
}
