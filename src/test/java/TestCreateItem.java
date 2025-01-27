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

public class TestCreateItem {
    public static String URL = "https://restful-booker.herokuapp.com";
    public static String ENDPOINT = "/booking";
    public static String PATH = "src/test/resources/ItemCreate.json";
    static Map<String, String> headers = new HashMap<>();

    static {
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer your-token-here");
    }

    String jsonBody = new String(Files.readAllBytes(Paths.get(PATH)));
    RequestSpecification requestSpecification = given()
            .baseUri(URL)
            .basePath(ENDPOINT)
            .headers(headers)
            .auth().preemptive().basic("admin", "password123")
            .body(jsonBody);

    public TestCreateItem() throws IOException {
    }

    @Test
    public void testCreateBooking() {
        Response response = requestSpecification.when().post();
        assertStatusCode(response, 200);
    }


}
