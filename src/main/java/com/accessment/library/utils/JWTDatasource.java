package com.accessment.library.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JWTDatasource {
    private String secretKey;
    private String tokenPrefix;
    private Long expirationDate;
}
