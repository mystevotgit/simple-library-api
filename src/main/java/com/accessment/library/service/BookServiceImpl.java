package com.accessment.library.service;

import com.accessment.library.dto.BookDTO;
import com.accessment.library.dto.LendResponseDTO;
import com.accessment.library.dto.SearchDTO;
import com.accessment.library.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService{

    @Override
    public Set<BookDTO> getAllLibraryBooks() {
        return null;
    }

    @Override
    public ResponseEntity<BookDTO> getBookById(Long bookId) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public BookDTO createBook(@Valid BookDTO bookDetails) {
        return null;
    }

    @Override
    public ResponseEntity<BookDTO> updateBook(Long bookId, BookDTO bookDetails) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public Map<String, Boolean> deleteBook(Long bookId) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public Set<BookDTO> bookSearch(@Valid SearchDTO keywords) {
        return null;
    }

    @Override
    public LendResponseDTO lendBook(Long bookId, Long userId) {
        return null;
    }
}
