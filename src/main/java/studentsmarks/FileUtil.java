package studentsmarks;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FileUtil {
    private static File file = new File("D:\\parallel2\\src\\main\\resources\\students.txt");

    void writeToFile(List<Student> students) {
        StringBuilder resultBuilder = new StringBuilder();

        for (Student student : students) {
            resultBuilder.append(student.getName());
            resultBuilder.append(" ");

            resultBuilder.append(student.getGroup());
            resultBuilder.append(" ");

            for (String mark : student.getMarks()) {
                resultBuilder.append(mark);
                resultBuilder.append(" ");
            }

            resultBuilder.append(System.getProperty("line.separator"));
        }

        try (RandomAccessFile reader = new RandomAccessFile(file, "rw")) {

            FileLock lock1;

            while (true) {
                lock1 = reader.getChannel().tryLock();

                if (lock1 != null) {
                    break;
                } else {
                    System.out.println("Someone is writing to the file, operation is unavailable!");
                }
            }

            TimeUnit.SECONDS.sleep(10);
            reader.seek(0);
            reader.write(resultBuilder.toString().getBytes());

            lock1.release();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    List<Student> getListOfStudents() {
        boolean readSuccessful = false;

        List<Student> students = new ArrayList<>();

        while (true) {
            if (readSuccessful) {
                break;
            } else {
                try (RandomAccessFile reader = new RandomAccessFile(file, "r")) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        students.add(parseLineToObject(line));
                    }

                    readSuccessful = true;
                } catch (IOException e) {
                    System.out.println("Someone is writing to the file, operation is unavailable!");

                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }

        return students;
    }

    List<Student> showStudents() {
        List<Student> students = new ArrayList<>();

        try (RandomAccessFile reader = new RandomAccessFile(file, "r")) {
            String line;
            while ((line = reader.readLine()) != null) {
                students.add(parseLineToObject(line));
            }

        } catch (IOException e) {
            System.out.println("Someone is writing to the file, operation is unavailable!");
        }

        return students;
    }

    private Student parseLineToObject(String line) {
        String[] parts = line.split(" ");
        String[] marks = new String[parts.length - 2];
        System.arraycopy(parts, 2, marks, 0, parts.length - 2);
        return new Student(parts[0], parts[1], Stream.of(marks).collect(Collectors.toList()));
    }
}
