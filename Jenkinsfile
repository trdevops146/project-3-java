pipeline {
    agent any
    stages{
        stage('Checkout the code from github'){
            steps{
                git branch: 'main', url: 'https://github.com/trdevops146/project-3-java.git'
            }
        }
        stage('Install Maven and Build the application'){
            steps{
                sh '''
                sudo apt update
                sudo apt install maven
                mvn --version
                mvn clean
                mvn package
                '''
            }
        }
        stage('Build the docker image'){
            steps{
                withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                sh '''
                docker build -t trdevops/java-app:order-service -f ./order-service/Dockerfile ./order-service
                docker build -t trdevops/java-app:user-service -f ./user-service/Dockerfile ./user-service
                echo "$DOCKER_PASS" | docker login -u $DOCKER_USER --password-stdin
                docker push trdevops/java-app:order-service
                docker push trdevops/java-app:user-service
                '''
            }
            }

        }

    }
}