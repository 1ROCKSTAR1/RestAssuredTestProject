package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import static helpers.Data.BASE_URL;

public abstract class BaseTest {

    protected static RequestSpecification requestSpecification;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://176.123.167.51:8081";

        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }
}