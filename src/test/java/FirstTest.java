import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.example.utils.Assertions.*;
import static org.example.utils.BaseOperations.getRequest;
import static org.hamcrest.Matchers.*;

public class FirstTest {
    public static String URL = "https://restful-booker.herokuapp.com";
    public static String ENDPOINT = "/booking";
    static Map<String, String> headers = new HashMap<>();

    static {
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer your-token-here");
    }


    @Test
    public void firstTest() {
        getRequest(URL, ENDPOINT, headers);
        Response response = getRequest(URL, ENDPOINT, headers);
        response.then().assertThat().statusCode(200);
        response.then().assertThat().statusCode(not(201));
        response.then().assertThat().time(lessThan(5000L));
        response.then().header("Content-Type", "application/json; charset=utf-8");
        response.then().headers("Content-Type", notNullValue());
        response.then().body("bookingid", is(notNullValue()));
        response.then().body("size()", greaterThan(4));
        response.then().body("bookingid", hasItem(1081));
        response.then().body("", hasItem(Map.of("bookingid", 1081)));
        response.then().log().body();
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
}
