# CT_Sample_Java

Sample Jsp Login spring MVC java with mvn Selenium web tester Added with Selenium IDE automation testing on grid and Jmeter.

At this stage the app is assumed to have CI complete and CT starts, i.e. the web app is deployable and is up and running at a reachable ip.

This branch adds Jenkins for Continuous Testing.

## Prerequisites

You need to have a Selenium Grid running, to be able to execute the Selenium test.

To Run the cargo server

```bash
mvn clean compile package -Dcargo.servlet.port=10001 cargo:run
```

Run Selenium test (Launch in system terminal so that the systems browser can be utilized)

```bash
mvn clean compile package verify -Dbrowsertype=firefox -Dapp.server.url=http://localhost:10001/ZTL-spring-selenium-test
```

Run on grid

```bash
mvn clean compile package verify -Dbrowsertype=firefox -Dgrid=true -Dheadless=false -Dgrid.server.url=http://<grid_ip>:4444/wd/hub -Dapp.server.url=http://<hostip>:8080/ZTL-spring-selenium-test-1.0.0
```

When hosted external change ```app.server.url``` accordingly, identify hostip of the seleniumnode.

### Start Selenium Grid using Docker from docker_devops_stack project

1. Create docker-compose.yml file with the following content.
2. Start containers using 'docker-compose up'

For details, see <https://github.com/zealtechlab/docker_devops_stack.git>

## Selenium IDE (SIDE)

Make sure the selenium IDE is installed. There are two parts to it, one is the Selenium IDE in browser to record the test cases. Two is the selenium command line interface to auto execute the recorded scripts.

Refer <https://www.selenium.dev/selenium-ide/docs/en/introduction/getting-started>

```bash

sudo apt install nodejs npm

npm install -g selenium-side-runner

selenium-side-runner -s http://172.18.0.7:4444/wd/hub ZTL_Spring_Selemium_SIDE.side -c "browserName=firefox" --base-url http://172.18.0.1:8080/ZTL-spring-selenium-test-1.0.0
```

NOTE: make sure the ip are corrected accordingly, i.e. the base url is where the application to be tested is running and the -s option is the selenium grid url.

## perfomrance testing Jmeter

Make sure jmeter binaries are avaiable. This can be acheived either by apt install or just by downloading from jmeter.apache.org and simply untaring the downloaded compressed verions.

Check by launching the jmeter UI to create some sample test plans. The same can be saved as .jmx files that could be ported.

Command line execution

```bash
jmeter -Jjmeter.save.saveservice.output_format=xml -n -t 'perf/HTTP Request.jmx' -l target/perf/log.jtl
```
