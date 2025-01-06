package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import pojo.Request;


public class MyTest3 {

    private static final String baseUrl = "https://petstore.swagger.io/v2";

    Request requestUser1 = new Request("11","Tom99","Tom", "Adams","tomadams1@example.com", "qwerty1","999888", "0");
    Request requestUser2 = new Request("22","Joe2","Joe", "Roberts","joeroberts1@example.com", "qwerty2","999777", "0");

    @Test
    public void reqTest() {

        RestAssured.given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(requestUser1)
                .post(baseUrl+"/user")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);

        RestAssured.given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(requestUser2)
                .post(baseUrl+"/user")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);



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

}
