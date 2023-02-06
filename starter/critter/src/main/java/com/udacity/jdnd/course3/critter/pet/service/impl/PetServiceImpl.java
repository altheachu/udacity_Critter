package com.udacity.jdnd.course3.critter.pet.service.impl;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.service.impl.CustomerServiceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private PetRepository petRepository;
    private CustomerRepository customerRepository;
    PetServiceImpl(PetRepository petRepository, CustomerRepository customerRepository){
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Pet savePet(Pet pet, long ownerId) throws Exception{
        Customer customer = customerRepository.findById(ownerId).get();
        pet.setCustomer(customer);
        pet =petRepository.save(pet);
        return pet;
    }

    @Override
    public Pet findPetById(long petId) throws Exception{
        return petRepository.findById(petId).get();
    }

    @Override
    public List<Pet> findAllPet() throws Exception{
        List<Pet> pets = new ArrayList<>();
        Iterable<Pet> iter = petRepository.findAll();
        for(Iterator<Pet> iterator = iter.iterator();iterator.hasNext();){
            pets.add(iterator.next());
        }
        return pets;
    }

    @Override
    public List<Pet> findPetByOwnerId(long ownerId) throws Exception{
        Customer customer = customerRepository.findById(ownerId).get();
        List<Pet> pets = petRepository.findAllByCustomer(customer);
        return pets;
    }
}
