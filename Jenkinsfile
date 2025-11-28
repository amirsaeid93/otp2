pipeline {
    agent any

    environment {
        // --- IMPORTANT: UPDATE THESE PATHS TO MATCH YOUR MACHINE ---
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-17'
        JMETER_HOME = 'C:\\Tools\\apache-jmeter-5.6.3'
        // This correctly sets the PATH for the entire pipeline
        PATH = "${JAVA_HOME}\\bin;${JMETER_HOME}\\bin;${env.PATH}"
    }

    tools {
        // Make sure 'Maven3' matches a Maven installation name in your Jenkins Global Tool Configuration
        maven 'MAVEN_HOME'
    }

    stages {
        stage('Checkout') {
            steps {
                // This checks out the whole OTP_2 repository
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                // Change directory into the correct project folder first
                dir('jmeter_assignement') {
                    // Run the build
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
            dir('jmeter_assignement') {
                echo "Archiving JMeter results..."
                // Archive the JTL results file for later analysis
                archiveArtifacts artifacts: 'result.jtl', allowEmptyArchive: true

                // Generate a performance report using the Performance Plugin
                perfReport sourceDataFiles: 'result.jtl'
            }
            
            echo "Build finished. Cleaning up workspace."
            cleanWs()
        }
    }
}
