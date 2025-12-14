package api;

import com.github.javafaker.Faker;
import helpers.AastriApi;
import org.junit.jupiter.api.Test;
import pojo.ChangeRequestAastri;
import pojo.CreateRequestAastri;

import java.util.Locale;

public class ImprovedAastriTests extends BaseTest{

    Faker faker = new Faker(new Locale("en","AU"));
    String fruit = faker.food().fruit();
    int randomNumber = faker.number().numberBetween(2, 1000);

    @Test
    public void addAndDeleteUserTest() {

        CreateRequestAastri requestAastri = new CreateRequestAastri(fruit,randomNumber);
        String productId = AastriApi.addUser(requestAastri);
        AastriApi.deleteOneById(productId);
    }

    @Test
    public void addChangeAndDeleteTest() {

        CreateRequestAastri requestAastri = new CreateRequestAastri(fruit,randomNumber);
        String productId = AastriApi.addUser(requestAastri);

        ChangeRequestAastri requestChange = new ChangeRequestAastri(productId,fruit,randomNumber);
        AastriApi.changeUser(requestChange,productId);
        AastriApi.deleteOneById(productId);
    }
}
