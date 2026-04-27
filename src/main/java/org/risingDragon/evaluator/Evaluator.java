package org.risingDragon.evaluator;

import java.util.List;

public interface Evaluator {
    /**
     * Evaluates on the operands and returns result
     * @param operands the data to be evaluated
     * @return result of the evaluation
     */
    Object eval(List<Object> operands);
}
