[![CircleCI](https://circleci.com/gh/iolsh/micro-playground.svg?style=svg)](https://circleci.com/gh/iolsh/micro-playground)
# micro-playground
### Playground for JEE  
* Payara micro / JEE 8 

### Install and setup RabbitMQ
* In ```docker/rabbitmq``` directory execute ```docker-compose up -d```
* create "playground" queue and bind to exchange of "amq.fanout" type
* adjust values in META-INF/microprofile-config.properties file

### Build and run:
Execute ```mvn clean package payara-micro:start```

### Play with something
* See http://localhost:8080/openapi/ for available REST endpoints

### TODO - check:
* https://www.devwithimagination.com/2019/09/03/integration-testing-with-payara-micro/?utm_content=100156467&utm_medium=social&utm_source=twitter&hss_channel=tw-2599580401
* http://arquillian.org/guides/
* https://github.com/javaee-samples/javaee8-samples





