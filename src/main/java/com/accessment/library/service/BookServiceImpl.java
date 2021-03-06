package com.accessment.library.service;

import com.accessment.library.dto.BookDTO;
import com.accessment.library.dto.BorrowDTO;
import com.accessment.library.dto.LendResponseDTO;
import com.accessment.library.dto.SearchDTO;
import com.accessment.library.exception.ResourceNotFoundException;
import com.accessment.library.model.Book;
import com.accessment.library.model.Borrow;
import com.accessment.library.repository.BookRepository;
import com.accessment.library.repository.LendRepository;
import com.accessment.library.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    BookRepository bookRepository;

    @Autowired
    LendRepository lendRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private void setModelMappingStrategy() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @Override
    public ResponseEntity<Set<BookDTO>> getAllLibraryBooks() {
        setModelMappingStrategy();
        Set<BookDTO> books =  bookRepository.findAll().stream().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toSet());
        return ResponseEntity.ok(books);
    }

    @Override
    public ResponseEntity<BookDTO> getBookById(Long bookId) throws ResourceNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));
        setModelMappingStrategy();
        BookDTO bookDTO = modelMapper
                .map(book, BookDTO.class);
        return ResponseEntity.ok().body(bookDTO);
    }

    @Override
    public ResponseEntity<BookDTO> createBook(@Valid BookDTO bookDetails) {
        setModelMappingStrategy();
        Book book = new Book();
        book.setAuthor(bookDetails.getAuthor());
        book.setTitle(bookDetails.getTitle());
        book.setCategory(bookDetails.getCategory());
        book.setCopies(bookDetails.getCopies());
        Book savedBook = bookRepository.save(book);
        BookDTO bookDTO = modelMapper.map(savedBook, BookDTO.class);
        return ResponseEntity.ok(bookDTO);
    }

    @Override
    public ResponseEntity<BookDTO> updateBook(Long bookId, BookDTO bookDetails) throws ResourceNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("book not found for this id :: " + bookId));
        setModelMappingStrategy();
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setCategory(bookDetails.getCategory());
        book.setCopies(bookDetails.getCopies());
        final Book updatedBook = bookRepository.save(book);
        BookDTO bookDTO = modelMapper.map(updatedBook, BookDTO.class);
        return ResponseEntity.ok(bookDTO);
    }

    @Override
    public void deleteBook(Long bookId) throws ResourceNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("book not found for this id :: " + bookId));
        bookRepository.delete(book);
        return;
    }

    @Override
    public ResponseEntity<Set<BookDTO>> bookSearch(@Valid SearchDTO keywords) {
        return null;
    }

    @Override
    public ResponseEntity<LendResponseDTO> lendBook(@Valid BookDTO bookDetails, Long userId) {
        setModelMappingStrategy();
        Book book = modelMapper.map(bookDetails, Book.class);
        Borrow borrower = new Borrow();
        borrower.setBook(book);
        borrower.setUser(userRepository.findById(userId).get());
        borrower.setCopies(bookDetails.getCopies());
        LendResponseDTO lendResponseDTO = new LendResponseDTO();
        if(book.getCopies() > 0) {
            book.setCopies(book.getCopies() - 1);
            bookRepository.save(book);
            Borrow recordedBorrow = lendRepository.save(borrower);
            lendResponseDTO.setBorrowerId(userId);
            lendResponseDTO.setBorrowerName(recordedBorrow.getUser().getFirstName() + " " + recordedBorrow.getUser().getLastName());
            Set<BorrowDTO> booksBorrowed = recordedBorrow.getUser().getBooksBorrowed().stream().map(borrow -> modelMapper
                    .map(borrow, BorrowDTO.class)).collect(Collectors.toSet());
            lendResponseDTO.setBooksBorrowed(booksBorrowed);
        }
        return ResponseEntity.ok(lendResponseDTO);
    }
}