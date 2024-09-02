package API;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


@TestMethodOrder (MethodOrderer.OrderAnnotation.class)
public class MyTest2 {

    private static final String baseUrl = "https://petstore.swagger.io/v2";

    final String body = "[{\n" +
            "\"id\": 11,\n" +
            "\"username\": \"Tom99\",\n" +
            "\"firstName\": \"Tom\",\n" +
            "\"lastName\": \"Adams\",\n" +
            "\"email\": \"tomadams1@example.com\",\n" +
            "\"password\": \"qwerty1\",\n" +
            "\"phone\": \"999888\",\n" +
            "\"userStatus\": 0\n" +
            "}," +
            "{\n" +
            "\"id\": 22,\n" +
            "\"username\": \"Joe2\",\n" +
            "\"firstName\": \"Joe\",\n" +
            "\"lastName\": \"Roberts\",\n" +
            "\"email\": \"joshroberts1@example.com\",\n" +
            "\"password\": \"qwerty2\",\n" +
            "\"phone\": \"999777\",\n" +
            "\"userStatus\": 0\n" +
            "}]";

    @Test()
    @Order(1)
    public void createTwoUsers() {


        RestAssured.given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(body)
                .post(baseUrl+"/user/createWithList")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(2)
    public void getCreatedUser1() {

        RestAssured.given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .get(baseUrl+"/user/Tom99")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id", Matchers.equalTo(11));
    }

    @Test
    @Order(3)
    public void getCreatedUser2() {

        RestAssured.given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .get(baseUrl+"/user/Joe2")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id", Matchers.equalTo(22));
    }

    @Test
    @Order(4)
    public void loginUser1Test() {
        
        String userSession = RestAssured.given()
                .auth()
                .basic("Tom99", "qwerty1")
                .when()
                .get(baseUrl+"/user/login")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().response().jsonPath().getString("message");

    }

    @Test
    @Order(5)
    public void logoutUser1Test() {

        RestAssured.given()
                .auth()
                .basic("Tom99", "qwerty1")
                .when()
                .get(baseUrl+"/user/logout")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(6)
    public void loginUser2Test() {

        RestAssured.given()
                .auth()
                .basic("Joe2", "qwerty2")
                .when()
                .get(baseUrl+"/user/login")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);

    }

    @Test
    @Order(7)
    public void logoutUser2Test() {

        RestAssured.given()
                .auth()
                .basic("Joe2", "qwerty2")
                .when()
                .get(baseUrl+"/user/logout")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(8)
    public void deleteCreatedUser1() {

        RestAssured.given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .delete(baseUrl+"/user/Tom99")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @Order(9)
    public void deleteCreatedUser2() {

        RestAssured.given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .delete(baseUrl+"/user/Joe2")
                .then()
                .log().all()
                .statusCode(200);
    }

}
