pipeline {
agent any
environment {
JAVA_HOME = 'C:\\Program Files\\Java\\jdk-17'
JMETER_HOME = 'C:\\Tools\\apache-jmeter-5.6.3'
PATH = "${JAVA_HOME}\\bin;${JMETER_HOME}\\bin;${env.PATH}"
}
tools {
maven 'MAVEN_HOME'
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