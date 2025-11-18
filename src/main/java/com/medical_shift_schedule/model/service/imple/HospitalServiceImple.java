package com.medical_shift_schedule.model.service.imple;

import com.medical_shift_schedule.model.Hospital;
import com.medical_shift_schedule.model.repository.HospitalRepository;
import com.medical_shift_schedule.model.service.HospitalService;

import java.util.List;
import java.util.NoSuchElementException;

public class HospitalServiceImple implements HospitalService {
    private final HospitalRepository hospitalRepository;

    public HospitalServiceImple(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public List<Hospital> findAll() {
        return hospitalRepository.findAll();
    }

    @Override
    public Hospital findById(Long id) {
        return hospitalRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Hospital create(Hospital hospitalToCreate) {
            if(hospitalRepository.existsByName(hospitalToCreate.getName())) {
                throw new IllegalArgumentException("This Hospital already exists");
            }
            return hospitalRepository.save(hospitalToCreate);
    }

    @Override
    public void delete(Long id) {
        hospitalRepository.deleteById(id);
    }
}
