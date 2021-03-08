package com.accessment.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupDTO {
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
}
