package authorization;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.example.utils.Assertions.assertStatusCode;

public class TestsOAuthAuth {
    public static String URL = "https://restful-booker.herokuapp.com";
    public static String ENDPOINT = "/booking";
    public static String PATH = "src/test/resources/ItemCreate.json";
    static Map<String, String> headers = new HashMap<>();
    String accessToken = "abcde";

    static {
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
    }

    String jsonBody = new String(Files.readAllBytes(Paths.get(PATH)));
    RequestSpecification requestSpecification = given()
            .baseUri(URL)
            .basePath(ENDPOINT)
            .headers(headers)
            //OAuth2 Authentication
            .auth()
            .oauth2(accessToken)
            .body(jsonBody)
            .log().ifValidationFails();

    public TestsOAuthAuth() throws IOException {
    }


    @Test
    public void testCreateBooking() {
        Response response = requestSpecification.when().post();
        assertStatusCode(response, 200);
    }

    @Test
    public void testAnotherCreateBooking() {
        //Using spec() with REST-Assured to apply a predefined request specification to a REST API request.
        given()
                .spec(requestSpecification)
                .when()
                .post()
                .then()
                .statusCode(200)
                .log().ifValidationFails();
    }
}
