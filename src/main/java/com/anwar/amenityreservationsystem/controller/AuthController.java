package com.anwar.amenityreservationsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anwar.amenityreservationsystem.entity.AuthRequest;
import com.anwar.amenityreservationsystem.entity.User;
import com.anwar.amenityreservationsystem.service.BasicauthService;
import com.anwar.amenityreservationsystem.service.UserDetailsServiceImpl;
import com.anwar.amenityreservationsystem.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private BasicauthService basicauthService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome this endpoint is not secure";
	}

	@PostMapping("/add-new-user")
	public String addNewUser(@RequestBody User userInfo) {
		return userDetailsServiceImpl.addUser(userInfo);
	}
	
	@PostMapping("/generate-token")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return basicauthService.generateToken(authRequest.getUsername(), authRequest.getPassword());  
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

	@GetMapping("/user/profile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userProfile() {
		UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        User user = userService.getUserByUsername(username );
		return "Welcome to User Profile";
	}

	@GetMapping("/admin/profile")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminProfile() {
		return "Welcome to Admin Profile";
	}

}
