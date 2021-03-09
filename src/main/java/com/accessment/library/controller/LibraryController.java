package com.accessment.library.controller;

import com.accessment.library.dto.*;
import com.accessment.library.exception.ResourceNotFoundException;
import com.accessment.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class LibraryController {
    @Autowired
    private BookService bookService;

    @GetMapping("/book")
    public ResponseEntity<Set<BookDTO>> getAllLibraryBooks() {
        return ResponseEntity.ok(bookService.getAllLibraryBooks());
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<List<BookDTO>> getBookById(
            @PathVariable(value = "id") Long bookId
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @GetMapping("/book/{id}/borrowers")
    public ResponseEntity<Set<UserDTO>> getBookBorrowersByBookId(
            @PathVariable(value = "id") Long bookId
    ) throws ResourceNotFoundException {
        return bookService.getBookBorrowersById(bookId);
    }

    @PostMapping("/book")
    public ResponseEntity<String> createBook(@Valid @RequestBody BookDTO bookDetails) {
        BookDTO bookDTO = bookService.createBook(bookDetails);
        if (bookDTO.getTitle() == null) {
            return ResponseEntity.ok(bookDetails.getTitle() + " has previously been added to the library.");
        }
        return ResponseEntity.ok(bookDetails.getTitle() + " was added to the library successfully");
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable(value = "id") Long bookId,
                                                  @Valid @RequestBody BookDTO bookDetails
    ) throws ResourceNotFoundException {
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

    @PostMapping("/book/lendbook/{bookId}/{userId}")
    public ResponseEntity<LendResponseDTO> lendBook(@PathVariable(value = "bookId") Long bookId, @PathVariable(value = "userId") Long userId, @Valid @RequestBody BookRequestDTO bookRequestDTO) {
        return bookService.lendBook(bookRequestDTO, bookId, userId);
    }
}
