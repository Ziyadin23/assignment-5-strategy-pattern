package edu.aitu.strategy;

/** A binary arithmetic operation using Java double arithmetic. */
@FunctionalInterface
public interface OperationStrategy {
    double execute(double left, double right);
}
