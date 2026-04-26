package org.risingDragon.parser;

import org.junit.jupiter.api.Test;
import org.risingDragon.Parser;
import org.risingDragon.tree.LNode;
import org.risingDragon.tree.Node;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WhitespaceAndEdgeCaseTest {

    @Test
    void parseWithExtraSpaces() {
        Node result = Parser.parse("(  +   1   2  )");
        assertInstanceOf(LNode.class, result);
        assertEquals(3, result.getValue().size());
    }

    @Test
    void parseSymbolBeforeCloseParen() {
        Node result = Parser.parse("(+ a b)");
        List<?> children = result.getValue();
        assertEquals("b", ((Node) children.get(2)).getValue().get(0));
    }

    @Test
    void parseSymbolNoSpaceBeforeParen() {
        // symbol immediately followed by ) — parseSymbol must stop at )
        Node result = Parser.parse("(define x(+ 1 2))");
        List<?> children = result.getValue();
        assertEquals("x", ((Node) children.get(1)).getValue().get(0));
        assertInstanceOf(LNode.class, children.get(2));
    }
}
