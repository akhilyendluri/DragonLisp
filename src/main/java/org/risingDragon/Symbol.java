package org.risingDragon;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Symbol {
    BEGIN("begin"),
    DEFINE("define"),
    LAMBDA("lambda"),
    IF("if"),
    AND("and"),
    OR("or"),
    NOT("not"),
    PRINT("print"),
    LEQ("<="), // <=
    GEQ(">="), // >=
    EQ("="), // =
    LE("<"), // <
    GE(">"), // >
    ADDITION("+"), // +
    SUBTRACTION("-"), // -
    MULTIPLY("*"), // *
    DIVIDE("/"), // /
    MODULO("%"); // %

    final String value;
    final static Map<String, Symbol> SYMBOL_MAP = Stream.of(values()).collect(
            Collectors.toMap(Symbol::getValue, type -> type));

    Symbol(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Symbol resolve(String key) {
        if (SYMBOL_MAP.containsKey(key)) {
            return SYMBOL_MAP.get(key);
        }
        throw new IllegalArgumentException();
    }
}
