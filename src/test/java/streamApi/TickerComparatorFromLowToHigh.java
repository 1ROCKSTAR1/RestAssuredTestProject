package streamApi;

import java.util.Comparator;

public class TickerComparatorFromLowToHigh implements Comparator<TickerData> {
    @Override
    public int compare(TickerData o1, TickerData o2) {
        float result = Float.compare(Float.parseFloat(o2.getChangeRate()), Float.parseFloat(o1.getChangeRate()));
        return (int) result;
    }
}
