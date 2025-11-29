pipeline {
    agent any

    environment {
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-17'  // Adjust to your JDK path
        MAVEN_HOME = tool 'MAVEN_HOME'  // Assumes 'Maven3' is configured in Jenkins Tools
        JMETER_HOME = 'C:\\Tools\\apache-jmeter-5.6.3\\apache-jmeter-5.6.3'  // Adjust to your JMeter path
        PATH = "${JAVA_HOME}\\bin;${MAVEN_HOME}\\bin;${JMETER_HOME}\\bin;${env.PATH}"
    }

    tools {
        maven 'MAVEN_HOME'  // Your Maven tool name in Jenkins (Manage Jenkins > Tools)
    }

    stages {
        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }
        stage('Non-Functional Test') {
            steps {
                bat 'jmeter -n -t tests/performance/demo.jmx -l result.jtl'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'result.jtl', allowEmptyArchive: true
            perfReport sourceDataFiles: 'result.jtl'
        }
    }
}