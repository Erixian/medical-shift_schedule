package com.medical_shift_schedule.controller;

import com.medical_shift_schedule.model.Doctor;
import com.medical_shift_schedule.model.Shift;
import com.medical_shift_schedule.model.service.DoctorService;
import com.medical_shift_schedule.model.service.ShiftService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/")
public class DoctorController {
    private final DoctorService doctorService;
    private final ShiftService shiftService;

    public DoctorController(DoctorService doctorService, ShiftService shiftService) {
        this.doctorService = doctorService;
        this.shiftService = shiftService;
    }

    @GetMapping("doctor/list")
    public String listDoctors(@RequestParam(required = false) String name, Model model) {
        List<Doctor> doctors;
        if (name != null && !name.trim().isEmpty()) {
            doctors = doctorService.findByNameContaining(name.trim());
            model.addAttribute("searchName", name);
        } else {
            doctors = doctorService.findAll();
        }
        model.addAttribute("doctors", doctors);
        return "doctor/list-doctor";
    }

    @GetMapping("doctor/list-doctor-shifts/{id}")
    public String getDoctorShifts(@PathVariable Long id, Model model) {
        Doctor doctor = doctorService.findById(id);
        List<Shift> doctorShifts = shiftService.findShiftsByDoctor(id);
        model.addAttribute("shifts", doctorShifts);
        model.addAttribute("doctor", doctor);
        return "doctor/list-doctor-shifts";
    }

    @GetMapping("doctor/list-doctor")
    public String listDoctors(Model model) {
        List<Doctor> doctors = doctorService.findAll();
        model.addAttribute("doctors", doctors);
        return "doctor/list-doctor";
    }

    @GetMapping("doctor/create-doctor")
    public String showCreateForm(Model model){
        model.addAttribute("doctor", new Doctor());
        return "doctor/create-doctor";
    }

    @PostMapping("/doctor/create-doctor")
    public String create(@ModelAttribute Doctor doctorToCreate, Model model){
        var doctorCreated = doctorService.create(doctorToCreate);
        model.addAttribute("successMessage", "Doctor '"
                            + doctorCreated.getName()
                            + "' created successfully!");

        model.addAttribute("doctor", new Doctor());

        return "doctor/create-doctor";
    }
}
