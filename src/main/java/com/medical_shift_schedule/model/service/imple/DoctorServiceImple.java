package com.medical_shift_schedule.model.service.imple;

import com.medical_shift_schedule.model.Doctor;
import com.medical_shift_schedule.model.repository.DoctorRepository;
import com.medical_shift_schedule.model.repository.ShiftRepository;
import com.medical_shift_schedule.model.service.DoctorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DoctorServiceImple implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final ShiftRepository shiftRepository;

    public DoctorServiceImple(DoctorRepository doctorRepository, ShiftRepository shiftRepository){
        this.shiftRepository = shiftRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void updateDoctor(Long id, String name, String crm) {
        Doctor doctor = doctorRepository.findById(id).get();
        doctor.setName(name);
        doctor.setCrm(crm);
        doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> findByNameContaining(String name) {
        return doctorRepository.findByNameContaining(name);
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

    //@Transactional
    @Override
    public void delete(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow();

        if(shiftRepository.findShiftsByDoctorId(doctor.getId()).isEmpty()) {
            doctorRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Não é possivel excluir um medico com plantões cadastrados");
        }
    }

}
