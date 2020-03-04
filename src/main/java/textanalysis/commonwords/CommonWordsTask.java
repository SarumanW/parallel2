package textanalysis.commonwords;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class CommonWordsTask extends RecursiveTask<List<String>> {
    private String firstText;
    private String secondText;

    private static final int THRESHOLD = 200;

    private CommonWordsTask(String firstText, String secondText) {
        this.firstText = firstText;
        this.secondText = secondText;
    }


    public static List<String> commonWords(String firstText, String secondText) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        return pool.submit(new CommonWordsTask(firstText, secondText)).join();
    }

    @Override
    protected List<String> compute() {
        if (firstText.length() > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubTasks())
                    .stream()
                    .map(ForkJoinTask::join)
                    .flatMap(Collection::stream)
                    .distinct()
                    .collect(Collectors.toList());
        } else {
            return getCommonWords(firstText, secondText);
        }
    }

    private Collection<CommonWordsTask> createSubTasks() {
        List<CommonWordsTask> dividedTasks = new ArrayList<>();

        dividedTasks.add(new CommonWordsTask(firstText.substring(0, firstText.length() / 2),
                secondText.substring(0, secondText.length() / 2)));
        dividedTasks.add(new CommonWordsTask(firstText.substring(firstText.length() / 2),
                secondText.substring(secondText.length() / 2)));

        return dividedTasks;
    }

    private List<String> getCommonWords(String firstText, String secondText) {
        List<String> commonWords = new ArrayList<>();

        String[] firstTextWords = firstText.split("\\W+");

        for (String w : firstTextWords) {
            if (secondText.contains(w)) {
                commonWords.add(w);
            }
        }

        return commonWords;
    }
}
