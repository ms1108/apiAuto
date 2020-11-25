pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('apiAction') {
            steps {
             sh 'mvn clean test -D g_host=${g_host} -D email_recipients=${email_recipients} -D g_run_suit_list=src/main/resources/testng-login.xml'
            }
        }
    }
}