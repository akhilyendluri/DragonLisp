package org.risingDragon.evaluator;

import org.risingDragon.Symbol;

import java.util.Map;

public class EvaluatorRegistry {
    private final Map<Symbol, Evaluator> registry;

    public EvaluatorRegistry () {
        this.registry = Map.ofEntries(
                Map.entry(Symbol.ADDITION, new ArithmeticEvaluator.AdditionEvaluator()),
                Map.entry(Symbol.SUBTRACTION, new ArithmeticEvaluator.SubtractionEvaluator()),
                Map.entry(Symbol.MULTIPLY, new ArithmeticEvaluator.MultiplicationEvaluator()),
                Map.entry(Symbol.DIVIDE, new ArithmeticEvaluator.DivisionEvaluator()),
                Map.entry(Symbol.MODULO, new ArithmeticEvaluator.ModuloEvaluator()),
                Map.entry(Symbol.GEQ, new ConditionalEvaluator.GEQ()),
                Map.entry(Symbol.LEQ, new ConditionalEvaluator.LEQ()),
                Map.entry(Symbol.EQ, new ConditionalEvaluator.EQ()),
                Map.entry(Symbol.GE, new ConditionalEvaluator.GE()),
                Map.entry(Symbol.LE, new ConditionalEvaluator.LE())
        );
    }

    public Evaluator getEvaluator(Symbol symbol) {
        return registry.get(symbol);
    }
}
