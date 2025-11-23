package com.medical_shift_schedule.controller;

import com.medical_shift_schedule.model.Doctor;
import com.medical_shift_schedule.model.Shift;
import com.medical_shift_schedule.model.service.DoctorService;
import com.medical_shift_schedule.model.service.HospitalService;
import com.medical_shift_schedule.model.service.ShiftService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("shift/create-shift")
    public String showCreatedShiftForm(Model model) {
        Shift shift = new Shift();
        formatDoctorList(shift);
        model.addAttribute("shift", shift);
        model.addAttribute("allHospitals", hospitalService.findAll());
        model.addAttribute("allDoctors", doctorService.findAll());
        return "shift/create-shift";
    }

    @PostMapping("shift/create-shift")
    public String create(@ModelAttribute Shift shiftToCreate, Model model) {
        try {
            var shiftCreated = shiftService.create(shiftToCreate);

            model.addAttribute("successMessage", "Plantão Criado com Sucesso!");
            model.addAttribute("shift", shiftCreated);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("shift", shiftToCreate);
        }

        model.addAttribute("allHospitals", hospitalService.findAll());
        model.addAttribute("allDoctors", doctorService.findAll());

        return "shift/create-shift";
    }

    @GetMapping("shift/list-shift-doctors/{id}")
    public String listShiftDoctors(@PathVariable Long id, Model model) {
        List<Doctor> shiftDoctors = shiftService.findDoctorsByShiftId(id);
        model.addAttribute("doctors", shiftDoctors);
        return "shift/list-shift-doctors";
    }

    @GetMapping("shift/{id}/edit")
    public String showEditShiftForm(@PathVariable Long id, Model model) {
        Shift shift = shiftService.findById(id);
        formatDoctorList(shift);
        model.addAttribute("shift", shift);
        model.addAttribute("allHospitals", hospitalService.findAll());
        model.addAttribute("allDoctors", doctorService.findAll());
        return "shift/edit-shift";
    }

    @PostMapping("shift/{id}/edit")
    public String updateShift(@PathVariable Long id, @ModelAttribute Shift shiftToUpdate, Model model) {
        try {
            shiftService.updateShiftDoctors(id, shiftToUpdate.getDoctorList());
            model.addAttribute("successMessage", "Plantão atualizado com sucesso!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        Shift updatedShift = shiftService.findById(id);
        formatDoctorList(updatedShift);
        model.addAttribute("shift", shiftService.findById(id));
        model.addAttribute("allHospitals", hospitalService.findAll());
        model.addAttribute("allDoctors", doctorService.findAll());
        return "shift/edit-shift";
    }
}
