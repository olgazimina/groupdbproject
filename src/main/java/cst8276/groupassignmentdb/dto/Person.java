package cst8276.groupassignmentdb.dto;

import lombok.Data;

@Data
public class Person {
    private Long personId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private String created;
}
