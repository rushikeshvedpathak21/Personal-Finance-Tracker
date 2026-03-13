package com.finance.tracker.controller;

import com.finance.tracker.dto.Dtos;
import com.finance.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Dtos.ApiResponse> register(@RequestBody Dtos.RegisterRequest request) {
		try {
			Dtos.UserResponse user = userService.register(request);
			return ResponseEntity.ok(new Dtos.ApiResponse(true, "Registration successful", user));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(new Dtos.ApiResponse(false, e.getMessage(), null));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<Dtos.ApiResponse> login(@RequestBody Dtos.LoginRequest request) {
		try {
			Dtos.UserResponse user = userService.login(request);
			return ResponseEntity.ok(new Dtos.ApiResponse(true, "Login successful", user));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(new Dtos.ApiResponse(false, e.getMessage(), null));
		}
	}
}
