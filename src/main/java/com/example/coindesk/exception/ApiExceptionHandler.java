package com.example.coindesk.exception;

import com.example.coindesk.presentation.dto.error.ErrorFieldDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = NotFoundCurrencyException.class)
    @ResponseBody
    public ResponseEntity<?> handleNotFound(NotFoundCurrencyException e) {
        return new ResponseEntity<>(new NotFoundCurrencyException(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidRequestException.class)
    @ResponseBody
    public ResponseEntity<?> handleInvalidRequest(InvalidRequestException e) {
        List<ErrorFieldDto> errorFieldList = new ArrayList<>();
        List<FieldError> fieldErrors = e.getErrors();

        fieldErrors.forEach(fieldError -> {
            errorFieldList.add(new ErrorFieldDto(fieldError.getField(), fieldError.getDefaultMessage()));
        });

        return ResponseEntity.badRequest().body(errorFieldList);
    }

}