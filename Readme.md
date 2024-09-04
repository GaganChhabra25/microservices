### **Docker commands** 

- Create Docker Image - docker build . -t gaganchhabra25/accounts:s4
- RUN container - docker run -d -p 8080:8080 5d36a504e79a
- Run Container on different port - docker run -d -p 8080:8081 5d36a504e79a
- **Push image to Docker Hub** - docker image push docker.io/gaganchhabra25/loans:s4
- docker compose up
- docker compose down
- docker compose stop
- docker container inspect
- docker container logs 
### Using Buildpacks

- Use Maven Plugin

 
  	<plugins>
  		<plugin>
  			<groupId>org.springframework.boot</groupId>
  			<artifactId>spring-boot-maven-plugin</artifactId>
  			<configuration>
  				<image>
  					<name>gaganchhabra25/${project.artifactId}:s4</name>
  				</image>
  				<excludes>
  					<exclude>
  						<groupId>org.projectlombok</groupId>
  						<artifactId>lombok</artifactId>
  					</exclude>
  				</excludes>
  			</configuration>
  		</plugin>
  	</plugins>

Command - **mvn spring-boot:build-image**

### Using Google Jib 

	<plugin>
				<groupId>com.google.cloud.tools</groupId>
				<artifactId>jib-maven-plugin</artifactId>
				<version>3.3.2</version>
				<configuration>
					<to>
						<image>gaganchhabra25/${project.artifactId}:s4</image>
					</to>
				</configuration>
			</plugin>

Command - **mvn compile jib:dockerBuild**

Command without requiring Docker on local system - **mvn compile jib:build**

### Docker Compose file

services:
accounts:
image: gaganchhabra25/accounts:s4
container_name: accounts-microservice
ports:
- "8080:8080"
deploy:
resources:
limits:
memory: 700m
networks:
- eazybank
loans:
image: gaganchhabra25/loans:s4
container_name: loans-microservice
ports:
- "8090:8090"
deploy:
resources:
limits:
memory: 700m
networks:
- eazybank
cards:
image: gaganchhabra25/cards:s4
container_name: cards-microservice
ports:
- "9000:9000"
deploy:
resources:
limits:
memory: 700m
networks:
- eazybank
networks:
eazybank:
driver: "bridge"

### **Healthchecks**
http://localhost:9000/api/health
http://localhost:8080/api/health
http://localhost:8090/api/health