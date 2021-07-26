package cst8276.groupassignmentdb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Course {
    private String courseId;
    private String name;
    private String instructor;
    private String credits;
}
