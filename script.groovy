pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', credentialsId: 'github-login' ,url: 'https://github.com/giangngocthanh07/js-nextjs.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ntgiang0709/js_nextjs_image:latest .'
            }
        }

        stage('Login Docker') {
            steps {
                withCredentials([
                    usernamePassword(credentialsId:'docker-login', usernameVariable:'DOCKER_USER', passwordVariable:'DOCKER_PASS')
                ]) {
                    sh'''
                        docker login -u ${DOCKER_USER} -p ${DOCKER_PASS}
                    '''
                }
            }
        }

        stage('Push Image To Docker Hub') {
            steps {
                sh'''
                    docker push ntgiang0709/js_nextjs_image:latest
                '''
            }
        }
    }
}