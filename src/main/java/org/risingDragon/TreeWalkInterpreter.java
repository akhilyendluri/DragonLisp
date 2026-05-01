package org.risingDragon;

import org.risingDragon.evaluator.EvaluatorRegistry;
import org.risingDragon.tree.ANode;
import org.risingDragon.tree.LNode;
import org.risingDragon.tree.Node;

import java.util.ArrayList;
import java.util.List;

public class TreeWalkInterpreter {
    private final EvaluatorRegistry evaluatorRegistry;

    private TreeWalkInterpreter() {
        this.evaluatorRegistry = new EvaluatorRegistry();
    }

    public static Object eval(final Node root) {
        return new TreeWalkInterpreter().evalInternal(root, new Environment(null));
    }

    private Object evalInternal(final Node node, Environment environment) {
        if (node instanceof LNode<?>) {
            return evalList((LNode<?>) node, environment);
        }
        return evalAtom((ANode<?>) node, environment);
    }

    private Object evalList(final LNode<?> node, Environment environment) {
        Node control = node.getValue().getFirst();

        // Special case which is possible only if it is a Lambda
        if (control instanceof LNode<?>) {
             return executeLambda(node, environment);
        }

        // Check and execute Function
        if (evalAtom((ANode<?>) control, environment) instanceof Function function) {
            return executeFunctionCommon(node, function, environment);
        }

        // Remaining are all builtInSymbols
        Symbol symbol = evalBuiltInSymbol(control);
        return switch (symbol) {
            case DEFINE -> processDefine(node, environment);
            case BEGIN -> processBegin(node, environment);
            case IF -> processIf(node, environment);
            case LAMBDA -> processLambda(node, environment);
            case PRINT -> processPrint(node, environment);
            case AND -> processAnd(node, environment);
            case OR -> processOr(node, environment);
            case NOT -> processNot(node, environment);
            default -> {
                List<Object> operands = new ArrayList<>();
                for (int i = 1; i < node.getValue().size(); i++) {
                    operands.add(evalInternal(node.getValue().get(i), environment));
                }
                yield evaluatorRegistry.getEvaluator(symbol).eval(operands);
            }
        };
    }

    private Object executeFunctionCommon(LNode<?> node, Function function, Environment environment) {
        List<Object> operands = new ArrayList<>();
        for (int i = 1; i < node.getValue().size(); i++)
            operands.add(evalInternal(node.getValue().get(i), environment));
        Environment childEnv = initializeChildEnvironment(function, operands);
        return evalInternal(function.body, childEnv);
    }

    private Void processDefine(final LNode<?> node, Environment environment) {
        if (!(node.getValue().get(1) instanceof ANode)) {
            throw new IllegalArgumentException();
        }
        String key = getAtom((ANode<?>) node.getValue().get(1)).toString();
        Object value = evalInternal(node.getValue().get(2), environment);
        environment.define(key, value);
        return null;
    }

    private Object processBegin(final LNode<?> node, Environment environment) {
        Object result = null;
        for (int i = 1; i < node.getValue().size(); i++) {
            result = evalInternal(node.getValue().get(i), environment);
        }
        return result;
    }

    private Object processIf(final LNode<?> node, Environment environment) {
        if (node.getValue().size() != 4)
            throw new IllegalArgumentException();
        boolean condition = (boolean) evalInternal(node.getValue().get(1), environment);
        if (condition) {
            return evalInternal(node.getValue().get(2), environment);
        }
        return evalInternal(node.getValue().get(3), environment);
    }

    private Object processLambda(final LNode<?> node, Environment environment) {
        Node parameters = node.getValue().get(1);
        Node body = node.getValue().get(2);
        return new Function(parameters, body, environment);
    }

    private Void processPrint(final LNode<?> node, Environment environment) {
        if (node.getValue().size() != 2)
            throw new IllegalArgumentException();
        String output = evalInternal(node.getValue().get(1), environment).toString();
        System.out.println(output);
        return null;
    }

    private boolean processAnd(final LNode<?> node, Environment environment) {
        for (int i = 1; i < node.getValue().size(); i++) {
            Object result = evalInternal(node.getValue().get(i), environment);
            if (!(result instanceof Boolean))
                throw new IllegalArgumentException();
            if (!(boolean) result) {
                return false;
            }
        }
        return true;
    }

    private boolean processOr(final LNode<?> node, Environment environment) {
        for (int i = 1; i < node.getValue().size(); i++) {
            Object result = evalInternal(node.getValue().get(i), environment);
            if (!(result instanceof Boolean))
                throw new IllegalArgumentException();
            if ((boolean) result) {
                return true;
            }
        }
        return false;
    }

    private boolean processNot(final LNode<?> node, Environment environment) {
        if (node.getValue().size() != 2)
            throw new IllegalArgumentException();
        Object result = evalInternal(node.getValue().get(1), environment);
        if (!(result instanceof Boolean))
            throw new IllegalArgumentException();
        return !(boolean) result;
    }

    private Object executeLambda(final LNode<?> node, Environment environment) {
        Node control = node.getValue().getFirst();
        Function function = (Function) evalList((LNode<?>) control, environment);
        if ((function.parameters.getValue().size() + 1) != node.getValue().size())
            throw new IllegalArgumentException();
        return executeFunctionCommon(node, function, environment);
    }

    private Environment initializeChildEnvironment(Function function, List<Object> params) {
        if (function.parameters.getValue().size() != params.size())
            throw new IllegalArgumentException();
        List<?> operands = function.parameters.getValue();
        Environment childEnv = new Environment(function.environment);
        for (int i = 0; i < params.size(); i++) {
            if (!(operands.get(i) instanceof ANode<?>))
                throw new IllegalArgumentException();
            childEnv.define(getAtom((ANode<?>) operands.get(i)).toString(), params.get(i));
        }
        return childEnv;
    }

    private Object getAtom(final ANode<?> node) {
        return node.getValue().getFirst();
    }

    private Object evalAtom(final ANode<?> node, Environment environment) {
        return environment.lookUp(node);
    }

    private Symbol evalBuiltInSymbol(final Node node) {
        if (!(node instanceof ANode<?>)) {
            throw new IllegalArgumentException();
        }
        return Symbol.resolve(node.getValue().getFirst().toString());
    }

    private record Function(Node parameters, Node body, Environment environment) {}
}
