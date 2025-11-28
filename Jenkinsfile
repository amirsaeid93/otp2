pipeline {
    agent any

    stages {
        stage('Cleanup') {
            steps {
                // Start with a clean workspace to ensure no old files are present
                cleanWs()
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test JMeter Project') {
            steps {
                // Use the full workspace path to be explicit
                dir("${env.WORKSPACE}/jmeter_assignement") {
                    // Verify the pom.xml exists before trying to build
                    bat 'dir pom.xml'
                    
                    bat 'mvn clean install'
                    
                    // Run the JMeter test
                    bat 'jmeter -n -t src/tests/performance/demo.jmx -l result.jtl'
                }
            }
        }
    }

    post {
        always {
            // Change directory to where the result file is located
            dir("${env.WORKSPACE}/jmeter_assignement") {
                echo "Archiving JMeter results..."
                archiveArtifacts artifacts: 'result.jtl', allowEmptyArchive: true
                perfReport sourceDataFiles: 'result.jtl'
            }
            
            echo "Build finished."
        }
    }
}
