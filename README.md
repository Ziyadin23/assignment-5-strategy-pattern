# Assignment 5 Strategy Pattern

Java 17 calculator for Software Design Patterns at Astana IT University.
Addition, subtraction, and multiplication share one strategy interface.
One `Calculator` object can change its operation at runtime.

## Run locally

Only a JDK 17 or newer and Bash are needed. There are no downloaded libraries.

```bash
./scripts/run.sh
./scripts/run.sh --interactive
./scripts/extension.sh
./scripts/test.sh
```

The default demo applies three strategies to the same operands, `12` and `4`.
Interactive mode accepts `add 12 4`, `subtract 12 4`, `multiply 12 4`,
`help`, and `quit`.

## Structure

```text
src/main/java/edu/aitu/strategy/   interface, strategies, context, client
src/test/java/edu/aitu/strategy/   executable tests
scripts/                         compile, run, test
docs/                            report, UML, defense notes
```

The context delegates to `OperationStrategy.execute` and never selects an
algorithm by its concrete type. Input validation and command selection belong
to the client.

## Report and defense

- [Markdown report](docs/REPORT.md) for Moodle, including six Clean Code principles.
- [Defense guide](docs/DEFENSE.md) with a five minute walkthrough and questions.
- [UML class diagram](docs/uml.png) and [editable Mermaid source](docs/uml.mmd).
- [Requirements checklist](docs/REQUIREMENTS.md) separates assignment requirements from additional work.
- [Test results](docs/TEST_RESULTS.md) records verification and limits.

The division extension has a separate client, `ExtensionDemo`. Run
`./scripts/extension.sh` to see it use the same original context. Division is
deliberately outside the original interactive command registry so the extension
can be added without modifying `Main` or any original strategy.

## IntelliJ IDEA

Open this folder, select Project SDK 17, and mark `src/main/java` as Sources Root
and `src/test/java` as Test Sources Root if IDEA does not detect them. Run
`edu.aitu.strategy.Main` for the demo; use `--interactive` as its program argument
for console mode. Tests also run as Java main classes: `CalculatorTest` and
`DivisionStrategyTest`. No Maven, Gradle, JUnit, or network access is required.

## Numeric behavior

Operations use Java `double` arithmetic, including rounding and overflow.
For example, `0.1 + 0.2` may print `0.30000000000000004`; sufficiently large
finite operands can produce infinity. The console rejects non-finite input.
`DivideStrategy` throws `ArithmeticException` for either signed zero divisor.
The core context forwards operands and exceptions to and from the strategy.
This is a single-threaded teaching example, not an exact decimal calculator.

Repository: https://github.com/Ziyadin23/assignment-5-strategy-pattern
