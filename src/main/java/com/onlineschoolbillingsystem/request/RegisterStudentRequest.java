package com.onlineschoolbillingsystem.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * This class is the Request Body
 * template for requests to enroll a student
 * to an institute.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterStudentRequest {
    @NotNull(message = "Student name should not be empty.")
    private String name;
    private String address;
    private String contact;
    private String gender;
}
