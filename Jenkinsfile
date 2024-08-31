pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'Maven 3.6.3'
    }
     environment {
        DOCKER_IMAGE = "myrkri/study_csvparser"
        BUILD_NUMBER = "${env.BUILD_NUMBER}"
     }

    stages {
        stage('Initialization') {
            steps {
                bat "java -version"
                bat "mvn -version"
            }
        }
        stage('Build') {
            steps {
                bat "mvn -DskipTests clean install"
            }
        }
        stage('Test') {
            steps {
                bat "mvn test"
            }
            post {
                always {
                    junit "**/target/surefire-reports/*.xml"
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
               script {
                    withSonarQubeEnv() {
                        bat "mvn clean verify sonar:sonar -Dsonar.projectKey=csv-parser -Dsonar.projectName='csv-parser'"
                    }
               }
            }
            post {
                always {
                    script {
                        timeout(time: 5, unit: 'MINUTES') {
                            def qg = waitForQualityGate()
                            if (qg.status != 'OK') {
                                qg.conditions.each { condition ->
                                    if (condition.status != 'OK') {
                                        echo "Failed conditions: ${condition.metricKey} - ${condition.actualValue} ${condition.comparator} ${condition.errorThreshold}"
                                    }
                                }
                                error "Failed to pass SonarQube Quality Gate"
                            } else {
                                echo "Successfully passed SonarQube Quality Gate"
                            }
                        }
                    }
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    bat "docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} ."
                }
            }
        }
        stage('Push to Docker Hub') {
            steps {
                script {
                   withDockerRegistry(credentialsId: 'DockerHub') {
                        bat "docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}"
                   }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    powershell """
                    (Get-Content ./manifests/deployment.yml) -replace 'myrkri/study_csvparser:.*', 'myrkri/study_csvparser:${BUILD_NUMBER}' | Set-Content ./manifests/deployment.yml
                    """
                    withKubeConfig(credentialsId: 'KubeConfig') {
                        bat "kubectl apply -f ./manifests/docker-secret.yml"
                        bat "kubectl apply -f ./manifests/secret.yml"
                        bat "kubectl apply -f ./manifests/config-map.yml"
                        bat "kubectl apply -f ./manifests/deployment.yml"
                        bat "kubectl apply -f ./manifests/service.yml"
                    }
                }
            }
        }
    }
}