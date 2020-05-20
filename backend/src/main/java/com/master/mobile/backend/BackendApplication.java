package com.master.mobile.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dm = new DriverManagerDataSource("jdbc:derby:movie-db", "root", "root");
		Properties properties = new Properties();
		properties.setProperty("create", "true");
		dm.setConnectionProperties(properties);
		dm.setSchema("APP");
		dm.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
		return dm;
	}

}
