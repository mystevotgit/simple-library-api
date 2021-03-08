package com.accessment.library.controller;

import com.accessment.library.model.Book;
import com.accessment.library.model.User;
import com.accessment.library.repository.BookRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.*;
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
    private JWTUtils jwtUtils;

    final Book book = new Book();
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

    public void getAndVerifyBookById(
           final ResultMatcher expectedStatus, final Long bookId
           ,final String token
    ) throws Exception {
        mockMvc.perform(get("/api/v1/book/" + bookId)
                .content(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer "+ token))
                .andExpect(expectedStatus);
    }

    public void getAndVerifyAllBooks(
            final ResultMatcher expectedStatus,final String token
    ) throws Exception {
        mockMvc.perform(get("/api/v1/book")
                .content(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer "+ token))
                .andExpect(expectedStatus).andDo(print());
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

        User newUser = new User();
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

    }
}