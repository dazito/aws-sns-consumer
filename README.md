# aws-sns-consumer
A simple consumer for SNS notifications from AWS.
It stores the notifications received from SES and every hour will process them and mail the users with the content of all SES received since the last process ran.

# Known issue

- When subscribing to a SNS topic it is required to manually confirm the subscription

# Database
- Create a database schema using the provided schema.
- Synchronize the datamodel with the newly created schema.


# Configurations
- Open the file "application.properties" located at \src\main\resources
  - Edit MySQL server configuration
  - Edit the AWS SES configuration with your SES credentials


# Run the server
- To run as a docker container
	
  Browse to:
	
      ../komoot/docker

	Run:
	
      docker build -t komoot .
	
      docker run -p 8080:8080 -t komoot

- To run as a single jar and in background
		
    Browse to:
		
      ../komoot/docker

	 Run:
    
		  nohup java -jar Komoot-1.0.jar &
      

- To run as a single jar
		
    Browse to:
		
      ../komoot/docker

	Run:
		
       java -jar Komoot-1.0.jar

# Logs
	
- Log location: /var/log
	
- Log file: app.log

- If running inside a docker container, you'll need to enter the container and browse to the log location file.
	
  To enter a docker container, first get the container id with the following command:
	
       docker ps
	
  Then enter the container with:
	
      docker exec -it **the_docker_container_id** /bin/bash
	
  Now browse to /var/log
