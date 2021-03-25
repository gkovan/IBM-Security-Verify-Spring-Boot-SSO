# IBM-Security-Verify-Spring-Boot-SSO

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
Open a browser and to to the url: http://localhost:8082/ui

```
