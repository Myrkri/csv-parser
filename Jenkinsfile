pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
    }

    stages {
        stage('Initialization') {
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
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }
    }
}