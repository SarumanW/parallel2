package textanalysis;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

    public static String readFileToString(File file) {
        StringBuilder contentBuilder = new StringBuilder();

        try {
            InputStream is = new FileInputStream(file);

            Reader r = new InputStreamReader(is, StandardCharsets.UTF_8);
            int c;
            while ((c = r.read()) != -1) {
                contentBuilder.append((char) c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return contentBuilder.toString();
    }
}
