package com.udacity.jdnd.course3.critter.schedule.controller;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.model.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;
    private PetService petService;
    private EmployeeService employeeService;

    ScheduleController(ScheduleService scheduleService, PetService petService, EmployeeService employeeService){
        this.scheduleService = scheduleService;
        this.petService = petService;
        this.employeeService = employeeService;
    }

    /**return a saved schedule*/
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO){
        Schedule schedule = this.convertScheduleDTOToSchedule(scheduleDTO);
        schedule = scheduleService.createSchedule(schedule);
        if(schedule!=null){
            scheduleDTO = this.convertScheduleToScheduleDTO(schedule);
        }else{
            scheduleDTO = null;
        }
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getAllSchedules();
        schedules.forEach(schedule -> {
            ScheduleDTO scheduleDTO = this.convertScheduleToScheduleDTO(schedule);
            scheduleDTOs.add(scheduleDTO);
        });
        return scheduleDTOs;
    }

    /**return all saved schedules for that pet.*/
    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getSchedulesByPetId(petId);
        schedules.forEach(schedule -> {
            ScheduleDTO scheduleDTO = this.convertScheduleToScheduleDTO(schedule);
            scheduleDTOs.add(scheduleDTO);
        });
        return scheduleDTOs;

    }

    /**return all saved schedules containing that employee*/
    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getSchedulesByEmployeeId(employeeId);
        schedules.forEach(schedule -> {
            ScheduleDTO scheduleDTO = this.convertScheduleToScheduleDTO(schedule);
            scheduleDTOs.add(scheduleDTO);
        });
        return scheduleDTOs;

    }

    /**return all saved schedules for any pets belonging to that owner*/
    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        List<Pet> pets = petService.findPetByOwnerId(customerId);
        List<Schedule> schedules = scheduleService.getSchedulesByPets(pets);
        schedules.forEach(schedule -> {
            ScheduleDTO scheduleDTO = this.convertScheduleToScheduleDTO(schedule);
            scheduleDTOs.add(scheduleDTO);
        });
        return scheduleDTOs;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){

        Schedule schedule = new Schedule();
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());

        List<Pet> pets = new ArrayList<>();
        for (Long petId : scheduleDTO.getPetIds()){
            Pet pet = petService.findPetById(petId);
            pets.add(pet);
        }
        schedule.setPets(pets);

        List<Employee> employees = new ArrayList<>();
        for(Long employeeId : scheduleDTO.getEmployeeIds()){
            Employee employee = employeeService.findEmployeeById(employeeId);
            employees.add(employee);
        }
        schedule.setEmployees(employees);
        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule,scheduleDTO);
        List<Long> employeeIds = new ArrayList<>();
        schedule.getEmployees().stream().forEach(e->{
            employeeIds.add(e.getId());
        });
        scheduleDTO.setEmployeeIds(employeeIds);
        List<Long> petIds = new ArrayList<>();
        schedule.getPets().stream().forEach(p->{
            petIds.add(p.getId());
        });
        scheduleDTO.setPetIds(petIds);
        return scheduleDTO;
    }
}
