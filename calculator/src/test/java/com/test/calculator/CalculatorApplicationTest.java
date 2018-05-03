package com.test.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

@SpringBootTest
public final class CalculatorApplicationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    CalculatorController controller;

    @Test
    public void contextLoads() {
        assertNotNull(controller);
    }
}
