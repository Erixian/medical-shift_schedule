package com.medical_shift_schedule.model.repository;

import com.medical_shift_schedule.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    boolean existsById(Long id);
    List<Shift> findAll();
    void deleteById(Long id);
}
