# Strategy Pattern Defense Guide

## Before the class

Open the project in IntelliJ IDEA with JDK 17, or use a terminal. Everything
needed to run the program and tests is local. Keep `uml.png` and `REPORT.md`
open beside the code. A local Word report is also included as `REPORT.docx`.

```bash
cd ~/AITU/assignment-5-strategy-pattern
./scripts/test.sh
./scripts/run.sh
```

Expected summary: `27 passed, 0 failed`, then `7 passed, 0 failed`.
Default demo results: `16.0`, `8.0`, `48.0`.

## A five minute walkthrough

### First minute explain the problem

Say in your own words: "My topic is a calculator. Addition, subtraction, and
multiplication are different algorithms with the same inputs and return type.
Strategy lets the calculator delegate to one selected algorithm and replace
it while the program runs."

Use a familiar analogy if helpful: one navigator can plan a walking route or
a driving route. The navigator stays the same while its route algorithm changes.

### Second minute explain the UML

![Calculator Strategy UML](uml.png)

Point to the four pattern roles:

1. `OperationStrategy` is the common contract: `execute(left, right)` returns a `double`.
2. The three concrete classes implement that contract with `+`, `-`, and `*`.
3. `Calculator` stores one `OperationStrategy`; its `calculate` method delegates.
4. `Main` creates the concrete strategies and chooses which one to pass to the context.

Read the arrows: a solid reference from the context to the interface, dashed
hollow triangles from implementations to the interface, and dashed client
dependencies on the context. `1` means one active strategy per calculator.
There is no composition diamond because the client supplies the strategy and
the same stateless object could be shared. The client creation dependencies
are summarized in the caption to keep the rendered diagram readable.

### Third minute demonstrate switching

```bash
./scripts/run.sh --interactive
```

Type these commands individually:

```text
add 12 4
subtract 12 4
multiply 12 4
add 12 4
quit
```

Expected results: `16.0`, `8.0`, `48.0`, `16.0`. Open `Main.runInteractive`
and show that `new Calculator(...)` occurs before the input loop. Open
`executeCommand` and point to `calculator.setStrategy(selected)`. The same
context receives a different strategy reference; it is not recreated per command.

Then show `Calculator.calculate`: the only operation is
`return strategy.execute(left, right);`. Java calls the implementation belonging
to the selected object through polymorphism.

### Fourth minute demonstrate extension and tests

```bash
./scripts/extension.sh
git show cb1a02e --stat
./scripts/test.sh
```

The extension prints `16.0`, `3.0`, `48.0`. It adds `DivideStrategy` and
`ExtensionDemo` without modifying any original Java class. The test script
also changes to run the new tests. The original interactive menu has only
the original three operations; division is wired by the separate extension client.

Explain three useful tests: one context switches through all operations and
back; a recording strategy proves both operands are forwarded exactly once;
both `0.0` and `-0.0` divisors raise `ArithmeticException` in division.

### Fifth minute explain the tradeoff

The advantages are runtime replacement, isolated algorithms, and a context
that accepts new implementations without modification. The disadvantages are
more classes, extra indirection, and the client's responsibility to select an
algorithm. For three permanently fixed arithmetic operations this structure
can be excessive, but it is useful when algorithms change independently.

Mention at least five principles: polymorphism, meaningful names, single
responsibility, dependency inversion, and open/closed extensibility. Use the
actual code excerpts in the report rather than reciting definitions alone.

## Likely instructor questions

**Why is this Strategy?** The context stores a common interface and delegates
to a replaceable algorithm. The client chooses the implementation at runtime.

**Where does strategy selection happen?** In `Main`, through a command map
or explicit demo calls. `Calculator` has no knowledge of concrete strategy types.

**Do all conditionals disappear?** No. Parsing, validation, loop control,
and the division zero guard still need conditions. The context's algorithm
selection is replaced by polymorphic dispatch.

**What is composition here?** `Calculator` has a strategy reference instead
of inheriting a specific arithmetic operation. This use of object composition
does not require a UML composition diamond or exclusive ownership.

**How would you add another operation?** Implement `OperationStrategy`, test
the algorithm, and pass its object to `setStrategy`. A user interface needs
appropriate client wiring or registry configuration. The context stays unchanged.

**Why use an interface instead of a superclass?** The strategies share a
contract but do not need shared implementation or inherited state.

**How does Strategy differ from State?** Strategy represents client-selected
alternative algorithms. State represents behavior associated with an object's
internal state and often manages transitions as that state changes.

**How does Strategy differ from Factory?** A factory focuses on creating an
object; Strategy focuses on replacing the algorithm used by a context. They
can be used together.

**Could a lambda be a strategy?** Yes. This interface has one abstract method,
so a lambda can implement it. Named classes make the required roles explicit.
One test supplies a lambda to prove the context accepts an unfamiliar implementation.

**Why reject a null strategy?** A calculator must always have an executable
algorithm. Constructor and setter enforce that invariant. A failed replacement
leaves the previous strategy intact.

**Why is there an if in DivideStrategy?** It enforces that algorithm's zero
divisor rule. It does not choose between algorithm types inside the context.

**Why no JUnit?** The tests use explicit checks that run with the JDK alone.
They print individual results and fail the process on any failed check. This
keeps defense preparation independent of network access and dependency downloads.

**What numeric limitations exist?** `double` has rounding and overflow. The
console rejects non-finite operands; valid large operands may still overflow.
This example does not promise exact decimal arithmetic or thread safety.

## Short version to remember

"The client selects. The context stores and delegates. The strategy executes.
Replacing the strategy changes behavior on the same context object."
