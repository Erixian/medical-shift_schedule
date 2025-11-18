package com.medical_shift_schedule.controller;

import com.medical_shift_schedule.model.Doctor;
import com.medical_shift_schedule.model.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import javax.print.Doc;
import java.util.List;

@Controller
@RequestMapping("/")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/doctor/list-doctor")
    public String listDoctors(Model model) {
        List<Doctor> doctors = doctorService.findAll();
        model.addAttribute("doctors", doctors);
        return "/doctor/list-doctor";
    }
    @GetMapping("/doctor/create-doctor")
    public String showCreateForm(Model model){
        model.addAttribute("doctor", new Doctor());
        return "/doctor/create-doctor";
    }

    @PostMapping("/doctor/create-doctor")
    public String create(@ModelAttribute Doctor doctorToCreate, Model model){
        var doctorCreated = doctorService.create(doctorToCreate);
        model.addAttribute("successMessage", "Doctor '"
                            + doctorCreated.getName()
                            + "' created successfully!");

        model.addAttribute("doctor", new Doctor());

        return "/doctor/create-doctor"; // Return the same template name
    }
}
