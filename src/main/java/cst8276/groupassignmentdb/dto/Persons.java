package cst8276.groupassignmentdb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persons {
    private Person person;
    private Program program;
    private List<Course> courseList;
}
