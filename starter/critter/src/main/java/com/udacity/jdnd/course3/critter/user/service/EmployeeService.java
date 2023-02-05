package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.utils.EmployeeSkill;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface EmployeeService {

    Employee saveEmployee(Employee employee) throws Exception;

    Employee findEmployeeById(long employeeId) throws Exception;

    void saveDaysAvailableByEmployeeId(Set<DayOfWeek> daysAvailable, long employeeId) throws Exception;

    List<Employee> findEmployeeMeetService(LocalDate date, Set<EmployeeSkill> skills) throws Exception;
}
