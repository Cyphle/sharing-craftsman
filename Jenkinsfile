pipeline {
    environment {
        TARGET_PATH     = '/home/apps/knowledge-library'
        INFOS_PATH      = 'scripts/knowledge-library-infos.yml'
    }

    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Modify application properties') {
            steps {
                sh 'chmod +x ./scripts/update-application-properties.sh'
                sh './scripts/update-application-properties.sh ${INFOS_PATH}'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deliver') {
            steps {
                sh 'chmod +x ./scripts/deliver.sh'
                sh './scripts/deliver.sh ${TARGET_PATH}'
            }
        }
    }
}