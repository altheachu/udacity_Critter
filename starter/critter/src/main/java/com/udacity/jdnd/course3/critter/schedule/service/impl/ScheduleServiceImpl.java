package com.udacity.jdnd.course3.critter.schedule.service.impl;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import com.udacity.jdnd.course3.critter.user.utils.EmployeeSkill;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepository scheduleRepository;
    private PetRepository petRepository;
    private EmployeeService employeeService;

    ScheduleServiceImpl(ScheduleRepository scheduleRepository, PetRepository petRepository, EmployeeService employeeService){
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.employeeService = employeeService;
    }

    @Override
    public Schedule createSchedule(Schedule schedule) throws Exception{

        boolean isActivityMatchSkills = false;
        Set<EmployeeSkill> allAssignedEmployeesSkills = new HashSet<>();
        List<Employee> employees = schedule.getEmployees();
        employees.forEach(employee->{
            employee.getSkills().forEach(skill->{
                allAssignedEmployeesSkills.add(skill);
            });
        });

        Set<EmployeeSkill> activities = schedule.getActivities();
        for(EmployeeSkill activity:activities){
            isActivityMatchSkills = allAssignedEmployeesSkills.contains(activity);
            if(!isActivityMatchSkills){
                break;
            }
        }

        if(!isActivityMatchSkills) {
            return null;
        }
        return scheduleRepository.save(schedule);

    }

    @Override
    public List<Schedule> getAllSchedules() throws Exception{
        List<Schedule> schedules = new ArrayList<>();
        Iterable<Schedule> iter = scheduleRepository.findAll();
        for(Iterator<Schedule> iterator = iter.iterator(); iterator.hasNext(); ){
            schedules.add(iterator.next());
        }
        return schedules;
    }

    @Override
    public List<Schedule> getSchedulesByPetId(long petId) throws Exception{
        Pet pet = petRepository.findById(petId).get();
        return scheduleRepository.findSchedulesByPets(pet);
    }

    @Override
    public List<Schedule> getSchedulesByEmployeeId(long employeeId) throws Exception{
        Employee employee = employeeService.findEmployeeById(employeeId);
        return scheduleRepository.findSchedulesByEmployees(employee);
    }

    @Override
    public List<Schedule> getSchedulesByPets(List<Pet> pets) throws Exception{
        return scheduleRepository.findSchedulesByPetsIn(pets);
    }
}
