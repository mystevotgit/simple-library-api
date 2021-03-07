package com.accessment.library.service;

import com.accessment.library.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface UserService {
    void addUser(UserDTO userDTO);
}
