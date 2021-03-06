package com.accessment.library.dto;

import java.util.Map;
import java.util.Set;

public class LendResponseDTO {
    private long borrowerId;
    private String borrowerName;
    private Set<Map<String, Integer>> booksBorrowed;
}
