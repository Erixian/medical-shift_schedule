package com.medical_shift_schedule.model.service;


import com.medical_shift_schedule.model.Doctor;

import java.util.List;

public interface DoctorService {

    List<Doctor> findByNameContaining(String name);

    List<Doctor> findAll();

    Doctor findById(Long id);

    Doctor create(Doctor doctorToCreate);

    void delete(Long id);

    void updateDoctor(Long id, String name, String crm);

}