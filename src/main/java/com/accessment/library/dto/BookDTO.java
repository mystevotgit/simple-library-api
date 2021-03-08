package com.accessment.library.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDTO {
    private long id;
    private String title;
    private String author;
    private String category;
    private int copies;
}
