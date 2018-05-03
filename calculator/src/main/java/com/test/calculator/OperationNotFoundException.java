package com.test.calculator;

class OperationNotFoundException extends IllegalArgumentException {
    OperationNotFoundException(String message) {
        super(message);
    }
}
