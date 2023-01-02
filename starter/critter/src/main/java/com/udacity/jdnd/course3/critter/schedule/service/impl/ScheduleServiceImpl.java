package com.udacity.jdnd.course3.critter.schedule.service.impl;

import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.utils.EmployeeSkill;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepository scheduleRepository;

    ScheduleServiceImpl(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule createSchedule(Schedule schedule){

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
    public List<Schedule> getAllSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        Iterable<Schedule> iter = scheduleRepository.findAll();
        for(Iterator<Schedule> iterator = iter.iterator(); iterator.hasNext(); ){
            schedules.add(iterator.next());
        }
        return schedules;
    }
}
