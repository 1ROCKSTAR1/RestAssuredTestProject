package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojo.ProductDataResponse;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class AastriTest {

    private static final String baseUrl = "http://localhost:5432";

    @Test
    public void getAllUsersTest() {

        RestAssured.given()
                .when()
                .get(baseUrl+"/product")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .contentType("application/json")
                .and().time(lessThan(1300L));
    }

    @Test
    public void checkIdsOfUsersTest() {

        List<ProductDataResponse> products = given()
                .when()
                .contentType(ContentType.JSON)
                .get(baseUrl + "/product")
                .then()
                .log().all()
                .extract()
                .body().jsonPath().getList("",ProductDataResponse.class);

        Assertions.assertTrue(products
                .stream()
                .allMatch(product -> product.getId() != null));
    }

    @Test
    public void getOneByIdTest() {

        int id = 1;

        RestAssured.given()
                .when()
                .get(baseUrl+"/product/"+id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .contentType("application/json")
                .and().time(lessThan(1300L));
    }
}
