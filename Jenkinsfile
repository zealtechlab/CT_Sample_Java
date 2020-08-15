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
                        SIDE/ZTL_Spring_Selemium_SIDE.side -c 'browserName=firefox' \
                        --output-directory=target/side --output-format=junit \
                        --base-url http://172.27.0.1:8080/ZTL-spring-selenium-test-1.0.0"
                }
            }
            post {
                always {
                    perfReport filterRegex: '', sourceDataFiles: '**target/side/*.xml'
                }
            }
        }
        stage("Jmeter Perf Testing") {
            steps {
                script {
                    // sh 'mvn cargo:run &'
                    sh "/opt/apache-jmeter-5.3/bin/jmeter -Jjmeter.save.saveservice.output_format=xml \
                        -Jjmeter.save.saveservice.response_data=true -Jjmeter.save.saveservice.samplerData=true \
                        -Jjmeter.save.saveservice.requestHeaders=true -Jjmeter.save.saveservice.url=true \
                        -Jjmeter.save.saveservice.responseHeaders=true \
                        -n -t 'perf/HTTP Request.jmx' \
                        -l target/perf/perfomancelog.jtl -j target/perf/jmeter.log"
                    // sh 'mvn cargo:stop'
                }
            }
            post {
                always {
                    perfReport filterRegex: '', sourceDataFiles: '**target/perf/*.jtl;**target/perf/*.xml'
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