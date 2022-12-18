package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;

public interface PetService {
    Pet savePet(Pet pet, Long ownerId);
}
