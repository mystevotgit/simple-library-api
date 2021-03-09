package com.accessment.library.controller;

import com.accessment.library.dto.BookDTO;
import com.accessment.library.dto.BookRequestDTO;
import com.accessment.library.dto.SearchDTO;
import com.accessment.library.model.Book;
import com.accessment.library.model.Borrow;
import com.accessment.library.model.User;
import com.accessment.library.repository.BookRepository;
import com.accessment.library.repository.LendRepository;
import com.accessment.library.repository.UserRepository;
import com.accessment.library.utils.JWTUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static com.accessment.library.util.JsonString.asJsonString;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LendRepository lendRepository;

    @Autowired
    private JWTUtils jwtUtils;

    final Book book = new Book();
    final User newUser = new User();
    String token = "";

    @BeforeAll
    void setUp() {
        seedData();
        token = jwtUtils.generateToken(createUser());
    }

    @Test
    @DisplayName("get book by id successfully")
    public void getBookById() throws Exception {

        getAndVerifyBookById(status().isOk(), book.getId(), token);
    }

    @Test
    @DisplayName("get all books successfully")
    public void getAllBooks() throws Exception {
        getAndVerifyAllBooks(status().isOk(), token);
    }

    @Test
    @DisplayName("get all book borrowers successfully")
    public void getBooksBorrowers() throws Exception {
        getAndVerifyBorrowedBooks(status().isOk(), book.getId(), token);
    }

    @Test
    @DisplayName("create a book successfully ")
    public void createBook() throws Exception {
        final BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor("author");
        bookDTO.setCategory("category");
        bookDTO.setCopies(4);
        bookDTO.setTitle("title");

        bookRepository.deleteAll();

        createAndVerifyBooks(status().isOk(), bookDTO, token);
    }

    @Test
    public void updateBook() throws Exception {
        final BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor("author updated");
        bookDTO.setCategory("category updated");
        bookDTO.setCopies(4);
        bookDTO.setTitle("titleupdated");

        updateAndVerifyBook(status().isOk(), bookDTO, token);
    }

    @Test
    @DisplayName("search for books successfully")
    public void searchForBooks() throws Exception {
        final SearchDTO searchDTO = new SearchDTO();
        searchDTO.setKeywords("title");

        searchAndVerifyBooks(status().isOk(), searchDTO, token);
    }

    @Test
    @DisplayName("lend books sucessfully")
    public void lendBooks() throws Exception {
        final BookRequestDTO bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setCopies(1);
        lendAndVerifyBook(status().isOk(), token, bookRequestDTO,
                book.getId(), newUser.getId());
    }

    @Test
    @DisplayName("delete book successfully")
    public void deleteBook() throws Exception {
        final Book book2 = new Book();

        book2.setCopies(2);
        book2.setCategory("category");
        book2.setAuthor("author");
        book2.setTitle("title2");
        book2.setBorrowers(Collections.emptySet());
        bookRepository.save(book2);

        deleteAndVerifyBook(status().isNoContent(), token, book2.getId());
    }

    public void getAndVerifyBookById(
            final ResultMatcher expectedStatus, final Long bookId
            , final String token
    ) throws Exception {
        mockMvc.perform(get("/api/v1/book/" + bookId)
                .content(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token))
                .andExpect(expectedStatus);
    }

    public void getAndVerifyAllBooks(
            final ResultMatcher expectedStatus, final String token
    ) throws Exception {

        mockMvc.perform(get("/api/v1/book")
                .content(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token))
                .andExpect(expectedStatus);
    }

    public void getAndVerifyBorrowedBooks(
            final ResultMatcher expectedStatus,
            final Long bookId, final String token
            ) throws Exception {

        mockMvc.perform(get("/api/v1/book/" + bookId + "/borrowers")
                .content(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token))
                .andExpect(expectedStatus).andDo(print());

    }

    public void createAndVerifyBooks(
            final ResultMatcher expectedStatus, final BookDTO bookDTO,
            final String token
            ) throws Exception {

        mockMvc.perform(post("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(bookDTO))
                .header("Authorization", "Bearer " + token))
                .andExpect(expectedStatus);
    }

    public void updateAndVerifyBook(
            final ResultMatcher expectedStatus,
            final BookDTO bookDTO, String token
    ) throws Exception {

        mockMvc.perform(put("/api/v1/book/"+ book.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(bookDTO))
                .header("Authorization", "Bearer " + token))
                .andExpect(expectedStatus);
    }

    public void searchAndVerifyBooks(
            final ResultMatcher expectedStatus,
            final SearchDTO searchDTO, final String token
    ) throws Exception {

        mockMvc.perform(post("/api/v1/book/search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(searchDTO))
                .header("Authorization", "Bearer " + token))
                .andExpect(expectedStatus).andDo(print());
    }

    public void lendAndVerifyBook(
            final ResultMatcher expectedStatus, final String token,
            final BookRequestDTO bookRequestDTO, final Long bookId, final Long userId
    ) throws Exception {

        mockMvc.perform(post("/api/v1/book/lendbook/"+bookId+"/"+userId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(bookRequestDTO))
                .header("Authorization", "Bearer " + token))
                .andExpect(expectedStatus);

    }

    public void deleteAndVerifyBook(
            final ResultMatcher expectedStatus, final String token,
             final Long bookId
    ) throws Exception {

        mockMvc.perform(delete("/api/v1/book/"+bookId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token))
                .andExpect(expectedStatus);

    }

    private UserDetails createUser() {
        User user = new User();
        user.setEmailId("test@app.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setLastName("test");
        user.setFirstName("firstName");
        return userRepository.save(user);
    }

    private void seedData() {

        newUser.setFirstName("firstName");
        newUser.setLastName("lastName");
        newUser.setPassword(passwordEncoder.encode("password"));
        newUser.setEmailId("doe@app.com");

        userRepository.save(newUser);

        book.setCopies(2);
        book.setCategory("category");
        book.setAuthor("author");
        book.setTitle("title");
        book.setBorrowers(Collections.emptySet());
        bookRepository.save(book);
        //ANOTHER BOOK

        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setUser(newUser);
        borrow.setCopies(1);

        lendRepository.save(borrow);
    }
}