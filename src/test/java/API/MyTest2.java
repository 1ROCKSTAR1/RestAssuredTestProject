package API;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import pojo.Request;


@TestMethodOrder (MethodOrderer.OrderAnnotation.class)
public class MyTest2 {

    private static final String baseUrl = "https://petstore.swagger.io/v2";

    Request postCreateTheFirstUser = new Request("11","Tom99","Tom", "Adams", "tomadams1@example.com", "qwerty1", "999888", "0");
    Request postCreateTheSecondUser = new Request("22","Joe2","Joe", "Roberts", "joshroberts1@example.com", "qwerty2", "999777", "0");

    @Test()
    @Order(1)
    public void createTheFirstUser() {


        RestAssured.given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(postCreateTheFirstUser)
                .post(baseUrl+"/user")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test()
    @Order(2)
    public void createTheSecondUser() {


        RestAssured.given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(postCreateTheSecondUser)
                .post(baseUrl+"/user")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(3)
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
    @Order(4)
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
    @Order(5)
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
    @Order(6)
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
    @Order(7)
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
    @Order(8)
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
    @Order(9)
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
    @Order(10)
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
