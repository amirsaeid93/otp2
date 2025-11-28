pipeline { 
    agent any 
    
    environment { 
        // --- IMPORTANT: UPDATE THESE PATHS TO MATCH YOUR MACHINE ---
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-17' 
        JMETER_HOME = 'C:\\Tools\\apache-jmeter-5.6.3' 
        PATH = "${JAVA_HOME}\\bin;${JMETER_HOME}\\bin;${env.PATH}" 
    } 
    
    tools { 
        // Make sure 'Maven3' matches a Maven installation name in your Jenkins Global Tool Configuration
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
                // Note the path to the JMX file is now relative to the workspace root
                bat 'jmeter -n -t src/tests/performance/demo.jmx -l result.jtl' 
            } 
        } 
    } 
    
    post { 
        always { 
            // Archive the JTL results file for later analysis
            archiveArtifacts artifacts: 'result.jtl', allowEmptyArchive: true 
            
            // Generate a performance report using the Performance Plugin
            // This requires the "Performance" plugin to be installed in Jenkins.
            perfReport sourceDataFiles: 'result.jtl' 
        } 
    } 
}