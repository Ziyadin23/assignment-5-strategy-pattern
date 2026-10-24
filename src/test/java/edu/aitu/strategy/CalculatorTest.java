package edu.aitu.strategy;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static edu.aitu.strategy.TestSupport.closeTo;
import static edu.aitu.strategy.TestSupport.contains;
import static edu.aitu.strategy.TestSupport.throwsType;

public final class CalculatorTest {
    private CalculatorTest() {
    }

    public static void main(String[] args) {
        TestSupport tests = new TestSupport();
        tests.test("addition of positive operands", () -> closeTo(16, new AddStrategy().execute(12, 4)));
        tests.test("addition with negative operands", () -> closeTo(-9, new AddStrategy().execute(-12, 3)));
        tests.test("addition of fractions", () -> closeTo(0.3, new AddStrategy().execute(0.1, 0.2)));
        tests.test("subtraction preserves operand order", () -> closeTo(-8, new SubtractStrategy().execute(4, 12)));
        tests.test("subtraction of negatives", () -> closeTo(-8, new SubtractStrategy().execute(-12, -4)));
        tests.test("subtraction of fractions", () -> closeTo(1.25, new SubtractStrategy().execute(2.5, 1.25)));
        tests.test("multiplication with a negative operand", () -> closeTo(-48, new MultiplyStrategy().execute(-12, 4)));
        tests.test("multiplication by zero", () -> closeTo(0, new MultiplyStrategy().execute(12, 0)));
        tests.test("multiplication of fractions", () -> closeTo(1.25, new MultiplyStrategy().execute(2.5, 0.5)));
        tests.test("context reuses the selected strategy", () -> {
            Calculator calculator = new Calculator(new AddStrategy());
            closeTo(16, calculator.calculate(12, 4));
            closeTo(5, calculator.calculate(2, 3));
        });
        tests.test("one context switches all strategies and switches back", () -> {
            Calculator calculator = new Calculator(new AddStrategy());
            closeTo(16, calculator.calculate(12, 4));
            calculator.setStrategy(new SubtractStrategy());
            closeTo(8, calculator.calculate(12, 4));
            calculator.setStrategy(new MultiplyStrategy());
            closeTo(48, calculator.calculate(12, 4));
            calculator.setStrategy(new AddStrategy());
            closeTo(16, calculator.calculate(12, 4));
        });
        tests.test("context forwards both operands exactly once", () -> {
            RecordingStrategy strategy = new RecordingStrategy();
            closeTo(99, new Calculator(strategy).calculate(7, -2));
            closeTo(7, strategy.left);
            closeTo(-2, strategy.right);
            closeTo(1, strategy.calls);
        });
        tests.test("context accepts an unfamiliar implementation", () -> {
            Calculator calculator = new Calculator((left, right) -> Math.max(left, right));
            closeTo(12, calculator.calculate(12, 4));
        });
        tests.test("constructor rejects a null strategy", () ->
                throwsType(NullPointerException.class, () -> new Calculator(null)));
        tests.test("rejected null replacement preserves the old strategy", () -> {
            Calculator calculator = new Calculator(new AddStrategy());
            throwsType(NullPointerException.class, () -> calculator.setStrategy(null));
            closeTo(16, calculator.calculate(12, 4));
        });
        tests.test("context propagates a strategy failure", () -> {
            Calculator calculator = new Calculator((left, right) -> {
                throw new ArithmeticException("Test strategy failure");
            });
            throwsType(ArithmeticException.class, () -> calculator.calculate(12, 4));
        });
        tests.test("interactive mode switches algorithms", () -> {
            String output = interactive("add 12 4\nsubtract 12 4\nmultiply 12 4\nquit\n");
            contains(output, "add => 16.0");
            contains(output, "subtract => 8.0");
            contains(output, "multiply => 48.0");
            contains(output, "Goodbye.");
        });
        tests.test("unknown operation does not prevent the next calculation", () -> {
            String output = interactive("power 12 4\nadd 2 3\nquit\n");
            contains(output, "Unknown operation: power");
            contains(output, "add => 5.0");
        });
        tests.test("malformed number does not prevent the next calculation", () -> {
            String output = interactive("add apple 4\nsubtract 9 2\nquit\n");
            contains(output, "Invalid number.");
            contains(output, "subtract => 7.0");
        });
        tests.test("wrong number of arguments is explained", () ->
                contains(interactive("add 12\nquit\n"), "Expected:"));
        tests.test("blank lines are ignored", () ->
                contains(interactive("\n  \nadd 2 3\nquit\n"), "add => 5.0"));
        tests.test("end of input exits cleanly", () -> contains(interactive(""), "Goodbye."));
        tests.test("commands accept case and whitespace variations", () ->
                contains(interactive("  MuLtIpLy\t3  4  \nQUIT\n"), "multiply => 12.0"));
        tests.test("help allows calculation afterwards", () -> {
            String output = interactive("help\nadd 1 2\nquit\n");
            contains(output, "Commands:");
            contains(output, "add => 3.0");
        });
        tests.test("NaN is rejected at the console boundary", () ->
                contains(interactive("add NaN 4\nquit\n"), "Operands must be finite"));
        tests.test("infinite input is rejected at the console boundary", () -> {
            String output = interactive("add 4 -Infinity\nadd 1e309 4\nquit\n");
            contains(output, "Operands must be finite");
            if (output.contains("add =>")) {
                throw new AssertionError("Non-finite operands reached calculation");
            }
        });
        tests.test("default demonstration shows the expected results", () -> {
            StringWriter text = new StringWriter();
            Main.runDemo(new PrintWriter(text, true));
            contains(text.toString(), "AddStrategy: 16.0");
            contains(text.toString(), "SubtractStrategy: 8.0");
            contains(text.toString(), "MultiplyStrategy: 48.0");
        });
        tests.finish();
    }

    private static String interactive(String commands) throws Exception {
        StringWriter text = new StringWriter();
        Main.runInteractive(new BufferedReader(new StringReader(commands)), new PrintWriter(text, true));
        return text.toString();
    }

    private static final class RecordingStrategy implements OperationStrategy {
        private double left;
        private double right;
        private int calls;

        @Override
        public double execute(double left, double right) {
            this.left = left;
            this.right = right;
            calls++;
            return 99;
        }
    }
}
