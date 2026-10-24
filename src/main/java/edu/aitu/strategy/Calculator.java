package edu.aitu.strategy;

import java.util.Objects;

/** Context: stores the active strategy and delegates the calculation to it. */
public final class Calculator {
    private OperationStrategy strategy;

    public Calculator(OperationStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(OperationStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Strategy must not be null");
    }

    public double calculate(double left, double right) {
        return strategy.execute(left, right);
    }
}
