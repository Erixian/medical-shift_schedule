package com.medical_shift_schedule.model.repository;

import com.medical_shift_schedule.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByCrm(String crm);
    List<Doctor> findAll();
}
