package wordslengthanalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

class CharacteristicUtil {

    static int getExpectedValue(Map<String, Integer> map) {
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

    static double getDispersion(Map<String, Integer> map) {
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
        valuesCount.forEach((k, v) -> res[0] += (Math.sqrt(k - average) * v.intValue() / values.size()));

        return res[0];
    }
}