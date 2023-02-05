package com.udacity.jdnd.course3.critter.pet.controller;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.model.PetDTO;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;
    private CustomerService customerService;

    PetController(PetService petService, CustomerService customerService){
        this.customerService = customerService;
        this.petService = petService;
    }
    /**return a saved pet*/
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO){
        try{
            Long ownerId = petDTO.getOwnerId();
            Pet pet = this.converPetDTOToPet(petDTO);
            pet = petService.savePet(pet, ownerId);
            PetDTO newPetDTO = this.converPetToPetDTO(pet);
            newPetDTO.setOwnerId(pet.getCustomer().getId());
            return newPetDTO;
        }catch (Exception e){
            System.out.println("save pet failed:" + e.getMessage());
            return new PetDTO();
        }

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        try{
            Pet pet = petService.findPetById(petId);
            long ownerId = pet.getCustomer().getId();
            PetDTO petDTO = this.converPetToPetDTO(pet);
            petDTO.setOwnerId(ownerId);
            return petDTO;
        }catch (Exception e){
            System.out.println("get pet by id failed:" + e.getMessage());
            return new PetDTO();
        }
    }

    @GetMapping
    public List<PetDTO> getPets(){
        try{
            List<Pet> pets = petService.findAllPet();
            List<PetDTO> petDTOs = new ArrayList<>();
            for(Pet pet:pets){
                PetDTO petDTO = this.converPetToPetDTO(pet);
                petDTO.setOwnerId(pet.getCustomer().getId());
                petDTOs.add(petDTO);
            }
            return petDTOs;
        }catch (Exception e){
            System.out.println("get all pets failed:" + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**return all pets saved for that owner*/
    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        try{
            List<PetDTO> petDTOs = new ArrayList<>();
            List<Pet> pets = petService.findPetByOwnerId(ownerId);
            for(Pet pet:pets){
                PetDTO petDTO = this.converPetToPetDTO(pet);
                petDTO.setOwnerId(ownerId);
                petDTOs.add(petDTO);
            }
            return petDTOs;
        }catch(Exception e){
            System.out.println("get pets by owner failed:" + e.getMessage());
            return new ArrayList<>();
        }
    }

    private Pet converPetDTOToPet(PetDTO petDTO){
        Pet p = new Pet();
        BeanUtils.copyProperties(petDTO, p);
        return p;
    }

    private PetDTO converPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;
    }


}
