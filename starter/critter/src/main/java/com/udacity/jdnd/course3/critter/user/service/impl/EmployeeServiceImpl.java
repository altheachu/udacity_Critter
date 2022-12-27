package com.udacity.jdnd.course3.critter.user.service.impl;

import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import com.udacity.jdnd.course3.critter.user.utils.EmployeeSkill;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    @Override
    public void saveDaysAvailableByEmployeeId(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        if(employee!=null){
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        }
    }

    @Override
    public List<Employee> findEmployeeMeetService(LocalDate localDate, Set<EmployeeSkill> employeeSkills) {
        Employee employee = new Employee();
        DayOfWeek weekday = localDate.getDayOfWeek();
        List<Employee> employees = employeeRepository.findAllByDaysAvailable(weekday);
        return employees.stream().filter(emp -> emp.getSkills().containsAll(employeeSkills)).collect(Collectors.toList());
    }
}
