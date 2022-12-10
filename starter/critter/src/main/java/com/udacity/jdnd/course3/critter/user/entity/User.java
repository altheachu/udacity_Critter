package com.udacity.jdnd.course3.critter.user.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="User")
@Entity
@DiscriminatorColumn(
        name="userType",
        discriminatorType=DiscriminatorType.STRING
)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id",nullable = false)
    private Long id;

    @Nationalized
    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
