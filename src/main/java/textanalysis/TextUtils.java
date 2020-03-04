package textanalysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TextUtils {
    public static List<String> readTextFromFileByLines(String path) throws IOException {
        List<String> textLines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream.forEach(textLines::add);
        }
        return textLines;
    }

    public static String readFileToString(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        Files.lines(Paths.get(filePath))
                .forEach(s -> contentBuilder.append(s).append("\n"));

        return contentBuilder.toString();
    }
}
