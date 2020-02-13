./gradlew clean release -Prelease.pushTagsOnly -Prelease.disableRemoteCheck
./gradlew build publishMavenJavaPublicationToReleaseRepository bintrayUpload

