#!/usr/bin/env bash
set -euo pipefail
PROJECT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$PROJECT_DIR"
rm -rf -- out/main
mkdir -p out/main
find src/main/java -name '*.java' -print | sort > out/main-sources.txt
javac --release 17 -Xlint:all -Werror -d out/main @out/main-sources.txt
