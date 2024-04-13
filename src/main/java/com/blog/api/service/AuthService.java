package com.blog.api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.api.dto.LoginDto;
import com.blog.api.dto.RegisterDto;
import com.blog.api.exception.BadRequestException;
import com.blog.api.model.Role;
import com.blog.api.model.User;
import com.blog.api.repo.RoleRepository;
import com.blog.api.repo.UserRepository;
import com.blog.api.security.JwtTokenProvider;

@Service
public class AuthService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private AuthenticationManager manager;
	private JwtTokenProvider provider;
	private PasswordEncoder passwordEncoder;
	
	public AuthService(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager manager,
			JwtTokenProvider provider, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.manager = manager;
		this.provider = provider;
		this.passwordEncoder = passwordEncoder;
	}



	public String authLogin(LoginDto login) {
		Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsernameOrEmail(), login.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = provider.generateToken(authentication);
		return token;
	}
	
	public String authRegister(RegisterDto register) {
		if (! userRepository.existsByUsername(register.getUsername())) {
			throw new BadRequestException("Username Already Exists!");
		}
		
		if (!userRepository.existsByEmail(register.getEmail())) {
			throw new BadRequestException("Email Already Exists!");
		}
		
		User user = new User();
		user.setName(register.getName());
		user.setUsername(register.getUsername());
		user.setEmail(register.getEmail());
		user.setPassword(passwordEncoder.encode(register.getPassword()));
		
		Set<Role> roles = new HashSet<Role>();
		Role role = roleRepository.findByName("ROLE_USER").get();
		roles.add(role);
		user.setRoles(roles);
		
		userRepository.save(user);
		
		return "User Registered Successfully!";
		
	}
}
