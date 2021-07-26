package cst8276.groupassignmentdb.dto;

import lombok.Data;

import java.util.List;

@Data
public class Persons {
    private Person person;
    private Program program;
    private List<Course> courseList;
}
