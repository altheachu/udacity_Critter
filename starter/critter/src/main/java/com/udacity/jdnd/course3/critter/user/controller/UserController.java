package com.udacity.jdnd.course3.critter.user.controller;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.model.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.model.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.model.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import com.udacity.jdnd.course3.critter.user.utils.EmployeeSkill;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private CustomerService customerService;
    private EmployeeService employeeService;
    UserController(CustomerService customerService, EmployeeService employeeService){
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    /**return a saved customer matching the request*/
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        try{
            Customer customer = this.customerDTOToCustomer(customerDTO);
            customer = customerService.saveCustomer(customer, customerDTO.getPetIds());
            return this.customerToCustomerDTO(customer);
        }catch(Exception e){
            System.out.println("save customer failed:" + e.getMessage());
            return new CustomerDTO();
        }
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        try{
            List<Customer> customers = customerService.getAllCustomer();
            List<CustomerDTO> customerDTOs = new ArrayList<>();
            for(Customer customer: customers){
                CustomerDTO customerDTO = new CustomerDTO();
                List<Pet> pets = customer.getPets();
                List<Long> petIds = new ArrayList<>();
                for(Pet pet:pets){
                    petIds.add(pet.getId());
                }
                BeanUtils.copyProperties(customer,customerDTO);
                customerDTO.setPetIds(petIds);
                customerDTOs.add(customerDTO);
            }
            return customerDTOs;
        }catch(Exception e){
            System.out.println("get customer list failed:" + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**return the owner who used to register a certain pet.*/
    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        try {
            Customer customer = customerService.getCustomerByPetId(petId);
            CustomerDTO customerDTO = this.customerToCustomerDTO(customer);
            List<Pet> pets = customer.getPets();
            List<Long> petIds = new ArrayList<>();
            for (Pet pet : pets) {
                petIds.add(pet.getId());
            }
            customerDTO.setPetIds(petIds);
            return customerDTO;
        }catch(Exception e){
            System.out.println("get customer by a pet failed:" + e.getMessage());
            return new CustomerDTO();
        }
    }

    /**return a saved employee*/
    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try{
            Employee employee = this.employeeDTOTOEmployee(employeeDTO);
            employee = employeeService.saveEmployee(employee);
            return this.employeeTOEmployeeDTO(employee);
        }catch(Exception e){
            System.out.println("save employee failed:" + e.getMessage());
            return new EmployeeDTO();
        }
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        try{
            Employee employee = employeeService.findEmployeeById(employeeId);
            return this.employeeTOEmployeeDTO(employee);
        }catch(Exception e){
            System.out.println("get employee by id failed:" + e.getMessage());
            return new EmployeeDTO();
        }
    }

    /**return an employee with the same availability as set for that employee*/
    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        try{
            employeeService.saveDaysAvailableByEmployeeId(daysAvailable, employeeId);
        }catch(Exception e){
            System.out.println("get availability for an employee failed:" + e.getMessage());
        }
    }

    /**return all saved employees that have the requested availability and skills and none that do not*/
    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        LocalDate inquiryDate = employeeRequestDTO.getDate();
        Set<EmployeeSkill> inquirySkills = employeeRequestDTO.getSkills();
        List<Employee> employees = employeeService.findEmployeeMeetService(inquiryDate, inquirySkills);
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for(Employee employee : employees){
            EmployeeDTO employeeDTO = this.employeeTOEmployeeDTO(employee);
            employeeDTOs.add(employeeDTO);
        }
        return employeeDTOs;
    }

    private Customer customerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private CustomerDTO customerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Long> petIds = customer.getPets().stream().map(pet->pet.getId()).collect(Collectors.toList());
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    private Employee employeeDTOTOEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        return employee;
    }

    private EmployeeDTO employeeTOEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }
}
