[![CircleCI](https://circleci.com/gh/iolsh/jee-playground.svg?style=svg)](https://circleci.com/gh/iolsh/jee-playground)
# jee-playground
### Playground for JEE  
* Payara micro / JEE 8 
### Install and setup RabbitMQ
* In ```docker/rabbitmq``` directory execute ```docker-compose up -d```
* create "playground" queue and bind to exchange of "amq.fanout" type
* adjust values in META-INF/microprofile-config.properties file
### Build and run:
Execute ```mvn clean package payara-micro:start```
### Play with something
* See http://localhost:8080/micro-playground for details







