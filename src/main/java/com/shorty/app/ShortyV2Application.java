package com.shorty.app;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.shorty.app.utilis.NameOfServer;

@SpringBootApplication
public class ShortyV2Application {

	public static void main(String[] args) {
		if (args.length != 0 && !args[0].contains("--server.port=")) {
			Properties properties = new Properties();
			if (args[0].isEmpty() == false) {
				properties.put("server.ip", args[0]);
				NameOfServer.setNameOfServer(args[0]);
			}
		} else {
			NameOfServer.setNameOfServer("localhost");
		}
		
		SpringApplication.run(ShortyV2Application.class, args);
	}

}
