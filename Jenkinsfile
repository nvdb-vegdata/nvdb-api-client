pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '7'))
    }

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build test'
            }
        }
        stage('Publish') {
            steps {
                sh './gradlew publishToMavenLocal publishMavenJavaPublicationToSnapshotRepository'
            }
        }
    }
    post {
        success {
           sh "/opt/vegdata/healthmonitor/report-build-outcome.sh APILES SUCCESS"
        }
        failure {
           sh "/opt/vegdata/healthmonitor/report-build-outcome.sh APILES FAILURE"
           mattermostSend message: "Build failure: ${env.JOB_NAME} ${env.BUILD_NUMBER}", color: "danger", endpoint: "https://mattermost.kantega.no/hooks/usuis6h8rbro3qixb59e19g97e"
           junit '**/build/*test-results/**/*.xml'
        }
        unstable {
           sh "/opt/vegdata/healthmonitor/report-build-outcome.sh APILES UNSTABLE"
           mattermostSend message: "Build failure: ${env.JOB_NAME} ${env.BUILD_NUMBER}", color: "danger", endpoint: "https://mattermost.kantega.no/hooks/usuis6h8rbro3qixb59e19g97e"
           junit '**/build/*test-results/**/*.xml'
        }
    }
}
