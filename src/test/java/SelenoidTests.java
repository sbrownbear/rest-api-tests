import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenoidTests {
    /*
        1. Make request to https://selenoid.autotests.cloud/status
        2. Get response
        { total: 20, used: 0, queued: 0, pending: 0, browsers: {
        chrome: { 100.0: { }, 113.0: { }, 114.0: { }, 120.0: { }, 121.0: { },
        122.0: { }, 123.0: { }, 124.0: { }, 125.0: { }, 126.0: { }, 99.0: { } },
        firefox: { 122.0: { }, 123.0: { }, 124.0: { }, 125.0: { } },
        opera: { 106.0: { }, 107.0: { }, 108.0: { }, 109.0: { } } } }
        3. Check total is 20
     */
    @Test
    void checkTotal() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }

    @Test
    void checkTotalWithStatus() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    void checkTotalWithGiven() {
        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    void checkTotalWithLogs() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    void checkTotalWithSomeLogs() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    void checkChromeVersion() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("browsers.chrome", hasKey("100.0"));
    }

    @Test
    void checkResponseBadPractice() {
        String expectedResponseString = "{\"total\":20,\"used\":0,\"queued\":0,\"pending\":0," +
                "\"browsers\":{\"chrome\":{\"100.0\":{},\"113.0\":{},\"114.0\":{},\"120.0\":{},\"121.0\":{}," +
                "\"122.0\":{},\"123.0\":{},\"124.0\":{},\"125.0\":{},\"126.0\":{},\"99.0\":{}}," +
                "\"firefox\":{\"122.0\":{},\"123.0\":{},\"124.0\":{},\"125.0\":{}}," +
                "\"opera\":{\"106.0\":{},\"107.0\":{},\"108.0\":{},\"109.0\":{}}}}";

        Response actualResponse = given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response();
        assertEquals(expectedResponseString, actualResponse.asString());
    }

    @Test
    void checkResponseGoodPractice() {
        Integer expectedTotal = 20;

        Integer actualTotal = given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().path("total");
        assertEquals(expectedTotal, actualTotal);
    }

        /*
        1. Make request to https://selenoid.autotests.cloud/wd/hub/status
        2. Get response { value: { message: "Selenoid 1.11.3 built at 2024-05-25_12:34:40PM", ready: true } }
        3. Check value.ready is true
     */

    @Test
    void checkWdHubNoStatus() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(401);
    }

    @Test
    void checkWdHubStatus() {
        given()
                .log().uri()
                .when()
                .get("https://user1:1234@selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }

    @Test
    void checkWdHubWithAuthStatus() {
        given()
                .log().uri()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }
}
