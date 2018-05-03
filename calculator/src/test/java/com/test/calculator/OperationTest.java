package com.test.calculator;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public final class OperationTest extends AbstractTestNGSpringContextTests {

    @DataProvider(name = "HappyPathResult")
    private Object[][] happyPathDataProvider() {
        return new Object[][]{
                {Double.MAX_VALUE, Double.MAX_VALUE, Operation.ADD, Double.POSITIVE_INFINITY},
                {Double.MAX_VALUE + 1, Double.MAX_VALUE + 1, Operation.ADD, Double.POSITIVE_INFINITY},
                {Double.MAX_VALUE - 1, Double.MAX_VALUE - 1, Operation.ADD, Double.POSITIVE_INFINITY},
                {Double.MAX_VALUE, Double.MAX_VALUE, Operation.MUL, Double.POSITIVE_INFINITY},
                {Double.MAX_VALUE, Double.MAX_VALUE, Operation.SUB, 0.0},
                {Double.MAX_VALUE, Double.MAX_VALUE, Operation.DIV, 1.0},
                {Double.MIN_VALUE, Double.MAX_VALUE, Operation.ADD, Double.MAX_VALUE},
                {Double.MAX_VALUE, Double.MAX_VALUE, Operation.DIV, 1.0},
                {0, 0, Operation.ADD, 0},
                {1.2, 2.0, Operation.ADD, 3.2},
                {1.2, 2, Operation.MUL, 2.4},
                {1.2, 2, Operation.DIV, 0.6},
                {1.2, 2, Operation.SUB, -0.8}
        };
    }

    @Test(dataProvider = "HappyPathResult")
    void getResultHappyPath(double number1, double number2, Operation operation, double expectedResult) {
        assertEquals(operation.getResult(number1, number2), expectedResult, "Calculation is wrong");
    }

    //Probably there could be more negative checks
    @DataProvider(name = "NegativeResult")
    private Object[][] negativeDataProvider() {
        return new Object[][]{
                {0, 0, Operation.DIV, Double.NaN},
        };
    }

    @Test(dataProvider = "NegativeResult")
    public void getResultNegativeChecks(double number1, double number2, Operation operation, double expectedResult) {
        assertEquals(operation.getResult(number1, number2), expectedResult, "Calculation is wrong");
    }

    @DataProvider(name = "Symbol")
    private Object[][] symbolDataProvider() {
        return new Object[][]{
                {"ADD", Operation.ADD},
                {"SUB", Operation.SUB},
                {"MUL", Operation.MUL},
                {"DIV", Operation.DIV},
        };
    }

    @Test(dataProvider = "Symbol")
    public void getSymbol(String symbol, Operation operation) {
        assertEquals(symbol, operation.getSymbol(), "Operation symbol doesn't match expected one");
    }

    @Test(dataProvider = "Symbol")
    public void getByValueHappyPath(String operationToFind, Operation expectedOperation) {
        assertEquals(Operation.getByValue(operationToFind), expectedOperation, "Operation can't be found");
    }

    @Test(expectedExceptions = OperationNotFoundException.class)
    public void getByValueNegative() throws OperationNotFoundException {
        Operation.getByValue("TestNegativeOperation");
    }

}