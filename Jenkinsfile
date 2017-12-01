pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build test'
                junit 'build/test-results/**/*.xml'
            }
        }
        stage('Publish') {
            steps {
                sh './gradlew publishToMavenLocal publishMavenJavaPublicationToSnapshotRepository'
            }
        }
    }
}
