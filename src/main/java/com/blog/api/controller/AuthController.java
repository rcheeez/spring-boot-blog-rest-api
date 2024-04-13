package com.blog.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.dto.JwtAuthResponse;
import com.blog.api.dto.LoginDto;
import com.blog.api.dto.RegisterDto;
import com.blog.api.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService service;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto login) {
		JwtAuthResponse response = new JwtAuthResponse();
		String accessToken = service.authLogin(login);
		response.setAccessToken(accessToken);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDto register) {
		String response = service.authRegister(register);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
