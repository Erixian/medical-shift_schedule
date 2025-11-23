package com.medical_shift_schedule.model.service;

import com.medical_shift_schedule.model.Doctor;
import com.medical_shift_schedule.model.Hospital;
import com.medical_shift_schedule.model.Shift;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShiftService {
    List<Shift> findAll();
    Shift findById(Long id);
    Shift create(Shift shiftToCreate);
    void delete(Long id);
    List<Shift> findShiftsByDoctor(Long doctorId);
    List<Doctor> findDoctorsByShiftId(Long id);
    Shift updateShiftDoctors(Long shiftId, List<Doctor> newDoctorList);
}
