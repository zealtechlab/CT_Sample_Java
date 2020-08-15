pipeline { 
    agent any 
    options {
        ansiColor('xterm')
        skipStagesAfterUnstable()
        }
    stages {
        stage("CloneCode") {
            steps {
                script {
                    echo 'Repo Checkout'
                    sh 'printenv'
                    // CloneCode is commented as the repo is already cloned by pipe
                    // git clone 'https://github.com/danielalejandrohc/cargotracker.git';
                }
            }
        }
        stage("Build") {
            steps {
                script {
                    // If you are using Windows then you should use "bat" step
                    // Since unit testing is out of the scope we skip them
                    sh "mvn clean package -DskipTests=true"
                }
            }
            tools { maven 'maven' }
        }
        stage("Selenium Testing") {
            steps {
                script {
                    sh "selenium-side-runner -s http://seleniumhub:4444/wd/hub \
                        SIDE/ZTL_Spring_Selemium_SIDE.side \
                        -c 'browserName=firefox' \
                        --base-url http://172.27.0.1:8080/ZTL-spring-selenium-test-1.0.0"
                }
            }
        }
        stage("Jmeter Perf Testing") {
            steps {
                script {
                    sh 'mvn cargo:run &'
                    sh 'sleep 30s'
                    sh "/opt/apache-jmeter-5.3/bin/jmeter -Jjmeter.save.saveservice.output_format=xml -n \
                        -t 'perf/HTTP Request.jmx' -l target/perf/log.jtl -j target/jmeter.log"
                    sh 'mvn cargo:stop'
                }
            }
            post {
                success {
                    echo 'Perf Stage SUCCESS'
                    perfReport filterRegex: '', sourceDataFiles: 'target/'
                }
            }
        }
    }
    post {
        always {
            echo 'JENKINS PIPELINE'
        }
        notBuilt {
            echo 'JENKINS PIPELINE NOT BUILT'
        }
        success {
            echo 'JENKINS PIPELINE SUCCESSFUL'
        }
        failure {
            echo 'JENKINS PIPELINE FAILED'
        }
        unstable {
            echo 'JENKINS PIPELINE WAS MARKED AS UNSTABLE'
        }
        changed {
            echo 'JENKINS PIPELINE STATUS HAS CHANGED SINCE LAST EXECUTION'
        }
        aborted {
            echo 'JENKINS PIPELINE ABORTED'
        }
    }
}