package tests;

import model.LoginBodyModel;
import model.LoginResponseModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ReqresinExtendedTests {
    /*
        1. Make POST request to https://reqres.in/api/login
            with body: { "email": "eve.holt@reqres.in", "password": "cityslicka" }
        2. Get response { "token": "QpwL5tke4Pnpja7X4" }
        3. Check token is QpwL5tke4Pnpja7X4
     */

    @Test
    void checkTokenLoginWithoutModelTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";
        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void checkTokenLoginTest() {
        LoginBodyModel loginBody = new LoginBodyModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseModel loginResponse = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(loginBody)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);

//        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());
        assertThat(loginResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");

    }

}

