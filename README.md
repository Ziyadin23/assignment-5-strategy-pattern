# Assignment 5 Strategy Pattern

Java 17 calculator for Software Design Patterns at Astana IT University.
Addition, subtraction, and multiplication share one strategy interface.
One `Calculator` object can change its operation at runtime.

## Run locally

Only a JDK 17 or newer and Bash are needed. There are no downloaded libraries.

```bash
./scripts/run.sh
./scripts/run.sh --interactive
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
