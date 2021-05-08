package com.geektrust.familytree.exception;

public class ChildAdditionFailureException extends RuntimeException {
    private static final String message = "CHILD_ADDITION_FAILED";

    @Override
    public String toString() {
        return message;
    }
}

