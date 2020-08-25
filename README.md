# ShortyV2
Shorty is a simple REST API url shortener

To start the server, navigate to the downloaded folder and run this command:
java -jar target/ShortyV2-0.0.1-SNAPSHOT.jar {serverIp} {--server.port=serverPort}
where objects inside {} braces are optional. For example you could run:
java -jar target/ShortyV2-0.0.1-SNAPSHOT.jar localhost --server.port=8085
which would start the server on localhost on port 8085. If you intend on running the server on a public ip, replace local host with the public ip of the server or the servers DNS name if it is already registered. Note that neither are required to run since the default settings are set to localhost and 8080.

Once you have the server up and running refer to http://{serverIP}:{serverPort}/swagger-ui/index.html for further documentation.
