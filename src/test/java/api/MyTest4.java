package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojo.ListDataResponse;
import pojo.RegRequest;
import pojo.SuccessRegResponse;
import pojo.UserDataResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class MyTest4 {

    private final static String BASE_URL = "https://reqres.in";

    @Test
    public void checkAvatarAndIdTest() {

        List<UserDataResponse> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(BASE_URL + "/api/users?page=2")
                .then()
                .log().all()
                .extract()
                .body().jsonPath().getList("data",UserDataResponse.class);

        users
                .stream()
                .forEach(a-> Assertions.assertTrue(a.getAvatar().contains(a.getId().toString())));

        Assertions.assertTrue(users
                .stream()
                .allMatch(a->a.getEmail().endsWith("@reqres.in")));
    }

    @Test
    public void successRegTest() {

        RegRequest user = new RegRequest("eve.holt@reqres.in","pistol");

        SuccessRegResponse succesReg = given()
                .when()
                .contentType(ContentType.JSON)
                .body(user)
                .post(BASE_URL + "/api/register")
                .then()
                .log().all()
                .extract()
                .as(SuccessRegResponse.class);

        Assertions.assertEquals(4,succesReg.getId());
        Assertions.assertEquals("QpwL5tke4Pnpja7X4",succesReg.getToken());
    }

    @Test
    public void unsuccessRegTest() {

        RegRequest userWithoutPassword = new RegRequest("eve.holt@reqres.in", "");

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(userWithoutPassword)
                .post(BASE_URL + "/api/register")
                .then()
                .assertThat()
                .statusCode(400)
                .log().all();
        }

    @Test
    public void sortedYearsTest() {

        List<ListDataResponse> ourList = given()
                .when()
                .contentType(ContentType.JSON)
                .get(BASE_URL + "/api/unknown")
                .then()
                .log().all()
                .extract()
                .body().jsonPath().getList("data",ListDataResponse.class);

        List<Integer> years = ourList
                .stream()
                .map(a->a.getYear())
                .toList();

        List<Integer> sortedYears = years
                .stream()
                .sorted()
                .toList();

        Assertions.assertEquals(sortedYears,years);
    }

    @Test
    public void deleteUserTest() {

        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .delete(BASE_URL + "/api/users/2")
                .then()
                .assertThat()
                .statusCode(204)
                .log().all();
    }

}


