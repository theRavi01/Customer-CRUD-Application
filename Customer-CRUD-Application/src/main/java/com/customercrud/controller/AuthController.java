package com.customercrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customercrud.config.JwtProvider;
import com.customercrud.exception.UserException;
import com.customercrud.model.User;
import com.customercrud.repository.UserRepository;
import com.customercrud.response.AuthResponse;
import com.customercrud.service.CustomUserDetailsServiceImp;

/*
@Author: Ravikant Pandey
*/

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserDetailsServiceImp customUserDetails;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
		String email = user.getEmail();
		String password = user.getPassword();
		 System.out.println("email "+email);
		User isEmailExist = userRepo.findByEmail(email);
		if(isEmailExist!= null) {
			throw new UserException("Email is already used with another account");
		}
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		User savedUser = userRepo.save(createdUser);
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		AuthResponse res = new AuthResponse(token, true );
		return new ResponseEntity<AuthResponse> (res,HttpStatus.CREATED);
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody User user){
		String username = user.getEmail();
		String password = user.getPassword();
		Authentication authentication = authenticate(username,password);
		String token = jwtProvider.generateToken(authentication);
		AuthResponse res = new AuthResponse(token, true );
		return new ResponseEntity<AuthResponse> (res,HttpStatus.ACCEPTED);
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails= customUserDetails.loadUserByUsername(username);
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid username...");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password..");
		}
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
	
}


