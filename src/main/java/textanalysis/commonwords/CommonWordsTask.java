package textanalysis.commonwords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class CommonWordsTask extends RecursiveTask<List<String>> {
    private List<String> firstText;
    private List<String> secondText;

    private static final int THRESHOLD = 200;

    private CommonWordsTask(List<String> firstText, List<String> secondText) {
        this.firstText = firstText;
        this.secondText = secondText;
    }


    public static List<String> commonWords(List<String> firstText, List<String> secondText) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        return pool.submit(new CommonWordsTask(firstText, secondText)).join();
    }

    @Override
    protected List<String> compute() {
        if (firstText.size() > THRESHOLD) {
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

        dividedTasks.add(new CommonWordsTask(firstText.subList(0, firstText.size() / 2),
                secondText.subList(0, secondText.size() / 2)));
        dividedTasks.add(new CommonWordsTask(firstText.subList(firstText.size() / 2, firstText.size()),
                secondText.subList(secondText.size() / 2, secondText.size())));

        return dividedTasks;
    }

    private List<String> getCommonWords(List<String> firstText, List<String> secondText) {
        List<String> firstTextWords = new ArrayList<>();
        List<String> secondTextWords = new ArrayList<>();

        for (String s : firstText) {
            firstTextWords.addAll(Arrays.asList(s.split("\\W+")));
        }

        for (String s : secondText) {
            secondTextWords.addAll(Arrays.asList(s.split("\\W+")));
        }

        firstTextWords.retainAll(secondTextWords);

        return firstTextWords;
    }
}
