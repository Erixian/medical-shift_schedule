package com.medical_shift_schedule.model.repository;

import com.medical_shift_schedule.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByCrm(String crm);
    List<Doctor> findAll();

    @Query("SELECT d FROM tb_doctor d WHERE d.name LIKE %:doctorName%")
    List<Doctor> findByNameContaining(@Param("doctorName") String doctorName);
}
