package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;

import java.util.List;

public interface PetService {
    Pet savePet(Pet pet, long ownerId) throws Exception;
    Pet findPetById(long petId) throws Exception;
    List<Pet> findAllPet() throws Exception;
    List<Pet> findPetByOwnerId(long ownerId) throws Exception;
}
