package com.udacity.jdnd.course3.critter.pet.entity;

import com.udacity.jdnd.course3.critter.pet.utils.PetType;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pet_id",nullable = false)
    private Long id;
    @Column(nullable = false)
    private PetType type;

    @Nationalized
    @Column(nullable = false)
    private String name;

    private LocalDate birthDate;

    @Nationalized
    private String notes;

    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="pet_schedules", joinColumns = {@JoinColumn(name="pet_id")},inverseJoinColumns = {@JoinColumn(name="schedule_id")})
    private List<Schedule> petSchedules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Schedule> getPetSchedules() {
        return petSchedules;
    }

    public void setPetSchedules(List<Schedule> petSchedules) {
        this.petSchedules = petSchedules;
    }

}
