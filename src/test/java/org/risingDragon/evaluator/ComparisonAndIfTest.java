package org.risingDragon.evaluator;

import org.junit.jupiter.api.Test;
import org.risingDragon.TreeWalkInterpreter;
import org.risingDragon.Parser;

import static org.junit.jupiter.api.Assertions.*;

class ComparisonAndIfTest {

    private Object run(String input) {
        return TreeWalkInterpreter.eval(Parser.parse(input));
    }

    // --- Comparisons ---

    @Test
    void equalTrue() {
        assertEquals(true, run("(= 5 5)"));
    }

    @Test
    void equalFalse() {
        assertEquals(false, run("(= 5 3)"));
    }

    @Test
    void lessThan() {
        assertEquals(true, run("(< 3 5)"));
    }

    @Test
    void lessThanFalse() {
        assertEquals(false, run("(< 5 3)"));
    }

    @Test
    void greaterThan() {
        assertEquals(true, run("(> 5 3)"));
    }

    @Test
    void greaterThanFalse() {
        assertEquals(false, run("(> 3 5)"));
    }

    @Test
    void lessOrEqual() {
        assertEquals(true, run("(<= 3 3)"));
    }

    @Test
    void lessOrEqualFalse() {
        assertEquals(false, run("(<= 5 3)"));
    }

    @Test
    void greaterOrEqual() {
        assertEquals(true, run("(>= 5 5)"));
    }

    @Test
    void greaterOrEqualFalse() {
        assertEquals(false, run("(>= 3 5)"));
    }

    @Test
    void comparisonWithExpressions() {
        assertEquals(true, run("(> (+ 3 4) (* 2 3))"));
    }

    // --- If ---

    @Test
    void ifTrue() {
        assertEquals(1.0, run("(if (> 5 3) 1 2)"));
    }

    @Test
    void ifFalse() {
        assertEquals(2.0, run("(if (< 5 3) 1 2)"));
    }

    @Test
    void ifWithExpressions() {
        assertEquals(10.0, run("(if (= 1 1) (+ 4 6) (- 4 6))"));
    }

    @Test
    void ifWithVariables() {
        assertEquals(1.0, run("(begin (define x 10) (if (> x 5) 1 0))"));
    }

    @Test
    void nestedIf() {
        assertEquals(3.0, run("(if (> 5 3) (if (> 3 1) 3 2) 1)"));
    }
}
