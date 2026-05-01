package org.risingDragon.evaluator;

import org.junit.jupiter.api.Test;
import org.risingDragon.TreeWalkInterpreter;
import org.risingDragon.Parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LogicAndPrintTest {

    private Object run(String input) {
        return TreeWalkInterpreter.eval(Parser.parse(input));
    }

    // --- And ---

    @Test
    void andBothTrue() {
        assertEquals(true, run("(and (> 5 3) (> 10 1))"));
    }

    @Test
    void andFirstFalse() {
        assertEquals(false, run("(and (< 5 3) (> 10 1))"));
    }

    @Test
    void andSecondFalse() {
        assertEquals(false, run("(and (> 5 3) (< 10 1))"));
    }

    @Test
    void andBothFalse() {
        assertEquals(false, run("(and (< 5 3) (< 10 1))"));
    }

    // --- Or ---

    @Test
    void orBothTrue() {
        assertEquals(true, run("(or (> 5 3) (> 10 1))"));
    }

    @Test
    void orFirstTrue() {
        assertEquals(true, run("(or (> 5 3) (< 10 1))"));
    }

    @Test
    void orSecondTrue() {
        assertEquals(true, run("(or (< 5 3) (> 10 1))"));
    }

    @Test
    void orBothFalse() {
        assertEquals(false, run("(or (< 5 3) (< 10 1))"));
    }

    // --- Not ---

    @Test
    void notTrue() {
        assertEquals(false, run("(not (> 5 3))"));
    }

    @Test
    void notFalse() {
        assertEquals(true, run("(not (< 5 3))"));
    }

    // --- Combined logic ---

    @Test
    void andWithNot() {
        assertEquals(true, run("(and (> 5 3) (not (< 5 3)))"));
    }

    @Test
    void orWithAnd() {
        assertEquals(true, run("(or (and (< 1 2) (< 2 3)) (> 1 10))"));
    }

    @Test
    void logicInIf() {
        assertEquals(1.0, run("(if (and (> 5 3) (not (= 5 3))) 1 0)"));
    }

    // --- Print ---

    @Test
    void printNumber() {
        PrintStream original = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        try {
            run("(print 42)");
            assertEquals("42.0", output.toString().trim());
        } finally {
            System.setOut(original);
        }
    }

    @Test
    void printString() {
        PrintStream original = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        try {
            run("(print \"hello\")");
            assertEquals("hello", output.toString().trim());
        } finally {
            System.setOut(original);
        }
    }

    @Test
    void printInBegin() {
        PrintStream original = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        try {
            Object result = run("(begin (print \"hi\") (+ 1 2))");
            assertEquals("hi", output.toString().trim());
            assertEquals(3.0, result);
        } finally {
            System.setOut(original);
        }
    }
}
