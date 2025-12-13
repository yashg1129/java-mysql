pipeline {
    environment {
		IMAGE_NAME = "skillstute/java-mysql"
		IMAGE_TAG = "${env.BUILD_NUMBER}"
	}
    agent any
    tools {
        maven 'maven_3_9_11'
    }
    stages {
        stage('Checkout Source') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/yashg1129/java-mysql.git',
                    credentialsId: 'git-creds'
            }
        }
        stage('Build') {
            steps {
                echo 'Building the application...'
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Docker build') {
            steps {
                echo 'Docker build...'
                sh 'docker build -t ${IMAGE_NAME}:${IMAGE_TAG} -t ${IMAGE_NAME}:latest .'
            }
        }
        stage('Login to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'Docker_Skills_Creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                }
            }
        }
        stage('Push Image') {
            steps {
                echo "Docker image pushing...${IMAGE_NAME}:${IMAGE_TAG}"
                sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
                sh "docker push ${IMAGE_NAME}:latest"
                echo 'Docker image pushed'
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                // EC2-1 (Jenkins)
                echo 'Kubernetes image deploying...'
               withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
                    sh 'kubectl apply -f k8s/config.yaml'
                    sh 'kubectl apply -f k8s/secret.yaml'
                    sh 'kubectl apply -f k8s/deployment.yaml'
                    sh 'kubectl apply -f k8s/service.yaml'
                }
                echo 'Kubernetes image deployed'
            }
        }
    }
}