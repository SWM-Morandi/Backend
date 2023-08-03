pipeline {
    agent any

    environment {
        registryCredential = 'docker-key'
        dockerImage = ''
        dbUrl = 'your_db_url'
        dbPassword = 'your_db_password'
        googleClientId = 'your_google_client_id'
        googleClientSecret = 'your_google_client_secret'
        googleRedirectUri = 'your_google_redirect_uri'
        githubClientId = 'your_github_client_id'
        githubClientSecret = 'your_github_client_secret'
        githubRedirectUri = 'your_github_redirect_uri'
    }

    stages {
        // git에서 repository clone
        stage('Prepare') {
          steps {
            echo 'Clonning Repository'
            git url: 'https://github.com/SWM-NM/morandi-backend.git',
                branch: 'master',
                credentialsId: 'github-personal-access-token'
            }
            post {
           	    failure {
                    error 'This pipeline stops here...'
                }
            }
        }
        stage('Bulid Gradle') {
            steps {
                echo 'Bulid Gradle'
                sh './gradlew clean bootJar'
                sh "sed -i 's/\\${db-url}/${db-url}/g' /var/jenkins_home/workspace/morandi-backend-server/src/main/resources/application.yml"
                sh "sed -i 's/\\${db-password}/${db-password}/g' /var/jenkins_home/workspace/morandi-backend-server/src/main/resources/application.yml"
                sh "sed -i 's/\\${google-client-id}/${google-client-id}/g' /var/jenkins_home/workspace/morandi-backend-server/src/main/resources/application.yml"
                sh "sed -i 's/\\${google-client-secret}/${google-client-secret}/g' /var/jenkins_home/workspace/morandi-backend-server/src/main/resources/application.yml"
                sh "sed -i 's/\\${google-redirect-uri}/${google-redirect-uri}/g' /var/jenkins_home/workspace/morandi-backend-server/src/main/resources/application.yml"
                sh "sed -i 's/\\${github-client-id}/${github-client-id}/g' /var/jenkins_home/workspace/morandi-backend-server/src/main/resources/application.yml"
                sh "sed -i 's/\\${github-client-secret}/${github-client-secret}/g' /var/jenkins_home/workspace/morandi-backend-server/src/main/resources/application.yml"
                sh "sed -i 's/\\${github-redirect-uri}/${github-redirect-uri}/g' /var/jenkins_home/workspace/morandi-backend-server/src/main/resources/application.yml"
            }
            post {
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }
        stage('Bulid Docker') {
            steps {
                echo 'Bulid Docker'
                script {
                    dockerImage = docker.build "${image}"
                }
            }
            post {
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }
        stage('Push Docker') {
            steps {
                echo 'Push Docker'
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push("latest")
                    }
                }
            }
            post {
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }
        stage('Deploy') {
          steps {
              echo 'SSH'
              script {
                  def imageExists = sh(returnStdout: true, script: "docker images -q ${image}").trim()
                  if (imageExists) {
                      sh "docker rmi ${image}"
                  }
                  sshagent(['server-key']) {
                      sh "ssh -o StrictHostKeyChecking=no ubuntu@10.0.11.225 'sudo docker stop morandi-container || true'"
                      sh "ssh -o StrictHostKeyChecking=no ubuntu@10.0.11.225 'sudo docker rm morandi-container || true'"
                      sh "ssh -o StrictHostKeyChecking=no ubuntu@10.0.11.225 'sudo docker rmi aj4941/morandi-server || true'"
                      sh "ssh -o StrictHostKeyChecking=no ubuntu@10.0.11.225 'sudo docker pull aj4941/morandi-server'"
                      sh "ssh -o StrictHostKeyChecking=no ubuntu@10.0.11.225 'sudo docker run -d -p 8080:8080 --name morandi-container aj4941/morandi-server'"
                  }
              }
          }
       }
   }
}
