pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2 -v /var/jenkins_home/jobs/apiAuto:/test-output/index.html'
        }
    }
    stages {
        stage('apiAction') {
            steps {
             sh 'mvn clean test -D g_host=${g_host} -D email_recipients=${email_recipients}'
            }
        }
    }
}