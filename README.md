# spring-cloud-microservice
The spring cloud sample applications

Please note that I have to use below dependencies 

```
	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>Finchley.BUILD-SNAPSHOT</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
```
  instead of 
```
  <version>Finchley.RELEASE</version> 
```  
  to make the configuration server and client work. 
  
  This is basic introduction to illustrate how the spring cloud works.  It covers the below topics

 1. spring configuration server (config-service) and configuration clients (all other project)
 2. spring restful service
 3. spring eureka service registry and discovery
 4. spring kafka broker, binders and bindings  based on external kafka server
 5. spring hystrix dashboard
 6. spring zipkin service monitoring based on external zipkin server 
 7. spring actuator
 8. spring SSO
 9. spring zuul proxy
 10. spring api gateway 
