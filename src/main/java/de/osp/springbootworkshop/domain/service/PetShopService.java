package de.osp.springbootworkshop.domain.service;

import de.osp.springbootworkshop.domain.model.Pet;
import de.osp.springbootworkshop.domain.model.PetType;
import de.osp.springbootworkshop.domain.repository.PetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Denny
 */
@Service
public class PetShopService {
    private final PetRepository pets;

    @Autowired public PetShopService(PetRepository petRepository) {
    	this.pets = petRepository;
    }

    public Iterable<Pet> listPets() {
        return pets.findAll();
    }

    public Pet createPet(final Pet pet) {
        if(pets.existsById(pet.getName())) {
            throw new PetAlreadyExistsException("pet '" + pet.getName() + "' already exists");
        }

        return pets.save(pet);

    }

    public void deletePet(final String name) {
        if(!pets.existsById(name)) {
            throw new PetNotExistsException("pet '" + name + "' doesn't exists");
        }

        pets.deleteById(name);
    }
}
