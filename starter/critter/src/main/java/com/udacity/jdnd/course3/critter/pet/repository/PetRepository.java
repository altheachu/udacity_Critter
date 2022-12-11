package com.udacity.jdnd.course3.critter.pet.repository;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class PetRepository {

    @PersistenceContext
    EntityManager entityManager;

    // Create
    public void create(Pet pet){
        entityManager.persist(pet);
    }

    // Read
    public Pet find(Long id){
        return entityManager.find(Pet.class, id);
    }

    // Update
    public Pet update(Pet pet){
        return entityManager.merge(pet);
    }

    // Delete
    public void delete(Long id){
        Pet pet = entityManager.find(Pet.class, id);
        entityManager.remove(pet);
    }


}
