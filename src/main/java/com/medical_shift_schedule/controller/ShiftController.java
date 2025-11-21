package com.medical_shift_schedule.controller;

import com.medical_shift_schedule.model.Doctor;
import com.medical_shift_schedule.model.Shift;
import com.medical_shift_schedule.model.service.DoctorService;
import com.medical_shift_schedule.model.service.HospitalService;
import com.medical_shift_schedule.model.service.ShiftService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class ShiftController {
    private final ShiftService shiftService;
    private final DoctorService doctorService;
    private final HospitalService hospitalService;

    public ShiftController(ShiftService ss, DoctorService ds, HospitalService hs) {
        this.shiftService = ss;
        this.doctorService = ds;
        this.hospitalService = hs;
    }

    public void formatDoctorList(Shift shift) {
        if(shift.getDoctorList() == null) {
            shift.setDoctorList(new ArrayList<Doctor>());
        }

        while(shift.getDoctorList().size() < 4) {
            shift.getDoctorList().add(new Doctor());
        }
    }

    @GetMapping("shift/list-shift")
    public String listShifts(Model model) {
        List<Shift> shiftList = shiftService.findAll();
        model.addAttribute("shift", shiftList);
        return "shift/list-shift";
    }

    @GetMapping("/shift/create-shift")
    public String showCreatedShiftForm(Model model) {
        Shift shift = new Shift();
        formatDoctorList(shift);
        model.addAttribute("shift", shift);
        model.addAttribute("allHospitals", hospitalService.findAll());
        model.addAttribute("allDoctors", doctorService.findAll());
        return "/shift/create-shift";
    }

    @PostMapping("/shift/create-shift")
    public String create(@ModelAttribute Shift shiftToCreate, Model model) {

        System.out.println("--- DEBUG START ---");
        if (shiftToCreate.getHospital() != null) {
            System.out.println("Hospital Object exists.");
            System.out.println("Hospital ID received: " + shiftToCreate.getHospital().getId());
        } else {
            System.out.println("Hospital Object is NULL (Frontend/Binding failed)");
        }
        System.out.println("--- DEBUG END ---");

        try {
            var shiftCreated = shiftService.create(shiftToCreate);

            model.addAttribute("successMessage", "Shift created successfully!");
            model.addAttribute("shift", shiftCreated);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("shift", shiftToCreate);
        }

        model.addAttribute("allHospitals", hospitalService.findAll());
        model.addAttribute("allDoctors", doctorService.findAll());

        return "/shift/create-shift";
    }
}
