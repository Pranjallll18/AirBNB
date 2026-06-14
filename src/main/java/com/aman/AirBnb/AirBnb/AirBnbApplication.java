package com.aman.AirBnb.AirBnb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.net.URI;

@SpringBootApplication
@EnableScheduling
public class AirBnbApplication {

	public static void main(String[] args) {
		String dbUrl = System.getenv("DB_URL");
		if (dbUrl == null || dbUrl.trim().isEmpty()) {
			dbUrl = System.getenv("DATABASE_URL");
		}
		
		if (dbUrl != null && dbUrl.startsWith("postgres://")) {
			try {
				URI dbUri = new URI(dbUrl);
				String username = dbUri.getUserInfo().split(":")[0];
				String password = dbUri.getUserInfo().split(":")[1];
				int port = dbUri.getPort();
				String portStr = port != -1 ? ":" + port : "";
				String dbUrlJdbc = "jdbc:postgresql://" + dbUri.getHost() + portStr + dbUri.getPath();
				
				System.setProperty("spring.datasource.url", dbUrlJdbc);
				System.setProperty("spring.datasource.username", username);
				System.setProperty("spring.datasource.password", password);
				System.out.println("Auto-configured JDBC URL from postgres:// connection string.");
			} catch (Exception e) {
				System.err.println("Failed to parse database url: " + e.getMessage());
			}
		}

		SpringApplication.run(AirBnbApplication.class, args);
	}

}
