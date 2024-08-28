pipeline {
    agent any

    stages {
//         stage('Checkout') {
//             steps {
//                 git 'https://github.com/Myrkri/csv-parser.git'
//             }
//         }
        stage('Build') {
            steps {
                sh 'mvn clean install'
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