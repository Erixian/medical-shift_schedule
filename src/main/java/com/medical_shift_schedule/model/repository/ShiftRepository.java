package com.medical_shift_schedule.model.repository;

import com.medical_shift_schedule.model.Doctor;
import com.medical_shift_schedule.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.print.Doc;
import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    boolean existsById(Long id);
    List<Shift> findAll();
    void deleteById(Long id);

    @Query("SELECT s FROM tb_shift s JOIN s.doctorList d WHERE d.id = :doctorId")
    List<Shift> findShiftsByDoctorId(@Param("doctorId") Long doctorId);


}
