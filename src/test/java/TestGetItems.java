import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.utils.Assertions.*;
import static org.example.utils.BaseOperations.getRequest;

public class TestGetItems {
    public static String URL = "https://restful-booker.herokuapp.com";
    public static String ENDPOINT = "/booking";
    static Map<String, String> headers = new HashMap<>();

    static {
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer your-token-here");
    }

    @Test
    public void verifyThatStatusCode() {
        Response response = getRequest(URL, ENDPOINT, headers);
        assertStatusCode(response, 200);
        assertStatusCodeIsNot(response, 201);
    }

    @Test
    public void verifyResponseTimeNeeded() {
        Response response = getRequest(URL, ENDPOINT, headers);
        assertResponseTimeIsAcceptable(response, 200);
    }

    @Test
    public void verifyNumberOfObjectsInResponse() {
        Response response = getRequest(URL, ENDPOINT, headers);
        // response.then().log().body();
        assertJsonContainsObjects(response, 20, "bookingid");
    }
    @Test
    public void getAllIds() {
        Response response = getRequest(URL, ENDPOINT, headers);
       //  response.then().log().body();
        JsonPath jsonPath = response.jsonPath();
        List<Integer> bookingIds = jsonPath.getList("bookingid", Integer.class);

        // Print the list of booking IDs
        System.out.println(bookingIds);
    }
}
