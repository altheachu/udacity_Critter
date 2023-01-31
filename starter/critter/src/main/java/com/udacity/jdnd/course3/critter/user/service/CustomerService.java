package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer, List<Long> petIds) throws Exception;

    List<Customer> getAllCustomer() throws Exception;

    Customer getCustomerByPetId(long petId) throws Exception;

}
