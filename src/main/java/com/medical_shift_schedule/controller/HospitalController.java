package com.medical_shift_schedule.controller;

import com.medical_shift_schedule.model.Doctor;
import com.medical_shift_schedule.model.Hospital;
import com.medical_shift_schedule.model.service.HospitalService;
import com.medical_shift_schedule.model.service.imple.HospitalServiceImple;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HospitalController {
    private final HospitalService hospitalService;
    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/hospital/list-hospital")
    public String listHospital(Model model) {
        List<Hospital> hospitals = hospitalService.findAll();
        model.addAttribute("hospitals", hospitals);
        return "/hospital/list-hospital";
    }

    @GetMapping("/hospital/create-hospital")
    public String showCreateForm(Model model){
        model.addAttribute("hospital", new Hospital());
        return "/hospital/create-hospital";
    }

    @PostMapping("/hospital/create-hospital")
    public String create(@ModelAttribute Hospital hospitalToCreate, Model model){
        var hospitalCreated = hospitalService.create(hospitalToCreate);
        model.addAttribute("successMessage", "Hospital '"
                + hospitalCreated.getName()
                + "' created successfully!");

        model.addAttribute("hospital", new Hospital());

        return "/hospital/create-hospital";
    }
}
