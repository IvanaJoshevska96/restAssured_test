import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.example.utils.Assertions.assertStatusCode;

public class TestDeleteItem {
    public static String URL = "https://restful-booker.herokuapp.com";
    public static String ENDPOINT = "/booking";
    static Map<String, String> headers = new HashMap<>();

    static {
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer your-token-here");
    }

    RequestSpecification requestSpecificationDelete = given()
            .baseUri(URL)
            .basePath(ENDPOINT + "/808")
            .headers(headers)
            .auth().preemptive().basic("admin", "password123");

    @Test
    public void testDeleteBooking() {
        Response response = requestSpecificationDelete.when().delete();
        assertStatusCode(response, 201);
    }
}
