### **Docker commands** 

- Create Docker Image - docker build . -t gaganchhabra25/accounts:s4
- RUN container - docker run -d -p 8080:8080 5d36a504e79a
- Run Container on different port - docker run -d -p 8080:8081 5d36a504e79a

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