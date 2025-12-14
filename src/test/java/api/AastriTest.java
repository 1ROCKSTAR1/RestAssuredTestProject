package api;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojo.ChangeRequestAastri;
import pojo.ProductDataResponse;
import pojo.CreateRequestAastri;

import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class AastriTest {

    private static final String baseUrl = "http://176.123.167.51:8081";

    Faker faker = new Faker(new Locale("en","AU"));

    String fruit = faker.food().fruit();
    String fruit2 = faker.food().fruit();
    int randomNumber = faker.number().numberBetween(2, 1000);
    int randomNumber2 = faker.number().numberBetween(2, 1000);

    CreateRequestAastri request = new CreateRequestAastri(fruit, randomNumber);

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

        String id = "1";

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

    @Test
    public void putOneByIdTest() {

        String id = "402";

        ChangeRequestAastri requestChange = new ChangeRequestAastri(id,fruit2,randomNumber2);

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(requestChange)
                .when()
                .put(baseUrl + "/product/" + id)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void deleteOneById() {

        String productId = "378";

        RestAssured
                .given()
                .when()
                .delete(baseUrl + "/product/" + productId)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void createChangeAndDeleteOneTest() {

        String productId = RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(baseUrl + "/product")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().response().jsonPath().getString("id");

        System.out.println("Created product ID: " + productId);

        ChangeRequestAastri requestChange = new ChangeRequestAastri(productId,fruit2,randomNumber2);

        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(requestChange)
                .when()
                .put(baseUrl + "/product/" + productId)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(Integer.parseInt(productId)))
                .body("name", equalTo(fruit2))
                .body("amount", equalTo(randomNumber2));

        RestAssured
                .given()
                .when()
                .delete(baseUrl + "/product/" + productId)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
}
