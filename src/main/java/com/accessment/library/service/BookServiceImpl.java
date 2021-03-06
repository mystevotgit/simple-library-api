package com.accessment.library.service;

import com.accessment.library.dto.*;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LendRepository lendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private void setModelMappingStrategy() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @Override
    public Set<BookDTO> getAllLibraryBooks() {
        setModelMappingStrategy();
        Set<BookDTO> books =  bookRepository.findAll().stream().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toSet());
        return books;
    }

    @Override
    public List<BookDTO> getBookById(Long bookId) throws ResourceNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));
        setModelMappingStrategy();
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        List<BookDTO> returnList = new ArrayList<>();
        returnList.add(bookDTO);
        return returnList;
    }

    @Override
    public BookDTO createBook(@Valid BookDTO bookDetails) {
        setModelMappingStrategy();
        Book book = new Book();
        if(bookRepository.findAll().stream().filter(currentBook -> currentBook.getTitle().equals(bookDetails.getTitle())).count() < 1) {
            book.setAuthor(bookDetails.getAuthor());
            book.setTitle(bookDetails.getTitle());
            book.setCategory(bookDetails.getCategory());
            book.setCopies(bookDetails.getCopies());
            Book savedBook =  bookRepository.save(book);
            BookDTO bookDTO = modelMapper.map(savedBook, BookDTO.class);
            return bookDTO;
        }
        return new BookDTO();
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
    public ResponseEntity<Set<BookDTO>> bookSearch(@Valid SearchDTO searchDetails) {
        Set<String> keywords = new HashSet<>(Arrays.asList(searchDetails.getKeywords().replaceAll(",", "").split(" ")));
        setModelMappingStrategy();
        Set<BookDTO> books =  bookRepository.findAll().stream().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toSet());
        Set<BookDTO> searchResult = new HashSet<>();
        keywords.forEach(keyword -> {
            searchResult.addAll(books.stream().filter(bookDTO -> bookDTO.toString().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toSet()));
        });
        return ResponseEntity.ok(searchResult);
    }

    @Override
    public ResponseEntity<LendResponseDTO> lendBook(@Valid BookRequestDTO bookRequestDTO, Long bookId, Long userId) {
        setModelMappingStrategy();
        Book book = bookRepository.findById(bookId).get();
        Borrow borrower = new Borrow();
        borrower.setBook(book);
        borrower.setUser(userRepository.findById(userId).get());
        borrower.setCopies(bookRequestDTO.getCopies());
        LendResponseDTO lendResponseDTO = new LendResponseDTO();
        if(book.getCopies() > bookRequestDTO.getCopies()) {
            book.setCopies(book.getCopies() - bookRequestDTO.getCopies());
            bookRepository.save(book);
            Borrow recordedBorrow = lendRepository.save(borrower);
            lendResponseDTO.setBorrowerId(userId);
            lendResponseDTO.setBorrowerName(recordedBorrow.getUser().getFirstName() + " " + recordedBorrow.getUser().getLastName());
            BorrowedBookDTO bookBorrowed = new BorrowedBookDTO();
            bookBorrowed.setTitle(book.getTitle());
            bookBorrowed.setAuthor(book.getAuthor());
            bookBorrowed.setCopies(bookRequestDTO.getCopies());
            lendResponseDTO.setBookBorrowed(bookBorrowed);
        }
        return ResponseEntity.ok(lendResponseDTO);
    }

    @Override
    public ResponseEntity<Set<UserDTO>> getBookBorrowersById(Long bookId) throws ResourceNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("book not found for this id :: " + bookId));
        setModelMappingStrategy();
        Set<UserDTO> borrowers = book.getBorrowers().stream().map(borrow -> modelMapper.map(borrow, UserDTO.class)).collect(Collectors.toSet());
        return ResponseEntity.ok(borrowers);
    }
}
