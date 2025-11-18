package com.medical_shift_schedule.model.service;

import com.medical_shift_schedule.model.Hospital;

import java.util.List;

public interface HospitalService {
    List<Hospital> findAll();
    Hospital findById(Long id);
    Hospital create(Hospital hospitalToCreate);
    void delete(Long id);
}
