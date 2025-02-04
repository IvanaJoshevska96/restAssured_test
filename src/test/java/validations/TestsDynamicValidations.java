package validations;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.utils.TestResultLogger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.example.utils.BaseOperations.isItemExistInTheArray;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(TestResultLogger.class)
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

    @Test
    public void isPersonExistWithIndex() {
        Response response = given()
                .queryParam("page", 1)
                .spec(requestSpecification)
                .when()
                .get();
        response
                .then()
                .body("data[0].first_name", equalTo("George"));
    }

    @Test
    public void isPersonExist() {
        Response response = given()
                .queryParam("page", 1)
                .spec(requestSpecification)
                .when()
                .get();
        response
                .then()
                //search for any object data where first_name = George, notNull ensures that at least one exist
                .body("data.find { it.first_name == 'George' }", notNullValue());
    }

    @Test
    public void isPersonExist2Way() {
        Response response = given()
                .queryParam("page", 1)
                .spec(requestSpecification)
                .when()
                .get();
        //extract all first names into a list
        List<String> firstNames = response.jsonPath().getList("data.first_name");
        assertThat(firstNames, hasItem("George"));
    }

    @Test
    public void findPersonIterateThroughPages() {
        boolean isFound = isItemExistInTheArray(requestSpecification, "data", "first_name", "Michael");
        assertThat(isFound, is(true));
    }
}
