if ./gradlew clean release -Prelease.pushTagsOnly -Prelease.disableRemoteCheck | grep -q 'FAILED'; then
  echo 'Failed to verify build - fix these errors before uploading'
else
  ./gradlew build publishMavenJavaPublicationToArtrepoRepository
fi

