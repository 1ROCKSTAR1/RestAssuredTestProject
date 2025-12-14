package api;

import io.restassured.http.Header;
import org.junit.jupiter.api.Assertions;
import utils.RandomEmail;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class DummyApiSimpleTest {

    private static final String baseUrl = "https://dummyapi.io/data/v1";

    String randomEmail = RandomEmail.generateRandomEmail();

    Header header = new Header("app-id", "63cf9e4bc876657cabc19378");

    private String userId;

    public List<String> getUsers() {
        return given()
                .when()
                .contentType(ContentType.JSON)
                .header(header)
                .get(baseUrl + "/user")
                .then()
                .log().body()
                .extract().jsonPath()
                .getList("data.picture");
    }

    @Test
    public void checkTestJpg() {

        List<String> users = getUsers()
                .stream()
                .toList();

        Assertions.assertTrue(users
                .stream()
                .allMatch(a->a.contains(".jpg")));
    }

    @Test
    public void getAllUsers() {

        RestAssured.given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .header(header)
                .get(baseUrl+"/user")
                .then()
                .log().all()
                .statusCode(200)
                .contentType("application/json")
                .and().time(lessThan(1300L));
    }

    @Test
    public void createUser() {
        String Mic = "{\"firstName\": \"Michael\", \"lastName\": \"Willis\", \"email\": \"" + randomEmail + "\"}";

        userId = RestAssured.given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .header(header)
                .body(Mic)
                .post(baseUrl+"/user/create")
                .then()
                .log().all()
                .statusCode(200)
                .contentType("application/json")
                .and().time(lessThan(1300L))
                .extract().path("id");
    }


    @Test
    public void editUser() {
        String MicEdit = "{\"firstName\": \"MichaellChanged\", \"lastName\": \"WillisChanged\", \"email\": \"" + randomEmail + "\"}";

        RestAssured .given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .header(header)
                .body(MicEdit)
                .put(baseUrl+"/user/" + userId)
                .then()
                .log().all()
                .statusCode(200)
                .contentType("application/json")
                .and().time(lessThan(1300L));
    }

    @Test
    public void deleteUser() {
        RestAssured .given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .delete(baseUrl+"/user/" + userId)
                .then()
                .log().all()
                .statusCode(204)
                .contentType("application/json")
                .and().time(lessThan(1300L));
    }
}
