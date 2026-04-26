package org.risingDragon.parser;

import org.junit.jupiter.api.Test;
import org.risingDragon.Parser;
import org.risingDragon.tree.LNode;
import org.risingDragon.tree.Node;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListParserTest {

    @Test
    void parseSimpleList() {
        Node result = Parser.parse("(+ 1 2)");
        assertInstanceOf(LNode.class, result);
        List<?> children = result.getValue();
        assertEquals(3, children.size());
        assertEquals("+", ((Node) children.get(0)).getValue().get(0));
        assertEquals(1.0, ((Node) children.get(1)).getValue().get(0));
        assertEquals(2.0, ((Node) children.get(2)).getValue().get(0));
    }

    @Test
    void parseListWithMultipleArgs() {
        Node result = Parser.parse("(+ 1 2 3 4)");
        assertInstanceOf(LNode.class, result);
        assertEquals(5, result.getValue().size());
    }

    @Test
    void parseEmptyList() {
        Node result = Parser.parse("()");
        assertInstanceOf(LNode.class, result);
        assertEquals(0, result.getValue().size());
    }

    @Test
    void parseDefine() {
        Node result = Parser.parse("(define x 10)");
        List<?> children = result.getValue();
        assertEquals(3, children.size());
        assertEquals("define", ((Node) children.get(0)).getValue().get(0));
        assertEquals("x", ((Node) children.get(1)).getValue().get(0));
        assertEquals(10.0, ((Node) children.get(2)).getValue().get(0));
    }

    @Test
    void parseIf() {
        Node result = Parser.parse("(if (> x 0) 1 -1)");
        List<?> children = result.getValue();
        assertEquals(4, children.size());
        assertEquals("if", ((Node) children.get(0)).getValue().get(0));
        assertInstanceOf(LNode.class, children.get(1));
    }
}
