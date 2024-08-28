pipeline {
    agent any

    stages {
        stage('Initialization') {
            tools {
                maven 'Maven 3.6.3'
                jdk 'jdk19'
            }
            steps {
                sh 'mvn -version'
                sh 'java -version'
            }
        }
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'mvn -DskipTests clean install'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }
    }
}