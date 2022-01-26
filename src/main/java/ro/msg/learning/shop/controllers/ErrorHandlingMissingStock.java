package ro.msg.learning.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.msg.learning.shop.dto.ErrorMissingStockDTO;
import ro.msg.learning.shop.exceptions.NoSuitableLocationsFound;

@RestControllerAdvice
public class ErrorHandlingMissingStock {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuitableLocationsFound.class)
    public ErrorMissingStockDTO handleNotFound(NoSuitableLocationsFound exception) {
        return ErrorMissingStockDTO.of(exception);
    }
}
