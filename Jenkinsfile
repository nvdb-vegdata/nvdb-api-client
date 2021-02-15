pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '7'))
    }

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build test cV'
            }
        }
        stage('Publish') {
            steps {
                sh './gradlew publishToMavenLocal publishMavenJavaPublicationToArtreposnapshotRepository'
            }
        }
    }
    post {
        failure {
           junit '**/build/*test-results/**/*.xml'
        }
        unstable {
           junit '**/build/*test-results/**/*.xml'
        }
    }
}
