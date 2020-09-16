package com.onlineschoolbillingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyBill {
    private long studentId;
    private String studentName;
    private Map<String, BigDecimal> feeBreakdown;
    private BigDecimal grandTotal;
}
