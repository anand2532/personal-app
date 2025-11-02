#!/bin/bash
# Script to create a feature branch and PR workflow

if [ -z "$1" ]; then
    echo "Usage: ./create-pr.sh <feature-name>"
    echo "Example: ./create-pr.sh add-new-feature"
    exit 1
fi

FEATURE_NAME=$1
BRANCH_NAME="feature/$FEATURE_NAME"

# Create and checkout new branch
git checkout -b $BRANCH_NAME

echo "✓ Created branch: $BRANCH_NAME"
echo ""
echo "Now you can:"
echo "1. Make your changes"
echo "2. Commit: git add . && git commit -m 'feat: Your commit message'"
echo "3. Push: git push -u origin $BRANCH_NAME"
echo "4. Create PR on GitHub or use: gh pr create --title 'Your PR Title' --body 'Description'"

