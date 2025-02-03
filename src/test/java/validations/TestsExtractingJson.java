package validations;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsExtractingJson {
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

    @Test
    public void testExtractingJson() {
        Response response = given()
                .spec(requestSpecification)
                .queryParam("page", 2)
                .when()
                .get();
        //Extract json response
        JsonPath responseData = response.jsonPath();
        //keep the value of 'page' field in int var
        int page = responseData.get("page");
        //extract values of emails 1 and 2 in String vars
        String email1 = responseData.get("data[0].email");
        String email2 = responseData.get("data[1].email");
        //size of 'data' array
        int dataLength = responseData.getList("data").size();
        Assertions.assertEquals(page, 2);
        Assertions.assertEquals(email1, "michael.lawson@reqres.in");
        assert email2.contains("lindsay");
        assertTrue(dataLength >= 5);
        Object supportField = responseData.get("support");
        assertNotNull(supportField);
        String supportFieldValue = supportField.toString();
        assertTrue(supportFieldValue.contains("url"));
        assertTrue(supportFieldValue.contains("text"));
        String urlValue=responseData.getString("support.url");
        assertTrue(urlValue.contains("https"));
    }
}
