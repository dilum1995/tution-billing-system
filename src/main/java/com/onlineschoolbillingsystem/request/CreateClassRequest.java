package com.onlineschoolbillingsystem.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * This class is the Request Body
 * template for requests to create a class.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClassRequest {
    @NotNull(message = "Class must have a subject.")
    private String subject;

    private String day;
    private String time;
    private String lectureHall;
    private String instructor;
    private BigDecimal monthlyFee;
}
