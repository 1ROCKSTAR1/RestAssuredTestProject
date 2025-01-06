package streamApi;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.given;

public class CryptoApiTest {

    private static final String BASE_URL = "https://api.kucoin.com/";

    public List<TickerData> getTickers() {
        return given().contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + "api/v1/market/allTickers")
                .then()
                .log().body()
                .extract().jsonPath()
                .getList("data.ticker", TickerData.class);
    }

    @Test
    public void getAllTickers() {

        given().contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + "api/v1/market/allTickers")
                .then()
                .log().body()
                .extract().jsonPath()
                .getList("data.ticker", TickerData.class);
    }


    @Test
    public void test() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + "api/v1/market/allTickers")
                .then()
                .log().body();
    }

    @Test
    public void checkTest() {

        List<TickerData> usdTickers = getTickers()
                .stream()
                .filter(a->a.getSymbol().endsWith("USDT"))
                .toList();

        Assertions.assertTrue(usdTickers
                .stream()
                .allMatch(a->a.getSymbol().endsWith("USDT")));
    }

    @Test
    public void sortHighToLow() {
        List<TickerData> highToLow = getTickers()
                .stream()
                .filter(a->a.getSymbol().endsWith("USDT"))
                .sorted(new Comparator<TickerData>() {
                    @Override
                    public int compare(TickerData o1, TickerData o2) {
                        return o2.getChangeRate().compareTo(o1.getChangeRate());
                    }
                }).toList();

        List<TickerData> top10 = highToLow
                .stream()
                .limit(10)
                .toList();

        Assertions.assertEquals(top10.get(0).getSymbol(),"CLH_USDT");
    }

    @Test
    public void sortLowToHigh() {
        List<TickerData> lowToHigh = getTickers()
                .stream()
                .filter(a->a.getSymbol().endsWith("USDT"))
                .sorted(new TickerComparatorFromLowToHigh())
                .toList();

        List<TickerData> bottomTop10 = lowToHigh
                .stream()
                .limit(10)
                .toList();
        int a = 0;
        Assertions.assertEquals(bottomTop10.get(0).getSymbol(),"FOMO_USDT");

    }

    @Test
    public void sortLowToHigh2() {

        List<TickerData> lowToHigh2 = getTickers()
                .stream()
                .filter(a->a.getSymbol().endsWith("USDT"))
                .sorted(new TickerComparatorFromLowToHigh())
                .limit(10)
                .toList();
    }

    @Test
    public void toMapTest() {
        Map<String, Float> usdMap = new HashMap<>();

        getTickers()
                .stream()
                .forEach(a->usdMap.put(a.getSymbol(),Float.parseFloat(a.getChangeRate())));
    }

    @Test
    public void toMapTest2() {

        List<TickerShort> shortList = new ArrayList<>();

        getTickers()
                .forEach(a->shortList.add(new TickerShort(a.getSymbol(), Float.parseFloat(a.getChangeRate()))));
    }
}