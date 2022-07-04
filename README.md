# knowledge-platform
Requirments
- JDK 17
- maven 3

how to run it with docker-compose
- mvn clean
- mvn package
- cd  src/main/docker
- docker-compose up -d

for importing data into database please set 
"spring_batch_job_enabled"
properties to true in docker-compose file and application.yml

swagger URL
http://localhost:8082/swagger-ui.html

postman collection in postman folder

using
-spring boot
-spring batch
-postgres db