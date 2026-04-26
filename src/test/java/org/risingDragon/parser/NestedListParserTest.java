package org.risingDragon.parser;

import org.junit.jupiter.api.Test;
import org.risingDragon.Parser;
import org.risingDragon.tree.LNode;
import org.risingDragon.tree.Node;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NestedListParserTest {

    @Test
    void parseNestedList() {
        Node result = Parser.parse("(+ 2 (* 3 4))");
        assertInstanceOf(LNode.class, result);
        List<?> children = result.getValue();
        assertEquals(3, children.size());
        assertInstanceOf(LNode.class, children.get(2));
    }

    @Test
    void parseDeeplyNested() {
        Node result = Parser.parse("(+ 1 (+ 2 (+ 3 4)))");
        List<?> outer = result.getValue();
        assertEquals(3, outer.size());
        Node inner = (Node) ((Node) outer.get(2)).getValue().get(2);
        assertInstanceOf(LNode.class, inner);
    }

    @Test
    void parseMultipleNestedLists() {
        Node result = Parser.parse("(+ (* 2 3) (* 4 5))");
        List<?> children = result.getValue();
        assertEquals(3, children.size());
        assertInstanceOf(LNode.class, children.get(1));
        assertInstanceOf(LNode.class, children.get(2));
    }
}
