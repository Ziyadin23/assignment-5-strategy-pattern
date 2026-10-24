package edu.aitu.strategy;

/** Small dependency-free test runner; checks are active without Java's -ea flag. */
final class TestSupport {
    private int passed;
    private int failed;

    @FunctionalInterface
    interface TestCase {
        void run() throws Exception;
    }

    void test(String name, TestCase testCase) {
        try {
            testCase.run();
            passed++;
            System.out.println("PASS " + name);
        } catch (Exception | AssertionError failure) {
            failed++;
            System.err.println("FAIL " + name + ": " + failure);
        }
    }

    void finish() {
        System.out.printf("Tests: %d passed, %d failed%n", passed, failed);
        if (failed != 0) {
            throw new AssertionError("Test suite failed");
        }
    }

    static void closeTo(double expected, double actual) {
        if (!Double.isFinite(actual) || Math.abs(expected - actual) > 1e-10) {
            throw new AssertionError("Expected " + expected + ", got " + actual);
        }
    }

    static void contains(String output, String expected) {
        if (!output.contains(expected)) {
            throw new AssertionError("Missing '" + expected + "' in: " + output);
        }
    }

    static void throwsType(Class<? extends Exception> expected, TestCase action) throws Exception {
        try {
            action.run();
        } catch (Exception actual) {
            if (expected.isInstance(actual)) {
                return;
            }
            throw new AssertionError("Expected " + expected.getSimpleName() + ", got " + actual, actual);
        }
        throw new AssertionError("Expected " + expected.getSimpleName() + " to be thrown");
    }
}
