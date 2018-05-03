package com.test.calculator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Builds response for calculation two numbers with provided operation.</h1>
 * <p>Supported operations can be found in {@link Operation}</p>
 */
final class ResponseBuilder {
    private String number1;
    private String number2;
    private String operation;

    private double parsedNumber1;
    private double parsedNumber2;
    private Operation parsedOperation;

    private List<String> errors = new ArrayList<>();
    private ResponseEntity response;

    ResponseBuilder(String number1, String number2, String operation) {
        this.number1 = number1;
        this.number2 = number2;
        this.operation = operation;
    }

    ResponseEntity build() {
        this.performParsing();
        this.buildResponse();
        return response;
    }

    private void performParsing() {
        parsedNumber1 = this.parseNumber(number1);
        parsedNumber2 = this.parseNumber(number2);
        try {
            parsedOperation = Operation.getByValue(operation);
        } catch (OperationNotFoundException e) {
            errors.add(e.getMessage());
        }
    }

    private double parseNumber(String number) {
        try {
            return Double.valueOf(number);
        } catch (NumberFormatException e) {
            errors.add(e.getMessage());
            return 0;
        }
    }

    private void buildResponse() {
        response = errors.isEmpty() ?
                new ResponseEntity(parsedOperation.getResult(parsedNumber1, parsedNumber2), HttpStatus.OK) :
                new ResponseEntity(errors.toArray(), HttpStatus.BAD_REQUEST);
    }
}
