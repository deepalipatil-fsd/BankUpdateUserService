package com.javacloud.updateuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolder {
    private Long id;
    private String accountNumber;
    private String name;
    private String userName;
    private String password;
    private String address;
    private String state;
    private String country;
    private String email;
    private String pan;
    private String contactNo;
    private Date dob;
    private String accountType;
}
