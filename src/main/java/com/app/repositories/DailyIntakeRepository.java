package com.app.repositories;

import com.app.model.DailyIntake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyIntakeRepository extends JpaRepository<DailyIntake, Long> {
    List<DailyIntake> findByDateBetween(LocalDate start, LocalDate end);
}
