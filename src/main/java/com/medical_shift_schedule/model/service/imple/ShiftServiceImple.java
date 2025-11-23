package com.medical_shift_schedule.model.service.imple;

import com.medical_shift_schedule.model.Doctor;
import com.medical_shift_schedule.model.Hospital;
import com.medical_shift_schedule.model.Shift;
import com.medical_shift_schedule.model.repository.DoctorRepository;
import com.medical_shift_schedule.model.repository.HospitalRepository;
import com.medical_shift_schedule.model.repository.ShiftRepository;
import com.medical_shift_schedule.model.service.DoctorService;
import com.medical_shift_schedule.model.service.ShiftService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ShiftServiceImple implements ShiftService {
    private final ShiftRepository shiftRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final DoctorService doctorService;

    public ShiftServiceImple(ShiftRepository shiftRepository,
                             DoctorRepository doctorRepository,
                             HospitalRepository hospitalRepository, DoctorService doctorService) {
        this.shiftRepository = shiftRepository;
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
        this.doctorService = doctorService;
    }

    @Override
    public List<Shift> findShiftsByDoctor(Long doctorId) {
        return shiftRepository.findShiftsByDoctorId(doctorId);
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

    @Override
    public List<Doctor> findDoctorsByShiftId(Long id) {
        Shift shift = shiftRepository.findById(id).orElseThrow();
        return shift.getDoctorList();
    }

    public Shift updateShiftDoctors(Long shiftId, List<Doctor> newDoctorList) {
        Shift shift = shiftRepository.findById(shiftId).orElseThrow();

        List<Doctor> validDoctors = new ArrayList<>();
        if (newDoctorList != null) {
            for (Doctor doctor : newDoctorList) {
                if (doctor.getId() != null) {
                    validDoctors.add(doctorRepository.findById(doctor.getId()).orElseThrow());
                }
            }
        }
        shift.setDoctorList(validDoctors);
        return shiftRepository.save(shift);
    }
}
