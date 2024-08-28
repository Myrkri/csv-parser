pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
        docker 'Docker'
    }
     environment {
        DOCKERHUB_CREDENTIALS = credentials('DockerHub')
        DOCKER_IMAGE = "myrkri/study_csvparser"
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
        stage('Build Docker Image') {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'DockerHub') {
                         sh "docker build -t ${DOCKER_IMAGE}:${env.BUILD_NUMBER} ."
                    }
                }
            }
        }
        stage('Push to Docker Hub') {
            steps {
                script {
                   withDockerRegistry(credentialsId: 'DockerHub') {
                        sh "docker push ${DOCKER_IMAGE}:${env.BUILD_NUMBER}"
                   }
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