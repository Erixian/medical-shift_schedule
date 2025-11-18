package com.medical_shift_schedule.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "tb_hospital")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String address;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Shift> shifts;

    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }
}
