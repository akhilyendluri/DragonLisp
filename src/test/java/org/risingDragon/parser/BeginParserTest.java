package org.risingDragon.parser;

import org.junit.jupiter.api.Test;
import org.risingDragon.Parser;
import org.risingDragon.tree.LNode;
import org.risingDragon.tree.Node;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BeginParserTest {

    @Test
    void parseBeginBlock() {
        Node result = Parser.parse("(begin (define x 10) (print x))");
        assertInstanceOf(LNode.class, result);
        List<?> children = result.getValue();
        assertEquals(3, children.size());
        assertEquals("begin", ((Node) children.get(0)).getValue().get(0));
        assertInstanceOf(LNode.class, children.get(1));
        assertInstanceOf(LNode.class, children.get(2));
    }

    @Test
    void parseBeginWithDefineAndCall() {
        Node result = Parser.parse("(begin (define sq (lambda (x) (* x x))) (sq 5))");
        assertInstanceOf(LNode.class, result);
        List<?> children = result.getValue();
        assertEquals(3, children.size());
        assertEquals("begin", ((Node) children.get(0)).getValue().get(0));

        // (define sq (lambda (x) (* x x)))
        List<?> define = ((Node) children.get(1)).getValue();
        assertEquals("define", ((Node) define.get(0)).getValue().get(0));
        assertEquals("sq", ((Node) define.get(1)).getValue().get(0));
        assertInstanceOf(LNode.class, define.get(2)); // the lambda

        // (sq 5)
        List<?> call = ((Node) children.get(2)).getValue();
        assertEquals("sq", ((Node) call.get(0)).getValue().get(0));
        assertEquals(5.0, ((Node) call.get(1)).getValue().get(0));
    }
}
