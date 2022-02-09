package ro.msg.learning.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.msg.learning.shop.dto.ErrorMissingTokenDTO;
import ro.msg.learning.shop.exceptions.MissingTokenException;

@RestControllerAdvice
public class ErrorHandlingMissingToken {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MissingTokenException.class)
    public ErrorMissingTokenDTO handleNotFound(MissingTokenException exception) {
        return ErrorMissingTokenDTO.of(exception);
    }
}
