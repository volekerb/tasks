package com.test.calculator;

import java.util.function.DoubleBinaryOperator;

/**
 * Supported operations are:
 * <ul>
 * <li>Addition (+)</li>
 * <li>Subtraction (-)</li>
 * <li>Multiplication (*)</li>
 * <li>Division (/)</li>
 * </ul>
 * <p>Supports double type for the input numbers.</p>
 */
public enum Operation {

    ADD("ADD", (number1, number2) -> number1 + number2),
    SUB("SUB", (number1, number2) -> number1 - number2),
    MUL("MUL", (number1, number2) -> number1 * number2),
    DIV("DIV", (number1, number2) -> number1 / number2);

    private final String symbol;
    private final DoubleBinaryOperator operator;

    Operation(String symbol, DoubleBinaryOperator operator) {
        this.symbol = symbol;
        this.operator = operator;
    }

    public double getResult(double number1, double number2) {
        return operator.applyAsDouble(number1, number2);
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns found supported operation
     *
     * @param value operation to find
     * @throws OperationNotFoundException in case when operation is not found
     */
    public static Operation getByValue(String value) {
        for (Operation operation : Operation.values()) {
            if (value.equals(operation.getSymbol())) return operation;
        }
        throw new OperationNotFoundException(value + ": Such operation is not supported");
    }
}