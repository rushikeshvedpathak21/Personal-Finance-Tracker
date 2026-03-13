package com.finance.tracker.service;

import com.finance.tracker.dto.Dtos;
import com.finance.tracker.model.Income;
import com.finance.tracker.model.User;
import com.finance.tracker.repository.IncomeRepository;
import com.finance.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    public Dtos.IncomeResponse addIncome(Long userId, Dtos.IncomeRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Income income = new Income();
        income.setUser(user);
        income.setAmount(request.getAmount());
        income.setSource(request.getSource());
        income.setIncomeDate(request.getIncomeDate());
        income.setFrequency(request.getFrequency());

        Income saved = incomeRepository.save(income);
        return toResponse(saved);
    }

    public List<Dtos.IncomeResponse> getIncome(Long userId) {
        return incomeRepository.findByUserIdOrderByIncomeDateDesc(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private Dtos.IncomeResponse toResponse(Income i) {
        return new Dtos.IncomeResponse(
                i.getId(), i.getAmount(), i.getSource(),
                i.getIncomeDate(), i.getFrequency(), i.getCreatedAt()
        );
    }
}
