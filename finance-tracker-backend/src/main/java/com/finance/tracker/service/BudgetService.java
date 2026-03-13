package com.finance.tracker.service;

import com.finance.tracker.dto.Dtos;
import com.finance.tracker.model.Budget;
import com.finance.tracker.model.User;
import com.finance.tracker.repository.BudgetRepository;
import com.finance.tracker.repository.ExpenseRepository;
import com.finance.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BudgetService {

	@Autowired
	private BudgetRepository budgetRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserRepository userRepository;

	public Dtos.BudgetResponse saveBudget(Long userId, Dtos.BudgetRequest request) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		Optional<Budget> existing = budgetRepository.findByUserIdAndCategoryAndMonthAndYear(userId,
				request.getCategory(), request.getMonth(), request.getYear());

		Budget budget = existing.orElse(new Budget());
		budget.setUser(user);
		budget.setCategory(request.getCategory());
		budget.setMonthlyLimit(request.getMonthlyLimit());
		budget.setMonth(request.getMonth());
		budget.setYear(request.getYear());

		Budget saved = budgetRepository.save(budget);
		BigDecimal spent = getSpentAmount(userId, saved.getCategory(), saved.getMonth(), saved.getYear());
		return toResponse(saved, spent);
	}

	public List<Dtos.BudgetResponse> getBudgets(Long userId, int month, int year) {
		return budgetRepository.findByUserIdAndMonthAndYear(userId, month, year).stream().map(b -> {
			BigDecimal spent = getSpentAmount(userId, b.getCategory(), b.getMonth(), b.getYear());
			return toResponse(b, spent);
		}).collect(Collectors.toList());
	}

	private BigDecimal getSpentAmount(Long userId, String category, int month, int year) {
		BigDecimal spent = expenseRepository.sumByUserAndCategoryAndMonth(userId, category, month, year);
		return spent != null ? spent : BigDecimal.ZERO;
	}

	private Dtos.BudgetResponse toResponse(Budget b, BigDecimal spent) {
		return new Dtos.BudgetResponse(b.getId(), b.getCategory(), b.getMonthlyLimit(), spent, b.getMonth(),
				b.getYear());
	}
}
