package wordslengthanalysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) throws IOException {
        String fileName = "D:\\parallel\\src\\main\\resources\\book.txt";

        List<String> textLines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(textLines::add);
        }

        Map<String, Integer> wordsLength = ForkJoinWordLength.wordsCount(textLines);

        System.out.println("Expected value: " + CharacteristicUtil.getExpectedValue(wordsLength));
        System.out.println("Dispersion value: " + CharacteristicUtil.getDispersion(wordsLength));
    }
}
