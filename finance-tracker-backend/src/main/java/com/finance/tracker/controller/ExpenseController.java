package com.finance.tracker.controller;

import com.finance.tracker.dto.Dtos;
import com.finance.tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@PostMapping
	public ResponseEntity<Dtos.ApiResponse> addExpense(@RequestHeader("X-User-Id") Long userId,
			@RequestBody Dtos.ExpenseRequest request) {
		try {
			Dtos.ExpenseResponse expense = expenseService.addExpense(userId, request);
			return ResponseEntity.ok(new Dtos.ApiResponse(true, "Expense added", expense));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(new Dtos.ApiResponse(false, e.getMessage(), null));
		}
	}

	@GetMapping
	public ResponseEntity<Dtos.ApiResponse> getExpenses(@RequestHeader("X-User-Id") Long userId) {
		return ResponseEntity.ok(new Dtos.ApiResponse(true, "OK", expenseService.getExpenses(userId)));
	}

	@GetMapping("/search")
	public ResponseEntity<Dtos.ApiResponse> searchExpenses(@RequestHeader("X-User-Id") Long userId,
			@RequestParam(required = false) String category, @RequestParam(required = false) String keyword) {
		return ResponseEntity
				.ok(new Dtos.ApiResponse(true, "OK", expenseService.searchExpenses(userId, category, keyword)));
	}
}
