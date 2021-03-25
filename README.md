# IBM-Security-Verify-Spring-Boot-SSO

Blog Post describing how to secure a Spring Boot application using IBM Security Verify:

https://gkovan.medium.com/secure-your-spring-boot-app-using-single-sign-on-with-ibm-security-verify-d89264e4c6e4

* To start isv-sso-client-app:
```
mvn spring-boot:run -Dspring-boot.run.arguments="--CLIENT_ID=<your client id> --CLIENT_SECRET=<your client secret>"
```

* To start the resource server, isv-sso-resource-server:
```
mvn spring-boot:run
``` 

* Once the two microservices are started, open a browser and go to the following url:
```
http://localhost:8082/ui
```
