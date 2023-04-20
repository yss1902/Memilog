package com.memil.memilog;

import com.memil.memilog.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class MemilogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemilogApplication.class, args);
	}

}
