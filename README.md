## Customers Phone Presentment
```
A spring boot project with spring thymleaf, it used to get cutomers by country and state.

We can filter by state (VALID, INVALID) and using country


Swagger links:

http://localhost:8080/swagger-ui.html
```

## Steps to run project
```
mvn clean install
docker build -t phone-presentment-image .
docker run -d -p 8080:8080 --name phone_presentment-container phone-presentment-image:latest

navigate to http://localhost:8080/

using postman:

http://localhost:8080/api/v1/phone-presentment/present?state=VALID&countries=Morocco,Mozambique
```
