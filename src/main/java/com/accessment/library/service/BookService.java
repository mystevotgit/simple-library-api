package com.accessment.library.service;

import com.accessment.library.dto.BookDTO;
import com.accessment.library.dto.LendResponseDTO;
import com.accessment.library.dto.SearchDTO;
import com.accessment.library.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.Set;

public interface BookService {
    ResponseEntity<Set<BookDTO>> getAllLibraryBooks();

    ResponseEntity<BookDTO> getBookById(Long bookId)
            throws ResourceNotFoundException;

    ResponseEntity<BookDTO> createBook(@Valid BookDTO bookDetails);

    ResponseEntity<BookDTO> updateBook(Long bookId, BookDTO bookDetails)
            throws ResourceNotFoundException;

    void deleteBook(Long bookId)
            throws ResourceNotFoundException;

    ResponseEntity<Set<BookDTO>> bookSearch(@Valid SearchDTO keywords);

    ResponseEntity<LendResponseDTO> lendBook(@Valid BookDTO bookDetails, Long userId);
}
