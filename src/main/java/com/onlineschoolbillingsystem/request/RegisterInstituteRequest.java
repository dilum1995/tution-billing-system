package com.onlineschoolbillingsystem.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterInstituteRequest {

    /**
     * This class is the Request Body template for requests to register a new institute.
     */

    @NotEmpty(message = "Email must not be empty.")
    private String email;

    @NotEmpty(message = "Password must not be empty.")
    private char[] password;

    private String address;

    private String contact;

}
