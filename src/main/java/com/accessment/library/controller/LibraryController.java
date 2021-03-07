package com.accessment.library.controller;

import com.accessment.library.dto.BookDTO;
import com.accessment.library.dto.LendResponseDTO;
import com.accessment.library.dto.SearchDTO;
import com.accessment.library.dto.UserDTO;
import com.accessment.library.exception.ResourceNotFoundException;
import com.accessment.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class LibraryController {
    @Autowired
    private BookService bookService;

    @GetMapping("/book")
    public ResponseEntity<Set<BookDTO>> getAllLibraryBooks() {
        return bookService.getAllLibraryBooks();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable(value = "id") Long bookId) throws ResourceNotFoundException {
        return bookService.getBookById(bookId);
    }

    @GetMapping("/book/{id}/borrowers")
    public ResponseEntity<Set<UserDTO>> getBookBorrowersByBookId(@PathVariable(value = "id") Long bookId) throws ResourceNotFoundException {
        return bookService.getBookBorrowersById(bookId);
    }

    @PostMapping("/book")
    public <T>ResponseEntity<T> createBook(@Valid @RequestBody BookDTO bookDetails) {
        bookService.createBook(bookDetails);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable(value = "id") Long bookId,
                                                      @Valid @RequestBody BookDTO bookDetails) throws ResourceNotFoundException {
        return bookService.updateBook(bookId, bookDetails);
    }

    @DeleteMapping("/book/{id}")
    public <T> ResponseEntity<T> deleteBookById(@PathVariable(value = "id") Long bookId)
            throws ResourceNotFoundException {
        bookService.deleteBook(bookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/book/search")
    public ResponseEntity<Set<BookDTO>> bookSearch(@Valid @RequestBody SearchDTO searchDetails) {
        return bookService.bookSearch(searchDetails);
    }

    @PostMapping("/book/lendbook/{userId}")
    public ResponseEntity<LendResponseDTO> lendBook(@PathVariable(value = "userId") Long userId, @Valid @RequestBody BookDTO bookDetails) {
        return bookService.lendBook(bookDetails, userId);
    }
}
