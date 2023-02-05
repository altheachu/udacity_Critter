package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleService {

    Schedule createSchedule(Schedule schedule) throws Exception;

    List<Schedule> getAllSchedules() throws Exception;

    List<Schedule> getSchedulesByPetId(long petId) throws Exception;

    List<Schedule> getSchedulesByEmployeeId(long employeeId) throws Exception;

    List<Schedule> getSchedulesByPets(List<Pet> pets) throws Exception;
}
