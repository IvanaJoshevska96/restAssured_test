import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.utils.BaseOperations;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.example.utils.Assertions.assertStatusCode;

public class TestPutItem {

    public static String URL = "https://restful-booker.herokuapp.com";
    static int num = BaseOperations.getAllItemsIds();
    public static String ENDPOINT = "/booking/" + num;
    static Map<String, String> headers = new HashMap<>();
    public static String PATH = "src/test/resources/ItemUpdate.json";

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


    public TestPutItem() throws IOException {
    }

    @Test
    public void testUpdateItem() throws IOException {
        Response response = requestSpecification.when().put();
        assertStatusCode(response, 200);
    }
}
