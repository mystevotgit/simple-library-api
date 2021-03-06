package com.accessment.library.service;

import com.accessment.library.dto.SignupDTO;
import com.accessment.library.model.User;
import com.accessment.library.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private void setModelMappingStrategy() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmailId(s);
        return user.get();
    }

    @Override
    public void addUser(SignupDTO signupDTO) {
        setModelMappingStrategy();
        User user = modelMapper.map(signupDTO, User.class);
        userRepository.save(user);
        return;
    }
}
