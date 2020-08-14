# CT_Sample_Java

Sample Jsp Login spring MVC java with mvn Selenium web tester Added with Selenium IDE automation testing on grid

### Prerequisites

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

When hosted external change ```app.server.url ``` accordingly, identify hostip of the seleniumnode.

### Start Selenium Grid using Docker from docker_devops_stack project

1. Create docker-compose.yml file with the following content.
2. Start containers using 'docker-compose up' 

For details, see https://github.com/zealtechlab/docker_devops_stack.git
