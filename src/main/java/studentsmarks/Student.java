package studentsmarks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Student {
    private String name;
    private String group;
    private List<String> marks;

    void addMark(String mark) {
        this.marks.add(mark);
    }
}
