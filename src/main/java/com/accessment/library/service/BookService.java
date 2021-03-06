package com.accessment.library.service;

import com.accessment.library.dto.BookDTO;
import com.accessment.library.dto.LendResponseDTO;
import com.accessment.library.dto.SearchDTO;
import com.accessment.library.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

public interface BookService {
    Set<BookDTO> getAllLibraryBooks();

    ResponseEntity<BookDTO> getBookById(Long bookId)
            throws ResourceNotFoundException;

    BookDTO createBook(@Valid BookDTO bookDetails);

    ResponseEntity<BookDTO> updateBook(Long bookId, BookDTO bookDetails)
            throws ResourceNotFoundException;

    Map<String, Boolean> deleteBook(Long bookId)
            throws ResourceNotFoundException;

    Set<BookDTO> bookSearch(@Valid SearchDTO keywords);

    LendResponseDTO lendBook(Long bookId, Long userId);
}
