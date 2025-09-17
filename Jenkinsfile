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
                '''
            }
        }
        stage('Run the mvn test and the sonarqube code scan'){
            steps{
                sh '''
                mvn test
                mvn clean package
                mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
                -Dsonar.projectKey=trdevops146_project-3-java \
                -Dsonar.organization=trdevops146 \
                -Dsonar.host.url=https://sonarcloud.io \
                -Dsonar.login=86ab33f429a87cf3f426dcc7042d257796016ede
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