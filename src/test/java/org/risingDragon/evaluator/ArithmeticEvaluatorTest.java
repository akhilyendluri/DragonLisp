package org.risingDragon.evaluator;

import org.junit.jupiter.api.Test;
import org.risingDragon.Interpreter;
import org.risingDragon.Parser;

import static org.junit.jupiter.api.Assertions.*;

class ArithmeticEvaluatorTest {

    private Object run(String input) {
        return Interpreter.eval(Parser.parse(input));
    }

    // --- Addition ---

    @Test
    void addTwoNumbers() {
        assertEquals(3.0, run("(+ 1 2)"));
    }

    @Test
    void addMultipleNumbers() {
        assertEquals(15.0, run("(+ 1 2 3 4 5)"));
    }

    @Test
    void addNegativeNumbers() {
        assertEquals(-3.0, run("(+ -1 -2)"));
    }

    @Test
    void addDecimals() {
        assertEquals(5.5, run("(+ 2.5 3.0)"));
    }

    @Test
    void addSingleNumber() {
        assertEquals(7.0, run("(+ 7)"));
    }

    @Test
    void nestedAddition() {
        assertEquals(10.0, run("(+ 1 (+ 2 (+ 3 4)))"));
    }

    // --- Subtraction ---

    @Test
    void subtractTwoNumbers() {
        assertEquals(1.0, run("(- 3 2)"));
    }

    @Test
    void subtractMultipleNumbers() {
        assertEquals(0.0, run("(- 10 5 3 2)"));
    }

    @Test
    void subtractNegativeResult() {
        assertEquals(-5.0, run("(- 3 8)"));
    }

    @Test
    void nestedSubtraction() {
        assertEquals(4.0, run("(- 10 (- 8 2))"));
    }

    // --- Multiplication ---

    @Test
    void multiplyTwoNumbers() {
        assertEquals(12.0, run("(* 3 4)"));
    }

    @Test
    void multiplyMultipleNumbers() {
        assertEquals(120.0, run("(* 2 3 4 5)"));
    }

    @Test
    void multiplyByZero() {
        assertEquals(0.0, run("(* 5 0)"));
    }

    @Test
    void multiplyNegatives() {
        assertEquals(6.0, run("(* -2 -3)"));
    }

    @Test
    void nestedMultiplication() {
        assertEquals(24.0, run("(* 2 (* 3 4))"));
    }

    // --- Division ---

    @Test
    void divideTwoNumbers() {
        assertEquals(5.0, run("(/ 10 2)"));
    }

    @Test
    void divideMultipleNumbers() {
        assertEquals(2.0, run("(/ 24 3 4)"));
    }

    @Test
    void divideWithRemainder() {
        assertEquals(2.5, run("(/ 5 2)"));
    }

    @Test
    void nestedDivision() {
        assertEquals(5.0, run("(/ 20 (/ 8 2))"));
    }

    // --- Modulo ---

    @Test
    void simpleModulo() {
        assertEquals(1.0, run("(% 7 3)"));
    }

    @Test
    void moduloEvenDivision() {
        assertEquals(0.0, run("(% 10 5)"));
    }

    @Test
    void nestedModulo() {
        assertEquals(1.0, run("(% 7 (% 10 4))"));
    }

    // --- Mixed operations ---

    @Test
    void additionInsideMultiplication() {
        assertEquals(20.0, run("(* (+ 2 3) 4)"));
    }

    @Test
    void subtractionInsideDivision() {
        assertEquals(2.5, run("(/ (- 10 5) 2)"));
    }

    @Test
    void deeplyNested() {
        // (+ 1 (* 2 (- 10 (/ 8 2)))) = 1 + (2 * (10 - 4)) = 1 + 12 = 13
        assertEquals(13.0, run("(+ 1 (* 2 (- 10 (/ 8 2))))"));
    }

    @Test
    void allOperators() {
        // 12 + 5 + 5 + 1 = 23
        assertEquals(23.0, run("(+ (* 3 4) (- 10 5) (/ 20 4) (% 7 3))"));
    }
}
