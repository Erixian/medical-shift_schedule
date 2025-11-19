package com.medical_shift_schedule.model.service.imple;

import com.medical_shift_schedule.model.Doctor;
import com.medical_shift_schedule.model.Hospital;
import com.medical_shift_schedule.model.Shift;
import com.medical_shift_schedule.model.repository.DoctorRepository;
import com.medical_shift_schedule.model.repository.HospitalRepository;
import com.medical_shift_schedule.model.repository.ShiftRepository;
import com.medical_shift_schedule.model.service.ShiftService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ShiftServiceImple implements ShiftService {
    private final ShiftRepository shiftRepository;
    private final DoctorRepository doctorRepository;   // Inject repositories for validation
    private final HospitalRepository hospitalRepository; // Inject repositories for validation

    // Constructor should only inject the repositories it directly uses
    public ShiftServiceImple(ShiftRepository shiftRepository,
                             DoctorRepository doctorRepository,
                             HospitalRepository hospitalRepository) {
        this.shiftRepository = shiftRepository;
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public Shift create(Shift shiftToCreate) {
        if(shiftToCreate.getHospital() == null) {
            throw new IllegalArgumentException("Shift must be associate with a valid Hospital.");
        }
        Hospital existingHospital = hospitalRepository.findById(shiftToCreate.getHospital().getId())
                .orElseThrow(() -> new NoSuchElementException("Hospital with ID "
                                                                + shiftToCreate.getHospital().getId()
                                                                + "not found."));

        shiftToCreate.setHospital(existingHospital);
        shiftToCreate.setEndTime(shiftToCreate.getStartTime().plusHours(12));

        List<Doctor> validDoctors = new ArrayList<>();

        if(shiftToCreate.getDoctorList() != null) {
            for(Doctor inputDoc : shiftToCreate.getDoctorList()) {
                // SKIP empty dropdowns (where ID is null)
                if (inputDoc.getId() != null) {
                    Doctor existingDoctor = doctorRepository.findById(inputDoc.getId())
                            .orElseThrow(() -> new NoSuchElementException("Doctor with ID "
                                    + inputDoc.getId() + " not found."));
                    validDoctors.add(existingDoctor);
                }
            }
        }
        shiftToCreate.setDoctorList(validDoctors);

        return shiftRepository.save(shiftToCreate);
    }

    @Override
    public List<Shift> findAll() {
        return shiftRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!shiftRepository.existsById(id)) {
            throw new NoSuchElementException("Shift not found with ID: " + id);
        }
        shiftRepository.deleteById(id);
    }

    @Override
    public Shift findById(Long id) {
        return shiftRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Shift not found with ID: " + id));
    }
}
