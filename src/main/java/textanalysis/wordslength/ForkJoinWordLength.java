package textanalysis.wordslength;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collector;

public class ForkJoinWordLength extends RecursiveTask<Map<String, Integer>> {
    private List<String> lines;
    private static final int THRESHOLD = 100;

    private ForkJoinWordLength(List<String> lines) {
        this.lines = lines;
    }


    public static Map<String, Integer> wordsCount(List<String> lines) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        return pool.submit(new ForkJoinWordLength(lines)).join();
    }

    @Override
    protected Map<String, Integer> compute() {
        Map<String, Integer> wordsLength = new HashMap<>();

        if (lines.size() > THRESHOLD) {
            return ForkJoinTask.invokeAll(divideText())
                    .stream()
                    .map(ForkJoinTask::join)
                    .collect(Collector.of(() -> wordsLength,
                            (result, wordsSizeMap) -> {
                                for (Map.Entry<String, Integer> entry : wordsSizeMap.entrySet()) {
                                    String word = entry.getKey();
                                    Integer size = entry.getValue();

                                    result.putIfAbsent(word, size);
                                }
                            },
                            (result1, result2) -> {
                                for (Map.Entry<String, Integer> entry : result2.entrySet()) {
                                    Integer count = result1.get(entry.getKey());
                                    count = count == null ? 0 : count;
                                    result1.put(entry.getKey(), count + entry.getValue());
                                }

                                return result1;
                            }));
        } else {
            return getWordsLength(lines);
        }
    }

    private Collection<ForkJoinWordLength> divideText() {
        List<ForkJoinWordLength> dividedTasks = new ArrayList<>();

        dividedTasks.add(new ForkJoinWordLength(lines.subList(0, lines.size() / 2)));
        dividedTasks.add(new ForkJoinWordLength(lines.subList(lines.size() / 2, lines.size())));

        return dividedTasks;
    }

    private Map<String, Integer> getWordsLength(List<String> lines) {
        Map<String, Integer> wordsLength = new HashMap<>();

        for (String line : lines) {
            String[] words = line.split("\\W+");

            for (String word : words) {
                if (word.length() > 0) {
                    wordsLength.putIfAbsent(word, word.length());
                }
            }
        }

        return wordsLength;
    }
}
