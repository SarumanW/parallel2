package textanalysis.filesearch;

import textanalysis.TextUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class FileLooper extends RecursiveAction {

    private File[] files;
    private List<String> resultFileNames;
    private static final List<String> themeWords;

    static {
        themeWords = Arrays.asList("IT", "project", "technology", "manager");
    }

    private FileLooper(File[] files, List<String> result) {
        this.files = files;
        this.resultFileNames = result;
    }

    public static void showFiles(File[] files, List<String> result) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        pool.submit(new FileLooper(files, result)).join();
    }

    @Override
    protected void compute() {
        for (File file : files) {
            if (file.isDirectory()) {
                ForkJoinTask.getPool()
                        .invoke(new FileLooper(file.listFiles(), resultFileNames));
            } else {
                checkFileTheme(file);
            }
        }
    }

    private void checkFileTheme(File file) {
        String fileContent = TextUtils.readFileToString(file);

        for (String word : themeWords) {
            if (fileContent.contains(word)) {
                this.resultFileNames.add(file.getName());
            }
        }

    }
}
