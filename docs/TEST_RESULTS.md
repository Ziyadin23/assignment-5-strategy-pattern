# Verification results

Verified locally on 24 October 2026 with OpenJDK Temurin 17.0.20.1.

| Check | Result |
| --- | --- |
| Compile production code | Pass with `--release 17 -Xlint:all -Werror` |
| Compile test code | Pass with the same strict compiler options |
| Core and console suite | 27 passed, 0 failed |
| Division extension suite | 7 passed, 0 failed |
| Total named tests | 34 passed, 0 failed |
| Default demo | One context produces `16.0`, `8.0`, `48.0` |
| Extension demo | One context produces `16.0`, `3.0`, `48.0` |
| Invalid launch argument | Usage message and exit status 2 |
| Shell syntax | `bash -n scripts/*.sh` passes |
| Patch whitespace | `git diff --check` passes |
| Original context inspection | No `if`, `switch`, or `instanceof` selection logic |
| Extension history inspection | Original six Java files unchanged by the division commit |

## Repeat the checks

```bash
./scripts/test.sh
./scripts/run.sh
./scripts/extension.sh
```

The raw output is saved in [test-output.txt](test-output.txt),
[demo-output.txt](demo-output.txt), and [extension-output.txt](extension-output.txt).

## What the tests establish

The core suite checks arithmetic with negative and fractional operands, operand
order, zero multiplication, persistent strategy selection, switching all
strategies and switching back, exact argument forwarding, an unfamiliar
implementation, null rejection, preservation of the old strategy after a rejected
replacement, and propagation of strategy errors. Console checks cover valid
commands, invalid command recovery, malformed numbers, wrong argument counts,
blank lines, case and whitespace, help, EOF, and rejection of non-finite input.

Division checks cover positive and negative operands, fractional results, a zero
numerator, both signed zero divisors, and runtime use by the original context.
The test runner uses explicit assertion failures, so it does not depend on `-ea`.

No automated coverage percentage, concurrency guarantee, performance benchmark,
or exact decimal precision is claimed. Calculations use Java `double` arithmetic.
