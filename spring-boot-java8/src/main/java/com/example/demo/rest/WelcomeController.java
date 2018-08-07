package com.example.demo.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	
	@GetMapping("/welcome")
	public String welcome(@RequestParam( name="name", defaultValue="mr") String name) {
		
		return "welcome "+name;
	}
}
