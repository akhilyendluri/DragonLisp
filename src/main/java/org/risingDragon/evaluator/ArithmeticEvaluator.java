package org.risingDragon.evaluator;

import java.util.List;

public abstract class ArithmeticEvaluator implements Evaluator {

    public static class AdditionEvaluator extends ArithmeticEvaluator {

        @Override
        public Double eval(List<Object> operands) {
            double result = 0;
            for (Object number : operands) {
                result = result + (double) number;
            }
            return result;
        }
    }

    public static class SubtractionEvaluator extends ArithmeticEvaluator {

        @Override
        public Double eval(List<Object> operands) {
            double result = (double) operands.getFirst();
            for (int i = 1; i < operands.size(); i++) {
                result = result - (double) operands.get(i);
            }
            return result;
        }
    }

    public static class MultiplicationEvaluator extends ArithmeticEvaluator {

        @Override
        public Double eval(List<Object> operands) {
            double result = 1;
            for (Object number : operands) {
                result = result * (double) number;
            }
            return result;
        }
    }

    public static class DivisionEvaluator extends ArithmeticEvaluator {

        @Override
        public Double eval(List<Object> operands) {
            double result = (double) operands.getFirst();
            for (int i = 1; i < operands.size(); i++) {
                result = result / (double) operands.get(i);
            }
            return result;
        }
    }

    public static class ModuloEvaluator extends ArithmeticEvaluator {

        @Override
        public Double eval(List<Object> operands) {
            double result = (double) operands.getFirst();
            for (int i = 1; i < operands.size(); i++) {
                result = result % (double) operands.get(i);
            }
            return result;
        }
    }
}
