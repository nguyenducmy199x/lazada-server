package com.example.comlazadserver;

import com.example.comlazadserver.entity.User;
import com.example.comlazadserver.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application {
	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
//	@PostConstruct
//	public void init(){
//		User user = new User();
//		user.setUsername("myn");
//		user.setPassword("123");
//		user.setEmail("nguyenducmy199x@gmail.com");
//		user.setRoles(List.of("ADMIN"));
//		userRepository.save(user);
//	}

}
