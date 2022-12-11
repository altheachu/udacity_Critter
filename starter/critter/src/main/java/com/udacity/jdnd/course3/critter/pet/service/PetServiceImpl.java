package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService{

    @Autowired
    private PetRepository petRepository;
    @Override
    public Pet save(Pet p) {
        return petRepository.save(p);
    }

    @Override
    public Pet findById(Long id) {
        return petRepository.findById(id);
    }


}
