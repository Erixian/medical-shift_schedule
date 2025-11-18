package com.medical_shift_schedule.model.service;

import com.medical_shift_schedule.model.Hospital;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HospitalService {
    List<Hospital> findAll();
    Hospital findById(Long id);
    Hospital create(Hospital hospitalToCreate);
    void delete(Long id);
}
