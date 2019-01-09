package com.grpcspring;

import com.grpcspring.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration
@Import(Config.class)
public class GrpcSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrpcSpringApplication.class, args);
	}

}

