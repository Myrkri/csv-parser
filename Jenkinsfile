pipeline {
    agent any

    stages {
        stage('Initialization') {
//             agent {
//                 docker {
//                     image 'maven:3.8.1-adoptopenjdk-11'
//                     args '-u root -v /root/.m2:/root/.m2 -v "$pwd":/usr/src/app -w /usr/src/app'
//                   }
//             }
            tools {
                jdk 'jdk19'
                maven 'Maven 3.6.3'
            }
            environment {
                JAVA_HOME = "${tool 'JDK 19'}"
                PATH = "${JAVA_HOME}/bin:${env.PATH}"
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