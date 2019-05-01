package de.osp.springbootworkshop.domain.service;

import de.osp.springbootworkshop.domain.model.Pet;
import de.osp.springbootworkshop.domain.model.PetType;
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
    private final Map<String, Pet> pets;

    public PetShopService() {
        this.pets = new ConcurrentHashMap<>();

        Pet klaus = new Pet("Klaus", new PetType("Hamster"), LocalDate.of(2019, 4, 13), BigDecimal.valueOf(20));
        Pet rubert = new Pet("Rubert",new PetType("Hund"), LocalDate.of(2018, 9, 18), BigDecimal.valueOf(550));
        Pet blacky = new Pet("Blacky",new PetType("Katze"), LocalDate.of(2018, 12, 12),  BigDecimal.valueOf(350));

        this.pets.put(klaus.getName().toLowerCase().trim(), klaus);
        this.pets.put(rubert.getName().toLowerCase().trim(), rubert);
        this.pets.put(blacky.getName().toLowerCase().trim(), blacky);
    }

    public Iterable<Pet> listPets() {
        return pets.values();
    }

    public Pet createPet(final Pet pet) {
        if(pets.containsKey(pet.getName().toLowerCase().trim())) {
            throw new PetAlreadyExistsException("pet '" + pet.getName() + "' already exists");
        }

        pets.put(pet.getName().toLowerCase().trim(), pet);

        return pets.get(pet.getName().toLowerCase().trim());
    }

    public void deletePet(final String name) {
        if(pets.get(name.toLowerCase().trim()) == null) {
            throw new PetNotExistsException("pet '" + name + "' doesn't exists");
        }

        pets.remove(name.toLowerCase().trim());
    }
}
