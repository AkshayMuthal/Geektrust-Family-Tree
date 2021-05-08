package com.geektrust.familytree.exception;

public class PersonNotFoundException extends RuntimeException {
    private static final String message = "PERSON_NOT_FOUND";

    @Override
    public String toString() {
        return message;
    }
}
