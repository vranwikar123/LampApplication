package com.example.LampControllerApp.LampApplication.exception;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException(String message) {
        super(message);
    }
}