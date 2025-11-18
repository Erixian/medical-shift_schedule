package com.medical_shift_schedule.controller;

import com.medical_shift_schedule.model.Hospital;
import com.medical_shift_schedule.model.service.HospitalService;
import com.medical_shift_schedule.model.service.imple.HospitalServiceImple;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String listDoctors(Model model) {
        List<Hospital> hospitals = hospitalService.findAll();
        model.addAttribute("hospitals", hospitals);
        return "/hospital/list-hospital";
    }
}
