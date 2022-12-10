package com.udacity.jdnd.course3.critter.user.entity;

import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.user.utils.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue(value="E")
public class Employee extends User {

    @ElementCollection
    @CollectionTable(name="employeeSkills", joinColumns=@JoinColumn(name="user_id"))
    @Column(name = "skill")
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @CollectionTable(name="daysAvailable", joinColumns=@JoinColumn(name="user_id"))
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany
    @JoinTable(name="employee_schedules", joinColumns = {@JoinColumn(name="user_id")},inverseJoinColumns = {@JoinColumn(name="schedule_id")})
    private List<Schedule> schedules;

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

}
