package api;

import org.junit.jupiter.api.Assertions;
import utils.RandomEmail;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class MyTest {

    private static final String baseUrl = "https://dummyapi.io/data/v1";

    String randomEmail = RandomEmail.generateRandomEmail();

    private String userId;

    public List<String> getUsers() {
        return given()
                .when()
                .contentType(ContentType.JSON)
                .header("app-id","63cf9e4bc876657cabc19378")
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
                .header("app-id","63cf9e4bc876657cabc19378")
                .get(baseUrl+"/user")
                .then()
                .log().all()
                .statusCode(200)
                .contentType("application/json")
                .and().time(lessThan(1300L));
    }

    @Test
    public void createUser() {
        String Mic = "{\n" +
                "\"firstName\": \"Michael\",\n" +
                "\"lastName\": \"Willis\",\n" +
                "\"email\": \"" + randomEmail + "\"\n" +
                "}";

        userId = RestAssured.given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .header("app-id","63cf9e4bc876657cabc19378")
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
        String MicEdit = "{\n" +
                "\"firstName\": \"MichaellChanged\",\n" +
                "\"lastName\": \"WillisChanged\",\n" +
                "\"email\": \"" + randomEmail + "\"\n" +
                "}";

        RestAssured .given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .header("app-id","63cf9e4bc876657cabc19378")
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
