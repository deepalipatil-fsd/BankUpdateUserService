package com.javacloud.updateuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDetails {
    private Long id;
    private String accountNumber;
    private String loanType; //Home, Car, Education, Personal
    private Long loanAmount;
    private double rateOfInterest;
    private double duration;
    private Date applicationDate;
}
