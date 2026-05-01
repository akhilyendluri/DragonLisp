package org.risingDragon.evaluator;

import org.junit.jupiter.api.Test;
import org.risingDragon.TreeWalkInterpreter;
import org.risingDragon.Parser;

import static org.junit.jupiter.api.Assertions.*;

class VariablesTest {

    private Object run(String input) {
        return TreeWalkInterpreter.eval(Parser.parse(input));
    }

    // --- Begin ---

    @Test
    void beginReturnsLastExpression() {
        assertEquals(3.0, run("(begin 1 2 3)"));
    }

    @Test
    void beginWithSingleExpression() {
        assertEquals(5.0, run("(begin 5)"));
    }

    @Test
    void beginWithNestedExpressions() {
        assertEquals(7.0, run("(begin (+ 1 2) (+ 3 4))"));
    }

    // --- Define ---

    @Test
    void defineAndUseVariable() {
        assertEquals(10.0, run("(begin (define x 10) x)"));
    }

    @Test
    void defineWithExpression() {
        assertEquals(14.0, run("(begin (define x (+ 3 4)) (* x 2))"));
    }

    @Test
    void defineMultipleVariables() {
        assertEquals(30.0, run("(begin (define x 10) (define y 20) (+ x y))"));
    }

    @Test
    void defineUsedInNestedExpression() {
        assertEquals(25.0, run("(begin (define a 5) (* a a))"));
    }

    @Test
    void defineOverwriteVariable() {
        assertEquals(20.0, run("(begin (define x 10) (define x 20) x)"));
    }

    @Test
    void defineUsingPreviousVariable() {
        assertEquals(6.0, run("(begin (define x 3) (define y (* x 2)) y)"));
    }
}
