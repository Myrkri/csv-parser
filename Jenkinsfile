pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
        jdk 'jdk19'
    }

    stages {
//         stage('Checkout') {
//             steps {
//                 git 'https://github.com/Myrkri/csv-parser.git'
//             }
//         }
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