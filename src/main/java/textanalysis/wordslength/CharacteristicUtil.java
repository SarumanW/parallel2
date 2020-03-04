package textanalysis.wordslength;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class CharacteristicUtil {

    public static int getExpectedValue(Map<String, Integer> map) {
        List<Integer> values = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            values.add(entry.getValue());
        }

        Map<Integer, Long> valuesCount = values.stream()
                .collect(groupingBy(Function.identity(), Collectors.counting()));

        AtomicInteger res = new AtomicInteger();
        valuesCount.forEach((k, v) -> res.addAndGet(k * v.intValue() / values.size()));

        return res.get();
    }

    public static double getDispersion(Map<String, Integer> map) {
        List<Integer> values = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            values.add(entry.getValue());
        }

        double average = values.stream()
                .mapToInt(v -> v)
                .average()
                .orElse(0.0);

        Map<Integer, Long> valuesCount = values.stream()
                .collect(groupingBy(Function.identity(), Collectors.counting()));

        final double[] res = {0};
        valuesCount.forEach((k, v) -> res[0] += (Math.pow(k - average, 2) * v / values.size()));

        return res[0];
    }
}
