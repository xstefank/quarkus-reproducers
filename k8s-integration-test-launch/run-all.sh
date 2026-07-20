#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"

modes=(jar native docker)
failed=()

for mode in "${modes[@]}"; do
    echo "=== Running $mode ==="
    if ./mvnw verify -D"$mode"; then
        echo "=== $mode PASSED ==="
    else
        echo "=== $mode FAILED ==="
        failed+=("$mode")
    fi
done

if [ "${RUN_K8S:-}" = "1" ]; then
    echo "=== Running k8s ==="
    if ./mvnw verify -Dk8s; then
        echo "=== k8s PASSED ==="
    else
        echo "=== k8s FAILED ==="
        failed+=("k8s")
    fi
else
    echo "=== Skipping k8s (set RUN_K8S=1 once a cluster + registry are reachable to include it) ==="
fi

echo
if [ ${#failed[@]} -eq 0 ]; then
    echo "All modes passed."
else
    echo "Failed modes: ${failed[*]}"
    exit 1
fi
