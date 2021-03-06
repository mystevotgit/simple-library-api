package com.accessment.library.controller;

import com.accessment.library.dto.BookDTO;
import com.accessment.library.dto.LendResponseDTO;
import com.accessment.library.dto.SearchDTO;
import com.accessment.library.exception.ResourceNotFoundException;
import com.accessment.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class LibraryController {
    @Autowired
    BookService bookService;

    @GetMapping("/book")
    public Set<BookDTO> getAllLibraryBooks() {
        return bookService.getAllLibraryBooks();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable(value = "id") Long bookId) throws ResourceNotFoundException {
        return bookService.getBookById(bookId);
    }

    @PostMapping("/book")
    public BookDTO createBook(@Valid @RequestBody BookDTO bookDetails) {
        return bookService.createBook(bookDetails);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable(value = "id") Long bookId,
                                                      @Valid @RequestBody BookDTO bookDetails) throws ResourceNotFoundException {
        return bookService.updateBook(bookId, bookDetails);
    }

    @DeleteMapping("/book/{id}")
    public Map<String, Boolean> deleteBookById(@PathVariable(value = "id") Long bookId)
            throws ResourceNotFoundException {
        return bookService.deleteBook(bookId);
    }

    @PostMapping("/book/search")
    public Set<BookDTO> bookSearch(@Valid @RequestBody SearchDTO searchDetails) {
        return bookService.bookSearch(searchDetails);
    }

    @PostMapping("/book/lendbook/{bookId}/{userId}")
    public LendResponseDTO lendBook(@PathVariable(value = "bookId") Long bookId, @PathVariable(value = "userId") Long userId) {
        return bookService.lendBook(bookId, userId);
    }
}
