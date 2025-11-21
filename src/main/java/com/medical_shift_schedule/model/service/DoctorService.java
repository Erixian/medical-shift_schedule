package com.medical_shift_schedule.model.service;


import com.medical_shift_schedule.model.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor findByName(String name);

    List<Doctor> findAll();

    Doctor findById(Long id);

    Doctor create(Doctor doctorToCreate);

    void delete(Long id);
}