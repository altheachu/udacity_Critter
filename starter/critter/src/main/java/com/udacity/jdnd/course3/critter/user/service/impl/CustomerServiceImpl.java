package com.udacity.jdnd.course3.critter.user.service.impl;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    private PetRepository petRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, PetRepository petRepository){
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }
    @Transactional(rollbackOn = Exception.class)
    @Override
    public Customer saveCustomer(Customer customer, List<Long> petIds) throws Exception{
        List<Pet> pets = new ArrayList<>();
        if(petIds!=null && !petIds.isEmpty()){
            petIds.forEach(petId->{
                Pet pet = petRepository.findById(petId).get();
                pets.add(pet);
            });
        }
        customer.setPets(pets);
        customer = customerRepository.save(customer);
        return customer;
    }

    @Override
    public List<Customer> getAllCustomer() throws Exception{
        List<Customer> customers = new ArrayList<>();
        Iterable<Customer> iter = customerRepository.findAll();
        for(Iterator<Customer> iterator = iter.iterator(); iterator.hasNext();){
            Customer customer = iterator.next();
            List<Pet> pets = petRepository.findAllByCustomer(customer);
            customer.setPets(pets);
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public Customer getCustomerByPetId(long petId) throws Exception{
        Pet pet = petRepository.findById(petId).get();
        Customer customer = customerRepository.findById(pet.getCustomer().getId()).get();
        List<Pet> pets = petRepository.findAllByCustomer(customer);
        customer.setPets(pets);
        return customer;
    }


}
