package me.local.mcn;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableMongock
@EnableScheduling
@SpringBootApplication
public class McnApplication {

	public static void main(String[] args) {
		SpringApplication.run(McnApplication.class, args);
	}

}
