package com.finance.tracker.repository;

import com.finance.tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	List<Expense> findByUserIdOrderByExpenseDateDesc(Long userId);

	@Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.id = :userId "
			+ "AND e.category = :category AND MONTH(e.expenseDate) = :month AND YEAR(e.expenseDate) = :year")
	BigDecimal sumByUserAndCategoryAndMonth(@Param("userId") Long userId, @Param("category") String category,
			@Param("month") int month, @Param("year") int year);

	@Query("SELECT e FROM Expense e WHERE e.user.id = :userId " + "AND (:category IS NULL OR e.category = :category) "
			+ "AND (:keyword IS NULL OR LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
			+ "ORDER BY e.expenseDate DESC")
	List<Expense> searchExpenses(@Param("userId") Long userId, @Param("category") String category,
			@Param("keyword") String keyword);
}
