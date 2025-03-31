#!/bin/bash

# pre-commit hooks
PRE_COMMIT_SOURCE="./config/.hooks/pre-commit"
PRE_COMMIT_TARGET="./.git/hooks/pre-commit"

# Remove any existing pre-commit hooks
if [ -e "$PRE_COMMIT_TARGET" ]; then
	echo "Removing existing file at $PRE_COMMIT_TARGET"
	rm "$PRE_COMMIT_TARGET"
fi

# Create a new symlink
cp "$PRE_COMMIT_SOURCE" "$PRE_COMMIT_TARGET"
echo "File copied from $PRE_COMMIT_SOURCE to $PRE_COMMIT_TARGET"

