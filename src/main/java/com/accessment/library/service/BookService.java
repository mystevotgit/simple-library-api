package com.accessment.library.service;

import com.accessment.library.dto.BookDTO;
import com.accessment.library.dto.LendResponseDTO;
import com.accessment.library.dto.SearchDTO;
import com.accessment.library.dto.UserDTO;
import com.accessment.library.exception.ResourceNotFoundException;
import com.accessment.library.model.Book;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

public interface BookService {
    Set<BookDTO> getAllLibraryBooks();

    List<BookDTO> getBookById(Long bookId)
            throws ResourceNotFoundException;

    BookDTO createBook(@Valid BookDTO bookDetails);

    ResponseEntity<BookDTO> updateBook(Long bookId, BookDTO bookDetails)
            throws ResourceNotFoundException;

    void deleteBook(Long bookId)
            throws ResourceNotFoundException;

    ResponseEntity<Set<BookDTO>> bookSearch(@Valid SearchDTO searchDetails);

    ResponseEntity<LendResponseDTO> lendBook(@Valid Integer copies, Long bookId, Long userId);

    ResponseEntity<Set<UserDTO>> getBookBorrowersById(Long bookId) throws ResourceNotFoundException;
}
