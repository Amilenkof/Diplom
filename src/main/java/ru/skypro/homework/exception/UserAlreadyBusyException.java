package ru.skypro.homework.exception;

public class UserAlreadyBusyException extends RuntimeException{
    public UserAlreadyBusyException(String message) {
        super(message);
    }
}
