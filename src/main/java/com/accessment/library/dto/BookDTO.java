package com.accessment.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookDTO {
    private long id;
    private String title;
    private String author;
    private String category;
    private int copies;
}
