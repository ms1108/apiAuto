pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'java -jar ./moco/moco-runner-1.0.0-standalone.jar http -p 8899 -c ./moco/test.json'
                sh 'mvn clean test'
            }
        }
    }
}