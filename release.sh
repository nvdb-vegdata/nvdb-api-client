./gradlew clean release -Prelease.pushTagsOnly -Prelease.disableRemoteCheck
./gradlew build publishMavenJavaPublicationToArtrepoRepository bintrayUpload

