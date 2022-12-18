package com.udacity.jdnd.course3.critter.pet.service.impl;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.service.impl.CustomerServiceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PetServiceImpl implements PetService {

    private PetRepository petRepository;
    private CustomerRepository customerRepository;
    PetServiceImpl(PetRepository petRepository, CustomerRepository customerRepository){
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public Pet savePet(Pet pet, Long ownerId) {
        Customer customer = customerRepository.findById(ownerId).get();
        pet.setCustomer(customer);
        Pet resultPet =petRepository.save(pet);
        return resultPet;
    }
}
