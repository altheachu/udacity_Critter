package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleService {

    Schedule createSchedule(Schedule schedule);

    List<Schedule> getAllSchedules();

    List<Schedule> getSchedulesByPetId(long petId);

    List<Schedule> getSchedulesByEmployeeId(long employeeId) throws Exception;

    List<Schedule> getSchedulesByPets(List<Pet> pets);
}
