package com.finance.tracker.controller;

import com.finance.tracker.dto.Dtos;
import com.finance.tracker.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/income")
@CrossOrigin(origins = "*")
public class IncomeController {

	@Autowired
	private IncomeService incomeService;

	@PostMapping
	public ResponseEntity<Dtos.ApiResponse> addIncome(@RequestHeader("X-User-Id") Long userId,
			@RequestBody Dtos.IncomeRequest request) {
		try {
			Dtos.IncomeResponse income = incomeService.addIncome(userId, request);
			return ResponseEntity.ok(new Dtos.ApiResponse(true, "Income added", income));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(new Dtos.ApiResponse(false, e.getMessage(), null));
		}
	}

	@GetMapping
	public ResponseEntity<Dtos.ApiResponse> getIncome(@RequestHeader("X-User-Id") Long userId) {
		return ResponseEntity.ok(new Dtos.ApiResponse(true, "OK", incomeService.getIncome(userId)));
	}
}
