package textanalysis;

import textanalysis.commonwords.CommonWordsTask;
import textanalysis.filesearch.FileLooper;
import textanalysis.wordslength.CharacteristicUtil;
import textanalysis.wordslength.ForkJoinWordLength;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws IOException {
        //testWordsLength();
        testCommonWords();
        //testFilesSearch();
    }

    private static void testWordsLength() throws IOException {
        String fileName = "D:\\parallel\\src\\main\\resources\\hungergames1.txt";

        List<String> textLines = TextUtils.readTextFromFileByLines(fileName);

        //long startTime = System.nanoTime();
        Map<String, Integer> wordsLength = ForkJoinWordLength.wordsCount(textLines);
        //long endTime = System.nanoTime();

        //long duration = (endTime - startTime) / 1000000;
        //System.out.println(duration);

        System.out.println("Expected value: " + CharacteristicUtil.getExpectedValue(wordsLength));
        System.out.println("Dispersion value: " + CharacteristicUtil.getDispersion(wordsLength));
    }

    private static void testCommonWords() throws IOException {
        String firstText = "D:\\parallel\\src\\main\\resources\\hungergames1.txt";
        String secondText = "D:\\parallel\\src\\main\\resources\\hungergames2.txt";

        List<String> commonWords = CommonWordsTask.commonWords(TextUtils.readTextFromFileByLines(firstText),
                TextUtils.readTextFromFileByLines(secondText));

        System.out.println(commonWords.size());
    }

    private static void testFilesSearch() {
        String fileName = "D:\\forkjoinfolder";

        List<String> filesNames = new ArrayList<>();
        FileLooper.showFiles(new File(fileName).listFiles(), filesNames);

        for(String f : filesNames) {
            System.out.println(f);
        }

        System.out.println(filesNames.size());
    }
}
