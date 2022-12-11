package com.udacity.jdnd.course3.critter.pet.repository;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;

import java.util.List;

public interface PetRepository {

    Pet save(Pet p);

    Pet findById(Long id);

    List<Pet> findAll();

    void delete(Pet p);

}
