package cst8276.groupassignmentdb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Program {
    private String programId;
    private String name;
    private String areaOfInterest;
    private String campus;
    private String credential;
    private String length;
}
