package com.test.calculator;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.AssertJUnit.assertEquals;

public final class ResponseBuilderTest {

    @DataProvider(name = "positive")
    private Object[][] positiveDataProvider() {
        return new Object[][]{
                {"2.0", "1.0", "ADD", new ResponseEntity(3.0, HttpStatus.OK)},
                {"2.0", "1.0", "MUL", new ResponseEntity(2.0, HttpStatus.OK)},
                {"2.0", "1.0", "SUB", new ResponseEntity(1.0, HttpStatus.OK)},
                {"2.0", "1.0", "DIV", new ResponseEntity(2.0, HttpStatus.OK)},
        };
    }

    @Test(dataProvider = "positive")
    public void buildPositive(String number1, String number2, String operation, ResponseEntity expectedResult) {
        ResponseBuilder responseBuilder = new ResponseBuilder(number1, number2, operation);
        ResponseEntity responseEntity = responseBuilder.build();

        assertEquals("Built response is incorrect", expectedResult, responseEntity);
    }

    // There could be more negative checks
    @DataProvider(name = "negative")
    private Object[][] negativeDataProvider() {
        return new Object[][]{
                {"2.0", "1.0", "+",
                        new ResponseEntity(Arrays.asList("+: Such operation is not supported").toArray(),
                                HttpStatus.BAD_REQUEST)},
                {"2.0", "", "ADD",
                        new ResponseEntity(Arrays.asList("empty String").toArray(),
                                HttpStatus.BAD_REQUEST)},
                {"2.0", "TestWord", "ADD",
                        new ResponseEntity(Arrays.asList("For input string: \"TestWord\"").toArray(),
                                HttpStatus.BAD_REQUEST)},
                {"TestFirst", "TestSecond", "ADD",
                        new ResponseEntity(
                                Arrays.asList("For input string: \"TestFirst\"",
                                "For input string: \"TestSecond\"").toArray(),
                                HttpStatus.BAD_REQUEST)},
        };
    }

    @Test(dataProvider = "negative")
    public void buildNegative(String number1, String number2, String operation, ResponseEntity expectedResult) {
        ResponseBuilder responseBuilder = new ResponseBuilder(number1, number2, operation);
        ResponseEntity responseEntity = responseBuilder.build();

        assertEquals("Built response is incorrect", expectedResult, responseEntity);
    }
}