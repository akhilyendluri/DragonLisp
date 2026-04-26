package org.risingDragon.parser;

import org.junit.jupiter.api.Test;
import org.risingDragon.Parser;
import org.risingDragon.tree.ANode;
import org.risingDragon.tree.Node;

import static org.junit.jupiter.api.Assertions.*;

class AtomParserTest {

    @Test
    void parseInteger() {
        Node result = Parser.parse("42");
        assertInstanceOf(ANode.class, result);
        assertEquals(42.0, result.getValue().get(0));
    }

    @Test
    void parseNegativeNumber() {
        Node result = Parser.parse("-7");
        assertInstanceOf(ANode.class, result);
        assertEquals(-7.0, result.getValue().get(0));
    }

    @Test
    void parseDecimal() {
        Node result = Parser.parse("3.14");
        assertInstanceOf(ANode.class, result);
        assertEquals(3.14, result.getValue().get(0));
    }

    @Test
    void parseString() {
        Node result = Parser.parse("\"hello\"");
        assertInstanceOf(ANode.class, result);
        assertEquals("hello", result.getValue().get(0));
    }

    @Test
    void parseSymbol() {
        Node result = Parser.parse("+");
        assertInstanceOf(ANode.class, result);
        assertEquals("+", result.getValue().get(0));
    }

    @Test
    void parseSymbolName() {
        Node result = Parser.parse("define");
        assertInstanceOf(ANode.class, result);
        assertEquals("define", result.getValue().get(0));
    }
}
