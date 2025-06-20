package com.nttdata.banking.bootcoin.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class ResourceNotFoundException.
 * BootCoin microservice class ResourceNotFoundException.
 */
@Getter
@Setter
@NoArgsConstructor
public class BlogAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus state;
    private String message;

    public BlogAppException(HttpStatus state, String message) {
        super();
        this.state = state;
        this.message = message;
    }
}
