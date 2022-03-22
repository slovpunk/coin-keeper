package ru.didyk.coinkeeper.exception;

public class AccountIsNull extends RuntimeException {
    public AccountIsNull(String message) {
        super(message);
    }
}
