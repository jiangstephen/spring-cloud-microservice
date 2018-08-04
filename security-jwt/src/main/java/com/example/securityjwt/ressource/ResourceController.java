package com.example.securityjwt.ressource;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/springjwt")
public class ResourceController {
	
	@PostMapping(value="/cities")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
	public List<String> getCities(){
		return Lists.newArrayList("city1", "city2");
	}
	
	@PostMapping(value="/users")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<String> getUsers(){
		return Lists.newArrayList("user1", "user2");
	}
}
