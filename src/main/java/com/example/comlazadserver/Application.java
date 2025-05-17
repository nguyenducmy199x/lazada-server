package com.example.comlazadserver;

import com.example.comlazadserver.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class Application {
	@Autowired
	UserRepository userRepository;

	@Value("${AWS_BUCKET_NAME}")
	String buckerName;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void init() {
		log.info("bucket name is .... " + buckerName);
	}


}
