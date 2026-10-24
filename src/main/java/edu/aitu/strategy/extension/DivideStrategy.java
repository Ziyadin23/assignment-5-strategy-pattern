package edu.aitu.strategy.extension;

import edu.aitu.strategy.OperationStrategy;

/** An extension with a domain rule: division by either signed zero is invalid. */
public final class DivideStrategy implements OperationStrategy {
    @Override
    public double execute(double left, double right) {
        if (right == 0.0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return left / right;
    }
}
