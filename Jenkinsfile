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
                sudo apt install maven -y
                mvn --version
                '''
            }
        }
        stage('Run the mvn test and the sonarqube code scan'){
            steps{
                sh '''
                cd user-service/
                mvn test
                mvn clean package
                mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
                -Dsonar.projectKey=trdevops146_project-3-java \
                -Dsonar.organization=trdevops146 \
                -Dsonar.host.url=https://sonarcloud.io \
                -Dsonar.login=86ab33f429a87cf3f426dcc7042d257796016ede
                cd ..
                cd order-service/
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
                sh """
                cd /var/lib/jenkins/workspace/java-project-3
                docker build -t trdevops/java-app:order-service-${BUILD_NUMBER} -f ./order-service/Dockerfile ./order-service
                docker build -t trdevops/java-app:user-service-${BUILD_NUMBER} -f ./user-service/Dockerfile ./user-service
                echo "$DOCKER_PASS" | docker login -u $DOCKER_USER --password-stdin
                docker push trdevops/java-app:order-service-${BUILD_NUMBER}
                docker push trdevops/java-app:user-service-${BUILD_NUMBER}
                """
            }
            }

        }
        stage('Clone the github repo which holds the k8 manifests'){
            steps{
                sh """
                git clone "https://github.com/trdevops146/k8-manifest-repo.git"
                sed -i "s|image: trdevops/java-app:order-service|image: trdevops/java-app:order-service-${BUILD_NUMBER}|g" k8-manifest-repo/order-service.yaml
                sed -i "s|image: trdevops/java-app:user-service|image: trdevops/java-app:user-service-${BUILD_NUMBER}|g" k8-manifest-repo/user-service.yaml
                cd k8-manifest-repo
                git config user.name "Jenkins"
                git config user.email "jenkins@example.com"
                git add .
                git commit -m "Update image to build"
                git push origin main
                """
            }
        }

    }
}