pipeline {
    agent any

    stages {
        stage('Initialization') {
            tools {
                jdk 'jdk19'
                maven 'Maven 3.6.3'
            }
            steps {
                sh 'java -version'
                sh 'mvn -version'
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