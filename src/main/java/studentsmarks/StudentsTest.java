package studentsmarks;

import asg.cliche.Command;
import asg.cliche.ShellFactory;

import java.io.IOException;
import java.util.List;

public class StudentsTest {
    private StudentController studentController;

    private StudentsTest() {
        studentController = new StudentController();
    }

    @Command
    public List showStudents() {
        return studentController.showStudents();
    }

    @Command
    public String setMark(String student, String mark) {
        studentController.setMark(student, mark);
        return "Mark was set";
    }

    public static void main(String[] args) throws IOException {
        ShellFactory.createConsoleShell("registry", "", new StudentsTest())
                .commandLoop();
    }
}
