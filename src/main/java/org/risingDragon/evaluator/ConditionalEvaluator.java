package org.risingDragon.evaluator;

import java.util.List;
import java.util.Objects;

public abstract class ConditionalEvaluator implements Evaluator {

    private static boolean validate(List<Object> operands) {
        if (operands.size() != 2) return true;
        if (!(operands.get(0) instanceof Number))
            throw new IllegalArgumentException();
        if (!(operands.get(1) instanceof Number))
            throw new IllegalArgumentException();
        return false;
    }

    public static class GEQ extends ConditionalEvaluator {

        @Override
        public Boolean eval(List<Object> operands) {
            if (validate(operands)) return false;
            return ((Double) operands.get(0)) >= ((Double) operands.get(1));
        }
    }

    public static class LEQ extends ConditionalEvaluator {

        @Override
        public Boolean eval(List<Object> operands) {
            if (validate(operands)) return false;
            return ((Double) operands.get(0)) <= ((Double) operands.get(1));
        }
    }

    public static class EQ extends ConditionalEvaluator {

        @Override
        public Boolean eval(List<Object> operands) {
            if (validate(operands)) return false;
            return Objects.equals(operands.get(0), operands.get(1));
        }
    }

    public static class GE extends ConditionalEvaluator {

        @Override
        public Boolean eval(List<Object> operands) {
            if (validate(operands)) return false;
            return ((Double) operands.get(0)) > ((Double) operands.get(1));
        }
    }

    public static class LE extends ConditionalEvaluator {

        @Override
        public Boolean eval(List<Object> operands) {
            if (validate(operands)) return false;
            return ((Double) operands.get(0)) < ((Double) operands.get(1));
        }
    }
}
