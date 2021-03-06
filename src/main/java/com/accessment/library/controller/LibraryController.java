package com.accessment.library.controller;

import com.accessment.library.dto.BookDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class LibraryController {
    @GetMapping("/books")
    public Set<BookDTO> getAllLibraryBooks() {
        System.out.println("<<<<<<<<<<<<<<<< The app is working >>>>>>>>>>>>>>>>");
        return new HashSet<>();
    }
}
