package com.udacity.jdnd.course3.critter.pet.repository;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PetRepositoryImpl implements PetRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Pet save(Pet p) {
        if(p.getId()==null||p.getId() <= 0){
            entityManager.persist(p);
        }else{
            p = entityManager.merge(p);
        }
        return p;
    }

    @Override
    public Pet findById(Long id) {
        return entityManager.find(Pet.class, id);
    }

    @Override
    public List<Pet> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pet> criteria = cb.createQuery(Pet.class);
        Root<Pet> root = criteria.from(Pet.class);
        criteria.select(root);
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public void delete(Pet p) {
        if(entityManager.contains(p)){
            entityManager.remove(p);
        }else{
            entityManager.remove(entityManager.merge(p));
        }
    }
}
