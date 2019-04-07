package de.osp.springbootworkshop.domain.service;

import de.osp.springbootworkshop.domain.model.Pet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
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

        Pet klaus = Pet.builder()
                .name("Klaus")
                .type("Hamster")
                .birthDay(LocalDate.of(2019, 4, 13))
                .price(BigDecimal.valueOf(20))
                .build();

        Pet rubert = Pet.builder()
                .name("Rubert")
                .type("Hund")
                .birthDay(LocalDate.of(2018, 9, 18))
                .price(BigDecimal.valueOf(550))
                .build();

        Pet blacky = Pet.builder()
                .name("Blacky")
                .type("Katze")
                .birthDay(LocalDate.of(2018, 12, 12))
                .price(BigDecimal.valueOf(350))
                .build();

        this.pets.put(klaus.getName().toLowerCase().trim(), klaus);
        this.pets.put(rubert.getName().toLowerCase().trim(), rubert);
        this.pets.put(blacky.getName().toLowerCase().trim(), blacky);
    }

    public Collection<Pet> listPets() {
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
