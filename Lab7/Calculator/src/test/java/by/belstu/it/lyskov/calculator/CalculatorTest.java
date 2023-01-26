package by.belstu.it.lyskov.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void init() {
        calculator = new Calculator();
    }

    @Test
    void testNotNullLineShouldNotTrow() {
        String line = "123+45";
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
                calculator.solve(line);
            }
        });
    }

    @Test
    void testPlusOperation() {
        String line = "111+12";
        Optional<Double> actual = calculator.solve(line);
        double expected = 123;
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testMinusOperation() {
        String line = "534-690";
        Optional<Double> actual = calculator.solve(line);
        double expected = -156;
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testMultiplyOperation() {
        String line = "17*42";
        Optional<Double> actual = calculator.solve(line);
        double expected = 714;
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testDivideOperation() {
        String line = "1593/59";
        Optional<Double> actual = calculator.solve(line);
        double expected = 27;
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testNullLineShouldThrow() {
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() {
                calculator.solve(null);
            }
        });
    }

    @Test
    void testEmptyLineShouldProduceEmptyResult() {
        String line = "     ";
        Optional<Double> actual = calculator.solve(line);
        assertFalse(actual.isPresent());
    }

    @Test
    void testSingleNumberShouldProduceEmptyResult() {
        String line = "123";
        Optional<Double> actual = calculator.solve(line);
        assertFalse(actual.isPresent());
    }

    @Test
    void testResultShouldBeEmptyIfLineInvalid() {
        String line = "*438+";
        Optional<Double> actual = calculator.solve(line);
        assertFalse(actual.isPresent());
    }

    @Test
    void testResultShouldBeEmptyIfLineIsWord() {
        String line = "Hello, world!";
        Optional<Double> actual = calculator.solve(line);
        assertFalse(actual.isPresent());
    }

    @Test
    void testResultShouldBeEmptyIfLineHasInvalidOperations() {
        String line = "83(34&23)11=79";
        Optional<Double> actual = calculator.solve(line);
        assertFalse(actual.isPresent());
    }

}