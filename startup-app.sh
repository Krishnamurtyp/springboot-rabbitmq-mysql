#! /bin/bash

function exec() {
#  Spring boot is getting failed because it's trying to search for the rabbitmq
   docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:management
   container_id=$(docker run  --env MYSQL_ROOT_PASSWORD=spring -d -p 3306:3306 --name mysql-db mysql:latest)
   sleep 10
   echo "INFO: Executing mvn clean install command to create a jar file"
   response=$(mvn clean install -U -Dspring.profiles.active=dev)
   echo "${response}"
   echo "INFO: Building and testing the app. Proceed to remove local Rabbitmq and Mysql Docker containers"
   docker container stop mysql-db
   docker container stop my-rabbit
   docker container rm -f mysql-db
   docker container rm -f my-rabbit
   echo "INFO: Succesfull removal of local Rabbitmq and Mysql Docker containers"
   echo "INFO: Running Docker Compose up to startup the app "
   docker-compose up -d
   docker-compose ps
   echo "INFO: Access http://localhost:8080/swagger-ui/index.html#/ to see deployed endpoints and test them"
   echo "INFO: Run 'docker-compose down' to shutdown the app, once you're done testing the app "
}


exec