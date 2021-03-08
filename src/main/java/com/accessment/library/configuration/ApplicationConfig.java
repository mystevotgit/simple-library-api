package com.accessment.library.configuration;

import com.accessment.library.utils.JWTDatasource;
import com.accessment.library.utils.JWTUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

    @Bean
    public JWTDatasource jwtDatasource() {
        JWTDatasource jwtDatasource = new JWTDatasource();
        return jwtDatasource;
    }
}
