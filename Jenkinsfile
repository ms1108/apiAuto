pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2 -v ${pwd}/test-output/index.html:/test-output/index.html'
        }
    }
    stages {
        stage('apiAction') {
            steps {
             sh 'mvn clean test -D g_host=${g_host} -D email_recipients=${email_recipients}'
             echo '${ip addr show | grep inet | grep eth1 | awk -F'/' '{print $1}' | awk '{print $2}'}/test-output/index.html'
            }
        }
    }
}