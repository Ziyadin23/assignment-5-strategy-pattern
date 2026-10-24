package edu.aitu.strategy;

import edu.aitu.strategy.extension.DivideStrategy;

import static edu.aitu.strategy.TestSupport.closeTo;
import static edu.aitu.strategy.TestSupport.throwsType;

public final class DivisionStrategyTest {
    private DivisionStrategyTest() {
    }

    public static void main(String[] args) {
        TestSupport tests = new TestSupport();
        tests.test("division of positive operands", () -> closeTo(3, new DivideStrategy().execute(12, 4)));
        tests.test("division with a fractional result", () -> closeTo(0.5, new DivideStrategy().execute(1, 2)));
        tests.test("division with a negative operand", () -> closeTo(-3, new DivideStrategy().execute(-12, 4)));
        tests.test("division with a zero numerator", () -> closeTo(0, new DivideStrategy().execute(0, 4)));
        tests.test("positive zero divisor is rejected", () ->
                throwsType(ArithmeticException.class, () -> new DivideStrategy().execute(12, 0.0)));
        tests.test("negative zero divisor is rejected", () ->
                throwsType(ArithmeticException.class, () -> new DivideStrategy().execute(12, -0.0)));
        tests.test("one original context accepts the extension and switches back", () -> {
            Calculator calculator = new Calculator(new AddStrategy());
            closeTo(16, calculator.calculate(12, 4));
            calculator.setStrategy(new DivideStrategy());
            closeTo(3, calculator.calculate(12, 4));
            calculator.setStrategy(new MultiplyStrategy());
            closeTo(48, calculator.calculate(12, 4));
        });
        tests.finish();
    }
}
