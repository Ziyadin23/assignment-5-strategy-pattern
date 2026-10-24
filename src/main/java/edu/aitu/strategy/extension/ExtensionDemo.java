package edu.aitu.strategy.extension;

import edu.aitu.strategy.AddStrategy;
import edu.aitu.strategy.Calculator;
import edu.aitu.strategy.MultiplyStrategy;

/** New client wiring the extension without editing the original application. */
public final class ExtensionDemo {
    private ExtensionDemo() {
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator(new AddStrategy());
        System.out.println("One Calculator object; an extra strategy needs no context changes.");
        System.out.println("AddStrategy: " + calculator.calculate(12, 4));
        calculator.setStrategy(new DivideStrategy());
        System.out.println("DivideStrategy: " + calculator.calculate(12, 4));
        calculator.setStrategy(new MultiplyStrategy());
        System.out.println("MultiplyStrategy: " + calculator.calculate(12, 4));
    }
}
