package com.onlineschoolbillingsystem.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * This class is the Request Body
 * template for requests to enroll a class
 * to an institute.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollStudentInClassRequest {
    @NotNull(message = "Student ID should not be empty.")
    private long studentId;
}
