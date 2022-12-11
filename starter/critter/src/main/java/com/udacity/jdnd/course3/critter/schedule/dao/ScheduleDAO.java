package com.udacity.jdnd.course3.critter.schedule.dao;

import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleDAO {

    // Create
    public Long create(Schedule schedule);

    // Read
    public List<Schedule> findAll();

    public Schedule findById(Long id);

    // Update
    public Long update(Schedule schedule);

    // Delete
    public void delete(Long id);

}
