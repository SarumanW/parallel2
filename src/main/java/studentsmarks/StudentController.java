package studentsmarks;

import java.util.List;
import java.util.stream.Collectors;

class StudentController {
    private FileUtil fileUtil;

    StudentController() {
        this.fileUtil = new FileUtil();
    }

    void setMark(String studentName, String mark) {
        List<Student> listOfStudents = this.fileUtil.getListOfStudents();

        Student student = listOfStudents.stream()
                .filter(s -> s.getName().equals(studentName))
                .collect(Collectors.toList())
                .get(0);
        student.addMark(mark);

        this.fileUtil.writeToFile(listOfStudents);
    }

    List<Student> showStudents() {
        return this.fileUtil.showStudents();
    }
}
