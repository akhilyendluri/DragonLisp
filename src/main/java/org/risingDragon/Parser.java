package org.risingDragon;

import org.risingDragon.tree.ANode;
import org.risingDragon.tree.AType;
import org.risingDragon.tree.LNode;
import org.risingDragon.tree.Node;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final char LIST_BEGIN = '(';
    private static final char LIST_END = ')';
    private static final char SPACE = ' ';
    private static final char QUOTATION_MARK = '"';
    private static final char NEGATIVE = '-';
    private static final char DECIMAL = '.';
    private static final char EXPONENT_UPPER = 'E';
    private static final char EXPONENT_LOWER = 'e';
    private int position = 0;
    private final char[] tokens;

    private Parser(String input) {
        this.tokens = input.toCharArray();
    }

    public static Node parse(String input) {
        Parser parser = new Parser(input);
        return parser.parseExpression();
    }

    private Node parseExpression() {
        if (!hasNextSkipSpaces()) return null;
        char token = readNext();
        if (token == LIST_BEGIN) {
            List<Node> result = new ArrayList<>();
            position++;
            if (!hasNextSkipSpaces()) return null;
            while (hasNextSkipSpaces() && readNext() != LIST_END) {
                result.add(parseExpression());
            }
            position++;
            return new LNode<>(result);
        }
        return parseAtom();
    }

    private Node parseAtom() {
        Node result;
        if (readNext() == QUOTATION_MARK) {
            position++;
            result = parseString();
            if (readNext() != QUOTATION_MARK) throw new IllegalArgumentException();
            position++;
        } else if (Character.isDigit(readNext())) {
            result = parseNumber();
        } else if (readNext() == NEGATIVE) {
            position++;
            if (Character.isDigit(readNext())) {
                double value = (double) parseNumber().getValue().getFirst();
                return new ANode<>(-1 * value, AType.number);
            } else if (readNext() == SPACE) {
                result = new ANode<>(NEGATIVE, AType.symbol);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            result = parseSymbol();
        }
        return result;
    }

    private Node parseString() {
        StringBuilder sb = new StringBuilder();
        while (hasNext() && readNext() != QUOTATION_MARK) {
            sb.append(readNext());
            position++;
        }
        return new ANode<>(sb.toString(), AType.string);
    }

    private Node parseNumber() {
        StringBuilder sb = new StringBuilder();
        while (hasNext() && (
                Character.isDigit(readNext()) || readNext() == DECIMAL
                        || readNext() == EXPONENT_LOWER || readNext() == EXPONENT_UPPER)) {
            sb.append(readNext());
            position++;
        }
        return new ANode<>(Double.parseDouble(sb.toString()), AType.number);
    }

    private Node parseSymbol() {
        StringBuilder sb = new StringBuilder();
        while (hasNext() && (readNext() != SPACE && readNext() != LIST_END && readNext() != LIST_BEGIN)) {
            sb.append(readNext());
            position++;
        }
        return new ANode<>(sb.toString(), AType.symbol);
    }

    private boolean hasNext() {
        return position < tokens.length;
    }

    private boolean hasNextSkipSpaces() {
        while (position < tokens.length && tokens[position] == SPACE) position++;
        return position < tokens.length;
    }

    private char readNext() {
        return tokens[position];
    }
}
