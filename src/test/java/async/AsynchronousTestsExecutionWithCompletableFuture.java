package async;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.CompletableFuture.runAsync;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsynchronousTestsExecutionWithCompletableFuture {
    public static String URL = "https://restful-booker.herokuapp.com";
    public static String ENDPOINT = "/booking";
    public static String PATH = "src/test/resources/ItemCreate.json";
    static Map<String, String> headers = new HashMap<>();

    static {
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
    }

    static String jsonBody;

    static {
        try {
            jsonBody = new String(Files.readAllBytes(Paths.get(PATH)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON body file", e);
        }
    }

    static RequestSpecification requestSpecification = given()
            .baseUri(URL)
            .basePath(ENDPOINT)
            .headers(headers)
            .auth().preemptive().basic("admin", "password123")
            .body(jsonBody)
            .log().ifValidationFails();

    @Test
    public void testAsynchronous() {
        CompletableFuture<Void> future = runAsync(() -> {
            try {
                Response response = given()
                        .spec(requestSpecification)
                        .when()
                        .post();

                System.out.println("Response: " + response.asString());

                assertEquals(200, response.getStatusCode(), "Status code should be 200");
            } catch (Exception e) {
                throw new RuntimeException("Error during API call: " + e.getMessage(), e);
            }
        });

        future.join();
    }

}
