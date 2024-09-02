package Utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomEmail {

    public static String generateRandomEmail() {
        String randomString = RandomStringUtils.randomAlphabetic(10);
        return randomString + "@example.com";
    }
}
