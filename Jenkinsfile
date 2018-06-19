pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '7'))
    }

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
