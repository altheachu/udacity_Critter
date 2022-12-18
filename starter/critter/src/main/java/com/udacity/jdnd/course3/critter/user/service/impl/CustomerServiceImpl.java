package com.udacity.jdnd.course3.critter.user.service.impl;

import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Transactional
    @Override
    public Customer saveCustomer(Customer customer) {
        Customer resultCustomer = customerRepository.save(customer);
        return resultCustomer;
    }
}
