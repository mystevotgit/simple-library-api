package com.accessment.library.controller;

import com.accessment.library.dto.LoginDTO;
import com.accessment.library.dto.UserDTO;
import com.accessment.library.service.UserService;
import com.accessment.library.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class AuthController {
    @Autowired
    private JWTUtils jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    @ResponseStatus(OK)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmailId(), dto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("incorrect username or passoword!");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmailId());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.of(Optional.of(jwt));
    }

    @PostMapping("/signup")
    public <T> ResponseEntity<T> signup(@RequestBody UserDTO userDTO){
        userService.addUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
