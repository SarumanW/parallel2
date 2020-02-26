package studentsmarks;

import asg.cliche.Command;
import asg.cliche.ShellFactory;

import java.io.IOException;
import java.util.List;

public class StudentsTest {
    private FileUtil fileUtil;

    private StudentsTest() {
        fileUtil = new FileUtil();
    }

    @Command
    public List showStudents() {
        return fileUtil.getListOfStudents();
    }

    @Command
    public String setMark(String student, String mark) {
        fileUtil.setMark(student, mark);
        return "Mark was set";
    }

    public static void main(String[] args) throws IOException {
        ShellFactory.createConsoleShell("registry", "", new StudentsTest())
                .commandLoop();
    }
}
