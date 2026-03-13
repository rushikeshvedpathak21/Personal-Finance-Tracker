package com.finance.tracker.service;

import com.finance.tracker.dto.Dtos;
import com.finance.tracker.model.Expense;
import com.finance.tracker.model.User;
import com.finance.tracker.repository.ExpenseRepository;
import com.finance.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserRepository userRepository;

	public Dtos.ExpenseResponse addExpense(Long userId, Dtos.ExpenseRequest request) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		Expense expense = new Expense();
		expense.setUser(user);
		expense.setAmount(request.getAmount());
		expense.setCategory(request.getCategory());
		expense.setDescription(request.getDescription());
		expense.setExpenseDate(request.getExpenseDate());

		Expense saved = expenseRepository.save(expense);
		return toResponse(saved);
	}

	public List<Dtos.ExpenseResponse> getExpenses(Long userId) {
		return expenseRepository.findByUserIdOrderByExpenseDateDesc(userId).stream().map(this::toResponse)
				.collect(Collectors.toList());
	}

	public List<Dtos.ExpenseResponse> searchExpenses(Long userId, String category, String keyword) {
		String cat = (category != null && category.isBlank()) ? null : category;
		String kw = (keyword != null && keyword.isBlank()) ? null : keyword;
		return expenseRepository.searchExpenses(userId, cat, kw).stream().map(this::toResponse)
				.collect(Collectors.toList());
	}

	private Dtos.ExpenseResponse toResponse(Expense e) {
		return new Dtos.ExpenseResponse(e.getId(), e.getAmount(), e.getCategory(), e.getDescription(),
				e.getExpenseDate(), e.getCreatedAt());
	}
}
