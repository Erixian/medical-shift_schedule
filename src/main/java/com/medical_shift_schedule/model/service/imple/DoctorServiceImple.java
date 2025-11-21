package com.medical_shift_schedule.model.service.imple;

import com.medical_shift_schedule.model.Doctor;
import com.medical_shift_schedule.model.repository.DoctorRepository;
import com.medical_shift_schedule.model.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DoctorServiceImple implements DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorServiceImple(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor findByName(String name) {
        return doctorRepository.findByName(name);
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }
    @Override
    public Doctor findById(Long id) {
        return doctorRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Doctor create(Doctor doctorToCreate) {
        if(doctorRepository.existsByCrm(doctorToCreate.getCrm())){
            throw new IllegalArgumentException("This CRM already exists");
        }
        return doctorRepository.save(doctorToCreate);
    }

    @Override
    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }

}
