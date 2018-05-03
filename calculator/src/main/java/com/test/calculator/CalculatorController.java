package com.test.calculator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>Controller for calculate endpoint.</h1>
 * Supports double type for numbers and limited number of operations {@link Operation}.
 */
@RestController
final class CalculatorController {

    @RequestMapping("/calculate")
    ResponseEntity calculate(@RequestParam("number1") String number1,
                             @RequestParam("number2") String number2,
                             @RequestParam("operation") String operation) {
        return new ResponseBuilder(number1, number2, operation).build();
    }
}
