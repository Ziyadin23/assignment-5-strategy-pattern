#!/usr/bin/env bash
set -euo pipefail
PROJECT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$PROJECT_DIR"
./scripts/build.sh
mkdir -p out/test
find src/test/java -name '*.java' -print | sort > out/test-sources.txt
javac --release 17 -Xlint:all -Werror -cp out/main -d out/test @out/test-sources.txt
java -cp out/main:out/test edu.aitu.strategy.CalculatorTest
