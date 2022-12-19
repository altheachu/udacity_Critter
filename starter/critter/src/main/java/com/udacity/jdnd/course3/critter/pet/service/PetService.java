package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;

import java.util.List;

public interface PetService {
    Pet savePet(Pet pet, long ownerId);
    Pet findPetById(long petId);
    List<Pet> findAllPet();
    List<Pet> findPetByOwnerId(long ownerId);
}
