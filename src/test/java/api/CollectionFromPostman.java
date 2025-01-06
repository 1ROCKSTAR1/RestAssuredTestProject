package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import pojo.RequestForPostman;
import utils.RandomEmail;

public class CollectionFromPostman {

    final String baseUrl = "https://dummyapi.io/";
    final String apiKey = "app-id";
    final String keyValue = "62e6f10d11c051e2d93a6723";

    String randomEmail = RandomEmail.generateRandomEmail();

    RequestForPostman requestForPostmanUser1 = new RequestForPostman("Tom", "Willis", randomEmail);

    RequestForPostman requestForPostmanUser1Changed = new RequestForPostman("Nash","Bridges");


    @Test
    public void postmanTest() {

        RestAssured.given()
                .header(apiKey,keyValue)
                .log().all()
                .when()
                .get(baseUrl + "data/v1/user")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);

        RestAssured.given()
                .header(apiKey,keyValue)
                .log().all()
                .when()
                .get(baseUrl + "data/v1/user?page=1&limit=10")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);

        String idUser = RestAssured.given()
                .header(apiKey,keyValue)
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(requestForPostmanUser1)
                .post(baseUrl + "data/v1/user/create")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("firstName", Matchers.equalTo("Tom"))
                .extract().response().jsonPath().get("id");

        RestAssured.given()
                .header(apiKey,keyValue)
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(requestForPostmanUser1Changed)
                .put(baseUrl+"data/v1/user/"+idUser)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);


        RestAssured.given()
                .header(apiKey,keyValue)
                .log().all()
                .when()
                .get(baseUrl+"data/v1/user/"+idUser)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("firstName", Matchers.equalTo("Nash"))
                .body("lastName", Matchers.equalTo("Bridges"));


        RestAssured.given()
                .header(apiKey,keyValue)
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "\"text\": \"my trip\",\n"+
                        "\"image\": \"https://randomuser.me/api/portraits/men/58.jpg\",\n"+
                        "\"likes\": \"20\",\n"+
                        "\"tags\": \"qa\",\n"+
                        "\"owner\": \"" + idUser + "\"\n" +
                        "}")
                .post(baseUrl + "data/v1/post/create")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);


        RestAssured.given()
                .header(apiKey,keyValue)
                .log().all()
                .when()
                .get(baseUrl + "user/" + idUser + "/post")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("text", Matchers.equalTo("my trip"));
    }
}
