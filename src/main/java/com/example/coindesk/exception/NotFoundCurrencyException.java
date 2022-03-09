package com.example.coindesk.exception;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

public class NotFoundCurrencyException extends RuntimeException {

    public NotFoundCurrencyException(String message) {
        super(message);
    }
}