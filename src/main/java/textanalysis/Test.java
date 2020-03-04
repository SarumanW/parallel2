package textanalysis;

import textanalysis.commonwords.CommonWordsTask;
import textanalysis.wordslength.CharacteristicUtil;
import textanalysis.wordslength.ForkJoinWordLength;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws IOException {
        testWordsLength();
        testCommonWords();
    }

    private static void testWordsLength() throws IOException {
        String fileName = "D:\\parallel\\src\\main\\resources\\hungergames1.txt";

        List<String> textLines = TextUtils.readTextFromFileByLines(fileName);

        Map<String, Integer> wordsLength = ForkJoinWordLength.wordsCount(textLines);

        System.out.println("Expected value: " + CharacteristicUtil.getExpectedValue(wordsLength));
        System.out.println("Dispersion value: " + CharacteristicUtil.getDispersion(wordsLength));
    }

    private static void testCommonWords() throws IOException {
        String firstText = "D:\\parallel\\src\\main\\resources\\hungergames1.txt";
        String secondText = "D:\\parallel\\src\\main\\resources\\hungergames2.txt";

        List<String> commonWords = CommonWordsTask.commonWords(TextUtils.readFileToString(firstText),
                TextUtils.readFileToString(secondText));

        System.out.println(commonWords.size());
    }
}
