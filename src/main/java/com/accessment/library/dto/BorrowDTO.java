package com.accessment.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BorrowDTO {
    private long id;
    private long userId;
    private long bookId;
    private int copies;
}
