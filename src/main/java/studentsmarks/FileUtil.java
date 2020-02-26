package studentsmarks;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FileUtil {
    private static File file = new File("D:\\parallel\\src\\main\\resources\\students.txt");

    void setMark(String studentName, String mark) {
        List<Student> listOfStudents = this.getListOfStudents();
        Student student = listOfStudents.stream()
                .filter(s -> s.getName().equals(studentName))
                .collect(Collectors.toList())
                .get(0);
        student.addMark(mark);

        this.writeToFile(listOfStudents);
    }

    private void writeToFile(List<Student> students) {
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

        try (RandomAccessFile reader = new RandomAccessFile(file, "rw");
             FileLock lock = reader.getChannel().lock()) {

            reader.seek(0);
            reader.write(resultBuilder.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<Student> getListOfStudents() {
        List<Student> students = new ArrayList<>();

        try (RandomAccessFile reader = new RandomAccessFile(file, "r")) {
            String line;

            while ((line = reader.readLine()) != null) {
                students.add(parseLineToObject(line));
            }

        } catch (IOException e) {
            e.printStackTrace();
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
