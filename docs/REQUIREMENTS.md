# Requirements and delivery checklist

## Requirements from the assignment document

| Requirement | Delivered evidence |
| --- | --- |
| Java JDK 17 | `scripts/build.sh` uses `--release 17`; no external dependencies |
| One suggested topic | Option B Calculator |
| Common algorithm interface | `OperationStrategy.execute(double, double)` |
| At least three concrete strategies | `AddStrategy`, `SubtractStrategy`, `MultiplyStrategy` |
| Context with runtime replacement | `Calculator.setStrategy` and `Calculator.calculate` |
| Client selects the strategy | `Main` configures the context |
| No strategy type branches inside context | `Calculator` delegates through the interface |
| Five or more justified Clean Code principles with code | Six principles and excerpts in `REPORT.md` |
| Demonstrate extension without changing existing classes | `DivideStrategy`, `ExtensionDemo`, dedicated incremental commit |
| UML includes all pattern roles and client | `uml.mmd`, `uml.svg`, `uml.png`, and report |
| GitHub repository with compiling code and short README | Repository root and README |
| Incremental history | Separate implementation, test, extension, and documentation commits |
| Short Markdown reflection with introduction, UML, Clean Code, conclusion, repo link | `docs/REPORT.md` |
| Defense walkthrough and live runtime switch | `docs/DEFENSE.md`, demo, interactive mode |

The assignment gives the submission deadline as Sunday at 11:59 PM at the end
of Week 7 and the defense as the last practice class of Week 8. It explicitly
ties week numbers to the Moodle course calendar. No exact calendar date is
specified in the supplied file; check Moodle for your class dates.

## Additional requests from the user

| Request | Delivered evidence |
| --- | --- |
| Folder inside the home AITU directory and initialized repository | `/home/ziyadin/AITU/assignment-5-strategy-pattern`, Git branch `main` |
| Link to repository | README, report, and final delivery |
| Clear report | Markdown report and Word copy |
| Local defense material explaining pattern and UML | `docs/DEFENSE.md`, source code, local diagram images |
| Advantages and disadvantages | Report and defense guide |
| Tests | 34 named tests, repeatable offline through `scripts/test.sh` |

## Submission steps for the student

Read and understand the source and report, rehearse the live demonstration,
and personalize the reflection so that it accurately represents your own
understanding. The assignment requires individual work and an oral defense.
Submit the GitHub link and Markdown report to Moodle before the course deadline.
If the LMS preview cannot resolve local images, include `uml.png` with the
Markdown report or use the Word report for reading alongside the Markdown.
