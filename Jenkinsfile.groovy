pipeline {
    agent any

    tools {
        maven 'maven-3.9.0'
    }
    environment {
        GCLOUD_CREDS = credentials('gcloud-creds')
    }
    stages {
        stage('Build backend') {
            steps {
                echo 'Building fisa-ssm backend..'
                echo 'mvn --version'
                sh 'mvn clean install -DskipTests'
                echo 'Compilation successfully completed'
            }
        }
        stage('Backend Quality Gate') {
            steps {
                echo 'Testing fisa-ssm backend.. .'
                sh 'mvn clean verify'
                echo 'Quality gate successfully passed'
            }
        }

        stage('GCP Authentication') {
            steps {
                sh '''
                echo "Activate GCP Service account"
                gcloud auth activate-service-account --key-file="$GCLOUD_CREDS"

                echo "Current project"
                gcloud config get-value project

                gcloud auth configure-docker
              '''
            }
        }

        stage('Deploy') {
            steps {

                echo "Deploying the Fisa-SSM application to the GCP instance using ${env.GIT_BRANCH}..."

                sh '''
                  docker compose down --remove-orphans -v
                  docker rmi -f gcr.io/portal-cos/portal-frontend
                  docker rmi -f gcr.io/portal-cos/portal-backend
                  docker system prune -af
                  docker compose up --build --no-deps --detach --renew-anon-volumes --force-recreate
               '''
            }
        }

    }

}
