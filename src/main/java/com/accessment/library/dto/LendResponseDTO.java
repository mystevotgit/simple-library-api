package com.accessment.library.dto;

import com.accessment.library.model.Borrow;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class LendResponseDTO {
    private long borrowerId;
    private String borrowerName;
    private BorrowedBookDTO bookBorrowed;
}
