package com.finance.tracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Dtos {

	public static class RegisterRequest {
		private String name;
		private String email;
		private String password;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static class LoginRequest {
		private String email;
		private String password;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static class UserResponse {
		private Long id;
		private String name;
		private String email;

		public UserResponse(Long id, String name, String email) {
			this.id = id;
			this.name = name;
			this.email = email;
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getEmail() {
			return email;
		}
	}

	public static class ExpenseRequest {
		private BigDecimal amount;
		private String category;
		private String description;
		private LocalDate expenseDate;

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public LocalDate getExpenseDate() {
			return expenseDate;
		}

		public void setExpenseDate(LocalDate expenseDate) {
			this.expenseDate = expenseDate;
		}
	}

	public static class ExpenseResponse {
		private Long id;
		private BigDecimal amount;
		private String category;
		private String description;
		private LocalDate expenseDate;
		private LocalDateTime createdAt;

		public ExpenseResponse(Long id, BigDecimal amount, String category, String description, LocalDate expenseDate,
				LocalDateTime createdAt) {
			this.id = id;
			this.amount = amount;
			this.category = category;
			this.description = description;
			this.expenseDate = expenseDate;
			this.createdAt = createdAt;
		}

		public Long getId() {
			return id;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public String getCategory() {
			return category;
		}

		public String getDescription() {
			return description;
		}

		public LocalDate getExpenseDate() {
			return expenseDate;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
	}

	public static class IncomeRequest {
		private BigDecimal amount;
		private String source;
		private LocalDate incomeDate;
		private String frequency;

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public LocalDate getIncomeDate() {
			return incomeDate;
		}

		public void setIncomeDate(LocalDate incomeDate) {
			this.incomeDate = incomeDate;
		}

		public String getFrequency() {
			return frequency;
		}

		public void setFrequency(String frequency) {
			this.frequency = frequency;
		}
	}

	public static class IncomeResponse {
		private Long id;
		private BigDecimal amount;
		private String source;
		private LocalDate incomeDate;
		private String frequency;
		private LocalDateTime createdAt;

		public IncomeResponse(Long id, BigDecimal amount, String source, LocalDate incomeDate, String frequency,
				LocalDateTime createdAt) {
			this.id = id;
			this.amount = amount;
			this.source = source;
			this.incomeDate = incomeDate;
			this.frequency = frequency;
			this.createdAt = createdAt;
		}

		public Long getId() {
			return id;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public String getSource() {
			return source;
		}

		public LocalDate getIncomeDate() {
			return incomeDate;
		}

		public String getFrequency() {
			return frequency;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
	}

	public static class BudgetRequest {
		private String category;
		private BigDecimal monthlyLimit;
		private Integer month;
		private Integer year;

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public BigDecimal getMonthlyLimit() {
			return monthlyLimit;
		}

		public void setMonthlyLimit(BigDecimal monthlyLimit) {
			this.monthlyLimit = monthlyLimit;
		}

		public Integer getMonth() {
			return month;
		}

		public void setMonth(Integer month) {
			this.month = month;
		}

		public Integer getYear() {
			return year;
		}

		public void setYear(Integer year) {
			this.year = year;
		}
	}

	public static class BudgetResponse {
		private Long id;
		private String category;
		private BigDecimal monthlyLimit;
		private BigDecimal amountSpent;
		private Integer month;
		private Integer year;

		public BudgetResponse(Long id, String category, BigDecimal monthlyLimit, BigDecimal amountSpent, Integer month,
				Integer year) {
			this.id = id;
			this.category = category;
			this.monthlyLimit = monthlyLimit;
			this.amountSpent = amountSpent;
			this.month = month;
			this.year = year;
		}

		public Long getId() {
			return id;
		}

		public String getCategory() {
			return category;
		}

		public BigDecimal getMonthlyLimit() {
			return monthlyLimit;
		}

		public BigDecimal getAmountSpent() {
			return amountSpent;
		}

		public Integer getMonth() {
			return month;
		}

		public Integer getYear() {
			return year;
		}
	}

	public static class ApiResponse {
		private boolean success;
		private String message;
		private Object data;

		public ApiResponse(boolean success, String message, Object data) {
			this.success = success;
			this.message = message;
			this.data = data;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage() {
			return message;
		}

		public Object getData() {
			return data;
		}
	}
}
