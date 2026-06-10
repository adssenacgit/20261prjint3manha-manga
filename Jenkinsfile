pipeline {
    agent any
    stages {
        stage('Verificar Repositório') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], useRemoteConfigs: [[url: 'https://github.com/adssenacgit/20261prjint3manha-manga']]])
            }
        }

        stage('Instalar Dependências') {
            steps {
                script {
                    // Atualiza o PATH se necessário
                    env.PATH = "/usr/bin:$PATH"
                    // Instalar as dependências Maven antes de compilar o projeto
                    sh 'mvn clean install'  // Instala as dependências do Maven

                    //dir('subpasta') {
                    //  sh "docker build -t ${imageTag} ."
                    //}

                }
            }
        }
        stage('Construir Imagem Docker') {
            steps {
                script {
                    def appName = 'backend-prjint3-lanchonete'
                    def imageTag = "${appName}:${env.BUILD_ID}"

                    // Construir a imagem Docker
                    sh "docker build -t ${imageTag} ."
                }
            }
        }

        stage('Fazer Deploy') {
            steps {
                script {
                    def appName = 'backend-prjint3-lanchonete'
                    def imageTag = "${appName}:${env.BUILD_ID}"

                    // Parar e remover o container existente, se houver
            		sh "docker stop ${appName} || exit 0"
            		sh "docker rm -v ${appName} || exit 0"  // Remover o container e os volumes associados

                    // Executar o novo container
                    sh "docker run -d --name ${appName} -p 4002:4002 ${imageTag}"
                }
            }
        }
    }
    post {
        success {
            echo 'Deploy realizado com sucesso!'
        }
        failure {
            echo 'Houve um erro durante o deploy.'
        }
    }
}