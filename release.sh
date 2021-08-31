if ./gradlew clean build verifyRelease | grep -q 'FAILED'; then
  echo 'Failed to verify build - fix these errors before uploading'
else
  ./gradlew uploadArchives
fi
