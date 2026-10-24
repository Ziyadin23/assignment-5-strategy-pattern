package edu.aitu.strategy;

public final class MultiplyStrategy implements OperationStrategy {
    @Override
    public double execute(double left, double right) {
        return left * right;
    }
}
