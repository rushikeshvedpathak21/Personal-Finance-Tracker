package com.finance.tracker.controller;

import com.finance.tracker.dto.Dtos;
import com.finance.tracker.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "*")
public class BudgetController {

	@Autowired
	private BudgetService budgetService;

	@PostMapping
	public ResponseEntity<Dtos.ApiResponse> saveBudget(@RequestHeader("X-User-Id") Long userId,
			@RequestBody Dtos.BudgetRequest request) {
		try {
			Dtos.BudgetResponse budget = budgetService.saveBudget(userId, request);
			return ResponseEntity.ok(new Dtos.ApiResponse(true, "Budget saved", budget));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(new Dtos.ApiResponse(false, e.getMessage(), null));
		}
	}

	@GetMapping
	public ResponseEntity<Dtos.ApiResponse> getBudgets(@RequestHeader("X-User-Id") Long userId,
			@RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year) {
		int m = month != null ? month : LocalDate.now().getMonthValue();
		int y = year != null ? year : LocalDate.now().getYear();
		return ResponseEntity.ok(new Dtos.ApiResponse(true, "OK", budgetService.getBudgets(userId, m, y)));
	}
}
