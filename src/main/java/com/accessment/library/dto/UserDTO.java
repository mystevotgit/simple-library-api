package com.accessment.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String emailId;
    private Set<BorrowDTO> booksBorrowed;
    private LocalDateTime timeStamp;
    private LocalDateTime created;
}
