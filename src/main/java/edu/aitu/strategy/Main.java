package edu.aitu.strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

/** Client: chooses strategies and handles console input and output. */
public final class Main {
    private static final Map<String, OperationStrategy> OPERATIONS = Map.of(
            "add", new AddStrategy(),
            "subtract", new SubtractStrategy(),
            "multiply", new MultiplyStrategy());

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        PrintWriter output = new PrintWriter(System.out, true, StandardCharsets.UTF_8);
        if (args.length == 0 || (args.length == 1 && "--demo".equals(args[0]))) {
            runDemo(output);
        } else if (args.length == 1 && "--interactive".equals(args[0])) {
            runInteractive(new BufferedReader(
                    new InputStreamReader(System.in, StandardCharsets.UTF_8)), output);
        } else {
            System.err.println("Usage: ./scripts/run.sh [--demo|--interactive]");
            System.exit(2);
        }
    }

    static void runDemo(PrintWriter output) {
        Calculator calculator = new Calculator(new AddStrategy());
        output.println("One Calculator object; operands stay 12 and 4.");
        output.println("AddStrategy: " + calculator.calculate(12, 4));
        calculator.setStrategy(new SubtractStrategy());
        output.println("SubtractStrategy: " + calculator.calculate(12, 4));
        calculator.setStrategy(new MultiplyStrategy());
        output.println("MultiplyStrategy: " + calculator.calculate(12, 4));
    }

    static void runInteractive(BufferedReader input, PrintWriter output) throws IOException {
        Calculator calculator = new Calculator(new AddStrategy());
        output.println("Strategy calculator. One context is reused for every command.");
        printHelp(output);
        while (true) {
            output.print("> ");
            output.flush();
            String line = input.readLine();
            if (line == null || "quit".equalsIgnoreCase(line.trim())) {
                output.println("Goodbye.");
                return;
            }
            if (line.isBlank()) {
                continue;
            }
            if ("help".equalsIgnoreCase(line.trim())) {
                printHelp(output);
                continue;
            }
            executeCommand(line, calculator, output);
        }
    }

    private static void executeCommand(String line, Calculator calculator, PrintWriter output) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) {
            output.println("Expected: <add|subtract|multiply> <left> <right>");
            return;
        }
        String operationName = parts[0].toLowerCase(Locale.ROOT);
        OperationStrategy selected = OPERATIONS.get(operationName);
        if (selected == null) {
            output.println("Unknown operation: " + parts[0]);
            return;
        }
        try {
            double left = Double.parseDouble(parts[1]);
            double right = Double.parseDouble(parts[2]);
            if (!Double.isFinite(left) || !Double.isFinite(right)) {
                output.println("Operands must be finite numbers.");
                return;
            }
            calculator.setStrategy(selected);
            output.println(operationName + " => " + calculator.calculate(left, right));
        } catch (NumberFormatException exception) {
            output.println("Invalid number. Example: add 12.5 4");
        }
    }

    private static void printHelp(PrintWriter output) {
        output.println("Commands: add 12 4 | subtract 12 4 | multiply 12 4 | help | quit");
    }
}
