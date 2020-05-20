package com.master.mobile.backend;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
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

	@Bean
	@Primary
	public ObjectMapper objectMapper(){
		return new Jackson2ObjectMapperBuilder()
			.indentOutput(true)
			.failOnUnknownProperties(false)
			.serializationInclusion(JsonInclude.Include.NON_NULL)
			.build();
	}

}
