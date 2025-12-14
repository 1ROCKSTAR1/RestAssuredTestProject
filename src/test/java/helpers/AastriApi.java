package helpers;

import api.BaseTest;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pojo.ChangeRequestAastri;
import pojo.CreateRequestAastri;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AastriApi extends BaseTest {

    @Step("Добавить товар")
    public static String addUser(CreateRequestAastri requestAastri) {
        return given()
                .spec(requestSpecification)
                .log().all()
                .contentType(ContentType.JSON)
                .body(requestAastri)
                .when()
                .post("/product")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id",notNullValue())
                .extract().response().jsonPath().getString("id");
    }

    @Step
    public static void changeUser(ChangeRequestAastri changeRequestAastri, String productId) {
        RestAssured
                .given()
                .spec(requestSpecification)
                .log().all()
                .contentType(ContentType.JSON)
                .body(changeRequestAastri)
                .when()
                .put("/product/" + productId)
                .then()
                .log().all()
                .assertThat()
                .body("id",notNullValue())
                .statusCode(200);
    }

    @Step
    public static void deleteOneById(String productId) {

        RestAssured
                .given()
                .spec(requestSpecification)
                .when()
                .delete("/product/" + productId)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
}
