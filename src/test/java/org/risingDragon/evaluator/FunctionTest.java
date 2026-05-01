package org.risingDragon.evaluator;

import org.junit.jupiter.api.Test;
import org.risingDragon.TreeWalkInterpreter;
import org.risingDragon.Parser;

import static org.junit.jupiter.api.Assertions.*;

class FunctionTest {

    private Object run(String input) {
        return TreeWalkInterpreter.eval(Parser.parse(input));
    }

    // --- Lambda ---

    @Test
    void lambdaInlineCall() {
        assertEquals(9.0, run("((lambda (x) (* x x)) 3)"));
    }

    @Test
    void lambdaMultipleParams() {
        assertEquals(7.0, run("((lambda (x y) (+ x y)) 3 4)"));
    }

    // --- Named functions via define + lambda ---

    @Test
    void defineAndCallFunction() {
        assertEquals(25.0, run("(begin (define square (lambda (x) (* x x))) (square 5))"));
    }

    @Test
    void defineMultiParamFunction() {
        assertEquals(12.0, run("(begin (define add (lambda (a b) (+ a b))) (add 5 7))"));
    }

    @Test
    void functionUsingOtherFunction() {
        assertEquals(100.0, run("(begin (define square (lambda (x) (* x x))) (define double-square (lambda (x) (square (* 2 x)))) (double-square 5))"));
    }

    // --- Closures ---

    @Test
    void closureOverVariable() {
        assertEquals(15.0, run("(begin (define y 10) (define add-y (lambda (x) (+ x y))) (add-y 5))"));
    }

    // --- Recursion ---

    @Test
    void recursiveFactorial() {
        assertEquals(120.0, run("(begin (define fact (lambda (n) (if (= n 0) 1 (* n (fact (- n 1)))))) (fact 5))"));
    }

    @Test
    void recursiveFibonacci() {
        assertEquals(8.0, run("(begin (define fib (lambda (n) (if (<= n 1) n (+ (fib (- n 1)) (fib (- n 2)))))) (fib 6))"));
    }

    // --- Function as argument ---

    @Test
    void functionPassedAsArgument() {
        assertEquals(9.0, run("(begin (define apply-fn (lambda (f x) (f x))) (define square (lambda (x) (* x x))) (apply-fn square 3))"));
    }

    // --- Lambda arguments should be evaluated ---

    @Test
    void lambdaWithExpressionAsArgument() {
        assertEquals(9.0, run("((lambda (x) (* x x)) (+ 1 2))"));
    }

    @Test
    void namedFunctionWithExpressionAsArgument() {
        assertEquals(16.0, run("(begin (define square (lambda (x) (* x x))) (square (+ 2 2)))"));
    }

    // --- Functions are values ---

    @Test
    void functionStoredInVariable() {
        assertEquals(9.0, run("(begin (define f (lambda (x) (* x x))) (define g f) (g 3))"));
    }

    // --- Closure over local variable ---

    @Test
    void closureOverLocalVariable() {
        assertEquals(15.0, run("(begin (define make-adder (lambda (n) (lambda (x) (+ x n)))) (define add5 (make-adder 5)) (add5 10))"));
    }

    // --- Scoping ---

    @Test
    void recursiveWithSameParamName() {
        assertEquals(0.0, run("(begin (define countdown (lambda (n) (if (= n 0) 0 (countdown (- n 1))))) (countdown 5))"));
    }

    @Test
    void mutuallyIndependentScopes() {
        assertEquals(11.0, run("(begin (define double (lambda (x) (* x 2))) (define inc (lambda (x) (+ x 1))) (+ (double 3) (inc 4)))"));
    }

    @Test
    void higherOrderWithExpressionArg() {
        assertEquals(25.0, run("(begin (define apply-fn (lambda (f x) (f x))) (define square (lambda (x) (* x x))) (apply-fn square (+ 2 3)))"));
    }

    @Test
    void paramShadowsCapturedVariable() {
        // make-fn captures n=5, but returned lambda takes n as param
        // calling with n=3 should use 3 (param), not 5 (captured)
        assertEquals(12.0, run("(begin (define make-fn (lambda (n) (lambda (n) (* n 4)))) (define f (make-fn 5)) (f 3))"));
    }
}
