package de.osp.springbootworkshop.application.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.osp.springbootworkshop.domain.model.Pet;




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
    public Iterable<Pet> getPetList(){
    	return this.pets.values();
    }

    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Pet addPet(@RequestBody Pet pet){
    	
    	if(this.pets.containsKey(pet.getName().toLowerCase().trim())) {
    		throw new PetAlreadyExistsException("pet '" + pet.getName() + "' already exists");
    	}else {
    		this.pets.put(pet.getName().toLowerCase().trim(), pet);
    	}
    	
    	return pet;
    }
    

    // methods for REST endpoints omitted
}