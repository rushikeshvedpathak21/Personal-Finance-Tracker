package com.finance.tracker.repository;

import com.finance.tracker.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

	List<Income> findByUserIdOrderByIncomeDateDesc(Long userId);

	@Query("SELECT i FROM Income i WHERE i.user.id = :userId " + "AND (:source IS NULL OR i.source = :source) "
			+ "ORDER BY i.incomeDate DESC")
	List<Income> searchIncome(@Param("userId") Long userId, @Param("source") String source);
}
