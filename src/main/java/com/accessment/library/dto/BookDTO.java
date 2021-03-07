package com.accessment.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class BookDTO {
    private long id;
    private String title;
    private String author;
    private String category;
    private int copies;
    private Set<BorrowDTO> borrowers;
}
