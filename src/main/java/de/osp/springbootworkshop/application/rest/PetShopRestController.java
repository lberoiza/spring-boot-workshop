package de.osp.springbootworkshop.application.rest;

import de.osp.springbootworkshop.domain.model.Pet;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Denny
 */
@RestController
@RequestMapping("/petshop/pets")
public class PetShopRestController {
    private final Map<String, Pet> pets;

    public PetShopRestController() {
        this.pets = new ConcurrentHashMap<>();

        Pet klaus = new Pet("Klaus", "Hamster", LocalDate.of(2019, 4, 13), BigDecimal.valueOf(20));
        Pet rubert = new Pet("Rubert","Hund", LocalDate.of(2018, 9, 18), BigDecimal.valueOf(550));
        Pet blacky = new Pet("Blacky","Katze", LocalDate.of(2018, 12, 12),  BigDecimal.valueOf(350));

        this.pets.put(klaus.getName().toLowerCase().trim(), klaus);
        this.pets.put(rubert.getName().toLowerCase().trim(), rubert);
        this.pets.put(blacky.getName().toLowerCase().trim(), blacky);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Pet> listPets() {
        return pets.values();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Pet createPet(@RequestBody @Validated @NotNull final Pet pet) {
        if(pets.containsKey(pet.getName().toLowerCase().trim())) {
            throw new PetAlreadyExistsException("pet '" + pet.getName() + "' already exists");
        }

        pets.put(pet.getName().toLowerCase().trim(), pet);

       return pets.get(pet.getName().toLowerCase().trim());
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable @Validated @NotNull final String name) {
        if(pets.get(name.toLowerCase().trim()) == null) {
            throw new PetNotExistsException("pet '" + name + "' doesn't exists");
        }

        pets.remove(name.toLowerCase().trim());
    }
}
