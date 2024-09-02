package API;

import Utils.RandomEmail;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

public class MyTest {

    private static final String baseUrl = "https://dummyapi.io/data/v1";

    String randomEmail = RandomEmail.generateRandomEmail();

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
                .statusCode(200);
    }

    @Test
    public void createUser() {
        String Mic = "{\n" +
                "\"firstName\": \"Michael\",\n" +
                "\"lastName\": \"Willis\",\n" +
                "\"email\": \"" + randomEmail + "\"\n" +
                "}";

        RestAssured .given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .header("app-id","63cf9e4bc876657cabc19378")
                .body(Mic)
                .post(baseUrl+"/user/create")
                .then()
                .log().all()
                .statusCode(200);
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
                .put(baseUrl+"/user/63ef943fc89363d27603d0ae")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void deleteUser() {
        RestAssured .given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                //.body(update)
                .delete(baseUrl+"/user/63ef943fc89363d27603d0ae")
                .then()
                .log().all()
                .statusCode(204);
    }
}
