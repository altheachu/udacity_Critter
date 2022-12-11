package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.user.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@Repository
public class UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    // Create
    public void create(User user){
        entityManager.persist(user);
    }

    // Read
    public User find(Long id){
        return entityManager.find(User.class, id);
    }

    // Update
    public User update(User user){
        return entityManager.merge(user);
    }

    // Delete
    public void delete(Long id){
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

}
