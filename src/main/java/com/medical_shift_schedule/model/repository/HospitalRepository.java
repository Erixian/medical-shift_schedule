package com.medical_shift_schedule.model.repository;

import com.medical_shift_schedule.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    boolean existsByName(String name);
    List<Hospital> findAll();
}