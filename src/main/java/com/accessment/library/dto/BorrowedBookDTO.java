package com.accessment.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BorrowedBookDTO {
    private String title;
    private String author;
    private int copies;
}

